/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_C_Activity
 *  org.compiere.model.I_C_Campaign
 *  org.compiere.model.I_C_Charge
 *  org.compiere.model.I_C_ElementValue
 *  org.compiere.model.I_C_Project
 *  org.compiere.model.I_C_UOM
 *  org.compiere.model.I_M_AttributeSetInstance
 *  org.compiere.model.I_M_Locator
 *  org.compiere.model.I_M_Product
 *  org.compiere.model.I_M_Shipper
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 *  org.compiere.util.Env
 *  org.compiere.util.KeyNamePair
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_Campaign;
import org.compiere.model.I_C_Charge;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.libero.tables.I_DD_Order;
import org.libero.tables.I_DD_OrderLine;

public class X_DD_OrderLine
extends PO
implements I_DD_OrderLine,
I_Persistent {
    private static final long serialVersionUID = 20130626L;

    public X_DD_OrderLine(Properties ctx, int DD_OrderLine_ID, String trxName) {
        super(ctx, DD_OrderLine_ID, trxName);
    }

    public X_DD_OrderLine(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53038, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_DD_OrderLine[").append(this.get_ID()).append("]");
        return sb.toString();
    }

    @Override
    public void setAD_OrgTrx_ID(int AD_OrgTrx_ID) {
        if (AD_OrgTrx_ID < 1) {
            this.set_Value("AD_OrgTrx_ID", null);
        } else {
            this.set_Value("AD_OrgTrx_ID", AD_OrgTrx_ID);
        }
    }

    @Override
    public int getAD_OrgTrx_ID() {
        Integer ii = (Integer)this.get_Value("AD_OrgTrx_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_C_Activity getC_Activity() throws RuntimeException {
        return (I_C_Activity)MTable.get((Properties)this.getCtx(), (String)"C_Activity").getPO(this.getC_Activity_ID(), this.get_TrxName());
    }

    @Override
    public void setC_Activity_ID(int C_Activity_ID) {
        if (C_Activity_ID < 1) {
            this.set_Value("C_Activity_ID", null);
        } else {
            this.set_Value("C_Activity_ID", C_Activity_ID);
        }
    }

    @Override
    public int getC_Activity_ID() {
        Integer ii = (Integer)this.get_Value("C_Activity_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_C_Campaign getC_Campaign() throws RuntimeException {
        return (I_C_Campaign)MTable.get((Properties)this.getCtx(), (String)"C_Campaign").getPO(this.getC_Campaign_ID(), this.get_TrxName());
    }

    @Override
    public void setC_Campaign_ID(int C_Campaign_ID) {
        if (C_Campaign_ID < 1) {
            this.set_Value("C_Campaign_ID", null);
        } else {
            this.set_Value("C_Campaign_ID", C_Campaign_ID);
        }
    }

    @Override
    public int getC_Campaign_ID() {
        Integer ii = (Integer)this.get_Value("C_Campaign_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_C_Charge getC_Charge() throws RuntimeException {
        return (I_C_Charge)MTable.get((Properties)this.getCtx(), (String)"C_Charge").getPO(this.getC_Charge_ID(), this.get_TrxName());
    }

    @Override
    public void setC_Charge_ID(int C_Charge_ID) {
        if (C_Charge_ID < 1) {
            this.set_Value("C_Charge_ID", null);
        } else {
            this.set_Value("C_Charge_ID", C_Charge_ID);
        }
    }

    @Override
    public int getC_Charge_ID() {
        Integer ii = (Integer)this.get_Value("C_Charge_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setConfirmedQty(BigDecimal ConfirmedQty) {
        this.set_Value("ConfirmedQty", ConfirmedQty);
    }

    @Override
    public BigDecimal getConfirmedQty() {
        BigDecimal bd = (BigDecimal)this.get_Value("ConfirmedQty");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public I_C_Project getC_Project() throws RuntimeException {
        return (I_C_Project)MTable.get((Properties)this.getCtx(), (String)"C_Project").getPO(this.getC_Project_ID(), this.get_TrxName());
    }

    @Override
    public void setC_Project_ID(int C_Project_ID) {
        if (C_Project_ID < 1) {
            this.set_Value("C_Project_ID", null);
        } else {
            this.set_Value("C_Project_ID", C_Project_ID);
        }
    }

    @Override
    public int getC_Project_ID() {
        Integer ii = (Integer)this.get_Value("C_Project_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_C_UOM getC_UOM() throws RuntimeException {
        return (I_C_UOM)MTable.get((Properties)this.getCtx(), (String)"C_UOM").getPO(this.getC_UOM_ID(), this.get_TrxName());
    }

    @Override
    public void setC_UOM_ID(int C_UOM_ID) {
        if (C_UOM_ID < 1) {
            this.set_ValueNoCheck("C_UOM_ID", null);
        } else {
            this.set_ValueNoCheck("C_UOM_ID", C_UOM_ID);
        }
    }

    @Override
    public int getC_UOM_ID() {
        Integer ii = (Integer)this.get_Value("C_UOM_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setDateDelivered(Timestamp DateDelivered) {
        this.set_Value("DateDelivered", DateDelivered);
    }

    @Override
    public Timestamp getDateDelivered() {
        return (Timestamp)this.get_Value("DateDelivered");
    }

    @Override
    public void setDateOrdered(Timestamp DateOrdered) {
        this.set_Value("DateOrdered", DateOrdered);
    }

    @Override
    public Timestamp getDateOrdered() {
        return (Timestamp)this.get_Value("DateOrdered");
    }

    @Override
    public void setDatePromised(Timestamp DatePromised) {
        this.set_Value("DatePromised", DatePromised);
    }

    @Override
    public Timestamp getDatePromised() {
        return (Timestamp)this.get_Value("DatePromised");
    }

    @Override
    public I_DD_Order getDD_Order() throws RuntimeException {
        return (I_DD_Order)MTable.get((Properties)this.getCtx(), (String)"DD_Order").getPO(this.getDD_Order_ID(), this.get_TrxName());
    }

    @Override
    public void setDD_Order_ID(int DD_Order_ID) {
        if (DD_Order_ID < 1) {
            this.set_ValueNoCheck("DD_Order_ID", null);
        } else {
            this.set_ValueNoCheck("DD_Order_ID", DD_Order_ID);
        }
    }

    @Override
    public int getDD_Order_ID() {
        Integer ii = (Integer)this.get_Value("DD_Order_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setDD_OrderLine_ID(int DD_OrderLine_ID) {
        if (DD_OrderLine_ID < 1) {
            this.set_ValueNoCheck("DD_OrderLine_ID", null);
        } else {
            this.set_ValueNoCheck("DD_OrderLine_ID", DD_OrderLine_ID);
        }
    }

    @Override
    public int getDD_OrderLine_ID() {
        Integer ii = (Integer)this.get_Value("DD_OrderLine_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setDD_OrderLine_UU(String DD_OrderLine_UU) {
        this.set_Value("DD_OrderLine_UU", DD_OrderLine_UU);
    }

    @Override
    public String getDD_OrderLine_UU() {
        return (String)this.get_Value("DD_OrderLine_UU");
    }

    @Override
    public void setDescription(String Description) {
        this.set_Value("Description", Description);
    }

    @Override
    public String getDescription() {
        return (String)this.get_Value("Description");
    }

    @Override
    public void setFreightAmt(BigDecimal FreightAmt) {
        this.set_Value("FreightAmt", FreightAmt);
    }

    @Override
    public BigDecimal getFreightAmt() {
        BigDecimal bd = (BigDecimal)this.get_Value("FreightAmt");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setIsDescription(boolean IsDescription) {
        this.set_Value("IsDescription", IsDescription);
    }

    @Override
    public boolean isDescription() {
        Object oo = this.get_Value("IsDescription");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIsInvoiced(boolean IsInvoiced) {
        this.set_Value("IsInvoiced", IsInvoiced);
    }

    @Override
    public boolean isInvoiced() {
        Object oo = this.get_Value("IsInvoiced");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setLine(int Line) {
        this.set_Value("Line", Line);
    }

    @Override
    public int getLine() {
        Integer ii = (Integer)this.get_Value("Line");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    public KeyNamePair getKeyNamePair() {
        return new KeyNamePair(this.get_ID(), String.valueOf(this.getLine()));
    }

    @Override
    public void setLineNetAmt(BigDecimal LineNetAmt) {
        this.set_Value("LineNetAmt", LineNetAmt);
    }

    @Override
    public BigDecimal getLineNetAmt() {
        BigDecimal bd = (BigDecimal)this.get_Value("LineNetAmt");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException {
        return (I_M_AttributeSetInstance)MTable.get((Properties)this.getCtx(), (String)"M_AttributeSetInstance").getPO(this.getM_AttributeSetInstance_ID(), this.get_TrxName());
    }

    @Override
    public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
        if (M_AttributeSetInstance_ID < 0) {
            this.set_Value("M_AttributeSetInstance_ID", null);
        } else {
            this.set_Value("M_AttributeSetInstance_ID", M_AttributeSetInstance_ID);
        }
    }

    @Override
    public int getM_AttributeSetInstance_ID() {
        Integer ii = (Integer)this.get_Value("M_AttributeSetInstance_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_M_AttributeSetInstance getM_AttributeSetInstanceTo() throws RuntimeException {
        return (I_M_AttributeSetInstance)MTable.get((Properties)this.getCtx(), (String)"M_AttributeSetInstance").getPO(this.getM_AttributeSetInstanceTo_ID(), this.get_TrxName());
    }

    @Override
    public void setM_AttributeSetInstanceTo_ID(int M_AttributeSetInstanceTo_ID) {
        if (M_AttributeSetInstanceTo_ID < 1) {
            this.set_Value("M_AttributeSetInstanceTo_ID", null);
        } else {
            this.set_Value("M_AttributeSetInstanceTo_ID", M_AttributeSetInstanceTo_ID);
        }
    }

    @Override
    public int getM_AttributeSetInstanceTo_ID() {
        Integer ii = (Integer)this.get_Value("M_AttributeSetInstanceTo_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_M_Locator getM_Locator() throws RuntimeException {
        return (I_M_Locator)MTable.get((Properties)this.getCtx(), (String)"M_Locator").getPO(this.getM_Locator_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Locator_ID(int M_Locator_ID) {
        if (M_Locator_ID < 1) {
            this.set_Value("M_Locator_ID", null);
        } else {
            this.set_Value("M_Locator_ID", M_Locator_ID);
        }
    }

    @Override
    public int getM_Locator_ID() {
        Integer ii = (Integer)this.get_Value("M_Locator_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_M_Locator getM_LocatorTo() throws RuntimeException {
        return (I_M_Locator)MTable.get((Properties)this.getCtx(), (String)"M_Locator").getPO(this.getM_LocatorTo_ID(), this.get_TrxName());
    }

    @Override
    public void setM_LocatorTo_ID(int M_LocatorTo_ID) {
        if (M_LocatorTo_ID < 1) {
            this.set_Value("M_LocatorTo_ID", null);
        } else {
            this.set_Value("M_LocatorTo_ID", M_LocatorTo_ID);
        }
    }

    @Override
    public int getM_LocatorTo_ID() {
        Integer ii = (Integer)this.get_Value("M_LocatorTo_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_M_Product getM_Product() throws RuntimeException {
        return (I_M_Product)MTable.get((Properties)this.getCtx(), (String)"M_Product").getPO(this.getM_Product_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Product_ID(int M_Product_ID) {
        if (M_Product_ID < 1) {
            this.set_Value("M_Product_ID", null);
        } else {
            this.set_Value("M_Product_ID", M_Product_ID);
        }
    }

    @Override
    public int getM_Product_ID() {
        Integer ii = (Integer)this.get_Value("M_Product_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_M_Shipper getM_Shipper() throws RuntimeException {
        return (I_M_Shipper)MTable.get((Properties)this.getCtx(), (String)"M_Shipper").getPO(this.getM_Shipper_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Shipper_ID(int M_Shipper_ID) {
        if (M_Shipper_ID < 1) {
            this.set_Value("M_Shipper_ID", null);
        } else {
            this.set_Value("M_Shipper_ID", M_Shipper_ID);
        }
    }

    @Override
    public int getM_Shipper_ID() {
        Integer ii = (Integer)this.get_Value("M_Shipper_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPickedQty(BigDecimal PickedQty) {
        this.set_Value("PickedQty", PickedQty);
    }

    @Override
    public BigDecimal getPickedQty() {
        BigDecimal bd = (BigDecimal)this.get_Value("PickedQty");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setProcessed(boolean Processed) {
        this.set_Value("Processed", Processed);
    }

    @Override
    public boolean isProcessed() {
        Object oo = this.get_Value("Processed");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setQtyDelivered(BigDecimal QtyDelivered) {
        this.set_Value("QtyDelivered", QtyDelivered);
    }

    @Override
    public BigDecimal getQtyDelivered() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyDelivered");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyEntered(BigDecimal QtyEntered) {
        this.set_Value("QtyEntered", QtyEntered);
    }

    @Override
    public BigDecimal getQtyEntered() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyEntered");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyInTransit(BigDecimal QtyInTransit) {
        this.set_Value("QtyInTransit", QtyInTransit);
    }

    @Override
    public BigDecimal getQtyInTransit() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyInTransit");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyOrdered(BigDecimal QtyOrdered) {
        this.set_Value("QtyOrdered", QtyOrdered);
    }

    @Override
    public BigDecimal getQtyOrdered() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyOrdered");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyReserved(BigDecimal QtyReserved) {
        this.set_Value("QtyReserved", QtyReserved);
    }

    @Override
    public BigDecimal getQtyReserved() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyReserved");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setScrappedQty(BigDecimal ScrappedQty) {
        this.set_Value("ScrappedQty", ScrappedQty);
    }

    @Override
    public BigDecimal getScrappedQty() {
        BigDecimal bd = (BigDecimal)this.get_Value("ScrappedQty");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setTargetQty(BigDecimal TargetQty) {
        this.set_Value("TargetQty", TargetQty);
    }

    @Override
    public BigDecimal getTargetQty() {
        BigDecimal bd = (BigDecimal)this.get_Value("TargetQty");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public I_C_ElementValue getUser1() throws RuntimeException {
        return (I_C_ElementValue)MTable.get((Properties)this.getCtx(), (String)"C_ElementValue").getPO(this.getUser1_ID(), this.get_TrxName());
    }

    @Override
    public void setUser1_ID(int User1_ID) {
        if (User1_ID < 1) {
            this.set_Value("User1_ID", null);
        } else {
            this.set_Value("User1_ID", User1_ID);
        }
    }

    @Override
    public int getUser1_ID() {
        Integer ii = (Integer)this.get_Value("User1_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_C_ElementValue getUser2() throws RuntimeException {
        return (I_C_ElementValue)MTable.get((Properties)this.getCtx(), (String)"C_ElementValue").getPO(this.getUser2_ID(), this.get_TrxName());
    }

    @Override
    public void setUser2_ID(int User2_ID) {
        if (User2_ID < 1) {
            this.set_Value("User2_ID", null);
        } else {
            this.set_Value("User2_ID", User2_ID);
        }
    }

    @Override
    public int getUser2_ID() {
        Integer ii = (Integer)this.get_Value("User2_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }
}

