/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MAsset
 *  org.compiere.model.MForecastLine
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.CPreparedStatement
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.compiere.util.TimeUtil
 *  org.eevolution.model.MPPProductPlanning
 */
package org.libero.process.eam;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MAsset;
import org.compiere.model.MForecastLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.MPPProductPlanning;

public class CreateForecastLine4eAM
extends SvrProcess {
    private int p_AD_Client_ID = Env.getAD_Client_ID((Properties)Env.getCtx());
    private int p_AD_User_ID = Env.getAD_User_ID((Properties)Env.getCtx());
    private int p_A_Asset_ID = 0;
    private int p_M_Forecast_ID = 0;
    private Timestamp p_DateValue = null;
    private int p_PlanningHorizon = 0;
    private Boolean p_DeleteOld = false;

    protected void prepare() {
        for (ProcessInfoParameter para : this.getParameter()) {
            String name = para.getParameterName();
            if (para.getParameter() == null) continue;
            if (name.equals("PlanningHorizon")) {
                this.p_PlanningHorizon = para.getParameterAsInt();
                continue;
            }
            if (name.equals("A_Asset_ID")) {
                this.p_A_Asset_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("M_Forecast_ID")) {
                this.p_M_Forecast_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("DeleteOld")) {
                this.p_DeleteOld = para.getParameterAsBoolean();
                continue;
            }
            this.log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
    }

    protected String doIt() throws Exception {
        String _result;
        block13: {
            if (this.p_DateValue == null) {
                this.p_DateValue = new Timestamp(System.currentTimeMillis());
            }
            _result = "";
            String pmrule = null;
            Timestamp MaintenanceDate = null;
            Timestamp FinishMaintenanceDate = null;
            Timestamp NextMaintenanceDate = null;
            Timestamp LastMaintenanceDate = null;
            int LastMaintenanceUnit = 0;
            int rate = 0;
            int rate_planning_end = 0;
            int rate_current = 0;
            int counter1 = 1;
            int counter2 = 1;
            int unitscycles = 0;
            int unit_current = 0;
            MAsset asset = new MAsset(this.getCtx(), this.p_A_Asset_ID, this.get_TrxName());
            if (this.p_DeleteOld.booleanValue()) {
                DB.executeUpdateEx((String)("DELETE FROM pp_mrp WHERE  m_forecastline_ID IN  (SELECT m_forecastline_ID FROM m_forecastline WHERE m_product_ID=" + asset.getM_Product_ID() + " AND  AD_Org_ID=" + asset.getAD_Org_ID() + ");" + " DELETE FROM m_forecastline WHERE m_product_ID=" + asset.getM_Product_ID() + " AND AD_Org_ID=" + asset.getAD_Org_ID()), (String)this.get_TrxName());
                this.commitEx();
            }
            String sql_so = "SELECT a_asset_id, Asset_Prev_Maintenance_Rule,   nextmaintenencedate, pp_product_planning_id, rate, unitscycles,   description, c_uom_id, validfrom, validto  FROM A_Asset_Prev_Maintenance  WHERE isActive='Y' AND A_Asset_ID=" + this.p_A_Asset_ID;
            CPreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                try {
                    pstmt = DB.prepareStatement((String)sql_so, null);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        MForecastLine fcl;
                        MPPProductPlanning pp = new MPPProductPlanning(this.getCtx(), rs.getInt(4), this.get_TrxName());
                        pmrule = rs.getString("Asset_Prev_Maintenance_Rule");
                        if (pmrule.equals("D")) {
                            FinishMaintenanceDate = TimeUtil.addDays((Timestamp)asset.getLastMaintenanceDate(), (int)this.p_PlanningHorizon);
                            MaintenanceDate = TimeUtil.addDays((Timestamp)asset.getLastMaintenanceDate(), (int)rs.getInt("UnitsCycles"));
                            while (MaintenanceDate.compareTo(FinishMaintenanceDate) <= 0) {
                                fcl = new MForecastLine(this.getCtx(), 0, this.get_TrxName());
                                fcl.setAD_Org_ID(pp.getAD_Org_ID());
                                fcl.setM_Forecast_ID(this.p_M_Forecast_ID);
                                fcl.setM_Product_ID(asset.getM_Product_ID());
                                fcl.setM_Warehouse_ID(pp.getM_Warehouse_ID());
                                fcl.setQty(Env.ONE);
                                fcl.setC_Period_ID(this.getPeriod_ID(MaintenanceDate));
                                fcl.setDatePromised(MaintenanceDate);
                                fcl.saveEx();
                                MaintenanceDate = TimeUtil.addDays((Timestamp)MaintenanceDate, (int)rs.getInt("UnitsCycles"));
                            }
                            _result = String.valueOf(_result) + " Created Forecat Lines for " + asset.getName() + " based Date Rules!";
                        }
                        if (pmrule.equals("M")) {
                            asset.getUseUnits();
                            rate = rs.getInt("Rate");
                            unitscycles = rs.getInt("UnitsCycles");
                            LastMaintenanceUnit = asset.getLastMaintenanceUnit();
                            LastMaintenanceDate = asset.getLastMaintenanceDate();
                            rate_planning_end = LastMaintenanceUnit + this.p_PlanningHorizon * rate;
                            rate_current = LastMaintenanceUnit + rate;
                            while (rate_current <= rate_planning_end) {
                                unit_current = rate * counter2;
                                if (unit_current >= unitscycles) {
                                    fcl = new MForecastLine(this.getCtx(), 0, this.get_TrxName());
                                    fcl.setAD_Org_ID(pp.getAD_Org_ID());
                                    fcl.setM_Forecast_ID(this.p_M_Forecast_ID);
                                    fcl.setM_Product_ID(asset.getM_Product_ID());
                                    fcl.setM_Warehouse_ID(pp.getM_Warehouse_ID());
                                    fcl.setQty(Env.ONE);
                                    fcl.setC_Period_ID(this.getPeriod_ID(TimeUtil.addDays((Timestamp)LastMaintenanceDate, (int)counter1)));
                                    fcl.setDatePromised(TimeUtil.addDays((Timestamp)LastMaintenanceDate, (int)counter1));
                                    fcl.saveEx();
                                    unit_current = 0;
                                    counter2 = 0;
                                }
                                rate_current += rate;
                                ++counter1;
                                ++counter2;
                            }
                            _result = String.valueOf(_result) + " Created Forecat Lines for " + asset.getName() + " based Meter Rules!";
                            rate_current = 0;
                            counter1 = 0;
                            counter2 = 0;
                        }
                        if (!pmrule.equals("L") || (NextMaintenanceDate = rs.getTimestamp("NextMaintenenceDate")) == null) continue;
                        fcl = new MForecastLine(this.getCtx(), 0, this.get_TrxName());
                        fcl.setAD_Org_ID(pp.getAD_Org_ID());
                        fcl.setM_Forecast_ID(this.p_M_Forecast_ID);
                        fcl.setM_Product_ID(asset.getM_Product_ID());
                        fcl.setM_Warehouse_ID(pp.getM_Warehouse_ID());
                        fcl.setQty(Env.ONE);
                        fcl.setC_Period_ID(this.getPeriod_ID(NextMaintenanceDate));
                        fcl.setDatePromised(NextMaintenanceDate);
                        fcl.saveEx();
                        _result = String.valueOf(_result) + " Created Forecat Lines for " + asset.getName() + " based List Dates!";
                    }
                }
                catch (Exception e) {
                    this.log.log(Level.SEVERE, sql_so, (Throwable)e);
                    DB.close((ResultSet)rs, (Statement)pstmt);
                    rs = null;
                    pstmt = null;
                    break block13;
                }
            }
            catch (Throwable throwable) {
                DB.close(rs, (Statement)pstmt);
                rs = null;
                pstmt = null;
                throw throwable;
            }
            DB.close((ResultSet)rs, (Statement)pstmt);
            rs = null;
            pstmt = null;
        }
        return _result;
    }

    public String getPMRuleStr(int Asset_ID, String Field2) {
        return DB.getSQLValueString((String)this.get_TrxName(), (String)("SELECT " + Field2 + " FROM A_Asset_Prev_Maintenance WHERE isActive='Y' AND A_Asset_ID=" + Asset_ID), (Object[])new Object[0]);
    }

    public int getPMRuleInt(int Asset_ID, String Field2) {
        return DB.getSQLValue((String)this.get_TrxName(), (String)("SELECT " + Field2 + " FROM A_Asset_Prev_Maintenance WHERE isActive='Y' AND A_Asset_ID=" + Asset_ID));
    }

    public Timestamp getPMRuleTS(int Asset_ID, String Field2) {
        return DB.getSQLValueTS((String)this.get_TrxName(), (String)("SELECT " + Field2 + " FROM A_Asset_Prev_Maintenance WHERE isActive='Y' AND A_Asset_ID=" + Asset_ID), (Object[])new Object[0]);
    }

    public int getPeriod_ID(Timestamp date) {
        return DB.getSQLValue((String)this.get_TrxName(), (String)("SELECT C_Period_ID FROM C_Period WHERE AD_Client_ID=" + this.p_AD_Client_ID + " AND CAST('" + date + "' AS date) BETWEEN CAST(StartDate AS date) and CAST(EndDate AS date);"));
    }
}

