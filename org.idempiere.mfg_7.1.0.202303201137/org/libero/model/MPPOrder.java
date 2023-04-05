/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.adempiere.exceptions.DocTypeNotFoundException
 *  org.compiere.model.I_AD_WF_Node
 *  org.compiere.model.I_AD_Workflow
 *  org.compiere.model.I_M_CostElement
 *  org.compiere.model.MAcctSchema
 *  org.compiere.model.MClient
 *  org.compiere.model.MCost
 *  org.compiere.model.MCostElement
 *  org.compiere.model.MDocType
 *  org.compiere.model.MForecastLine
 *  org.compiere.model.MLocator
 *  org.compiere.model.MOrderLine
 *  org.compiere.model.MProduct
 *  org.compiere.model.MProject
 *  org.compiere.model.MResource
 *  org.compiere.model.MStorageOnHand
 *  org.compiere.model.MTable
 *  org.compiere.model.MUOM
 *  org.compiere.model.MWarehouse
 *  org.compiere.model.ModelValidationEngine
 *  org.compiere.model.PO
 *  org.compiere.model.POResultSet
 *  org.compiere.model.Query
 *  org.compiere.print.ReportEngine
 *  org.compiere.process.DocAction
 *  org.compiere.process.DocOptions
 *  org.compiere.process.DocumentEngine
 *  org.compiere.util.CLogger
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.compiere.util.KeyNamePair
 *  org.compiere.util.Msg
 *  org.compiere.wf.MWFNode
 *  org.compiere.wf.MWFNodeNext
 *  org.compiere.wf.MWorkflow
 *  org.eevolution.model.I_PP_Order
 *  org.eevolution.model.I_PP_Product_BOM
 *  org.eevolution.model.MPPProductBOM
 *  org.eevolution.model.MPPProductBOMLine
 *  org.eevolution.model.X_PP_Order
 */
package org.libero.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DocTypeNotFoundException;
import org.adempiere.model.engines.CostDimension;
import org.adempiere.model.engines.CostEngine;
import org.adempiere.model.engines.CostEngineFactory;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MCost;
import org.compiere.model.MCostElement;
import org.compiere.model.MDocType;
import org.compiere.model.MForecastLine;
import org.compiere.model.MLocator;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MProject;
import org.compiere.model.MResource;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MTable;
import org.compiere.model.MUOM;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.PO;
import org.compiere.model.POResultSet;
import org.compiere.model.Query;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.wf.MWFNode;
import org.compiere.wf.MWFNodeNext;
import org.compiere.wf.MWorkflow;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.eevolution.model.X_PP_Order;
import org.libero.exceptions.BOMExpiredException;
import org.libero.exceptions.RoutingExpiredException;
import org.libero.model.MPPCostCollector;
import org.libero.model.MPPMRP;
import org.libero.model.MPPOrderBOM;
import org.libero.model.MPPOrderBOMLine;
import org.libero.model.MPPOrderCost;
import org.libero.model.MPPOrderNode;
import org.libero.model.MPPOrderNodeAsset;
import org.libero.model.MPPOrderNodeNext;
import org.libero.model.MPPOrderNodeProduct;
import org.libero.model.MPPOrderWorkflow;
import org.libero.model.MPPWFNodeAsset;
import org.libero.model.MPPWFNodeProduct;
import org.libero.model.MQMSpecification;
import org.libero.model.MRequisition;
import org.libero.model.RoutingService;
import org.libero.model.RoutingServiceFactory;
import org.libero.tables.I_PP_Order_BOMLine;
import org.libero.tables.I_PP_Order_Node;

