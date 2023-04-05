/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.adempiere.exceptions.DocTypeNotFoundException
 *  org.adempiere.exceptions.FillMandatoryException
 *  org.adempiere.exceptions.NoVendorForProductException
 *  org.compiere.model.I_C_UOM
 *  org.compiere.model.MBPartner
 *  org.compiere.model.MDocType
 *  org.compiere.model.MLocator
 *  org.compiere.model.MOrder
 *  org.compiere.model.MOrderLine
 *  org.compiere.model.MPeriod
 *  org.compiere.model.MProduct
 *  org.compiere.model.MProductPO
 *  org.compiere.model.MUOM
 *  org.compiere.model.MWarehouse
 *  org.compiere.model.ModelValidationEngine
 *  org.compiere.model.PO
 *  org.compiere.model.Query
 *  org.compiere.print.ReportEngine
 *  org.compiere.process.DocAction
 *  org.compiere.process.DocumentEngine
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.compiere.util.Msg
 *  org.compiere.util.TimeUtil
 */
package org.libero.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DocTypeNotFoundException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.exceptions.NoVendorForProductException;
import org.adempiere.model.engines.CostEngineFactory;
import org.adempiere.model.engines.IDocumentLine;
import org.adempiere.model.engines.StorageEngine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MLocator;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPeriod;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPO;
import org.compiere.model.MUOM;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.libero.exceptions.ActivityProcessedException;
import org.libero.model.MPPOrder;
import org.libero.model.MPPOrderBOMLine;
import org.libero.model.MPPOrderNode;
import org.libero.model.MPPOrderNodeProduct;
import org.libero.model.MPPOrderWorkflow;
import org.libero.tables.I_PP_Cost_Collector;
import org.libero.tables.X_PP_Cost_Collector;

