/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_PInstance
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 */
package org.libero.tables;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.libero.tables.I_T_MRP_CRP;

public class X_T_MRP_CRP
extends PO
implements I_T_MRP_CRP,
I_Persistent {
    private static final long serialVersionUID = 20130626L;

    public X_T_MRP_CRP(Properties ctx, int T_MRP_CRP_ID, String trxName) {
        super(ctx, T_MRP_CRP_ID, trxName);
    }

    public X_T_MRP_CRP(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53044, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_T_MRP_CRP[").append(this.get_ID()).append("]");
        return sb.toString();
    }

    @Override
    public I_AD_PInstance getAD_PInstance() throws RuntimeException {
        return (I_AD_PInstance)MTable.get((Properties)this.getCtx(), (String)"AD_PInstance").getPO(this.getAD_PInstance_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_PInstance_ID(int AD_PInstance_ID) {
        if (AD_PInstance_ID < 1) {
            this.set_Value("AD_PInstance_ID", null);
        } else {
            this.set_Value("AD_PInstance_ID", AD_PInstance_ID);
        }
    }

    @Override
    public int getAD_PInstance_ID() {
        Integer ii = (Integer)this.get_Value("AD_PInstance_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setDescription(String Description) {
        this.set_Value("Description", Description);
    }

    @Override
    public String getDescription() {
        return (String)this.get_Value("Description");
    }

    @Override
    public void setSeqNo(int SeqNo) {
        this.set_Value("SeqNo", SeqNo);
    }

    @Override
    public int getSeqNo() {
        Integer ii = (Integer)this.get_Value("SeqNo");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setT_MRP_CRP_ID(int T_MRP_CRP_ID) {
        if (T_MRP_CRP_ID < 1) {
            this.set_ValueNoCheck("T_MRP_CRP_ID", null);
        } else {
            this.set_ValueNoCheck("T_MRP_CRP_ID", T_MRP_CRP_ID);
        }
    }

    @Override
    public int getT_MRP_CRP_ID() {
        Integer ii = (Integer)this.get_Value("T_MRP_CRP_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setT_MRP_CRP_UU(String T_MRP_CRP_UU) {
        this.set_Value("T_MRP_CRP_UU", T_MRP_CRP_UU);
    }

    @Override
    public String getT_MRP_CRP_UU() {
        return (String)this.get_Value("T_MRP_CRP_UU");
    }
}

