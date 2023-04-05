/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_WF_Node
 *  org.compiere.model.I_M_Product
 *  org.compiere.util.KeyNamePair
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_M_Product;
import org.compiere.util.KeyNamePair;

public interface I_PP_WF_Node_Product {
    public static final String Table_Name = "PP_WF_Node_Product";
    public static final int Table_ID = 53016;
    public static final KeyNamePair Model = new KeyNamePair(53016, "PP_WF_Node_Product");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";
    public static final String COLUMNNAME_ConfigurationLevel = "ConfigurationLevel";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_EntityType = "EntityType";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_IsSubcontracting = "IsSubcontracting";
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
    public static final String COLUMNNAME_PP_WF_Node_Product_ID = "PP_WF_Node_Product_ID";
    public static final String COLUMNNAME_PP_WF_Node_Product_UU = "PP_WF_Node_Product_UU";
    public static final String COLUMNNAME_Qty = "Qty";
    public static final String COLUMNNAME_SeqNo = "SeqNo";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setAD_WF_Node_ID(int var1);

    public int getAD_WF_Node_ID();

    public I_AD_WF_Node getAD_WF_Node() throws RuntimeException;

    public void setConfigurationLevel(String var1);

    public String getConfigurationLevel();

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setEntityType(String var1);

    public String getEntityType();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setIsSubcontracting(boolean var1);

    public boolean isSubcontracting();

    public void setM_Product_ID(int var1);

    public int getM_Product_ID();

    public I_M_Product getM_Product() throws RuntimeException;

    public void setPP_WF_Node_Product_ID(int var1);

    public int getPP_WF_Node_Product_ID();

    public void setPP_WF_Node_Product_UU(String var1);

    public String getPP_WF_Node_Product_UU();

    public void setQty(BigDecimal var1);

    public BigDecimal getQty();

    public void setSeqNo(int var1);

    public int getSeqNo();

    public Timestamp getUpdated();

    public int getUpdatedBy();
}