public class MPPCostCollector
extends X_PP_Cost_Collector
implements DocAction,
IDocumentLine {
    private static final long serialVersionUID = 1L;
    private static BigDecimal costva = BigDecimal.ZERO;
    private static BigDecimal jmltenagakerja = BigDecimal.ZERO;
    private static BigDecimal qtyreserved = BigDecimal.ZERO;
    private String m_processMsg = null;
    private boolean m_justPrepared = false;
    private MPPOrder m_order = null;
    private MPPOrderNode m_orderNode = null;
    private MPPOrderBOMLine m_bomLine = null;
    private int attribute = 0;

    public static MPPCostCollector createCollector(MPPOrder order, int M_Product_ID, int M_Locator_ID, int M_AttributeSetInstance_ID, int S_Resource_ID, int PP_Order_BOMLine_ID, int PP_Order_Node_ID, int C_DocType_ID, String CostCollectorType, Timestamp movementdate, BigDecimal qty, BigDecimal scrap, BigDecimal reject, int durationSetup, BigDecimal duration, BigDecimal costvarian, BigDecimal qtyres, BigDecimal qtyjmltenagakerja) {
        System.out.println("afsafa" + qtyres);
        qtyreserved = qtyres;
        jmltenagakerja = qtyjmltenagakerja;
        costva = costvarian;
        MPPCostCollector cc = new MPPCostCollector(order);
        cc.setPP_Order_BOMLine_ID(PP_Order_BOMLine_ID);
        cc.setPP_Order_Node_ID(PP_Order_Node_ID);
        cc.setC_DocType_ID(C_DocType_ID);
        cc.setC_DocTypeTarget_ID(C_DocType_ID);
        cc.setCostCollectorType(CostCollectorType);
        cc.setDocAction("CO");
        cc.setDocStatus("DR");
        cc.setIsActive(true);
        cc.setM_Locator_ID(M_Locator_ID);
        cc.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
        cc.setS_Resource_ID(S_Resource_ID);
        cc.setMovementDate(movementdate);
        cc.setDateAcct(movementdate);
        System.out.println("stok" + qty);
        cc.setMovementQty(qty);
        cc.setScrappedQty(scrap);
        cc.setQtyReject(reject);
        cc.setSetupTimeReal(new BigDecimal(durationSetup));
        cc.setDurationReal(duration);
        cc.setPosted(false);
        cc.setProcessed(false);
        cc.setProcessing(false);
        cc.setUser1_ID(order.getUser1_ID());
        cc.setUser2_ID(order.getUser2_ID());
        cc.setM_Product_ID(M_Product_ID);
        if (PP_Order_Node_ID > 0) {
            cc.setIsSubcontracting(PP_Order_Node_ID);
        }
        if (PP_Order_BOMLine_ID > 0) {
            cc.setC_UOM_ID(0);
        }
        cc.saveEx(order.get_TrxName());
        if (!cc.processIt("CO")) {
            throw new AdempiereException(cc.getProcessMsg());
        }
        cc.saveEx(order.get_TrxName());
        return cc;
    }

    public static void setPP_Order(I_PP_Cost_Collector cc, MPPOrder order) {
        cc.setPP_Order_ID(order.getPP_Order_ID());
        cc.setPP_Order_Workflow_ID(order.getMPPOrderWorkflow().get_ID());
        cc.setAD_Org_ID(order.getAD_Org_ID());
        cc.setM_Warehouse_ID(order.getM_Warehouse_ID());
        cc.setAD_OrgTrx_ID(order.getAD_OrgTrx_ID());
        cc.setC_Activity_ID(order.getC_Activity_ID());
        cc.setC_Campaign_ID(order.getC_Campaign_ID());
        cc.setC_Project_ID(order.getC_Project_ID());
        cc.setDescription(order.getDescription());
        cc.setS_Resource_ID(order.getS_Resource_ID());
        cc.setM_Product_ID(order.getM_Product_ID());
        cc.setC_UOM_ID(order.getC_UOM_ID());
        cc.setM_AttributeSetInstance_ID(order.getM_AttributeSetInstance_ID());
        cc.setMovementQty(order.getQtyOrdered());
    }

    public MPPCostCollector(Properties ctx, int PP_Cost_Collector_ID, String trxName) {
        super(ctx, PP_Cost_Collector_ID, trxName);
        if (PP_Cost_Collector_ID == 0) {
            this.setDocStatus("DR");
            this.setDocAction("CO");
            this.setMovementDate(new Timestamp(System.currentTimeMillis()));
            this.setIsActive(true);
            this.setPosted(false);
            this.setProcessing(false);
            this.setProcessed(false);
        }
    }

    public MPPCostCollector(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    public MPPCostCollector(MPPOrder order) {
        this(order.getCtx(), 0, order.get_TrxName());
        MPPCostCollector.setPP_Order(this, order);
        this.m_order = order;
    }

    public void addDescription(String description) {
        String desc = this.getDescription();
        if (desc == null) {
            this.setDescription(description);
        } else {
            this.setDescription(String.valueOf(desc) + " | " + description);
        }
    }

    public void setC_DocTypeTarget_ID(String docBaseType) {
        MDocType[] doc = MDocType.getOfDocBaseType((Properties)this.getCtx(), (String)docBaseType);
        if (doc == null) {
            throw new DocTypeNotFoundException(docBaseType, "");
        }
        this.setC_DocTypeTarget_ID(doc[0].get_ID());
    }

    @Override
    public void setProcessed(boolean processed) {
        super.setProcessed(processed);
        if (this.get_ID() == 0) {
            return;
        }
        int noLine = DB.executeUpdateEx((String)"UPDATE PP_Cost_Collector SET Processed=? WHERE PP_Cost_Collector_ID=?", (Object[])new Object[]{processed, this.get_ID()}, (String)this.get_TrxName());
        this.log.fine("setProcessed - " + processed + " - Lines=" + noLine);
    }

    public boolean processIt(String processAction) {
        this.m_processMsg = null;
        DocumentEngine engine = new DocumentEngine((DocAction)this, this.getDocStatus());
        return engine.processIt(processAction, this.getDocAction());
    }

    public boolean unlockIt() {
        this.log.info("unlockIt - " + this.toString());
        this.setProcessing(false);
        return true;
    }

    public boolean invalidateIt() {
        this.log.info("invalidateIt - " + this.toString());
        this.setDocAction("PR");
        return true;
    }

    public String prepareIt() {
        this.m_processMsg = ModelValidationEngine.get().fireDocValidate((PO)this, 1);
        if (this.m_processMsg != null) {
            return "IN";
        }
        MPeriod.testPeriodOpen((Properties)this.getCtx(), (Timestamp)this.getDateAcct(), (int)this.getC_DocTypeTarget_ID(), (int)this.getAD_Org_ID());
        this.setC_DocType_ID(this.getC_DocTypeTarget_ID());
        if (this.isActivityControl()) {
            MPPOrderNode activity = this.getPP_Order_Node();
            if ("CO".equals(activity.getDocStatus())) {
                throw new ActivityProcessedException(activity);
            }
            if (activity.isSubcontracting()) {
                if ("IP".equals(activity.getDocStatus()) && "IP".equals(this.getDocStatus())) {
                    return "IP";
                }
                if ("IP".equals(activity.getDocStatus()) && "DR".equals(this.getDocStatus())) {
                    throw new ActivityProcessedException(activity);
                }
                this.m_processMsg = this.createPO(activity);
                this.m_justPrepared = false;
                activity.setInProgress(this);
                activity.saveEx(this.get_TrxName());
                return "IP";
            }
            activity.set_CustomColumn("qtyreserved", qtyreserved);
            activity.setInProgress(this);
            activity.setQtyDelivered(activity.getQtyDelivered().add(this.getMovementQty()));
            activity.setQtyScrap(activity.getQtyScrap().add(this.getScrappedQty()));
            activity.setQtyReject(activity.getQtyReject().add(this.getQtyReject()));
            activity.setDurationReal(activity.getDurationReal() + this.getDurationReal().intValueExact());
            activity.setSetupTimeReal(activity.getSetupTimeReal() + this.getSetupTimeReal().intValueExact());
            activity.saveEx(this.get_TrxName());
            if (activity.isMilestone()) {
                MPPOrderWorkflow order_workflow = activity.getMPPOrderWorkflow();
                order_workflow.closeActivities(activity, this.getMovementDate(), true);
            }
        } else if (this.isIssue()) {
            MProduct product = this.getM_Product();
            if (this.getM_AttributeSetInstance_ID() == 0 && product.isASIMandatory(false)) {
                throw new AdempiereException("@M_AttributeSet_ID@ @IsMandatory@ @M_Product_ID@=" + product.getValue());
            }
        } else if (this.isReceipt()) {
            MProduct product = this.getM_Product();
            if (this.getM_AttributeSetInstance_ID() == 0 && product.isASIMandatory(true)) {
                throw new AdempiereException("@M_AttributeSet_ID@ @IsMandatory@ @M_Product_ID@=" + product.getValue());
            }
        }
        this.m_justPrepared = true;
        this.setDocAction("CO");
        this.m_processMsg = ModelValidationEngine.get().fireDocValidate((PO)this, 8);
        if (this.m_processMsg != null) {
            return "IN";
        }
        System.out.println("attr" + this.getM_AttributeSetInstance_ID());
        return "IP";
    }

    public boolean approveIt() {
        this.log.info("approveIt - " + this.toString());
        return true;
    }

    public boolean rejectIt() {
        this.log.info("rejectIt - " + this.toString());
        return true;
    }

    public String completeIt() {
        MPPOrderNode activity;
        String status;
        if (!this.m_justPrepared && !"IP".equals(status = this.prepareIt())) {
            return status;
        }
        this.m_processMsg = ModelValidationEngine.get().fireDocValidate((PO)this, 7);
        if (this.m_processMsg != null) {
            return "IN";
        }
        if (this.isIssue() || this.isReceipt()) {
            MProduct product = this.getM_Product();
            String docBaseType = MDocType.get((Properties)this.getCtx(), (int)this.getPP_Order().getC_DocType_ID()).getDocBaseType();
            if (docBaseType.equals("MOF")) {
                if (product != null && product.isStocked()) {
                    System.out.println("movementqty" + this.getMovementQty());
                    StorageEngine.createTransaction(this, this.getMovementType(), this.getMovementDate(), this.getMovementQty(), false, this.getM_Warehouse_ID(), this.getPP_Order().getM_AttributeSetInstance_ID(), this.getPP_Order().getM_Warehouse_ID(), false);
                }
            } else if (product != null && product.isStocked() && !this.isVariance()) {
                System.out.println("attrtr" + this.getPP_Order().getM_AttributeSetInstance_ID());
                StorageEngine.createTransaction(this, this.getMovementType(), this.getMovementDate(), this.getMovementQty(), false, this.getM_Warehouse_ID(), this.getPP_Order().getM_AttributeSetInstance_ID(), this.getPP_Order().getM_Warehouse_ID(), false);
            }
            if (this.isIssue()) {
                MPPOrderBOMLine obomline = this.getPP_Order_BOMLine();
                obomline.setQtyDelivered(obomline.getQtyDelivered().add(this.getMovementQty()));
                obomline.setQtyScrap(obomline.getQtyScrap().add(this.getScrappedQty()));
                obomline.setQtyReject(obomline.getQtyReject().add(this.getQtyReject()));
                obomline.setDateDelivered(this.getMovementDate());
                this.log.fine("OrderLine - Reserved=" + obomline.getQtyReserved() + ", Delivered=" + obomline.getQtyDelivered());
                obomline.saveEx(this.get_TrxName());
                this.log.fine("OrderLine -> Reserved=" + obomline.getQtyReserved() + ", Delivered=" + obomline.getQtyDelivered());
            }
            if (this.isReceipt()) {
                MPPOrder order = this.getPP_Order();
                order.setQtyDelivered(order.getQtyDelivered().add(this.getMovementQty()));
                order.setQtyScrap(order.getQtyScrap().add(this.getScrappedQty()));
                order.setQtyReject(order.getQtyReject().add(this.getQtyReject()));
                order.setDateDelivered(this.getMovementDate());
                if (order.getDateStart() == null) {
                    order.setDateStart(this.getDateStart());
                }
                if (order.getQtyOpen().signum() <= 0) {
                    order.setDateFinish(this.getDateFinish());
                }
                order.saveEx(this.get_TrxName());
            }
        } else if (this.isActivityControl()) {
            activity = this.getPP_Order_Node();
            if (activity.isProcessed()) {
                throw new ActivityProcessedException(activity);
            }
            if (this.isSubcontracting()) {
                String whereClause = "PP_Cost_Collector_ID=?";
                List olines = new Query(this.getCtx(), "C_OrderLine", whereClause, this.get_TrxName()).setParameters(new Object[]{this.get_ID()}).list();
                String DocStatus = "CO";
                StringBuffer msg = new StringBuffer("The quantity do not is complete for next Purchase Order : ");
                for (MOrderLine oline : olines) {
                    if (oline.getQtyDelivered().compareTo(oline.getQtyOrdered()) < 0) {
                        DocStatus = "IP";
                    }
                    msg.append(oline.getParent().getDocumentNo()).append(",");
                }
                if ("IP".equals(DocStatus)) {
                    this.m_processMsg = msg.toString();
                    return DocStatus;
                }
                this.setProcessed(true);
                this.setDocAction("CL");
                this.setDocStatus("CO");
                activity.completeIt();
                activity.saveEx(this.get_TrxName());
                this.m_processMsg = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"PP_Order_ID")) + ": " + this.getPP_Order().getDocumentNo() + " " + Msg.translate((Properties)this.getCtx(), (String)"PP_Order_Node_ID") + ": " + this.getPP_Order_Node().getValue();
                return DocStatus;
            }
            CostEngineFactory.getCostEngine(this.getAD_Client_ID()).createActivityControl(this);
            if (activity.getQtyDelivered().compareTo(activity.getQtyRequired()) >= 0) {
                activity.closeIt();
                activity.saveEx(this.get_TrxName());
            }
        } else if (this.isCostCollectorType("120") && this.getPP_Order_BOMLine_ID() > 0) {
            MPPOrderBOMLine obomline = this.getPP_Order_BOMLine();
            obomline.setQtyDelivered(obomline.getQtyDelivered().add(this.getMovementQty()));
            obomline.setQtyScrap(obomline.getQtyScrap().add(this.getScrappedQty()));
            obomline.setQtyReject(obomline.getQtyReject().add(this.getQtyReject()));
            this.log.fine("OrderLine - Reserved=" + obomline.getQtyReserved() + ", Delivered=" + obomline.getQtyDelivered());
            obomline.saveEx(this.get_TrxName());
            this.log.fine("OrderLine -> Reserved=" + obomline.getQtyReserved() + ", Delivered=" + obomline.getQtyDelivered());
            CostEngineFactory.getCostEngine(this.getAD_Client_ID()).createUsageVariances(this, Env.ZERO, Env.ZERO);
        } else if (this.isCostCollectorType("120") && this.getPP_Order_Node_ID() > 0) {
            activity = this.getPP_Order_Node();
            activity.setDurationReal(activity.getDurationReal() + this.getDurationReal().intValueExact());
            activity.setSetupTimeReal(activity.getSetupTimeReal() + this.getSetupTimeReal().intValueExact());
            activity.saveEx(this.get_TrxName());
            System.out.println("COSTCOLLECTORTYPE_UsegeVariance" + costva);
            CostEngineFactory.getCostEngine(this.getAD_Client_ID()).createUsageVariances(this, costva, jmltenagakerja);
        }
        if (qtyreserved.compareTo(BigDecimal.ZERO) == 0) {
            CostEngineFactory.getCostEngine(this.getAD_Client_ID()).createRateVariances(this);
            CostEngineFactory.getCostEngine(this.getAD_Client_ID()).createMethodVariances(this);
        } else {
            System.out.println("createActivityControl");
        }
        this.m_processMsg = ModelValidationEngine.get().fireDocValidate((PO)this, 9);
        if (this.m_processMsg != null) {
            return "IN";
        }
        System.out.println("attribute" + this.attribute);
        this.setProcessed(true);
        this.setDocAction("CL");
        this.setDocStatus("CO");
        this.setDescription(String.valueOf(DB.getSQLValue(null, (String)("select count(*) from pp_cost_collector where pp_order_id = " + this.getPP_Order_ID() + " and costcollectortype = '100' and docstatus = 'CO'"))));
        return "CO";
    }

    public boolean voidIt() {
        this.m_processMsg = ModelValidationEngine.get().fireDocValidate((PO)this, 2);
        MPPCostCollector collector = new MPPCostCollector(this.getCtx(), this.getPP_Cost_Collector_ID(), this.get_TrxName());
        if (collector.getCostCollectorType().equals("100")) {
            StorageEngine.createTransaction(this, "W-", new Timestamp(new Date().getTime()), collector.getMovementQty(), true, collector.getM_Warehouse_ID(), collector.getM_AttributeSetInstance_ID(), collector.getM_Warehouse_ID(), false);
        } else if (collector.getCostCollectorType().equals("110")) {
            StorageEngine.createTransaction(this, "W+", new Timestamp(new Date().getTime()), collector.getMovementQty(), true, collector.getM_Warehouse_ID(), collector.getM_AttributeSetInstance_ID(), collector.getM_Warehouse_ID(), false);
        }
        if (collector.getCostCollectorType().equals("100")) {
            MPPOrder order = collector.getPP_Order();
            order.setQtyDelivered(order.getQtyDelivered().subtract(collector.getMovementQty()));
            order.setQtyScrap(order.getQtyScrap().subtract(collector.getScrappedQty()));
            order.setQtyReject(order.getQtyReject().subtract(collector.getQtyReject()));
            if (order.getDocStatus().equals("CL")) {
                order.setDocStatus("CO");
                order.setDocAction("CL");
            }
            order.saveEx(this.get_TrxName());
        } else if (collector.getCostCollectorType().equals("110")) {
            MPPOrderBOMLine obomline = collector.getPP_Order_BOMLine();
            obomline.setQtyDelivered(obomline.getQtyDelivered().subtract(collector.getMovementQty()));
            obomline.setQtyScrap(obomline.getQtyScrap().subtract(collector.getScrappedQty()));
            obomline.setQtyReject(obomline.getQtyReject().subtract(collector.getQtyReject()));
            obomline.saveEx(this.get_TrxName());
        } else if (collector.getCostCollectorType().equals("160")) {
            MPPOrderNode orderNode = collector.getPP_Order_Node();
            BigDecimal qtydeliver = orderNode.getQtyRequired().subtract(collector.getMovementQty().add(new BigDecimal(orderNode.get_Value("qtyreserved").toString())));
            orderNode.setQtyDelivered(orderNode.getQtyDelivered().subtract(collector.getMovementQty()));
            orderNode.setDurationReal(orderNode.getDurationReal() - collector.getDurationReal().intValue());
            orderNode.set_CustomColumn("qtyreserved", orderNode.getQtyRequired().subtract(collector.getMovementQty().add(new BigDecimal(orderNode.get_Value("qtyreserved").toString()))));
            if (orderNode.getDocStatus().equals("CL") || orderNode.getDocStatus().equals("IP")) {
                if (qtydeliver.compareTo(BigDecimal.ZERO) == 0) {
                    orderNode.setDocStatus("DR");
                    orderNode.setDocAction("CO");
                } else {
                    orderNode.setDocStatus("IP");
                    orderNode.setDocAction("CO");
                }
            }
            orderNode.saveEx();
        }
        this.m_processMsg = ModelValidationEngine.get().fireDocValidate((PO)this, 10);
        DB.executeUpdate((String)("delete from Fact_Acct where record_id = " + collector.getPP_Cost_Collector_ID()), (String)this.get_TrxName());
        if (DB.getSQLValueString((String)this.get_TrxName(), (String)("select coalesce(costingmethod,'') from M_Product_Category_Acct where m_product_category_id = " + collector.getM_Product().getM_Product_Category_ID()), (Object[])new Object[0]).equals("F")) {
            DB.executeUpdate((String)("update m_cost set currentqty = (select sum(qtyonhand) from m_storage where m_product_id = " + collector.getM_Product_ID() + " and M_AttributeSetInstance_ID = " + collector.getM_AttributeSetInstance_ID() + ") where m_product_id = " + collector.getM_Product_ID() + " and M_AttributeSetInstance_ID = " + collector.getM_AttributeSetInstance_ID()), (String)this.get_TrxName());
        } else {
            DB.executeUpdate((String)("update m_cost set currentqty = (select sum(qtyonhand) from m_storage where m_product_id = " + collector.getM_Product_ID() + ") where m_product_id = " + collector.getM_Product_ID() + " and M_CostElement_ID=1000000 "), (String)this.get_TrxName());
        }
        return true;
    }

    public boolean closeIt() {
        this.log.info("closeIt - " + this.toString());
        this.setDocAction("--");
        return true;
    }

    public boolean reverseCorrectIt() {
        return false;
    }

    public boolean reverseAccrualIt() {
        return false;
    }

    public boolean reActivateIt() {
        return false;
    }

    public String getSummary() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getDescription());
        return sb.toString();
    }

    public String getProcessMsg() {
        return this.m_processMsg;
    }

    public int getDoc_User_ID() {
        return this.getCreatedBy();
    }

    public int getC_Currency_ID() {
        return 0;
    }

    public BigDecimal getApprovalAmt() {
        return Env.ZERO;
    }

    public File createPDF() {
        try {
            File temp = File.createTempFile(String.valueOf(this.get_TableName()) + this.get_ID() + "_", ".pdf");
            return this.createPDF(temp);
        }
        catch (Exception e) {
            this.log.severe("Could not create PDF - " + e.getMessage());
            return null;
        }
    }

    public File createPDF(File file) {
        ReportEngine re = ReportEngine.get((Properties)this.getCtx(), (int)0, (int)this.getPP_Order_ID());
        if (re == null) {
            return null;
        }
        return re.getPDF(file);
    }

    public String getDocumentInfo() {
        MDocType dt = MDocType.get((Properties)this.getCtx(), (int)this.getC_DocType_ID());
        return String.valueOf(dt.getName()) + " " + this.getDocumentNo();
    }

    protected boolean beforeSave(boolean newRecord) {
        MWarehouse wh;
        MLocator loc;
        if (this.getM_Locator_ID() <= 0 && this.getM_Warehouse_ID() > 0 && (loc = (wh = MWarehouse.get((Properties)this.getCtx(), (int)this.getM_Warehouse_ID())).getDefaultLocator()) != null) {
            this.setM_Locator_ID(loc.get_ID());
        }
        if (this.isIssue()) {
            if (this.getPP_Order_BOMLine_ID() <= 0) {
                throw new FillMandatoryException(new String[]{"PP_Order_BOMLine_ID"});
            }
            if (this.getC_UOM_ID() <= 0) {
                this.setC_UOM_ID(this.getPP_Order_BOMLine().getC_UOM_ID());
            }
            if (this.getC_UOM_ID() != this.getPP_Order_BOMLine().getC_UOM_ID()) {
                throw new AdempiereException("@PP_Cost_Collector_ID@ @C_UOM_ID@ <> @PP_Order_BOMLine_ID@ @C_UOM_ID@");
            }
        }
        if (this.isActivityControl() && this.getPP_Order_Node_ID() <= 0) {
            throw new FillMandatoryException(new String[]{"PP_Order_Node_ID"});
        }
        return true;
    }

    @Override
    public MPPOrderNode getPP_Order_Node() {
        int node_id = this.getPP_Order_Node_ID();
        if (node_id <= 0) {
            this.m_orderNode = null;
            return null;
        }
        if (this.m_orderNode == null || this.m_orderNode.get_ID() != node_id) {
            this.m_orderNode = new MPPOrderNode(this.getCtx(), node_id, this.get_TrxName());
        }
        return this.m_orderNode;
    }

    @Override
    public MPPOrderBOMLine getPP_Order_BOMLine() {
        int id = this.getPP_Order_BOMLine_ID();
        if (id <= 0) {
            this.m_bomLine = null;
            return null;
        }
        if (this.m_bomLine == null || this.m_bomLine.get_ID() != id) {
            this.m_bomLine = new MPPOrderBOMLine(this.getCtx(), id, this.get_TrxName());
        }
        this.m_bomLine.set_TrxName(this.get_TrxName());
        return this.m_bomLine;
    }

    public MPPOrder getPP_Order() {
        int id = this.getPP_Order_ID();
        if (id <= 0) {
            this.m_order = null;
            return null;
        }
        if (this.m_order == null || this.m_order.get_ID() != id) {
            this.m_order = new MPPOrder(this.getCtx(), id, this.get_TrxName());
        }
        return this.m_order;
    }

    public long getDurationBaseSec() {
        return this.getPP_Order().getMPPOrderWorkflow().getDurationBaseSec();
    }

    public Timestamp getDateStart() {
        double duration = this.getDurationReal().doubleValue();
        if (duration != 0.0) {
            long durationMillis = (long)(this.getDurationReal().doubleValue() * (double)this.getDurationBaseSec() * 1000.0);
            return new Timestamp(this.getMovementDate().getTime() - durationMillis);
        }
        return this.getMovementDate();
    }

    public Timestamp getDateFinish() {
        return this.getMovementDate();
    }

    private String createPO(MPPOrderNode activity) {
        String msg = "";
        HashMap<Integer, MOrder> orders = new HashMap<Integer, MOrder>();
        String whereClause = "PP_Order_Node_ID=? AND IsSubcontracting=?";
        List subcontracts = new Query(this.getCtx(), "PP_Order_Node_Product", whereClause, this.get_TrxName()).setParameters(new Object[]{activity.get_ID(), true}).setOnlyActiveRecords(true).list();
        for (MPPOrderNodeProduct subcontract : subcontracts) {
            MProduct product = MProduct.get((Properties)this.getCtx(), (int)subcontract.getM_Product_ID());
            if (!product.isPurchased() || !"S".equals(product.getProductType())) {
                throw new AdempiereException("The Product: " + product.getName() + " Do not is Purchase or Service Type");
            }
            int C_BPartner_ID = activity.getC_BPartner_ID();
            MProductPO product_po = null;
            for (MProductPO ppo : MProductPO.getOfProduct((Properties)this.getCtx(), (int)product.get_ID(), null)) {
                if (C_BPartner_ID == ppo.getC_BPartner_ID()) {
                    C_BPartner_ID = ppo.getC_BPartner_ID();
                    product_po = ppo;
                    break;
                }
                if (!ppo.isCurrentVendor() || ppo.getC_BPartner_ID() == 0) continue;
                C_BPartner_ID = ppo.getC_BPartner_ID();
                product_po = ppo;
                break;
            }
            if (C_BPartner_ID <= 0 || product_po == null) {
                throw new NoVendorForProductException(product.getName());
            }
            Timestamp today = new Timestamp(System.currentTimeMillis());
            Timestamp datePromised = TimeUtil.addDays((Timestamp)today, (int)product_po.getDeliveryTime_Promised());
            MOrder order = (MOrder)orders.get(C_BPartner_ID);
            if (order == null) {
                order = new MOrder(this.getCtx(), 0, this.get_TrxName());
                MBPartner vendor = MBPartner.get((Properties)this.getCtx(), (int)C_BPartner_ID);
                order.setAD_Org_ID(this.getAD_Org_ID());
                order.setBPartner(vendor);
                order.setIsSOTrx(false);
                order.setC_DocTypeTarget_ID();
                order.setDatePromised(datePromised);
                order.setDescription(String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"PP_Order_ID")) + ":" + this.getPP_Order().getDocumentNo());
                order.setDocStatus("DR");
                order.setDocAction("CO");
                order.setAD_User_ID(this.getAD_User_ID());
                order.setM_Warehouse_ID(this.getM_Warehouse_ID());
                order.saveEx(this.get_TrxName());
                this.addDescription(String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"C_Order_ID")) + ": " + order.getDocumentNo());
                orders.put(C_BPartner_ID, order);
                msg = String.valueOf(msg) + Msg.translate((Properties)this.getCtx(), (String)"C_Order_ID") + " : " + order.getDocumentNo() + " - " + Msg.translate((Properties)this.getCtx(), (String)"C_BPartner_ID") + " : " + vendor.getName() + " , ";
            }
            BigDecimal QtyOrdered = this.getMovementQty().multiply(subcontract.getQty());
            if (product_po.getOrder_Min().signum() > 0) {
                QtyOrdered = QtyOrdered.max(product_po.getOrder_Min());
            }
            if (product_po.getOrder_Pack().signum() > 0 && QtyOrdered.signum() > 0) {
                QtyOrdered = product_po.getOrder_Pack().multiply(QtyOrdered.divide(product_po.getOrder_Pack(), 0, 0));
            }
            MOrderLine oline = new MOrderLine(order);
            oline.setM_Product_ID(product.getM_Product_ID());
            oline.setDescription(activity.getDescription());
            oline.setM_Warehouse_ID(this.getM_Warehouse_ID());
            oline.setQty(QtyOrdered);
            oline.setPP_Cost_Collector_ID(this.get_ID());
            oline.setDatePromised(datePromised);
            oline.saveEx(this.get_TrxName());
            this.setProcessed(true);
        }
        return msg;
    }

    public MProduct getM_Product() {
        return MProduct.get((Properties)this.getCtx(), (int)this.getM_Product_ID());
    }

    @Override
    public I_C_UOM getC_UOM() {
        return MUOM.get((Properties)this.getCtx(), (int)this.getC_UOM_ID());
    }

    public boolean isIssue() {
        if (!this.isCostCollectorType("110")) {
            if (!this.isCostCollectorType("130") || this.getPP_Order_BOMLine_ID() <= 0) {
                if (!this.isCostCollectorType("150") || this.getPP_Order_BOMLine_ID() <= 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isReceipt() {
        return this.isCostCollectorType("100");
    }

    public boolean isActivityControl() {
        return this.isCostCollectorType("160");
    }

    public boolean isVariance() {
        return this.isCostCollectorType("130", "120", "140", "150");
    }

    public String getMovementType() {
        if (this.isReceipt()) {
            return "W+";
        }
        if (this.isIssue()) {
            return "W-";
        }
        return null;
    }

    public boolean isCostCollectorType(String ... types) {
        String type = this.getCostCollectorType();
        String[] arrstring = types;
        int n = types.length;
        for (int i = 0; i < n; ++i) {
            String t = arrstring[i];
            if (!type.equals(t)) continue;
            return true;
        }
        return false;
    }

    public boolean isFloorStock() {
        boolean isFloorStock = new Query(this.getCtx(), "PP_Order_BOMLine", "PP_Order_BOMLine_ID=? AND IssueMethod=?", this.get_TrxName()).setOnlyActiveRecords(true).setParameters(new Object[]{this.getPP_Order_BOMLine_ID(), "2"}).match();
        return isFloorStock;
    }

    public void setIsSubcontracting(int PP_Order_Node_ID) {
        this.setIsSubcontracting(MPPOrderNode.get(this.getCtx(), PP_Order_Node_ID, this.get_TrxName()).isSubcontracting());
    }
}

