/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_User
 *  org.compiere.model.I_C_Activity
 *  org.compiere.model.I_C_Campaign
 *  org.compiere.model.I_C_DocType
 *  org.compiere.model.I_C_Project
 *  org.compiere.model.I_C_UOM
 *  org.compiere.model.I_M_AttributeSetInstance
 *  org.compiere.model.I_M_Locator
 *  org.compiere.model.I_M_Product
 *  org.compiere.model.I_M_Warehouse
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.I_S_Resource
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 *  org.compiere.util.Env
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_Campaign;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_Persistent;
import org.compiere.model.I_S_Resource;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_Cost_Collector;
import org.libero.tables.I_PP_Order_BOMLine;
import org.libero.tables.I_PP_Order_Node;
import org.libero.tables.I_PP_Order_Workflow;

public class X_PP_Cost_Collector
extends PO
implements I_PP_Cost_Collector,
I_Persistent {
    private static final long serialVersionUID = 20130626L;
    public static final int COSTCOLLECTORTYPE_AD_Reference_ID = 53287;
    public static final String COSTCOLLECTORTYPE_MaterialReceipt = "100";
    public static final String COSTCOLLECTORTYPE_ComponentIssue = "110";
    public static final String COSTCOLLECTORTYPE_UsegeVariance = "120";
    public static final String COSTCOLLECTORTYPE_MethodChangeVariance = "130";
    public static final String COSTCOLLECTORTYPE_RateVariance = "140";
    public static final String COSTCOLLECTORTYPE_MixVariance = "150";
    public static final String COSTCOLLECTORTYPE_ActivityControl = "160";
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

    public X_PP_Cost_Collector(Properties ctx, int PP_Cost_Collector_ID, String trxName) {
        super(ctx, PP_Cost_Collector_ID, trxName);
    }

    public X_PP_Cost_Collector(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53035, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_PP_Cost_Collector[").append(this.get_ID()).append("]");
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
    public I_AD_User getAD_User() throws RuntimeException {
        return (I_AD_User)MTable.get((Properties)this.getCtx(), (String)"AD_User").getPO(this.getAD_User_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_User_ID(int AD_User_ID) {
        if (AD_User_ID < 1) {
            this.set_Value("AD_User_ID", null);
        } else {
            this.set_Value("AD_User_ID", AD_User_ID);
        }
    }

    @Override
    public int getAD_User_ID() {
        Integer ii = (Integer)this.get_Value("AD_User_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
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
    public void setCostCollectorType(String CostCollectorType) {
        this.set_Value("CostCollectorType", CostCollectorType);
    }

    @Override
    public String getCostCollectorType() {
        return (String)this.get_Value("CostCollectorType");
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
            this.set_Value("C_UOM_ID", null);
        } else {
            this.set_Value("C_UOM_ID", C_UOM_ID);
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
    public void setDateAcct(Timestamp DateAcct) {
        this.set_Value("DateAcct", DateAcct);
    }

    @Override
    public Timestamp getDateAcct() {
        return (Timestamp)this.get_Value("DateAcct");
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

    @Override
    public void setDurationReal(BigDecimal DurationReal) {
        this.set_Value("DurationReal", DurationReal);
    }

    @Override
    public BigDecimal getDurationReal() {
        BigDecimal bd = (BigDecimal)this.get_Value("DurationReal");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setIsBatchTime(boolean IsBatchTime) {
        this.set_Value("IsBatchTime", IsBatchTime);
    }

    @Override
    public boolean isBatchTime() {
        Object oo = this.get_Value("IsBatchTime");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIsSubcontracting(boolean IsSubcontracting) {
        this.set_ValueNoCheck("IsSubcontracting", IsSubcontracting);
    }

    @Override
    public boolean isSubcontracting() {
        Object oo = this.get_Value("IsSubcontracting");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
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
    public I_M_Locator getM_Locator() throws RuntimeException {
        return (I_M_Locator)MTable.get((Properties)this.getCtx(), (String)"M_Locator").getPO(this.getM_Locator_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Locator_ID(int M_Locator_ID) {
        if (M_Locator_ID < 1) {
            this.set_Value("M_Locator_ID", null);
        } else {
            this.set_Value("M_Locator_ID", M_Locator_ID);
        }
    }

    @Override
    public int getM_Locator_ID() {
        Integer ii = (Integer)this.get_Value("M_Locator_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setMovementDate(Timestamp MovementDate) {
        this.set_Value("MovementDate", MovementDate);
    }

    @Override
    public Timestamp getMovementDate() {
        return (Timestamp)this.get_Value("MovementDate");
    }

    @Override
    public void setMovementQty(BigDecimal MovementQty) {
        this.set_Value("MovementQty", MovementQty);
    }

    @Override
    public BigDecimal getMovementQty() {
        BigDecimal bd = (BigDecimal)this.get_Value("MovementQty");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public I_M_Product getM_Product() throws RuntimeException {
        return (I_M_Product)MTable.get((Properties)this.getCtx(), (String)"M_Product").getPO(this.getM_Product_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Product_ID(int M_Product_ID) {
        if (M_Product_ID < 1) {
            this.set_Value("M_Product_ID", null);
        } else {
            this.set_Value("M_Product_ID", M_Product_ID);
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

    public KeyNamePair getKeyNamePair() {
        return new KeyNamePair(this.get_ID(), String.valueOf(this.getM_Product_ID()));
    }

    @Override
    public I_M_Warehouse getM_Warehouse() throws RuntimeException {
        return (I_M_Warehouse)MTable.get((Properties)this.getCtx(), (String)"M_Warehouse").getPO(this.getM_Warehouse_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Warehouse_ID(int M_Warehouse_ID) {
        if (M_Warehouse_ID < 1) {
            this.set_Value("M_Warehouse_ID", null);
        } else {
            this.set_Value("M_Warehouse_ID", M_Warehouse_ID);
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
    public void setPP_Cost_Collector_ID(int PP_Cost_Collector_ID) {
        if (PP_Cost_Collector_ID < 1) {
            this.set_ValueNoCheck("PP_Cost_Collector_ID", null);
        } else {
            this.set_ValueNoCheck("PP_Cost_Collector_ID", PP_Cost_Collector_ID);
        }
    }

    @Override
    public int getPP_Cost_Collector_ID() {
        Integer ii = (Integer)this.get_Value("PP_Cost_Collector_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPP_Cost_Collector_UU(String PP_Cost_Collector_UU) {
        this.set_Value("PP_Cost_Collector_UU", PP_Cost_Collector_UU);
    }

    @Override
    public String getPP_Cost_Collector_UU() {
        return (String)this.get_Value("PP_Cost_Collector_UU");
    }

    @Override
    public I_PP_Order_BOMLine getPP_Order_BOMLine() throws RuntimeException {
        return (I_PP_Order_BOMLine)MTable.get((Properties)this.getCtx(), (String)"PP_Order_BOMLine").getPO(this.getPP_Order_BOMLine_ID(), this.get_TrxName());
    }

    @Override
    public void setPP_Order_BOMLine_ID(int PP_Order_BOMLine_ID) {
        if (PP_Order_BOMLine_ID < 1) {
            this.set_Value("PP_Order_BOMLine_ID", null);
        } else {
            this.set_Value("PP_Order_BOMLine_ID", PP_Order_BOMLine_ID);
        }
    }

    @Override
    public int getPP_Order_BOMLine_ID() {
        Integer ii = (Integer)this.get_Value("PP_Order_BOMLine_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_PP_Order getPP_Order() throws RuntimeException {
        return (I_PP_Order)MTable.get((Properties)this.getCtx(), (String)"PP_Order").getPO(this.getPP_Order_ID(), this.get_TrxName());
    }

    @Override
    public void setPP_Order_ID(int PP_Order_ID) {
        if (PP_Order_ID < 1) {
            this.set_Value("PP_Order_ID", null);
        } else {
            this.set_Value("PP_Order_ID", PP_Order_ID);
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
    public I_PP_Order_Node getPP_Order_Node() throws RuntimeException {
        return (I_PP_Order_Node)MTable.get((Properties)this.getCtx(), (String)"PP_Order_Node").getPO(this.getPP_Order_Node_ID(), this.get_TrxName());
    }

    @Override
    public void setPP_Order_Node_ID(int PP_Order_Node_ID) {
        if (PP_Order_Node_ID < 1) {
            this.set_Value("PP_Order_Node_ID", null);
        } else {
            this.set_Value("PP_Order_Node_ID", PP_Order_Node_ID);
        }
    }

    @Override
    public int getPP_Order_Node_ID() {
        Integer ii = (Integer)this.get_Value("PP_Order_Node_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_PP_Order_Workflow getPP_Order_Workflow() throws RuntimeException {
        return (I_PP_Order_Workflow)MTable.get((Properties)this.getCtx(), (String)"PP_Order_Workflow").getPO(this.getPP_Order_Workflow_ID(), this.get_TrxName());
    }

    @Override
    public void setPP_Order_Workflow_ID(int PP_Order_Workflow_ID) {
        if (PP_Order_Workflow_ID < 1) {
            this.set_Value("PP_Order_Workflow_ID", null);
        } else {
            this.set_Value("PP_Order_Workflow_ID", PP_Order_Workflow_ID);
        }
    }

    @Override
    public int getPP_Order_Workflow_ID() {
        Integer ii = (Integer)this.get_Value("PP_Order_Workflow_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
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
    public I_PP_Cost_Collector getReversal() throws RuntimeException {
        return (I_PP_Cost_Collector)MTable.get((Properties)this.getCtx(), (String)"PP_Cost_Collector").getPO(this.getReversal_ID(), this.get_TrxName());
    }

    @Override
    public void setReversal_ID(int Reversal_ID) {
        if (Reversal_ID < 1) {
            this.set_Value("Reversal_ID", null);
        } else {
            this.set_Value("Reversal_ID", Reversal_ID);
        }
    }

    @Override
    public int getReversal_ID() {
        Integer ii = (Integer)this.get_Value("Reversal_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setScrappedQty(BigDecimal ScrappedQty) {
        this.set_Value("ScrappedQty", ScrappedQty);
    }

    @Override
    public BigDecimal getScrappedQty() {
        BigDecimal bd = (BigDecimal)this.get_Value("ScrappedQty");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setSetupTimeReal(BigDecimal SetupTimeReal) {
        this.set_Value("SetupTimeReal", SetupTimeReal);
    }

    @Override
    public BigDecimal getSetupTimeReal() {
        BigDecimal bd = (BigDecimal)this.get_Value("SetupTimeReal");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public I_S_Resource getS_Resource() throws RuntimeException {
        return (I_S_Resource)MTable.get((Properties)this.getCtx(), (String)"S_Resource").getPO(this.getS_Resource_ID(), this.get_TrxName());
    }

    @Override
    public void setS_Resource_ID(int S_Resource_ID) {
        if (S_Resource_ID < 1) {
            this.set_Value("S_Resource_ID", null);
        } else {
            this.set_Value("S_Resource_ID", S_Resource_ID);
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
    public I_AD_User getUser1() throws RuntimeException {
        return (I_AD_User)MTable.get((Properties)this.getCtx(), (String)"AD_User").getPO(this.getUser1_ID(), this.get_TrxName());
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
    public I_AD_User getUser2() throws RuntimeException {
        return (I_AD_User)MTable.get((Properties)this.getCtx(), (String)"AD_User").getPO(this.getUser2_ID(), this.get_TrxName());
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
}

