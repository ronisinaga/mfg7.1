/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_M_Shipper
 *  org.compiere.model.I_M_Warehouse
 *  org.compiere.util.KeyNamePair
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.KeyNamePair;
import org.libero.tables.I_DD_NetworkDistribution;

public interface I_DD_NetworkDistributionLine {
    public static final String Table_Name = "DD_NetworkDistributionLine";
    public static final int Table_ID = 53061;
    public static final KeyNamePair Model = new KeyNamePair(53061, "DD_NetworkDistributionLine");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_DD_NetworkDistribution_ID = "DD_NetworkDistribution_ID";
    public static final String COLUMNNAME_DD_NetworkDistributionLine_ID = "DD_NetworkDistributionLine_ID";
    public static final String COLUMNNAME_DD_NetworkDistributionLine_UU = "DD_NetworkDistributionLine_UU";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";
    public static final String COLUMNNAME_M_WarehouseSource_ID = "M_WarehouseSource_ID";
    public static final String COLUMNNAME_Percent = "Percent";
    public static final String COLUMNNAME_PriorityNo = "PriorityNo";
    public static final String COLUMNNAME_TransfertTime = "TransfertTime";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";
    public static final String COLUMNNAME_ValidTo = "ValidTo";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setDD_NetworkDistribution_ID(int var1);

    public int getDD_NetworkDistribution_ID();

    public I_DD_NetworkDistribution getDD_NetworkDistribution() throws RuntimeException;

    public void setDD_NetworkDistributionLine_ID(int var1);

    public int getDD_NetworkDistributionLine_ID();

    public void setDD_NetworkDistributionLine_UU(String var1);

    public String getDD_NetworkDistributionLine_UU();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setM_Shipper_ID(int var1);

    public int getM_Shipper_ID();

    public I_M_Shipper getM_Shipper() throws RuntimeException;

    public void setM_Warehouse_ID(int var1);

    public int getM_Warehouse_ID();

    public I_M_Warehouse getM_Warehouse() throws RuntimeException;

    public void setM_WarehouseSource_ID(int var1);

    public int getM_WarehouseSource_ID();

    public I_M_Warehouse getM_WarehouseSource() throws RuntimeException;

    public void setPercent(BigDecimal var1);

    public BigDecimal getPercent();

    public void setPriorityNo(int var1);

    public int getPriorityNo();

    public void setTransfertTime(BigDecimal var1);

    public BigDecimal getTransfertTime();

    public Timestamp getUpdated();

    public int getUpdatedBy();

    public void setValidFrom(Timestamp var1);

    public Timestamp getValidFrom();

    public void setValidTo(Timestamp var1);

    public Timestamp getValidTo();
}

