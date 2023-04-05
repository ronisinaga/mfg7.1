/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_M_AttributeSetInstance
 *  org.compiere.util.KeyNamePair
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.KeyNamePair;
import org.libero.tables.I_PP_Cost_Collector;

public interface I_PP_Cost_CollectorMA {
    public static final String Table_Name = "PP_Cost_CollectorMA";
    public static final int Table_ID = 53062;
    public static final KeyNamePair Model = new KeyNamePair(53062, "PP_Cost_CollectorMA");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";
    public static final String COLUMNNAME_MovementQty = "MovementQty";
    public static final String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";
    public static final String COLUMNNAME_PP_Cost_CollectorMA_ID = "PP_Cost_CollectorMA_ID";
    public static final String COLUMNNAME_PP_Cost_CollectorMA_UU = "PP_Cost_CollectorMA_UU";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setM_AttributeSetInstance_ID(int var1);

    public int getM_AttributeSetInstance_ID();

    public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

    public void setMovementQty(BigDecimal var1);

    public BigDecimal getMovementQty();

    public void setPP_Cost_Collector_ID(int var1);

    public int getPP_Cost_Collector_ID();

    public I_PP_Cost_Collector getPP_Cost_Collector() throws RuntimeException;

    public void setPP_Cost_CollectorMA_ID(int var1);

    public int getPP_Cost_CollectorMA_ID();

    public void setPP_Cost_CollectorMA_UU(String var1);

    public String getPP_Cost_CollectorMA_UU();

    public Timestamp getUpdated();

    public int getUpdatedBy();
}

