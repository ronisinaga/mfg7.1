/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.adempiere.model.GridTabWrapper
 *  org.compiere.model.CalloutEngine
 *  org.compiere.model.GridField
 *  org.compiere.model.GridTab
 *  org.compiere.model.MProduct
 *  org.compiere.model.MUOMConversion
 *  org.compiere.util.Env
 *  org.eevolution.model.I_PP_Product_BOM
 *  org.eevolution.model.I_PP_Product_BOMLine
 */
package org.libero.callouts;

import java.math.BigDecimal;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;
import org.compiere.model.MUOMConversion;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.libero.tables.I_PP_Order_BOMLine;

public class CalloutBOM
extends CalloutEngine {
    public String parent(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
        if (this.isCalloutActive() || value == null) {
            return "";
        }
        int M_Product_ID = (Integer)value;
        if (M_Product_ID <= 0) {
            return "";
        }
        I_PP_Product_BOMLine bomLine = (I_PP_Product_BOMLine)GridTabWrapper.create((GridTab)mTab, I_PP_Product_BOMLine.class);
        I_PP_Product_BOM bom = bomLine.getPP_Product_BOM();
        if (bom.getM_Product_ID() == bomLine.getM_Product_ID()) {
            throw new AdempiereException("@ValidComponent@ - Error Parent not be Component");
        }
        MProduct product = MProduct.get((Properties)ctx, (int)M_Product_ID);
        bomLine.setDescription(product.getDescription());
        bomLine.setHelp(product.getHelp());
        bomLine.setC_UOM_ID(product.getC_UOM_ID());
        return "";
    }

    public String qtyLine(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
        if (this.isCalloutActive() || value == null) {
            return "";
        }
        I_PP_Order_BOMLine bomLine = (I_PP_Order_BOMLine)GridTabWrapper.create((GridTab)mTab, I_PP_Order_BOMLine.class);
        int M_Product_ID = bomLine.getM_Product_ID();
        String columnName = mField.getColumnName();
        if (M_Product_ID <= 0) {
            BigDecimal QtyEntered = bomLine.getQtyEntered();
            bomLine.setQtyRequired(QtyEntered);
        } else if ("C_UOM_ID".equals(columnName) || "QtyEntered".equals(columnName)) {
            BigDecimal QtyEntered = bomLine.getQtyEntered();
            BigDecimal QtyRequired = MUOMConversion.convertProductFrom((Properties)ctx, (int)M_Product_ID, (int)bomLine.getC_UOM_ID(), (BigDecimal)QtyEntered);
            if (QtyRequired == null) {
                QtyRequired = QtyEntered;
            }
            boolean conversion = QtyEntered.compareTo(QtyRequired) != 0;
            Env.setContext((Properties)ctx, (int)WindowNo, (String)"UOMConversion", (boolean)conversion);
            bomLine.setQtyRequired(QtyRequired);
        } else if ("QtyRequired".equals(columnName)) {
            BigDecimal QtyRequired = bomLine.getQtyRequired();
            BigDecimal QtyEntered = MUOMConversion.convertProductTo((Properties)ctx, (int)M_Product_ID, (int)bomLine.getC_UOM_ID(), (BigDecimal)QtyRequired);
            if (QtyEntered == null) {
                QtyEntered = QtyRequired;
            }
            boolean conversion = QtyRequired.compareTo(QtyEntered) != 0;
            Env.setContext((Properties)ctx, (int)WindowNo, (String)"UOMConversion", (boolean)conversion);
            bomLine.setQtyEntered(QtyEntered);
        }
        return "";
    }

    public String getdefaults(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
        if (this.isCalloutActive() || value == null) {
            return "";
        }
        int M_Product_ID = (Integer)value;
        if (M_Product_ID <= 0) {
            return "";
        }
        MProduct product = MProduct.get((Properties)ctx, (int)M_Product_ID);
        I_PP_Product_BOM bom = (I_PP_Product_BOM)GridTabWrapper.create((GridTab)mTab, I_PP_Product_BOM.class);
        bom.setValue(product.getValue());
        bom.setName(product.getName());
        bom.setDescription(product.getDescription());
        bom.setHelp(product.getHelp());
        bom.setC_UOM_ID(product.getC_UOM_ID());
        return "";
    }
}

