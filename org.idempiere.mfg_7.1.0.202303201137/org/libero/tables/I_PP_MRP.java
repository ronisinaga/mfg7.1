/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_User
 *  org.compiere.model.I_C_BPartner
 *  org.compiere.model.I_C_Order
 *  org.compiere.model.I_C_OrderLine
 *  org.compiere.model.I_M_Forecast
 *  org.compiere.model.I_M_ForecastLine
 *  org.compiere.model.I_M_Product
 *  org.compiere.model.I_M_Requisition
 *  org.compiere.model.I_M_RequisitionLine
 *  org.compiere.model.I_M_Warehouse
 *  org.compiere.model.I_S_Resource
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_DD_Order
 *  org.eevolution.model.I_DD_OrderLine
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_Order_BOMLine;

public interface I_PP_MRP {
    public static final String Table_Name = "PP_MRP";
    public static final int Table_ID = 53043;
    public static final KeyNamePair Model = new KeyNamePair(53043, "PP_MRP");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_DateConfirm = "DateConfirm";
    public static final String COLUMNNAME_DateFinishSchedule = "DateFinishSchedule";
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";
    public static final String COLUMNNAME_DatePromised = "DatePromised";
    public static final String COLUMNNAME_DateSimulation = "DateSimulation";
    public static final String COLUMNNAME_DateStart = "DateStart";
    public static final String COLUMNNAME_DateStartSchedule = "DateStartSchedule";
    public static final String COLUMNNAME_DD_Order_ID = "DD_Order_ID";
    public static final String COLUMNNAME_DD_OrderLine_ID = "DD_OrderLine_ID";
    public static final String COLUMNNAME_Description = "Description";
    public static final String COLUMNNAME_DocStatus = "DocStatus";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_IsAvailable = "IsAvailable";
    public static final String COLUMNNAME_M_Forecast_ID = "M_Forecast_ID";
    public static final String COLUMNNAME_M_ForecastLine_ID = "M_ForecastLine_ID";
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
    public static final String COLUMNNAME_M_Requisition_ID = "M_Requisition_ID";
    public static final String COLUMNNAME_M_RequisitionLine_ID = "M_RequisitionLine_ID";
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";
    public static final String COLUMNNAME_Name = "Name";
    public static final String COLUMNNAME_OrderType = "OrderType";
    public static final String COLUMNNAME_Planner_ID = "Planner_ID";
    public static final String COLUMNNAME_PP_MRP_ID = "PP_MRP_ID";
    public static final String COLUMNNAME_PP_MRP_UU = "PP_MRP_UU";
    public static final String COLUMNNAME_PP_Order_BOMLine_ID = "PP_Order_BOMLine_ID";
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";
    public static final String COLUMNNAME_Priority = "Priority";
    public static final String COLUMNNAME_Qty = "Qty";
    public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";
    public static final String COLUMNNAME_TypeMRP = "TypeMRP";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
    public static final String COLUMNNAME_Value = "Value";
    public static final String COLUMNNAME_Version = "Version";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setC_BPartner_ID(int var1);

    public int getC_BPartner_ID();

    public I_C_BPartner getC_BPartner() throws RuntimeException;

    public void setC_Order_ID(int var1);

    public int getC_Order_ID();

    public I_C_Order getC_Order() throws RuntimeException;

    public void setC_OrderLine_ID(int var1);

    public int getC_OrderLine_ID();

    public I_C_OrderLine getC_OrderLine() throws RuntimeException;

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setDateConfirm(Timestamp var1);

    public Timestamp getDateConfirm();

    public void setDateFinishSchedule(Timestamp var1);

    public Timestamp getDateFinishSchedule();

    public void setDateOrdered(Timestamp var1);

    public Timestamp getDateOrdered();

    public void setDatePromised(Timestamp var1);

    public Timestamp getDatePromised();

    public void setDateSimulation(Timestamp var1);

    public Timestamp getDateSimulation();

    public void setDateStart(Timestamp var1);

    public Timestamp getDateStart();

    public void setDateStartSchedule(Timestamp var1);

    public Timestamp getDateStartSchedule();

    public void setDD_Order_ID(int var1);

    public int getDD_Order_ID();

    public I_DD_Order getDD_Order() throws RuntimeException;

    public void setDD_OrderLine_ID(int var1);

    public int getDD_OrderLine_ID();

    public I_DD_OrderLine getDD_OrderLine() throws RuntimeException;

    public void setDescription(String var1);

    public String getDescription();

    public void setDocStatus(String var1);

    public String getDocStatus();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setIsAvailable(boolean var1);

    public boolean isAvailable();

    public void setM_Forecast_ID(int var1);

    public int getM_Forecast_ID();

    public I_M_Forecast getM_Forecast() throws RuntimeException;

    public void setM_ForecastLine_ID(int var1);

    public int getM_ForecastLine_ID();

    public I_M_ForecastLine getM_ForecastLine() throws RuntimeException;

    public void setM_Product_ID(int var1);

    public int getM_Product_ID();

    public I_M_Product getM_Product() throws RuntimeException;

    public void setM_Requisition_ID(int var1);

    public int getM_Requisition_ID();

    public I_M_Requisition getM_Requisition() throws RuntimeException;

    public void setM_RequisitionLine_ID(int var1);

    public int getM_RequisitionLine_ID();

    public I_M_RequisitionLine getM_RequisitionLine() throws RuntimeException;

    public void setM_Warehouse_ID(int var1);

    public int getM_Warehouse_ID();

    public I_M_Warehouse getM_Warehouse() throws RuntimeException;

    public void setName(String var1);

    public String getName();

    public void setOrderType(String var1);

    public String getOrderType();

    public void setPlanner_ID(int var1);

    public int getPlanner_ID();

    public I_AD_User getPlanner() throws RuntimeException;

    public void setPP_MRP_ID(int var1);

    public int getPP_MRP_ID();

    public void setPP_MRP_UU(String var1);

    public String getPP_MRP_UU();

    public void setPP_Order_BOMLine_ID(int var1);

    public int getPP_Order_BOMLine_ID();

    public I_PP_Order_BOMLine getPP_Order_BOMLine() throws RuntimeException;

    public void setPP_Order_ID(int var1);

    public int getPP_Order_ID();

    public I_PP_Order getPP_Order() throws RuntimeException;

    public void setPriority(String var1);

    public String getPriority();

    public void setQty(BigDecimal var1);

    public BigDecimal getQty();

    public void setS_Resource_ID(int var1);

    public int getS_Resource_ID();

    public I_S_Resource getS_Resource() throws RuntimeException;

    public void setTypeMRP(String var1);

    public String getTypeMRP();

    public Timestamp getUpdated();

    public int getUpdatedBy();

    public void setValue(String var1);

    public String getValue();

    public void setVersion(BigDecimal var1);

    public BigDecimal getVersion();
}

