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
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.I_S_Resource
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 *  org.compiere.util.Env
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_DD_Order
 *  org.eevolution.model.I_DD_OrderLine
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
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
import org.compiere.model.I_Persistent;
import org.compiere.model.I_S_Resource;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_MRP;
import org.libero.tables.I_PP_Order_BOMLine;

public class X_PP_MRP
extends PO
implements I_PP_MRP,
I_Persistent {
    private static final long serialVersionUID = 20130626L;
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
    public static final int ORDERTYPE_AD_Reference_ID = 53229;
    public static final String ORDERTYPE_Forecast = "FCT";
    public static final String ORDERTYPE_ManufacturingOrder = "MOP";
    public static final String ORDERTYPE_PurchaseOrder = "POO";
    public static final String ORDERTYPE_MaterialRequisition = "POR";
    public static final String ORDERTYPE_SalesOrder = "SOO";
    public static final String ORDERTYPE_DistributionOrder = "DOO";
    public static final String ORDERTYPE_SafetyStock = "STK";
    public static final int TYPEMRP_AD_Reference_ID = 53230;
    public static final String TYPEMRP_Demand = "D";
    public static final String TYPEMRP_Supply = "S";

    public X_PP_MRP(Properties ctx, int PP_MRP_ID, String trxName) {
        super(ctx, PP_MRP_ID, trxName);
    }

    public X_PP_MRP(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53043, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_PP_MRP[").append(this.get_ID()).append("]");
        return sb.toString();
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
    public I_C_Order getC_Order() throws RuntimeException {
        return (I_C_Order)MTable.get((Properties)this.getCtx(), (String)"C_Order").getPO(this.getC_Order_ID(), this.get_TrxName());
    }

    @Override
    public void setC_Order_ID(int C_Order_ID) {
        if (C_Order_ID < 1) {
            this.set_Value("C_Order_ID", null);
        } else {
            this.set_Value("C_Order_ID", C_Order_ID);
        }
    }

    @Override
    public int getC_Order_ID() {
        Integer ii = (Integer)this.get_Value("C_Order_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
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
    public void setDateConfirm(Timestamp DateConfirm) {
        this.set_Value("DateConfirm", DateConfirm);
    }

    @Override
    public Timestamp getDateConfirm() {
        return (Timestamp)this.get_Value("DateConfirm");
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
    public void setDateSimulation(Timestamp DateSimulation) {
        this.set_Value("DateSimulation", DateSimulation);
    }

    @Override
    public Timestamp getDateSimulation() {
        return (Timestamp)this.get_Value("DateSimulation");
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
    public I_DD_Order getDD_Order() throws RuntimeException {
        return (I_DD_Order)MTable.get((Properties)this.getCtx(), (String)"DD_Order").getPO(this.getDD_Order_ID(), this.get_TrxName());
    }

    @Override
    public void setDD_Order_ID(int DD_Order_ID) {
        if (DD_Order_ID < 1) {
            this.set_Value("DD_Order_ID", null);
        } else {
            this.set_Value("DD_Order_ID", DD_Order_ID);
        }
    }

    @Override
    public int getDD_Order_ID() {
        Integer ii = (Integer)this.get_Value("DD_Order_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_DD_OrderLine getDD_OrderLine() throws RuntimeException {
        return (I_DD_OrderLine)MTable.get((Properties)this.getCtx(), (String)"DD_OrderLine").getPO(this.getDD_OrderLine_ID(), this.get_TrxName());
    }

    @Override
    public void setDD_OrderLine_ID(int DD_OrderLine_ID) {
        if (DD_OrderLine_ID < 1) {
            this.set_Value("DD_OrderLine_ID", null);
        } else {
            this.set_Value("DD_OrderLine_ID", DD_OrderLine_ID);
        }
    }

    @Override
    public int getDD_OrderLine_ID() {
        Integer ii = (Integer)this.get_Value("DD_OrderLine_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
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
    public void setDocStatus(String DocStatus) {
        this.set_Value("DocStatus", DocStatus);
    }

    @Override
    public String getDocStatus() {
        return (String)this.get_Value("DocStatus");
    }

    @Override
    public void setIsAvailable(boolean IsAvailable) {
        this.set_Value("IsAvailable", IsAvailable);
    }

    @Override
    public boolean isAvailable() {
        Object oo = this.get_Value("IsAvailable");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public I_M_Forecast getM_Forecast() throws RuntimeException {
        return (I_M_Forecast)MTable.get((Properties)this.getCtx(), (String)"M_Forecast").getPO(this.getM_Forecast_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Forecast_ID(int M_Forecast_ID) {
        if (M_Forecast_ID < 1) {
            this.set_Value("M_Forecast_ID", null);
        } else {
            this.set_Value("M_Forecast_ID", M_Forecast_ID);
        }
    }

    @Override
    public int getM_Forecast_ID() {
        Integer ii = (Integer)this.get_Value("M_Forecast_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_M_ForecastLine getM_ForecastLine() throws RuntimeException {
        return (I_M_ForecastLine)MTable.get((Properties)this.getCtx(), (String)"M_ForecastLine").getPO(this.getM_ForecastLine_ID(), this.get_TrxName());
    }

    @Override
    public void setM_ForecastLine_ID(int M_ForecastLine_ID) {
        if (M_ForecastLine_ID < 1) {
            this.set_Value("M_ForecastLine_ID", null);
        } else {
            this.set_Value("M_ForecastLine_ID", M_ForecastLine_ID);
        }
    }

    @Override
    public int getM_ForecastLine_ID() {
        Integer ii = (Integer)this.get_Value("M_ForecastLine_ID");
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

    @Override
    public I_M_Requisition getM_Requisition() throws RuntimeException {
        return (I_M_Requisition)MTable.get((Properties)this.getCtx(), (String)"M_Requisition").getPO(this.getM_Requisition_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Requisition_ID(int M_Requisition_ID) {
        if (M_Requisition_ID < 1) {
            this.set_Value("M_Requisition_ID", null);
        } else {
            this.set_Value("M_Requisition_ID", M_Requisition_ID);
        }
    }

    @Override
    public int getM_Requisition_ID() {
        Integer ii = (Integer)this.get_Value("M_Requisition_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_M_RequisitionLine getM_RequisitionLine() throws RuntimeException {
        return (I_M_RequisitionLine)MTable.get((Properties)this.getCtx(), (String)"M_RequisitionLine").getPO(this.getM_RequisitionLine_ID(), this.get_TrxName());
    }

    @Override
    public void setM_RequisitionLine_ID(int M_RequisitionLine_ID) {
        if (M_RequisitionLine_ID < 1) {
            this.set_Value("M_RequisitionLine_ID", null);
        } else {
            this.set_Value("M_RequisitionLine_ID", M_RequisitionLine_ID);
        }
    }

    @Override
    public int getM_RequisitionLine_ID() {
        Integer ii = (Integer)this.get_Value("M_RequisitionLine_ID");
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
    public void setPP_MRP_ID(int PP_MRP_ID) {
        if (PP_MRP_ID < 1) {
            this.set_ValueNoCheck("PP_MRP_ID", null);
        } else {
            this.set_ValueNoCheck("PP_MRP_ID", PP_MRP_ID);
        }
    }

    @Override
    public int getPP_MRP_ID() {
        Integer ii = (Integer)this.get_Value("PP_MRP_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPP_MRP_UU(String PP_MRP_UU) {
        this.set_Value("PP_MRP_UU", PP_MRP_UU);
    }

    @Override
    public String getPP_MRP_UU() {
        return (String)this.get_Value("PP_MRP_UU");
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
    public void setPriority(String Priority) {
        this.set_Value("Priority", Priority);
    }

    @Override
    public String getPriority() {
        return (String)this.get_Value("Priority");
    }

    @Override
    public void setQty(BigDecimal Qty) {
        this.set_Value("Qty", Qty);
    }

    @Override
    public BigDecimal getQty() {
        BigDecimal bd = (BigDecimal)this.get_Value("Qty");
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
    public void setTypeMRP(String TypeMRP) {
        this.set_Value("TypeMRP", TypeMRP);
    }

    @Override
    public String getTypeMRP() {
        return (String)this.get_Value("TypeMRP");
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
    public void setVersion(BigDecimal Version) {
        this.set_Value("Version", Version);
    }

    @Override
    public BigDecimal getVersion() {
        BigDecimal bd = (BigDecimal)this.get_Value("Version");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }
}

