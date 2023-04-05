/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_PInstance
 *  org.compiere.util.KeyNamePair
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_AD_PInstance;
import org.compiere.util.KeyNamePair;

public interface I_T_MRP_CRP {
    public static final String Table_Name = "T_MRP_CRP";
    public static final int Table_ID = 53044;
    public static final KeyNamePair Model = new KeyNamePair(53044, "T_MRP_CRP");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_Description = "Description";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_SeqNo = "SeqNo";
    public static final String COLUMNNAME_T_MRP_CRP_ID = "T_MRP_CRP_ID";
    public static final String COLUMNNAME_T_MRP_CRP_UU = "T_MRP_CRP_UU";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setAD_PInstance_ID(int var1);

    public int getAD_PInstance_ID();

    public I_AD_PInstance getAD_PInstance() throws RuntimeException;

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setDescription(String var1);

    public String getDescription();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setSeqNo(int var1);

    public int getSeqNo();

    public void setT_MRP_CRP_ID(int var1);

    public int getT_MRP_CRP_ID();

    public void setT_MRP_CRP_UU(String var1);

    public String getT_MRP_CRP_UU();

    public Timestamp getUpdated();

    public int getUpdatedBy();
}

