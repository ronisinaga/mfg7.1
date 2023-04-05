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
 *  org.compiere.model.I_S_Resource
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import org.compiere.model.I_S_Resource;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_Order_Workflow;

public interface I_PP_Order_Node {
    public static final String Table_Name = "PP_Order_Node";
    public static final int Table_ID = 53022;
    public static final KeyNamePair Model = new KeyNamePair(53022, "PP_Order_Node");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_Action = "Action";
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";
    public static final String COLUMNNAME_AD_Form_ID = "AD_Form_ID";
    public static final String COLUMNNAME_AD_Image_ID = "AD_Image_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";
    public static final String COLUMNNAME_AD_Task_ID = "AD_Task_ID";
    public static final String COLUMNNAME_AD_WF_Block_ID = "AD_WF_Block_ID";
    public static final String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";
    public static final String COLUMNNAME_AD_WF_Responsible_ID = "AD_WF_Responsible_ID";
    public static final String COLUMNNAME_AD_Window_ID = "AD_Window_ID";
    public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";
    public static final String COLUMNNAME_AttributeName = "AttributeName";
    public static final String COLUMNNAME_AttributeValue = "AttributeValue";
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";
    public static final String COLUMNNAME_Cost = "Cost";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_DateFinish = "DateFinish";
    public static final String COLUMNNAME_DateFinishSchedule = "DateFinishSchedule";
    public static final String COLUMNNAME_DateStart = "DateStart";
    public static final String COLUMNNAME_DateStartSchedule = "DateStartSchedule";
    public static final String COLUMNNAME_Description = "Description";
    public static final String COLUMNNAME_DocAction = "DocAction";
    public static final String COLUMNNAME_DocStatus = "DocStatus";
    public static final String COLUMNNAME_Duration = "Duration";
    public static final String COLUMNNAME_DurationReal = "DurationReal";
    public static final String COLUMNNAME_DurationRequired = "DurationRequired";
    public static final String COLUMNNAME_EntityType = "EntityType";
    public static final String COLUMNNAME_FinishMode = "FinishMode";
    public static final String COLUMNNAME_Help = "Help";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_IsCentrallyMaintained = "IsCentrallyMaintained";
    public static final String COLUMNNAME_IsMilestone = "IsMilestone";
    public static final String COLUMNNAME_IsSubcontracting = "IsSubcontracting";
    public static final String COLUMNNAME_JoinElement = "JoinElement";
    public static final String COLUMNNAME_Limit = "Limit";
    public static final String COLUMNNAME_MovingTime = "MovingTime";
    public static final String COLUMNNAME_Name = "Name";
    public static final String COLUMNNAME_OverlapUnits = "OverlapUnits";
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";
    public static final String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";
    public static final String COLUMNNAME_PP_Order_Node_UU = "PP_Order_Node_UU";
    public static final String COLUMNNAME_PP_Order_Workflow_ID = "PP_Order_Workflow_ID";
    public static final String COLUMNNAME_Priority = "Priority";
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";
    public static final String COLUMNNAME_QtyReject = "QtyReject";
    public static final String COLUMNNAME_QtyRequired = "QtyRequired";
    public static final String COLUMNNAME_QtyScrap = "QtyScrap";
    public static final String COLUMNNAME_QueuingTime = "QueuingTime";
    public static final String COLUMNNAME_SetupTime = "SetupTime";
    public static final String COLUMNNAME_SetupTimeReal = "SetupTimeReal";
    public static final String COLUMNNAME_SetupTimeRequired = "SetupTimeRequired";
    public static final String COLUMNNAME_SplitElement = "SplitElement";
    public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";
    public static final String COLUMNNAME_StartMode = "StartMode";
    public static final String COLUMNNAME_SubflowExecution = "SubflowExecution";
    public static final String COLUMNNAME_UnitsCycles = "UnitsCycles";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";
    public static final String COLUMNNAME_ValidTo = "ValidTo";
    public static final String COLUMNNAME_Value = "Value";
    public static final String COLUMNNAME_WaitingTime = "WaitingTime";
    public static final String COLUMNNAME_Workflow_ID = "Workflow_ID";
    public static final String COLUMNNAME_WorkingTime = "WorkingTime";
    public static final String COLUMNNAME_XPosition = "XPosition";
    public static final String COLUMNNAME_Yield = "Yield";
    public static final String COLUMNNAME_YPosition = "YPosition";

    public void setAction(String var1);

    public String getAction();

    public int getAD_Client_ID();

    public void setAD_Column_ID(int var1);

    public int getAD_Column_ID();

    public I_AD_Column getAD_Column() throws RuntimeException;

    public void setAD_Form_ID(int var1);

    public int getAD_Form_ID();

    public I_AD_Form getAD_Form() throws RuntimeException;

    public void setAD_Image_ID(int var1);

    public int getAD_Image_ID();

    public I_AD_Image getAD_Image() throws RuntimeException;

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setAD_Process_ID(int var1);

    public int getAD_Process_ID();

    public I_AD_Process getAD_Process() throws RuntimeException;

    public void setAD_Task_ID(int var1);

    public int getAD_Task_ID();

    public I_AD_Task getAD_Task() throws RuntimeException;

