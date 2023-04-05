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
 *  org.compiere.model.I_S_Resource
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import org.compiere.model.I_S_Resource;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_Order_BOMLine;
import org.libero.tables.I_PP_Order_Node;
import org.libero.tables.I_PP_Order_Workflow;

public interface I_PP_Cost_Collector {
    public static final String Table_Name = "PP_Cost_Collector";
    public static final int Table_ID = 53035;
    public static final KeyNamePair Model = new KeyNamePair(53035, "PP_Cost_Collector");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(1L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";
    public static final String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";
    public static final String COLUMNNAME_CostCollectorType = "CostCollectorType";
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";
    public static final String COLUMNNAME_DateAcct = "DateAcct";
    public static final String COLUMNNAME_Description = "Description";
    public static final String COLUMNNAME_DocAction = "DocAction";
    public static final String COLUMNNAME_DocStatus = "DocStatus";
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";
    public static final String COLUMNNAME_DurationReal = "DurationReal";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_IsBatchTime = "IsBatchTime";
    public static final String COLUMNNAME_IsSubcontracting = "IsSubcontracting";
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";
    public static final String COLUMNNAME_MovementDate = "MovementDate";
    public static final String COLUMNNAME_MovementQty = "MovementQty";
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";
    public static final String COLUMNNAME_Posted = "Posted";
    public static final String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";
    public static final String COLUMNNAME_PP_Cost_Collector_UU = "PP_Cost_Collector_UU";
    public static final String COLUMNNAME_PP_Order_BOMLine_ID = "PP_Order_BOMLine_ID";
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";
    public static final String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";
    public static final String COLUMNNAME_PP_Order_Workflow_ID = "PP_Order_Workflow_ID";
    public static final String COLUMNNAME_Processed = "Processed";
    public static final String COLUMNNAME_ProcessedOn = "ProcessedOn";
    public static final String COLUMNNAME_Processing = "Processing";
    public static final String COLUMNNAME_QtyReject = "QtyReject";
    public static final String COLUMNNAME_Reversal_ID = "Reversal_ID";
    public static final String COLUMNNAME_ScrappedQty = "ScrappedQty";
    public static final String COLUMNNAME_SetupTimeReal = "SetupTimeReal";
    public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
    public static final String COLUMNNAME_User1_ID = "User1_ID";
    public static final String COLUMNNAME_User2_ID = "User2_ID";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setAD_OrgTrx_ID(int var1);

    public int getAD_OrgTrx_ID();

    public void setAD_User_ID(int var1);

    public int getAD_User_ID();

    public I_AD_User getAD_User() throws RuntimeException;

    public void setC_Activity_ID(int var1);

    public int getC_Activity_ID();

    public I_C_Activity getC_Activity() throws RuntimeException;

    public void setC_Campaign_ID(int var1);

    public int getC_Campaign_ID();

    public I_C_Campaign getC_Campaign() throws RuntimeException;

    public void setC_DocType_ID(int var1);

    public int getC_DocType_ID();

    public I_C_DocType getC_DocType() throws RuntimeException;

    public void setC_DocTypeTarget_ID(int var1);

    public int getC_DocTypeTarget_ID();

    public I_C_DocType getC_DocTypeTarget() throws RuntimeException;

    public void setCostCollectorType(String var1);

    public String getCostCollectorType();

    public void setC_Project_ID(int var1);

    public int getC_Project_ID();

    public I_C_Project getC_Project() throws RuntimeException;

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setC_UOM_ID(int var1);

    public int getC_UOM_ID();

    public I_C_UOM getC_UOM() throws RuntimeException;

    public void setDateAcct(Timestamp var1);

    public Timestamp getDateAcct();

    public void setDescription(String var1);

    public String getDescription();

    public void setDocAction(String var1);

    public String getDocAction();

    public void setDocStatus(String var1);

    public String getDocStatus();

    public void setDocumentNo(String var1);

    public String getDocumentNo();

    public void setDurationReal(BigDecimal var1);

    public BigDecimal getDurationReal();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setIsBatchTime(boolean var1);

    public boolean isBatchTime();

    public void setIsSubcontracting(boolean var1);

    public boolean isSubcontracting();

    public void setM_AttributeSetInstance_ID(int var1);

    public int getM_AttributeSetInstance_ID();

    public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

    public void setM_Locator_ID(int var1);

    public int getM_Locator_ID();

    public I_M_Locator getM_Locator() throws RuntimeException;

    public void setMovementDate(Timestamp var1);

    public Timestamp getMovementDate();

    public void setMovementQty(BigDecimal var1);

    public BigDecimal getMovementQty();

    public void setM_Product_ID(int var1);

    public int getM_Product_ID();

    public I_M_Product getM_Product() throws RuntimeException;

    public void setM_Warehouse_ID(int var1);

    public int getM_Warehouse_ID();

    public I_M_Warehouse getM_Warehouse() throws RuntimeException;

    public void setPosted(boolean var1);

    public boolean isPosted();

    public void setPP_Cost_Collector_ID(int var1);

    public int getPP_Cost_Collector_ID();

    public void setPP_Cost_Collector_UU(String var1);

    public String getPP_Cost_Collector_UU();

    public void setPP_Order_BOMLine_ID(int var1);

    public int getPP_Order_BOMLine_ID();

    public I_PP_Order_BOMLine getPP_Order_BOMLine() throws RuntimeException;

    public void setPP_Order_ID(int var1);

    public int getPP_Order_ID();

    public I_PP_Order getPP_Order() throws RuntimeException;

    public void setPP_Order_Node_ID(int var1);

    public int getPP_Order_Node_ID();

    public I_PP_Order_Node getPP_Order_Node() throws RuntimeException;

    public void setPP_Order_Workflow_ID(int var1);

    public int getPP_Order_Workflow_ID();

    public I_PP_Order_Workflow getPP_Order_Workflow() throws RuntimeException;

    public void setProcessed(boolean var1);

    public boolean isProcessed();

    public void setProcessedOn(BigDecimal var1);

    public BigDecimal getProcessedOn();

    public void setProcessing(boolean var1);

    public boolean isProcessing();

    public void setQtyReject(BigDecimal var1);

    public BigDecimal getQtyReject();

    public void setReversal_ID(int var1);

    public int getReversal_ID();

    public I_PP_Cost_Collector getReversal() throws RuntimeException;

    public void setScrappedQty(BigDecimal var1);

    public BigDecimal getScrappedQty();

    public void setSetupTimeReal(BigDecimal var1);

    public BigDecimal getSetupTimeReal();

    public void setS_Resource_ID(int var1);

    public int getS_Resource_ID();

    public I_S_Resource getS_Resource() throws RuntimeException;

    public Timestamp getUpdated();

    public int getUpdatedBy();

    public void setUser1_ID(int var1);

    public int getUser1_ID();

    public I_AD_User getUser1() throws RuntimeException;

    public void setUser2_ID(int var1);

    public int getUser2_ID();

    public I_AD_User getUser2() throws RuntimeException;
}

