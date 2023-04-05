/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.base.IColumnCallout
 *  org.compiere.model.GridField
 *  org.compiere.model.GridTab
 */
package org.libero.callouts;

import java.util.Properties;
import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.libero.callouts.CalloutDistributionOrder;

public class Callout_DD_OrderLine
extends CalloutDistributionOrder
implements IColumnCallout {
    public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
        if (mField.getColumnName().equals("QtyEntered")) {
            return this.qty(ctx, WindowNo, mTab, mField, value);
        }
        if (mField.getColumnName().equals("M_Product_ID")) {
            return this.setLocatorTo(ctx, WindowNo, mTab, mField, value);
        }
        if (mField.getColumnName().equals("ConfirmedQty")) {
            return this.qtyConfirmed(ctx, WindowNo, mTab, mField, value);
        }
        if (mField.getColumnName().equals("C_UOM_ID")) {
            return this.qty(ctx, WindowNo, mTab, mField, value);
        }
        if (mField.getColumnName().equals("M_AtttributeSetInstanceTo_ID")) {
            return this.qtyConfirmed(ctx, WindowNo, mTab, mField, value);
        }
        if (mField.getColumnName().equals("M_AtttributeSetInstance_ID")) {
            return this.qty(ctx, WindowNo, mTab, mField, value);
        }
        if (mField.getColumnName().equals("QtyOrdered")) {
            return this.qty(ctx, WindowNo, mTab, mField, value);
        }
        if (mField.getColumnName().equals("AD_Org_ID")) {
            return this.bPartner(ctx, WindowNo, mTab, mField, value);
        }
        return null;
    }
}

