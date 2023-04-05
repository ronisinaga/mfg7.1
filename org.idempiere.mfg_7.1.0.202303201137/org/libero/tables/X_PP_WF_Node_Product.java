/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_WF_Node
 *  org.compiere.model.I_M_Product
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 *  org.compiere.util.Env
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.libero.tables.I_PP_WF_Node_Product;

public class X_PP_WF_Node_Product
extends PO
implements I_PP_WF_Node_Product,
I_Persistent {
    private static final long serialVersionUID = 20130626L;
    public static final int CONFIGURATIONLEVEL_AD_Reference_ID = 53222;
    public static final String CONFIGURATIONLEVEL_System = "S";
    public static final String CONFIGURATIONLEVEL_Client = "C";
    public static final String CONFIGURATIONLEVEL_Organization = "O";
    public static final int ENTITYTYPE_AD_Reference_ID = 389;

    public X_PP_WF_Node_Product(Properties ctx, int PP_WF_Node_Product_ID, String trxName) {
        super(ctx, PP_WF_Node_Product_ID, trxName);
    }

    public X_PP_WF_Node_Product(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53016, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_PP_WF_Node_Product[").append(this.get_ID()).append("]");
        return sb.toString();
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
    public void setConfigurationLevel(String ConfigurationLevel) {
        this.set_Value("ConfigurationLevel", ConfigurationLevel);
    }

    @Override
    public String getConfigurationLevel() {
        return (String)this.get_Value("ConfigurationLevel");
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
    public void setPP_WF_Node_Product_ID(int PP_WF_Node_Product_ID) {
        if (PP_WF_Node_Product_ID < 1) {
            this.set_ValueNoCheck("PP_WF_Node_Product_ID", null);
        } else {
            this.set_ValueNoCheck("PP_WF_Node_Product_ID", PP_WF_Node_Product_ID);
        }
    }

    @Override
    public int getPP_WF_Node_Product_ID() {
        Integer ii = (Integer)this.get_Value("PP_WF_Node_Product_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPP_WF_Node_Product_UU(String PP_WF_Node_Product_UU) {
        this.set_Value("PP_WF_Node_Product_UU", PP_WF_Node_Product_UU);
    }

    @Override
    public String getPP_WF_Node_Product_UU() {
        return (String)this.get_Value("PP_WF_Node_Product_UU");
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