    public void setAD_WF_Block_ID(int var1);

    public int getAD_WF_Block_ID();

    public I_AD_WF_Block getAD_WF_Block() throws RuntimeException;

    public void setAD_WF_Node_ID(int var1);

    public int getAD_WF_Node_ID();

    public I_AD_WF_Node getAD_WF_Node() throws RuntimeException;

    public void setAD_WF_Responsible_ID(int var1);

    public int getAD_WF_Responsible_ID();

    public I_AD_WF_Responsible getAD_WF_Responsible() throws RuntimeException;

    public void setAD_Window_ID(int var1);

    public int getAD_Window_ID();

    public I_AD_Window getAD_Window() throws RuntimeException;

    public void setAD_Workflow_ID(int var1);

    public int getAD_Workflow_ID();

    public I_AD_Workflow getAD_Workflow() throws RuntimeException;

    public void setAttributeName(String var1);

    public String getAttributeName();

    public void setAttributeValue(String var1);

    public String getAttributeValue();

    public void setC_BPartner_ID(int var1);

    public int getC_BPartner_ID();

    public I_C_BPartner getC_BPartner() throws RuntimeException;

    public void setCost(BigDecimal var1);

    public BigDecimal getCost();

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setDateFinish(Timestamp var1);

    public Timestamp getDateFinish();

    public void setDateFinishSchedule(Timestamp var1);

    public Timestamp getDateFinishSchedule();

    public void setDateStart(Timestamp var1);

    public Timestamp getDateStart();

    public void setDateStartSchedule(Timestamp var1);

    public Timestamp getDateStartSchedule();

    public void setDescription(String var1);

    public String getDescription();

    public void setDocAction(String var1);

    public String getDocAction();

    public void setDocStatus(String var1);

    public String getDocStatus();

    public void setDuration(int var1);

    public int getDuration();

    public void setDurationReal(int var1);

    public int getDurationReal();

    public void setDurationRequired(int var1);

    public int getDurationRequired();

    public void setEntityType(String var1);

    public String getEntityType();

    public void setFinishMode(String var1);

    public String getFinishMode();

    public void setHelp(String var1);

    public String getHelp();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setIsCentrallyMaintained(boolean var1);

    public boolean isCentrallyMaintained();

    public void setIsMilestone(boolean var1);

    public boolean isMilestone();

    public void setIsSubcontracting(boolean var1);

    public boolean isSubcontracting();

    public void setJoinElement(String var1);

    public String getJoinElement();

    public void setLimit(int var1);

    public int getLimit();

    public void setMovingTime(int var1);

    public int getMovingTime();

    public void setName(String var1);

    public String getName();

    public void setOverlapUnits(int var1);

    public int getOverlapUnits();

    public void setPP_Order_ID(int var1);

    public int getPP_Order_ID();

    public I_PP_Order getPP_Order() throws RuntimeException;

    public void setPP_Order_Node_ID(int var1);

    public int getPP_Order_Node_ID();

    public void setPP_Order_Node_UU(String var1);

    public String getPP_Order_Node_UU();

    public void setPP_Order_Workflow_ID(int var1);

    public int getPP_Order_Workflow_ID();

    public I_PP_Order_Workflow getPP_Order_Workflow() throws RuntimeException;

    public void setPriority(int var1);

    public int getPriority();

    public void setQtyDelivered(BigDecimal var1);

    public BigDecimal getQtyDelivered();

    public void setQtyReject(BigDecimal var1);

    public BigDecimal getQtyReject();

    public void setQtyRequired(BigDecimal var1);

    public BigDecimal getQtyRequired();

    public void setQtyScrap(BigDecimal var1);

    public BigDecimal getQtyScrap();

    public void setQueuingTime(int var1);

    public int getQueuingTime();

    public void setSetupTime(int var1);

    public int getSetupTime();

    public void setSetupTimeReal(int var1);

    public int getSetupTimeReal();

    public void setSetupTimeRequired(int var1);

    public int getSetupTimeRequired();

    public void setSplitElement(String var1);

    public String getSplitElement();

    public void setS_Resource_ID(int var1);

    public int getS_Resource_ID();

    public I_S_Resource getS_Resource() throws RuntimeException;

    public void setStartMode(String var1);

    public String getStartMode();

    public void setSubflowExecution(String var1);

    public String getSubflowExecution();

    public void setUnitsCycles(int var1);

    public int getUnitsCycles();

    public Timestamp getUpdated();

    public int getUpdatedBy();

    public void setValidFrom(Timestamp var1);

    public Timestamp getValidFrom();

    public void setValidTo(Timestamp var1);

    public Timestamp getValidTo();

    public void setValue(String var1);

    public String getValue();

    public void setWaitingTime(int var1);

    public int getWaitingTime();

    public void setWorkflow_ID(int var1);

    public int getWorkflow_ID();

    public I_AD_Workflow getWorkflow() throws RuntimeException;

    public void setWorkingTime(int var1);

    public int getWorkingTime();

    public void setXPosition(int var1);

    public int getXPosition();

    public void setYield(int var1);

    public int getYield();

    public void setYPosition(int var1);

    public int getYPosition();
}

