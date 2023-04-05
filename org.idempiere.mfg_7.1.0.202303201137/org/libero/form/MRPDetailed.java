/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.minigrid.ColumnInfo
 *  org.compiere.minigrid.IDColumn
 *  org.compiere.model.MQuery
 *  org.compiere.model.MTable
 *  org.compiere.util.CLogger
 *  org.compiere.util.Env
 *  org.compiere.util.Language
 *  org.compiere.util.Msg
 */
package org.libero.form;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.compiere.util.Msg;
import org.libero.model.MPPMRP;

public abstract class MRPDetailed {
    public static CLogger log = CLogger.getCLogger(MRPDetailed.class);
    public int m_WindowNo = 0;
    public int AD_Client_ID = Env.getAD_Client_ID((Properties)this.getCtx());
    public int p_WindowNo;
    public String p_keyColumn;
    public boolean p_multiSelection = true;
    public String p_whereClause = "";
    public int m_keyColumnIndex = -1;
    public boolean m_cancel = false;
    public String m_sqlMain;
    public String m_sqlAdd;
    public final int INFO_WIDTH = 800;
    public int AD_Window_ID;
    public MQuery query;
    private boolean isBaseLanguage;
    public final ColumnInfo[] m_layout;

    public MRPDetailed() {
        Env.getLanguage((Properties)Env.getCtx());
        this.isBaseLanguage = Language.getBaseAD_Language().compareTo(Env.getLoginLanguage((Properties)Env.getCtx()).getAD_Language()) == 0;
        this.m_layout = new ColumnInfo[]{new ColumnInfo(" ", String.valueOf(this.getTableName()) + ".PP_MRP_ID", IDColumn.class), new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"Value"), "(SELECT Value FROM M_Product p WHERE p.M_Product_ID=" + this.getTableName() + ".M_Product_ID) AS ProductValue", String.class), new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"Name"), "(SELECT Name FROM M_Product p WHERE p.M_Product_ID=" + this.getTableName() + ".M_Product_ID)", String.class), new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"S_Resource_ID"), "(SELECT Name FROM S_Resource sr WHERE sr.S_Resource_ID=" + this.getTableName() + ".S_Resource_ID)", String.class), new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"M_Warehouse_ID"), "(SELECT Name FROM M_Warehouse wh WHERE wh.M_Warehouse_ID=" + this.getTableName() + ".M_Warehouse_ID)", String.class), new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"DatePromised"), this.getTableName() + ".DatePromised", Timestamp.class), new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"QtyGrossReq"), "(CASE WHEN " + this.getTableName() + ".TypeMRP='D' THEN " + this.getTableName() + ".Qty ELSE NULL END)", BigDecimal.class), new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"QtyScheduledReceipts"), "(CASE WHEN " + this.getTableName() + ".TypeMRP='S' AND " + this.getTableName() + ".DocStatus  IN ('IP','CO') THEN " + this.getTableName() + ".Qty ELSE NULL END)", BigDecimal.class), new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"PlannedQty"), "(CASE WHEN " + this.getTableName() + ".TypeMRP='S' AND " + this.getTableName() + ".DocStatus ='DR' THEN " + this.getTableName() + ".Qty ELSE NULL END)", BigDecimal.class), new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"QtyOnHandProjected"), "bomQtyOnHand(" + this.getTableName() + ".M_Product_ID , " + this.getTableName() + ".M_Warehouse_ID, 0)", BigDecimal.class), this.isBaseLanguage ? new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"TypeMRP"), "(SELECT Name FROM  AD_Ref_List WHERE AD_Reference_ID=53230 AND Value = " + this.getTableName() + ".TypeMRP)", String.class) : new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"TypeMRP"), "(SELECT rlt.Name FROM  AD_Ref_List rl INNER JOIN AD_Ref_List_Trl  rlt ON (rl.AD_Ref_List_ID=rlt.AD_Ref_List_ID)  WHERE rl.AD_Reference_ID=53230 AND rlt.AD_Language = '" + Env.getLoginLanguage((Properties)Env.getCtx()).getAD_Language() + "' AND Value = " + this.getTableName() + ".TypeMRP)", String.class), this.isBaseLanguage ? new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"OrderType"), "(SELECT Name FROM  AD_Ref_List WHERE AD_Reference_ID=53229 AND Value = " + this.getTableName() + ".OrderType)", String.class) : new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"OrderType"), "(SELECT rlt.Name FROM  AD_Ref_List rl INNER JOIN AD_Ref_List_Trl  rlt ON (rl.AD_Ref_List_ID=rlt.AD_Ref_List_ID)  WHERE rl.AD_Reference_ID=53229 AND rlt.AD_Language = '" + Env.getLoginLanguage((Properties)Env.getCtx()).getAD_Language() + "' AND Value = " + this.getTableName() + ".OrderType)", String.class), new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"DocumentNo"), "documentNo(" + this.getTableName() + ".PP_MRP_ID)", String.class), this.isBaseLanguage ? new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"DocStatus"), "(SELECT Name FROM  AD_Ref_List WHERE AD_Reference_ID=131 AND Value = " + this.getTableName() + ".DocStatus)", String.class) : new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"DocStatus"), "(SELECT rlt.Name FROM  AD_Ref_List rl INNER JOIN AD_Ref_List_Trl  rlt ON (rl.AD_Ref_List_ID=rlt.AD_Ref_List_ID)  WHERE rl.AD_Reference_ID=131 AND rlt.AD_Language = '" + Env.getLoginLanguage((Properties)Env.getCtx()).getAD_Language() + "' AND Value = " + this.getTableName() + ".DocStatus)", String.class), new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"DateStartSchedule"), this.getTableName() + ".DateStartSchedule", Timestamp.class), new ColumnInfo(Msg.translate((Properties)Env.getCtx(), (String)"C_BPartner_ID"), "(SELECT cb.Name FROM C_BPartner cb WHERE cb.C_BPartner_ID=" + this.getTableName() + ".C_BPartner_ID)", String.class)};
    }

    public String getTableName() {
        return "RV_PP_MRP";
    }

    public void customize() {
    }

    public void doReset() {
    }

    public int getAD_Client_ID() {
        return Env.getAD_Client_ID((Properties)this.getCtx());
    }

    public Properties getCtx() {
        return Env.getCtx();
    }

    abstract Integer getSelectedRowKey();

    public String getWhereClause(String staticWhere) {
        StringBuffer where = new StringBuffer(this.getTableName() + ".DocStatus IN ('DR','IP','CO')  AND " + this.getTableName() + ".IsActive='Y' and " + this.getTableName() + ".Qty!=0 ");
        if (!staticWhere.equals("")) {
            where.append(staticWhere);
        }
        return where.toString();
    }

    public boolean hasCustomize() {
        return false;
    }

    public boolean hasHistory() {
        return false;
    }

    public boolean hasReset() {
        return false;
    }

    public boolean hasZoom() {
        return true;
    }

    public void showHistory() {
    }

    public void zoom() {
        log.info("InfoMRPDetailed.zoom");
        Integer PP_MPR_ID = this.getSelectedRowKey();
        this.AD_Window_ID = 0;
        if (PP_MPR_ID == null) {
            return;
        }
        this.query = null;
        MPPMRP mrp = new MPPMRP(this.getCtx(), PP_MPR_ID, null);
        String ordertype = mrp.getOrderType();
        if ("POO".equals(ordertype)) {
            this.AD_Window_ID = MTable.get((Properties)this.getCtx(), (int)259).getPO_Window_ID();
            this.query = new MQuery("C_Order");
            this.query.addRestriction("C_Order_ID", "=", mrp.getC_Order_ID());
        } else if ("SOO".equals(ordertype)) {
            this.AD_Window_ID = MTable.get((Properties)this.getCtx(), (int)259).getAD_Window_ID();
            this.query = new MQuery("C_Order");
            this.query.addRestriction("C_Order_ID", "=", mrp.getC_Order_ID());
        } else if ("MOP".equals(ordertype)) {
            this.AD_Window_ID = MTable.get((Properties)this.getCtx(), (int)53027).getAD_Window_ID();
            this.query = new MQuery("PP_Order");
            this.query.addRestriction("PP_Order_ID", "=", mrp.getPP_Order_ID());
        } else if ("POR".equals(ordertype)) {
            this.AD_Window_ID = MTable.get((Properties)this.getCtx(), (int)702).getAD_Window_ID();
            this.query = new MQuery("M_Requisition");
            this.query.addRestriction("M_Requisition_ID", "=", mrp.getM_Requisition_ID());
        } else if ("FCT".equals(ordertype)) {
            this.AD_Window_ID = MTable.get((Properties)this.getCtx(), (int)720).getAD_Window_ID();
            this.query = new MQuery("M_Forecast");
            this.query.addRestriction("M_Forecast_ID", "=", mrp.getM_Forecast_ID());
        }
        if ("DOO".equals(ordertype)) {
            this.AD_Window_ID = MTable.get((Properties)this.getCtx(), (int)53037).getAD_Window_ID();
            this.query = new MQuery("DD_Order");
            this.query.addRestriction("DD_Order_ID", "=", mrp.getDD_Order_ID());
        }
        if (this.AD_Window_ID == 0) {
            return;
        }
        log.info("AD_WindowNo " + this.AD_Window_ID);
        this.zoom(this.AD_Window_ID, this.query);
    }

    abstract void zoom(int var1, MQuery var2);
}

