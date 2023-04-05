/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.compiere.model.MBPartner
 *  org.compiere.model.MDocType
 *  org.compiere.model.MLocator
 *  org.compiere.model.MMessage
 *  org.compiere.model.MNote
 *  org.compiere.model.MOrg
 *  org.compiere.model.MProduct
 *  org.compiere.model.MProductPO
 *  org.compiere.model.MRequisition
 *  org.compiere.model.MRequisitionLine
 *  org.compiere.model.MWarehouse
 *  org.compiere.model.PO
 *  org.compiere.model.POResultSet
 *  org.compiere.model.Query
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.CCache
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.compiere.util.Msg
 *  org.compiere.util.TimeUtil
 *  org.compiere.util.Util
 *  org.compiere.wf.MWorkflow
 *  org.eevolution.model.I_PP_Product_Planning
 *  org.eevolution.model.MDDOrder
 *  org.eevolution.model.MDDOrderLine
 *  org.eevolution.model.MPPProductBOM
 *  org.eevolution.model.MPPProductBOMLine
 *  org.eevolution.model.MPPProductPlanning
 */
package org.libero.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.swing.tree.DefaultMutableTreeNode;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MLocator;
import org.compiere.model.MMessage;
import org.compiere.model.MNote;
import org.compiere.model.MOrg;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPO;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MWarehouse;
import org.compiere.model.PO;
import org.compiere.model.POResultSet;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.compiere.wf.MWorkflow;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.MDDOrder;
import org.eevolution.model.MDDOrderLine;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.eevolution.model.MPPProductPlanning;
import org.libero.form.TreeBOM;
import org.libero.model.MDDNetworkDistribution;
import org.libero.model.MDDNetworkDistributionLine;
import org.libero.model.MPPMRP;
import org.libero.model.MPPOrder;
import org.libero.model.TampungDataBom;
import org.libero.model.TampungDataDeleteMRP;

