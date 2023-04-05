/*
 * Decompiled with CFR 0.150.
 */
package org.adempiere.model.engines;

import java.math.BigDecimal;
import java.util.Properties;

public interface IDocumentLine {
    public Properties getCtx();

    public String get_TrxName();

    public String get_TableName();

    public int get_ID();

    public int getAD_Client_ID();

    public int getAD_Org_ID();

    public int getM_Product_ID();

    public String getDescription();

    public int getM_Locator_ID();

    public void setM_Locator_ID(int var1);

    public int getM_AttributeSetInstance_ID();

    public void setM_AttributeSetInstance_ID(int var1);

    public BigDecimal getMovementQty();
}

