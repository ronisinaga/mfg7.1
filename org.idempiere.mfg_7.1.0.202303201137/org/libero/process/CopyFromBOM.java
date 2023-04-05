/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.PO
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.AdempiereSystemError
 *  org.compiere.util.Env
 *  org.eevolution.model.MPPProductBOM
 *  org.eevolution.model.MPPProductBOMLine
 */
package org.libero.process;

import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.Env;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;

public class CopyFromBOM
extends SvrProcess {
    private int p_Record_ID = 0;
    private int p_PP_Product_BOM_ID = 0;
    private int no = 0;
    private Properties ctx = Env.getCtx();

    protected void prepare() {
        ProcessInfoParameter[] para = this.getParameter();
        for (int i = 0; i < para.length; ++i) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) continue;
            if (name.equals("PP_Product_BOM_ID")) {
                this.p_PP_Product_BOM_ID = para[i].getParameterAsInt();
                continue;
            }
            this.log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
        this.p_Record_ID = this.getRecord_ID();
    }

    protected String doIt() throws Exception {
        MPPProductBOMLine[] frombomlines;
        this.log.info("From PP_Product_BOM_ID=" + this.p_PP_Product_BOM_ID + " to " + this.p_Record_ID);
        if (this.p_Record_ID == 0) {
            throw new IllegalArgumentException("Target PP_Product_BOM_ID == 0");
        }
        if (this.p_PP_Product_BOM_ID == 0) {
            throw new IllegalArgumentException("Source PP_Product_BOM_ID == 0");
        }
        if (this.p_Record_ID == this.p_PP_Product_BOM_ID) {
            return "";
        }
        MPPProductBOM fromBom = new MPPProductBOM(this.ctx, this.p_PP_Product_BOM_ID, this.get_TrxName());
        MPPProductBOM toBOM = new MPPProductBOM(this.ctx, this.p_Record_ID, this.get_TrxName());
        if (toBOM.getLines().length > 0) {
            throw new AdempiereSystemError("@Error@ Existing BOM Line(s)");
        }
        MPPProductBOMLine[] arrmPPProductBOMLine = frombomlines = fromBom.getLines();
        int n = frombomlines.length;
        for (int i = 0; i < n; ++i) {
            MPPProductBOMLine frombomline = arrmPPProductBOMLine[i];
            MPPProductBOMLine tobomline = new MPPProductBOMLine(this.ctx, 0, this.get_TrxName());
            MPPProductBOMLine.copyValues((PO)frombomline, (PO)tobomline);
            tobomline.setPP_Product_BOM_ID(toBOM.getPP_Product_BOM_ID());
            tobomline.save();
            ++this.no;
        }
        return "OK";
    }

    protected void postProcess(boolean success) {
        this.addLog("@Copied@=" + this.no);
    }
}