public class GenerateBOMProduct
extends SvrProcess {
    TreeBOM treebom = new TreeBOM();
    private int p_AD_Org_ID = 0;
    private int p_S_Resource_ID = 0;
    private int p_M_Warehouse_ID = 0;
    private boolean p_IsRequiredDRP = false;
    private int p_Planner_ID = 0;
    private String p_Version = "1";
    protected int p_M_Product_ID = 0;
    private int p_C_Order_ID = 0;
    private int p_M_Order_ID = 0;
    private int generatebom = 0;
    private MPPProductPlanning m_product_planning = null;
    private BigDecimal QtyProjectOnHand = Env.ZERO;
    private BigDecimal QtyGrossReqs = Env.ZERO;
    private BigDecimal QtyScheduledReceipts = Env.ZERO;
    private Timestamp DatePromisedFrom = null;
    private Timestamp DatePromisedTo = null;
    private Timestamp Today = new Timestamp(System.currentTimeMillis());
    private Timestamp TimeFence = null;
    private Timestamp Planning_Horizon = null;
    private int docTypeReq_ID = 0;
    private int docTypeMO_ID = 0;
    private int docTypeMF_ID = 0;
    private int docTypeDO_ID = 0;
    private int count_MO = 0;
    private int count_MR = 0;
    private int count_DO = 0;
    private int count_Msg = 0;
    private boolean p_DeleteMRP;
    private String msg_debug;
    private static CCache<String, Integer> dd_order_id_cache = new CCache("DD_Order_ID", 50);
    private static CCache<Integer, MBPartner> partner_cache = new CCache("C_BPartner", 50);
    private List<TampungDataDeleteMRP> listtaTampungDataDeleteMRPs = null;
    private List<TampungDataBom> list = new ArrayList<TampungDataBom>();

    protected void prepare() {
    }

    public boolean isRequiredDRP() {
        return this.p_IsRequiredDRP;
    }

    public int getPlanner_ID() {
        if (this.p_Planner_ID <= 0) {
            this.p_Planner_ID = Env.getAD_User_ID((Properties)this.getCtx());
        }
        return this.p_Planner_ID;
    }

    protected String doIt() throws Exception {
        StringBuffer resultMsg = new StringBuffer();
        MPPOrder mppOrder = new MPPOrder(this.getCtx(), this.getRecord_ID(), this.get_TrxName());
        List listmrp = new Query(this.getCtx(), "PP_MRP", "pp_orderref_id = " + mppOrder.getPP_Order_ID() + " and coalesce(pp_order_id,0) != " + mppOrder.getPP_Order_ID() + " and typemrp = 'S'", this.msg_debug).list();
        this.listtaTampungDataDeleteMRPs = new ArrayList<TampungDataDeleteMRP>();
        for (MPPMRP mppmrp : listmrp) {
            TampungDataDeleteMRP dataDeleteMRP = new TampungDataDeleteMRP();
            dataDeleteMRP.setRequisitionid(mppmrp.getM_Requisition_ID());
            dataDeleteMRP.setOrderid(mppmrp.getPP_Order_ID());
            this.listtaTampungDataDeleteMRPs.add(dataDeleteMRP);
        }
        try {
            this.deleteMRP(mppOrder.getAD_Client_ID(), mppOrder.getAD_Org_ID(), mppOrder.getS_Resource_ID(), mppOrder.getM_Warehouse_ID(), mppOrder.getPP_Order_ID());
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
        MProduct product = MProduct.get((Properties)this.getCtx(), (int)mppOrder.getM_Product_ID());
        DefaultMutableTreeNode parent = new DefaultMutableTreeNode(this.treebom.productSummary(product, false));
        for (MPPProductBOM bom : MPPProductBOM.getProductBOMs((MProduct)product)) {
            parent.add(this.parent(bom, mppOrder.getM_Product_ID(), mppOrder));
        }
        resultMsg.append("<br> " + Msg.translate((Properties)this.getCtx(), (String)"Created"));
        resultMsg.append("<br> ");
        resultMsg.append("<br> " + Msg.translate((Properties)this.getCtx(), (String)"PP_Order_ID") + ":" + this.count_MO);
        resultMsg.append("<br> " + Msg.translate((Properties)this.getCtx(), (String)"DD_Order_ID") + ":" + this.count_DO);
        resultMsg.append("<br> " + Msg.translate((Properties)this.getCtx(), (String)"M_Requisition_ID") + ":" + this.count_MR);
        resultMsg.append("<br> " + Msg.translate((Properties)this.getCtx(), (String)"AD_Note_ID") + ":" + this.count_Msg);
        return String.valueOf(this.msg_debug) + "|" + resultMsg.toString();
    }

    protected String runMRP(int AD_Client_ID, int AD_Org_ID, int S_Resource_ID, int M_Warehouse_ID, int C_Order_ID, int PP_Order_ID, int productid, int ProductMPO, BigDecimal qtybom, int productmasterbom) throws SQLException {
        MProduct product = null;
        int BeforePP_MRP_ID = 0;
        Timestamp BeforeDateStartSchedule = null;
        Timestamp POQDateStartSchedule = null;
        BigDecimal Qty = BigDecimal.ZERO;
        this.docTypeReq_ID = this.getDocType("POR", AD_Org_ID);
        this.docTypeMO_ID = this.getDocType("MOP", AD_Org_ID);
        this.docTypeMF_ID = this.getDocType("MOF", AD_Org_ID);
        this.docTypeDO_ID = this.getDocType("DOO", AD_Org_ID);
        if (this.generatebom == 0) {
            TampungDataBom tampungDataBom = new TampungDataBom();
            tampungDataBom.setProductparent(DB.getSQLValue((String)this.get_TrxName(), (String)("select m_product_id from pp_order where pp_order_id = " + PP_Order_ID)));
            tampungDataBom.setQtyrequired(DB.getSQLValueBD((String)this.get_TrxName(), (String)("select qtyentered from pp_order where pp_order_id = " + PP_Order_ID), (Object[])new Object[0]));
            this.list.add(tampungDataBom);
            this.generatebom = 1;
        }
        int PP_MRP_ID = DB.getSQLValue(null, (String)("select pp_mrp_id from pp_mrp where PP_Order_ID =  " + PP_Order_ID));
        Timestamp DatePromised = DB.getSQLValueTS(null, (String)("select datepromised from PP_Order where PP_Order_ID =  " + PP_Order_ID), (Object[])new Object[0]);
        for (int i = 0; i < this.list.size(); ++i) {
            if (productmasterbom != this.list.get(i).getProductparent()) continue;
            Qty = this.list.get(i).getQtyrequired().multiply(qtybom);
        }
        int M_Product_ID = productid;
        product = MProduct.get((Properties)this.getCtx(), (int)M_Product_ID);
        this.log.info("Calculte Plan to this Product:" + (Object)product);
        this.setProduct(AD_Client_ID, AD_Org_ID, S_Resource_ID, M_Warehouse_ID, product);
        if ("POQ".equals(this.m_product_planning.getOrder_Policy())) {
            POQDateStartSchedule = null;
        }
        int daysPOQ = this.m_product_planning.getOrder_Period().intValueExact() - 1;
        if ("POQ".equals(this.m_product_planning.getOrder_Policy()) && this.DatePromisedTo != null && DatePromised.compareTo(this.DatePromisedTo) > 0) {
            this.calculatePlan(AD_Client_ID, AD_Org_ID, M_Warehouse_ID, PP_MRP_ID, product, this.DatePromisedFrom, C_Order_ID, ProductMPO, productmasterbom);
            this.DatePromisedFrom = DatePromised;
            this.DatePromisedTo = TimeUtil.addDays((Timestamp)DatePromised, (int)(daysPOQ < 0 ? 0 : daysPOQ));
            POQDateStartSchedule = DatePromised;
        } else if (POQDateStartSchedule == null) {
            this.DatePromisedFrom = DatePromised;
            this.DatePromisedTo = TimeUtil.addDays((Timestamp)DatePromised, (int)(daysPOQ < 0 ? 0 : daysPOQ));
            POQDateStartSchedule = DatePromised;
        }
        if (DatePromised.compareTo(this.getToday()) < 0) {
            String comment = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"DatePromised")) + ": " + DatePromised;
            this.createMRPNote("MRP-150", AD_Org_ID, PP_MRP_ID, product, MPPMRP.getDocumentNo(PP_MRP_ID), Qty, comment);
        }
        BeforePP_MRP_ID = PP_MRP_ID;
        if ("POQ".equals(this.m_product_planning.getOrder_Policy())) {
            if (this.DatePromisedTo != null && DatePromised.compareTo(this.DatePromisedTo) <= 0) {
                this.QtyGrossReqs = this.QtyGrossReqs.add(Qty);
                this.log.info("Accumulation   QtyGrossReqs:" + this.QtyGrossReqs);
                this.log.info("DatePromised:" + DatePromised);
                this.log.info("DatePromisedTo:" + this.DatePromisedTo);
            }
        } else if ("LFL".equals(this.m_product_planning.getOrder_Policy())) {
            System.out.println("Lot");
            this.QtyGrossReqs = this.QtyGrossReqs.add(Qty);
            System.out.println(Qty);
            System.out.println("" + BeforeDateStartSchedule + DatePromised);
            BeforeDateStartSchedule = DatePromised;
            this.calculatePlan(AD_Client_ID, AD_Org_ID, M_Warehouse_ID, PP_MRP_ID, product, BeforeDateStartSchedule, C_Order_ID, ProductMPO, productmasterbom);
        }
        if (this.QtyGrossReqs.signum() != 0 && product != null) {
            if ("POQ".equals(this.m_product_planning.getOrder_Policy()) && POQDateStartSchedule.compareTo(this.Planning_Horizon) < 0) {
                BeforeDateStartSchedule = POQDateStartSchedule;
                this.calculatePlan(AD_Client_ID, AD_Org_ID, M_Warehouse_ID, BeforePP_MRP_ID, product, BeforeDateStartSchedule, C_Order_ID, ProductMPO, productmasterbom);
            } else if ("LFL".equals(this.m_product_planning.getOrder_Policy()) && BeforeDateStartSchedule.compareTo(this.Planning_Horizon) <= 0) {
                this.calculatePlan(AD_Client_ID, AD_Org_ID, M_Warehouse_ID, BeforePP_MRP_ID, product, BeforeDateStartSchedule, C_Order_ID, ProductMPO, productmasterbom);
            }
        } else if (product != null) {
            this.getNetRequirements(AD_Client_ID, AD_Org_ID, M_Warehouse_ID, product, null);
        }
        return "ok";
    }

    protected void deleteMRP(int AD_Client_ID, int AD_Org_ID, int S_Resource_ID, int M_Warehouse_ID, int pporderid) throws SQLException {
        String sql = "DELETE FROM PP_MRP WHERE OrderType = 'POR' AND DocStatus NOT IN ('CL', 'CO') AND AD_Client_ID = " + AD_Client_ID + " AND AD_Org_ID=" + AD_Org_ID + " AND M_Warehouse_ID=" + M_Warehouse_ID + " AND pp_orderref_id = " + pporderid;
        DB.executeUpdateEx((String)sql, (String)this.get_TrxName());
        this.commitEx();
        String whereClause = "DocStatus IN ('DR') AND AD_Client_ID=? AND AD_Org_ID=? AND M_Warehouse_ID=?";
        sql = "DELETE FROM AD_Note WHERE AD_Table_ID=? AND AD_Client_ID=? AND AD_Org_ID=?";
        DB.executeUpdateEx((String)sql, (Object[])new Object[]{53043, AD_Client_ID, AD_Org_ID}, (String)this.get_TrxName());
        this.commitEx();
        if (this.isRequiredDRP()) {
            whereClause = "DocStatus='DR' AND AD_Client_ID=? AND AD_Org_ID=? AND EXISTS (SELECT 1 FROM PP_MRP mrp WHERE  mrp.DD_Order_ID=DD_Order.DD_Order_ID AND mrp.S_Resource_ID=? ) AND EXISTS (SELECT 1 FROM DD_OrderLine ol INNER JOIN  M_Locator l ON (l.M_Locator_ID=ol.M_LocatorTo_ID)  WHERE ol.DD_Order_ID=DD_Order.DD_Order_ID AND l.M_Warehouse_ID=?)";
            this.deletePO("DD_Order", whereClause, new Object[]{AD_Client_ID, AD_Org_ID, S_Resource_ID, M_Warehouse_ID});
        }
        sql = "DELETE FROM PP_MRP WHERE OrderType = 'MOP' AND DocStatus NOT IN ('CL', 'CO')AND AD_Client_ID=" + AD_Client_ID + " AND AD_Org_ID=" + AD_Org_ID + " AND M_Warehouse_ID=" + M_Warehouse_ID + " AND S_Resource_ID=" + S_Resource_ID + " AND PP_Order_ID != " + pporderid;
        DB.executeUpdateEx((String)sql, (String)this.get_TrxName());
        this.commitEx();
        whereClause = "DocStatus='DR' AND AD_Client_ID=? AND AD_Org_ID=? AND M_Warehouse_ID=? AND S_Resource_ID=? AND PP_Order_ID!=?";
        for (int i = 0; i < this.listtaTampungDataDeleteMRPs.size(); ++i) {
            if (this.listtaTampungDataDeleteMRPs.get(i).getRequisitionid() > 0) {
                this.deletePO("M_Requisition", "M_Requisition_ID = ?", new Object[]{this.listtaTampungDataDeleteMRPs.get(i).getRequisitionid()});
                continue;
            }
            if (this.listtaTampungDataDeleteMRPs.get(i).getOrderid() <= 0) continue;
            this.deletePO("PP_Order", "PP_Order_ID = ?", new Object[]{this.listtaTampungDataDeleteMRPs.get(i).getOrderid()});
        }
        DB.executeUpdateEx((String)"UPDATE PP_MRP SET IsAvailable ='Y' WHERE TypeMRP = 'S' AND AD_Client_ID = ? AND AD_Org_ID=? AND M_Warehouse_ID=?", (Object[])new Object[]{AD_Client_ID, AD_Org_ID, M_Warehouse_ID}, (String)this.get_TrxName());
        this.commitEx();
    }

    public DefaultMutableTreeNode parent(MPPProductBOM bom, int product, MPPOrder mppOrder) {
        DefaultMutableTreeNode parent = new DefaultMutableTreeNode(this.treebom.productSummary(bom));
        for (MPPProductBOMLine bomline : bom.getLines()) {
            MProduct component = MProduct.get((Properties)this.getCtx(), (int)bomline.getM_Product_ID());
            try {
                this.runMRP(mppOrder.getAD_Client_ID(), mppOrder.getAD_Org_ID(), mppOrder.getS_Resource_ID(), mppOrder.getM_Warehouse_ID(), 0, mppOrder.getPP_Order_ID(), bomline.getM_Product_ID(), mppOrder.getPP_Order_ID(), bomline.getQtyBOM(), bomline.getPP_Product_BOM().getM_Product_ID());
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Product" + bomline.getM_Product().getName() + " " + bomline.getLowLevel() + " " + bomline.getPP_Product_BOM_ID() + " " + bom.getPP_Product_BOM_ID() + " " + DB.getSQLValue((String)this.get_TrxName(), (String)("select count(*) from PP_Product_BOM where m_product_id = " + bomline.getM_Product().getM_Product_ID())));
            DB.getSQLValue((String)this.get_TrxName(), (String)("select count(*) from PP_Product_BOM where m_product_id = " + bomline.getM_Product().getM_Product_ID()));
            parent.add(this.component(component, product, mppOrder));
        }
        return parent;
    }

    public DefaultMutableTreeNode component(MProduct product, int productfp, MPPOrder mppOrder) {
        Iterator iterator = MPPProductBOM.getProductBOMs((MProduct)product).iterator();
        if (iterator.hasNext()) {
            MPPProductBOM bom = (MPPProductBOM)iterator.next();
            return this.parent(bom, productfp, mppOrder);
        }
        return new DefaultMutableTreeNode(this.treebom.productSummary(product, true));
    }

    private void setProduct(int AD_Client_ID, int AD_Org_ID, int S_Resource_ID, int M_Warehouse_ID, MProduct product) throws SQLException {
        String comment;
        this.DatePromisedTo = null;
        this.DatePromisedFrom = null;
        this.m_product_planning = this.getProductPlanning(AD_Client_ID, AD_Org_ID, S_Resource_ID, M_Warehouse_ID, product);
        this.log.info("PP:" + AD_Client_ID + "|" + AD_Org_ID + "|" + S_Resource_ID + "|" + M_Warehouse_ID + "|" + (Object)product);
        if (this.m_product_planning == null) {
            this.createMRPNote("MRP-120", AD_Org_ID, 0, product, (String)null, (BigDecimal)null, (String)null);
            return;
        }
        if (this.m_product_planning.getTimeFence().signum() > 0) {
            this.TimeFence = TimeUtil.addDays((Timestamp)this.getToday(), (int)this.m_product_planning.getTimeFence().intValueExact());
        }
        this.QtyProjectOnHand = this.getQtyOnHand((I_PP_Product_Planning)this.m_product_planning);
        System.out.println("qtyprojectonhand" + this.QtyProjectOnHand);
        if (this.QtyProjectOnHand.signum() < 0) {
            comment = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"QtyOnHand")) + ": " + this.QtyProjectOnHand;
            this.createMRPNote("MRP-140", AD_Org_ID, 0, product, null, this.QtyProjectOnHand, comment);
        }
        if (this.m_product_planning.getSafetyStock().signum() > 0 && this.m_product_planning.getSafetyStock().compareTo(this.QtyProjectOnHand) > 0) {
            comment = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"QtyOnHand")) + ": " + this.QtyProjectOnHand + " " + Msg.translate((Properties)this.getCtx(), (String)"SafetyStock") + ": " + this.m_product_planning.getSafetyStock();
            this.createMRPNote("MRP-001", AD_Org_ID, 0, product, null, this.QtyProjectOnHand, comment);
        }
        this.log.info("QtyOnHand :" + this.QtyProjectOnHand);
    }

    protected MPPProductPlanning getProductPlanning(int AD_Client_ID, int AD_Org_ID, int S_Resource_ID, int M_Warehouse_ID, MProduct product) throws SQLException {
        MPPProductPlanning pp = MPPProductPlanning.find((Properties)this.getCtx(), (int)AD_Org_ID, (int)M_Warehouse_ID, (int)S_Resource_ID, (int)product.getM_Product_ID(), (String)this.get_TrxName());
        if (pp == null) {
            return null;
        }
        MPPProductPlanning pp2 = new MPPProductPlanning(this.getCtx(), 0, null);
        MPPProductPlanning.copyValues((PO)pp, (PO)pp2);
        pp2.setIsRequiredDRP(this.isRequiredDRP());
        if (pp2.getPP_Product_BOM_ID() <= 0 && product.isBOM()) {
            pp2.setPP_Product_BOM_ID(MPPProductBOM.getBOMSearchKey((MProduct)product));
        }
        if (pp2.getAD_Workflow_ID() <= 0 && product.isBOM()) {
            pp2.setAD_Workflow_ID(MWorkflow.getWorkflowSearchKey((MProduct)product));
        }
        if (pp2.getPlanner_ID() <= 0) {
            pp2.setPlanner_ID(this.getPlanner_ID());
        }
        if (pp2.getM_Warehouse_ID() <= 0) {
            pp2.setM_Warehouse_ID(M_Warehouse_ID);
        }
        if (pp2.getS_Resource_ID() <= 0) {
            pp2.setS_Resource_ID(S_Resource_ID);
        }
        if (pp2.getOrder_Policy() == null) {
            pp2.setOrder_Policy("LFL");
        }
        if (!this.isRequiredDRP()) {
            if (product.isPurchased()) {
                int C_BPartner_ID = 0;
                MProductPO[] ppos = MProductPO.getOfProduct((Properties)this.getCtx(), (int)product.getM_Product_ID(), (String)this.get_TrxName());
                for (int i = 0; i < ppos.length; ++i) {
                    if (!ppos[i].isCurrentVendor() || ppos[i].getC_BPartner_ID() == 0) continue;
                    C_BPartner_ID = ppos[i].getC_BPartner_ID();
                    pp2.setDeliveryTime_Promised(BigDecimal.valueOf(ppos[i].getDeliveryTime_Promised()));
                    pp2.setOrder_Min(ppos[i].getOrder_Min());
                    pp2.setOrder_Max(Env.ZERO);
                    pp2.setOrder_Pack(ppos[i].getOrder_Pack());
                    pp2.setC_BPartner_ID(C_BPartner_ID);
                    break;
                }
                if (C_BPartner_ID <= 0) {
                    this.createMRPNote("MRP-130", AD_Org_ID, 0, product, (String)null, (BigDecimal)null, (String)null);
                    pp2.setIsCreatePlan(false);
                }
            }
            if (product.isBOM() && pp2.getAD_Workflow_ID() <= 0) {
                this.log.info("Error: Do not exist workflow (" + product.getValue() + ")");
            }
        }
        return pp2;
    }

    protected BigDecimal getQtyOnHand(I_PP_Product_Planning pp) {
        return MPPMRP.getQtyOnHand(this.getCtx(), pp.getM_Warehouse_ID(), pp.getM_Product_ID(), this.get_TrxName());
    }

    protected Timestamp getToday() {
        return this.Today;
    }

    private void calculatePlan(int AD_Client_ID, int AD_Org_ID, int M_Warehouse_ID, int PP_MRP_ID, MProduct product, Timestamp DemandDateStartSchedule, int order_id, int ProductMO, int productmasterbom) throws SQLException {
        String comment;
        this.log.info("Create Plan ...");
        if (this.m_product_planning.getM_Product_ID() != product.get_ID()) {
            throw new IllegalStateException("MRP Internal Error: DataPlanningProduct(" + this.m_product_planning.getM_Product_ID() + ")" + " <> Product(" + (Object)product + ")");
        }
        BigDecimal yield = BigDecimal.valueOf(this.m_product_planning.getYield());
        if (yield.signum() != 0) {
            this.QtyGrossReqs = this.QtyGrossReqs.multiply(Env.ONEHUNDRED).divide(yield, 4, RoundingMode.HALF_UP);
        }
        BigDecimal QtyNetReqs = this.getNetRequirements(AD_Client_ID, AD_Org_ID, M_Warehouse_ID, product, DemandDateStartSchedule);
        System.out.println("qtynetfsfs" + QtyNetReqs);
        BigDecimal QtyPlanned = Env.ZERO;
        this.m_product_planning.dump();
        this.log.info("                    Product:" + (Object)product);
        this.log.info(" Demand Date Start Schedule:" + DemandDateStartSchedule);
        this.log.info("           DatePromisedFrom:" + this.DatePromisedFrom + " DatePromisedTo:" + this.DatePromisedTo);
        this.log.info("                Qty Planned:" + QtyPlanned);
        this.log.info("     Qty Scheduled Receipts:" + this.QtyScheduledReceipts);
        this.log.info("           QtyProjectOnHand:" + this.QtyProjectOnHand);
        this.log.info("               QtyGrossReqs:" + this.QtyGrossReqs);
        this.log.info("                     Supply:" + this.QtyScheduledReceipts.add(this.QtyProjectOnHand));
        this.log.info("                 QtyNetReqs:" + QtyNetReqs);
        if (QtyNetReqs.signum() > 0) {
            this.QtyProjectOnHand = QtyNetReqs;
            QtyNetReqs = Env.ZERO;
            this.QtyScheduledReceipts = Env.ZERO;
            QtyPlanned = Env.ZERO;
            this.QtyGrossReqs = Env.ZERO;
            return;
        }
        QtyPlanned = QtyNetReqs.negate();
        this.QtyGrossReqs = Env.ZERO;
        this.QtyScheduledReceipts = Env.ZERO;
        System.out.println("dsdsd" + QtyPlanned);
        if (QtyPlanned.signum() > 0 && this.m_product_planning.getOrder_Min().signum() > 0) {
            if (this.m_product_planning.getOrder_Min().compareTo(QtyPlanned) > 0) {
                comment = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"Order_Min")) + ":" + this.m_product_planning.getOrder_Min();
                this.createMRPNote("MRP-080", AD_Org_ID, PP_MRP_ID, product, null, QtyPlanned, comment);
            }
            QtyPlanned = QtyPlanned.max(this.m_product_planning.getOrder_Min());
        }
        if (this.m_product_planning.getOrder_Pack().signum() > 0 && QtyPlanned.signum() > 0) {
            QtyPlanned = this.m_product_planning.getOrder_Pack().multiply(QtyPlanned.divide(this.m_product_planning.getOrder_Pack(), 0, 0));
        }
        if (QtyPlanned.compareTo(this.m_product_planning.getOrder_Max()) > 0 && this.m_product_planning.getOrder_Max().signum() > 0) {
            comment = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"Order_Max")) + ":" + this.m_product_planning.getOrder_Max();
            this.createMRPNote("MRP-090", AD_Org_ID, PP_MRP_ID, product, null, QtyPlanned, comment);
        }
        this.QtyProjectOnHand = QtyPlanned.add(QtyNetReqs);
        this.log.info("QtyNetReqs:" + QtyNetReqs);
        this.log.info("QtyPlanned:" + QtyPlanned);
        this.log.info("QtyProjectOnHand:" + this.QtyProjectOnHand);
        if (this.TimeFence != null && DemandDateStartSchedule.compareTo(this.TimeFence) < 0) {
            comment = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"TimeFence")) + ":" + this.m_product_planning.getTimeFence() + "-" + Msg.getMsg((Properties)this.getCtx(), (String)"Date") + ":" + this.TimeFence + " " + Msg.translate((Properties)this.getCtx(), (String)"DatePromised") + ":" + DemandDateStartSchedule;
            this.createMRPNote("MRP-100", AD_Org_ID, PP_MRP_ID, product, null, QtyPlanned, comment);
        }
        if (!this.m_product_planning.isCreatePlan() && QtyPlanned.signum() > 0) {
            this.createMRPNote("MRP-020", AD_Org_ID, PP_MRP_ID, product, null, QtyPlanned, null);
            return;
        }
        if (QtyPlanned.signum() > 0) {
            int loops = 1;
            if (this.m_product_planning.getOrder_Policy().equals("FOQ")) {
                if (this.m_product_planning.getOrder_Qty().signum() != 0) {
                    loops = QtyPlanned.divide(this.m_product_planning.getOrder_Qty(), 0, 0).intValueExact();
                }
                QtyPlanned = this.m_product_planning.getOrder_Qty();
            }
            for (int ofq = 1; ofq <= loops; ++ofq) {
                this.log.info("Is Purchased: " + product.isPurchased() + " Is BOM: " + product.isBOM());
                try {
                    System.out.println("yoyo" + product.getName() + " " + QtyPlanned);
                    this.createSupply(AD_Org_ID, PP_MRP_ID, product, QtyPlanned, DemandDateStartSchedule, order_id, ProductMO, productmasterbom);
                    continue;
                }
                catch (Exception e) {
                    this.createMRPNote("MRP-160", AD_Org_ID, PP_MRP_ID, product, QtyPlanned, DemandDateStartSchedule, e);
                }
            }
        } else {
            this.log.info("No Create Plan");
        }
    }

    protected void createSupply(int AD_Org_ID, int PP_MRP_ID, MProduct product, BigDecimal QtyPlanned, Timestamp DemandDateStartSchedule, int orderid, int productMO, int productmasterbom) throws AdempiereException, SQLException {
        if (this.isRequiredDRP() && this.m_product_planning.getDD_NetworkDistribution_ID() > 0) {
            this.createDDOrder(AD_Org_ID, PP_MRP_ID, product, QtyPlanned, DemandDateStartSchedule);
        } else if (product.isPurchased() && !product.isBOM()) {
            this.createRequisition(AD_Org_ID, PP_MRP_ID, product, QtyPlanned, DemandDateStartSchedule, orderid, productMO, productmasterbom);
        } else if (product.isBOM()) {
            this.createPPOrder(AD_Org_ID, PP_MRP_ID, product, QtyPlanned, DemandDateStartSchedule, productMO);
        } else {
            throw new IllegalStateException("MRP Internal Error: Don't know what document to create for " + (Object)product + "(" + (Object)this.m_product_planning + ")");
        }
    }

    protected void createDDOrder(int AD_Org_ID, int PP_MRP_ID, MProduct product, BigDecimal QtyPlanned, Timestamp DemandDateStartSchedule) throws AdempiereException, SQLException {
        if (this.m_product_planning.getDD_NetworkDistribution_ID() == 0) {
            this.createMRPNote("DRP-060", AD_Org_ID, PP_MRP_ID, product, (String)null, (BigDecimal)null, (String)null);
        }
        MDDNetworkDistribution network = MDDNetworkDistribution.get(this.getCtx(), this.m_product_planning.getDD_NetworkDistribution_ID());
        MDDNetworkDistributionLine[] network_lines = network.getLines(this.m_product_planning.getM_Warehouse_ID());
        int M_Shipper_ID = 0;
        MDDOrder order = null;
        Integer DD_Order_ID = 0;
        MDDNetworkDistributionLine[] arrmDDNetworkDistributionLine = network_lines;
        int n = network_lines.length;
        for (int i = 0; i < n; ++i) {
            String comment;
            MDDNetworkDistributionLine network_line = arrmDDNetworkDistributionLine[i];
            MWarehouse source = MWarehouse.get((Properties)this.getCtx(), (int)network_line.getM_WarehouseSource_ID());
            MLocator locator = source.getDefaultLocator();
            MWarehouse target = MWarehouse.get((Properties)this.getCtx(), (int)network_line.getM_Warehouse_ID());
            MLocator locator_to = target.getDefaultLocator();
            BigDecimal transfertTime = network_line.getTransfertTime();
            if (transfertTime.compareTo(Env.ZERO) <= 0) {
                transfertTime = this.m_product_planning.getTransfertTime();
            }
            if (locator == null || locator_to == null) {
                String comment2 = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"M_WarehouseSource_ID")) + ":" + source.getName();
                this.createMRPNote("DRP-001", AD_Org_ID, PP_MRP_ID, product, null, null, comment2);
                continue;
            }
            MWarehouse[] wsts = MWarehouse.getInTransitForOrg((Properties)this.getCtx(), (int)source.getAD_Org_ID());
            if (wsts == null || wsts.length == 0) {
                comment = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"Name")) + ":" + MOrg.get((Properties)this.getCtx(), (int)AD_Org_ID).getName();
                this.createMRPNote("DRP-010", AD_Org_ID, PP_MRP_ID, product, null, null, comment);
                continue;
            }
            if (network_line.getM_Shipper_ID() == 0) {
                comment = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"Name")) + ":" + network.getName();
                this.createMRPNote("DRP-030", AD_Org_ID, PP_MRP_ID, product, null, null, comment);
                continue;
            }
            if (M_Shipper_ID != network_line.getM_Shipper_ID()) {
                MOrg org = MOrg.get((Properties)this.getCtx(), (int)locator_to.getAD_Org_ID());
                int C_BPartner_ID = org.getLinkedC_BPartner_ID(this.get_TrxName());
                if (C_BPartner_ID == 0) {
                    String comment3 = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"Name")) + ":" + MOrg.get((Properties)this.getCtx(), (int)AD_Org_ID).getName();
                    this.createMRPNote("DRP-020", AD_Org_ID, PP_MRP_ID, product, null, null, comment3);
                    continue;
                }
                MBPartner bp = this.getBPartner(C_BPartner_ID);
                DD_Order_ID = this.getDDOrder_ID(AD_Org_ID, wsts[0].get_ID(), network_line.getM_Shipper_ID(), bp.getC_BPartner_ID(), DemandDateStartSchedule);
                if (DD_Order_ID <= 0) {
                    order = new MDDOrder(this.getCtx(), 0, this.get_TrxName());
                    order.setAD_Org_ID(target.getAD_Org_ID());
                    order.setC_BPartner_ID(C_BPartner_ID);
                    order.setAD_User_ID(bp.getPrimaryAD_User_ID());
                    order.setC_DocType_ID(this.docTypeDO_ID);
                    order.setM_Warehouse_ID(wsts[0].get_ID());
                    order.setDocAction("CO");
                    order.setDateOrdered(this.getToday());
                    order.setDatePromised(DemandDateStartSchedule);
                    order.setM_Shipper_ID(network_line.getM_Shipper_ID());
                    order.setIsInDispute(false);
                    order.setIsInTransit(false);
                    order.setSalesRep_ID(this.m_product_planning.getPlanner_ID());
                    order.saveEx();
                    DD_Order_ID = order.get_ID();
                    String key = String.valueOf(order.getAD_Org_ID()) + "#" + order.getM_Warehouse_ID() + "#" + network_line.getM_Shipper_ID() + "#" + C_BPartner_ID + "#" + DemandDateStartSchedule + "DR";
                    dd_order_id_cache.put((Object)key, (Object)DD_Order_ID);
                } else {
                    order = new MDDOrder(this.getCtx(), DD_Order_ID.intValue(), this.get_TrxName());
                }
                M_Shipper_ID = network_line.getM_Shipper_ID();
            }
            BigDecimal QtyOrdered = QtyPlanned.multiply(network_line.getPercent()).divide(Env.ONEHUNDRED);
            MDDOrderLine oline = new MDDOrderLine(this.getCtx(), 0, this.get_TrxName());
            oline.setDD_Order_ID(order.getDD_Order_ID());
            oline.setAD_Org_ID(target.getAD_Org_ID());
            oline.setM_Locator_ID(locator.getM_Locator_ID());
            oline.setM_LocatorTo_ID(locator_to.getM_Locator_ID());
            oline.setM_Product_ID(this.m_product_planning.getM_Product_ID());
            oline.setDateOrdered(this.getToday());
            oline.setDatePromised(DemandDateStartSchedule);
            oline.setQtyEntered(QtyOrdered);
            oline.setQtyOrdered(QtyOrdered);
            oline.setTargetQty(MPPMRP.getQtyReserved(this.getCtx(), target.getM_Warehouse_ID(), this.m_product_planning.getM_Product_ID(), DemandDateStartSchedule, this.get_TrxName()));
            oline.setIsInvoiced(false);
            oline.saveEx();
            List mrpList = new Query(this.getCtx(), "PP_MRP", "DD_OrderLine_ID=?", this.get_TrxName()).setParameters(new Object[]{oline.getDD_OrderLine_ID()}).list();
            for (MPPMRP mrp : mrpList) {
                mrp.setDateOrdered(this.getToday());
                mrp.setS_Resource_ID(this.m_product_planning.getS_Resource_ID());
                mrp.setDatePromised(TimeUtil.addDays((Timestamp)DemandDateStartSchedule, (int)this.m_product_planning.getDeliveryTime_Promised().add(transfertTime).negate().intValueExact()));
                mrp.setDateFinishSchedule(DemandDateStartSchedule);
                mrp.saveEx();
            }
            ++this.count_DO;
        }
        this.commitEx();
    }

    protected void createRequisition(int AD_Org_ID, int PP_MRP_ID, MProduct product, BigDecimal QtyPlanned, Timestamp DemandDateStartSchedule, int order_id, int ProductMO, int productmasterbom) throws AdempiereException, SQLException {
        this.log.info("Create Requisition");
        int duration = MPPMRP.getDurationDays(null, QtyPlanned, (I_PP_Product_Planning)this.m_product_planning);
        System.out.println("sfaaga" + duration);
        int M_PriceList_ID = -1;
        if (this.m_product_planning.getC_BPartner_ID() > 0) {
            M_PriceList_ID = DB.getSQLValueEx((String)this.get_TrxName(), (String)"SELECT COALESCE(bp.PO_PriceList_ID,bpg.PO_PriceList_ID) FROM C_BPartner bp INNER JOIN C_BP_Group bpg ON (bpg.C_BP_Group_ID=bp.C_BP_Group_ID) WHERE bp.C_BPartner_ID=?", (Object[])new Object[]{this.m_product_planning.getC_BPartner_ID()});
        }
        MRequisition req = new MRequisition(this.getCtx(), 0, this.get_TrxName());
        req.setAD_Org_ID(AD_Org_ID);
        req.setAD_User_ID(this.m_product_planning.getPlanner_ID());
        req.setDateRequired(TimeUtil.addDays((Timestamp)DemandDateStartSchedule, (int)(0 - duration)));
        req.setDescription("Requisition generated from MRP");
        req.setM_Warehouse_ID(this.m_product_planning.getM_Warehouse_ID());
        req.setC_DocType_ID(this.docTypeReq_ID);
        if (M_PriceList_ID > 0) {
            req.setM_PriceList_ID(M_PriceList_ID);
        }
        req.saveEx();
        MRequisitionLine reqline = new MRequisitionLine(req);
        reqline.setLine(10);
        reqline.setAD_Org_ID(AD_Org_ID);
        reqline.setC_BPartner_ID(this.m_product_planning.getC_BPartner_ID());
        reqline.setM_Product_ID(this.m_product_planning.getM_Product_ID());
        reqline.setPrice();
        reqline.setPriceActual(Env.ZERO);
        reqline.setQty(QtyPlanned);
        reqline.saveEx();
        List mrpList = new Query(this.getCtx(), "PP_MRP", "M_Requisition_ID=?", this.get_TrxName()).setParameters(new Object[]{req.getM_Requisition_ID()}).list();
        for (MPPMRP mrp : mrpList) {
            mrp.setDateOrdered(this.getToday());
            mrp.setS_Resource_ID(this.m_product_planning.getS_Resource_ID());
            mrp.setDatePromised(req.getDateRequired());
            mrp.setDateStartSchedule(req.getDateRequired());
            mrp.setDateFinishSchedule(DemandDateStartSchedule);
            mrp.set_CustomColumn("PP_OrderRef_ID", ProductMO);
            if (order_id > 0) {
                mrp.setC_Order_ID(order_id);
            }
            mrp.saveEx();
        }
        this.commitEx();
        ++this.count_MR;
    }

    protected void createPPOrder(int AD_Org_ID, int PP_MRP_ID, MProduct product, BigDecimal QtyPlanned, Timestamp DemandDateStartSchedule, int ProductMO) throws AdempiereException, SQLException {
        this.log.info("PP_Product_BOM_ID:" + this.m_product_planning.getPP_Product_BOM_ID() + ", AD_Workflow_ID:" + this.m_product_planning.getAD_Workflow_ID() + ", product_planning:" + (Object)this.m_product_planning);
        if (this.m_product_planning.getPP_Product_BOM_ID() == 0 || this.m_product_planning.getAD_Workflow_ID() == 0) {
            throw new AdempiereException("@FillMandatory@ @PP_Product_BOM_ID@, @AD_Workflow_ID@ ( @M_Product_ID@=" + product.getValue() + ")");
        }
        MPPOrder order = new MPPOrder(this.getCtx(), 0, this.get_TrxName());
        order.addDescription("MO generated from MRP");
        order.setAD_Org_ID(AD_Org_ID);
        order.setLine(10);
        if ("M".equals(this.getBOMType())) {
            this.log.info("Maintenance Order Created");
            order.setC_DocTypeTarget_ID(this.docTypeMF_ID);
            order.setC_DocType_ID(this.docTypeMF_ID);
        } else {
            this.log.info("Manufacturing Order Created");
            order.setC_DocTypeTarget_ID(this.docTypeMO_ID);
            order.setC_DocType_ID(this.docTypeMO_ID);
        }
        order.setS_Resource_ID(this.m_product_planning.getS_Resource_ID());
        order.setM_Warehouse_ID(this.m_product_planning.getM_Warehouse_ID());
        order.setM_Product_ID(this.m_product_planning.getM_Product_ID());
        order.setM_AttributeSetInstance_ID(0);
        order.setPP_Product_BOM_ID(this.m_product_planning.getPP_Product_BOM_ID());
        order.setAD_Workflow_ID(this.m_product_planning.getAD_Workflow_ID());
        order.setPlanner_ID(this.m_product_planning.getPlanner_ID());
        order.setDateOrdered(this.getToday());
        order.setDatePromised(DemandDateStartSchedule);
        int duration = 0;
        order.setDateStartSchedule(TimeUtil.addDays((Timestamp)DemandDateStartSchedule, (int)(0 - duration)));
        order.setDateFinishSchedule(DemandDateStartSchedule);
        order.setQty(QtyPlanned);
        order.setC_UOM_ID(product.getC_UOM_ID());
        order.setYield(Env.ZERO);
        order.setScheduleType("D");
        order.setPriorityRule("5");
        order.setDocAction("CO");
        order.saveEx();
        List mrpList = new Query(this.getCtx(), "PP_MRP", "PP_Order_ID=?", this.get_TrxName()).setParameters(new Object[]{order.getPP_Order_ID()}).list();
        for (MPPMRP mrp : mrpList) {
            mrp.set_CustomColumn("PP_OrderRef_ID", ProductMO);
            mrp.saveEx();
        }
        this.commitEx();
        if (this.generatebom > 0) {
            TampungDataBom tampungDataBom = new TampungDataBom();
            tampungDataBom.setProductparent(this.m_product_planning.getM_Product_ID());
            tampungDataBom.setQtyrequired(QtyPlanned);
            this.list.add(tampungDataBom);
            this.generatebom = 1;
        }
        ++this.count_MO;
    }

    private void deletePO(String tableName, String whereClause, Object[] params) throws SQLException {
        try (POResultSet rs = new Query(this.getCtx(), tableName, whereClause, this.get_TrxName()).setParameters(params).scroll();){
            while (rs.hasNext()) {
                rs.next().deleteEx(true);
            }
        }
        this.commitEx();
    }

    protected void createMRPNote(String code, int AD_Org_ID, int PP_MRP_ID, MProduct product, String documentNo, BigDecimal qty, String comment) throws SQLException {
        documentNo = documentNo != null ? documentNo : "";
        comment = comment != null ? comment : "";
        qty = qty != null ? qty : Env.ZERO;
        MMessage msg = MMessage.get((Properties)this.getCtx(), (String)code);
        if (msg == null) {
            msg = MMessage.get((Properties)this.getCtx(), (String)"MRP-999");
        }
        String message = Msg.getMsg((Properties)this.getCtx(), (String)msg.getValue());
        int user_id = 0;
        if (this.m_product_planning != null) {
            user_id = this.m_product_planning.getPlanner_ID();
        }
        String reference = "";
        if (product != null) {
            reference = String.valueOf(product.getValue()) + " " + product.getName();
        }
        if (!Util.isEmpty((String)documentNo, (boolean)true)) {
            message = String.valueOf(message) + " " + Msg.translate((Properties)this.getCtx(), (String)"DocumentNo") + ":" + documentNo;
        }
        if (qty != null) {
            message = String.valueOf(message) + " " + Msg.translate((Properties)this.getCtx(), (String)"QtyPlan") + ":" + qty;
        }
        if (!Util.isEmpty((String)comment, (boolean)true)) {
            message = String.valueOf(message) + " " + comment;
        }
        MNote note = new MNote(this.getCtx(), msg.getAD_Message_ID(), user_id, 53043, PP_MRP_ID, reference, message, this.get_TrxName());
        note.setAD_Org_ID(AD_Org_ID);
        note.saveEx();
        this.commitEx();
        this.log.info(String.valueOf(code) + ": " + note.getTextMsg());
        ++this.count_Msg;
    }

    private void createMRPNote(String code, MPPMRP mrp, MProduct product, String comment) throws SQLException {
        this.createMRPNote(code, mrp.getAD_Org_ID(), mrp.get_ID(), product, MPPMRP.getDocumentNo(mrp.get_ID()), mrp.getQty(), comment);
    }

    protected void createMRPNote(String code, int AD_Org_ID, int PP_MRP_ID, MProduct product, BigDecimal qty, Timestamp DemandDateStartSchedule, Exception e) throws SQLException {
        String documentNo = null;
        String comment = e.getLocalizedMessage();
        this.createMRPNote(code, AD_Org_ID, PP_MRP_ID, product, documentNo, qty, comment);
    }

    private int getDDOrder_ID(int AD_Org_ID, int M_Warehouse_ID, int M_Shipper_ID, int C_BPartner_ID, Timestamp DatePromised) {
        String key = String.valueOf(AD_Org_ID) + "#" + M_Warehouse_ID + "#" + M_Shipper_ID + "#" + C_BPartner_ID + "#" + DatePromised + "DR";
        Integer order_id = (Integer)dd_order_id_cache.get((Object)key.toString());
        if (order_id == null) {
            String sql = "SELECT DD_Order_ID FROM DD_Order WHERE AD_Org_ID=? AND M_Warehouse_ID=? AND M_Shipper_ID = ? AND C_BPartner_ID=? AND DatePromised=? AND DocStatus=?";
            order_id = DB.getSQLValueEx((String)this.get_TrxName(), (String)sql, (Object[])new Object[]{AD_Org_ID, M_Warehouse_ID, M_Shipper_ID, C_BPartner_ID, DatePromised, "DR"});
            if (order_id > 0) {
                dd_order_id_cache.put((Object)key, (Object)order_id);
            }
        }
        return order_id;
    }

    private MBPartner getBPartner(int C_BPartner_ID) {
        MBPartner partner = (MBPartner)partner_cache.get((Object)C_BPartner_ID);
        if (partner == null) {
            partner = MBPartner.get((Properties)this.getCtx(), (int)C_BPartner_ID);
            partner_cache.put((Object)C_BPartner_ID, (Object)partner);
        }
        return partner;
    }

    private BigDecimal getNetRequirements(int AD_Client_ID, int AD_Org_ID, int M_Warehouse_ID, MProduct product, Timestamp DemandDateStartSchedule) throws SQLException {
        BigDecimal QtyNetReqs = this.QtyProjectOnHand.subtract(this.QtyGrossReqs);
        ArrayList<Object> parameters = new ArrayList<Object>();
        parameters.add(AD_Client_ID);
        parameters.add(AD_Org_ID);
        parameters.add(product.get_ID());
        parameters.add(M_Warehouse_ID);
        parameters.add("S");
        parameters.add("CO");
        parameters.add("IP");
        parameters.add("DR");
        parameters.add(true);
        List mrps = new Query(this.getCtx(), "PP_MRP", "AD_Client_ID=? AND AD_Org_ID=? AND M_Product_ID=? AND M_Warehouse_ID=? AND TypeMRP=? AND DocStatus IN (?,?,?) AND Qty<>0 AND IsAvailable=?", this.get_TrxName()).setParameters(parameters).setOrderBy("DateStartSchedule").list();
        for (MPPMRP mrp : mrps) {
            String comment;
            if (mrp.isReleased()) {
                this.QtyScheduledReceipts = this.QtyScheduledReceipts.add(mrp.getQty());
            }
            if (DemandDateStartSchedule != null) {
                if (mrp.isReleased() && QtyNetReqs.negate().signum() > 0 && mrp.getDateStartSchedule() != null && mrp.getDateStartSchedule().compareTo(DemandDateStartSchedule) < 0) {
                    comment = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"DateStartSchedule")) + ":" + mrp.getDateStartSchedule() + " " + Msg.translate((Properties)this.getCtx(), (String)"DatePromised") + ":" + DemandDateStartSchedule;
                    this.createMRPNote("MRP-030", mrp, product, comment);
                }
                if (mrp.isReleased() && QtyNetReqs.negate().signum() > 0 && mrp.getDateStartSchedule() != null && mrp.getDateStartSchedule().compareTo(DemandDateStartSchedule) > 0) {
                    comment = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"DateStartSchedule")) + ":" + mrp.getDateStartSchedule() + " " + Msg.translate((Properties)this.getCtx(), (String)"DatePromised") + ":" + DemandDateStartSchedule;
                    this.createMRPNote("MRP-040", mrp, product, comment);
                }
                if (!mrp.isReleased() && QtyNetReqs.negate().signum() > 0 && mrp.getDateStartSchedule() != null && mrp.getDatePromised().compareTo(this.getToday()) >= 0) {
                    comment = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"DatePromised")) + ":" + mrp.getDatePromised();
                    this.createMRPNote("MRP-060", mrp, product, comment);
                }
                if (!mrp.isReleased() && QtyNetReqs.negate().signum() > 0 && mrp.getDateStartSchedule() != null && mrp.getDatePromised().compareTo(this.getToday()) < 0) {
                    comment = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"DatePromised")) + ":" + mrp.getDatePromised();
                    this.createMRPNote("MRP-070", mrp, product, comment);
                }
                if (mrp.isReleased() && mrp.getDateStartSchedule() != null && mrp.getDatePromised().compareTo(this.getToday()) < 0) {
                    comment = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"DatePromised")) + ":" + mrp.getDatePromised();
                    this.createMRPNote("MRP-110", mrp, product, comment);
                }
                mrp.setIsAvailable(false);
                mrp.saveEx();
                QtyNetReqs = QtyNetReqs.add(mrp.getQty());
                if (QtyNetReqs.signum() < 0) continue;
                return QtyNetReqs;
            }
            if (mrp.isReleased() && this.QtyScheduledReceipts.signum() > 0) {
                comment = String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"DatePromised")) + ":" + mrp.getDatePromised();
                this.createMRPNote("MRP-050", mrp, product, comment);
            }
            mrp.setIsAvailable(false);
            mrp.saveEx();
            QtyNetReqs = QtyNetReqs.add(mrp.getQty());
        }
        return QtyNetReqs;
    }

    protected int getDocType(String docBaseType, int AD_Org_ID) {
        MDocType[] docs = MDocType.getOfDocBaseType((Properties)this.getCtx(), (String)docBaseType);
        if (docs == null || docs.length == 0) {
            String reference = Msg.getMsg((Properties)this.getCtx(), (String)"SequenceDocNotFound");
            String textMsg = "Not found default document type for docbasetype " + docBaseType;
            MNote note = new MNote(this.getCtx(), MMessage.getAD_Message_ID((Properties)this.getCtx(), (String)"SequenceDocNotFound"), this.getPlanner_ID(), 53043, 0, reference, textMsg, this.get_TrxName());
            note.saveEx();
            throw new AdempiereException(textMsg);
        }
        MDocType[] arrmDocType = docs;
        int n = docs.length;
        for (int i = 0; i < n; ++i) {
            MDocType doc = arrmDocType[i];
            if (doc.getAD_Org_ID() != AD_Org_ID) continue;
            return doc.getC_DocType_ID();
        }
        this.log.info("Doc Type for " + docBaseType + ": " + docs[0].getC_DocType_ID());
        return docs[0].getC_DocType_ID();
    }

    private String getBOMType() {
        if (this.m_product_planning == null || this.m_product_planning.getPP_Product_BOM_ID() == 0) {
            return null;
        }
        String BOMType = DB.getSQLValueString((String)this.get_TrxName(), (String)"SELECT BOMType FROM PP_Product_BOM WHERE PP_Product_BOM_ID = ?", (int)this.m_product_planning.getPP_Product_BOM_ID());
        return BOMType;
    }
}

