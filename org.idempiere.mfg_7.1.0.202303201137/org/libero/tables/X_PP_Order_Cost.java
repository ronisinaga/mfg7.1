/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_Workflow
 *  org.compiere.model.I_C_AcctSchema
 *  org.compiere.model.I_M_AttributeSetInstance
 *  org.compiere.model.I_M_CostElement
 *  org.compiere.model.I_M_CostType
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
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_CostType;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_Order_Cost;

public class X_PP_Order_Cost
extends PO
implements I_PP_Order_Cost,
I_Persistent {
    private static final long serialVersionUID = 20130626L;
    public static final int COSTINGMETHOD_AD_Reference_ID = 122;
    public static final String COSTINGMETHOD_StandardCosting = "S";
    public static final String COSTINGMETHOD_AveragePO = "A";
    public static final String COSTINGMETHOD_Lifo = "L";
    public static final String COSTINGMETHOD_Fifo = "F";
    public static final String COSTINGMETHOD_LastPOPrice = "p";
    public static final String COSTINGMETHOD_AverageInvoice = "I";
    public static final String COSTINGMETHOD_LastInvoice = "i";
    public static final String COSTINGMETHOD_UserDefined = "U";
    public static final String COSTINGMETHOD__ = "x";

    public X_PP_Order_Cost(Properties ctx, int PP_Order_Cost_ID, String trxName) {
        super(ctx, PP_Order_Cost_ID, trxName);
    }

    public X_PP_Order_Cost(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53024, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_PP_Order_Cost[").append(this.get_ID()).append("]");
        return sb.toString();
    }

    @Override
    public I_AD_Workflow getAD_Workflow() throws RuntimeException {
        return (I_AD_Workflow)MTable.get((Properties)this.getCtx(), (String)"AD_Workflow").getPO(this.getAD_Workflow_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_Workflow_ID(int AD_Workflow_ID) {
        if (AD_Workflow_ID < 1) {
            this.set_Value("AD_Workflow_ID", null);
        } else {
            this.set_Value("AD_Workflow_ID", AD_Workflow_ID);
        }
    }

    @Override
    public int getAD_Workflow_ID() {
        Integer ii = (Integer)this.get_Value("AD_Workflow_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_C_AcctSchema getC_AcctSchema() throws RuntimeException {
        return (I_C_AcctSchema)MTable.get((Properties)this.getCtx(), (String)"C_AcctSchema").getPO(this.getC_AcctSchema_ID(), this.get_TrxName());
    }

    @Override
    public void setC_AcctSchema_ID(int C_AcctSchema_ID) {
        if (C_AcctSchema_ID < 1) {
            this.set_Value("C_AcctSchema_ID", null);
        } else {
            this.set_Value("C_AcctSchema_ID", C_AcctSchema_ID);
        }
    }

    @Override
    public int getC_AcctSchema_ID() {
        Integer ii = (Integer)this.get_Value("C_AcctSchema_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setCostingMethod(String CostingMethod) {
        this.set_ValueNoCheck("CostingMethod", CostingMethod);
    }

    @Override
    public String getCostingMethod() {
        return (String)this.get_Value("CostingMethod");
    }

    @Override
    public void setCumulatedAmt(BigDecimal CumulatedAmt) {
        this.set_ValueNoCheck("CumulatedAmt", CumulatedAmt);
    }

    @Override
    public BigDecimal getCumulatedAmt() {
        BigDecimal bd = (BigDecimal)this.get_Value("CumulatedAmt");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setCumulatedAmtPost(BigDecimal CumulatedAmtPost) {
        this.set_ValueNoCheck("CumulatedAmtPost", CumulatedAmtPost);
    }

    @Override
    public BigDecimal getCumulatedAmtPost() {
        BigDecimal bd = (BigDecimal)this.get_Value("CumulatedAmtPost");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setCumulatedQty(BigDecimal CumulatedQty) {
        this.set_ValueNoCheck("CumulatedQty", CumulatedQty);
    }

    @Override
    public BigDecimal getCumulatedQty() {
        BigDecimal bd = (BigDecimal)this.get_Value("CumulatedQty");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setCumulatedQtyPost(BigDecimal CumulatedQtyPost) {
        this.set_ValueNoCheck("CumulatedQtyPost", CumulatedQtyPost);
    }

    @Override
    public BigDecimal getCumulatedQtyPost() {
        BigDecimal bd = (BigDecimal)this.get_Value("CumulatedQtyPost");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setCurrentCostPrice(BigDecimal CurrentCostPrice) {
        this.set_ValueNoCheck("CurrentCostPrice", CurrentCostPrice);
    }

    @Override
    public BigDecimal getCurrentCostPrice() {
        BigDecimal bd = (BigDecimal)this.get_Value("CurrentCostPrice");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setCurrentCostPriceLL(BigDecimal CurrentCostPriceLL) {
        this.set_ValueNoCheck("CurrentCostPriceLL", CurrentCostPriceLL);
    }

    @Override
    public BigDecimal getCurrentCostPriceLL() {
        BigDecimal bd = (BigDecimal)this.get_Value("CurrentCostPriceLL");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setCurrentQty(BigDecimal CurrentQty) {
        this.set_Value("CurrentQty", CurrentQty);
    }

    @Override
    public BigDecimal getCurrentQty() {
        BigDecimal bd = (BigDecimal)this.get_Value("CurrentQty");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException {
        return (I_M_AttributeSetInstance)MTable.get((Properties)this.getCtx(), (String)"M_AttributeSetInstance").getPO(this.getM_AttributeSetInstance_ID(), this.get_TrxName());
    }

    @Override
    public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
        if (M_AttributeSetInstance_ID < 0) {
            this.set_Value("M_AttributeSetInstance_ID", null);
        } else {
            this.set_Value("M_AttributeSetInstance_ID", M_AttributeSetInstance_ID);
        }
    }

    @Override
    public int getM_AttributeSetInstance_ID() {
        Integer ii = (Integer)this.get_Value("M_AttributeSetInstance_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_M_CostElement getM_CostElement() throws RuntimeException {
        return (I_M_CostElement)MTable.get((Properties)this.getCtx(), (String)"M_CostElement").getPO(this.getM_CostElement_ID(), this.get_TrxName());
    }

    @Override
    public void setM_CostElement_ID(int M_CostElement_ID) {
        if (M_CostElement_ID < 1) {
            this.set_ValueNoCheck("M_CostElement_ID", null);
        } else {
            this.set_ValueNoCheck("M_CostElement_ID", M_CostElement_ID);
        }
    }

    @Override
    public int getM_CostElement_ID() {
        Integer ii = (Integer)this.get_Value("M_CostElement_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_M_CostType getM_CostType() throws RuntimeException {
        return (I_M_CostType)MTable.get((Properties)this.getCtx(), (String)"M_CostType").getPO(this.getM_CostType_ID(), this.get_TrxName());
    }

    @Override
    public void setM_CostType_ID(int M_CostType_ID) {
        if (M_CostType_ID < 1) {
            this.set_Value("M_CostType_ID", null);
        } else {
            this.set_Value("M_CostType_ID", M_CostType_ID);
        }
    }

    @Override
    public int getM_CostType_ID() {
        Integer ii = (Integer)this.get_Value("M_CostType_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_M_Product getM_Product() throws RuntimeException {
        return (I_M_Product)MTable.get((Properties)this.getCtx(), (String)"M_Product").getPO(this.getM_Product_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Product_ID(int M_Product_ID) {
        if (M_Product_ID < 1) {
            this.set_ValueNoCheck("M_Product_ID", null);
        } else {
            this.set_ValueNoCheck("M_Product_ID", M_Product_ID);
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
    public void setPP_Order_Cost_ID(int PP_Order_Cost_ID) {
        if (PP_Order_Cost_ID < 1) {
            this.set_ValueNoCheck("PP_Order_Cost_ID", null);
        } else {
            this.set_ValueNoCheck("PP_Order_Cost_ID", PP_Order_Cost_ID);
        }
    }

    @Override
    public int getPP_Order_Cost_ID() {
        Integer ii = (Integer)this.get_Value("PP_Order_Cost_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPP_Order_Cost_UU(String PP_Order_Cost_UU) {
        this.set_Value("PP_Order_Cost_UU", PP_Order_Cost_UU);
    }

    @Override
    public String getPP_Order_Cost_UU() {
        return (String)this.get_Value("PP_Order_Cost_UU");
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
}

