/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.Adempiere
 *  org.compiere.model.MCashLine
 *  org.compiere.model.MPayment
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.CLogger
 *  org.compiere.util.CPreparedStatement
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.compiere.util.Trx
 */
package org.libero.process;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.Adempiere;
import org.compiere.model.MCashLine;
import org.compiere.model.MPayment;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

public class FixPaymentCashLine
extends SvrProcess {
    private static final Properties ctx = Env.getCtx();
    private static CLogger s_log = CLogger.getCLogger(FixPaymentCashLine.class);

    protected void prepare() {
        this.getParameter();
    }

    protected String doIt() throws Exception {
        String sql = "SELECT cl.C_CashLine_ID, c.Name FROM C_CashLine cl INNER JOIN C_Cash c ON (c.C_Cash_ID=cl.C_Cash_ID) WHERE cl.CashType='T'";
        CPreparedStatement pstmt = null;
        try {
            pstmt = DB.prepareStatement((String)sql, (String)this.get_TrxName());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                MPayment[] payments;
                Trx trx = Trx.get((String)Trx.createTrxName(), (boolean)true);
                MCashLine cashline = new MCashLine(Env.getCtx(), rs.getInt(1), trx.getTrxName());
                cashline.getC_CashLine_ID();
                MPayment[] arrmPayment = payments = FixPaymentCashLine.getOfCash(Env.getCtx(), rs.getString(2), cashline.getAmount(), cashline.getC_BankAccount_ID(), cashline.getAD_Client_ID(), trx.getTrxName());
                if (payments.length != 0) {
                    MPayment payment = arrmPayment[0];
                    cashline.setC_Payment_ID(payment.getC_Payment_ID());
                    if (!cashline.save()) {
                        throw new IllegalStateException("Cannot assign payment to Cash Line");
                    }
                }
                trx.commit();
            }
            rs.close();
            pstmt.close();
            pstmt = null;
        }
        catch (Exception e) {
            s_log.log(Level.SEVERE, sql, (Throwable)e);
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
            pstmt = null;
        }
        catch (Exception exception) {
            pstmt = null;
        }
        return "@ProcessOK@";
    }

    public static MPayment[] getOfCash(Properties ctx, String cashName, BigDecimal amt, int C_BankAccount_ID, int AD_Client_ID, String trxName) {
        String sql = "SELECT * FROM C_Payment p WHERE p.DocumentNo=? AND R_PnRef=? AND PayAmt=? AND C_BankAccount_ID=? AND AD_Client_ID=?  AND TrxType='X' AND TenderType='X'";
        ArrayList<MPayment> list = new ArrayList<MPayment>();
        CPreparedStatement pstmt = null;
        try {
            pstmt = DB.prepareStatement((String)sql, (String)trxName);
            pstmt.setString(1, cashName);
            pstmt.setString(2, cashName);
            pstmt.setBigDecimal(3, amt.negate());
            pstmt.setInt(4, C_BankAccount_ID);
            pstmt.setInt(5, AD_Client_ID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new MPayment(ctx, rs, trxName));
            }
            rs.close();
            pstmt.close();
            pstmt = null;
        }
        catch (Exception e) {
            s_log.log(Level.SEVERE, sql, (Throwable)e);
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
            pstmt = null;
        }
        catch (Exception exception) {
            pstmt = null;
        }
        MPayment[] retValue = new MPayment[list.size()];
        list.toArray((T[])retValue);
        return retValue;
    }

    public static void main(String[] args) {
        Adempiere.startup((boolean)true);
        Env.setContext((Properties)Env.getCtx(), (String)"#AD_Client_ID", (int)11);
        FixPaymentCashLine pcf = new FixPaymentCashLine();
        try {
            pcf.doIt();
        }
        catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }
}

