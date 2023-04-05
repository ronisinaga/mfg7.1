/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_PInstance
 *  org.compiere.model.I_C_AcctSchema
 *  org.compiere.model.I_M_CostElement
 *  org.compiere.model.I_M_CostType
 *  org.compiere.model.I_M_Product
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 *  org.compiere.util.Env
 *  org.eevolution.model.I_PP_Product_BOM
 *  org.eevolution.model.I_PP_Product_BOMLine
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_CostType;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.libero.tables.I_T_BOMLine;

public class X_T_BOMLine
extends PO
implements I_T_BOMLine,
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

    public X_T_BOMLine(Properties ctx, int T_BOMLine_ID, String trxName) {
        super(ctx, T_BOMLine_ID, trxName);
    }

    public X_T_BOMLine(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53045, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_T_BOMLine[").append(this.get_ID()).append("]");
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
    public void setCost(BigDecimal Cost) {
        this.set_Value("Cost", Cost);
    }

    @Override
    public BigDecimal getCost() {
        BigDecimal bd = (BigDecimal)this.get_Value("Cost");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setCostingMethod(String CostingMethod) {
        this.set_Value("CostingMethod", CostingMethod);
    }

    @Override
    public String getCostingMethod() {
        return (String)this.get_Value("CostingMethod");
    }

    @Override
    public void setCostStandard(BigDecimal CostStandard) {
        this.set_Value("CostStandard", CostStandard);
    }

    @Override
    public BigDecimal getCostStandard() {
        BigDecimal bd = (BigDecimal)this.get_Value("CostStandard");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setCurrentCostPrice(BigDecimal CurrentCostPrice) {
        this.set_Value("CurrentCostPrice", CurrentCostPrice);
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
        this.set_Value("CurrentCostPriceLL", CurrentCostPriceLL);
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
    public void setFutureCostPrice(BigDecimal FutureCostPrice) {
        this.set_Value("FutureCostPrice", FutureCostPrice);
    }

    @Override
    public BigDecimal getFutureCostPrice() {
        BigDecimal bd = (BigDecimal)this.get_Value("FutureCostPrice");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setFutureCostPriceLL(BigDecimal FutureCostPriceLL) {
        this.set_Value("FutureCostPriceLL", FutureCostPriceLL);
    }

    @Override
    public BigDecimal getFutureCostPriceLL() {
        BigDecimal bd = (BigDecimal)this.get_Value("FutureCostPriceLL");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setImplosion(boolean Implosion) {
        this.set_Value("Implosion", Implosion);
    }

    @Override
    public boolean isImplosion() {
        Object oo = this.get_Value("Implosion");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIsCostFrozen(boolean IsCostFrozen) {
        this.set_Value("IsCostFrozen", IsCostFrozen);
    }

    @Override
    public boolean isCostFrozen() {
        Object oo = this.get_Value("IsCostFrozen");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setLevelNo(int LevelNo) {
        this.set_Value("LevelNo", LevelNo);
    }

    @Override
    public int getLevelNo() {
        Integer ii = (Integer)this.get_Value("LevelNo");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setLevels(String Levels) {
        this.set_Value("Levels", Levels);
    }

    @Override
    public String getLevels() {
        return (String)this.get_Value("Levels");
    }

    @Override
    public I_M_CostElement getM_CostElement() throws RuntimeException {
        return (I_M_CostElement)MTable.get((Properties)this.getCtx(), (String)"M_CostElement").getPO(this.getM_CostElement_ID(), this.get_TrxName());
    }

    @Override
    public void setM_CostElement_ID(int M_CostElement_ID) {
        if (M_CostElement_ID < 1) {
            this.set_Value("M_CostElement_ID", null);
        } else {
            this.set_Value("M_CostElement_ID", M_CostElement_ID);
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
    public I_PP_Product_BOM getPP_Product_BOM() throws RuntimeException {
        return (I_PP_Product_BOM)MTable.get((Properties)this.getCtx(), (String)"PP_Product_BOM").getPO(this.getPP_Product_BOM_ID(), this.get_TrxName());
    }

    @Override
    public void setPP_Product_BOM_ID(int PP_Product_BOM_ID) {
        if (PP_Product_BOM_ID < 1) {
            this.set_Value("PP_Product_BOM_ID", null);
        } else {
            this.set_Value("PP_Product_BOM_ID", PP_Product_BOM_ID);
        }
    }

    @Override
    public int getPP_Product_BOM_ID() {
        Integer ii = (Integer)this.get_Value("PP_Product_BOM_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_PP_Product_BOMLine getPP_Product_BOMLine() throws RuntimeException {
        return (I_PP_Product_BOMLine)MTable.get((Properties)this.getCtx(), (String)"PP_Product_BOMLine").getPO(this.getPP_Product_BOMLine_ID(), this.get_TrxName());
    }

    @Override
    public void setPP_Product_BOMLine_ID(int PP_Product_BOMLine_ID) {
        if (PP_Product_BOMLine_ID < 1) {
            this.set_Value("PP_Product_BOMLine_ID", null);
        } else {
            this.set_Value("PP_Product_BOMLine_ID", PP_Product_BOMLine_ID);
        }
    }

    @Override
    public int getPP_Product_BOMLine_ID() {
        Integer ii = (Integer)this.get_Value("PP_Product_BOMLine_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setQtyBOM(BigDecimal QtyBOM) {
        this.set_Value("QtyBOM", QtyBOM);
    }

    @Override
    public BigDecimal getQtyBOM() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyBOM");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setSel_Product_ID(int Sel_Product_ID) {
        if (Sel_Product_ID < 1) {
            this.set_Value("Sel_Product_ID", null);
        } else {
            this.set_Value("Sel_Product_ID", Sel_Product_ID);
        }
    }

    @Override
    public int getSel_Product_ID() {
        Integer ii = (Integer)this.get_Value("Sel_Product_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
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
    public void setT_BOMLine_ID(int T_BOMLine_ID) {
        if (T_BOMLine_ID < 1) {
            this.set_ValueNoCheck("T_BOMLine_ID", null);
        } else {
            this.set_ValueNoCheck("T_BOMLine_ID", T_BOMLine_ID);
        }
    }

    @Override
    public int getT_BOMLine_ID() {
        Integer ii = (Integer)this.get_Value("T_BOMLine_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setT_BOMLine_UU(String T_BOMLine_UU) {
        this.set_Value("T_BOMLine_UU", T_BOMLine_UU);
    }

    @Override
    public String getT_BOMLine_UU() {
        return (String)this.get_Value("T_BOMLine_UU");
    }
}

