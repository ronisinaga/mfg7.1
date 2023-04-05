/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_PInstance
 *  org.compiere.model.I_C_AcctSchema
 *  org.compiere.model.I_M_CostElement
 *  org.compiere.model.I_M_CostType
 *  org.compiere.model.I_M_Product
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_PP_Product_BOM
 *  org.eevolution.model.I_PP_Product_BOMLine
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_CostType;
import org.compiere.model.I_M_Product;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

public interface I_T_BOMLine {
    public static final String Table_Name = "T_BOMLine";
    public static final int Table_ID = 53045;
    public static final KeyNamePair Model = new KeyNamePair(53045, "T_BOMLine");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";
    public static final String COLUMNNAME_Cost = "Cost";
    public static final String COLUMNNAME_CostingMethod = "CostingMethod";
    public static final String COLUMNNAME_CostStandard = "CostStandard";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_CurrentCostPrice = "CurrentCostPrice";
    public static final String COLUMNNAME_CurrentCostPriceLL = "CurrentCostPriceLL";
    public static final String COLUMNNAME_FutureCostPrice = "FutureCostPrice";
    public static final String COLUMNNAME_FutureCostPriceLL = "FutureCostPriceLL";
    public static final String COLUMNNAME_Implosion = "Implosion";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_IsCostFrozen = "IsCostFrozen";
    public static final String COLUMNNAME_LevelNo = "LevelNo";
    public static final String COLUMNNAME_Levels = "Levels";
    public static final String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";
    public static final String COLUMNNAME_M_CostType_ID = "M_CostType_ID";
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
    public static final String COLUMNNAME_PP_Product_BOM_ID = "PP_Product_BOM_ID";
    public static final String COLUMNNAME_PP_Product_BOMLine_ID = "PP_Product_BOMLine_ID";
    public static final String COLUMNNAME_QtyBOM = "QtyBOM";
    public static final String COLUMNNAME_Sel_Product_ID = "Sel_Product_ID";
    public static final String COLUMNNAME_SeqNo = "SeqNo";
    public static final String COLUMNNAME_T_BOMLine_ID = "T_BOMLine_ID";
    public static final String COLUMNNAME_T_BOMLine_UU = "T_BOMLine_UU";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setAD_PInstance_ID(int var1);

    public int getAD_PInstance_ID();

    public I_AD_PInstance getAD_PInstance() throws RuntimeException;

    public void setC_AcctSchema_ID(int var1);

    public int getC_AcctSchema_ID();

    public I_C_AcctSchema getC_AcctSchema() throws RuntimeException;

    public void setCost(BigDecimal var1);

    public BigDecimal getCost();

    public void setCostingMethod(String var1);

    public String getCostingMethod();

    public void setCostStandard(BigDecimal var1);

    public BigDecimal getCostStandard();

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setCurrentCostPrice(BigDecimal var1);

    public BigDecimal getCurrentCostPrice();

    public void setCurrentCostPriceLL(BigDecimal var1);

    public BigDecimal getCurrentCostPriceLL();

    public void setFutureCostPrice(BigDecimal var1);

    public BigDecimal getFutureCostPrice();

    public void setFutureCostPriceLL(BigDecimal var1);

    public BigDecimal getFutureCostPriceLL();

    public void setImplosion(boolean var1);

    public boolean isImplosion();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setIsCostFrozen(boolean var1);

    public boolean isCostFrozen();

    public void setLevelNo(int var1);

    public int getLevelNo();

    public void setLevels(String var1);

    public String getLevels();

    public void setM_CostElement_ID(int var1);

    public int getM_CostElement_ID();

    public I_M_CostElement getM_CostElement() throws RuntimeException;

    public void setM_CostType_ID(int var1);

    public int getM_CostType_ID();

    public I_M_CostType getM_CostType() throws RuntimeException;

    public void setM_Product_ID(int var1);

    public int getM_Product_ID();

    public I_M_Product getM_Product() throws RuntimeException;

    public void setPP_Product_BOM_ID(int var1);

    public int getPP_Product_BOM_ID();

    public I_PP_Product_BOM getPP_Product_BOM() throws RuntimeException;

    public void setPP_Product_BOMLine_ID(int var1);

    public int getPP_Product_BOMLine_ID();

    public I_PP_Product_BOMLine getPP_Product_BOMLine() throws RuntimeException;

    public void setQtyBOM(BigDecimal var1);

    public BigDecimal getQtyBOM();

    public void setSel_Product_ID(int var1);

    public int getSel_Product_ID();

    public void setSeqNo(int var1);

    public int getSeqNo();

    public void setT_BOMLine_ID(int var1);

    public int getT_BOMLine_ID();

    public void setT_BOMLine_UU(String var1);

    public String getT_BOMLine_UU();

    public Timestamp getUpdated();

    public int getUpdatedBy();
}