public class MPPOrder
extends X_PP_Order
implements DocAction,
DocOptions {
    private static final long serialVersionUID = 1L;
    private static CLogger log = CLogger.getCLogger(MPPOrder.class);
    private MPPOrderBOMLine[] m_lines = null;
    private String m_processMsg = null;
    private boolean m_justPrepared = false;
    private MPPOrderWorkflow m_PP_Order_Workflow = null;
    public static int count_MR;
    private BigDecimal costvarian = BigDecimal.ZERO;
    private Collection<MCostElement> m_costElements = null;
    private RoutingService m_routingService = null;

    public static MPPOrder forC_OrderLine_ID(Properties ctx, int C_OrderLine_ID, String trxName) {
        MOrderLine line = new MOrderLine(ctx, C_OrderLine_ID, trxName);
        return (MPPOrder)new Query(ctx, "PP_Order", "C_OrderLine_ID=? AND M_Product_ID=?", trxName).setParameters(new Object[]{C_OrderLine_ID, line.getM_Product_ID()}).firstOnly();
    }

    public static MPPOrder forM_ForecastLine_ID(Properties ctx, int C_OrderLine_ID, String trxName) {
        MForecastLine line = new MForecastLine(ctx, C_OrderLine_ID, trxName);
        return (MPPOrder)new Query(ctx, "PP_Order", "M_ForecastLine_ID=? AND M_Product_ID=?", trxName).setParameters(new Object[]{C_OrderLine_ID, line.getM_Product_ID()}).firstOnly();
    }

    public static void updateQtyBatchs(Properties ctx, I_PP_Order order, boolean override) {
        BigDecimal qtyBatchSize = order.getQtyBatchSize();
        if (qtyBatchSize.signum() == 0 || override) {
            int AD_Workflow_ID = order.getAD_Workflow_ID();
            if (AD_Workflow_ID <= 0) {
                return;
            }
            MWorkflow wf = MWorkflow.get((Properties)ctx, (int)AD_Workflow_ID);
            qtyBatchSize = wf.getQtyBatchSize().setScale(0, RoundingMode.UP);
            order.setQtyBatchSize(qtyBatchSize);
        }
        BigDecimal QtyBatchs = qtyBatchSize.signum() == 0 ? Env.ONE : order.getQtyOrdered().divide(qtyBatchSize, 0, 0);
        order.setQtyBatchs(QtyBatchs);
    }

    public static boolean isQtyAvailable(MPPOrder order, ArrayList[][] issue, Timestamp minGuaranteeDate) {
        boolean isCompleteQtyDeliver = false;
        for (int i = 0; i < issue.length; ++i) {
            MStorageOnHand storage;
            int n;
            int n2;
            MStorageOnHand[] arrmStorageOnHand;
            int PP_Order_BOMLine_ID;
            KeyNamePair key = (KeyNamePair)issue[i][0].get(0);
            boolean isSelected = key.getName().equals("Y");
            if (key == null || !isSelected) continue;
            String value = (String)issue[i][0].get(2);
            KeyNamePair productkey = (KeyNamePair)issue[i][0].get(3);
            int M_Product_ID = productkey.getKey();
            BigDecimal qtyToDeliver = (BigDecimal)issue[i][0].get(4);
            BigDecimal qtyScrapComponent = (BigDecimal)issue[i][0].get(5);
            MProduct product = MProduct.get((Properties)order.getCtx(), (int)M_Product_ID);
            if (product == null || !product.isStocked()) continue;
            int M_AttributeSetInstance_ID = 0;
            if (value == null && isSelected) {
                M_AttributeSetInstance_ID = key.getKey();
            } else if (value != null && isSelected && (PP_Order_BOMLine_ID = Integer.valueOf(key.getKey()).intValue()) > 0) {
                MPPOrderBOMLine orderBOMLine = new MPPOrderBOMLine(order.getCtx(), PP_Order_BOMLine_ID, order.get_TrxName());
                M_AttributeSetInstance_ID = orderBOMLine.getM_AttributeSetInstance_ID();
            }
            MStorageOnHand[] storages = MPPOrder.getStorages(order.getCtx(), M_Product_ID, order.getM_Warehouse_ID(), M_AttributeSetInstance_ID, minGuaranteeDate, order.get_TrxName());
            if (M_AttributeSetInstance_ID == 0) {
                BigDecimal toIssue = qtyToDeliver.add(qtyScrapComponent);
                arrmStorageOnHand = storages;
                n2 = storages.length;
                for (n = 0; n < n2; ++n) {
                    BigDecimal issueActual;
                    storage = arrmStorageOnHand[n];
                    if (storage.getQtyOnHand().signum() == 0 || (toIssue = toIssue.subtract(issueActual = toIssue.min(storage.getQtyOnHand()))).signum() > 0) {
                        continue;
                    }
                    break;
                }
            } else {
                BigDecimal qtydelivered = qtyToDeliver;
                qtydelivered.setScale(4, 4);
                qtydelivered = Env.ZERO;
            }
            BigDecimal onHand = Env.ZERO;
            arrmStorageOnHand = storages;
            n2 = storages.length;
            for (n = 0; n < n2; ++n) {
                storage = arrmStorageOnHand[n];
                onHand = onHand.add(storage.getQtyOnHand());
            }
            boolean bl = isCompleteQtyDeliver = onHand.compareTo(qtyToDeliver.add(qtyScrapComponent)) >= 0;
            if (!isCompleteQtyDeliver) break;
        }
        return isCompleteQtyDeliver;
    }

    public static MStorageOnHand[] getStorages(Properties ctx, int M_Product_ID, int M_Warehouse_ID, int M_ASI_ID, Timestamp minGuaranteeDate, String trxName) {
        MProduct product = MProduct.get((Properties)ctx, (int)M_Product_ID);
        if (product != null && product.isStocked()) {
            if (product.getM_AttributeSetInstance_ID() == 0) {
                String MMPolicy = product.getMMPolicy();
                return MStorageOnHand.getWarehouse((Properties)ctx, (int)M_Warehouse_ID, (int)M_Product_ID, (int)M_ASI_ID, (Timestamp)minGuaranteeDate, (boolean)"F".equals(MMPolicy), (boolean)true, (int)0, (String)trxName);
            }
            String MMPolicy = product.getMMPolicy();
            return MStorageOnHand.getWarehouse((Properties)ctx, (int)M_Warehouse_ID, (int)M_Product_ID, (int)0, (Timestamp)minGuaranteeDate, (boolean)"F".equals(MMPolicy), (boolean)true, (int)0, (String)trxName);
        }
        return new MStorageOnHand[0];
    }

    public MPPOrder(Properties ctx, int PP_Order_ID, String trxName) {
        super(ctx, PP_Order_ID, trxName);
        if (PP_Order_ID == 0) {
            this.setDefault();
        }
    }

    public MPPOrder(MProject project, int PP_Product_BOM_ID, int AD_Workflow_ID) {
        this(project.getCtx(), 0, project.get_TrxName());
        this.setAD_Client_ID(project.getAD_Client_ID());
        this.setAD_Org_ID(project.getAD_Org_ID());
        this.setC_Campaign_ID(project.getC_Campaign_ID());
        this.setC_Project_ID(project.getC_Project_ID());
        this.setDescription(project.getName());
        this.setLine(10);
        this.setPriorityRule("5");
        if (project.getDateContract() == null) {
            throw new IllegalStateException("Date Contract is mandatory for Manufacturing Order.");
        }
        if (project.getDateFinish() == null) {
            throw new IllegalStateException("Date Finish is mandatory for Manufacturing Order.");
        }
        Timestamp ts = project.getDateContract();
        Timestamp df = project.getDateContract();
        if (ts != null) {
            this.setDateOrdered(ts);
        }
        if (ts != null) {
            this.setDateStartSchedule(ts);
        }
        ts = project.getDateFinish();
        if (df != null) {
            this.setDatePromised(df);
        }
        this.setM_Warehouse_ID(project.getM_Warehouse_ID());
        this.setPP_Product_BOM_ID(PP_Product_BOM_ID);
        this.setAD_Workflow_ID(AD_Workflow_ID);
        this.setQtyEntered(Env.ONE);
        this.setQtyOrdered(Env.ONE);
        MPPProductBOM bom = new MPPProductBOM(project.getCtx(), PP_Product_BOM_ID, project.get_TrxName());
        MProduct product = MProduct.get((Properties)project.getCtx(), (int)bom.getM_Product_ID());
        this.setC_UOM_ID(product.getC_UOM_ID());
        this.setM_Product_ID(bom.getM_Product_ID());
        String where = "IsManufacturingResource = 'Y' AND ManufacturingResourceType = 'PT' AND M_Warehouse_ID = " + project.getM_Warehouse_ID();
        MResource resoruce = (MResource)MTable.get((Properties)project.getCtx(), (int)487).getPO(where, project.get_TrxName());
        if (resoruce == null) {
            throw new IllegalStateException("Resource is mandatory.");
        }
        this.setS_Resource_ID(resoruce.getS_Resource_ID());
    }

    public MPPOrder(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    public BigDecimal getQtyOpen() {
        return this.getQtyOrdered().subtract(this.getQtyDelivered()).subtract(this.getQtyScrap());
    }

    public MPPOrderBOMLine[] getLines(boolean requery) {
        if (this.m_lines != null && !requery) {
            MPPOrder.set_TrxName((PO[])this.m_lines, (String)this.get_TrxName());
            return this.m_lines;
        }
        String whereClause = "PP_Order_ID=?";
        List list = new Query(this.getCtx(), "PP_Order_BOMLine", whereClause, this.get_TrxName()).setParameters(new Object[]{this.getPP_Order_ID()}).setOrderBy("Line").list();
        this.m_lines = list.toArray(new MPPOrderBOMLine[list.size()]);
        return this.m_lines;
    }

    public MPPOrderBOMLine[] getLines() {
        return this.getLines(true);
    }

    public void setC_DocTypeTarget_ID(String docBaseType) {
        if (this.getC_DocTypeTarget_ID() > 0) {
            return;
        }
        MDocType[] doc = MDocType.getOfDocBaseType((Properties)this.getCtx(), (String)docBaseType);
        if (doc == null) {
            throw new DocTypeNotFoundException(docBaseType, "");
        }
        this.setC_DocTypeTarget_ID(doc[0].get_ID());
    }

    public void setProcessed(boolean processed) {
        super.setProcessed(processed);
        if (this.get_ID() <= 0) {
            return;
        }
        DB.executeUpdateEx((String)"UPDATE PP_Order SET Processed=? WHERE PP_Order_ID=?", (Object[])new Object[]{processed, this.get_ID()}, (String)this.get_TrxName());
    }

    protected boolean beforeSave(boolean newRecord) {
        int ii;
        if (this.getAD_Client_ID() == 0) {
            this.m_processMsg = "AD_Client_ID = 0";
            return false;
        }
        if (this.getAD_Org_ID() == 0) {
            int context_AD_Org_ID = Env.getAD_Org_ID((Properties)this.getCtx());
            if (context_AD_Org_ID == 0) {
                this.m_processMsg = "AD_Org_ID = 0";
                return false;
            }
            this.setAD_Org_ID(context_AD_Org_ID);
            log.warning("beforeSave - Changed Org to Context=" + context_AD_Org_ID);
        }
        if (this.getM_Warehouse_ID() == 0 && (ii = Env.getContextAsInt((Properties)this.getCtx(), (String)"#M_Warehouse_ID")) != 0) {
            this.setM_Warehouse_ID(ii);
        }
        if (this.getC_UOM_ID() <= 0 && this.getM_Product_ID() > 0) {
            this.setC_UOM_ID(this.getM_Product().getC_UOM_ID());
        }
        if (this.getDateFinishSchedule() == null) {
            this.setDateFinishSchedule(this.getDatePromised());
        }
        MPPOrder.updateQtyBatchs(this.getCtx(), (I_PP_Order)this, false);
        return true;
    }

    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) {
            return false;
        }
        if ("CL".equals(this.getDocAction()) || "VO".equals(this.getDocAction())) {
            return true;
        }
        if (this.is_ValueChanged("QtyEntered") && !this.isDelivered()) {
            this.deleteWorkflowAndBOM();
            this.explosion();
        }
        if (this.is_ValueChanged("QtyEntered") && this.isDelivered()) {
            throw new AdempiereException("Cannot Change Quantity, Only for Draft or In-Progess Status");
        }
        if (!newRecord) {
            return success;
        }
        this.explosion();
        return true;
    }

    protected boolean beforeDelete() {
        if (this.getDocStatus().equals("DR") || this.getDocStatus().equals("IP")) {
            String whereClause = "PP_Order_ID=? AND AD_Client_ID=?";
            Object[] params = new Object[]{this.get_ID(), this.getAD_Client_ID()};
            this.deletePO("PP_Order_Cost", whereClause, params);
            this.deleteWorkflowAndBOM();
        }
        this.setQtyOrdered(Env.ZERO);
        this.orderStock();
        return true;
    }

    private void deleteWorkflowAndBOM() {
        if (this.get_ID() <= 0) {
            return;
        }
        String whereClause = "PP_Order_ID=? AND AD_Client_ID=?";
        Object[] params = new Object[]{this.get_ID(), this.getAD_Client_ID()};
        DB.executeUpdateEx((String)("UPDATE PP_Order_Workflow SET PP_Order_Node_ID=NULL WHERE " + whereClause), (Object[])params, (String)this.get_TrxName());
        this.deletePO("PP_Order_Node_Asset", whereClause, params);
        this.deletePO("PP_Order_Node_Product", whereClause, params);
        this.deletePO("PP_Order_NodeNext", whereClause, params);
        this.deletePO("PP_Order_Node", whereClause, params);
        this.deletePO("PP_Order_Workflow", whereClause, params);
        this.deletePO("PP_Order_BOMLine", whereClause, params);
        this.deletePO("PP_Order_BOM", whereClause, params);
    }

    public boolean processIt(String processAction) {
        this.m_processMsg = null;
        DocumentEngine engine = new DocumentEngine((DocAction)this, this.getDocStatus());
        return engine.processIt(processAction, this.getDocAction());
    }

    public boolean unlockIt() {
        log.info(this.toString());
        this.setProcessing(false);
        return true;
    }

    public boolean invalidateIt() {
        log.info(this.toString());
        this.setDocAction("PR");
        return true;
    }

    public String prepareIt() {
        int maxLowLevel;
        String docBaseType;
        log.info(this.toString());
        this.m_processMsg = ModelValidationEngine.get().fireDocValidate((PO)this, 1);
        if (this.m_processMsg != null) {
            return "IN";
        }
        MPPOrderBOMLine[] lines = this.getLines(true);
        if (lines.length == 0) {
            this.m_processMsg = "@NoLines@";
            return "IN";
        }
        if (this.getC_DocType_ID() != 0) {
            for (int i = 0; i < lines.length; ++i) {
                if (lines[i].getM_Warehouse_ID() == this.getM_Warehouse_ID()) continue;
                log.warning("different Warehouse " + lines[i]);
                this.m_processMsg = "@CannotChangeDocType@";
                return "IN";
            }
        }
        if ("DR".equals(this.getDocStatus()) || "IP".equals(this.getDocStatus()) || "IN".equals(this.getDocStatus()) || this.getC_DocType_ID() == 0) {
            this.setC_DocType_ID(this.getC_DocTypeTarget_ID());
        }
        if (!"MQO".equals(docBaseType = MDocType.get((Properties)this.getCtx(), (int)this.getC_DocType_ID()).getDocBaseType())) {
            this.reserveStock(lines);
        }
        this.m_processMsg = ModelValidationEngine.get().fireDocValidate((PO)this, 8);
        if (this.m_processMsg != null) {
            return "IN";
        }
        this.resetCostsLLForLLC0();
        for (int lowLevel = maxLowLevel = MPPMRP.getMaxLowLevel(this.getCtx(), this.get_TrxName()); lowLevel >= 0; --lowLevel) {
            for (MProduct product : this.getProducts(lowLevel)) {
                MPPProductBOM bom = MPPProductBOM.get((Properties)this.getCtx(), (int)this.getPP_Product_BOM_ID());
                this.rollup(product, bom);
            }
        }
        for (MProduct product : this.getProductsResource()) {
            MWorkflow workflow = new MWorkflow(this.getCtx(), this.getAD_Workflow_ID(), this.get_TrxName());
            this.rollup(product, workflow);
        }
        this.m_justPrepared = true;
        return "IP";
    }

    private void orderStock() {
        MProduct product = this.getM_Product();
        if (!product.isStocked()) {
            return;
        }
        BigDecimal target = this.getQtyOrdered();
        BigDecimal difference = target.subtract(this.getQtyReserved()).subtract(this.getQtyDelivered());
        if (difference.signum() == 0) {
            return;
        }
        BigDecimal ordered = difference;
        int M_Locator_ID = this.getM_Locator_ID(ordered, product);
        if ("CL".equals(this.getDocAction()) ? !MStorageOnHand.add((Properties)this.getCtx(), (int)this.getM_Warehouse_ID(), (int)M_Locator_ID, (int)this.getM_Product_ID(), (int)this.getM_AttributeSetInstance_ID(), (BigDecimal)ordered, (String)this.get_TrxName()) : !MStorageOnHand.add((Properties)this.getCtx(), (int)this.getM_Warehouse_ID(), (int)M_Locator_ID, (int)this.getM_Product_ID(), (int)this.getM_AttributeSetInstance_ID(), (BigDecimal)ordered, (String)this.get_TrxName())) {
            throw new AdempiereException();
        }
        this.setQtyReserved(this.getQtyReserved().add(difference));
    }

    private void reserveStock(MPPOrderBOMLine[] lines) {
        MPPOrderBOMLine[] arrmPPOrderBOMLine = lines;
        int n = lines.length;
        for (int i = 0; i < n; ++i) {
            MPPOrderBOMLine line = arrmPPOrderBOMLine[i];
            line.reserveStock();
            line.saveEx(this.get_TrxName());
        }
    }

    public boolean approveIt() {
        log.info("approveIt - " + this.toString());
        MDocType doc = MDocType.get((Properties)this.getCtx(), (int)this.getC_DocType_ID());
        if (doc.getDocBaseType().equals("MQO")) {
            String whereClause = "PP_Product_BOM_ID=? AND AD_Workflow_ID=?";
            MQMSpecification qms = (MQMSpecification)new Query(this.getCtx(), "QM_Specification", whereClause, this.get_TrxName()).setParameters(new Object[]{this.getPP_Product_BOM_ID(), this.getAD_Workflow_ID()}).firstOnly();
            return qms != null ? qms.isValid(this.getM_AttributeSetInstance_ID()) : true;
        }
        this.setIsApproved(true);
        return true;
    }

    public boolean rejectIt() {
        log.info("rejectIt - " + this.toString());
        this.setIsApproved(false);
        return true;
    }

    public String completeIt() {
        String status;
        if ("PR".equals(this.getDocAction())) {
            this.setProcessed(false);
            return "IP";
        }
        if (!this.m_justPrepared && !"IP".equals(status = this.prepareIt())) {
            return status;
        }
        this.m_processMsg = ModelValidationEngine.get().fireDocValidate((PO)this, 7);
        if (this.m_processMsg != null) {
            return "IN";
        }
        if (!this.isApproved()) {
            this.approveIt();
        }
        this.createStandardCosts();
        this.createStandardFifo();
        this.autoReportActivities();
        this.setDocAction("CL");
        String valid = ModelValidationEngine.get().fireDocValidate((PO)this, 9);
        if (valid != null) {
            this.m_processMsg = valid;
            return "IN";
        }
        return "CO";
    }

    public boolean isAvailable() {
        String whereClause = "QtyOnHand >= QtyRequired AND PP_Order_ID=?";
        boolean available = new Query(this.getCtx(), "RV_PP_Order_Storage", whereClause, this.get_TrxName()).setParameters(new Object[]{this.get_ID()}).match();
        return available;
    }

    public boolean voidIt() {
        log.info(this.toString());
        this.m_processMsg = ModelValidationEngine.get().fireDocValidate((PO)this, 2);
        if (this.m_processMsg != null) {
            return false;
        }
        if (this.isDelivered()) {
            throw new AdempiereException("Cannot void this document because exist transactions");
        }
        for (MPPOrderBOMLine line : this.getLines()) {
            BigDecimal old = line.getQtyRequired();
            if (old.signum() == 0) continue;
            line.addDescription(Msg.parseTranslation((Properties)this.getCtx(), (String)("@Voided@ @QtyRequired@ : (" + old + ")")));
            line.setQtyRequired(Env.ZERO);
            line.saveEx(this.get_TrxName());
        }
        this.getMPPOrderWorkflow().voidActivities();
        BigDecimal old = this.getQtyOrdered();
        if (old.signum() != 0) {
            this.addDescription(Msg.parseTranslation((Properties)this.getCtx(), (String)("@Voided@ @QtyOrdered@ : (" + old + ")")));
            this.setQtyOrdered(Env.ZERO);
            this.setQtyEntered(Env.ZERO);
            this.saveEx(this.get_TrxName());
        }
        this.orderStock();
        this.reserveStock(this.getLines());
        this.m_processMsg = ModelValidationEngine.get().fireDocValidate((PO)this, 10);
        if (this.m_processMsg != null) {
            return false;
        }
        this.setDocAction("--");
        return true;
    }

    public boolean closeIt() {
        log.info(this.toString());
        this.m_processMsg = ModelValidationEngine.get().fireDocValidate((PO)this, 3);
        if (this.m_processMsg != null) {
            return false;
        }
        if ("CL".equals(this.getDocStatus())) {
            return true;
        }
        if (!"CO".equals(this.getDocStatus())) {
            String DocStatus = this.completeIt();
            this.setDocStatus(DocStatus);
            this.setDocAction("--");
        }
        if (!this.isDelivered()) {
            throw new AdempiereException("Cannot close this document because do not exist transactions");
        }
        this.createVariances();
        for (MPPOrderBOMLine line : this.getLines()) {
            BigDecimal old = line.getQtyRequired();
            if (old.compareTo(line.getQtyDelivered()) == 0) continue;
            line.setQtyRequired(line.getQtyDelivered());
            line.addDescription(Msg.parseTranslation((Properties)this.getCtx(), (String)("@closed@ @QtyRequired@ (" + old + ")")));
            line.saveEx(this.get_TrxName());
        }
        MPPOrderWorkflow m_order_wf = this.getMPPOrderWorkflow();
        m_order_wf.closeActivities(m_order_wf.getLastNode(this.getAD_Client_ID()), this.getUpdated(), false);
        BigDecimal old = this.getQtyOrdered();
        if (old.signum() != 0) {
            this.addDescription(Msg.parseTranslation((Properties)this.getCtx(), (String)("@closed@ @QtyOrdered@ : (" + old + ")")));
            this.setQtyOrdered(this.getQtyDelivered());
            this.saveEx(this.get_TrxName());
        }
        this.orderStock();
        this.reserveStock(this.getLines());
        this.setDocStatus("CL");
        this.setDocAction("--");
        this.m_processMsg = ModelValidationEngine.get().fireDocValidate((PO)this, 11);
        return this.m_processMsg == null;
    }

    public boolean reverseCorrectIt() {
        log.info("reverseCorrectIt - " + this.toString());
        return this.voidIt();
    }

    public boolean reverseAccrualIt() {
        log.info("reverseAccrualIt - " + this.toString());
        return false;
    }

    public boolean reActivateIt() {
        this.m_processMsg = ModelValidationEngine.get().fireDocValidate((PO)this, 12);
        if (this.m_processMsg != null) {
            return false;
        }
        if (this.isDelivered()) {
            throw new AdempiereException("Cannot re activate this document because exist transactions");
        }
        this.setDocAction("CO");
        this.setProcessed(false);
        return true;
    }

    public int getDoc_User_ID() {
        return this.getPlanner_ID();
    }

    public BigDecimal getApprovalAmt() {
        return Env.ZERO;
    }

    public int getC_Currency_ID() {
        return 0;
    }

    public String getProcessMsg() {
        return this.m_processMsg;
    }

    public String getSummary() {
        return this.getDocumentNo() + "/" + this.getDatePromised();
    }

    public File createPDF() {
        try {
            File temp = File.createTempFile(String.valueOf(this.get_TableName()) + this.get_ID() + "_", ".pdf");
            return this.createPDF(temp);
        }
        catch (Exception e) {
            log.severe("Could not create PDF - " + e.getMessage());
            return null;
        }
    }

    public File createPDF(File file) {
        ReportEngine re = ReportEngine.get((Properties)this.getCtx(), (int)8, (int)this.getPP_Order_ID());
        if (re == null) {
            return null;
        }
        return re.getPDF(file);
    }

    public String getDocumentInfo() {
        MDocType dt = MDocType.get((Properties)this.getCtx(), (int)this.getC_DocType_ID());
        return String.valueOf(dt.getName()) + " " + this.getDocumentNo();
    }

    private void deletePO(String tableName, String whereClause, Object[] params) {
        try (POResultSet rs = new Query(this.getCtx(), tableName, whereClause, this.get_TrxName()).setParameters(params).scroll();){
            while (rs.hasNext()) {
                rs.next().deleteEx(true);
            }
        }
    }

    public void setQty(BigDecimal Qty) {
        super.setQtyEntered(Qty);
        super.setQtyOrdered(this.getQtyEntered());
    }

    public void setQtyEntered(BigDecimal QtyEntered) {
        if (QtyEntered != null && this.getC_UOM_ID() != 0) {
            int precision = MUOM.getPrecision((Properties)this.getCtx(), (int)this.getC_UOM_ID());
            QtyEntered = QtyEntered.setScale(precision, 4);
        }
        super.setQtyEntered(QtyEntered);
    }

    public void setQtyOrdered(BigDecimal QtyOrdered) {
        if (QtyOrdered != null) {
            int precision = this.getM_Product().getUOMPrecision();
            QtyOrdered = QtyOrdered.setScale(precision, 4);
        }
        super.setQtyOrdered(QtyOrdered);
    }

    public MProduct getM_Product() {
        return MProduct.get((Properties)this.getCtx(), (int)this.getM_Product_ID());
    }

    public MPPOrderBOM getMPPOrderBOM() {
        return (MPPOrderBOM)new Query(this.getCtx(), "PP_Order_BOM", "PP_Order_ID=?", this.get_TrxName()).setParameters(new Object[]{this.getPP_Order_ID()}).firstOnly();
    }

    public MPPOrderWorkflow getMPPOrderWorkflow() {
        if (this.m_PP_Order_Workflow != null) {
            return this.m_PP_Order_Workflow;
        }
        this.m_PP_Order_Workflow = (MPPOrderWorkflow)new Query(this.getCtx(), "PP_Order_Workflow", "PP_Order_ID=?", this.get_TrxName()).setParameters(new Object[]{this.getPP_Order_ID()}).firstOnly();
        return this.m_PP_Order_Workflow;
    }

    private void explosion() {
        MPPOrderWorkflow PP_Order_Workflow;
        MPPProductBOM PP_Product_BOM = MPPProductBOM.get((Properties)this.getCtx(), (int)this.getPP_Product_BOM_ID());
        if (this.getM_Product_ID() != PP_Product_BOM.getM_Product_ID()) {
            throw new AdempiereException("@NotMatch@ @PP_Product_BOM_ID@ , @M_Product_ID@");
        }
        MProduct product = MProduct.get((Properties)this.getCtx(), (int)PP_Product_BOM.getM_Product_ID());
        if (!product.isVerified()) {
            throw new AdempiereException("Product BOM Configuration not verified. Please verify the product first - " + product.getValue());
        }
        if (!PP_Product_BOM.isValidFromTo(this.getDateStartSchedule())) {
            throw new BOMExpiredException((I_PP_Product_BOM)PP_Product_BOM, this.getDateStartSchedule());
        }
        MPPOrderBOM PP_Order_BOM = new MPPOrderBOM(PP_Product_BOM, this.getPP_Order_ID(), this.get_TrxName());
        PP_Order_BOM.setAD_Org_ID(this.getAD_Org_ID());
        PP_Order_BOM.saveEx(this.get_TrxName());
        this.expandBOM(PP_Product_BOM, PP_Order_BOM, this.getQtyOrdered());
        MWorkflow AD_Workflow = MWorkflow.get((Properties)this.getCtx(), (int)this.getAD_Workflow_ID());
        if (!AD_Workflow.isValid()) {
            throw new AdempiereException("Routing is not valid. Please validate it first - " + AD_Workflow.getValue());
        }
        if (AD_Workflow.isValidFromTo(this.getDateStartSchedule())) {
            PP_Order_Workflow = new MPPOrderWorkflow(AD_Workflow, this.get_ID(), this.get_TrxName());
            PP_Order_Workflow.setAD_Org_ID(this.getAD_Org_ID());
            PP_Order_Workflow.saveEx(this.get_TrxName());
            for (MWFNode mWFNode : AD_Workflow.getNodes(false, this.getAD_Client_ID())) {
                if (!mWFNode.isValidFromTo(this.getDateStartSchedule())) continue;
                MPPOrderNode PP_Order_Node = new MPPOrderNode(mWFNode, PP_Order_Workflow, this.getQtyOrdered(), this.get_TrxName());
                PP_Order_Node.setAD_Org_ID(this.getAD_Org_ID());
                PP_Order_Node.saveEx(this.get_TrxName());
                for (MWFNodeNext AD_WF_NodeNext : mWFNode.getTransitions(this.getAD_Client_ID())) {
                    MPPOrderNodeNext nodenext = new MPPOrderNodeNext(AD_WF_NodeNext, PP_Order_Node);
                    nodenext.setAD_Org_ID(this.getAD_Org_ID());
                    nodenext.saveEx(this.get_TrxName());
                }
                for (MPPWFNodeProduct wfnp : MPPWFNodeProduct.forAD_WF_Node_ID(this.getCtx(), mWFNode.get_ID())) {
                    MPPOrderNodeProduct nodeOrderProduct = new MPPOrderNodeProduct(wfnp, PP_Order_Node);
                    nodeOrderProduct.setAD_Org_ID(this.getAD_Org_ID());
                    nodeOrderProduct.saveEx(this.get_TrxName());
                }
                for (MPPWFNodeAsset wfna : MPPWFNodeAsset.forAD_WF_Node_ID(this.getCtx(), mWFNode.get_ID())) {
                    MPPOrderNodeAsset nodeorderasset = new MPPOrderNodeAsset(wfna, PP_Order_Node);
                    nodeorderasset.setAD_Org_ID(this.getAD_Org_ID());
                    nodeorderasset.saveEx(this.get_TrxName());
                }
            }
            PP_Order_Workflow.getNodes(true);
            for (MPPOrderNode mPPOrderNode : PP_Order_Workflow.getNodes(false, this.getAD_Client_ID())) {
                if (PP_Order_Workflow.getAD_WF_Node_ID() == mPPOrderNode.getAD_WF_Node_ID()) {
                    PP_Order_Workflow.setPP_Order_Node_ID(mPPOrderNode.getPP_Order_Node_ID());
                }
                for (MPPOrderNodeNext next : mPPOrderNode.getTransitions(this.getAD_Client_ID())) {
                    next.setPP_Order_Next_ID();
                    next.saveEx(this.get_TrxName());
                }
            }
        } else {
            throw new RoutingExpiredException((I_AD_Workflow)AD_Workflow, this.getDateStartSchedule());
        }
        PP_Order_Workflow.saveEx(this.get_TrxName());
    }

    private void expandBOM(MPPProductBOM PP_Product_BOM, MPPOrderBOM PP_Order_BOM, BigDecimal parentQty) {
        for (MPPProductBOMLine PP_Product_BOMline : PP_Product_BOM.getLines(true)) {
            MProduct cfr_ignored_0 = (MProduct)PP_Product_BOMline.getM_Product();
            if (PP_Product_BOMline.isValidFromTo(this.getDateStartSchedule())) {
                MPPOrderBOMLine obl = new MPPOrderBOMLine(PP_Product_BOMline, this.getPP_Order_ID(), PP_Order_BOM.get_ID(), this.getM_Warehouse_ID(), this.get_TrxName());
                obl.setAD_Org_ID(this.getAD_Org_ID());
                obl.setM_Warehouse_ID(this.getM_Warehouse_ID());
                obl.setM_Locator_ID(this.getM_Locator_ID());
                obl.setQtyPlusScrap(parentQty);
                obl.saveEx(this.get_TrxName());
                continue;
            }
            log.fine("BOM Line skiped - " + (Object)PP_Product_BOMline);
        }
    }

    private void createRequisitionIFpurchased(MProduct product, BigDecimal qtyRequired) {
        if (product.isPurchased()) {
            log.finer("CLASS PPOrder.explosion product is purchased, check stock/creating requisition");
            MPPMRP mrp = (MPPMRP)new Query(Env.getCtx(), "PP_MRP", "PP_Order_ID=?", this.get_TrxName()).setParameters(new Object[]{this.getPP_Order_ID()}).first();
            MRequisition req = new MRequisition(Env.getCtx(), 0, this.get_TrxName());
            req.create(mrp.get_ID(), qtyRequired, product.getM_Product_ID(), this.getC_OrderLine().getC_BPartner_ID(), this.getAD_Org_ID(), this.getPlanner_ID(), this.getDatePromised(), String.valueOf(this.getDescription()) + "!!!", this.getM_Warehouse_ID(), this.getC_DocType_ID());
            ++count_MR;
        }
    }

    public static void createReceipt(MPPOrder order, Timestamp movementDate, BigDecimal qtyDelivered, BigDecimal qtyToDeliver, BigDecimal qtyScrap, BigDecimal qtyReject, int M_Locator_ID, int M_AttributeSetInstance_ID) {
        if (qtyToDeliver.signum() != 0 || qtyScrap.signum() != 0 || qtyReject.signum() != 0) {
            MPPCostCollector.createCollector(order, order.getM_Product_ID(), M_Locator_ID, M_AttributeSetInstance_ID, order.getS_Resource_ID(), 0, 0, MDocType.getDocType((String)"MCC"), "100", movementDate, qtyToDeliver, qtyScrap, qtyReject, 0, Env.ZERO, Env.ZERO, Env.ZERO, Env.ZERO);
        }
        order.setDateDelivered(movementDate);
        if (order.getDateStart() == null) {
            order.setDateStart(movementDate);
        }
        BigDecimal DQ = qtyDelivered;
        BigDecimal SQ = qtyScrap;
        BigDecimal OQ = qtyToDeliver;
        if (DQ.add(SQ).compareTo(OQ) >= 0) {
            order.setDateFinish(movementDate);
        }
        order.saveEx(order.get_TrxName());
    }

    public static void createIssue(MPPOrder order, int PP_OrderBOMLine_ID, Timestamp movementdate, BigDecimal qty, BigDecimal qtyScrap, BigDecimal qtyReject, MStorageOnHand[] storages, boolean forceIssue) {
        if (qty.signum() == 0) {
            return;
        }
        MPPOrderBOMLine PP_orderbomLine = new MPPOrderBOMLine(order.getCtx(), PP_OrderBOMLine_ID, order.get_TrxName());
        BigDecimal toIssue = qty.add(qtyScrap);
        MStorageOnHand[] arrmStorageOnHand = storages;
        int n = storages.length;
        for (int i = 0; i < n; ++i) {
            MStorageOnHand storage = arrmStorageOnHand[i];
            System.out.println("yoyo" + storage.getQtyOnHand().signum());
            if (storage.getQtyOnHand().signum() == 0) continue;
            BigDecimal qtyIssue = toIssue.min(storage.getQtyOnHand());
            System.out.println("safagaStok" + qtyIssue);
            if (qtyIssue.signum() != 0 || qtyScrap.signum() != 0 || qtyReject.signum() != 0) {
                String CostCollectorType = "110";
                System.out.println("yoyo" + PP_orderbomLine.getQtyBatch().signum());
                if (PP_orderbomLine.getQtyBatch().signum() == 0 && PP_orderbomLine.getQtyBOM().signum() == 0) {
                    CostCollectorType = "130";
                } else if (PP_orderbomLine.isComponentType("CP")) {
                    CostCollectorType = "150";
                }
                MPPCostCollector.createCollector(order, PP_orderbomLine.getM_Product_ID(), storage.getM_Locator_ID(), storage.getM_AttributeSetInstance_ID(), order.getS_Resource_ID(), PP_OrderBOMLine_ID, 0, MDocType.getDocType((String)"MCC"), CostCollectorType, movementdate, qtyIssue, qtyScrap, qtyReject, 0, Env.ZERO, Env.ZERO, Env.ZERO, Env.ZERO);
            }
            toIssue = toIssue.subtract(qtyIssue);
            System.out.println("safaga1" + qtyIssue + " ");
            if (toIssue.signum() == 0) break;
        }
        System.out.println("agfagaga" + forceIssue + toIssue);
        if (forceIssue && toIssue.signum() != 0) {
            System.out.println("agfagaga" + forceIssue + toIssue);
            MPPCostCollector.createCollector(order, PP_orderbomLine.getM_Product_ID(), PP_orderbomLine.getM_Locator_ID(), PP_orderbomLine.getM_AttributeSetInstance_ID(), order.getS_Resource_ID(), PP_OrderBOMLine_ID, 0, MDocType.getDocType((String)"MCC"), "110", movementdate, toIssue, Env.ZERO, Env.ZERO, 0, Env.ZERO, Env.ZERO, Env.ZERO, Env.ZERO);
            return;
        }
        if (toIssue.signum() != 0) {
            throw new AdempiereException("Should not happen toIssue=" + toIssue);
        }
    }

    public static boolean isQtyAvailable(MPPOrder order, I_PP_Order_BOMLine line) {
        MProduct product = MProduct.get((Properties)order.getCtx(), (int)line.getM_Product_ID());
        if (product == null || !product.isStocked()) {
            return true;
        }
        BigDecimal qtyToDeliver = line.getQtyRequired();
        BigDecimal qtyScrap = line.getQtyScrap();
        BigDecimal qtyRequired = qtyToDeliver.add(qtyScrap);
        BigDecimal qtyAvailable = MStorageOnHand.getQtyOnHand((int)line.getM_Product_ID(), (int)order.getM_Warehouse_ID(), (int)line.getM_AttributeSetInstance_ID(), (String)order.get_TrxName());
        return qtyAvailable.compareTo(qtyRequired) >= 0;
    }

    public int getM_Locator_ID() {
        MWarehouse wh = MWarehouse.get((Properties)this.getCtx(), (int)this.getM_Warehouse_ID());
        return wh.getDefaultLocator().getM_Locator_ID();
    }

    private int getM_Locator_ID(BigDecimal qty, MProduct product) {
        int M_Locator_ID = 0;
        int M_ASI_ID = this.getM_AttributeSetInstance_ID();
        if (M_ASI_ID != 0) {
            M_Locator_ID = MStorageOnHand.getM_Locator_ID((int)this.getM_Warehouse_ID(), (int)this.getM_Product_ID(), (int)M_ASI_ID, (BigDecimal)qty, (String)this.get_TrxName());
        }
        if (M_Locator_ID == 0) {
            M_Locator_ID = product.getM_Locator_ID() > 0 ? product.getM_Locator_ID() : this.getM_Locator_ID();
        }
        return M_Locator_ID;
    }

    public boolean isDelivered() {
        if (this.getQtyDelivered().signum() > 0 || this.getQtyScrap().signum() > 0 || this.getQtyReject().signum() > 0) {
            return true;
        }
        for (MPPOrderBOMLine mPPOrderBOMLine : this.getLines()) {
            if (mPPOrderBOMLine.getQtyDelivered().signum() <= 0) continue;
            return true;
        }
        for (PO pO : this.getMPPOrderWorkflow().getNodes(true, this.getAD_Client_ID())) {
            if (pO.getQtyDelivered().signum() > 0) {
                return true;
            }
            if (pO.getDurationReal() <= 0) continue;
            return true;
        }
        return false;
    }

    public void setDefault() {
        this.setLine(10);
        this.setPriorityRule("5");
        this.setDescription("");
        this.setQtyDelivered(Env.ZERO);
        this.setQtyReject(Env.ZERO);
        this.setQtyScrap(Env.ZERO);
        this.setIsSelected(false);
        this.setIsSOTrx(false);
        this.setIsApproved(false);
        this.setIsPrinted(false);
        this.setProcessed(false);
        this.setProcessing(false);
        this.setPosted(false);
        this.setC_DocTypeTarget_ID("MOP");
        this.setC_DocType_ID(this.getC_DocTypeTarget_ID());
        this.setDocStatus("DR");
        this.setDocAction("PR");
        this.setC_OrderLine_ID(MPPMRP.C_OrderLine_ID);
    }

    public void addDescription(String description) {
        String desc = this.getDescription();
        if (desc == null) {
            this.setDescription(description);
        } else {
            this.setDescription(String.valueOf(desc) + " | " + description);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("MPPOrder[").append(this.get_ID()).append("-").append(this.getDocumentNo()).append(",IsSOTrx=").append(this.isSOTrx()).append(",C_DocType_ID=").append(this.getC_DocType_ID()).append("]");
        return sb.toString();
    }

    public void autoReportActivities() {
        for (MPPOrderNode activity : this.getMPPOrderWorkflow().getNodes()) {
            if (!activity.isMilestone() || !activity.isSubcontracting() && activity.get_ID() != this.getMPPOrderWorkflow().getPP_Order_Node_ID()) continue;
            MPPCostCollector.createCollector(this, this.getM_Product_ID(), this.getM_Locator_ID(), this.getM_AttributeSetInstance_ID(), this.getS_Resource_ID(), 0, activity.getPP_Order_Node_ID(), MDocType.getDocType((String)"MCC"), "160", this.getUpdated(), activity.getQtyToDeliver(), Env.ZERO, Env.ZERO, 0, Env.ZERO, Env.ZERO, Env.ZERO, Env.ZERO);
        }
    }

    private final void createStandardCosts() {
        MAcctSchema as = MClient.get((Properties)this.getCtx(), (int)this.getAD_Client_ID()).getAcctSchema();
        log.info("Cost_Group_ID" + as.getM_CostType_ID());
        TreeSet<Integer> productsAdded = new TreeSet<Integer>();
        MProduct product = this.getM_Product();
        productsAdded.add(product.getM_Product_ID());
        CostDimension d = new CostDimension(product, as, as.getM_CostType_ID(), this.getAD_Org_ID(), this.getM_AttributeSetInstance_ID(), -10);
        List costs = d.toQuery(MCost.class, this.get_TrxName()).list();
        for (MCost cost : costs) {
            MPPOrderCost PP_Order_Cost = new MPPOrderCost(cost, this.get_ID(), this.get_TrxName());
            PP_Order_Cost.saveEx(this.get_TrxName());
        }
        for (MCost line : this.getLines()) {
            MProduct product2 = line.getM_Product();
            if (productsAdded.contains(product2.getM_Product_ID())) continue;
            productsAdded.add(product2.getM_Product_ID());
            CostDimension d2 = new CostDimension(line.getM_Product(), as, as.getM_CostType_ID(), line.getAD_Org_ID(), line.getM_AttributeSetInstance_ID(), -10);
            List costs2 = d2.toQuery(MCost.class, this.get_TrxName()).list();
            for (MCost cost : costs2) {
                MPPOrderCost PP_Order_Cost = new MPPOrderCost(cost, this.get_ID(), this.get_TrxName());
                PP_Order_Cost.saveEx(this.get_TrxName());
            }
        }
        for (MPPOrderNode node : this.getMPPOrderWorkflow().getNodes(true)) {
            MProduct resourceProduct;
            int S_Resource_ID = node.getS_Resource_ID();
            if (S_Resource_ID <= 0 || productsAdded.contains((resourceProduct = MProduct.forS_Resource_ID((Properties)this.getCtx(), (int)S_Resource_ID, null)).getM_Product_ID())) continue;
            productsAdded.add(resourceProduct.getM_Product_ID());
            CostDimension d3 = new CostDimension(resourceProduct, as, as.getM_CostType_ID(), node.getAD_Org_ID(), 0, -10);
            List costs3 = d3.toQuery(MCost.class, this.get_TrxName()).list();
            for (MCost cost : costs3) {
                MPPOrderCost orderCost = new MPPOrderCost(cost, this.getPP_Order_ID(), this.get_TrxName());
                orderCost.saveEx(this.get_TrxName());
            }
        }
    }

    private final void createStandardFifo() {
        MAcctSchema as = MClient.get((Properties)this.getCtx(), (int)this.getAD_Client_ID()).getAcctSchema();
        log.info("Cost_Group_ID" + as.getM_CostType_ID());
        TreeSet<Integer> productsAdded = new TreeSet<Integer>();
        for (MPPOrderBOMLine line : this.getLines()) {
            MProduct product = line.getM_Product();
            if (productsAdded.contains(product.getM_Product_ID())) continue;
            productsAdded.add(product.getM_Product_ID());
            if (DB.getSQLValue(null, (String)"select m_attributesetinstance_id from m_cost where m_product_id = ? and coalesce(m_attributesetinstance_id,0)>0", (int)product.getM_Product_ID()) <= 0) continue;
            CostDimension d = new CostDimension(line.getM_Product(), as, as.getM_CostType_ID(), line.getAD_Org_ID(), DB.getSQLValue(null, (String)"select m_attributesetinstance_id from m_cost where m_product_id = ? and coalesce(m_attributesetinstance_id,0)>0 ", (int)product.getM_Product_ID()), -10);
            List costs = d.toQuery(MCost.class, this.get_TrxName()).list();
            for (MCost cost : costs) {
                MPPOrderCost PP_Order_Cost = new MPPOrderCost(cost, this.get_ID(), this.get_TrxName());
                PP_Order_Cost.saveEx(this.get_TrxName());
            }
        }
    }

    public void createVariances() {
        for (MPPOrderBOMLine line : this.getLines(true)) {
            this.createUsageVariance(line);
        }
        this.m_lines = null;
        MPPOrderWorkflow orderWorkflow = this.getMPPOrderWorkflow();
        if (orderWorkflow != null) {
            for (MPPOrderNode node : orderWorkflow.getNodes(true)) {
                this.createUsageVariance(node);
                this.createUsageVarianceCost(node);
            }
        }
    }

    private void createUsageVariance(I_PP_Order_BOMLine bomLine) {
        MLocator locator;
        MPPOrder order = this;
        Timestamp movementDate = order.getUpdated();
        MPPOrderBOMLine line = (MPPOrderBOMLine)bomLine;
        if (line.getQtyBatch().signum() == 0 && line.getQtyBOM().signum() == 0) {
            return;
        }
        BigDecimal qtyUsageVariancePrev = line.getQtyVariance();
        BigDecimal qtyOpen = line.getQtyOpen();
        BigDecimal qtyUsageVariance = qtyOpen.subtract(qtyUsageVariancePrev);
        System.out.println("QtyVariance" + qtyUsageVariancePrev + " " + qtyOpen + " " + qtyUsageVariance);
        if (qtyUsageVariance.signum() == 0 || this.getQtyReject().compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        int M_Locator_ID = line.getM_Locator_ID();
        if (M_Locator_ID <= 0 && (locator = MLocator.getDefault((MWarehouse)MWarehouse.get((Properties)order.getCtx(), (int)order.getM_Warehouse_ID()))) != null) {
            M_Locator_ID = locator.getM_Locator_ID();
        }
        MPPCostCollector.createCollector(order, line.getM_Product_ID(), M_Locator_ID, line.getM_AttributeSetInstance_ID(), order.getS_Resource_ID(), line.getPP_Order_BOMLine_ID(), 0, MDocType.getDocType((String)"MCC"), "120", movementDate, qtyUsageVariance, Env.ZERO, Env.ZERO, 0, Env.ZERO, Env.ZERO, Env.ZERO, Env.ZERO);
    }

    public void createUsageVariance(I_PP_Order_Node orderNode) {
        MPPOrder order = this;
        Timestamp movementDate = order.getUpdated();
        MPPOrderNode node = (MPPOrderNode)orderNode;
        BigDecimal setupTimeReal = BigDecimal.valueOf(node.getSetupTimeReal());
        BigDecimal durationReal = BigDecimal.valueOf(node.getDurationReal());
        if (setupTimeReal.signum() == 0 && durationReal.signum() == 0) {
            return;
        }
        BigDecimal setupTimeVariancePrev = node.getSetupTimeUsageVariance();
        node.getDurationUsageVariance();
        BigDecimal setupTimeRequired = BigDecimal.valueOf(node.getSetupTimeRequired());
        BigDecimal.valueOf(node.getDurationRequired());
        BigDecimal qtyOpen = node.getQtyToDeliver().subtract(new BigDecimal(node.get_ValueAsString("qtyreserved")));
        BigDecimal setupTimeVariance = setupTimeRequired.subtract(setupTimeReal).subtract(setupTimeVariancePrev);
        BigDecimal durationVariance = new BigDecimal(node.getDuration()).multiply(qtyOpen);
        System.out.println("qtyreservedvariance" + new BigDecimal(node.get_ValueAsString("qtyreserved")) + " " + durationVariance + " " + node.getQtyRequired().subtract(node.getQtyDelivered()));
        if (qtyOpen.signum() == 0 && setupTimeVariance.signum() == 0 && durationVariance.signum() == 0 && new BigDecimal(node.get_ValueAsString("qtyreserved")).signum() == 0) {
            return;
        }
        System.out.println("yoyok" + qtyOpen + " " + node.getQtyRequired().subtract(node.getQtyDelivered()));
        if (new BigDecimal(node.get_ValueAsString("qtyreserved")).compareTo(BigDecimal.ZERO) == 0) {
            System.out.println("yoyok1" + qtyOpen + " " + node.getQtyRequired().subtract(node.getQtyDelivered()));
            MPPCostCollector.createCollector(order, order.getM_Product_ID(), order.getM_Locator_ID(), order.getM_AttributeSetInstance_ID(), node.getS_Resource_ID(), 0, node.getPP_Order_Node_ID(), MDocType.getDocType((String)"MCC"), "120", movementDate, qtyOpen, Env.ZERO, Env.ZERO, setupTimeVariance.intValueExact(), durationVariance, Env.ZERO, Env.ZERO, new BigDecimal(node.get_ValueAsInt("jmltenagakerja")));
        } else {
            System.out.println("yoyok2" + qtyOpen + " " + node.getQtyRequired().subtract(node.getQtyDelivered()));
            MPPCostCollector.createCollector(order, this.getM_Product_ID(), this.getM_Locator_ID(), this.getM_AttributeSetInstance_ID(), node.getS_Resource_ID(), 0, node.getPP_Order_Node_ID(), MDocType.getDocType((String)"MCC"), "160", order.getUpdated(), node.getQtyRequired().subtract(node.getQtyDelivered()), Env.ZERO, Env.ZERO, setupTimeVariance.intValueExact(), new BigDecimal(node.getDuration()).multiply(node.getQtyRequired().subtract(node.getQtyDelivered())), Env.ZERO, Env.ZERO, Env.ZERO);
        }
    }

    public void createUsageVarianceCost(I_PP_Order_Node orderNode) {
        MPPOrder order = this;
        Timestamp movementDate = order.getUpdated();
        MPPOrderNode node = (MPPOrderNode)orderNode;
        BigDecimal setupTimeReal = BigDecimal.valueOf(node.getSetupTimeReal());
        BigDecimal.valueOf(node.getDurationReal());
        BigDecimal setupTimeVariancePrev = node.getSetupTimeUsageVariance();
        node.getDurationUsageVariance();
        BigDecimal setupTimeRequired = BigDecimal.valueOf(node.getSetupTimeRequired());
        BigDecimal.valueOf(node.getDurationRequired());
        BigDecimal qtyOpen = node.getQtyToDeliver().subtract(new BigDecimal(node.get_ValueAsString("qtyreserved")));
        BigDecimal setupTimeVariance = setupTimeRequired.subtract(setupTimeReal).subtract(setupTimeVariancePrev);
        new BigDecimal(node.getDuration()).multiply(qtyOpen);
        this.costvarian = node.getPP_Order_Workflow().getCost().subtract(DB.getSQLValueBD(null, (String)("select sum(currentcostprice) from m_cost where M_CostElement_ID != 1000000 and m_product_id = " + node.getPP_Order_Workflow().getPP_Order().getM_Product_ID()), (Object[])new Object[0]));
        System.out.println("Tes" + this.costvarian);
        if (this.costvarian.compareTo(BigDecimal.ZERO) > 0) {
            System.out.println("asfagagalk;k;jl");
            MPPCostCollector.createCollector(order, order.getM_Product_ID(), order.getM_Locator_ID(), order.getM_AttributeSetInstance_ID(), node.getS_Resource_ID(), 0, node.getPP_Order_Node_ID(), MDocType.getDocType((String)"MCC"), "120", movementDate, node.getQtyRequired().subtract(node.getQtyDelivered()), Env.ZERO, Env.ZERO, setupTimeVariance.intValueExact(), new BigDecimal(node.getDuration()).multiply(node.getQtyRequired().subtract(node.getQtyDelivered())), this.costvarian, Env.ZERO, new BigDecimal(node.get_ValueAsInt("jmltenagakerja")));
        }
    }

    public BigDecimal getQtyToDeliver() {
        return this.getQtyOrdered().subtract(this.getQtyDelivered());
    }

    public void updateMakeToKit(BigDecimal qtyShipment) {
        MPPOrderBOM obom = this.getMPPOrderBOM();
        this.getLines(true);
        if ("K".equals(obom.getBOMType()) && "M".equals(obom.getBOMUse())) {
            Timestamp today = new Timestamp(System.currentTimeMillis());
            ArrayList[][] issue = new ArrayList[this.m_lines.length][1];
            for (int i = 0; i < this.getLines().length; ++i) {
                MPPOrderBOMLine line = this.m_lines[i];
                KeyNamePair id = null;
                id = "1".equals(line.getIssueMethod()) ? new KeyNamePair(line.get_ID(), "Y") : new KeyNamePair(line.get_ID(), "N");
                ArrayList<Object> data = new ArrayList<Object>();
                BigDecimal qtyToDeliver = qtyShipment.multiply(line.getQtyMultiplier());
                data.add((Object)id);
                data.add(line.isCritical());
                MProduct product = line.getM_Product();
                data.add(product.getValue());
                KeyNamePair productKey = new KeyNamePair(product.get_ID(), product.getName());
                data.add((Object)productKey);
                data.add(qtyToDeliver);
                data.add(Env.ZERO);
                issue[i][0] = data;
            }
            boolean forceIssue = false;
            MOrderLine oline = (MOrderLine)this.getC_OrderLine();
            if ("L".equals(oline.getParent().getDeliveryRule()) || "O".equals(oline.getParent().getDeliveryRule())) {
                boolean isCompleteQtyDeliver = MPPOrder.isQtyAvailable(this, issue, today);
                if (!isCompleteQtyDeliver) {
                    throw new AdempiereException("@NoQtyAvailable@");
                }
            } else {
                if ("A".equals(oline.getParent().getDeliveryRule()) || "R".equals(oline.getParent().getDeliveryRule()) || "M".equals(oline.getParent().getDeliveryRule())) {
                    throw new AdempiereException("@ActionNotSupported@");
                }
                if ("F".equals(oline.getParent().getDeliveryRule())) {
                    forceIssue = true;
                }
            }
            for (int i = 0; i < issue.length; ++i) {
                int M_AttributeSetInstance_ID = 0;
                KeyNamePair key = (KeyNamePair)issue[i][0].get(0);
                Boolean cfr_ignored_0 = (Boolean)issue[i][0].get(1);
                String cfr_ignored_1 = (String)issue[i][0].get(2);
                KeyNamePair productkey = (KeyNamePair)issue[i][0].get(3);
                int M_Product_ID = productkey.getKey();
                MProduct.get((Properties)this.getCtx(), (int)M_Product_ID);
                BigDecimal qtyToDeliver = (BigDecimal)issue[i][0].get(4);
                BigDecimal qtyScrapComponent = (BigDecimal)issue[i][0].get(5);
                int PP_Order_BOMLine_ID = key.getKey();
                if (PP_Order_BOMLine_ID > 0) {
                    MPPOrderBOMLine orderBOMLine = new MPPOrderBOMLine(this.getCtx(), PP_Order_BOMLine_ID, this.get_TrxName());
                    M_AttributeSetInstance_ID = orderBOMLine.getM_AttributeSetInstance_ID();
                }
                MStorageOnHand[] storages = MPPOrder.getStorages(this.getCtx(), M_Product_ID, this.getM_Warehouse_ID(), M_AttributeSetInstance_ID, today, this.get_TrxName());
                MPPOrder.createIssue(this, key.getKey(), today, qtyToDeliver, qtyScrapComponent, Env.ZERO, storages, forceIssue);
            }
            MPPOrder.createReceipt(this, today, this.getQtyDelivered(), qtyShipment, this.getQtyScrap(), this.getQtyReject(), this.getM_Locator_ID(), this.getM_AttributeSetInstance_ID());
        }
    }

    protected void rollup(MProduct product, MPPProductBOM bom) {
        for (MCostElement element : this.getCostElements()) {
            for (MCost cost : this.getCosts(product, element.get_ID())) {
                log.info("Calculate Lower Cost for: " + (Object)bom);
                BigDecimal price = this.getCurrentCostPriceLL(bom, element);
                log.info(String.valueOf(element.getName()) + " Cost Low Level:" + price);
                cost.setCurrentCostPriceLL(price);
                this.updateCoProductCosts(bom, cost);
                cost.saveEx();
            }
        }
    }

    private void updateCoProductCosts(MPPProductBOM bom, MCost baseCost) {
        if (bom == null) {
            return;
        }
        BigDecimal costPriceTotal = Env.ZERO;
        for (MPPProductBOMLine bomline : bom.getLines()) {
            if (!bomline.isCoProduct()) continue;
            BigDecimal costPrice = baseCost.getCurrentCostPriceLL().multiply(bomline.getCostAllocationPerc(true));
            MCost cost = MCost.get((Properties)baseCost.getCtx(), (int)baseCost.getAD_Client_ID(), (int)baseCost.getAD_Org_ID(), (int)bomline.getM_Product_ID(), (int)baseCost.getM_CostType_ID(), (int)baseCost.getC_AcctSchema_ID(), (int)baseCost.getM_CostElement_ID(), (int)0, (String)baseCost.get_TrxName());
            if (cost == null) {
                cost = new MCost(baseCost.getCtx(), 0, baseCost.get_TrxName());
                cost.setAD_Org_ID(baseCost.getAD_Org_ID());
                cost.setM_Product_ID(bomline.getM_Product_ID());
                cost.setM_CostType_ID(baseCost.getM_CostType_ID());
                cost.setC_AcctSchema_ID(baseCost.getC_AcctSchema_ID());
                cost.setM_CostElement_ID(baseCost.getM_CostElement_ID());
                cost.setM_AttributeSetInstance_ID(0);
            }
            cost.setCurrentCostPriceLL(costPrice);
            cost.saveEx();
            costPriceTotal = costPriceTotal.add(costPrice);
        }
        if (costPriceTotal.signum() != 0) {
            baseCost.setCurrentCostPriceLL(costPriceTotal);
        }
    }

    private BigDecimal getCurrentCostPriceLL(MPPProductBOM bom, MCostElement element) {
        log.info("Element: " + (Object)element);
        BigDecimal costPriceLL = Env.ZERO;
        if (bom == null) {
            return costPriceLL;
        }
        for (MPPProductBOMLine bomline : bom.getLines()) {
            if (bomline.isCoProduct()) continue;
            MProduct component = MProduct.get((Properties)this.getCtx(), (int)bomline.getM_Product_ID());
            for (MCost cost : this.getCosts(component, element.get_ID())) {
                BigDecimal qty = bomline.getQty(true);
                if (bomline.isByProduct()) {
                    cost.setCurrentCostPriceLL(Env.ZERO);
                }
                BigDecimal costPrice = cost.getCurrentCostPrice().add(cost.getCurrentCostPriceLL());
                BigDecimal componentCost = costPrice.multiply(qty);
                costPriceLL = costPriceLL.add(componentCost);
                log.info("CostElement: " + element.getName() + ", Component: " + component.getValue() + ", CostPrice: " + costPrice + ", Qty: " + qty + ", Cost: " + componentCost + " => Total Cost Element: " + costPriceLL);
            }
        }
        return costPriceLL;
    }

    private Collection<MCost> getCosts(MProduct product, int M_CostElement_ID) {
        MAcctSchema as = MAcctSchema.get((Properties)this.getCtx(), (int)1000000);
        int hasil = 0;
        CostDimension d = null;
        hasil = DB.getSQLValue(null, (String)"select m_attributesetinstance_id from m_cost where coalesce(m_attributesetinstance_id,0) > 0 and m_product_id = ?", (int)product.getM_Product_ID()) > 0 ? DB.getSQLValue(null, (String)"select m_attributesetinstance_id from m_cost where coalesce(m_attributesetinstance_id,0) > 0 and m_product_id = ?", (int)product.getM_Product_ID()) : 0;
        d = new CostDimension(product, as, 1000000, this.getAD_Org_ID(), hasil, M_CostElement_ID);
        return d.toQuery(MCost.class, this.get_TrxName()).list();
    }

    private Collection<MProduct> getProducts(int lowLevel) {
        ArrayList<Comparable<Integer>> params = new ArrayList<Comparable<Integer>>();
        StringBuffer whereClause = new StringBuffer("AD_Client_ID=?").append(" AND ").append("LowLevel").append("=?");
        params.add(Integer.valueOf(this.getAD_Client_ID()));
        params.add(Integer.valueOf(lowLevel));
        whereClause.append(" AND ").append("IsBOM").append("=?");
        params.add(Boolean.valueOf(true));
        whereClause.append(" AND ").append("M_Product_ID").append("=?");
        params.add(Integer.valueOf(this.getM_Product_ID()));
        return new Query(this.getCtx(), "M_Product", whereClause.toString(), this.get_TrxName()).setParameters(params).list();
    }

    private void resetCostsLLForLLC0() {
        ArrayList<Integer> params = new ArrayList<Integer>();
        StringBuffer productWhereClause = new StringBuffer();
        productWhereClause.append("AD_Client_ID=? AND LowLevel=? AND M_Product_ID=?");
        params.add(this.getAD_Client_ID());
        params.add(0);
        params.add(this.getM_Product_ID());
        String sql = "UPDATE M_Cost c SET CurrentCostPriceLL=0 WHERE EXISTS (SELECT 1 FROM M_Product p WHERE p.M_Product_ID=c.M_Product_ID AND " + productWhereClause + ")";
        int no = DB.executeUpdateEx((String)sql, (Object[])params.toArray(), (String)this.get_TrxName());
        log.info("Updated #" + no);
    }

    private Collection<MCostElement> getCostElements() {
        if (this.m_costElements == null) {
            this.m_costElements = MCostElement.getByCostingMethod((Properties)this.getCtx(), (String)"S");
        }
        return this.m_costElements;
    }

    private Collection<MProduct> getProductsResource() {
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuffer whereClause = new StringBuffer("AD_Client_ID=?");
        params.add(this.getAD_Client_ID());
        whereClause.append(" AND (").append("ProductType").append("=?");
        params.add("I");
        whereClause.append(" OR ").append("ProductType").append("=?");
        params.add("R");
        whereClause.append(") AND ").append("IsBOM").append("=?");
        params.add(true);
        whereClause.append(" AND ").append("M_Product_ID").append("=?");
        params.add(this.getM_Product_ID());
        List products = new Query(this.getCtx(), "M_Product", whereClause.toString(), this.get_TrxName()).setOrderBy("LowLevel").setParameters(params).list();
        return products;
    }

    public void rollup(MProduct product, MWorkflow workflow) {
        MWFNode node;
        log.info("Workflow: " + (Object)workflow);
        System.out.println("sfafagfa" + this.getPP_Order_ID());
        MPPOrderWorkflow mppOrderWorkflow = new MPPOrderWorkflow(this.getCtx(), DB.getSQLValue((String)this.get_TrxName(), (String)("select PP_Order_Workflow_ID from PP_Order_Workflow where pp_order_id = " + this.getPP_Order_ID())), this.get_TrxName());
        workflow.setCost(Env.ZERO);
        double Yield = 1.0;
        int QueuingTime = 0;
        int SetupTime = 0;
        int Duration = 0;
        int WaitingTime = 0;
        int MovingTime = 0;
        int WorkingTime = 0;
        int jmltenagakerja = 0;
        this.m_routingService = RoutingServiceFactory.get().getRoutingService(this.getAD_Client_ID());
        MWFNode[] nodes = workflow.getNodes(false, this.getAD_Client_ID());
        MPPOrderNode[] mppOrderNodes = mppOrderWorkflow.getNodes(false, this.getAD_Client_ID());
        MWFNode[] arrmWFNode = nodes;
        int n = nodes.length;
        for (int i = 0; i < n; ++i) {
            node = arrmWFNode[i];
            node.setCost(Env.ZERO);
            if (node.getYield() != 0) {
                Yield *= (double)node.getYield() / 100.0;
            }
            long nodeDuration = node.getDuration();
            QueuingTime += node.getQueuingTime();
            SetupTime += node.getSetupTime();
            Duration = (int)((long)Duration + nodeDuration);
            WaitingTime += node.getWaitingTime();
            MovingTime += node.getMovingTime();
            WorkingTime += node.getWorkingTime();
            jmltenagakerja += node.get_ValueAsInt("jmltenagakerja");
        }
        workflow.setCost(Env.ZERO);
        workflow.setYield((int)(Yield * 100.0));
        workflow.setQueuingTime(QueuingTime);
        workflow.setSetupTime(SetupTime);
        workflow.setDuration(Duration);
        workflow.setWaitingTime(WaitingTime);
        workflow.setMovingTime(MovingTime);
        workflow.setWorkingTime(WorkingTime);
        workflow.set_CustomColumn("jmltenagakerja", (Object)jmltenagakerja);
        mppOrderWorkflow.setDuration(Duration);
        mppOrderWorkflow.set_CustomColumn("jmltenagakerja", jmltenagakerja);
        for (MCostElement element : MCostElement.getByCostingMethod((Properties)this.getCtx(), (String)"S")) {
            if (!CostEngine.isActivityControlElement((I_M_CostElement)element)) continue;
            MAcctSchema m_as = MAcctSchema.get((Properties)this.getCtx(), (int)1000000);
            CostDimension d = new CostDimension(product, m_as, 1000000, this.getAD_Org_ID(), 0, element.get_ID());
            List costs = d.toQuery(MCost.class, this.get_TrxName()).list();
            for (MCost cost : costs) {
                int precision = MAcctSchema.get((Properties)Env.getCtx(), (int)cost.getC_AcctSchema_ID()).getCostingPrecision();
                BigDecimal segmentCost = Env.ZERO;
                MWFNode[] arrmWFNode2 = nodes;
                int n2 = nodes.length;
                for (int i = 0; i < n2; ++i) {
                    MWFNode node2 = arrmWFNode2[i];
                    CostEngine costEngine = CostEngineFactory.getCostEngine(node2.getAD_Client_ID());
                    BigDecimal rate = costEngine.getResourceActualCostRate(null, node2.getS_Resource_ID(), d, this.get_TrxName());
                    BigDecimal baseValue = this.m_routingService.getResourceBaseValue(node2.getS_Resource_ID(), (I_AD_WF_Node)node2);
                    BigDecimal nodeCost = baseValue.multiply(rate).multiply(new BigDecimal(node2.get_ValueAsInt("jmltenagakerja")));
                    if (nodeCost.scale() > precision) {
                        nodeCost = nodeCost.setScale(precision, RoundingMode.HALF_UP);
                    }
                    segmentCost = segmentCost.add(nodeCost);
                    log.info("Element : " + (Object)element + ", Node=" + (Object)node2 + ", BaseValue=" + baseValue + ", rate=" + rate + ", nodeCost=" + nodeCost + " => Cost=" + segmentCost);
                    node2.setCost(node2.getCost().add(nodeCost));
                    MPPOrderNode[] arrmPPOrderNode = mppOrderNodes;
                    int n3 = mppOrderNodes.length;
                    for (int j = 0; j < n3; ++j) {
                        MPPOrderNode ppnode = arrmPPOrderNode[j];
                        if (ppnode.get_ValueAsInt("jmltenagakerja") == 0) {
                            ppnode.setCost(node2.getCost().add(nodeCost));
                        }
                        ppnode.setDuration(node2.getDuration());
                        ppnode.set_CustomColumn("jmltenagakerja", node2.get_ValueAsInt("jmltenagakerja"));
                        ppnode.saveEx();
                    }
                }
                if (cost.getCurrentCostPrice().compareTo(BigDecimal.ZERO) == 0) {
                    cost.setCurrentCostPrice(segmentCost);
                    cost.saveEx();
                }
                workflow.setCost(workflow.getCost().add(segmentCost));
                mppOrderWorkflow.setCost(DB.getSQLValueBD(null, (String)("select sum(cost) from PP_Order_Node where PP_Order_ID=" + mppOrderWorkflow.getPP_Order_ID()), (Object[])new Object[0]));
            }
        }
        arrmWFNode = nodes;
        n = nodes.length;
        for (int i = 0; i < n; ++i) {
            node = arrmWFNode[i];
            node.saveEx();
        }
        workflow.saveEx();
        mppOrderWorkflow.saveEx();
        log.info("Product: " + product.getName() + " WFCost: " + workflow.getCost());
    }

    public int customizeValidActions(String docStatus, Object processing, String orderType, String isSOTrx, int AD_Table_ID, String[] docAction, String[] options, int index) {
        for (int i = 0; i < options.length; ++i) {
            options[i] = null;
        }
        index = 0;
        if (docStatus.equals("DR")) {
            options[index++] = "PR";
            options[index++] = "VO";
        } else if (docStatus.equals("CO")) {
            options[index++] = "VO";
            options[index++] = "RE";
        } else if (docStatus.equals("IN")) {
            options[index++] = "VO";
            options[index++] = "CO";
        } else if (docStatus.equals("IP")) {
            options[index++] = "CO";
            options[index++] = "VO";
        }
        return index;
    }
}

