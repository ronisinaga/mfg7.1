/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_User
 *  org.compiere.model.I_C_UOM
 *  org.compiere.model.I_M_AttributeSetInstance
 *  org.compiere.model.I_M_ChangeNotice
 *  org.compiere.model.I_M_Locator
 *  org.compiere.model.I_M_Product
 *  org.compiere.model.I_M_Warehouse
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_ChangeNotice;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_Order_BOM;

public interface I_PP_Order_BOMLine {
    public static final String Table_Name = "PP_Order_BOMLine";
    public static final int Table_ID = 53025;
    public static final KeyNamePair Model = new KeyNamePair(53025, "PP_Order_BOMLine");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";
    public static final String COLUMNNAME_Assay = "Assay";
    public static final String COLUMNNAME_BackflushGroup = "BackflushGroup";
    public static final String COLUMNNAME_ComponentType = "ComponentType";
    public static final String COLUMNNAME_CostAllocationPerc = "CostAllocationPerc";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";
    public static final String COLUMNNAME_DateDelivered = "DateDelivered";
    public static final String COLUMNNAME_Description = "Description";
    public static final String COLUMNNAME_Feature = "Feature";
    public static final String COLUMNNAME_Forecast = "Forecast";
    public static final String COLUMNNAME_Help = "Help";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_IsCritical = "IsCritical";
    public static final String COLUMNNAME_IsQtyPercentage = "IsQtyPercentage";
    public static final String COLUMNNAME_IssueMethod = "IssueMethod";
    public static final String COLUMNNAME_LeadTimeOffset = "LeadTimeOffset";
    public static final String COLUMNNAME_Line = "Line";
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";
    public static final String COLUMNNAME_M_ChangeNotice_ID = "M_ChangeNotice_ID";
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";
    public static final String COLUMNNAME_PP_Order_BOM_ID = "PP_Order_BOM_ID";
    public static final String COLUMNNAME_PP_Order_BOMLine_ID = "PP_Order_BOMLine_ID";
    public static final String COLUMNNAME_PP_Order_BOMLine_UU = "PP_Order_BOMLine_UU";
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";
    public static final String COLUMNNAME_QtyBatch = "QtyBatch";
    public static final String COLUMNNAME_QtyBOM = "QtyBOM";
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";
    public static final String COLUMNNAME_QtyEntered = "QtyEntered";
    public static final String COLUMNNAME_QtyPost = "QtyPost";
    public static final String COLUMNNAME_QtyReject = "QtyReject";
    public static final String COLUMNNAME_QtyRequired = "QtyRequired";
    public static final String COLUMNNAME_QtyReserved = "QtyReserved";
    public static final String COLUMNNAME_QtyScrap = "QtyScrap";
    public static final String COLUMNNAME_Scrap = "Scrap";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";
    public static final String COLUMNNAME_ValidTo = "ValidTo";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setAD_User_ID(int var1);

    public int getAD_User_ID();

    public I_AD_User getAD_User() throws RuntimeException;

    public void setAssay(BigDecimal var1);

    public BigDecimal getAssay();

    public void setBackflushGroup(String var1);

    public String getBackflushGroup();

    public void setComponentType(String var1);

    public String getComponentType();

    public void setCostAllocationPerc(BigDecimal var1);

    public BigDecimal getCostAllocationPerc();

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setC_UOM_ID(int var1);

    public int getC_UOM_ID();

    public I_C_UOM getC_UOM() throws RuntimeException;

    public void setDateDelivered(Timestamp var1);

    public Timestamp getDateDelivered();

    public void setDescription(String var1);

    public String getDescription();

    public void setFeature(String var1);

    public String getFeature();

    public void setForecast(BigDecimal var1);

    public BigDecimal getForecast();

    public void setHelp(String var1);

    public String getHelp();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setIsCritical(boolean var1);

    public boolean isCritical();

    public void setIsQtyPercentage(boolean var1);

    public boolean isQtyPercentage();

    public void setIssueMethod(String var1);

    public String getIssueMethod();

    public void setLeadTimeOffset(int var1);

    public int getLeadTimeOffset();

    public void setLine(int var1);

    public int getLine();

    public void setM_AttributeSetInstance_ID(int var1);

    public int getM_AttributeSetInstance_ID();

    public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

    public void setM_ChangeNotice_ID(int var1);

    public int getM_ChangeNotice_ID();

    public I_M_ChangeNotice getM_ChangeNotice() throws RuntimeException;

    public void setM_Locator_ID(int var1);

    public int getM_Locator_ID();

    public I_M_Locator getM_Locator() throws RuntimeException;

    public void setM_Product_ID(int var1);

    public int getM_Product_ID();

    public I_M_Product getM_Product() throws RuntimeException;

    public void setM_Warehouse_ID(int var1);

    public int getM_Warehouse_ID();

    public I_M_Warehouse getM_Warehouse() throws RuntimeException;

    public void setPP_Order_BOM_ID(int var1);

    public int getPP_Order_BOM_ID();

    public I_PP_Order_BOM getPP_Order_BOM() throws RuntimeException;

    public void setPP_Order_BOMLine_ID(int var1);

    public int getPP_Order_BOMLine_ID();

    public void setPP_Order_BOMLine_UU(String var1);

    public String getPP_Order_BOMLine_UU();

    public void setPP_Order_ID(int var1);

    public int getPP_Order_ID();

    public I_PP_Order getPP_Order() throws RuntimeException;

    public void setQtyBatch(BigDecimal var1);

    public BigDecimal getQtyBatch();

    public void setQtyBOM(BigDecimal var1);

    public BigDecimal getQtyBOM();

    public void setQtyDelivered(BigDecimal var1);

    public BigDecimal getQtyDelivered();

    public void setQtyEntered(BigDecimal var1);

    public BigDecimal getQtyEntered();

    public void setQtyPost(BigDecimal var1);

    public BigDecimal getQtyPost();

    public void setQtyReject(BigDecimal var1);

    public BigDecimal getQtyReject();

    public void setQtyRequired(BigDecimal var1);

    public BigDecimal getQtyRequired();

    public void setQtyReserved(BigDecimal var1);

    public BigDecimal getQtyReserved();

    public void setQtyScrap(BigDecimal var1);

    public BigDecimal getQtyScrap();

    public void setScrap(BigDecimal var1);

    public BigDecimal getScrap();

    public Timestamp getUpdated();

    public int getUpdatedBy();

    public void setValidFrom(Timestamp var1);

    public Timestamp getValidFrom();

    public void setValidTo(Timestamp var1);

    public Timestamp getValidTo();
}

