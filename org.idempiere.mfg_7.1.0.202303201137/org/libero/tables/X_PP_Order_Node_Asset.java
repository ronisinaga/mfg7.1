/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_A_Asset
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.tables;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_A_Asset;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_Order_Node;
import org.libero.tables.I_PP_Order_Node_Asset;
import org.libero.tables.I_PP_Order_Workflow;

public class X_PP_Order_Node_Asset
extends PO
implements I_PP_Order_Node_Asset,
I_Persistent {
    private static final long serialVersionUID = 20130626L;

    public X_PP_Order_Node_Asset(Properties ctx, int PP_Order_Node_Asset_ID, String trxName) {
        super(ctx, PP_Order_Node_Asset_ID, trxName);
    }

    public X_PP_Order_Node_Asset(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53031, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_PP_Order_Node_Asset[").append(this.get_ID()).append("]");
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
    public I_PP_Order getPP_Order() throws RuntimeException {
        return (I_PP_Order)MTable.get((Properties)this.getCtx(), (String)"PP_Order").getPO(this.getPP_Order_ID(), this.get_TrxName());
    }

    @Override
    public void setPP_Order_ID(int PP_Order_ID) {
        if (PP_Order_ID < 1) {
            this.set_ValueNoCheck("PP_Order_ID", null);
        } else {
            this.set_ValueNoCheck("PP_Order_ID", PP_Order_ID);
        }
    }

    @Override
    public int getPP_Order_ID() {
        Integer ii = (Integer)this.get_Value("PP_Order_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPP_Order_Node_Asset_ID(int PP_Order_Node_Asset_ID) {
        if (PP_Order_Node_Asset_ID < 1) {
            this.set_ValueNoCheck("PP_Order_Node_Asset_ID", null);
        } else {
            this.set_ValueNoCheck("PP_Order_Node_Asset_ID", PP_Order_Node_Asset_ID);
        }
    }

    @Override
    public int getPP_Order_Node_Asset_ID() {
        Integer ii = (Integer)this.get_Value("PP_Order_Node_Asset_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPP_Order_Node_Asset_UU(String PP_Order_Node_Asset_UU) {
        this.set_Value("PP_Order_Node_Asset_UU", PP_Order_Node_Asset_UU);
    }

    @Override
    public String getPP_Order_Node_Asset_UU() {
        return (String)this.get_Value("PP_Order_Node_Asset_UU");
    }

    @Override
    public I_PP_Order_Node getPP_Order_Node() throws RuntimeException {
        return (I_PP_Order_Node)MTable.get((Properties)this.getCtx(), (String)"PP_Order_Node").getPO(this.getPP_Order_Node_ID(), this.get_TrxName());
    }

    @Override
    public void setPP_Order_Node_ID(int PP_Order_Node_ID) {
        if (PP_Order_Node_ID < 1) {
            this.set_ValueNoCheck("PP_Order_Node_ID", null);
        } else {
            this.set_ValueNoCheck("PP_Order_Node_ID", PP_Order_Node_ID);
        }
    }

    @Override
    public int getPP_Order_Node_ID() {
        Integer ii = (Integer)this.get_Value("PP_Order_Node_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_PP_Order_Workflow getPP_Order_Workflow() throws RuntimeException {
        return (I_PP_Order_Workflow)MTable.get((Properties)this.getCtx(), (String)"PP_Order_Workflow").getPO(this.getPP_Order_Workflow_ID(), this.get_TrxName());
    }

    @Override
    public void setPP_Order_Workflow_ID(int PP_Order_Workflow_ID) {
        if (PP_Order_Workflow_ID < 1) {
            this.set_ValueNoCheck("PP_Order_Workflow_ID", null);
        } else {
            this.set_ValueNoCheck("PP_Order_Workflow_ID", PP_Order_Workflow_ID);
        }
    }

    @Override
    public int getPP_Order_Workflow_ID() {
        Integer ii = (Integer)this.get_Value("PP_Order_Workflow_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }
}

