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
import org.libero.callouts.CalloutBOM;

public class Callout_PP_Product_BOM
extends CalloutBOM
implements IColumnCallout {
    public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
        if (mField.getColumnName().equals("M_Product_ID")) {
            return this.getdefaults(ctx, WindowNo, mTab, mField, value);
        }
        return null;
    }
}

