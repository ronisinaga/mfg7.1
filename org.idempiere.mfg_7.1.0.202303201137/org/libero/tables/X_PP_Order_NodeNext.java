/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_WF_Node
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.tables;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_Order_Node;
import org.libero.tables.I_PP_Order_NodeNext;

public class X_PP_Order_NodeNext
extends PO
implements I_PP_Order_NodeNext,
I_Persistent {
    private static final long serialVersionUID = 20130626L;
    public static final int ENTITYTYPE_AD_Reference_ID = 389;

    public X_PP_Order_NodeNext(Properties ctx, int PP_Order_NodeNext_ID, String trxName) {
        super(ctx, PP_Order_NodeNext_ID, trxName);
    }

    public X_PP_Order_NodeNext(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53023, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_PP_Order_NodeNext[").append(this.get_ID()).append("]");
        return sb.toString();
    }

    @Override
    public I_AD_WF_Node getAD_WF_Next() throws RuntimeException {
        return (I_AD_WF_Node)MTable.get((Properties)this.getCtx(), (String)"AD_WF_Node").getPO(this.getAD_WF_Next_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_WF_Next_ID(int AD_WF_Next_ID) {
        if (AD_WF_Next_ID < 1) {
            this.set_Value("AD_WF_Next_ID", null);
        } else {
            this.set_Value("AD_WF_Next_ID", AD_WF_Next_ID);
        }
    }

    @Override
    public int getAD_WF_Next_ID() {
        Integer ii = (Integer)this.get_Value("AD_WF_Next_ID");
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
            this.set_Value("AD_WF_Node_ID", null);
        } else {
            this.set_Value("AD_WF_Node_ID", AD_WF_Node_ID);
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
    public void setDescription(String Description) {
        this.set_Value("Description", Description);
    }

    @Override
    public String getDescription() {
        return (String)this.get_Value("Description");
    }

    @Override
    public void setEntityType(String EntityType) {
        this.set_Value("EntityType", EntityType);
    }

    @Override
    public String getEntityType() {
        return (String)this.get_Value("EntityType");
    }

    @Override
    public void setIsStdUserWorkflow(boolean IsStdUserWorkflow) {
        this.set_Value("IsStdUserWorkflow", IsStdUserWorkflow);
    }

    @Override
    public boolean isStdUserWorkflow() {
        Object oo = this.get_Value("IsStdUserWorkflow");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
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
    public I_PP_Order_Node getPP_Order_Next() throws RuntimeException {
        return (I_PP_Order_Node)MTable.get((Properties)this.getCtx(), (String)"PP_Order_Node").getPO(this.getPP_Order_Next_ID(), this.get_TrxName());
    }

    @Override
    public void setPP_Order_Next_ID(int PP_Order_Next_ID) {
        if (PP_Order_Next_ID < 1) {
            this.set_Value("PP_Order_Next_ID", null);
        } else {
            this.set_Value("PP_Order_Next_ID", PP_Order_Next_ID);
        }
    }

    @Override
    public int getPP_Order_Next_ID() {
        Integer ii = (Integer)this.get_Value("PP_Order_Next_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
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
    public void setPP_Order_NodeNext_ID(int PP_Order_NodeNext_ID) {
        if (PP_Order_NodeNext_ID < 1) {
            this.set_ValueNoCheck("PP_Order_NodeNext_ID", null);
        } else {
            this.set_ValueNoCheck("PP_Order_NodeNext_ID", PP_Order_NodeNext_ID);
        }
    }

    @Override
    public int getPP_Order_NodeNext_ID() {
        Integer ii = (Integer)this.get_Value("PP_Order_NodeNext_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPP_Order_NodeNext_UU(String PP_Order_NodeNext_UU) {
        this.set_Value("PP_Order_NodeNext_UU", PP_Order_NodeNext_UU);
    }

    @Override
    public String getPP_Order_NodeNext_UU() {
        return (String)this.get_Value("PP_Order_NodeNext_UU");
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
    public void setTransitionCode(String TransitionCode) {
        this.set_Value("TransitionCode", TransitionCode);
    }

    @Override
    public String getTransitionCode() {
        return (String)this.get_Value("TransitionCode");
    }
}

