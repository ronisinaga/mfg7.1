/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_C_AcctSchema
 *  org.compiere.model.I_C_ValidCombination
 *  org.compiere.model.I_M_Product
 *  org.compiere.model.MTable
 *  org.compiere.util.KeyNamePair
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_M_Product;
import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

public interface I_M_Product_Acct {
    public static final String Table_Name = "M_Product_Acct";
    public static final int Table_ID = MTable.getTable_ID((String)"M_Product_Acct");
    public static final KeyNamePair Model = new KeyNamePair(Table_ID, "M_Product_Acct");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
    public static final String COLUMNNAME_P_Asset_Acct = "P_Asset_Acct";
    public static final String COLUMNNAME_P_AverageCostVariance_Acct = "P_AverageCostVariance_Acct";
    public static final String COLUMNNAME_P_Burden_Acct = "P_Burden_Acct";
    public static final String COLUMNNAME_P_COGS_Acct = "P_COGS_Acct";
    public static final String COLUMNNAME_P_CostAdjustment_Acct = "P_CostAdjustment_Acct";
    public static final String COLUMNNAME_P_CostOfProduction_Acct = "P_CostOfProduction_Acct";
    public static final String COLUMNNAME_P_Expense_Acct = "P_Expense_Acct";
    public static final String COLUMNNAME_P_FloorStock_Acct = "P_FloorStock_Acct";
    public static final String COLUMNNAME_P_InventoryClearing_Acct = "P_InventoryClearing_Acct";
    public static final String COLUMNNAME_P_InvoicePriceVariance_Acct = "P_InvoicePriceVariance_Acct";
    public static final String COLUMNNAME_P_Labor_Acct = "P_Labor_Acct";
    public static final String COLUMNNAME_P_MethodChangeVariance_Acct = "P_MethodChangeVariance_Acct";
    public static final String COLUMNNAME_P_MixVariance_Acct = "P_MixVariance_Acct";
    public static final String COLUMNNAME_P_OutsideProcessing_Acct = "P_OutsideProcessing_Acct";
    public static final String COLUMNNAME_P_Overhead_Acct = "P_Overhead_Acct";
    public static final String COLUMNNAME_P_PurchasePriceVariance_Acct = "P_PurchasePriceVariance_Acct";
    public static final String COLUMNNAME_P_RateVariance_Acct = "P_RateVariance_Acct";
    public static final String COLUMNNAME_P_Revenue_Acct = "P_Revenue_Acct";
    public static final String COLUMNNAME_P_Scrap_Acct = "P_Scrap_Acct";
    public static final String COLUMNNAME_P_TradeDiscountGrant_Acct = "P_TradeDiscountGrant_Acct";
    public static final String COLUMNNAME_P_TradeDiscountRec_Acct = "P_TradeDiscountRec_Acct";
    public static final String COLUMNNAME_P_UsageVariance_Acct = "P_UsageVariance_Acct";
    public static final String COLUMNNAME_P_WIP_Acct = "P_WIP_Acct";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setC_AcctSchema_ID(int var1);

    public int getC_AcctSchema_ID();

    public I_C_AcctSchema getC_AcctSchema() throws RuntimeException;

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setM_Product_ID(int var1);

    public int getM_Product_ID();

    public I_M_Product getM_Product() throws RuntimeException;

    public void setP_Asset_Acct(int var1);

    public int getP_Asset_Acct();

    public I_C_ValidCombination getP_Asset_A() throws RuntimeException;

    public void setP_AverageCostVariance_Acct(int var1);

    public int getP_AverageCostVariance_Acct();

    public I_C_ValidCombination getP_AverageCostVariance_A() throws RuntimeException;

    public void setP_Burden_Acct(int var1);

    public int getP_Burden_Acct();

    public I_C_ValidCombination getP_Burden_A() throws RuntimeException;

    public void setP_COGS_Acct(int var1);

    public int getP_COGS_Acct();

    public I_C_ValidCombination getP_COGS_A() throws RuntimeException;

    public void setP_CostAdjustment_Acct(int var1);

    public int getP_CostAdjustment_Acct();

    public I_C_ValidCombination getP_CostAdjustment_A() throws RuntimeException;

    public void setP_CostOfProduction_Acct(int var1);

    public int getP_CostOfProduction_Acct();

    public I_C_ValidCombination getP_CostOfProduction_A() throws RuntimeException;

    public void setP_Expense_Acct(int var1);

    public int getP_Expense_Acct();

    public I_C_ValidCombination getP_Expense_A() throws RuntimeException;

    public void setP_FloorStock_Acct(int var1);

    public int getP_FloorStock_Acct();

    public I_C_ValidCombination getP_FloorStock_A() throws RuntimeException;

    public void setP_InventoryClearing_Acct(int var1);

    public int getP_InventoryClearing_Acct();

    public I_C_ValidCombination getP_InventoryClearing_A() throws RuntimeException;

    public void setP_InvoicePriceVariance_Acct(int var1);

    public int getP_InvoicePriceVariance_Acct();

    public I_C_ValidCombination getP_InvoicePriceVariance_A() throws RuntimeException;

    public void setP_Labor_Acct(int var1);

    public int getP_Labor_Acct();

    public I_C_ValidCombination getP_Labor_A() throws RuntimeException;

    public void setP_MethodChangeVariance_Acct(int var1);

    public int getP_MethodChangeVariance_Acct();

    public I_C_ValidCombination getP_MethodChangeVariance_A() throws RuntimeException;

    public void setP_MixVariance_Acct(int var1);

    public int getP_MixVariance_Acct();

    public I_C_ValidCombination getP_MixVariance_A() throws RuntimeException;

    public void setP_OutsideProcessing_Acct(int var1);

    public int getP_OutsideProcessing_Acct();

    public I_C_ValidCombination getP_OutsideProcessing_A() throws RuntimeException;

    public void setP_Overhead_Acct(int var1);

    public int getP_Overhead_Acct();

    public I_C_ValidCombination getP_Overhead_A() throws RuntimeException;

    public void setP_PurchasePriceVariance_Acct(int var1);

    public int getP_PurchasePriceVariance_Acct();

    public I_C_ValidCombination getP_PurchasePriceVariance_A() throws RuntimeException;

    public void setP_RateVariance_Acct(int var1);

    public int getP_RateVariance_Acct();

    public I_C_ValidCombination getP_RateVariance_A() throws RuntimeException;

    public void setP_Revenue_Acct(int var1);

    public int getP_Revenue_Acct();

    public I_C_ValidCombination getP_Revenue_A() throws RuntimeException;

    public void setP_Scrap_Acct(int var1);

    public int getP_Scrap_Acct();

    public I_C_ValidCombination getP_Scrap_A() throws RuntimeException;

    public void setP_TradeDiscountGrant_Acct(int var1);

    public int getP_TradeDiscountGrant_Acct();

    public I_C_ValidCombination getP_TradeDiscountGrant_A() throws RuntimeException;

    public void setP_TradeDiscountRec_Acct(int var1);

    public int getP_TradeDiscountRec_Acct();

    public I_C_ValidCombination getP_TradeDiscountRec_A() throws RuntimeException;

    public void setP_UsageVariance_Acct(int var1);

    public int getP_UsageVariance_Acct();

    public I_C_ValidCombination getP_UsageVariance_A() throws RuntimeException;

    public void setP_WIP_Acct(int var1);

    public int getP_WIP_Acct();

    public I_C_ValidCombination getP_WIP_A() throws RuntimeException;

    public Timestamp getUpdated();

    public int getUpdatedBy();
}

