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
 *  org.compiere.model.I_S_Resource
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_PP_Product_BOM
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import org.compiere.model.I_S_Resource;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_PP_Product_BOM;

public interface I_PP_Order {
    public static final String Table_Name = "PP_Order";
    public static final int Table_ID = 53027;
    public static final KeyNamePair Model = new KeyNamePair(53027, "PP_Order");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(1L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";
    public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";
    public static final String COLUMNNAME_Assay = "Assay";
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";
    public static final String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";
    public static final String COLUMNNAME_CopyFrom = "CopyFrom";
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";
    public static final String COLUMNNAME_DateConfirm = "DateConfirm";
    public static final String COLUMNNAME_DateDelivered = "DateDelivered";
    public static final String COLUMNNAME_DateFinish = "DateFinish";
    public static final String COLUMNNAME_DateFinishSchedule = "DateFinishSchedule";
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";
    public static final String COLUMNNAME_DatePromised = "DatePromised";
    public static final String COLUMNNAME_DateStart = "DateStart";
    public static final String COLUMNNAME_DateStartSchedule = "DateStartSchedule";
    public static final String COLUMNNAME_Description = "Description";
    public static final String COLUMNNAME_DocAction = "DocAction";
    public static final String COLUMNNAME_DocStatus = "DocStatus";
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";
    public static final String COLUMNNAME_FloatAfter = "FloatAfter";
    public static final String COLUMNNAME_FloatBefored = "FloatBefored";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_IsApproved = "IsApproved";
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";
    public static final String COLUMNNAME_IsQtyPercentage = "IsQtyPercentage";
    public static final String COLUMNNAME_IsSelected = "IsSelected";
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";
    public static final String COLUMNNAME_Line = "Line";
    public static final String COLUMNNAME_Lot = "Lot";
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";
    public static final String COLUMNNAME_OrderType = "OrderType";
    public static final String COLUMNNAME_Planner_ID = "Planner_ID";
    public static final String COLUMNNAME_Posted = "Posted";
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";
    public static final String COLUMNNAME_PP_Order_UU = "PP_Order_UU";
    public static final String COLUMNNAME_PP_Product_BOM_ID = "PP_Product_BOM_ID";
    public static final String COLUMNNAME_PriorityRule = "PriorityRule";
    public static final String COLUMNNAME_Processed = "Processed";
    public static final String COLUMNNAME_ProcessedOn = "ProcessedOn";
    public static final String COLUMNNAME_Processing = "Processing";
    public static final String COLUMNNAME_QtyBatchs = "QtyBatchs";
    public static final String COLUMNNAME_QtyBatchSize = "QtyBatchSize";
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";
    public static final String COLUMNNAME_QtyEntered = "QtyEntered";
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";
    public static final String COLUMNNAME_QtyReject = "QtyReject";
    public static final String COLUMNNAME_QtyReserved = "QtyReserved";
    public static final String COLUMNNAME_QtyScrap = "QtyScrap";
    public static final String COLUMNNAME_ScheduleType = "ScheduleType";
    public static final String COLUMNNAME_SerNo = "SerNo";
    public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
    public static final String COLUMNNAME_User1_ID = "User1_ID";
    public static final String COLUMNNAME_User2_ID = "User2_ID";
    public static final String COLUMNNAME_Yield = "Yield";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setAD_OrgTrx_ID(int var1);

    public int getAD_OrgTrx_ID();

    public void setAD_Workflow_ID(int var1);

    public int getAD_Workflow_ID();

    public I_AD_Workflow getAD_Workflow() throws RuntimeException;

    public void setAssay(BigDecimal var1);

    public BigDecimal getAssay();

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

    public void setCopyFrom(String var1);

    public String getCopyFrom();

    public void setC_OrderLine_ID(int var1);

    public int getC_OrderLine_ID();

    public I_C_OrderLine getC_OrderLine() throws RuntimeException;

    public void setC_Project_ID(int var1);

    public int getC_Project_ID();

    public I_C_Project getC_Project() throws RuntimeException;

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setC_UOM_ID(int var1);

    public int getC_UOM_ID();

    public I_C_UOM getC_UOM() throws RuntimeException;

    public void setDateConfirm(Timestamp var1);

    public Timestamp getDateConfirm();

    public void setDateDelivered(Timestamp var1);

    public Timestamp getDateDelivered();

    public void setDateFinish(Timestamp var1);

    public Timestamp getDateFinish();

    public void setDateFinishSchedule(Timestamp var1);

    public Timestamp getDateFinishSchedule();

    public void setDateOrdered(Timestamp var1);

    public Timestamp getDateOrdered();

    public void setDatePromised(Timestamp var1);

    public Timestamp getDatePromised();

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

    public void setDocumentNo(String var1);

    public String getDocumentNo();

    public void setFloatAfter(BigDecimal var1);

    public BigDecimal getFloatAfter();

    public void setFloatBefored(BigDecimal var1);

    public BigDecimal getFloatBefored();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setIsApproved(boolean var1);

    public boolean isApproved();

    public void setIsPrinted(boolean var1);

    public boolean isPrinted();

    public void setIsQtyPercentage(boolean var1);

    public boolean isQtyPercentage();

    public void setIsSelected(boolean var1);

    public boolean isSelected();

    public void setIsSOTrx(boolean var1);

    public boolean isSOTrx();

    public void setLine(int var1);

    public int getLine();

    public void setLot(String var1);

    public String getLot();

    public void setM_AttributeSetInstance_ID(int var1);

    public int getM_AttributeSetInstance_ID();

    public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

    public void setM_Product_ID(int var1);

    public int getM_Product_ID();

    public I_M_Product getM_Product() throws RuntimeException;

    public void setM_Warehouse_ID(int var1);

    public int getM_Warehouse_ID();

    public I_M_Warehouse getM_Warehouse() throws RuntimeException;

    public void setOrderType(String var1);

    public String getOrderType();

    public void setPlanner_ID(int var1);

    public int getPlanner_ID();

    public I_AD_User getPlanner() throws RuntimeException;

    public void setPosted(boolean var1);

    public boolean isPosted();

    public void setPP_Order_ID(int var1);

    public int getPP_Order_ID();

    public void setPP_Order_UU(String var1);

    public String getPP_Order_UU();

    public void setPP_Product_BOM_ID(int var1);

    public int getPP_Product_BOM_ID();

    public I_PP_Product_BOM getPP_Product_BOM() throws RuntimeException;

    public void setPriorityRule(String var1);

    public String getPriorityRule();

    public void setProcessed(boolean var1);

    public boolean isProcessed();

    public void setProcessedOn(BigDecimal var1);

    public BigDecimal getProcessedOn();

    public void setProcessing(boolean var1);

    public boolean isProcessing();

    public void setQtyBatchs(BigDecimal var1);

    public BigDecimal getQtyBatchs();

    public void setQtyBatchSize(BigDecimal var1);

    public BigDecimal getQtyBatchSize();

    public void setQtyDelivered(BigDecimal var1);

    public BigDecimal getQtyDelivered();

    public void setQtyEntered(BigDecimal var1);

    public BigDecimal getQtyEntered();

    public void setQtyOrdered(BigDecimal var1);

    public BigDecimal getQtyOrdered();

    public void setQtyReject(BigDecimal var1);

    public BigDecimal getQtyReject();

    public void setQtyReserved(BigDecimal var1);

    public BigDecimal getQtyReserved();

    public void setQtyScrap(BigDecimal var1);

    public BigDecimal getQtyScrap();

    public void setScheduleType(String var1);

    public String getScheduleType();

    public void setSerNo(String var1);

    public String getSerNo();

    public void setS_Resource_ID(int var1);

    public int getS_Resource_ID();

    public I_S_Resource getS_Resource() throws RuntimeException;

    public Timestamp getUpdated();

    public int getUpdatedBy();

    public void setUser1_ID(int var1);

    public int getUser1_ID();

    public I_C_ElementValue getUser1() throws RuntimeException;

    public void setUser2_ID(int var1);

    public int getUser2_ID();

    public I_C_ElementValue getUser2() throws RuntimeException;

    public void setYield(BigDecimal var1);

    public BigDecimal getYield();
}

