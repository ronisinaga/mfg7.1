/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_Workflow
 *  org.compiere.model.I_M_AttributeSet
 *  org.compiere.model.I_M_Product
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_PP_Product_BOM
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_Product;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_PP_Product_BOM;

public interface I_QM_Specification {
    public static final String Table_Name = "QM_Specification";
    public static final int Table_ID = 53040;
    public static final KeyNamePair Model = new KeyNamePair(53040, "QM_Specification");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_Description = "Description";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_M_AttributeSet_ID = "M_AttributeSet_ID";
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
    public static final String COLUMNNAME_Name = "Name";
    public static final String COLUMNNAME_PP_Product_BOM_ID = "PP_Product_BOM_ID";
    public static final String COLUMNNAME_QM_Specification_ID = "QM_Specification_ID";
    public static final String COLUMNNAME_QM_Specification_UU = "QM_Specification_UU";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";
    public static final String COLUMNNAME_ValidTo = "ValidTo";
    public static final String COLUMNNAME_Value = "Value";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setAD_Workflow_ID(int var1);

    public int getAD_Workflow_ID();

    public I_AD_Workflow getAD_Workflow() throws RuntimeException;

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setDescription(String var1);

    public String getDescription();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setM_AttributeSet_ID(int var1);

    public int getM_AttributeSet_ID();

    public I_M_AttributeSet getM_AttributeSet() throws RuntimeException;

    public void setM_Product_ID(int var1);

    public int getM_Product_ID();

    public I_M_Product getM_Product() throws RuntimeException;

    public void setName(String var1);

    public String getName();

    public void setPP_Product_BOM_ID(int var1);

    public int getPP_Product_BOM_ID();

    public I_PP_Product_BOM getPP_Product_BOM() throws RuntimeException;

    public void setQM_Specification_ID(int var1);

    public int getQM_Specification_ID();

    public void setQM_Specification_UU(String var1);

    public String getQM_Specification_UU();

    public Timestamp getUpdated();

    public int getUpdatedBy();

    public void setValidFrom(Timestamp var1);

    public Timestamp getValidFrom();

    public void setValidTo(Timestamp var1);

    public Timestamp getValidTo();

    public void setValue(String var1);

    public String getValue();
}

