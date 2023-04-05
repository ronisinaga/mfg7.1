/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_Column
 *  org.compiere.model.I_AD_Form
 *  org.compiere.model.I_AD_Image
 *  org.compiere.model.I_AD_Process
 *  org.compiere.model.I_AD_Task
 *  org.compiere.model.I_AD_WF_Block
 *  org.compiere.model.I_AD_WF_Node
 *  org.compiere.model.I_AD_WF_Responsible
 *  org.compiere.model.I_AD_Window
 *  org.compiere.model.I_AD_Workflow
 *  org.compiere.model.I_C_BPartner
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
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Form;
import org.compiere.model.I_AD_Image;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Task;
import org.compiere.model.I_AD_WF_Block;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_WF_Responsible;
import org.compiere.model.I_AD_Window;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_Persistent;
import org.compiere.model.I_S_Resource;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_Order_Node;
import org.libero.tables.I_PP_Order_Workflow;

public class X_PP_Order_Node
extends PO
implements I_PP_Order_Node,
I_Persistent {
    private static final long serialVersionUID = 20130626L;
    public static final int ACTION_AD_Reference_ID = 302;
    public static final String ACTION_WaitSleep = "Z";
    public static final String ACTION_UserChoice = "C";
    public static final String ACTION_SubWorkflow = "F";
    public static final String ACTION_SetVariable = "V";
    public static final String ACTION_UserWindow = "W";
    public static final String ACTION_UserForm = "X";
    public static final String ACTION_AppsTask = "T";
    public static final String ACTION_AppsReport = "R";
    public static final String ACTION_AppsProcess = "P";
    public static final String ACTION_DocumentAction = "D";
    public static final String ACTION_EMail = "M";
    public static final String ACTION_UserWorkbench = "B";
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
    public static final int ENTITYTYPE_AD_Reference_ID = 389;
    public static final int FINISHMODE_AD_Reference_ID = 303;
    public static final String FINISHMODE_Automatic = "A";
    public static final String FINISHMODE_Manual = "M";
    public static final int JOINELEMENT_AD_Reference_ID = 301;
    public static final String JOINELEMENT_AND = "A";
    public static final String JOINELEMENT_XOR = "X";
    public static final int SPLITELEMENT_AD_Reference_ID = 301;
    public static final String SPLITELEMENT_AND = "A";
    public static final String SPLITELEMENT_XOR = "X";
    public static final int STARTMODE_AD_Reference_ID = 303;
    public static final String STARTMODE_Automatic = "A";
    public static final String STARTMODE_Manual = "M";
    public static final int SUBFLOWEXECUTION_AD_Reference_ID = 307;
    public static final String SUBFLOWEXECUTION_Asynchronously = "A";
    public static final String SUBFLOWEXECUTION_Synchronously = "S";

    public X_PP_Order_Node(Properties ctx, int PP_Order_Node_ID, String trxName) {
        super(ctx, PP_Order_Node_ID, trxName);
    }

    public X_PP_Order_Node(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53022, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_PP_Order_Node[").append(this.get_ID()).append("]");
        return sb.toString();
    }

    @Override
    public void setAction(String Action) {
        this.set_Value("Action", Action);
    }

    @Override
    public String getAction() {
        return (String)this.get_Value("Action");
    }

    @Override
    public I_AD_Column getAD_Column() throws RuntimeException {
        return (I_AD_Column)MTable.get((Properties)this.getCtx(), (String)"AD_Column").getPO(this.getAD_Column_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_Column_ID(int AD_Column_ID) {
        if (AD_Column_ID < 1) {
            this.set_Value("AD_Column_ID", null);
        } else {
            this.set_Value("AD_Column_ID", AD_Column_ID);
        }
    }

    @Override
    public int getAD_Column_ID() {
        Integer ii = (Integer)this.get_Value("AD_Column_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_AD_Form getAD_Form() throws RuntimeException {
        return (I_AD_Form)MTable.get((Properties)this.getCtx(), (String)"AD_Form").getPO(this.getAD_Form_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_Form_ID(int AD_Form_ID) {
        if (AD_Form_ID < 1) {
            this.set_Value("AD_Form_ID", null);
        } else {
            this.set_Value("AD_Form_ID", AD_Form_ID);
        }
    }

    @Override
    public int getAD_Form_ID() {
        Integer ii = (Integer)this.get_Value("AD_Form_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_AD_Image getAD_Image() throws RuntimeException {
        return (I_AD_Image)MTable.get((Properties)this.getCtx(), (String)"AD_Image").getPO(this.getAD_Image_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_Image_ID(int AD_Image_ID) {
        if (AD_Image_ID < 1) {
            this.set_Value("AD_Image_ID", null);
        } else {
            this.set_Value("AD_Image_ID", AD_Image_ID);
        }
    }

    @Override
    public int getAD_Image_ID() {
        Integer ii = (Integer)this.get_Value("AD_Image_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_AD_Process getAD_Process() throws RuntimeException {
        return (I_AD_Process)MTable.get((Properties)this.getCtx(), (String)"AD_Process").getPO(this.getAD_Process_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_Process_ID(int AD_Process_ID) {
        if (AD_Process_ID < 1) {
            this.set_Value("AD_Process_ID", null);
        } else {
            this.set_Value("AD_Process_ID", AD_Process_ID);
        }
    }

    @Override
    public int getAD_Process_ID() {
        Integer ii = (Integer)this.get_Value("AD_Process_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_AD_Task getAD_Task() throws RuntimeException {
        return (I_AD_Task)MTable.get((Properties)this.getCtx(), (String)"AD_Task").getPO(this.getAD_Task_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_Task_ID(int AD_Task_ID) {
        if (AD_Task_ID < 1) {
            this.set_Value("AD_Task_ID", null);
        } else {
            this.set_Value("AD_Task_ID", AD_Task_ID);
        }
    }

    @Override
    public int getAD_Task_ID() {
        Integer ii = (Integer)this.get_Value("AD_Task_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_AD_WF_Block getAD_WF_Block() throws RuntimeException {
        return (I_AD_WF_Block)MTable.get((Properties)this.getCtx(), (String)"AD_WF_Block").getPO(this.getAD_WF_Block_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_WF_Block_ID(int AD_WF_Block_ID) {
        if (AD_WF_Block_ID < 1) {
            this.set_Value("AD_WF_Block_ID", null);
        } else {
            this.set_Value("AD_WF_Block_ID", AD_WF_Block_ID);
        }
    }

    @Override
    public int getAD_WF_Block_ID() {
        Integer ii = (Integer)this.get_Value("AD_WF_Block_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_AD_WF_Node getAD_WF_Node() throws RuntimeException {
        return (I_AD_WF_Node)MTable.get((Properties)this.getCtx(), (String)"AD_WF_Node").getPO(this.getAD_WF_Node_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_WF_Node_ID(int AD_WF_Node_ID) {
        if (AD_WF_Node_ID < 1) {
            this.set_Value("AD_WF_Node_ID", null);
        } else {
            this.set_Value("AD_WF_Node_ID", AD_WF_Node_ID);
        }
    }

    @Override
    public int getAD_WF_Node_ID() {
        Integer ii = (Integer)this.get_Value("AD_WF_Node_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_AD_WF_Responsible getAD_WF_Responsible() throws RuntimeException {
        return (I_AD_WF_Responsible)MTable.get((Properties)this.getCtx(), (String)"AD_WF_Responsible").getPO(this.getAD_WF_Responsible_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_WF_Responsible_ID(int AD_WF_Responsible_ID) {
        if (AD_WF_Responsible_ID < 1) {
            this.set_Value("AD_WF_Responsible_ID", null);
        } else {
            this.set_Value("AD_WF_Responsible_ID", AD_WF_Responsible_ID);
        }
    }

    @Override
    public int getAD_WF_Responsible_ID() {
        Integer ii = (Integer)this.get_Value("AD_WF_Responsible_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_AD_Window getAD_Window() throws RuntimeException {
        return (I_AD_Window)MTable.get((Properties)this.getCtx(), (String)"AD_Window").getPO(this.getAD_Window_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_Window_ID(int AD_Window_ID) {
        if (AD_Window_ID < 1) {
            this.set_Value("AD_Window_ID", null);
        } else {
            this.set_Value("AD_Window_ID", AD_Window_ID);
        }
    }

    @Override
    public int getAD_Window_ID() {
        Integer ii = (Integer)this.get_Value("AD_Window_ID");
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
            this.set_Value("AD_Workflow_ID", null);
        } else {
            this.set_Value("AD_Workflow_ID", AD_Workflow_ID);
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
    public void setAttributeName(String AttributeName) {
        this.set_Value("AttributeName", AttributeName);
    }

    @Override
    public String getAttributeName() {
        return (String)this.get_Value("AttributeName");
    }

    @Override
    public void setAttributeValue(String AttributeValue) {
        this.set_Value("AttributeValue", AttributeValue);
    }

    @Override
    public String getAttributeValue() {
        return (String)this.get_Value("AttributeValue");
    }

    @Override
    public I_C_BPartner getC_BPartner() throws RuntimeException {
        return (I_C_BPartner)MTable.get((Properties)this.getCtx(), (String)"C_BPartner").getPO(this.getC_BPartner_ID(), this.get_TrxName());
    }

    @Override
    public void setC_BPartner_ID(int C_BPartner_ID) {
        if (C_BPartner_ID < 1) {
            this.set_Value("C_BPartner_ID", null);
        } else {
            this.set_Value("C_BPartner_ID", C_BPartner_ID);
        }
    }

    @Override
    public int getC_BPartner_ID() {
        Integer ii = (Integer)this.get_Value("C_BPartner_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setCost(BigDecimal Cost) {
        this.set_Value("Cost", Cost);
    }

    @Override
    public BigDecimal getCost() {
        BigDecimal bd = (BigDecimal)this.get_Value("Cost");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setDateFinish(Timestamp DateFinish) {
        this.set_Value("DateFinish", DateFinish);
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
    public void setDateStart(Timestamp DateStart) {
        this.set_Value("DateStart", DateStart);
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
    public void setDuration(int Duration) {
        this.set_Value("Duration", Duration);
    }

    @Override
    public int getDuration() {
        Integer ii = (Integer)this.get_Value("Duration");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setDurationReal(int DurationReal) {
        this.set_Value("DurationReal", DurationReal);
    }

    @Override
    public int getDurationReal() {
        Integer ii = (Integer)this.get_Value("DurationReal");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setDurationRequired(int DurationRequired) {
        this.set_Value("DurationRequired", DurationRequired);
    }

    @Override
    public int getDurationRequired() {
        Integer ii = (Integer)this.get_Value("DurationRequired");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setEntityType(String EntityType) {
        this.set_Value("EntityType", EntityType);
    }

    @Override
    public String getEntityType() {
        return (String)this.get_Value("EntityType");
    }

    @Override
    public void setFinishMode(String FinishMode) {
        this.set_Value("FinishMode", FinishMode);
    }

    @Override
    public String getFinishMode() {
        return (String)this.get_Value("FinishMode");
    }

    @Override
    public void setHelp(String Help) {
        this.set_Value("Help", Help);
    }

    @Override
    public String getHelp() {
        return (String)this.get_Value("Help");
    }

    @Override
    public void setIsCentrallyMaintained(boolean IsCentrallyMaintained) {
        this.set_Value("IsCentrallyMaintained", IsCentrallyMaintained);
    }

    @Override
    public boolean isCentrallyMaintained() {
        Object oo = this.get_Value("IsCentrallyMaintained");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIsMilestone(boolean IsMilestone) {
        this.set_Value("IsMilestone", IsMilestone);
    }

    @Override
    public boolean isMilestone() {
        Object oo = this.get_Value("IsMilestone");
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
        this.set_Value("IsSubcontracting", IsSubcontracting);
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
    public void setJoinElement(String JoinElement) {
        this.set_Value("JoinElement", JoinElement);
    }

    @Override
    public String getJoinElement() {
        return (String)this.get_Value("JoinElement");
    }

    @Override
    public void setLimit(int Limit) {
        this.set_Value("Limit", Limit);
    }

    @Override
    public int getLimit() {
        Integer ii = (Integer)this.get_Value("Limit");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setMovingTime(int MovingTime) {
        this.set_Value("MovingTime", MovingTime);
    }

    @Override
    public int getMovingTime() {
        Integer ii = (Integer)this.get_Value("MovingTime");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setName(String Name) {
        this.set_Value("Name", Name);
    }

    @Override
    public String getName() {
        return (String)this.get_Value("Name");
    }

    public KeyNamePair getKeyNamePair() {
        return new KeyNamePair(this.get_ID(), this.getName());
    }

    @Override
    public void setOverlapUnits(int OverlapUnits) {
        this.set_Value("OverlapUnits", OverlapUnits);
    }

    @Override
    public int getOverlapUnits() {
        Integer ii = (Integer)this.get_Value("OverlapUnits");
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
    public void setPP_Order_Node_ID(int PP_Order_Node_ID) {
        if (PP_Order_Node_ID < 1) {
            this.set_ValueNoCheck("PP_Order_Node_ID", null);
        } else {
            this.set_ValueNoCheck("PP_Order_Node_ID", PP_Order_Node_ID);
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
    public void setPP_Order_Node_UU(String PP_Order_Node_UU) {
        this.set_Value("PP_Order_Node_UU", PP_Order_Node_UU);
    }

    @Override
    public String getPP_Order_Node_UU() {
        return (String)this.get_Value("PP_Order_Node_UU");
    }

    @Override
    public I_PP_Order_Workflow getPP_Order_Workflow() throws RuntimeException {
        return (I_PP_Order_Workflow)MTable.get((Properties)this.getCtx(), (String)"PP_Order_Workflow").getPO(this.getPP_Order_Workflow_ID(), this.get_TrxName());
    }

    @Override
    public void setPP_Order_Workflow_ID(int PP_Order_Workflow_ID) {
        if (PP_Order_Workflow_ID < 1) {
            this.set_ValueNoCheck("PP_Order_Workflow_ID", null);
        } else {
            this.set_ValueNoCheck("PP_Order_Workflow_ID", PP_Order_Workflow_ID);
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
    public void setPriority(int Priority) {
        this.set_Value("Priority", Priority);
    }

    @Override
    public int getPriority() {
        Integer ii = (Integer)this.get_Value("Priority");
        if (ii == null) {
            return 0;
        }
        return ii;
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
    public void setQtyRequired(BigDecimal QtyRequired) {
        this.set_Value("QtyRequired", QtyRequired);
    }

    @Override
    public BigDecimal getQtyRequired() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyRequired");
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
    public void setQueuingTime(int QueuingTime) {
        this.set_Value("QueuingTime", QueuingTime);
    }

    @Override
    public int getQueuingTime() {
        Integer ii = (Integer)this.get_Value("QueuingTime");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setSetupTime(int SetupTime) {
        this.set_Value("SetupTime", SetupTime);
    }

    @Override
    public int getSetupTime() {
        Integer ii = (Integer)this.get_Value("SetupTime");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setSetupTimeReal(int SetupTimeReal) {
        this.set_Value("SetupTimeReal", SetupTimeReal);
    }

    @Override
    public int getSetupTimeReal() {
        Integer ii = (Integer)this.get_Value("SetupTimeReal");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setSetupTimeRequired(int SetupTimeRequired) {
        this.set_Value("SetupTimeRequired", SetupTimeRequired);
    }

    @Override
    public int getSetupTimeRequired() {
        Integer ii = (Integer)this.get_Value("SetupTimeRequired");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setSplitElement(String SplitElement) {
        this.set_Value("SplitElement", SplitElement);
    }

    @Override
    public String getSplitElement() {
        return (String)this.get_Value("SplitElement");
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
    public void setStartMode(String StartMode) {
        this.set_Value("StartMode", StartMode);
    }

    @Override
    public String getStartMode() {
        return (String)this.get_Value("StartMode");
    }

    @Override
    public void setSubflowExecution(String SubflowExecution) {
        this.set_Value("SubflowExecution", SubflowExecution);
    }

    @Override
    public String getSubflowExecution() {
        return (String)this.get_Value("SubflowExecution");
    }

    @Override
    public void setUnitsCycles(int UnitsCycles) {
        this.set_Value("UnitsCycles", UnitsCycles);
    }

    @Override
    public int getUnitsCycles() {
        Integer ii = (Integer)this.get_Value("UnitsCycles");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setValidFrom(Timestamp ValidFrom) {
        this.set_Value("ValidFrom", ValidFrom);
    }

    @Override
    public Timestamp getValidFrom() {
        return (Timestamp)this.get_Value("ValidFrom");
    }

    @Override
    public void setValidTo(Timestamp ValidTo) {
        this.set_Value("ValidTo", ValidTo);
    }

    @Override
    public Timestamp getValidTo() {
        return (Timestamp)this.get_Value("ValidTo");
    }

    @Override
    public void setValue(String Value) {
        this.set_Value("Value", Value);
    }

    @Override
    public String getValue() {
        return (String)this.get_Value("Value");
    }

    @Override
    public void setWaitingTime(int WaitingTime) {
        this.set_Value("WaitingTime", WaitingTime);
    }

    @Override
    public int getWaitingTime() {
        Integer ii = (Integer)this.get_Value("WaitingTime");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_AD_Workflow getWorkflow() throws RuntimeException {
        return (I_AD_Workflow)MTable.get((Properties)this.getCtx(), (String)"AD_Workflow").getPO(this.getWorkflow_ID(), this.get_TrxName());
    }

    @Override
    public void setWorkflow_ID(int Workflow_ID) {
        if (Workflow_ID < 1) {
            this.set_Value("Workflow_ID", null);
        } else {
            this.set_Value("Workflow_ID", Workflow_ID);
        }
    }

    @Override
    public int getWorkflow_ID() {
        Integer ii = (Integer)this.get_Value("Workflow_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setWorkingTime(int WorkingTime) {
        this.set_Value("WorkingTime", WorkingTime);
    }

    @Override
    public int getWorkingTime() {
        Integer ii = (Integer)this.get_Value("WorkingTime");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setXPosition(int XPosition) {
        this.set_Value("XPosition", XPosition);
    }

    @Override
    public int getXPosition() {
        Integer ii = (Integer)this.get_Value("XPosition");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setYield(int Yield) {
        this.set_Value("Yield", Yield);
    }

    @Override
    public int getYield() {
        Integer ii = (Integer)this.get_Value("Yield");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setYPosition(int YPosition) {
        this.set_Value("YPosition", YPosition);
    }

    @Override
    public int getYPosition() {
        Integer ii = (Integer)this.get_Value("YPosition");
        if (ii == null) {
            return 0;
        }
        return ii;
    }
}

