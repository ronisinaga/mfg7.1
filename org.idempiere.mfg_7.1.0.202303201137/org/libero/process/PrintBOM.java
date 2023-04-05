/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MQuery
 *  org.compiere.model.MTable
 *  org.compiere.model.PrintInfo
 *  org.compiere.print.MPrintFormat
 *  org.compiere.print.ReportCtl
 *  org.compiere.print.ReportEngine
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.CLogger
 *  org.compiere.util.CPreparedStatement
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.compiere.util.ValueNamePair
 */
package org.libero.process;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.model.PrintInfo;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.ValueNamePair;
import org.libero.tables.X_T_BOMLine;

public class PrintBOM
extends SvrProcess {
    private static final Properties ctx = Env.getCtx();
    private int p_M_Product_ID = 0;
    private boolean p_implosion = false;
    private int LevelNo = 1;
    private int SeqNo = 0;
    private String levels = new String("....................");
    private int AD_PInstance_ID = 0;
    private static final int X_RV_PP_Product_BOMLine_Table_ID = 53063;
    private static final String X_RV_PP_Product_BOMLine_Table_Name = "RV_PP_Product_BOMLine";

    protected void prepare() {
        ProcessInfoParameter[] para = this.getParameter();
        for (int i = 0; i < para.length; ++i) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) continue;
            if (name.equals("M_Product_ID")) {
                this.p_M_Product_ID = ((BigDecimal)para[i].getParameter()).intValue();
                continue;
            }
            if (name.equals("Implosion")) {
                this.p_implosion = !((String)para[i].getParameter()).equals("N");
                continue;
            }
            this.log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
    }

    protected String doIt() throws Exception {
        this.AD_PInstance_ID = this.getAD_PInstance_ID();
        try {
            try {
                this.loadBOM();
                this.print();
            }
            catch (Exception e) {
                this.log.log(Level.SEVERE, "PrintBOM", (Object)e.toString());
                throw new Exception(e.getLocalizedMessage());
            }
        }
        finally {
            String sql = "DELETE FROM T_BomLine WHERE AD_PInstance_ID = " + this.AD_PInstance_ID;
            DB.executeUpdate((String)sql, null);
        }
        return "@OK@";
    }

    private void loadBOM() throws Exception {
        int count = 0;
        if (this.p_M_Product_ID == 0) {
            this.raiseError("Error: ", "Product ID not found");
        }
        X_T_BOMLine tboml = new X_T_BOMLine(ctx, 0, null);
        tboml.setPP_Product_BOM_ID(0);
        tboml.setPP_Product_BOMLine_ID(0);
        tboml.setM_Product_ID(this.p_M_Product_ID);
        tboml.setSel_Product_ID(this.p_M_Product_ID);
        tboml.setImplosion(this.p_implosion);
        tboml.setLevelNo(0);
        tboml.setLevels("0");
        tboml.setSeqNo(0);
        tboml.setAD_PInstance_ID(this.AD_PInstance_ID);
        tboml.save();
        if (this.p_implosion) {
            CPreparedStatement stmt = null;
            ResultSet rs = null;
            String sql = "SELECT PP_Product_BOMLine_ID FROM PP_Product_BOMLine WHERE IsActive = 'Y' AND M_Product_ID = ? ";
            try {
                try {
                    stmt = DB.prepareStatement((String)sql, (String)this.get_TrxName());
                    stmt.setInt(1, this.p_M_Product_ID);
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        this.parentImplotion(rs.getInt(1));
                        ++count;
                    }
                    if (count == 0) {
                        this.raiseError("Error: ", "Product is not a component");
                    }
                }
                catch (SQLException e) {
                    this.log.log(Level.SEVERE, String.valueOf(e.getLocalizedMessage()) + sql, (Throwable)e);
                    throw new Exception("SQLException: " + e.getLocalizedMessage());
                }
            }
            catch (Throwable throwable) {
                DB.close(rs, stmt);
                rs = null;
                stmt = null;
                throw throwable;
            }
            DB.close((ResultSet)rs, (Statement)stmt);
            rs = null;
            stmt = null;
        } else {
            CPreparedStatement stmt = null;
            ResultSet rs = null;
            String sql = "SELECT PP_Product_BOM_ID FROM PP_Product_BOM WHERE IsActive = 'Y' AND M_Product_ID = ? ";
            try {
                try {
                    stmt = DB.prepareStatement((String)sql, (String)this.get_TrxName());
                    stmt.setInt(1, this.p_M_Product_ID);
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        this.parentExplotion(rs.getInt(1));
                        ++count;
                    }
                    if (count == 0) {
                        this.raiseError("Error: ", "Product is not a BOM");
                    }
                }
                catch (SQLException e) {
                    this.log.log(Level.SEVERE, String.valueOf(e.getLocalizedMessage()) + sql, (Throwable)e);
                    throw new Exception("SQLException: " + e.getLocalizedMessage());
                }
            }
            catch (Throwable throwable) {
                DB.close(rs, stmt);
                rs = null;
                stmt = null;
                throw throwable;
            }
            DB.close((ResultSet)rs, (Statement)stmt);
            rs = null;
            Object var3_4 = null;
        }
    }

    public void parentImplotion(int PP_Product_BOMLine_ID) throws Exception {
        int PP_Product_BOM_ID = 0;
        int M_Product_ID = 0;
        X_T_BOMLine tboml = new X_T_BOMLine(ctx, 0, null);
        PP_Product_BOM_ID = DB.getSQLValue(null, (String)"SELECT PP_Product_BOM_ID FROM PP_Product_BOMLine WHERE PP_Product_BOMLine_ID=?", (int)PP_Product_BOMLine_ID);
        if (PP_Product_BOM_ID < 0) {
            throw new Exception(CLogger.retrieveErrorString((String)"Error: PrintBOM.parentImplotion()"));
        }
        M_Product_ID = DB.getSQLValue(null, (String)"SELECT M_Product_ID FROM PP_Product_BOM WHERE PP_Product_BOM_ID=?", (int)PP_Product_BOM_ID);
        if (M_Product_ID < 0) {
            throw new Exception(CLogger.retrieveErrorString((String)"Error: PrintBOM.parentImplotion()"));
        }
        tboml.setPP_Product_BOM_ID(PP_Product_BOM_ID);
        tboml.setPP_Product_BOMLine_ID(PP_Product_BOMLine_ID);
        tboml.setM_Product_ID(M_Product_ID);
        tboml.setLevelNo(this.LevelNo);
        tboml.setSel_Product_ID(this.p_M_Product_ID);
        tboml.setImplosion(this.p_implosion);
        if (this.LevelNo >= 11) {
            tboml.setLevels(String.valueOf(this.levels) + ">" + this.LevelNo);
        } else if (this.LevelNo >= 1) {
            tboml.setLevels(String.valueOf(this.levels.substring(0, this.LevelNo)) + this.LevelNo);
        }
        tboml.setSeqNo(this.SeqNo);
        tboml.setAD_PInstance_ID(this.AD_PInstance_ID);
        tboml.save();
        CPreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT PP_Product_BOM_ID, M_Product_ID FROM PP_Product_BOM WHERE IsActive = 'Y' AND M_Product_ID = ? ";
        try {
            try {
                stmt = DB.prepareStatement((String)sql, (String)this.get_TrxName());
                stmt.setInt(1, M_Product_ID);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    ++this.SeqNo;
                    this.component(rs.getInt(2));
                }
            }
            catch (SQLException e) {
                this.log.log(Level.SEVERE, String.valueOf(e.getLocalizedMessage()) + sql, (Throwable)e);
                throw new Exception("SQLException: " + e.getLocalizedMessage());
            }
        }
        catch (Throwable throwable) {
            DB.close(rs, stmt);
            rs = null;
            stmt = null;
            throw throwable;
        }
        DB.close((ResultSet)rs, (Statement)stmt);
        rs = null;
        stmt = null;
    }

    public void parentExplotion(int PP_Product_BOM_ID) throws Exception {
        CPreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT PP_Product_BOMLine_ID, M_Product_ID FROM PP_Product_BOMLine boml WHERE IsActive = 'Y' AND PP_Product_BOM_ID = ? ORDER BY Line ";
        try {
            try {
                stmt = DB.prepareStatement((String)sql, (String)this.get_TrxName());
                stmt.setInt(1, PP_Product_BOM_ID);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    ++this.SeqNo;
                    X_T_BOMLine tboml = new X_T_BOMLine(ctx, 0, null);
                    tboml.setPP_Product_BOM_ID(PP_Product_BOM_ID);
                    tboml.setPP_Product_BOMLine_ID(rs.getInt(1));
                    tboml.setM_Product_ID(rs.getInt(2));
                    tboml.setLevelNo(this.LevelNo);
                    tboml.setLevels(String.valueOf(this.levels.substring(0, this.LevelNo)) + this.LevelNo);
                    tboml.setSeqNo(this.SeqNo);
                    tboml.setAD_PInstance_ID(this.AD_PInstance_ID);
                    tboml.setSel_Product_ID(this.p_M_Product_ID);
                    tboml.setImplosion(this.p_implosion);
                    tboml.save();
                    this.component(rs.getInt(2));
                }
            }
            catch (SQLException e) {
                this.log.log(Level.SEVERE, String.valueOf(e.getLocalizedMessage()) + sql, (Throwable)e);
                throw new Exception("SQLException: " + e.getLocalizedMessage());
            }
        }
        catch (Throwable throwable) {
            DB.close(rs, stmt);
            rs = null;
            stmt = null;
            throw throwable;
        }
        DB.close((ResultSet)rs, (Statement)stmt);
        rs = null;
        stmt = null;
    }

    public void component(int M_Product_ID) throws Exception {
        if (this.p_implosion) {
            ++this.LevelNo;
            CPreparedStatement stmt = null;
            ResultSet rs = null;
            String sql = "SELECT PP_Product_BOMLine_ID FROM PP_Product_BOMLine WHERE IsActive = 'Y' AND M_Product_ID = ? ";
            try {
                stmt = DB.prepareStatement((String)sql, (String)this.get_TrxName());
                stmt.setInt(1, M_Product_ID);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    this.parentImplotion(rs.getInt(1));
                }
                rs.close();
                stmt.close();
                --this.LevelNo;
            }
            catch (SQLException e) {
                try {
                    this.log.log(Level.SEVERE, String.valueOf(e.getLocalizedMessage()) + sql, (Throwable)e);
                    throw new Exception("SQLException: " + e.getLocalizedMessage());
                }
                catch (Throwable throwable) {
                    DB.close(rs, (Statement)stmt);
                    rs = null;
                    stmt = null;
                    throw throwable;
                }
            }
            DB.close((ResultSet)rs, (Statement)stmt);
            rs = null;
            stmt = null;
            return;
        }
        String sql = "SELECT PP_Product_BOM_ID FROM PP_Product_BOM  WHERE IsActive = 'Y' AND Value = ? ";
        CPreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            try {
                String Value = DB.getSQLValueString((String)this.get_TrxName(), (String)"SELECT Value FROM M_PRODUCT WHERE M_PRODUCT_ID=?", (int)M_Product_ID);
                if (Value == null) {
                    throw new Exception(CLogger.retrieveErrorString((String)"Error: PrintBOM.component()"));
                }
                stmt = DB.prepareStatement((String)sql, (String)this.get_TrxName());
                stmt.setString(1, Value);
                rs = stmt.executeQuery();
                boolean level = false;
                while (rs.next()) {
                    if (!level) {
                        ++this.LevelNo;
                    }
                    level = true;
                    this.parentExplotion(rs.getInt(1));
                    --this.LevelNo;
                }
            }
            catch (SQLException e) {
                this.log.log(Level.SEVERE, String.valueOf(e.getLocalizedMessage()) + sql, (Throwable)e);
                throw new Exception("SQLException: " + e.getLocalizedMessage());
            }
        }
        catch (Throwable throwable) {
            DB.close(rs, stmt);
            rs = null;
            stmt = null;
            throw throwable;
        }
        DB.close((ResultSet)rs, (Statement)stmt);
        rs = null;
        Object var3_5 = null;
    }

    private void raiseError(String string, String hint) throws Exception {
        String msg = string;
        ValueNamePair pp = CLogger.retrieveError();
        if (pp != null) {
            msg = String.valueOf(pp.getName()) + " - ";
        }
        msg = String.valueOf(msg) + hint;
        throw new Exception(msg);
    }

    private void print() {
        String formatName = "Multi Level BOM & Formula Detail";
        String tableName = X_RV_PP_Product_BOMLine_Table_Name;
        int format_id = MPrintFormat.getPrintFormat_ID((String)formatName, (int)MTable.getTable_ID((String)tableName), (int)this.getAD_Client_ID());
        MPrintFormat format = MPrintFormat.get((Properties)this.getCtx(), (int)format_id, (boolean)true);
        if (format == null) {
            this.addLog("@NotFound@ @AD_PrintFormat_ID@" + format_id);
        }
        MQuery query = new MQuery(tableName);
        query.addRestriction("AD_PInstance_ID", "=", this.AD_PInstance_ID);
        PrintInfo info = new PrintInfo(X_RV_PP_Product_BOMLine_Table_Name, 53063, this.getRecord_ID());
        ReportEngine re = new ReportEngine(this.getCtx(), format, query, info);
        ReportCtl.preview((ReportEngine)re);
        re.print();
    }
}

