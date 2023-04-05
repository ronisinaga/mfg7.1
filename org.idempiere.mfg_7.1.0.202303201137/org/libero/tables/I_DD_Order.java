/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_User
 *  org.compiere.model.I_C_Activity
 *  org.compiere.model.I_C_BPartner
 *  org.compiere.model.I_C_BPartner_Location
 *  org.compiere.model.I_C_Campaign
 *  org.compiere.model.I_C_Charge
 *  org.compiere.model.I_C_DocType
 *  org.compiere.model.I_C_ElementValue
 *  org.compiere.model.I_C_Invoice
 *  org.compiere.model.I_C_Order
 *  org.compiere.model.I_C_Project
 *  org.compiere.model.I_M_Shipper
 *  org.compiere.model.I_M_Warehouse
 *  org.compiere.util.KeyNamePair
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Campaign;
import org.compiere.model.I_C_Charge;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.KeyNamePair;

public interface I_DD_Order {
    public static final String Table_Name = "DD_Order";
    public static final int Table_ID = 53037;
    public static final KeyNamePair Model = new KeyNamePair(53037, "DD_Order");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(1L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";
    public static final String COLUMNNAME_ChargeAmt = "ChargeAmt";
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";
    public static final String COLUMNNAME_CreateConfirm = "CreateConfirm";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_CreateFrom = "CreateFrom";
    public static final String COLUMNNAME_CreatePackage = "CreatePackage";
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";
    public static final String COLUMNNAME_DatePrinted = "DatePrinted";
    public static final String COLUMNNAME_DatePromised = "DatePromised";
    public static final String COLUMNNAME_DateReceived = "DateReceived";
    public static final String COLUMNNAME_DD_Order_ID = "DD_Order_ID";
    public static final String COLUMNNAME_DD_Order_UU = "DD_Order_UU";
    public static final String COLUMNNAME_DeliveryRule = "DeliveryRule";
    public static final String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";
    public static final String COLUMNNAME_Description = "Description";
    public static final String COLUMNNAME_DocAction = "DocAction";
    public static final String COLUMNNAME_DocStatus = "DocStatus";
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";
    public static final String COLUMNNAME_FreightAmt = "FreightAmt";
    public static final String COLUMNNAME_FreightCostRule = "FreightCostRule";
    public static final String COLUMNNAME_GenerateTo = "GenerateTo";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_IsApproved = "IsApproved";
    public static final String COLUMNNAME_IsDelivered = "IsDelivered";
    public static final String COLUMNNAME_IsDropShip = "IsDropShip";
    public static final String COLUMNNAME_IsInDispute = "IsInDispute";
    public static final String COLUMNNAME_IsInTransit = "IsInTransit";
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";
    public static final String COLUMNNAME_IsSelected = "IsSelected";
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";
    public static final String COLUMNNAME_NoPackages = "NoPackages";
    public static final String COLUMNNAME_PickDate = "PickDate";
    public static final String COLUMNNAME_POReference = "POReference";
    public static final String COLUMNNAME_Posted = "Posted";
    public static final String COLUMNNAME_PriorityRule = "PriorityRule";
    public static final String COLUMNNAME_Processed = "Processed";
    public static final String COLUMNNAME_ProcessedOn = "ProcessedOn";
    public static final String COLUMNNAME_Processing = "Processing";
    public static final String COLUMNNAME_Ref_Order_ID = "Ref_Order_ID";
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";
    public static final String COLUMNNAME_SendEMail = "SendEMail";
    public static final String COLUMNNAME_ShipDate = "ShipDate";
    public static final String COLUMNNAME_TrackingNo = "TrackingNo";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
    public static final String COLUMNNAME_User1_ID = "User1_ID";
    public static final String COLUMNNAME_User2_ID = "User2_ID";
    public static final String COLUMNNAME_Volume = "Volume";
    public static final String COLUMNNAME_Weight = "Weight";

    public int getAD_Client_ID();

    public void setAD_Org_ID(int var1);

    public int getAD_Org_ID();

    public void setAD_OrgTrx_ID(int var1);

    public int getAD_OrgTrx_ID();

    public void setAD_User_ID(int var1);

    public int getAD_User_ID();

    public I_AD_User getAD_User() throws RuntimeException;

    public void setC_Activity_ID(int var1);

    public int getC_Activity_ID();

    public I_C_Activity getC_Activity() throws RuntimeException;

    public void setC_BPartner_ID(int var1);

    public int getC_BPartner_ID();

    public I_C_BPartner getC_BPartner() throws RuntimeException;

    public void setC_BPartner_Location_ID(int var1);

    public int getC_BPartner_Location_ID();

    public I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

    public void setC_Campaign_ID(int var1);

    public int getC_Campaign_ID();

    public I_C_Campaign getC_Campaign() throws RuntimeException;

    public void setC_Charge_ID(int var1);

    public int getC_Charge_ID();

    public I_C_Charge getC_Charge() throws RuntimeException;

    public void setC_DocType_ID(int var1);

    public int getC_DocType_ID();

    public I_C_DocType getC_DocType() throws RuntimeException;

    public void setChargeAmt(BigDecimal var1);

    public BigDecimal getChargeAmt();

    public void setC_Invoice_ID(int var1);

    public int getC_Invoice_ID();

    public I_C_Invoice getC_Invoice() throws RuntimeException;

    public void setC_Order_ID(int var1);

    public int getC_Order_ID();

    public I_C_Order getC_Order() throws RuntimeException;

    public void setC_Project_ID(int var1);

    public int getC_Project_ID();

    public I_C_Project getC_Project() throws RuntimeException;

    public void setCreateConfirm(String var1);

    public String getCreateConfirm();

    public Timestamp getCreated();

    public int getCreatedBy();

    public void setCreateFrom(String var1);

    public String getCreateFrom();

    public void setCreatePackage(String var1);

    public String getCreatePackage();

    public void setDateOrdered(Timestamp var1);

    public Timestamp getDateOrdered();

    public void setDatePrinted(Timestamp var1);

    public Timestamp getDatePrinted();

    public void setDatePromised(Timestamp var1);

    public Timestamp getDatePromised();

    public void setDateReceived(Timestamp var1);

    public Timestamp getDateReceived();

    public void setDD_Order_ID(int var1);

    public int getDD_Order_ID();

    public void setDD_Order_UU(String var1);

    public String getDD_Order_UU();

    public void setDeliveryRule(String var1);

    public String getDeliveryRule();

    public void setDeliveryViaRule(String var1);

    public String getDeliveryViaRule();

    public void setDescription(String var1);

    public String getDescription();

    public void setDocAction(String var1);

    public String getDocAction();

    public void setDocStatus(String var1);

    public String getDocStatus();

    public void setDocumentNo(String var1);

    public String getDocumentNo();

    public void setFreightAmt(BigDecimal var1);

    public BigDecimal getFreightAmt();

    public void setFreightCostRule(String var1);

    public String getFreightCostRule();

    public void setGenerateTo(String var1);

    public String getGenerateTo();

    public void setIsActive(boolean var1);

    public boolean isActive();

    public void setIsApproved(boolean var1);

    public boolean isApproved();

    public void setIsDelivered(boolean var1);

    public boolean isDelivered();

    public void setIsDropShip(boolean var1);

    public boolean isDropShip();

    public void setIsInDispute(boolean var1);

    public boolean isInDispute();

    public void setIsInTransit(boolean var1);

    public boolean isInTransit();

    public void setIsPrinted(boolean var1);

    public boolean isPrinted();

    public void setIsSelected(boolean var1);

    public boolean isSelected();

    public void setIsSOTrx(boolean var1);

    public boolean isSOTrx();

    public void setM_Shipper_ID(int var1);

    public int getM_Shipper_ID();

    public I_M_Shipper getM_Shipper() throws RuntimeException;

    public void setM_Warehouse_ID(int var1);

    public int getM_Warehouse_ID();

    public I_M_Warehouse getM_Warehouse() throws RuntimeException;

    public void setNoPackages(int var1);

    public int getNoPackages();

    public void setPickDate(Timestamp var1);

    public Timestamp getPickDate();

    public void setPOReference(String var1);

    public String getPOReference();

    public void setPosted(boolean var1);

    public boolean isPosted();

    public void setPriorityRule(String var1);

    public String getPriorityRule();

    public void setProcessed(boolean var1);

    public boolean isProcessed();

    public void setProcessedOn(BigDecimal var1);

    public BigDecimal getProcessedOn();

    public void setProcessing(boolean var1);

    public boolean isProcessing();

    public void setRef_Order_ID(int var1);

    public int getRef_Order_ID();

    public I_C_Order getRef_Order() throws RuntimeException;

    public void setSalesRep_ID(int var1);

    public int getSalesRep_ID();

    public I_AD_User getSalesRep() throws RuntimeException;

    public void setSendEMail(boolean var1);

    public boolean isSendEMail();

    public void setShipDate(Timestamp var1);

    public Timestamp getShipDate();

    public void setTrackingNo(String var1);

    public String getTrackingNo();

    public Timestamp getUpdated();

    public int getUpdatedBy();

    public void setUser1_ID(int var1);

    public int getUser1_ID();

    public I_C_ElementValue getUser1() throws RuntimeException;

    public void setUser2_ID(int var1);

    public int getUser2_ID();

    public I_C_ElementValue getUser2() throws RuntimeException;

    public void setVolume(BigDecimal var1);

    public BigDecimal getVolume();

    public void setWeight(BigDecimal var1);

    public BigDecimal getWeight();
}

