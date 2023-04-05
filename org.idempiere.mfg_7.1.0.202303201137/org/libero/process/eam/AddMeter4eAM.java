/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MAsset
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 */
package org.libero.process.eam;

import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MAsset;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class AddMeter4eAM
extends SvrProcess {
    private int p_AD_Client_ID = Env.getAD_Client_ID((Properties)Env.getCtx());
    private int p_AD_User_ID = Env.getAD_User_ID((Properties)Env.getCtx());
    private int p_A_Asset_ID = 0;
    private Timestamp p_DateValue = null;
    private int p_UnitsCycles = 0;

    protected void prepare() {
        for (ProcessInfoParameter para : this.getParameter()) {
            String name = para.getParameterName();
            if (para.getParameter() == null) continue;
            if (name.equals("A_Asset_ID")) {
                this.p_A_Asset_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("DateValue")) {
                this.p_DateValue = para.getParameterAsTimestamp();
                continue;
            }
            if (name.equals("UnitsCycles")) {
                this.p_UnitsCycles = para.getParameterAsInt();
                continue;
            }
            this.log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
    }

    protected String doIt() throws Exception {
        if (!this.getPMRuleStr(this.p_A_Asset_ID, "Asset_Prev_Maintenance_Rule").equals("M")) {
            return "Asset don't have a Meter rule!";
        }
        String _result = null;
        MAsset asset = new MAsset(this.getCtx(), this.p_A_Asset_ID, this.get_TrxName());
        asset.setUseUnits(this.p_UnitsCycles);
        asset.saveEx();
        return _result;
    }

    public String getPMRuleStr(int Asset_ID, String Field2) {
        return DB.getSQLValueString((String)this.get_TrxName(), (String)("SELECT " + Field2 + " FROM A_Asset_Prev_Maintenance WHERE isActive='Y' AND A_Asset_ID=" + Asset_ID), (Object[])new Object[0]);
    }
}

