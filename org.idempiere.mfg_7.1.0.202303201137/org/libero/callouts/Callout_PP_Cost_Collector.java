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
import org.libero.callouts.CalloutCostCollector;

public class Callout_PP_Cost_Collector
extends CalloutCostCollector
implements IColumnCallout {
    public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
        if (mField.getColumnName().equals("PP_Order_ID")) {
            return this.order(ctx, WindowNo, mTab, mField, value);
        }
        if (mField.getColumnName().equals("PP_Order_Node_ID")) {
            return this.node(ctx, WindowNo, mTab, mField, value);
        }
        if (mField.getColumnName().equals("MovementQty")) {
            return this.duration(ctx, WindowNo, mTab, mField, value);
        }
        return null;
    }
}

