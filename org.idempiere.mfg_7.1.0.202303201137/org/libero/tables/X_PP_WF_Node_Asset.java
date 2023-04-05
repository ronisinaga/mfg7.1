/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_WF_Node
 *  org.compiere.model.I_A_Asset
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 */
package org.libero.tables;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_A_Asset;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.libero.tables.I_PP_WF_Node_Asset;

public class X_PP_WF_Node_Asset
extends PO
implements I_PP_WF_Node_Asset,
I_Persistent {
    private static final long serialVersionUID = 20130626L;

    public X_PP_WF_Node_Asset(Properties ctx, int PP_WF_Node_Asset_ID, String trxName) {
        super(ctx, PP_WF_Node_Asset_ID, trxName);
    }

    public X_PP_WF_Node_Asset(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53017, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_PP_WF_Node_Asset[").append(this.get_ID()).append("]");
        return sb.toString();
    }

    @Override
    public I_A_Asset getA_Asset() throws RuntimeException {
        return (I_A_Asset)MTable.get((Properties)this.getCtx(), (String)"A_Asset").getPO(this.getA_Asset_ID(), this.get_TrxName());
    }

    @Override
    public void setA_Asset_ID(int A_Asset_ID) {
        if (A_Asset_ID < 1) {
            this.set_Value("A_Asset_ID", null);
        } else {
            this.set_Value("A_Asset_ID", A_Asset_ID);
        }
    }

    @Override
    public int getA_Asset_ID() {
        Integer ii = (Integer)this.get_Value("A_Asset_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_AD_WF_Node getAD_WF_Node() throws RuntimeException {
        return (I_AD_WF_Node)MTable.get((Properties)this.getCtx(), (String)"AD_WF_Node").getPO(this.getAD_WF_Node_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_WF_Node_ID(int AD_WF_Node_ID) {
        if (AD_WF_Node_ID < 1) {
            this.set_ValueNoCheck("AD_WF_Node_ID", null);
        } else {
            this.set_ValueNoCheck("AD_WF_Node_ID", AD_WF_Node_ID);
        }
    }

    @Override
    public int getAD_WF_Node_ID() {
        Integer ii = (Integer)this.get_Value("AD_WF_Node_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPP_WF_Node_Asset_ID(int PP_WF_Node_Asset_ID) {
        if (PP_WF_Node_Asset_ID < 1) {
            this.set_ValueNoCheck("PP_WF_Node_Asset_ID", null);
        } else {
            this.set_ValueNoCheck("PP_WF_Node_Asset_ID", PP_WF_Node_Asset_ID);
        }
    }

    @Override
    public int getPP_WF_Node_Asset_ID() {
        Integer ii = (Integer)this.get_Value("PP_WF_Node_Asset_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPP_WF_Node_Asset_UU(String PP_WF_Node_Asset_UU) {
        this.set_Value("PP_WF_Node_Asset_UU", PP_WF_Node_Asset_UU);
    }

    @Override
    public String getPP_WF_Node_Asset_UU() {
        return (String)this.get_Value("PP_WF_Node_Asset_UU");
    }

    @Override
    public void setSeqNo(int SeqNo) {
        this.set_ValueNoCheck("SeqNo", SeqNo);
    }

    @Override
    public int getSeqNo() {
        Integer ii = (Integer)this.get_Value("SeqNo");
        if (ii == null) {
            return 0;
        }
        return ii;
    }
}

