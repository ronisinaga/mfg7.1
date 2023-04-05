/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MAttribute
 *  org.compiere.model.MAttributeInstance
 *  org.compiere.model.MAttributeSet
 *  org.compiere.model.MAttributeSetInstance
 *  org.compiere.model.MProduct
 *  org.compiere.model.MStorageOnHand
 *  org.compiere.model.MStorageReservation
 *  org.compiere.model.PO
 *  org.compiere.util.Env
 */
package org.libero.model.reasoner;

import java.math.BigDecimal;
import java.util.Properties;
import org.compiere.model.MAttribute;
import org.compiere.model.MAttributeInstance;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MProduct;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MStorageReservation;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.libero.model.MPPOrder;
import org.libero.model.MPPOrderWorkflow;
import org.libero.model.wrapper.BOMLineWrapper;

public class StorageReasoner {
    public MPPOrderWorkflow getPPOrderWorkflow(MPPOrder order) {
        int[] ids = PO.getAllIDs((String)"PP_Order_Workflow", (String)("PP_Order_ID = " + order.get_ID()), null);
        if (ids.length != 1) {
            return null;
        }
        return new MPPOrderWorkflow(Env.getCtx(), ids[0], null);
    }

    public boolean equalAttributeInstanceValue(MAttributeInstance ai1, MAttributeInstance ai2) {
        if (ai1.getM_Attribute_ID() != ai2.getM_Attribute_ID()) {
            return false;
        }
        boolean equal = true;
        MAttribute a = new MAttribute(Env.getCtx(), ai1.getM_Attribute_ID(), null);
        if ("N".equals(a.getAttributeValueType())) {
            equal = ai1.getValue() == null ? ai2.getValueNumber() == null : ai1.getValueNumber().compareTo(ai2.getValueNumber()) == 0;
        } else if ("S".equals(a.getAttributeValueType())) {
            equal = ai1.getValue() == null ? ai2.getValue() == null : ai1.getValue().equals(ai2.getValue());
        } else if ("L".equals(a.getAttributeValueType())) {
            equal = ai1.getM_AttributeValue_ID() == ai2.getM_AttributeValue_ID();
        }
        return equal;
    }

    public int[] getAttributeIDs(MAttributeSetInstance asi) {
        MAttributeSet as = new MAttributeSet(Env.getCtx(), asi.getM_AttributeSet_ID(), null);
        return this.getPOIDs("M_Attribute", "M_Attribute_ID IN (SELECT M_Attribute_ID FROM M_AttributeUse WHERE M_AttributeSet_ID = " + as.get_ID() + ")", null);
    }

    public BigDecimal getSumQtyAvailable(MProduct p, MAttributeSetInstance asi) {
        int[] ids = this.getPOIDs("M_Locator", null, null);
        MStorageOnHand storage = null;
        BigDecimal sumQtyAvailable = BigDecimal.ZERO;
        for (int i = 0; i < ids.length; ++i) {
            storage = MStorageOnHand.get((Properties)Env.getCtx(), (int)ids[i], (int)p.get_ID(), (int)asi.get_ID(), null);
            if (storage == null) continue;
            BigDecimal available = MStorageReservation.getQtyAvailable((int)p.get_ID(), (int)storage.getM_Warehouse_ID(), (int)asi.get_ID(), null);
            sumQtyAvailable = sumQtyAvailable.add(available);
        }
        return sumQtyAvailable;
    }

    public BigDecimal getSumQtyRequired(BOMLineWrapper line) {
        MProduct p = new MProduct(Env.getCtx(), line.getM_Product_ID(), null);
        MAttributeSetInstance asi = new MAttributeSetInstance(Env.getCtx(), line.getM_AttributeSetInstance_ID(), null);
        return this.getSumQtyAvailable(p, asi).subtract(line.getQtyBOM()).negate();
    }

    public BigDecimal getAvailableQtyLocator(int prodID, String trxName) {
        BigDecimal qtyOnHand = this.getQtyOnHand(prodID, trxName);
        BigDecimal qtyReserved = this.getQtyReserved(prodID, trxName);
        return qtyOnHand.subtract(qtyReserved);
    }

    public BigDecimal getQtyOnHand(int prodID, String trxName) {
        MStorageOnHand[] storages;
        BigDecimal qtyOnHand = BigDecimal.ZERO;
        MStorageOnHand[] arrmStorageOnHand = storages = MStorageOnHand.getOfProduct((Properties)Env.getCtx(), (int)prodID, (String)trxName);
        int n = storages.length;
        for (int i = 0; i < n; ++i) {
            MStorageOnHand storage = arrmStorageOnHand[i];
            if (storage == null) continue;
            qtyOnHand = qtyOnHand.add(storage.getQtyOnHand());
        }
        return qtyOnHand;
    }

    public BigDecimal getQtyReserved(int prodID, String trxName) {
        MStorageReservation[] reserves;
        BigDecimal qtyReserved = BigDecimal.ZERO;
        MStorageReservation[] arrmStorageReservation = reserves = MStorageReservation.getOfProduct((Properties)Env.getCtx(), (int)prodID, (String)trxName);
        int n = reserves.length;
        for (int i = 0; i < n; ++i) {
            MStorageReservation reserve = arrmStorageReservation[i];
            qtyReserved = qtyReserved.add(reserve.getQty());
        }
        return qtyReserved;
    }

    public boolean isQtyAvailable(BOMLineWrapper line) {
        MProduct p = new MProduct(Env.getCtx(), line.getM_Product_ID(), null);
        MAttributeSetInstance asi = new MAttributeSetInstance(Env.getCtx(), line.getM_AttributeSetInstance_ID(), null);
        return this.isQtyAvailable(p, asi);
    }

    public boolean isQtyAvailable(MProduct p, MAttributeSetInstance asi) {
        int[] ids = this.getPOIDs("M_Locator", null, null);
        MStorageOnHand storage = null;
        BigDecimal sumQtyOnHand = BigDecimal.ZERO;
        BigDecimal sumQtyReserved = BigDecimal.ZERO;
        boolean count = false;
        for (int i = 0; i < ids.length; ++i) {
            storage = MStorageOnHand.get((Properties)Env.getCtx(), (int)ids[i], (int)p.get_ID(), (int)asi.get_ID(), null);
            if (storage == null) continue;
            BigDecimal available = MStorageReservation.getQtyAvailable((int)p.get_ID(), (int)storage.getM_Warehouse_ID(), (int)asi.get_ID(), null);
            BigDecimal reserved = storage.getQtyOnHand().subtract(available);
            sumQtyOnHand = sumQtyOnHand.add(storage.getQtyOnHand());
            sumQtyReserved = sumQtyReserved.add(reserved);
        }
        double available = sumQtyOnHand.subtract(sumQtyReserved).setScale(2, 4).doubleValue();
        return count && !(available <= 0.0);
    }

    public int[] getPOIDs(String locator, String where, String trx) {
        String client = "AD_Client_ID = " + Env.getAD_Client_ID((Properties)Env.getCtx());
        String w = null;
        w = where == null || where.length() == 0 ? client : String.valueOf(where) + " AND " + client;
        return PO.getAllIDs((String)locator, (String)w, (String)trx);
    }
}

