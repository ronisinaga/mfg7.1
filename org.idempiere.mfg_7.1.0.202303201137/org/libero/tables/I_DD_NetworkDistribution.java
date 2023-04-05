/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_M_ChangeNotice
 *  org.compiere.util.KeyNamePair
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_M_ChangeNotice;
import org.compiere.util.KeyNamePair;

public interface I_DD_NetworkDistribution {
    public static final String Table_Name = "DD_NetworkDistribution";
    public static final int Table_ID = 53060;
    public static final KeyNamePair Model = new KeyNamePair(53060, "DD_NetworkDistribution");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_CopyFrom = "CopyFrom";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_DD_NetworkDistribution_ID = "DD_NetworkDistribution_ID";
    public static final String COLUMNNAME_DD_NetworkDistribution_UU = "DD_NetworkDistribution_UU";
    public static final String COLUMNNAME_Description = "Description";
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";
    public static final String COLUMNNAME_Help = "Help";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_M_ChangeNotice_ID = "M_ChangeNotice_ID";
    public static final String COLUMNNAME_Name = "Name";
    public static final String COLUMNNAME_Processing = "Processing";
    public static final String COLUMNNAME_Revision = "Revision";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";
    public static final String COLUMNNAME_ValidTo = "ValidTo";
    public static final String COLUMNNAME_Value = "Value";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setCopyFrom(String var1);

    public String getCopyFrom();

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setDD_NetworkDistribution_ID(int var1);

    public int getDD_NetworkDistribution_ID();

    public void setDD_NetworkDistribution_UU(String var1);

    public String getDD_NetworkDistribution_UU();

    public void setDescription(String var1);

    public String getDescription();

    public void setDocumentNo(String var1);

    public String getDocumentNo();

    public void setHelp(String var1);

    public String getHelp();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setM_ChangeNotice_ID(int var1);

    public int getM_ChangeNotice_ID();

    public I_M_ChangeNotice getM_ChangeNotice() throws RuntimeException;

    public void setName(String var1);

    public String getName();

    public void setProcessing(boolean var1);

    public boolean isProcessing();

    public void setRevision(String var1);

    public String getRevision();

    public Timestamp getUpdated();

    public int getUpdatedBy();

    public void setValidFrom(Timestamp var1);

    public Timestamp getValidFrom();

    public void setValidTo(Timestamp var1);

    public Timestamp getValidTo();

    public void setValue(String var1);

    public String getValue();
}

