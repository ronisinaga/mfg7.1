/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_M_Attribute
 *  org.compiere.util.KeyNamePair
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.KeyNamePair;
import org.libero.tables.I_QM_Specification;

public interface I_QM_SpecificationLine {
    public static final String Table_Name = "QM_SpecificationLine";
    public static final int Table_ID = 53041;
    public static final KeyNamePair Model = new KeyNamePair(53041, "QM_SpecificationLine");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_AndOr = "AndOr";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";
    public static final String COLUMNNAME_Operation = "Operation";
    public static final String COLUMNNAME_QM_Specification_ID = "QM_Specification_ID";
    public static final String COLUMNNAME_QM_SpecificationLine_ID = "QM_SpecificationLine_ID";
    public static final String COLUMNNAME_QM_SpecificationLine_UU = "QM_SpecificationLine_UU";
    public static final String COLUMNNAME_SeqNo = "SeqNo";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";
    public static final String COLUMNNAME_ValidTo = "ValidTo";
    public static final String COLUMNNAME_Value = "Value";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setAndOr(String var1);

    public String getAndOr();

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setM_Attribute_ID(int var1);

    public int getM_Attribute_ID();

    public I_M_Attribute getM_Attribute() throws RuntimeException;

    public void setOperation(String var1);

    public String getOperation();

    public void setQM_Specification_ID(int var1);

    public int getQM_Specification_ID();

    public I_QM_Specification getQM_Specification() throws RuntimeException;

    public void setQM_SpecificationLine_ID(int var1);

    public int getQM_SpecificationLine_ID();

    public void setQM_SpecificationLine_UU(String var1);

    public String getQM_SpecificationLine_UU();

    public void setSeqNo(int var1);

    public int getSeqNo();

    public Timestamp getUpdated();

    public int getUpdatedBy();

    public void setValidFrom(String var1);

    public String getValidFrom();

    public void setValidTo(Timestamp var1);

    public Timestamp getValidTo();

    public void setValue(String var1);

    public String getValue();
}

