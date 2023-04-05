/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_Table
 *  org.compiere.model.I_AD_WF_Node
 *  org.compiere.model.I_AD_WF_Responsible
 *  org.compiere.model.I_AD_Workflow
 *  org.compiere.model.I_AD_WorkflowProcessor
 *  org.compiere.model.I_S_Resource
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_WF_Responsible;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_AD_WorkflowProcessor;
import org.compiere.model.I_S_Resource;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_Order_Node;

public interface I_PP_Order_Workflow {
    public static final String Table_Name = "PP_Order_Workflow";
    public static final int Table_ID = 53029;
    public static final KeyNamePair Model = new KeyNamePair(53029, "PP_Order_Workflow");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_AccessLevel = "AccessLevel";
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";
    public static final String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";
    public static final String COLUMNNAME_AD_WF_Responsible_ID = "AD_WF_Responsible_ID";
    public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";
    public static final String COLUMNNAME_AD_WorkflowProcessor_ID = "AD_WorkflowProcessor_ID";
    public static final String COLUMNNAME_Author = "Author";
    public static final String COLUMNNAME_Cost = "Cost";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_Description = "Description";
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";
    public static final String COLUMNNAME_Duration = "Duration";
    public static final String COLUMNNAME_DurationUnit = "DurationUnit";
    public static final String COLUMNNAME_EntityType = "EntityType";
    public static final String COLUMNNAME_Help = "Help";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_IsDefault = "IsDefault";
    public static final String COLUMNNAME_Limit = "Limit";
    public static final String COLUMNNAME_MovingTime = "MovingTime";
    public static final String COLUMNNAME_Name = "Name";
    public static final String COLUMNNAME_OverlapUnits = "OverlapUnits";
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";
    public static final String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";
    public static final String COLUMNNAME_PP_Order_Workflow_ID = "PP_Order_Workflow_ID";
    public static final String COLUMNNAME_PP_Order_Workflow_UU = "PP_Order_Workflow_UU";
    public static final String COLUMNNAME_Priority = "Priority";
    public static final String COLUMNNAME_ProcessType = "ProcessType";
    public static final String COLUMNNAME_PublishStatus = "PublishStatus";
    public static final String COLUMNNAME_QtyBatchSize = "QtyBatchSize";
    public static final String COLUMNNAME_QueuingTime = "QueuingTime";
    public static final String COLUMNNAME_SetupTime = "SetupTime";
    public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";
    public static final String COLUMNNAME_UnitsCycles = "UnitsCycles";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
    public static final String COLUMNNAME_ValidateWorkflow = "ValidateWorkflow";
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";
    public static final String COLUMNNAME_ValidTo = "ValidTo";
    public static final String COLUMNNAME_Value = "Value";
    public static final String COLUMNNAME_Version = "Version";
    public static final String COLUMNNAME_WaitingTime = "WaitingTime";
    public static final String COLUMNNAME_WorkflowType = "WorkflowType";
    public static final String COLUMNNAME_WorkingTime = "WorkingTime";
    public static final String COLUMNNAME_Yield = "Yield";

    public void setAccessLevel(String var1);

    public String getAccessLevel();

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setAD_Table_ID(int var1);

    public int getAD_Table_ID();

    public I_AD_Table getAD_Table() throws RuntimeException;

    public void setAD_WF_Node_ID(int var1);

    public int getAD_WF_Node_ID();

    public I_AD_WF_Node getAD_WF_Node() throws RuntimeException;

    public void setAD_WF_Responsible_ID(int var1);

    public int getAD_WF_Responsible_ID();

    public I_AD_WF_Responsible getAD_WF_Responsible() throws RuntimeException;

    public void setAD_Workflow_ID(int var1);

    public int getAD_Workflow_ID();

    public I_AD_Workflow getAD_Workflow() throws RuntimeException;

    public void setAD_WorkflowProcessor_ID(int var1);

    public int getAD_WorkflowProcessor_ID();

    public I_AD_WorkflowProcessor getAD_WorkflowProcessor() throws RuntimeException;

    public void setAuthor(String var1);

    public String getAuthor();

    public void setCost(BigDecimal var1);

    public BigDecimal getCost();

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setDescription(String var1);

    public String getDescription();

    public void setDocumentNo(String var1);

    public String getDocumentNo();

    public void setDuration(int var1);

    public int getDuration();

    public void setDurationUnit(String var1);

    public String getDurationUnit();

    public void setEntityType(String var1);

    public String getEntityType();

    public void setHelp(String var1);

    public String getHelp();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setIsDefault(boolean var1);

    public boolean isDefault();

    public void setLimit(int var1);

    public int getLimit();

    public void setMovingTime(int var1);

    public int getMovingTime();

    public void setName(String var1);

    public String getName();

    public void setOverlapUnits(BigDecimal var1);

    public BigDecimal getOverlapUnits();

    public void setPP_Order_ID(int var1);

    public int getPP_Order_ID();

    public I_PP_Order getPP_Order() throws RuntimeException;

    public void setPP_Order_Node_ID(int var1);

    public int getPP_Order_Node_ID();

    public I_PP_Order_Node getPP_Order_Node() throws RuntimeException;

    public void setPP_Order_Workflow_ID(int var1);

    public int getPP_Order_Workflow_ID();

    public void setPP_Order_Workflow_UU(String var1);

    public String getPP_Order_Workflow_UU();

    public void setPriority(int var1);

    public int getPriority();

    public void setProcessType(String var1);

    public String getProcessType();

    public void setPublishStatus(String var1);

    public String getPublishStatus();

    public void setQtyBatchSize(BigDecimal var1);

    public BigDecimal getQtyBatchSize();

    public void setQueuingTime(int var1);

    public int getQueuingTime();

    public void setSetupTime(int var1);

    public int getSetupTime();

    public void setS_Resource_ID(int var1);

    public int getS_Resource_ID();

    public I_S_Resource getS_Resource() throws RuntimeException;

    public void setUnitsCycles(BigDecimal var1);

    public BigDecimal getUnitsCycles();

    public Timestamp getUpdated();

    public int getUpdatedBy();

    public void setValidateWorkflow(String var1);

    public String getValidateWorkflow();

    public void setValidFrom(Timestamp var1);

    public Timestamp getValidFrom();

    public void setValidTo(Timestamp var1);

    public Timestamp getValidTo();

    public void setValue(String var1);

    public String getValue();

    public void setVersion(int var1);

    public int getVersion();

    public void setWaitingTime(int var1);

    public int getWaitingTime();

    public void setWorkflowType(String var1);

    public String getWorkflowType();

    public void setWorkingTime(int var1);

    public int getWorkingTime();

    public void setYield(int var1);

    public int getYield();
}

