/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_Table
 *  org.compiere.model.I_AD_WF_Node
 *  org.compiere.model.I_AD_WF_Responsible
 *  org.compiere.model.I_AD_Workflow
 *  org.compiere.model.I_AD_WorkflowProcessor
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
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_WF_Responsible;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_AD_WorkflowProcessor;
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

public class X_PP_Order_Workflow
extends PO
implements I_PP_Order_Workflow,
I_Persistent {
    private static final long serialVersionUID = 20130626L;
    public static final int ACCESSLEVEL_AD_Reference_ID = 5;
    public static final String ACCESSLEVEL_Organization = "1";
    public static final String ACCESSLEVEL_ClientPlusOrganization = "3";
    public static final String ACCESSLEVEL_SystemOnly = "4";
    public static final String ACCESSLEVEL_All = "7";
    public static final String ACCESSLEVEL_SystemPlusClient = "6";
    public static final String ACCESSLEVEL_ClientOnly = "2";
    public static final int DURATIONUNIT_AD_Reference_ID = 299;
    public static final String DURATIONUNIT_Year = "Y";
    public static final String DURATIONUNIT_Month = "M";
    public static final String DURATIONUNIT_Day = "D";
    public static final String DURATIONUNIT_Hour = "h";
    public static final String DURATIONUNIT_Minute = "m";
    public static final String DURATIONUNIT_Second = "s";
    public static final int ENTITYTYPE_AD_Reference_ID = 389;
    public static final int PROCESSTYPE_AD_Reference_ID = 53224;
    public static final String PROCESSTYPE_BatchFlow = "BF";
    public static final String PROCESSTYPE_ContinuousFlow = "CF";
    public static final String PROCESSTYPE_DedicateRepetititiveFlow = "DR";
    public static final String PROCESSTYPE_JobShop = "JS";
    public static final String PROCESSTYPE_MixedRepetitiveFlow = "MR";
    public static final String PROCESSTYPE_Plant = "PL";
    public static final int PUBLISHSTATUS_AD_Reference_ID = 310;
    public static final String PUBLISHSTATUS_Released = "R";
    public static final String PUBLISHSTATUS_Test = "T";
    public static final String PUBLISHSTATUS_UnderRevision = "U";
    public static final String PUBLISHSTATUS_Void = "V";
    public static final int WORKFLOWTYPE_AD_Reference_ID = 108;
    public static final String WORKFLOWTYPE_SingleRecord = "S";
    public static final String WORKFLOWTYPE_Maintain = "M";
    public static final String WORKFLOWTYPE_Transaction = "T";
    public static final String WORKFLOWTYPE_QueryOnly = "Q";

    public X_PP_Order_Workflow(Properties ctx, int PP_Order_Workflow_ID, String trxName) {
        super(ctx, PP_Order_Workflow_ID, trxName);
    }

    public X_PP_Order_Workflow(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53029, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_PP_Order_Workflow[").append(this.get_ID()).append("]");
        return sb.toString();
    }

    @Override
    public void setAccessLevel(String AccessLevel) {
        this.set_Value("AccessLevel", AccessLevel);
    }

    @Override
    public String getAccessLevel() {
        return (String)this.get_Value("AccessLevel");
    }

    @Override
    public I_AD_Table getAD_Table() throws RuntimeException {
        return (I_AD_Table)MTable.get((Properties)this.getCtx(), (String)"AD_Table").getPO(this.getAD_Table_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_Table_ID(int AD_Table_ID) {
        if (AD_Table_ID < 1) {
            this.set_Value("AD_Table_ID", null);
        } else {
            this.set_Value("AD_Table_ID", AD_Table_ID);
        }
    }

    @Override
    public int getAD_Table_ID() {
        Integer ii = (Integer)this.get_Value("AD_Table_ID");
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
    public I_AD_WorkflowProcessor getAD_WorkflowProcessor() throws RuntimeException {
        return (I_AD_WorkflowProcessor)MTable.get((Properties)this.getCtx(), (String)"AD_WorkflowProcessor").getPO(this.getAD_WorkflowProcessor_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_WorkflowProcessor_ID(int AD_WorkflowProcessor_ID) {
        if (AD_WorkflowProcessor_ID < 1) {
            this.set_Value("AD_WorkflowProcessor_ID", null);
        } else {
            this.set_Value("AD_WorkflowProcessor_ID", AD_WorkflowProcessor_ID);
        }
    }

    @Override
    public int getAD_WorkflowProcessor_ID() {
        Integer ii = (Integer)this.get_Value("AD_WorkflowProcessor_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setAuthor(String Author) {
        this.set_Value("Author", Author);
    }

    @Override
    public String getAuthor() {
        return (String)this.get_Value("Author");
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
    public void setDescription(String Description) {
        this.set_Value("Description", Description);
    }

    @Override
    public String getDescription() {
        return (String)this.get_Value("Description");
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
    public void setDurationUnit(String DurationUnit) {
        this.set_Value("DurationUnit", DurationUnit);
    }

    @Override
    public String getDurationUnit() {
        return (String)this.get_Value("DurationUnit");
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
    public void setHelp(String Help) {
        this.set_Value("Help", Help);
    }

    @Override
    public String getHelp() {
        return (String)this.get_Value("Help");
    }

    @Override
    public void setIsDefault(boolean IsDefault) {
        this.set_Value("IsDefault", IsDefault);
    }

    @Override
    public boolean isDefault() {
        Object oo = this.get_Value("IsDefault");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return DURATIONUNIT_Year.equals(oo);
        }
        return false;
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
    public void setOverlapUnits(BigDecimal OverlapUnits) {
        this.set_Value("OverlapUnits", OverlapUnits);
    }

    @Override
    public BigDecimal getOverlapUnits() {
        BigDecimal bd = (BigDecimal)this.get_Value("OverlapUnits");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
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
    public void setPP_Order_Workflow_UU(String PP_Order_Workflow_UU) {
        this.set_Value("PP_Order_Workflow_UU", PP_Order_Workflow_UU);
    }

    @Override
    public String getPP_Order_Workflow_UU() {
        return (String)this.get_Value("PP_Order_Workflow_UU");
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
    public void setProcessType(String ProcessType) {
        this.set_Value("ProcessType", ProcessType);
    }

    @Override
    public String getProcessType() {
        return (String)this.get_Value("ProcessType");
    }

    @Override
    public void setPublishStatus(String PublishStatus) {
        this.set_Value("PublishStatus", PublishStatus);
    }

    @Override
    public String getPublishStatus() {
        return (String)this.get_Value("PublishStatus");
    }

    @Override
    public void setQtyBatchSize(BigDecimal QtyBatchSize) {
        this.set_Value("QtyBatchSize", QtyBatchSize);
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
    public void setUnitsCycles(BigDecimal UnitsCycles) {
        this.set_Value("UnitsCycles", UnitsCycles);
    }

    @Override
    public BigDecimal getUnitsCycles() {
        BigDecimal bd = (BigDecimal)this.get_Value("UnitsCycles");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setValidateWorkflow(String ValidateWorkflow) {
        this.set_Value("ValidateWorkflow", ValidateWorkflow);
    }

    @Override
    public String getValidateWorkflow() {
        return (String)this.get_Value("ValidateWorkflow");
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
    public void setVersion(int Version) {
        this.set_Value("Version", Version);
    }

    @Override
    public int getVersion() {
        Integer ii = (Integer)this.get_Value("Version");
        if (ii == null) {
            return 0;
        }
        return ii;
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
    public void setWorkflowType(String WorkflowType) {
        this.set_Value("WorkflowType", WorkflowType);
    }

    @Override
    public String getWorkflowType() {
        return (String)this.get_Value("WorkflowType");
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
}

