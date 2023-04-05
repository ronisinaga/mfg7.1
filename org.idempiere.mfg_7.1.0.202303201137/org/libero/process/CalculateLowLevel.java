/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MProduct
 *  org.compiere.model.POResultSet
 *  org.compiere.model.Query
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.Env
 *  org.eevolution.model.MPPProductBOMLine
 */
package org.libero.process;

import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MProduct;
import org.compiere.model.POResultSet;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.eevolution.model.MPPProductBOMLine;

public class CalculateLowLevel
extends SvrProcess {
    protected void prepare() {
    }

    protected String doIt() throws Exception {
        int count_ok = 0;
        int count_err = 0;
        POResultSet rs = new Query(this.getCtx(), "M_Product", "AD_Client_ID=?", this.get_TrxName()).setParameters(new Object[]{Env.getAD_Client_ID((Properties)this.getCtx())}).setOrderBy("M_Product_ID").scroll();
        rs.setCloseOnError(true);
        while (rs.hasNext()) {
            MProduct product = (MProduct)rs.next();
            try {
                int lowlevel = MPPProductBOMLine.getLowLevel((Properties)this.getCtx(), (int)product.get_ID(), (String)this.get_TrxName());
                product.setLowLevel(lowlevel);
                product.saveEx();
                ++count_ok;
            }
            catch (Exception e) {
                this.log.log(Level.SEVERE, e.getLocalizedMessage(), (Throwable)e);
                ++count_err;
            }
        }
        rs.close();
        return "@Ok@ #" + count_ok + " @Error@ #" + count_err;
    }
}

