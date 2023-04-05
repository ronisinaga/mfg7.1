/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_User
 *  org.compiere.model.I_AD_Workflow
 *  org.compiere.model.I_C_Activity
 *  org.compiere.model.I_C_Campaign
 *  org.compiere.model.I_C_DocType
 *  org.compiere.model.I_C_ElementValue
 *  org.compiere.model.I_C_OrderLine
 *  org.compiere.model.I_C_Project
 *  org.compiere.model.I_C_UOM
 *  org.compiere.model.I_M_AttributeSetInstance
 *  org.compiere.model.I_M_Product
 *  org.compiere.model.I_M_Warehouse
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.I_S_Resource
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 *  org.compiere.util.Env
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_PP_Product_BOM
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_Campaign;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_Persistent;
import org.compiere.model.I_S_Resource;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_PP_Product_BOM;
import org.libero.tables.I_PP_Order;

public class X_PP_Order
extends PO
implements I_PP_Order,
I_Persistent {
    private static final long serialVersionUID = 20130626L;
    public static final int DOCACTION_AD_Reference_ID = 135;
    public static final String DOCACTION_Complete = "CO";
    public static final String DOCACTION_Approve = "AP";
    public static final String DOCACTION_Reject = "RJ";
    public static final String DOCACTION_Post = "PO";
    public static final String DOCACTION_Void = "VO";
    public static final String DOCACTION_Close = "CL";
    public static final String DOCACTION_Reverse_Correct = "RC";
    public static final String DOCACTION_Reverse_Accrual = "RA";
    public static final String DOCACTION_Invalidate = "IN";
    public static final String DOCACTION_Re_Activate = "RE";
    public static final String DOCACTION_None = "--";
    public static final String DOCACTION_Prepare = "PR";
    public static final String DOCACTION_Unlock = "XL";
    public static final String DOCACTION_WaitComplete = "WC";
    public static final int DOCSTATUS_AD_Reference_ID = 131;
    public static final String DOCSTATUS_Drafted = "DR";
    public static final String DOCSTATUS_Completed = "CO";
    public static final String DOCSTATUS_Approved = "AP";
    public static final String DOCSTATUS_NotApproved = "NA";
    public static final String DOCSTATUS_Voided = "VO";
    public static final String DOCSTATUS_Invalid = "IN";
    public static final String DOCSTATUS_Reversed = "RE";
    public static final String DOCSTATUS_Closed = "CL";
    public static final String DOCSTATUS_Unknown = "??";
    public static final String DOCSTATUS_InProgress = "IP";
    public static final String DOCSTATUS_WaitingPayment = "WP";
    public static final String DOCSTATUS_WaitingConfirmation = "WC";
    public static final int PRIORITYRULE_AD_Reference_ID = 154;
    public static final String PRIORITYRULE_High = "3";
    public static final String PRIORITYRULE_Medium = "5";
    public static final String PRIORITYRULE_Low = "7";
    public static final String PRIORITYRULE_Urgent = "1";
    public static final String PRIORITYRULE_Minor = "9";

    public X_PP_Order(Properties ctx, int PP_Order_ID, String trxName) {
        super(ctx, PP_Order_ID, trxName);
    }

    public X_PP_Order(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53027, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_PP_Order[").append(this.get_ID()).append("]");
        return sb.toString();
    }

    @Override
    public void setAD_OrgTrx_ID(int AD_OrgTrx_ID) {
        if (AD_OrgTrx_ID < 1) {
            this.set_Value("AD_OrgTrx_ID", null);
        } else {
            this.set_Value("AD_OrgTrx_ID", AD_OrgTrx_ID);
        }
    }

    @Override
    public int getAD_OrgTrx_ID() {
        Integer ii = (Integer)this.get_Value("AD_OrgTrx_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_AD_Workflow getAD_Workflow() throws RuntimeException {
        return (I_AD_Workflow)MTable.get((Properties)this.getCtx(), (String)"AD_Workflow").getPO(this.getAD_Workflow_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_Workflow_ID(int AD_Workflow_ID) {
        if (AD_Workflow_ID < 1) {
            this.set_ValueNoCheck("AD_Workflow_ID", null);
        } else {
            this.set_ValueNoCheck("AD_Workflow_ID", AD_Workflow_ID);
        }
    }

    @Override
    public int getAD_Workflow_ID() {
        Integer ii = (Integer)this.get_Value("AD_Workflow_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setAssay(BigDecimal Assay) {
        this.set_Value("Assay", Assay);
    }

    @Override
    public BigDecimal getAssay() {
        BigDecimal bd = (BigDecimal)this.get_Value("Assay");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public I_C_Activity getC_Activity() throws RuntimeException {
        return (I_C_Activity)MTable.get((Properties)this.getCtx(), (String)"C_Activity").getPO(this.getC_Activity_ID(), this.get_TrxName());
    }

    @Override
    public void setC_Activity_ID(int C_Activity_ID) {
        if (C_Activity_ID < 1) {
            this.set_Value("C_Activity_ID", null);
        } else {
            this.set_Value("C_Activity_ID", C_Activity_ID);
        }
    }

    @Override
    public int getC_Activity_ID() {
        Integer ii = (Integer)this.get_Value("C_Activity_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_C_Campaign getC_Campaign() throws RuntimeException {
        return (I_C_Campaign)MTable.get((Properties)this.getCtx(), (String)"C_Campaign").getPO(this.getC_Campaign_ID(), this.get_TrxName());
    }

    @Override
    public void setC_Campaign_ID(int C_Campaign_ID) {
        if (C_Campaign_ID < 1) {
            this.set_Value("C_Campaign_ID", null);
        } else {
            this.set_Value("C_Campaign_ID", C_Campaign_ID);
        }
    }

    @Override
    public int getC_Campaign_ID() {
        Integer ii = (Integer)this.get_Value("C_Campaign_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_C_DocType getC_DocType() throws RuntimeException {
        return (I_C_DocType)MTable.get((Properties)this.getCtx(), (String)"C_DocType").getPO(this.getC_DocType_ID(), this.get_TrxName());
    }

    @Override
    public void setC_DocType_ID(int C_DocType_ID) {
        if (C_DocType_ID < 0) {
            this.set_Value("C_DocType_ID", null);
        } else {
            this.set_Value("C_DocType_ID", C_DocType_ID);
        }
    }

    @Override
    public int getC_DocType_ID() {
        Integer ii = (Integer)this.get_Value("C_DocType_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_C_DocType getC_DocTypeTarget() throws RuntimeException {
        return (I_C_DocType)MTable.get((Properties)this.getCtx(), (String)"C_DocType").getPO(this.getC_DocTypeTarget_ID(), this.get_TrxName());
    }

    @Override
    public void setC_DocTypeTarget_ID(int C_DocTypeTarget_ID) {
        if (C_DocTypeTarget_ID < 1) {
            this.set_ValueNoCheck("C_DocTypeTarget_ID", null);
        } else {
            this.set_ValueNoCheck("C_DocTypeTarget_ID", C_DocTypeTarget_ID);
        }
    }

    @Override
    public int getC_DocTypeTarget_ID() {
        Integer ii = (Integer)this.get_Value("C_DocTypeTarget_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setCopyFrom(String CopyFrom) {
        this.set_Value("CopyFrom", CopyFrom);
    }

    @Override
    public String getCopyFrom() {
        return (String)this.get_Value("CopyFrom");
    }

    @Override
    public I_C_OrderLine getC_OrderLine() throws RuntimeException {
        return (I_C_OrderLine)MTable.get((Properties)this.getCtx(), (String)"C_OrderLine").getPO(this.getC_OrderLine_ID(), this.get_TrxName());
    }

    @Override
    public void setC_OrderLine_ID(int C_OrderLine_ID) {
        if (C_OrderLine_ID < 1) {
            this.set_Value("C_OrderLine_ID", null);
        } else {
            this.set_Value("C_OrderLine_ID", C_OrderLine_ID);
        }
    }

    @Override
    public int getC_OrderLine_ID() {
        Integer ii = (Integer)this.get_Value("C_OrderLine_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_C_Project getC_Project() throws RuntimeException {
        return (I_C_Project)MTable.get((Properties)this.getCtx(), (String)"C_Project").getPO(this.getC_Project_ID(), this.get_TrxName());
    }

    @Override
    public void setC_Project_ID(int C_Project_ID) {
        if (C_Project_ID < 1) {
            this.set_Value("C_Project_ID", null);
        } else {
            this.set_Value("C_Project_ID", C_Project_ID);
        }
    }

    @Override
    public int getC_Project_ID() {
        Integer ii = (Integer)this.get_Value("C_Project_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_C_UOM getC_UOM() throws RuntimeException {
        return (I_C_UOM)MTable.get((Properties)this.getCtx(), (String)"C_UOM").getPO(this.getC_UOM_ID(), this.get_TrxName());
    }

    @Override
    public void setC_UOM_ID(int C_UOM_ID) {
        if (C_UOM_ID < 1) {
            this.set_ValueNoCheck("C_UOM_ID", null);
        } else {
            this.set_ValueNoCheck("C_UOM_ID", C_UOM_ID);
        }
    }

    @Override
    public int getC_UOM_ID() {
        Integer ii = (Integer)this.get_Value("C_UOM_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setDateConfirm(Timestamp DateConfirm) {
        this.set_ValueNoCheck("DateConfirm", DateConfirm);
    }

    @Override
    public Timestamp getDateConfirm() {
        return (Timestamp)this.get_Value("DateConfirm");
    }

    @Override
    public void setDateDelivered(Timestamp DateDelivered) {
        this.set_ValueNoCheck("DateDelivered", DateDelivered);
    }

    @Override
    public Timestamp getDateDelivered() {
        return (Timestamp)this.get_Value("DateDelivered");
    }

    @Override
    public void setDateFinish(Timestamp DateFinish) {
        this.set_ValueNoCheck("DateFinish", DateFinish);
    }

    @Override
    public Timestamp getDateFinish() {
        return (Timestamp)this.get_Value("DateFinish");
    }

    @Override
    public void setDateFinishSchedule(Timestamp DateFinishSchedule) {
        this.set_Value("DateFinishSchedule", DateFinishSchedule);
    }

    @Override
    public Timestamp getDateFinishSchedule() {
        return (Timestamp)this.get_Value("DateFinishSchedule");
    }

    @Override
    public void setDateOrdered(Timestamp DateOrdered) {
        this.set_Value("DateOrdered", DateOrdered);
    }

    @Override
    public Timestamp getDateOrdered() {
        return (Timestamp)this.get_Value("DateOrdered");
    }

    @Override
    public void setDatePromised(Timestamp DatePromised) {
        this.set_Value("DatePromised", DatePromised);
    }

    @Override
    public Timestamp getDatePromised() {
        return (Timestamp)this.get_Value("DatePromised");
    }

    @Override
    public void setDateStart(Timestamp DateStart) {
        this.set_ValueNoCheck("DateStart", DateStart);
    }

    @Override
    public Timestamp getDateStart() {
        return (Timestamp)this.get_Value("DateStart");
    }

    @Override
    public void setDateStartSchedule(Timestamp DateStartSchedule) {
        this.set_Value("DateStartSchedule", DateStartSchedule);
    }

    @Override
    public Timestamp getDateStartSchedule() {
        return (Timestamp)this.get_Value("DateStartSchedule");
    }

    @Override
    public void setDescription(String Description) {
        this.set_Value("Description", Description);
    }

    @Override
    public String getDescription() {
        return (String)this.get_Value("Description");
    }

    @Override
    public void setDocAction(String DocAction2) {
        this.set_Value("DocAction", DocAction2);
    }

    @Override
    public String getDocAction() {
        return (String)this.get_Value("DocAction");
    }

    @Override
    public void setDocStatus(String DocStatus) {
        this.set_Value("DocStatus", DocStatus);
    }

    @Override
    public String getDocStatus() {
        return (String)this.get_Value("DocStatus");
    }

    @Override
    public void setDocumentNo(String DocumentNo) {
        this.set_Value("DocumentNo", DocumentNo);
    }

    @Override
    public String getDocumentNo() {
        return (String)this.get_Value("DocumentNo");
    }

    public KeyNamePair getKeyNamePair() {
        return new KeyNamePair(this.get_ID(), this.getDocumentNo());
    }

    @Override
    public void setFloatAfter(BigDecimal FloatAfter) {
        this.set_Value("FloatAfter", FloatAfter);
    }

    @Override
    public BigDecimal getFloatAfter() {
        BigDecimal bd = (BigDecimal)this.get_Value("FloatAfter");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setFloatBefored(BigDecimal FloatBefored) {
        this.set_Value("FloatBefored", FloatBefored);
    }

    @Override
    public BigDecimal getFloatBefored() {
        BigDecimal bd = (BigDecimal)this.get_Value("FloatBefored");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setIsApproved(boolean IsApproved) {
        this.set_Value("IsApproved", IsApproved);
    }

    @Override
    public boolean isApproved() {
        Object oo = this.get_Value("IsApproved");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIsPrinted(boolean IsPrinted) {
        this.set_Value("IsPrinted", IsPrinted);
    }

    @Override
    public boolean isPrinted() {
        Object oo = this.get_Value("IsPrinted");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIsQtyPercentage(boolean IsQtyPercentage) {
        this.set_Value("IsQtyPercentage", IsQtyPercentage);
    }

    @Override
    public boolean isQtyPercentage() {
        Object oo = this.get_Value("IsQtyPercentage");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIsSelected(boolean IsSelected) {
        this.set_Value("IsSelected", IsSelected);
    }

    @Override
    public boolean isSelected() {
        Object oo = this.get_Value("IsSelected");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIsSOTrx(boolean IsSOTrx) {
        this.set_Value("IsSOTrx", IsSOTrx);
    }

    @Override
    public boolean isSOTrx() {
        Object oo = this.get_Value("IsSOTrx");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setLine(int Line) {
        this.set_Value("Line", Line);
    }

    @Override
    public int getLine() {
        Integer ii = (Integer)this.get_Value("Line");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setLot(String Lot) {
        this.set_Value("Lot", Lot);
    }

    @Override
    public String getLot() {
        return (String)this.get_Value("Lot");
    }

    @Override
    public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException {
        return (I_M_AttributeSetInstance)MTable.get((Properties)this.getCtx(), (String)"M_AttributeSetInstance").getPO(this.getM_AttributeSetInstance_ID(), this.get_TrxName());
    }

    @Override
    public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
        if (M_AttributeSetInstance_ID < 0) {
            this.set_Value("M_AttributeSetInstance_ID", null);
        } else {
            this.set_Value("M_AttributeSetInstance_ID", M_AttributeSetInstance_ID);
        }
    }

    @Override
    public int getM_AttributeSetInstance_ID() {
        Integer ii = (Integer)this.get_Value("M_AttributeSetInstance_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_M_Product getM_Product() throws RuntimeException {
        return (I_M_Product)MTable.get((Properties)this.getCtx(), (String)"M_Product").getPO(this.getM_Product_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Product_ID(int M_Product_ID) {
        if (M_Product_ID < 1) {
            this.set_ValueNoCheck("M_Product_ID", null);
        } else {
            this.set_ValueNoCheck("M_Product_ID", M_Product_ID);
        }
    }

    @Override
    public int getM_Product_ID() {
        Integer ii = (Integer)this.get_Value("M_Product_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_M_Warehouse getM_Warehouse() throws RuntimeException {
        return (I_M_Warehouse)MTable.get((Properties)this.getCtx(), (String)"M_Warehouse").getPO(this.getM_Warehouse_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Warehouse_ID(int M_Warehouse_ID) {
        if (M_Warehouse_ID < 1) {
            this.set_ValueNoCheck("M_Warehouse_ID", null);
        } else {
            this.set_ValueNoCheck("M_Warehouse_ID", M_Warehouse_ID);
        }
    }

    @Override
    public int getM_Warehouse_ID() {
        Integer ii = (Integer)this.get_Value("M_Warehouse_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setOrderType(String OrderType) {
        this.set_Value("OrderType", OrderType);
    }

    @Override
    public String getOrderType() {
        return (String)this.get_Value("OrderType");
    }

    @Override
    public I_AD_User getPlanner() throws RuntimeException {
        return (I_AD_User)MTable.get((Properties)this.getCtx(), (String)"AD_User").getPO(this.getPlanner_ID(), this.get_TrxName());
    }

    @Override
    public void setPlanner_ID(int Planner_ID) {
        if (Planner_ID < 1) {
            this.set_Value("Planner_ID", null);
        } else {
            this.set_Value("Planner_ID", Planner_ID);
        }
    }

    @Override
    public int getPlanner_ID() {
        Integer ii = (Integer)this.get_Value("Planner_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPosted(boolean Posted) {
        this.set_Value("Posted", Posted);
    }

    @Override
    public boolean isPosted() {
        Object oo = this.get_Value("Posted");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setPP_Order_ID(int PP_Order_ID) {
        if (PP_Order_ID < 1) {
            this.set_ValueNoCheck("PP_Order_ID", null);
        } else {
            this.set_ValueNoCheck("PP_Order_ID", PP_Order_ID);
        }
    }

    @Override
    public int getPP_Order_ID() {
        Integer ii = (Integer)this.get_Value("PP_Order_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPP_Order_UU(String PP_Order_UU) {
        this.set_Value("PP_Order_UU", PP_Order_UU);
    }

    @Override
    public String getPP_Order_UU() {
        return (String)this.get_Value("PP_Order_UU");
    }

    @Override
    public I_PP_Product_BOM getPP_Product_BOM() throws RuntimeException {
        return (I_PP_Product_BOM)MTable.get((Properties)this.getCtx(), (String)"PP_Product_BOM").getPO(this.getPP_Product_BOM_ID(), this.get_TrxName());
    }

    @Override
    public void setPP_Product_BOM_ID(int PP_Product_BOM_ID) {
        if (PP_Product_BOM_ID < 1) {
            this.set_ValueNoCheck("PP_Product_BOM_ID", null);
        } else {
            this.set_ValueNoCheck("PP_Product_BOM_ID", PP_Product_BOM_ID);
        }
    }

    @Override
    public int getPP_Product_BOM_ID() {
        Integer ii = (Integer)this.get_Value("PP_Product_BOM_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPriorityRule(String PriorityRule) {
        this.set_Value("PriorityRule", PriorityRule);
    }

    @Override
    public String getPriorityRule() {
        return (String)this.get_Value("PriorityRule");
    }

    @Override
    public void setProcessed(boolean Processed) {
        this.set_Value("Processed", Processed);
    }

    @Override
    public boolean isProcessed() {
        Object oo = this.get_Value("Processed");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setProcessedOn(BigDecimal ProcessedOn) {
        this.set_Value("ProcessedOn", ProcessedOn);
    }

    @Override
    public BigDecimal getProcessedOn() {
        BigDecimal bd = (BigDecimal)this.get_Value("ProcessedOn");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setProcessing(boolean Processing) {
        this.set_Value("Processing", Processing);
    }

    @Override
    public boolean isProcessing() {
        Object oo = this.get_Value("Processing");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setQtyBatchs(BigDecimal QtyBatchs) {
        this.set_ValueNoCheck("QtyBatchs", QtyBatchs);
    }

    @Override
    public BigDecimal getQtyBatchs() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyBatchs");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyBatchSize(BigDecimal QtyBatchSize) {
        this.set_ValueNoCheck("QtyBatchSize", QtyBatchSize);
    }

    @Override
    public BigDecimal getQtyBatchSize() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyBatchSize");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyDelivered(BigDecimal QtyDelivered) {
        this.set_Value("QtyDelivered", QtyDelivered);
    }

    @Override
    public BigDecimal getQtyDelivered() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyDelivered");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyEntered(BigDecimal QtyEntered) {
        this.set_Value("QtyEntered", QtyEntered);
    }

    @Override
    public BigDecimal getQtyEntered() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyEntered");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyOrdered(BigDecimal QtyOrdered) {
        this.set_ValueNoCheck("QtyOrdered", QtyOrdered);
    }

    @Override
    public BigDecimal getQtyOrdered() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyOrdered");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyReject(BigDecimal QtyReject) {
        this.set_Value("QtyReject", QtyReject);
    }

    @Override
    public BigDecimal getQtyReject() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyReject");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyReserved(BigDecimal QtyReserved) {
        this.set_Value("QtyReserved", QtyReserved);
    }

    @Override
    public BigDecimal getQtyReserved() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyReserved");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyScrap(BigDecimal QtyScrap) {
        this.set_Value("QtyScrap", QtyScrap);
    }

    @Override
    public BigDecimal getQtyScrap() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyScrap");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setScheduleType(String ScheduleType) {
        this.set_Value("ScheduleType", ScheduleType);
    }

    @Override
    public String getScheduleType() {
        return (String)this.get_Value("ScheduleType");
    }

    @Override
    public void setSerNo(String SerNo) {
        this.set_Value("SerNo", SerNo);
    }

    @Override
    public String getSerNo() {
        return (String)this.get_Value("SerNo");
    }

    @Override
    public I_S_Resource getS_Resource() throws RuntimeException {
        return (I_S_Resource)MTable.get((Properties)this.getCtx(), (String)"S_Resource").getPO(this.getS_Resource_ID(), this.get_TrxName());
    }

    @Override
    public void setS_Resource_ID(int S_Resource_ID) {
        if (S_Resource_ID < 1) {
            this.set_ValueNoCheck("S_Resource_ID", null);
        } else {
            this.set_ValueNoCheck("S_Resource_ID", S_Resource_ID);
        }
    }

    @Override
    public int getS_Resource_ID() {
        Integer ii = (Integer)this.get_Value("S_Resource_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_C_ElementValue getUser1() throws RuntimeException {
        return (I_C_ElementValue)MTable.get((Properties)this.getCtx(), (String)"C_ElementValue").getPO(this.getUser1_ID(), this.get_TrxName());
    }

    @Override
    public void setUser1_ID(int User1_ID) {
        if (User1_ID < 1) {
            this.set_Value("User1_ID", null);
        } else {
            this.set_Value("User1_ID", User1_ID);
        }
    }

    @Override
    public int getUser1_ID() {
        Integer ii = (Integer)this.get_Value("User1_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_C_ElementValue getUser2() throws RuntimeException {
        return (I_C_ElementValue)MTable.get((Properties)this.getCtx(), (String)"C_ElementValue").getPO(this.getUser2_ID(), this.get_TrxName());
    }

    @Override
    public void setUser2_ID(int User2_ID) {
        if (User2_ID < 1) {
            this.set_Value("User2_ID", null);
        } else {
            this.set_Value("User2_ID", User2_ID);
        }
    }

    @Override
    public int getUser2_ID() {
        Integer ii = (Integer)this.get_Value("User2_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setYield(BigDecimal Yield) {
        this.set_Value("Yield", Yield);
    }

    @Override
    public BigDecimal getYield() {
        BigDecimal bd = (BigDecimal)this.get_Value("Yield");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }
}

