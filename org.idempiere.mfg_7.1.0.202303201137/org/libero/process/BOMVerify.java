/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MProduct
 *  org.compiere.model.Query
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.CPreparedStatement
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.eevolution.model.MPPProductBOM
 *  org.eevolution.model.MPPProductBOMLine
 */
package org.libero.process;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;

public class BOMVerify
extends SvrProcess {
    private int p_M_Product_ID = 0;
    private int p_M_Product_Category_ID = 0;
    private boolean p_IsReValidate = false;
    private boolean p_fromButton = false;
    private ArrayList<MProduct> foundproducts = new ArrayList();
    private ArrayList<MProduct> validproducts = new ArrayList();
    private ArrayList<MProduct> invalidproducts = new ArrayList();
    private ArrayList<MProduct> containinvalidproducts = new ArrayList();
    private ArrayList<MProduct> checkedproducts = new ArrayList();

    protected void prepare() {
        ProcessInfoParameter[] para = this.getParameter();
        for (int i = 0; i < para.length; ++i) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) continue;
            if (name.equals("M_Product_ID")) {
                this.p_M_Product_ID = para[i].getParameterAsInt();
                continue;
            }
            if (name.equals("M_Product_Category_ID")) {
                this.p_M_Product_Category_ID = para[i].getParameterAsInt();
                continue;
            }
            if (name.equals("IsReValidate")) {
                this.p_IsReValidate = "Y".equals(para[i].getParameter());
                continue;
            }
            this.log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
        if (this.p_M_Product_ID == 0) {
            this.p_M_Product_ID = this.getRecord_ID();
        }
        this.p_fromButton = this.getRecord_ID() > 0;
    }

    protected String doIt() throws Exception {
        if (this.p_M_Product_ID != 0) {
            if (this.log.isLoggable(Level.INFO)) {
                this.log.info("M_Product_ID=" + this.p_M_Product_ID);
            }
            this.checkProduct(new MProduct(this.getCtx(), this.p_M_Product_ID, this.get_TrxName()));
            return "Product BOM [based libero] Checked ";
        }
        if (this.log.isLoggable(Level.INFO)) {
            this.log.info("M_Product_Category_ID=" + this.p_M_Product_Category_ID + ", IsReValidate=" + this.p_IsReValidate);
        }
        int counter = 0;
        CPreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT M_Product_ID FROM M_Product WHERE IsBOM='Y' AND ";
        sql = this.p_M_Product_Category_ID == 0 ? String.valueOf(sql) + "AD_Client_ID=? " : String.valueOf(sql) + "M_Product_Category_ID=? ";
        if (!this.p_IsReValidate) {
            sql = String.valueOf(sql) + "AND IsVerified<>'Y' ";
        }
        sql = String.valueOf(sql) + "ORDER BY Name";
        int AD_Client_ID = Env.getAD_Client_ID((Properties)this.getCtx());
        try {
            pstmt = DB.prepareStatement((String)sql, (String)this.get_TrxName());
            if (this.p_M_Product_Category_ID == 0) {
                pstmt.setInt(1, AD_Client_ID);
            } else {
                pstmt.setInt(1, this.p_M_Product_Category_ID);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                this.p_M_Product_ID = rs.getInt(1);
                this.checkProduct(new MProduct(this.getCtx(), this.p_M_Product_ID, this.get_TrxName()));
                ++counter;
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
        return "#" + counter;
    }

    private void checkProduct(MProduct product) {
        if (product.isBOM() && !this.checkedproducts.contains((Object)product)) {
            this.validateProduct(product);
        }
    }

    private boolean validateProduct(MProduct product) {
        if (!product.isBOM()) {
            return false;
        }
        Env.getContextAsDate((Properties)this.getCtx(), (String)"#Date");
        if (this.log.isLoggable(Level.CONFIG)) {
            this.log.config(product.getName());
        }
        this.foundproducts.add(product);
        List productsBOMLines = new Query(product.getCtx(), "PP_Product_BOMLine", "PP_Product_BOM_ID=?", product.get_TrxName()).setParameters(new Object[]{MPPProductBOM.getBOMSearchKey((MProduct)product)}).list();
        boolean containsinvalid = false;
        boolean invalid = false;
        int lines = 0;
        for (MPPProductBOMLine productsBOMLine : productsBOMLines) {
            if (!productsBOMLine.isActive()) continue;
            ++lines;
            MProduct pp = new MProduct(this.getCtx(), productsBOMLine.getM_Product_ID(), this.get_TrxName());
            if (!pp.isBOM()) {
                if (!this.log.isLoggable(Level.FINER)) continue;
                this.log.finer(pp.getName());
                continue;
            }
            this.validproducts.contains((Object)pp);
            if (this.invalidproducts.contains((Object)pp)) {
                containsinvalid = true;
                continue;
            }
            if (this.foundproducts.contains((Object)pp)) {
                invalid = true;
                if (this.p_fromButton) {
                    this.addLog(0, null, null, String.valueOf(product.getValue()) + " recursively contains " + pp.getValue());
                    continue;
                }
                this.addBufferLog(0, null, null, String.valueOf(product.getValue()) + " recursively contains " + pp.getValue(), 208, product.getM_Product_ID());
                continue;
            }
            if (this.validateProduct(pp)) continue;
            containsinvalid = true;
        }
        if (lines == 0) {
            invalid = true;
            if (this.p_fromButton) {
                this.addLog(0, null, null, String.valueOf(product.getValue()) + " does not have lines");
            } else {
                this.addBufferLog(0, null, null, String.valueOf(product.getValue()) + " does not have lines", 208, product.getM_Product_ID());
            }
        }
        this.checkedproducts.add(product);
        this.foundproducts.remove((Object)product);
        if (invalid) {
            this.invalidproducts.add(product);
            product.setIsVerified(false);
            product.saveEx();
            return false;
        }
        if (containsinvalid) {
            this.containinvalidproducts.add(product);
            product.setIsVerified(false);
            product.saveEx();
            return false;
        }
        this.validproducts.add(product);
        product.setIsVerified(true);
        product.saveEx();
        return true;
    }
}

