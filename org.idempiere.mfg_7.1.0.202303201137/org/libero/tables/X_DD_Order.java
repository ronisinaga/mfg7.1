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
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.libero.tables.I_DD_Order;

public class X_DD_Order
extends PO
implements I_DD_Order,
I_Persistent {
    private static final long serialVersionUID = 20130626L;
    public static final int DELIVERYRULE_AD_Reference_ID = 151;
    public static final String DELIVERYRULE_AfterReceipt = "R";
    public static final String DELIVERYRULE_Availability = "A";
    public static final String DELIVERYRULE_CompleteLine = "L";
    public static final String DELIVERYRULE_CompleteOrder = "O";
    public static final String DELIVERYRULE_Force = "F";
    public static final String DELIVERYRULE_Manual = "M";
    public static final int DELIVERYVIARULE_AD_Reference_ID = 152;
    public static final String DELIVERYVIARULE_Pickup = "P";
    public static final String DELIVERYVIARULE_Delivery = "D";
    public static final String DELIVERYVIARULE_Shipper = "S";
    public static final int DOCACTION_AD_Reference_ID = 135;
    public static final String DOCACTION_Complete = "CO";
    public static final String DOCACTION_Approve = "AP";
    public static final String DOCACTION_Reject = "RJ";
    public static final String DOCACTION_Post = "PO";
    public static final String DOCACTION_Void = "VO";
    public static final String DOCACTION_Close = "CL";
    public static final String DOCACTION_Reverse_Correct = "RC";
    public static final String DOCACTION_Reverse_Accrual = "RA";
    public static final String DOCACTION_Invalidate = "IN";
    public static final String DOCACTION_Re_Activate = "RE";
    public static final String DOCACTION_None = "--";
    public static final String DOCACTION_Prepare = "PR";
    public static final String DOCACTION_Unlock = "XL";
    public static final String DOCACTION_WaitComplete = "WC";
    public static final int DOCSTATUS_AD_Reference_ID = 131;
    public static final String DOCSTATUS_Drafted = "DR";
    public static final String DOCSTATUS_Completed = "CO";
    public static final String DOCSTATUS_Approved = "AP";
    public static final String DOCSTATUS_NotApproved = "NA";
    public static final String DOCSTATUS_Voided = "VO";
    public static final String DOCSTATUS_Invalid = "IN";
    public static final String DOCSTATUS_Reversed = "RE";
    public static final String DOCSTATUS_Closed = "CL";
    public static final String DOCSTATUS_Unknown = "??";
    public static final String DOCSTATUS_InProgress = "IP";
    public static final String DOCSTATUS_WaitingPayment = "WP";
    public static final String DOCSTATUS_WaitingConfirmation = "WC";
    public static final int FREIGHTCOSTRULE_AD_Reference_ID = 153;
    public static final String FREIGHTCOSTRULE_FreightIncluded = "I";
    public static final String FREIGHTCOSTRULE_FixPrice = "F";
    public static final String FREIGHTCOSTRULE_Calculated = "C";
    public static final String FREIGHTCOSTRULE_Line = "L";
    public static final int PRIORITYRULE_AD_Reference_ID = 154;
    public static final String PRIORITYRULE_High = "3";
    public static final String PRIORITYRULE_Medium = "5";
    public static final String PRIORITYRULE_Low = "7";
    public static final String PRIORITYRULE_Urgent = "1";
    public static final String PRIORITYRULE_Minor = "9";

    public X_DD_Order(Properties ctx, int DD_Order_ID, String trxName) {
        super(ctx, DD_Order_ID, trxName);
    }

    public X_DD_Order(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53037, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_DD_Order[").append(this.get_ID()).append("]");
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
    public I_AD_User getAD_User() throws RuntimeException {
        return (I_AD_User)MTable.get((Properties)this.getCtx(), (String)"AD_User").getPO(this.getAD_User_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_User_ID(int AD_User_ID) {
        if (AD_User_ID < 1) {
            this.set_Value("AD_User_ID", null);
        } else {
            this.set_Value("AD_User_ID", AD_User_ID);
        }
    }

    @Override
    public int getAD_User_ID() {
        Integer ii = (Integer)this.get_Value("AD_User_ID");
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
    public I_C_BPartner getC_BPartner() throws RuntimeException {
        return (I_C_BPartner)MTable.get((Properties)this.getCtx(), (String)"C_BPartner").getPO(this.getC_BPartner_ID(), this.get_TrxName());
    }

    @Override
    public void setC_BPartner_ID(int C_BPartner_ID) {
        if (C_BPartner_ID < 1) {
            this.set_Value("C_BPartner_ID", null);
        } else {
            this.set_Value("C_BPartner_ID", C_BPartner_ID);
        }
    }

    @Override
    public int getC_BPartner_ID() {
        Integer ii = (Integer)this.get_Value("C_BPartner_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException {
        return (I_C_BPartner_Location)MTable.get((Properties)this.getCtx(), (String)"C_BPartner_Location").getPO(this.getC_BPartner_Location_ID(), this.get_TrxName());
    }

    @Override
    public void setC_BPartner_Location_ID(int C_BPartner_Location_ID) {
        if (C_BPartner_Location_ID < 1) {
            this.set_Value("C_BPartner_Location_ID", null);
        } else {
            this.set_Value("C_BPartner_Location_ID", C_BPartner_Location_ID);
        }
    }

    @Override
    public int getC_BPartner_Location_ID() {
        Integer ii = (Integer)this.get_Value("C_BPartner_Location_ID");
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
    public I_C_DocType getC_DocType() throws RuntimeException {
        return (I_C_DocType)MTable.get((Properties)this.getCtx(), (String)"C_DocType").getPO(this.getC_DocType_ID(), this.get_TrxName());
    }

    @Override
    public void setC_DocType_ID(int C_DocType_ID) {
        if (C_DocType_ID < 0) {
            this.set_ValueNoCheck("C_DocType_ID", null);
        } else {
            this.set_ValueNoCheck("C_DocType_ID", C_DocType_ID);
        }
    }

    @Override
    public int getC_DocType_ID() {
        Integer ii = (Integer)this.get_Value("C_DocType_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setChargeAmt(BigDecimal ChargeAmt) {
        this.set_Value("ChargeAmt", ChargeAmt);
    }

    @Override
    public BigDecimal getChargeAmt() {
        BigDecimal bd = (BigDecimal)this.get_Value("ChargeAmt");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public I_C_Invoice getC_Invoice() throws RuntimeException {
        return (I_C_Invoice)MTable.get((Properties)this.getCtx(), (String)"C_Invoice").getPO(this.getC_Invoice_ID(), this.get_TrxName());
    }

    @Override
    public void setC_Invoice_ID(int C_Invoice_ID) {
        if (C_Invoice_ID < 1) {
            this.set_ValueNoCheck("C_Invoice_ID", null);
        } else {
            this.set_ValueNoCheck("C_Invoice_ID", C_Invoice_ID);
        }
    }

    @Override
    public int getC_Invoice_ID() {
        Integer ii = (Integer)this.get_Value("C_Invoice_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_C_Order getC_Order() throws RuntimeException {
        return (I_C_Order)MTable.get((Properties)this.getCtx(), (String)"C_Order").getPO(this.getC_Order_ID(), this.get_TrxName());
    }

    @Override
    public void setC_Order_ID(int C_Order_ID) {
        if (C_Order_ID < 1) {
            this.set_ValueNoCheck("C_Order_ID", null);
        } else {
            this.set_ValueNoCheck("C_Order_ID", C_Order_ID);
        }
    }

    @Override
    public int getC_Order_ID() {
        Integer ii = (Integer)this.get_Value("C_Order_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
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
    public void setCreateConfirm(String CreateConfirm) {
        this.set_Value("CreateConfirm", CreateConfirm);
    }

    @Override
    public String getCreateConfirm() {
        return (String)this.get_Value("CreateConfirm");
    }

    @Override
    public void setCreateFrom(String CreateFrom) {
        this.set_Value("CreateFrom", CreateFrom);
    }

    @Override
    public String getCreateFrom() {
        return (String)this.get_Value("CreateFrom");
    }

    @Override
    public void setCreatePackage(String CreatePackage) {
        this.set_Value("CreatePackage", CreatePackage);
    }

    @Override
    public String getCreatePackage() {
        return (String)this.get_Value("CreatePackage");
    }

    @Override
    public void setDateOrdered(Timestamp DateOrdered) {
        this.set_ValueNoCheck("DateOrdered", DateOrdered);
    }

    @Override
    public Timestamp getDateOrdered() {
        return (Timestamp)this.get_Value("DateOrdered");
    }

    @Override
    public void setDatePrinted(Timestamp DatePrinted) {
        this.set_Value("DatePrinted", DatePrinted);
    }

    @Override
    public Timestamp getDatePrinted() {
        return (Timestamp)this.get_Value("DatePrinted");
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
    public void setDateReceived(Timestamp DateReceived) {
        this.set_Value("DateReceived", DateReceived);
    }

    @Override
    public Timestamp getDateReceived() {
        return (Timestamp)this.get_Value("DateReceived");
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
    public void setDD_Order_UU(String DD_Order_UU) {
        this.set_Value("DD_Order_UU", DD_Order_UU);
    }

    @Override
    public String getDD_Order_UU() {
        return (String)this.get_Value("DD_Order_UU");
    }

    @Override
    public void setDeliveryRule(String DeliveryRule) {
        this.set_Value("DeliveryRule", DeliveryRule);
    }

    @Override
    public String getDeliveryRule() {
        return (String)this.get_Value("DeliveryRule");
    }

    @Override
    public void setDeliveryViaRule(String DeliveryViaRule) {
        this.set_Value("DeliveryViaRule", DeliveryViaRule);
    }

    @Override
    public String getDeliveryViaRule() {
        return (String)this.get_Value("DeliveryViaRule");
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
    public void setDocAction(String DocAction2) {
        this.set_Value("DocAction", DocAction2);
    }

    @Override
    public String getDocAction() {
        return (String)this.get_Value("DocAction");
    }

    @Override
    public void setDocStatus(String DocStatus) {
        this.set_Value("DocStatus", DocStatus);
    }

    @Override
    public String getDocStatus() {
        return (String)this.get_Value("DocStatus");
    }

    @Override
    public void setDocumentNo(String DocumentNo) {
        this.set_ValueNoCheck("DocumentNo", DocumentNo);
    }

    @Override
    public String getDocumentNo() {
        return (String)this.get_Value("DocumentNo");
    }

    public KeyNamePair getKeyNamePair() {
        return new KeyNamePair(this.get_ID(), this.getDocumentNo());
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
    public void setFreightCostRule(String FreightCostRule) {
        this.set_Value("FreightCostRule", FreightCostRule);
    }

    @Override
    public String getFreightCostRule() {
        return (String)this.get_Value("FreightCostRule");
    }

    @Override
    public void setGenerateTo(String GenerateTo) {
        this.set_Value("GenerateTo", GenerateTo);
    }

    @Override
    public String getGenerateTo() {
        return (String)this.get_Value("GenerateTo");
    }

    @Override
    public void setIsApproved(boolean IsApproved) {
        this.set_Value("IsApproved", IsApproved);
    }

    @Override
    public boolean isApproved() {
        Object oo = this.get_Value("IsApproved");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIsDelivered(boolean IsDelivered) {
        this.set_Value("IsDelivered", IsDelivered);
    }

    @Override
    public boolean isDelivered() {
        Object oo = this.get_Value("IsDelivered");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIsDropShip(boolean IsDropShip) {
        this.set_Value("IsDropShip", IsDropShip);
    }

    @Override
    public boolean isDropShip() {
        Object oo = this.get_Value("IsDropShip");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIsInDispute(boolean IsInDispute) {
        this.set_Value("IsInDispute", IsInDispute);
    }

    @Override
    public boolean isInDispute() {
        Object oo = this.get_Value("IsInDispute");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIsInTransit(boolean IsInTransit) {
        this.set_Value("IsInTransit", IsInTransit);
    }

    @Override
    public boolean isInTransit() {
        Object oo = this.get_Value("IsInTransit");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIsPrinted(boolean IsPrinted) {
        this.set_Value("IsPrinted", IsPrinted);
    }

    @Override
    public boolean isPrinted() {
        Object oo = this.get_Value("IsPrinted");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIsSelected(boolean IsSelected) {
        this.set_Value("IsSelected", IsSelected);
    }

    @Override
    public boolean isSelected() {
        Object oo = this.get_Value("IsSelected");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIsSOTrx(boolean IsSOTrx) {
        this.set_Value("IsSOTrx", IsSOTrx);
    }

    @Override
    public boolean isSOTrx() {
        Object oo = this.get_Value("IsSOTrx");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
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
    public I_M_Warehouse getM_Warehouse() throws RuntimeException {
        return (I_M_Warehouse)MTable.get((Properties)this.getCtx(), (String)"M_Warehouse").getPO(this.getM_Warehouse_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Warehouse_ID(int M_Warehouse_ID) {
        if (M_Warehouse_ID < 1) {
            this.set_ValueNoCheck("M_Warehouse_ID", null);
        } else {
            this.set_ValueNoCheck("M_Warehouse_ID", M_Warehouse_ID);
        }
    }

    @Override
    public int getM_Warehouse_ID() {
        Integer ii = (Integer)this.get_Value("M_Warehouse_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setNoPackages(int NoPackages) {
        this.set_Value("NoPackages", NoPackages);
    }

    @Override
    public int getNoPackages() {
        Integer ii = (Integer)this.get_Value("NoPackages");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPickDate(Timestamp PickDate) {
        this.set_Value("PickDate", PickDate);
    }

    @Override
    public Timestamp getPickDate() {
        return (Timestamp)this.get_Value("PickDate");
    }

    @Override
    public void setPOReference(String POReference) {
        this.set_Value("POReference", POReference);
    }

    @Override
    public String getPOReference() {
        return (String)this.get_Value("POReference");
    }

    @Override
    public void setPosted(boolean Posted) {
        this.set_Value("Posted", Posted);
    }

    @Override
    public boolean isPosted() {
        Object oo = this.get_Value("Posted");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setPriorityRule(String PriorityRule) {
        this.set_Value("PriorityRule", PriorityRule);
    }

    @Override
    public String getPriorityRule() {
        return (String)this.get_Value("PriorityRule");
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
    public void setProcessedOn(BigDecimal ProcessedOn) {
        this.set_Value("ProcessedOn", ProcessedOn);
    }

    @Override
    public BigDecimal getProcessedOn() {
        BigDecimal bd = (BigDecimal)this.get_Value("ProcessedOn");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setProcessing(boolean Processing) {
        this.set_Value("Processing", Processing);
    }

    @Override
    public boolean isProcessing() {
        Object oo = this.get_Value("Processing");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public I_C_Order getRef_Order() throws RuntimeException {
        return (I_C_Order)MTable.get((Properties)this.getCtx(), (String)"C_Order").getPO(this.getRef_Order_ID(), this.get_TrxName());
    }

    @Override
    public void setRef_Order_ID(int Ref_Order_ID) {
        if (Ref_Order_ID < 1) {
            this.set_Value("Ref_Order_ID", null);
        } else {
            this.set_Value("Ref_Order_ID", Ref_Order_ID);
        }
    }

    @Override
    public int getRef_Order_ID() {
        Integer ii = (Integer)this.get_Value("Ref_Order_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_AD_User getSalesRep() throws RuntimeException {
        return (I_AD_User)MTable.get((Properties)this.getCtx(), (String)"AD_User").getPO(this.getSalesRep_ID(), this.get_TrxName());
    }

    @Override
    public void setSalesRep_ID(int SalesRep_ID) {
        if (SalesRep_ID < 1) {
            this.set_Value("SalesRep_ID", null);
        } else {
            this.set_Value("SalesRep_ID", SalesRep_ID);
        }
    }

    @Override
    public int getSalesRep_ID() {
        Integer ii = (Integer)this.get_Value("SalesRep_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setSendEMail(boolean SendEMail) {
        this.set_Value("SendEMail", SendEMail);
    }

    @Override
    public boolean isSendEMail() {
        Object oo = this.get_Value("SendEMail");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setShipDate(Timestamp ShipDate) {
        this.set_Value("ShipDate", ShipDate);
    }

    @Override
    public Timestamp getShipDate() {
        return (Timestamp)this.get_Value("ShipDate");
    }

    @Override
    public void setTrackingNo(String TrackingNo) {
        this.set_Value("TrackingNo", TrackingNo);
    }

    @Override
    public String getTrackingNo() {
        return (String)this.get_Value("TrackingNo");
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

    @Override
    public void setVolume(BigDecimal Volume) {
        this.set_Value("Volume", Volume);
    }

    @Override
    public BigDecimal getVolume() {
        BigDecimal bd = (BigDecimal)this.get_Value("Volume");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setWeight(BigDecimal Weight) {
        this.set_Value("Weight", Weight);
    }

    @Override
    public BigDecimal getWeight() {
        BigDecimal bd = (BigDecimal)this.get_Value("Weight");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }
}

