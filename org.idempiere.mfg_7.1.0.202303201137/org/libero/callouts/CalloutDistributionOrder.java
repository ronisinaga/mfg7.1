/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.model.GridTabWrapper
 *  org.compiere.model.CalloutEngine
 *  org.compiere.model.GridField
 *  org.compiere.model.GridTab
 *  org.compiere.model.I_M_Movement
 *  org.compiere.model.MBPartnerLocation
 *  org.compiere.model.MLocator
 *  org.compiere.model.MOrg
 *  org.compiere.model.MProduct
 *  org.compiere.model.MStorageReservation
 *  org.compiere.model.MUOM
 *  org.compiere.model.MUOMConversion
 *  org.compiere.model.MWarehouse
 *  org.compiere.util.Env
 *  org.compiere.util.Msg
 *  org.eevolution.model.MDDOrderLine
 */
package org.libero.callouts;

import java.math.BigDecimal;
import java.util.Properties;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_M_Movement;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MLocator;
import org.compiere.model.MOrg;
import org.compiere.model.MProduct;
import org.compiere.model.MStorageReservation;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.model.MWarehouse;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.eevolution.model.MDDOrderLine;
import org.libero.tables.I_DD_OrderLine;

public class CalloutDistributionOrder
extends CalloutEngine {
    private boolean steps = false;

    public String qty(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
        MProduct product;
        int C_UOM_To_ID;
        if (this.isCalloutActive() || value == null) {
            return "";
        }
        int M_Product_ID = Env.getContextAsInt((Properties)ctx, (int)WindowNo, (String)"M_Product_ID");
        if (this.steps) {
            this.log.warning("init - M_Product_ID=" + M_Product_ID + " - ");
        }
        BigDecimal QtyOrdered = Env.ZERO;
        if (M_Product_ID == 0) {
            return "";
        }
        if (mField.getColumnName().equals("C_UOM_ID")) {
            BigDecimal QtyEntered1;
            C_UOM_To_ID = (Integer)value;
            BigDecimal QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
            if (QtyEntered.compareTo(QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision((Properties)ctx, (int)C_UOM_To_ID), 4)) != 0) {
                this.log.fine("Corrected QtyEntered Scale UOM=" + C_UOM_To_ID + "; QtyEntered=" + QtyEntered + "->" + QtyEntered1);
                QtyEntered = QtyEntered1;
                mTab.setValue("QtyEntered", (Object)QtyEntered);
            }
            if ((QtyOrdered = MUOMConversion.convertProductFrom((Properties)ctx, (int)M_Product_ID, (int)C_UOM_To_ID, (BigDecimal)QtyEntered)) == null) {
                QtyOrdered = QtyEntered;
            }
            boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
            Env.setContext((Properties)ctx, (int)WindowNo, (String)"UOMConversion", (String)(conversion ? "Y" : "N"));
            mTab.setValue("QtyOrdered", (Object)QtyOrdered);
        } else if (mField.getColumnName().equals("QtyEntered")) {
            BigDecimal QtyEntered = (BigDecimal)value;
            C_UOM_To_ID = Env.getContextAsInt((Properties)ctx, (int)WindowNo, (String)"C_UOM_ID");
            BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision((Properties)ctx, (int)C_UOM_To_ID), 4);
            if (QtyEntered.compareTo(QtyEntered1) != 0) {
                this.log.fine("Corrected QtyEntered Scale UOM=" + C_UOM_To_ID + "; QtyEntered=" + QtyEntered + "->" + QtyEntered1);
                QtyEntered = QtyEntered1;
                mTab.setValue("QtyEntered", (Object)QtyEntered);
            }
            if ((QtyOrdered = MUOMConversion.convertProductFrom((Properties)ctx, (int)M_Product_ID, (int)C_UOM_To_ID, (BigDecimal)QtyEntered)) == null) {
                QtyOrdered = QtyEntered;
            }
            boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
            this.log.fine("UOM=" + C_UOM_To_ID + ", QtyEntered=" + QtyEntered + " -> " + conversion + " QtyOrdered=" + QtyOrdered);
            Env.setContext((Properties)ctx, (int)WindowNo, (String)"UOMConversion", (String)(conversion ? "Y" : "N"));
            mTab.setValue("QtyOrdered", (Object)QtyOrdered);
        } else if (mField.getColumnName().equals("QtyOrdered")) {
            BigDecimal QtyEntered;
            C_UOM_To_ID = Env.getContextAsInt((Properties)ctx, (int)WindowNo, (String)"C_UOM_ID");
            QtyOrdered = (BigDecimal)value;
            int precision = MProduct.get((Properties)ctx, (int)M_Product_ID).getUOMPrecision();
            BigDecimal QtyOrdered1 = QtyOrdered.setScale(precision, 4);
            if (QtyOrdered.compareTo(QtyOrdered1) != 0) {
                this.log.fine("Corrected QtyOrdered Scale " + QtyOrdered + "->" + QtyOrdered1);
                QtyOrdered = QtyOrdered1;
                mTab.setValue("QtyOrdered", (Object)QtyOrdered);
            }
            if ((QtyEntered = MUOMConversion.convertProductTo((Properties)ctx, (int)M_Product_ID, (int)C_UOM_To_ID, (BigDecimal)QtyOrdered)) == null) {
                QtyEntered = QtyOrdered;
            }
            boolean conversion = QtyOrdered.compareTo(QtyEntered) != 0;
            this.log.fine("UOM=" + C_UOM_To_ID + ", QtyOrdered=" + QtyOrdered + " -> " + conversion + " QtyEntered=" + QtyEntered);
            Env.setContext((Properties)ctx, (int)WindowNo, (String)"UOMConversion", (String)(conversion ? "Y" : "N"));
            mTab.setValue("QtyEntered", (Object)QtyEntered);
        } else {
            QtyOrdered = (BigDecimal)mTab.getValue("QtyOrdered");
        }
        if (M_Product_ID != 0 && Env.isSOTrx((Properties)ctx, (int)WindowNo) && QtyOrdered.signum() > 0 && (product = MProduct.get((Properties)ctx, (int)M_Product_ID)).isStocked()) {
            int M_Locator_ID = Env.getContextAsInt((Properties)ctx, (int)WindowNo, (String)"M_Locator_ID");
            int M_AttributeSetInstance_ID = Env.getContextAsInt((Properties)ctx, (int)WindowNo, (String)"M_AttributeSetInstance_ID");
            int M_Warehouse_ID = MLocator.get((Properties)ctx, (int)M_Locator_ID).getM_Warehouse_ID();
            BigDecimal available = MStorageReservation.getQtyAvailable((int)M_Warehouse_ID, (int)M_Product_ID, (int)M_AttributeSetInstance_ID, null);
            if (available == null) {
                available = Env.ZERO;
            }
            if (available.signum() == 0) {
                mTab.fireDataStatusEEvent("NoQtyAvailable", "0", false);
            } else if (available.compareTo(QtyOrdered) < 0) {
                mTab.fireDataStatusEEvent("InsufficientQtyAvailable", available.toString(), false);
            } else {
                BigDecimal total;
                BigDecimal notReserved;
                Integer DD_OrderLine_ID = (Integer)mTab.getValue("DD_OrderLine_ID");
                if (DD_OrderLine_ID == null) {
                    DD_OrderLine_ID = new Integer(0);
                }
                if ((notReserved = MDDOrderLine.getNotReserved((Properties)ctx, (int)M_Locator_ID, (int)M_Product_ID, (int)M_AttributeSetInstance_ID, (int)DD_OrderLine_ID)) == null) {
                    notReserved = Env.ZERO;
                }
                if ((total = available.subtract(notReserved)).compareTo(QtyOrdered) < 0) {
                    String info = Msg.parseTranslation((Properties)ctx, (String)("@QtyAvailable@=" + available + "  -  @QtyNotReserved@=" + notReserved + "  =  " + total));
                    mTab.fireDataStatusEEvent("InsufficientQtyAvailable", info, false);
                }
            }
        }
        return "";
    }

    public String qtyConfirmed(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
        I_DD_OrderLine line = (I_DD_OrderLine)GridTabWrapper.create((GridTab)mTab, I_DD_OrderLine.class);
        if (line.getConfirmedQty().compareTo(line.getQtyOrdered().subtract(line.getQtyInTransit()).subtract(line.getQtyDelivered())) > 0) {
            String info = Msg.parseTranslation((Properties)ctx, (String)("@ConfirmedQty@ : " + line.getConfirmedQty() + " > @QtyToDeliver@ : " + line.getQtyOrdered().subtract(line.getQtyInTransit()).subtract(line.getQtyDelivered())));
            mTab.fireDataStatusEEvent("", info, false);
            line.setConfirmedQty(line.getQtyOrdered().subtract(line.getQtyInTransit()).subtract(line.getQtyDelivered()));
        }
        return "";
    }

    public String setLocatorTo(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
        MWarehouse[] ws;
        I_DD_OrderLine line = (I_DD_OrderLine)GridTabWrapper.create((GridTab)mTab, I_DD_OrderLine.class);
        if (value != null) {
            MProduct product = MProduct.get((Properties)ctx, (int)((Integer)value));
            if (line.getC_UOM_ID() <= 0) {
                line.setC_UOM_ID(product.getC_UOM_ID());
            }
        }
        if ((ws = MWarehouse.getForOrg((Properties)ctx, (int)line.getAD_Org_ID())) == null && ws.length < 0) {
            return "";
        }
        MLocator locator_to = MLocator.getDefault((MWarehouse)ws[0]);
        if (locator_to != null) {
            line.setM_LocatorTo_ID(locator_to.getM_Locator_ID());
        }
        return "";
    }

    public String UOM(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
        I_DD_OrderLine line = (I_DD_OrderLine)GridTabWrapper.create((GridTab)mTab, I_DD_OrderLine.class);
        MProduct product = MProduct.get((Properties)ctx, (int)line.getM_Product_ID());
        if (product != null) {
            line.setC_UOM_ID(product.getC_UOM_ID());
        }
        return "";
    }

    public String bPartner(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
        I_M_Movement m_movement = (I_M_Movement)GridTabWrapper.create((GridTab)mTab, I_M_Movement.class);
        MOrg org = MOrg.get((Properties)ctx, (int)m_movement.getAD_Org_ID());
        int C_BPartner_ID = org.getLinkedC_BPartner_ID(null);
        if (C_BPartner_ID > 0) {
            MBPartnerLocation[] locations = MBPartnerLocation.getForBPartner((Properties)ctx, (int)C_BPartner_ID, null);
            m_movement.setC_BPartner_ID(C_BPartner_ID);
            if (locations.length > 0) {
                m_movement.setC_BPartner_Location_ID(locations[0].getC_BPartner_Location_ID());
            }
        }
        return "";
    }
}

