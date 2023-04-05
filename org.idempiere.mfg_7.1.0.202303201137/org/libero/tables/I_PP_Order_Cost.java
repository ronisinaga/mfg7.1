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
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_CostType;
import org.compiere.model.I_M_Product;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_PP_Order;

public interface I_PP_Order_Cost {
    public static final String Table_Name = "PP_Order_Cost";
    public static final int Table_ID = 53024;
    public static final KeyNamePair Model = new KeyNamePair(53024, "PP_Order_Cost");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";
    public static final String COLUMNNAME_CostingMethod = "CostingMethod";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_CumulatedAmt = "CumulatedAmt";
    public static final String COLUMNNAME_CumulatedAmtPost = "CumulatedAmtPost";
    public static final String COLUMNNAME_CumulatedQty = "CumulatedQty";
    public static final String COLUMNNAME_CumulatedQtyPost = "CumulatedQtyPost";
    public static final String COLUMNNAME_CurrentCostPrice = "CurrentCostPrice";
    public static final String COLUMNNAME_CurrentCostPriceLL = "CurrentCostPriceLL";
    public static final String COLUMNNAME_CurrentQty = "CurrentQty";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";
    public static final String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";
    public static final String COLUMNNAME_M_CostType_ID = "M_CostType_ID";
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
    public static final String COLUMNNAME_PP_Order_Cost_ID = "PP_Order_Cost_ID";
    public static final String COLUMNNAME_PP_Order_Cost_UU = "PP_Order_Cost_UU";
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setAD_Workflow_ID(int var1);

    public int getAD_Workflow_ID();

    public I_AD_Workflow getAD_Workflow() throws RuntimeException;

    public void setC_AcctSchema_ID(int var1);

    public int getC_AcctSchema_ID();

    public I_C_AcctSchema getC_AcctSchema() throws RuntimeException;

    public void setCostingMethod(String var1);

    public String getCostingMethod();

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setCumulatedAmt(BigDecimal var1);

    public BigDecimal getCumulatedAmt();

    public void setCumulatedAmtPost(BigDecimal var1);

    public BigDecimal getCumulatedAmtPost();

    public void setCumulatedQty(BigDecimal var1);

    public BigDecimal getCumulatedQty();

    public void setCumulatedQtyPost(BigDecimal var1);

    public BigDecimal getCumulatedQtyPost();

    public void setCurrentCostPrice(BigDecimal var1);

    public BigDecimal getCurrentCostPrice();

    public void setCurrentCostPriceLL(BigDecimal var1);

    public BigDecimal getCurrentCostPriceLL();

    public void setCurrentQty(BigDecimal var1);

    public BigDecimal getCurrentQty();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setM_AttributeSetInstance_ID(int var1);

    public int getM_AttributeSetInstance_ID();

    public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

    public void setM_CostElement_ID(int var1);

    public int getM_CostElement_ID();

    public I_M_CostElement getM_CostElement() throws RuntimeException;

    public void setM_CostType_ID(int var1);

    public int getM_CostType_ID();

    public I_M_CostType getM_CostType() throws RuntimeException;

    public void setM_Product_ID(int var1);

    public int getM_Product_ID();

    public I_M_Product getM_Product() throws RuntimeException;

    public void setPP_Order_Cost_ID(int var1);

    public int getPP_Order_Cost_ID();

    public void setPP_Order_Cost_UU(String var1);

    public String getPP_Order_Cost_UU();

    public void setPP_Order_ID(int var1);

    public int getPP_Order_ID();

    public I_PP_Order getPP_Order() throws RuntimeException;

    public Timestamp getUpdated();

    public int getUpdatedBy();
}

