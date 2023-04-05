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
 *  org.compiere.util.KeyNamePair
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import org.compiere.util.KeyNamePair;
import org.libero.tables.I_DD_Order;

public interface I_DD_OrderLine {
    public static final String Table_Name = "DD_OrderLine";
    public static final int Table_ID = 53038;
    public static final KeyNamePair Model = new KeyNamePair(53038, "DD_OrderLine");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(1L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";
    public static final String COLUMNNAME_ConfirmedQty = "ConfirmedQty";
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";
    public static final String COLUMNNAME_DateDelivered = "DateDelivered";
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";
    public static final String COLUMNNAME_DatePromised = "DatePromised";
    public static final String COLUMNNAME_DD_Order_ID = "DD_Order_ID";
    public static final String COLUMNNAME_DD_OrderLine_ID = "DD_OrderLine_ID";
    public static final String COLUMNNAME_DD_OrderLine_UU = "DD_OrderLine_UU";
    public static final String COLUMNNAME_Description = "Description";
    public static final String COLUMNNAME_FreightAmt = "FreightAmt";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_IsDescription = "IsDescription";
    public static final String COLUMNNAME_IsInvoiced = "IsInvoiced";
    public static final String COLUMNNAME_Line = "Line";
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";
    public static final String COLUMNNAME_M_AttributeSetInstanceTo_ID = "M_AttributeSetInstanceTo_ID";
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";
    public static final String COLUMNNAME_M_LocatorTo_ID = "M_LocatorTo_ID";
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";
    public static final String COLUMNNAME_PickedQty = "PickedQty";
    public static final String COLUMNNAME_Processed = "Processed";
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";
    public static final String COLUMNNAME_QtyEntered = "QtyEntered";
    public static final String COLUMNNAME_QtyInTransit = "QtyInTransit";
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";
    public static final String COLUMNNAME_QtyReserved = "QtyReserved";
    public static final String COLUMNNAME_ScrappedQty = "ScrappedQty";
    public static final String COLUMNNAME_TargetQty = "TargetQty";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
    public static final String COLUMNNAME_User1_ID = "User1_ID";
    public static final String COLUMNNAME_User2_ID = "User2_ID";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setAD_OrgTrx_ID(int var1);

    public int getAD_OrgTrx_ID();

    public void setC_Activity_ID(int var1);

    public int getC_Activity_ID();

    public I_C_Activity getC_Activity() throws RuntimeException;

    public void setC_Campaign_ID(int var1);

    public int getC_Campaign_ID();

    public I_C_Campaign getC_Campaign() throws RuntimeException;

    public void setC_Charge_ID(int var1);

    public int getC_Charge_ID();

    public I_C_Charge getC_Charge() throws RuntimeException;

    public void setConfirmedQty(BigDecimal var1);

    public BigDecimal getConfirmedQty();

    public void setC_Project_ID(int var1);

    public int getC_Project_ID();

    public I_C_Project getC_Project() throws RuntimeException;

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setC_UOM_ID(int var1);

    public int getC_UOM_ID();

    public I_C_UOM getC_UOM() throws RuntimeException;

    public void setDateDelivered(Timestamp var1);

    public Timestamp getDateDelivered();

    public void setDateOrdered(Timestamp var1);

    public Timestamp getDateOrdered();

    public void setDatePromised(Timestamp var1);

    public Timestamp getDatePromised();

    public void setDD_Order_ID(int var1);

    public int getDD_Order_ID();

    public I_DD_Order getDD_Order() throws RuntimeException;

    public void setDD_OrderLine_ID(int var1);

    public int getDD_OrderLine_ID();

    public void setDD_OrderLine_UU(String var1);

    public String getDD_OrderLine_UU();

    public void setDescription(String var1);

    public String getDescription();

    public void setFreightAmt(BigDecimal var1);

    public BigDecimal getFreightAmt();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setIsDescription(boolean var1);

    public boolean isDescription();

    public void setIsInvoiced(boolean var1);

    public boolean isInvoiced();

    public void setLine(int var1);

    public int getLine();

    public void setLineNetAmt(BigDecimal var1);

    public BigDecimal getLineNetAmt();

    public void setM_AttributeSetInstance_ID(int var1);

    public int getM_AttributeSetInstance_ID();

    public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

    public void setM_AttributeSetInstanceTo_ID(int var1);

    public int getM_AttributeSetInstanceTo_ID();

    public I_M_AttributeSetInstance getM_AttributeSetInstanceTo() throws RuntimeException;

    public void setM_Locator_ID(int var1);

    public int getM_Locator_ID();

    public I_M_Locator getM_Locator() throws RuntimeException;

    public void setM_LocatorTo_ID(int var1);

    public int getM_LocatorTo_ID();

    public I_M_Locator getM_LocatorTo() throws RuntimeException;

    public void setM_Product_ID(int var1);

    public int getM_Product_ID();

    public I_M_Product getM_Product() throws RuntimeException;

    public void setM_Shipper_ID(int var1);

    public int getM_Shipper_ID();

    public I_M_Shipper getM_Shipper() throws RuntimeException;

    public void setPickedQty(BigDecimal var1);

    public BigDecimal getPickedQty();

    public void setProcessed(boolean var1);

    public boolean isProcessed();

    public void setQtyDelivered(BigDecimal var1);

    public BigDecimal getQtyDelivered();

    public void setQtyEntered(BigDecimal var1);

    public BigDecimal getQtyEntered();

    public void setQtyInTransit(BigDecimal var1);

    public BigDecimal getQtyInTransit();

    public void setQtyOrdered(BigDecimal var1);

    public BigDecimal getQtyOrdered();

    public void setQtyReserved(BigDecimal var1);

    public BigDecimal getQtyReserved();

    public void setScrappedQty(BigDecimal var1);

    public BigDecimal getScrappedQty();

    public void setTargetQty(BigDecimal var1);

    public BigDecimal getTargetQty();

    public Timestamp getUpdated();

    public int getUpdatedBy();

    public void setUser1_ID(int var1);

    public int getUser1_ID();

    public I_C_ElementValue getUser1() throws RuntimeException;

    public void setUser2_ID(int var1);

    public int getUser2_ID();

    public I_C_ElementValue getUser2() throws RuntimeException;
}

