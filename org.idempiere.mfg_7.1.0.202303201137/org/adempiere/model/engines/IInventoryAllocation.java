/*
 * Decompiled with CFR 0.150.
 */
package org.adempiere.model.engines;

import java.math.BigDecimal;

public interface IInventoryAllocation {
    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public int getM_AttributeSetInstance_ID();

    public void setM_AttributeSetInstance_ID(int var1);

    public BigDecimal getMovementQty();

    public void setMovementQty(BigDecimal var1);
}

