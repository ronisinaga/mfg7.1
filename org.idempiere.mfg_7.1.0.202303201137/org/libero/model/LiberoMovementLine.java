/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.compiere.model.MLocator
 *  org.compiere.model.MMovement
 *  org.compiere.model.MMovementLine
 *  org.compiere.model.MProduct
 *  org.compiere.model.MWarehouse
 *  org.compiere.model.Query
 *  org.eevolution.model.MDDOrderLine
 */
package org.libero.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MLocator;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.MProduct;
import org.compiere.model.MWarehouse;
import org.compiere.model.Query;
import org.eevolution.model.MDDOrderLine;

public class LiberoMovementLine
extends MMovementLine {
    private static final long serialVersionUID = 1L;

    public LiberoMovementLine(MMovement parent) {
        super(parent);
    }

    public LiberoMovementLine(Properties ctx, int record_ID, String trxName) {
        super(ctx, record_ID, trxName);
    }

    public void setOrderLine(MDDOrderLine oLine, BigDecimal Qty, boolean isReceipt) {
        this.setDD_OrderLine_ID(oLine.getDD_OrderLine_ID());
        this.setLine(oLine.getLine());
        MProduct product = oLine.getProduct();
        if (product == null) {
            this.set_ValueNoCheck("M_Product_ID", null);
            this.set_ValueNoCheck("M_AttributeSetInstance_ID", null);
            this.set_ValueNoCheck("M_AttributeSetInstanceTo_ID", null);
            this.set_ValueNoCheck("M_Locator_ID", null);
            this.set_ValueNoCheck("M_LocatorTo_ID", null);
        } else {
            this.setM_Product_ID(oLine.getM_Product_ID());
            this.setM_AttributeSetInstance_ID(oLine.getM_AttributeSetInstance_ID());
            this.setM_AttributeSetInstanceTo_ID(oLine.getM_AttributeSetInstanceTo_ID());
            if (product.isItem()) {
                MWarehouse w = MWarehouse.get((Properties)this.getCtx(), (int)oLine.getParent().getM_Warehouse_ID());
                MLocator locator_inTransit = MLocator.getDefault((MWarehouse)w);
                if (locator_inTransit == null) {
                    throw new AdempiereException("Do not exist Locator for the  Warehouse in transit");
                }
                if (isReceipt) {
                    this.setM_Locator_ID(locator_inTransit.getM_Locator_ID());
                    this.setM_LocatorTo_ID(oLine.getM_LocatorTo_ID());
                } else {
                    this.setM_Locator_ID(oLine.getM_Locator_ID());
                    this.setM_LocatorTo_ID(locator_inTransit.getM_Locator_ID());
                }
            } else {
                this.set_ValueNoCheck("M_Locator_ID", null);
                this.set_ValueNoCheck("M_LocatorTo_ID", null);
            }
        }
        this.setDescription(oLine.getDescription());
        this.setMovementQty(Qty);
    }

    public static LiberoMovementLine[] getOfOrderLine(Properties ctx, int DD_OrderLine_ID, String where, String trxName) {
        String whereClause = "DD_OrderLine_ID=?";
        if (where != null && where.length() > 0) {
            whereClause = String.valueOf(whereClause) + " AND (" + where + ")";
        }
        List list = new Query(ctx, "M_MovementLine", whereClause, trxName).setParameters(new Object[]{DD_OrderLine_ID}).list();
        return list.toArray((T[])new LiberoMovementLine[list.size()]);
    }
}

