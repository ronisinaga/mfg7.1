/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_M_Product
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 *  org.compiere.util.Env
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_Order_Node;
import org.libero.tables.I_PP_Order_Node_Product;
import org.libero.tables.I_PP_Order_Workflow;

public class X_PP_Order_Node_Product
extends PO
implements I_PP_Order_Node_Product,
I_Persistent {
    private static final long serialVersionUID = 20130626L;

    public X_PP_Order_Node_Product(Properties ctx, int PP_Order_Node_Product_ID, String trxName) {
        super(ctx, PP_Order_Node_Product_ID, trxName);
    }

    public X_PP_Order_Node_Product(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53030, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_PP_Order_Node_Product[").append(this.get_ID()).append("]");
        return sb.toString();
    }

    @Override
    public void setIsSubcontracting(boolean IsSubcontracting) {
        this.set_Value("IsSubcontracting", IsSubcontracting);
    }

    @Override
    public boolean isSubcontracting() {
        Object oo = this.get_Value("IsSubcontracting");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public I_M_Product getM_Product() throws RuntimeException {
        return (I_M_Product)MTable.get((Properties)this.getCtx(), (String)"M_Product").getPO(this.getM_Product_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Product_ID(int M_Product_ID) {
        if (M_Product_ID < 1) {
            this.set_Value("M_Product_ID", null);
        } else {
            this.set_Value("M_Product_ID", M_Product_ID);
        }
    }

    @Override
    public int getM_Product_ID() {
        Integer ii = (Integer)this.get_Value("M_Product_ID");
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
    public void setPP_Order_Node_Product_ID(int PP_Order_Node_Product_ID) {
        if (PP_Order_Node_Product_ID < 1) {
            this.set_ValueNoCheck("PP_Order_Node_Product_ID", null);
        } else {
            this.set_ValueNoCheck("PP_Order_Node_Product_ID", PP_Order_Node_Product_ID);
        }
    }

    @Override
    public int getPP_Order_Node_Product_ID() {
        Integer ii = (Integer)this.get_Value("PP_Order_Node_Product_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPP_Order_Node_Product_UU(String PP_Order_Node_Product_UU) {
        this.set_Value("PP_Order_Node_Product_UU", PP_Order_Node_Product_UU);
    }

    @Override
    public String getPP_Order_Node_Product_UU() {
        return (String)this.get_Value("PP_Order_Node_Product_UU");
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

    @Override
    public void setQty(BigDecimal Qty) {
        this.set_Value("Qty", Qty);
    }

    @Override
    public BigDecimal getQty() {
        BigDecimal bd = (BigDecimal)this.get_Value("Qty");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
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
}

