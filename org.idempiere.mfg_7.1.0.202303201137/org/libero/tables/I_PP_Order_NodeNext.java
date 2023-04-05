/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_WF_Node
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_Order_Node;

public interface I_PP_Order_NodeNext {
    public static final String Table_Name = "PP_Order_NodeNext";
    public static final int Table_ID = 53023;
    public static final KeyNamePair Model = new KeyNamePair(53023, "PP_Order_NodeNext");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_AD_WF_Next_ID = "AD_WF_Next_ID";
    public static final String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_Description = "Description";
    public static final String COLUMNNAME_EntityType = "EntityType";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_IsStdUserWorkflow = "IsStdUserWorkflow";
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";
    public static final String COLUMNNAME_PP_Order_Next_ID = "PP_Order_Next_ID";
    public static final String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";
    public static final String COLUMNNAME_PP_Order_NodeNext_ID = "PP_Order_NodeNext_ID";
    public static final String COLUMNNAME_PP_Order_NodeNext_UU = "PP_Order_NodeNext_UU";
    public static final String COLUMNNAME_SeqNo = "SeqNo";
    public static final String COLUMNNAME_TransitionCode = "TransitionCode";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setAD_WF_Next_ID(int var1);

    public int getAD_WF_Next_ID();

    public I_AD_WF_Node getAD_WF_Next() throws RuntimeException;

    public void setAD_WF_Node_ID(int var1);

    public int getAD_WF_Node_ID();

    public I_AD_WF_Node getAD_WF_Node() throws RuntimeException;

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setDescription(String var1);

    public String getDescription();

    public void setEntityType(String var1);

    public String getEntityType();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setIsStdUserWorkflow(boolean var1);

    public boolean isStdUserWorkflow();

    public void setPP_Order_ID(int var1);

    public int getPP_Order_ID();

    public I_PP_Order getPP_Order() throws RuntimeException;

    public void setPP_Order_Next_ID(int var1);

    public int getPP_Order_Next_ID();

    public I_PP_Order_Node getPP_Order_Next() throws RuntimeException;

    public void setPP_Order_Node_ID(int var1);

    public int getPP_Order_Node_ID();

    public I_PP_Order_Node getPP_Order_Node() throws RuntimeException;

    public void setPP_Order_NodeNext_ID(int var1);

    public int getPP_Order_NodeNext_ID();

    public void setPP_Order_NodeNext_UU(String var1);

    public String getPP_Order_NodeNext_UU();

    public void setSeqNo(int var1);

    public int getSeqNo();

    public void setTransitionCode(String var1);

    public String getTransitionCode();

    public Timestamp getUpdated();

    public int getUpdatedBy();
}

