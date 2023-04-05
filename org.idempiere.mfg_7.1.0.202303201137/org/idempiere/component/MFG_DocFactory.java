/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.base.IDocFactory
 *  org.compiere.acct.Doc
 *  org.compiere.model.MAcctSchema
 *  org.compiere.model.MTable
 *  org.compiere.util.CLogger
 *  org.compiere.util.CPreparedStatement
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 */
package org.idempiere.component;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.base.IDocFactory;
import org.compiere.acct.Doc;
import org.compiere.acct.Doc_DDOrder;
import org.compiere.acct.Doc_PPCostCollector;
import org.compiere.acct.Doc_PPOrder;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MTable;
import org.compiere.util.CLogger;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MFG_DocFactory
implements IDocFactory {
    private static final CLogger s_log = CLogger.getCLogger(MFG_DocFactory.class);

    public Doc getDocument(MAcctSchema as, int AD_Table_ID, int Record_ID, String trxName) {
        Doc doc;
        block8: {
            String tableName = MTable.getTableName((Properties)Env.getCtx(), (int)AD_Table_ID);
            if (!(tableName.equals("PP_Order") && tableName.equals("DD_Order") && tableName.equals("PP_Cost_Collector"))) {
                return null;
            }
            doc = null;
            StringBuffer sql = new StringBuffer("SELECT * FROM ").append(tableName).append(" WHERE ").append(tableName).append("_ID=? AND Processed='Y'");
            CPreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                try {
                    pstmt = DB.prepareStatement((String)sql.toString(), (String)trxName);
                    pstmt.setInt(1, Record_ID);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        doc = this.getDocument(as, AD_Table_ID, rs, trxName);
                    } else {
                        s_log.severe("Not Found: " + tableName + "_ID=" + Record_ID);
                    }
                }
                catch (Exception e) {
                    s_log.log(Level.SEVERE, sql.toString(), (Throwable)e);
                    DB.close(rs, (Statement)pstmt);
                    rs = null;
                    pstmt = null;
                    break block8;
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
        }
        return doc;
    }

    public Doc getDocument(MAcctSchema as, int AD_Table_ID, ResultSet rs, String trxName) {
        String tableName = MTable.getTableName((Properties)Env.getCtx(), (int)AD_Table_ID);
        if (tableName.equals("PP_Order")) {
            return new Doc_PPOrder(as, rs, trxName);
        }
        if (tableName.equals("DD_Order")) {
            return new Doc_DDOrder(as, rs, trxName);
        }
        if (tableName.equals("PP_Cost_Collector")) {
            return new Doc_PPCostCollector(as, rs, trxName);
        }
        return null;
    }
}

