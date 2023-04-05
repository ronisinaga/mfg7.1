/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.apps.form.TreeMaintenance
 *  org.compiere.model.MProduct
 *  org.compiere.model.MUOM
 *  org.compiere.util.CLogger
 *  org.compiere.util.Env
 *  org.eevolution.model.MPPProductBOM
 */
package org.libero.form;

import java.util.Properties;
import org.compiere.apps.form.TreeMaintenance;
import org.compiere.model.MProduct;
import org.compiere.model.MUOM;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.eevolution.model.MPPProductBOM;

public class TreeBOM {
    public static CLogger log = CLogger.getCLogger(TreeMaintenance.class);

    public Properties getCtx() {
        return Env.getCtx();
    }

    public String productSummary(MProduct product, boolean isLeaf) {
        MUOM uom = MUOM.get((Properties)this.getCtx(), (int)product.getC_UOM_ID());
        String value = product.getValue();
        String name = product.get_Translation("Name");
        StringBuffer sb = new StringBuffer(value);
        if (name != null && !value.equals(name)) {
            sb.append("_").append(product.getName());
        }
        sb.append(" [").append(uom.get_Translation("UOMSymbol")).append("]");
        return sb.toString();
    }

    public String productSummary(MPPProductBOM bom) {
        String value = bom.getValue();
        String name = bom.get_Translation("Name");
        StringBuffer sb = new StringBuffer(value);
        if (name != null && !name.equals(value)) {
            sb.append("_").append(name);
        }
        return sb.toString();
    }
}

