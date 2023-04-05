/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.DBException
 *  org.compiere.model.MWarehouse
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.CPreparedStatement
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.eevolution.model.MPPProductPlanning
 */
package org.libero.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.exceptions.DBException;
import org.compiere.model.MWarehouse;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MPPProductPlanning;

public class CreateProductPlanning
extends SvrProcess {
    private int p_M_Product_Category_ID = 0;
    private int p_M_Warehouse_ID = 0;
    private int p_S_Resource_ID = 0;
    private int p_Planner = 0;
    private BigDecimal p_DeliveryTime_Promised = Env.ZERO;
    private int p_DD_NetworkDistribution_ID = 0;
    private int p_AD_Workflow_ID = 0;
    private BigDecimal p_TimeFence = Env.ZERO;
    private boolean p_CreatePlan = false;
    private boolean p_MPS = false;
    private String p_OrderPolicy = "";
    private BigDecimal p_OrderPeriod = Env.ZERO;
    private BigDecimal p_TransferTime = Env.ZERO;
    private BigDecimal p_SafetyStock = Env.ZERO;
    private BigDecimal p_Order_Min = Env.ZERO;
    private BigDecimal p_Order_Max = Env.ZERO;
    private BigDecimal p_Order_Pack = Env.ZERO;
    private BigDecimal p_Order_Qty = Env.ZERO;
    private BigDecimal p_WorkingTime = Env.ZERO;
    private int p_Yield = 0;
    private int m_AD_Org_ID = 0;
    private int m_AD_Client_ID = 0;
    private int count_created = 0;
    private int count_updated = 0;
    private int count_error = 0;

    protected void prepare() {
        for (ProcessInfoParameter para : this.getParameter()) {
            String name = para.getParameterName();
            if (para.getParameter() == null) continue;
            if (name.equals("M_Product_Category_ID")) {
                this.p_M_Product_Category_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("M_Warehouse_ID")) {
                this.p_M_Warehouse_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("S_Resource_ID")) {
                this.p_S_Resource_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("IsCreatePlan")) {
                this.p_CreatePlan = "Y".equals((String)para.getParameter());
                continue;
            }
            if (name.equals("IsMPS")) {
                this.p_MPS = "Y".equals((String)para.getParameter());
                continue;
            }
            if (name.equals("DD_NetworkDistribution_ID")) {
                this.p_DD_NetworkDistribution_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("AD_Workflow_ID")) {
                this.p_AD_Workflow_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("TimeFence")) {
                this.p_TimeFence = (BigDecimal)para.getParameter();
                continue;
            }
            if (name.equals("TransfertTime")) {
                this.p_TransferTime = (BigDecimal)para.getParameter();
                continue;
            }
            if (name.equals("SafetyStock")) {
                this.p_SafetyStock = (BigDecimal)para.getParameter();
                continue;
            }
            if (name.equals("Order_Min")) {
                this.p_Order_Min = (BigDecimal)para.getParameter();
                continue;
            }
            if (name.equals("Order_Max")) {
                this.p_Order_Max = (BigDecimal)para.getParameter();
                continue;
            }
            if (name.equals("Order_Pack")) {
                this.p_Order_Pack = (BigDecimal)para.getParameter();
                continue;
            }
            if (name.equals("Order_Qty")) {
                this.p_Order_Qty = (BigDecimal)para.getParameter();
                continue;
            }
            if (name.equals("WorkingTime")) {
                this.p_WorkingTime = (BigDecimal)para.getParameter();
                continue;
            }
            if (name.equals("Yield")) {
                this.p_Yield = ((BigDecimal)para.getParameter()).intValue();
                continue;
            }
            if (name.equals("DeliveryTime_Promised")) {
                this.p_DeliveryTime_Promised = (BigDecimal)para.getParameter();
                continue;
            }
            if (name.equals("Order_Period")) {
                this.p_OrderPeriod = (BigDecimal)para.getParameter();
                continue;
            }
            if (name.equals("Order_Policy")) {
                this.p_OrderPolicy = (String)para.getParameter();
                continue;
            }
            if (name.equals("Planner_ID")) {
                this.p_Planner = para.getParameterAsInt();
                continue;
            }
            this.log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
        this.m_AD_Client_ID = Env.getAD_Client_ID((Properties)this.getCtx());
        if (this.p_M_Warehouse_ID > 0) {
            MWarehouse w = MWarehouse.get((Properties)this.getCtx(), (int)this.p_M_Warehouse_ID);
            this.m_AD_Org_ID = w.getAD_Org_ID();
        }
    }

    protected String doIt() throws Exception {
        ArrayList<Integer> params = new ArrayList<Integer>();
        String sql = "SELECT p.M_Product_ID FROM M_Product p WHERE p.AD_Client_ID=?";
        params.add(this.m_AD_Client_ID);
        if (this.p_M_Product_Category_ID > 0) {
            sql = String.valueOf(sql) + " AND p.M_Product_Category_ID=?";
            params.add(this.p_M_Product_Category_ID);
        }
        CPreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            try {
                pstmt = DB.prepareStatement((String)sql, (String)this.get_TrxName());
                DB.setParameters((PreparedStatement)pstmt, params);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int M_Product_ID = rs.getInt(1);
                    this.createPlanning(M_Product_ID);
                }
            }
            catch (SQLException e) {
                throw new DBException(e, sql);
            }
        }
        catch (Throwable throwable) {
            DB.close(rs, pstmt);
            rs = null;
            pstmt = null;
            throw throwable;
        }
        DB.close((ResultSet)rs, (Statement)pstmt);
        rs = null;
        pstmt = null;
        return "@Created@ #" + this.count_created + " @Updated@ #" + this.count_updated + " @Error@ #" + this.count_error;
    }

    private void createPlanning(int M_Product_ID) {
        boolean isNew;
        MPPProductPlanning pp = MPPProductPlanning.get((Properties)this.getCtx(), (int)this.m_AD_Client_ID, (int)this.m_AD_Org_ID, (int)this.p_M_Warehouse_ID, (int)this.p_S_Resource_ID, (int)M_Product_ID, (String)this.get_TrxName());
        boolean bl = isNew = pp == null;
        if (pp == null) {
            pp = new MPPProductPlanning(this.getCtx(), 0, this.get_TrxName());
            pp.setAD_Org_ID(this.m_AD_Org_ID);
            pp.setM_Warehouse_ID(this.p_M_Warehouse_ID);
            pp.setS_Resource_ID(this.p_S_Resource_ID);
            pp.setM_Product_ID(M_Product_ID);
        }
        pp.setDD_NetworkDistribution_ID(this.p_DD_NetworkDistribution_ID);
        pp.setAD_Workflow_ID(this.p_AD_Workflow_ID);
        pp.setIsCreatePlan(this.p_CreatePlan);
        pp.setIsMPS(this.p_MPS);
        pp.setIsRequiredMRP(true);
        pp.setIsRequiredDRP(true);
        pp.setDeliveryTime_Promised(this.p_DeliveryTime_Promised);
        pp.setOrder_Period(this.p_OrderPeriod);
        pp.setPlanner_ID(this.p_Planner);
        pp.setOrder_Policy(this.p_OrderPolicy);
        pp.setSafetyStock(this.p_SafetyStock);
        pp.setOrder_Qty(this.p_Order_Qty);
        pp.setOrder_Min(this.p_Order_Min);
        pp.setOrder_Max(this.p_Order_Max);
        pp.setOrder_Pack(this.p_Order_Pack);
        pp.setTimeFence(this.p_TimeFence);
        pp.setTransfertTime(this.p_TransferTime);
        pp.setIsPhantom(false);
        pp.setWorkingTime(this.p_WorkingTime);
        pp.setYield(this.p_Yield);
        if (!pp.save()) {
            ++this.count_error;
        }
        if (isNew) {
            ++this.count_created;
        } else {
            ++this.count_updated;
        }
    }
}

