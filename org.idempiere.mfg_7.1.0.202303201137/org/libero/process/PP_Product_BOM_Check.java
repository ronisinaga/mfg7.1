/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MProduct
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.AdempiereUserError
 *  org.compiere.util.CLogger
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.compiere.util.ValueNamePair
 *  org.eevolution.model.MPPProductBOM
 *  org.eevolution.model.MPPProductBOMLine
 */
package org.libero.process;

import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MProduct;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.ValueNamePair;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;

public class PP_Product_BOM_Check
extends SvrProcess {
    private int p_Record_ID = 0;

    protected void prepare() {
        ProcessInfoParameter[] para = this.getParameter();
        for (int i = 0; i < para.length; ++i) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) continue;
            this.log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
        this.p_Record_ID = this.getRecord_ID();
    }

    protected String doIt() throws Exception {
        this.log.info("Check BOM Structure");
        MProduct xp = new MProduct(Env.getCtx(), this.p_Record_ID, this.get_TrxName());
        if (!xp.isBOM()) {
            this.log.info("Product is not a BOM");
            return "OK";
        }
        int lowlevel = MPPProductBOMLine.getLowLevel((Properties)this.getCtx(), (int)this.p_Record_ID, (String)this.get_TrxName());
        xp.setLowLevel(lowlevel);
        xp.setIsVerified(true);
        xp.saveEx();
        MPPProductBOM tbom = MPPProductBOM.getDefault((MProduct)xp, (String)this.get_TrxName());
        if (tbom == null) {
            this.raiseError("No Default BOM found: ", "Check BOM Parent search key");
        }
        if (tbom.getM_Product_ID() != 0) {
            MPPProductBOMLine[] tbomlines;
            MPPProductBOMLine[] arrmPPProductBOMLine = tbomlines = tbom.getLines();
            int n = tbomlines.length;
            for (int i = 0; i < n; ++i) {
                MPPProductBOMLine tbomline = arrmPPProductBOMLine[i];
                lowlevel = tbomline.getLowLevel();
                MProduct p = new MProduct(this.getCtx(), tbomline.getM_Product_ID(), this.get_TrxName());
                p.setLowLevel(lowlevel);
                p.setIsVerified(true);
                p.saveEx();
            }
        }
        return "OK";
    }

    private void raiseError(String string, String hint) throws Exception {
        DB.rollback((boolean)false, (String)this.get_TrxName());
        MProduct xp = new MProduct(this.getCtx(), this.p_Record_ID, null);
        xp.setIsVerified(false);
        xp.saveEx();
        String msg = string;
        ValueNamePair pp = CLogger.retrieveError();
        if (pp != null) {
            msg = String.valueOf(pp.getName()) + " - ";
        }
        msg = String.valueOf(msg) + hint;
        throw new AdempiereUserError(msg);
    }
}

