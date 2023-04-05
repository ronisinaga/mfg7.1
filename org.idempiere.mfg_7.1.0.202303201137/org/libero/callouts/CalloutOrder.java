/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.model.GridTabWrapper
 *  org.compiere.model.CalloutEngine
 *  org.compiere.model.GridField
 *  org.compiere.model.GridTab
 *  org.compiere.model.MProduct
 *  org.compiere.model.MUOMConversion
 *  org.compiere.util.Env
 *  org.compiere.wf.MWorkflow
 *  org.eevolution.model.I_PP_Order
 *  org.eevolution.model.I_PP_Product_BOM
 *  org.eevolution.model.I_PP_Product_Planning
 *  org.eevolution.model.MPPProductBOM
 *  org.eevolution.model.MPPProductPlanning
 */
package org.libero.callouts;

import java.math.BigDecimal;
import java.util.Properties;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;
import org.compiere.model.MUOMConversion;
import org.compiere.util.Env;
import org.compiere.wf.MWorkflow;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductPlanning;
import org.libero.model.MPPOrder;

public class CalloutOrder
extends CalloutEngine {
    private boolean steps = false;

    public String qty(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
        if (value == null) {
            return "";
        }
        int M_Product_ID = Env.getContextAsInt((Properties)ctx, (int)WindowNo, (String)"M_Product_ID");
        if (this.steps) {
            this.log.warning("qty - init - M_Product_ID=" + M_Product_ID + " - ");
        }
        BigDecimal QtyOrdered = Env.ZERO;
        BigDecimal QtyEntered = Env.ZERO;
        if (M_Product_ID == 0) {
            QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
            mTab.setValue("QtyOrdered", (Object)QtyEntered);
        } else if (mField.getColumnName().equals("C_UOM_ID")) {
            int C_UOM_To_ID = (Integer)value;
            QtyOrdered = MUOMConversion.convertProductFrom((Properties)ctx, (int)M_Product_ID, (int)C_UOM_To_ID, (BigDecimal)(QtyEntered = (BigDecimal)mTab.getValue("QtyEntered")));
            if (QtyOrdered == null) {
                QtyOrdered = QtyEntered;
            }
            boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
            Env.setContext((Properties)ctx, (int)WindowNo, (String)"UOMConversion", (String)(conversion ? "Y" : "N"));
            mTab.setValue("QtyOrdered", (Object)QtyOrdered);
        } else if (mField.getColumnName().equals("QtyEntered")) {
            int C_UOM_To_ID = Env.getContextAsInt((Properties)ctx, (int)WindowNo, (String)"C_UOM_ID");
            QtyOrdered = MUOMConversion.convertProductFrom((Properties)ctx, (int)M_Product_ID, (int)C_UOM_To_ID, (BigDecimal)(QtyEntered = (BigDecimal)value));
            if (QtyOrdered == null) {
                QtyOrdered = QtyEntered;
            }
            boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
            this.log.fine("qty - UOM=" + C_UOM_To_ID + ", QtyEntered=" + QtyEntered + " -> " + conversion + " QtyOrdered=" + QtyOrdered);
            Env.setContext((Properties)ctx, (int)WindowNo, (String)"UOMConversion", (String)(conversion ? "Y" : "N"));
            mTab.setValue("QtyOrdered", (Object)QtyOrdered);
        } else if (mField.getColumnName().equals("QtyOrdered")) {
            int C_UOM_To_ID = Env.getContextAsInt((Properties)ctx, (int)WindowNo, (String)"C_UOM_ID");
            QtyEntered = MUOMConversion.convertProductTo((Properties)ctx, (int)M_Product_ID, (int)C_UOM_To_ID, (BigDecimal)(QtyOrdered = (BigDecimal)value));
            if (QtyEntered == null) {
                QtyEntered = QtyOrdered;
            }
            boolean conversion = QtyOrdered.compareTo(QtyEntered) != 0;
            this.log.fine("qty - UOM=" + C_UOM_To_ID + ", QtyOrdered=" + QtyOrdered + " -> " + conversion + " QtyEntered=" + QtyEntered);
            Env.setContext((Properties)ctx, (int)WindowNo, (String)"UOMConversion", (String)(conversion ? "Y" : "N"));
            mTab.setValue("QtyEntered", (Object)QtyEntered);
        }
        return this.qtyBatch(ctx, WindowNo, mTab, mField, value);
    }

    public String qtyBatch(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
        I_PP_Order order = (I_PP_Order)GridTabWrapper.create((GridTab)mTab, I_PP_Order.class);
        MPPOrder.updateQtyBatchs(ctx, order, true);
        return "";
    }

    public String product(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
        if (this.isCalloutActive()) {
            return "";
        }
        I_PP_Order order = (I_PP_Order)GridTabWrapper.create((GridTab)mTab, I_PP_Order.class);
        MProduct product = MProduct.get((Properties)ctx, (int)order.getM_Product_ID());
        if (product == null) {
            return "";
        }
        order.setC_UOM_ID(product.getC_UOM_ID());
        I_PP_Product_Planning pp = CalloutOrder.getPP_Product_Planning(ctx, order);
        order.setAD_Workflow_ID(pp.getAD_Workflow_ID());
        order.setPP_Product_BOM_ID(pp.getPP_Product_BOM_ID());
        if (pp.getPP_Product_BOM_ID() > 0) {
            I_PP_Product_BOM bom = pp.getPP_Product_BOM();
            order.setC_UOM_ID(bom.getC_UOM_ID());
        }
        MPPOrder.updateQtyBatchs(ctx, order, true);
        return "";
    }

    protected static I_PP_Product_Planning getPP_Product_Planning(Properties ctx, I_PP_Order order) {
        MPPProductBOM bom;
        MPPProductPlanning pp = MPPProductPlanning.find((Properties)ctx, (int)order.getAD_Org_ID(), (int)order.getM_Warehouse_ID(), (int)order.getS_Resource_ID(), (int)order.getM_Product_ID(), null);
        if (pp == null) {
            pp = new MPPProductPlanning(ctx, 0, null);
            pp.setAD_Org_ID(order.getAD_Org_ID());
            pp.setM_Warehouse_ID(order.getM_Warehouse_ID());
            pp.setS_Resource_ID(order.getS_Resource_ID());
            pp.setM_Product_ID(order.getM_Product_ID());
        }
        MProduct product = MProduct.get((Properties)ctx, (int)pp.getM_Product_ID());
        if (pp.getAD_Workflow_ID() <= 0) {
            pp.setAD_Workflow_ID(MWorkflow.getWorkflowSearchKey((MProduct)product));
        }
        if (pp.getPP_Product_BOM_ID() <= 0 && (bom = MPPProductBOM.getDefault((MProduct)product, null)) != null) {
            pp.setPP_Product_BOM_ID(bom.getPP_Product_BOM_ID());
        }
        return pp;
    }
}

