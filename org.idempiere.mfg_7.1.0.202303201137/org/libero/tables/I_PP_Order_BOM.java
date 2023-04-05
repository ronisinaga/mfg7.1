/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_C_UOM
 *  org.compiere.model.I_M_AttributeSetInstance
 *  org.compiere.model.I_M_ChangeNotice
 *  org.compiere.model.I_M_Product
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_ChangeNotice;
import org.compiere.model.I_M_Product;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_PP_Order;

public interface I_PP_Order_BOM {
    public static final String Table_Name = "PP_Order_BOM";
    public static final int Table_ID = 53026;
    public static final KeyNamePair Model = new KeyNamePair(53026, "PP_Order_BOM");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_BOMType = "BOMType";
    public static final String COLUMNNAME_BOMUse = "BOMUse";
    public static final String COLUMNNAME_CopyFrom = "CopyFrom";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";
    public static final String COLUMNNAME_Description = "Description";
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";
    public static final String COLUMNNAME_Help = "Help";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";
    public static final String COLUMNNAME_M_ChangeNotice_ID = "M_ChangeNotice_ID";
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
    public static final String COLUMNNAME_Name = "Name";
    public static final String COLUMNNAME_PP_Order_BOM_ID = "PP_Order_BOM_ID";
    public static final String COLUMNNAME_PP_Order_BOM_UU = "PP_Order_BOM_UU";
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";
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

    public void setBOMType(String var1);

    public String getBOMType();

    public void setBOMUse(String var1);

    public String getBOMUse();

    public void setCopyFrom(String var1);

    public String getCopyFrom();

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setC_UOM_ID(int var1);

    public int getC_UOM_ID();

    public I_C_UOM getC_UOM() throws RuntimeException;

    public void setDescription(String var1);

    public String getDescription();

    public void setDocumentNo(String var1);

    public String getDocumentNo();

    public void setHelp(String var1);

    public String getHelp();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setM_AttributeSetInstance_ID(int var1);

    public int getM_AttributeSetInstance_ID();

    public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

    public void setM_ChangeNotice_ID(int var1);

    public int getM_ChangeNotice_ID();

    public I_M_ChangeNotice getM_ChangeNotice() throws RuntimeException;

    public void setM_Product_ID(int var1);

    public int getM_Product_ID();

    public I_M_Product getM_Product() throws RuntimeException;

    public void setName(String var1);

    public String getName();

    public void setPP_Order_BOM_ID(int var1);

    public int getPP_Order_BOM_ID();

    public void setPP_Order_BOM_UU(String var1);

    public String getPP_Order_BOM_UU();

    public void setPP_Order_ID(int var1);

    public int getPP_Order_ID();

    public I_PP_Order getPP_Order() throws RuntimeException;

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

