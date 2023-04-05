/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_A_Asset
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_A_Asset;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_Order_Node;
import org.libero.tables.I_PP_Order_Workflow;

public interface I_PP_Order_Node_Asset {
    public static final String Table_Name = "PP_Order_Node_Asset";
    public static final int Table_ID = 53031;
    public static final KeyNamePair Model = new KeyNamePair(53031, "PP_Order_Node_Asset");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_A_Asset_ID = "A_Asset_ID";
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";
    public static final String COLUMNNAME_PP_Order_Node_Asset_ID = "PP_Order_Node_Asset_ID";
    public static final String COLUMNNAME_PP_Order_Node_Asset_UU = "PP_Order_Node_Asset_UU";
    public static final String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";
    public static final String COLUMNNAME_PP_Order_Workflow_ID = "PP_Order_Workflow_ID";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

    public void setA_Asset_ID(int var1);

    public int getA_Asset_ID();

    public I_A_Asset getA_Asset() throws RuntimeException;

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setPP_Order_ID(int var1);

    public int getPP_Order_ID();

    public I_PP_Order getPP_Order() throws RuntimeException;

    public void setPP_Order_Node_Asset_ID(int var1);

    public int getPP_Order_Node_Asset_ID();

    public void setPP_Order_Node_Asset_UU(String var1);

    public String getPP_Order_Node_Asset_UU();

    public void setPP_Order_Node_ID(int var1);

    public int getPP_Order_Node_ID();

    public I_PP_Order_Node getPP_Order_Node() throws RuntimeException;

    public void setPP_Order_Workflow_ID(int var1);

    public int getPP_Order_Workflow_ID();

    public I_PP_Order_Workflow getPP_Order_Workflow() throws RuntimeException;

    public Timestamp getUpdated();

    public int getUpdatedBy();
}

