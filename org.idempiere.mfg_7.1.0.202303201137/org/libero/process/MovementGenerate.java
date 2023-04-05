/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.compiere.model.MAttributeSet
 *  org.compiere.model.MClient
 *  org.compiere.model.MDocType
 *  org.compiere.model.MLocator
 *  org.compiere.model.MMovement
 *  org.compiere.model.MProduct
 *  org.compiere.model.MProductCategory
 *  org.compiere.model.MStorageOnHand
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.AdempiereUserError
 *  org.compiere.util.CPreparedStatement
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.eevolution.model.MDDOrder
 *  org.eevolution.model.MDDOrderLine
 */
package org.libero.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MLocator;
import org.compiere.model.MMovement;
import org.compiere.model.MProduct;
import org.compiere.model.MProductCategory;
import org.compiere.model.MStorageOnHand;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MDDOrder;
import org.eevolution.model.MDDOrderLine;
import org.libero.model.LiberoMovementLine;

public class MovementGenerate
extends SvrProcess {
    private boolean p_Selection = false;
    private int p_M_Warehouse_ID = 0;
    private int p_C_BPartner_ID = 0;
    private Timestamp p_DatePromised = null;
    private boolean p_IsUnconfirmedInOut = false;
    private String p_docAction = "CO";
    private boolean p_ConsolidateDocument = true;
    private Timestamp p_DateShipped = null;
    private MMovement m_movement = null;
    private int m_created = 0;
    private int m_line = 0;
    private Timestamp m_movementDate = null;
    private int m_lastC_BPartner_Location_ID = -1;
    private String m_sql = null;
    private HashMap<SParameter, MStorageOnHand[]> m_map = new HashMap();
    private SParameter m_lastPP = null;
    private MStorageOnHand[] m_lastStorages = null;

    protected void prepare() {
        for (ProcessInfoParameter para : this.getParameter()) {
            String name = para.getParameterName();
            if (para.getParameter() != null) {
                if (name.equals("M_Warehouse_ID")) {
                    this.p_M_Warehouse_ID = para.getParameterAsInt();
                } else if (name.equals("C_BPartner_ID")) {
                    this.p_C_BPartner_ID = para.getParameterAsInt();
                } else if (name.equals("DatePromised")) {
                    this.p_DatePromised = (Timestamp)para.getParameter();
                } else if (name.equals("Selection")) {
                    this.p_Selection = "Y".equals(para.getParameter());
                } else if (name.equals("IsUnconfirmedInOut")) {
                    this.p_IsUnconfirmedInOut = "Y".equals(para.getParameter());
                } else if (name.equals("ConsolidateDocument")) {
                    this.p_ConsolidateDocument = "Y".equals(para.getParameter());
                } else if (name.equals("DocAction")) {
                    this.p_docAction = (String)para.getParameter();
                } else if (name.equals("MovementDate")) {
                    this.p_DateShipped = (Timestamp)para.getParameter();
                } else {
                    this.log.log(Level.SEVERE, "Unknown Parameter: " + name);
                }
            }
            if (this.p_DateShipped == null) {
                this.m_movementDate = Env.getContextAsDate((Properties)this.getCtx(), (String)"#Date");
                if (this.m_movementDate == null) {
                    this.m_movementDate = new Timestamp(System.currentTimeMillis());
                }
            } else {
                this.m_movementDate = this.p_DateShipped;
            }
            if ("CO".equals(this.p_docAction)) continue;
            this.p_docAction = "PR";
        }
    }

    protected String doIt() throws Exception {
        this.log.info("Selection=" + this.p_Selection + ", M_Warehouse_ID=" + this.p_M_Warehouse_ID + ", C_BPartner_ID=" + this.p_C_BPartner_ID + ", Consolidate=" + this.p_ConsolidateDocument + ", IsUnconfirmed=" + this.p_IsUnconfirmedInOut + ", Movement=" + this.m_movementDate);
        if (this.p_M_Warehouse_ID == 0) {
            throw new AdempiereUserError("@NotFound@ @M_Warehouse_ID@");
        }
        if (this.p_Selection) {
            this.m_sql = "SELECT DD_Order.* FROM DD_Order, T_Selection WHERE DD_Order.DocStatus='CO' AND DD_Order.AD_Client_ID=? AND DD_Order.DD_Order_ID = T_Selection.T_Selection_ID AND T_Selection.AD_PInstance_ID=? ";
        } else {
            this.m_sql = "SELECT * FROM DD_Order o WHERE DocStatus='CO'  AND o.C_DocType_ID IN (SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType='DOO')\tAND o.IsDropShip='N' AND o.DeliveryRule<>'M' AND EXISTS (SELECT 1 FROM DD_OrderLine ol  WHERE ? IN (SELECT l.M_Warehouse_ID FROM M_Locator l WHERE l.M_Locator_ID=ol.M_Locator_ID) ";
            if (this.p_DatePromised != null) {
                this.m_sql = String.valueOf(this.m_sql) + " AND TRUNC(ol.DatePromised)<=?";
            }
            this.m_sql = String.valueOf(this.m_sql) + " AND o.DD_Order_ID=ol.DD_Order_ID AND ol.QtyOrdered<>ol.QtyIntransit)";
            if (this.p_C_BPartner_ID != 0) {
                this.m_sql = String.valueOf(this.m_sql) + " AND o.C_BPartner_ID=?";
            }
            this.m_sql = String.valueOf(this.m_sql) + " ORDER BY M_Warehouse_ID, PriorityRule, M_Shipper_ID, C_BPartner_ID, C_BPartner_Location_ID, DD_Order_ID";
        }
        CPreparedStatement pstmt = null;
        try {
            pstmt = DB.prepareStatement((String)this.m_sql, (String)this.get_TrxName());
            int index = 1;
            if (this.p_Selection) {
                pstmt.setInt(index++, Env.getAD_Client_ID((Properties)this.getCtx()));
                pstmt.setInt(index++, this.getAD_PInstance_ID());
            } else {
                pstmt.setInt(index++, this.p_M_Warehouse_ID);
                if (this.p_DatePromised != null) {
                    pstmt.setTimestamp(index++, this.p_DatePromised);
                }
                if (this.p_C_BPartner_ID != 0) {
                    pstmt.setInt(index++, this.p_C_BPartner_ID);
                }
            }
        }
        catch (Exception e) {
            this.log.log(Level.SEVERE, this.m_sql, (Throwable)e);
        }
        return this.generate((PreparedStatement)pstmt);
    }

    private String generate(PreparedStatement pstmt) {
        MClient client = MClient.get((Properties)this.getCtx());
        try {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                BigDecimal toDeliver;
                MLocator l;
                MDDOrderLine line;
                int i;
                MDDOrder order = new MDDOrder(this.getCtx(), rs, this.get_TrxName());
                if (!this.p_ConsolidateDocument || this.m_movement != null && (this.m_movement.getC_BPartner_Location_ID() != order.getC_BPartner_Location_ID() || this.m_movement.getM_Shipper_ID() != order.getM_Shipper_ID())) {
                    this.completeMovement();
                }
                this.log.fine("check: " + (Object)order + " - DeliveryRule=" + order.getDeliveryRule());
                Timestamp minGuaranteeDate = this.m_movementDate;
                boolean completeOrder = "O".equals(order.getDeliveryRule());
                String where = " " + this.p_M_Warehouse_ID + " IN (SELECT l.M_Warehouse_ID FROM M_Locator l WHERE l.M_Locator_ID=M_Locator_ID) ";
                if (this.p_DatePromised != null) {
                    where = String.valueOf(where) + " AND (TRUNC(DatePromised)<=" + DB.TO_DATE((Timestamp)this.p_DatePromised, (boolean)true) + " OR DatePromised IS NULL)";
                }
                if (!"F".equals(order.getDeliveryRule())) {
                    where = String.valueOf(where) + " AND (DD_OrderLine.M_Product_ID IS NULL OR EXISTS (SELECT * FROM M_Product p WHERE DD_OrderLine.M_Product_ID=p.M_Product_ID AND IsExcludeAutoDelivery='N'))";
                }
                if (!this.p_IsUnconfirmedInOut) {
                    where = String.valueOf(where) + " AND NOT EXISTS (SELECT * FROM M_MovementLine iol INNER JOIN M_Movement io ON (iol.M_Movement_ID=io.M_Movement_ID) WHERE iol.DD_OrderLine_ID=DD_OrderLine.DD_OrderLine_ID AND io.DocStatus IN ('IP','WC'))";
                }
                MDDOrderLine[] lines = order.getLines(where, "M_Product_ID");
                for (i = 0; i < lines.length; ++i) {
                    BigDecimal deliver;
                    boolean fullLine;
                    line = lines[i];
                    l = new MLocator(this.getCtx(), line.getM_Locator_ID(), this.get_TrxName());
                    if (l.getM_Warehouse_ID() != this.p_M_Warehouse_ID) continue;
                    this.log.fine("check: " + (Object)line);
                    BigDecimal onHand = Env.ZERO;
                    toDeliver = line.getConfirmedQty();
                    MProduct product = line.getProduct();
                    if (product != null && toDeliver.signum() == 0 || line.getC_Charge_ID() != 0 && toDeliver.signum() == 0) continue;
                    BigDecimal unconfirmedShippedQty = Env.ZERO;
                    if (this.p_IsUnconfirmedInOut && product != null && toDeliver.signum() != 0) {
                        String where2 = "EXISTS (SELECT * FROM M_Movement io WHERE io.M_Movement_ID=M_MovementLine.M_Movement_ID AND io.DocStatus IN ('IP','WC'))";
                        LiberoMovementLine[] iols = LiberoMovementLine.getOfOrderLine(this.getCtx(), line.getDD_OrderLine_ID(), where2, null);
                        for (int j = 0; j < iols.length; ++j) {
                            unconfirmedShippedQty = unconfirmedShippedQty.add(iols[j].getMovementQty());
                        }
                        String logInfo = "Unconfirmed Qty=" + unconfirmedShippedQty + " - ToDeliver=" + toDeliver + "->";
                        toDeliver = toDeliver.subtract(unconfirmedShippedQty);
                        logInfo = String.valueOf(logInfo) + toDeliver;
                        if (toDeliver.signum() < 0) {
                            toDeliver = Env.ZERO;
                            logInfo = String.valueOf(logInfo) + " (set to 0)";
                        }
                        onHand = onHand.subtract(unconfirmedShippedQty);
                        this.log.fine(logInfo);
                    }
                    if (!(product != null && product.isStocked() || line.getQtyOrdered().signum() != 0 && toDeliver.signum() == 0)) {
                        if ("O".equals(order.getDeliveryRule())) continue;
                        this.createLine(order, line, toDeliver, null, false);
                        continue;
                    }
                    MProductCategory pc = MProductCategory.get((Properties)order.getCtx(), (int)product.getM_Product_Category_ID());
                    String MMPolicy = pc.getMMPolicy();
                    if (MMPolicy == null || MMPolicy.length() == 0) {
                        MMPolicy = client.getMMPolicy();
                    }
                    MStorageOnHand[] storages = this.getStorages(l.getM_Warehouse_ID(), line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(), product.getM_AttributeSet_ID(), line.getM_AttributeSetInstance_ID() == 0, minGuaranteeDate, "F".equals(MMPolicy));
                    for (int j = 0; j < storages.length; ++j) {
                        MStorageOnHand storage = storages[j];
                        onHand = onHand.add(storage.getQtyOnHand());
                    }
                    boolean bl = fullLine = onHand.compareTo(toDeliver) >= 0 || toDeliver.signum() < 0;
                    if (completeOrder && !fullLine) {
                        this.log.fine("Failed CompleteOrder - OnHand=" + onHand + " (Unconfirmed=" + unconfirmedShippedQty + "), ToDeliver=" + toDeliver + " - " + (Object)line);
                        completeOrder = false;
                        break;
                    }
                    if (fullLine && "L".equals(order.getDeliveryRule())) {
                        this.log.fine("CompleteLine - OnHand=" + onHand + " (Unconfirmed=" + unconfirmedShippedQty + ", ToDeliver=" + toDeliver + " - " + (Object)line);
                        this.createLine(order, line, toDeliver, storages, false);
                        continue;
                    }
                    if ("A".equals(order.getDeliveryRule()) && (onHand.signum() > 0 || toDeliver.signum() < 0)) {
                        deliver = toDeliver;
                        if (deliver.compareTo(onHand) > 0) {
                            deliver = onHand;
                        }
                        this.log.fine("Available - OnHand=" + onHand + " (Unconfirmed=" + unconfirmedShippedQty + "), ToDeliver=" + toDeliver + ", Delivering=" + deliver + " - " + (Object)line);
                        this.createLine(order, line, deliver, storages, false);
                        continue;
                    }
                    if ("F".equals(order.getDeliveryRule())) {
                        deliver = toDeliver;
                        this.log.fine("Force - OnHand=" + onHand + " (Unconfirmed=" + unconfirmedShippedQty + "), ToDeliver=" + toDeliver + ", Delivering=" + deliver + " - " + (Object)line);
                        this.createLine(order, line, deliver, storages, true);
                        continue;
                    }
                    if ("M".equals(order.getDeliveryRule())) {
                        this.log.fine("Manual - OnHand=" + onHand + " (Unconfirmed=" + unconfirmedShippedQty + ") - " + (Object)line);
                        continue;
                    }
                    this.log.fine("Failed: " + order.getDeliveryRule() + " - OnHand=" + onHand + " (Unconfirmed=" + unconfirmedShippedQty + "), ToDeliver=" + toDeliver + " - " + (Object)line);
                }
                if (completeOrder && "O".equals(order.getDeliveryRule())) {
                    for (i = 0; i < lines.length; ++i) {
                        line = lines[i];
                        l = new MLocator(this.getCtx(), line.getM_Locator_ID(), this.get_TrxName());
                        if (l.getM_Warehouse_ID() != this.p_M_Warehouse_ID) continue;
                        MProduct product = line.getProduct();
                        toDeliver = line.getQtyOrdered().subtract(line.getQtyDelivered());
                        MStorageOnHand[] storages = null;
                        if (product != null && product.isStocked()) {
                            MProductCategory pc = MProductCategory.get((Properties)order.getCtx(), (int)product.getM_Product_Category_ID());
                            String MMPolicy = pc.getMMPolicy();
                            if (MMPolicy == null || MMPolicy.length() == 0) {
                                MMPolicy = client.getMMPolicy();
                            }
                            storages = this.getStorages(l.getM_Warehouse_ID(), line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(), product.getM_AttributeSet_ID(), line.getM_AttributeSetInstance_ID() == 0, minGuaranteeDate, "F".equals(MMPolicy));
                        }
                        this.createLine(order, line, toDeliver, storages, false);
                    }
                }
                this.m_line += 1000;
            }
            rs.close();
            pstmt.close();
            pstmt = null;
        }
        catch (Exception e) {
            this.log.log(Level.SEVERE, this.m_sql, (Throwable)e);
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
            pstmt = null;
        }
        catch (Exception exception) {
            pstmt = null;
        }
        this.completeMovement();
        return "@Created@ = " + this.m_created;
    }

    private void createLine(MDDOrder order, MDDOrderLine orderLine, BigDecimal qty, MStorageOnHand[] storages, boolean force) {
        if (this.m_lastC_BPartner_Location_ID != order.getC_BPartner_Location_ID()) {
            this.completeMovement();
        }
        this.m_lastC_BPartner_Location_ID = order.getC_BPartner_Location_ID();
        if (this.m_movement == null) {
            int docTypeDO_ID;
            MLocator locator = MLocator.get((Properties)this.getCtx(), (int)orderLine.getM_Locator_ID());
            this.m_movement = MovementGenerate.createMovement(order, this.m_movementDate);
            this.m_movement.setAD_Org_ID(locator.getAD_Org_ID());
            this.m_movement.setIsInTransit(true);
            this.m_movement.setDD_Order_ID(order.getDD_Order_ID());
            if (order.getC_BPartner_ID() != order.getC_BPartner_ID()) {
                this.m_movement.setC_BPartner_ID(order.getC_BPartner_ID());
            }
            if (order.getC_BPartner_Location_ID() != order.getC_BPartner_Location_ID()) {
                this.m_movement.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());
            }
            if ((docTypeDO_ID = this.getDocType("MMM", this.m_movement.getAD_Org_ID())) > 0) {
                this.m_movement.setC_DocType_ID(docTypeDO_ID);
            }
            if (!this.m_movement.save()) {
                throw new IllegalStateException("Could not create Movement");
            }
        }
        if (storages == null) {
            LiberoMovementLine line = new LiberoMovementLine(this.m_movement);
            line.setOrderLine(orderLine, Env.ZERO, false);
            line.setMovementQty(qty);
            if (orderLine.getQtyEntered().compareTo(orderLine.getQtyOrdered()) != 0) {
                line.setMovementQty(qty.multiply(orderLine.getQtyEntered()).divide(orderLine.getQtyOrdered(), 12, 4));
            }
            line.setLine(this.m_line + orderLine.getLine());
            if (!line.save()) {
                throw new IllegalStateException("Could not create Shipment Line");
            }
            this.log.fine(line.toString());
            return;
        }
        MProduct product = orderLine.getProduct();
        boolean linePerASI = false;
        if (product.getM_AttributeSet_ID() != 0) {
            MAttributeSet mas = MAttributeSet.get((Properties)this.getCtx(), (int)product.getM_AttributeSet_ID());
            linePerASI = mas.isInstanceAttribute();
        }
        ArrayList<LiberoMovementLine> list = new ArrayList<LiberoMovementLine>();
        BigDecimal toDeliver = qty;
        for (int i = 0; i < storages.length; ++i) {
            BigDecimal deliver = toDeliver;
            MStorageOnHand storage = storages[i];
            if (deliver.compareTo(storage.getQtyOnHand()) > 0 && storage.getQtyOnHand().signum() >= 0 && (!force || force && i + 1 != storages.length)) {
                deliver = storage.getQtyOnHand();
            }
            if (deliver.signum() == 0) continue;
            int M_Locator_ID = storage.getM_Locator_ID();
            LiberoMovementLine line = null;
            if (!linePerASI) {
                for (int ll = 0; ll < list.size(); ++ll) {
                    LiberoMovementLine test = (LiberoMovementLine)((Object)list.get(ll));
                    if (test.getM_Locator_ID() != M_Locator_ID) continue;
                    line = test;
                    break;
                }
            }
            if (line == null) {
                line = new LiberoMovementLine(this.m_movement);
                line.setOrderLine(orderLine, deliver, false);
                line.setMovementQty(deliver);
                list.add(line);
            } else {
                line.setMovementQty(line.getMovementQty().add(deliver));
            }
            if (orderLine.getQtyEntered().compareTo(orderLine.getQtyOrdered()) != 0) {
                line.setMovementQty(line.getMovementQty().multiply(orderLine.getQtyEntered()).divide(orderLine.getQtyOrdered(), 12, 4));
            }
            line.setLine(this.m_line + orderLine.getLine());
            if (linePerASI) {
                line.setM_AttributeSetInstance_ID(storage.getM_AttributeSetInstance_ID());
            }
            if (!line.save()) {
                throw new IllegalStateException("Could not create Shipment Line");
            }
            this.log.fine("ToDeliver=" + qty + "/" + deliver + " - " + (Object)((Object)line));
            toDeliver = toDeliver.subtract(deliver);
            storage.setQtyOnHand(storage.getQtyOnHand().subtract(deliver));
            if (toDeliver.signum() == 0) break;
        }
        if (toDeliver.signum() != 0) {
            throw new IllegalStateException("Not All Delivered - Remainder=" + toDeliver);
        }
    }

    private static MMovement createMovement(MDDOrder order, Timestamp movementDate) {
        MMovement move = new MMovement(order.getCtx(), 0, order.get_TrxName());
        move.setC_BPartner_ID(order.getC_BPartner_ID());
        move.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());
        move.setAD_User_ID(order.getAD_User_ID());
        if (movementDate != null) {
            move.setMovementDate(movementDate);
        }
        move.setDD_Order_ID(order.getC_Order_ID());
        move.setDeliveryRule(order.getDeliveryRule());
        move.setDeliveryViaRule(order.getDeliveryViaRule());
        move.setM_Shipper_ID(order.getM_Shipper_ID());
        move.setFreightCostRule(order.getFreightCostRule());
        move.setFreightAmt(order.getFreightAmt());
        move.setSalesRep_ID(order.getSalesRep_ID());
        move.setC_Activity_ID(order.getC_Activity_ID());
        move.setC_Campaign_ID(order.getC_Campaign_ID());
        move.setC_Charge_ID(order.getC_Charge_ID());
        move.setChargeAmt(order.getChargeAmt());
        move.setC_Project_ID(order.getC_Project_ID());
        move.setDescription(order.getDescription());
        move.setSalesRep_ID(order.getSalesRep_ID());
        move.setAD_OrgTrx_ID(order.getAD_OrgTrx_ID());
        move.setUser1_ID(order.getUser1_ID());
        move.setUser2_ID(order.getUser2_ID());
        move.setPriorityRule(order.getPriorityRule());
        return move;
    }

    private int getDocType(String docBaseType, int AD_Org_ID) {
        MDocType[] docs = MDocType.getOfDocBaseType((Properties)this.getCtx(), (String)docBaseType);
        if (docs == null || docs.length == 0) {
            String textMsg = "Not found default document type for docbasetype " + docBaseType;
            throw new AdempiereException(textMsg);
        }
        MDocType[] arrmDocType = docs;
        int n = docs.length;
        for (int i = 0; i < n; ++i) {
            MDocType doc = arrmDocType[i];
            if (doc.getAD_Org_ID() != AD_Org_ID) continue;
            return doc.getC_DocType_ID();
        }
        this.log.info("Doc Type for " + docBaseType + ": " + docs[0].getC_DocType_ID());
        return docs[0].getC_DocType_ID();
    }

    private MStorageOnHand[] getStorages(int M_Warehouse_ID, int M_Product_ID, int M_AttributeSetInstance_ID, int M_AttributeSet_ID, boolean allAttributeInstances, Timestamp minGuaranteeDate, boolean FiFo) {
        this.m_lastPP = new SParameter(M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID, M_AttributeSet_ID, allAttributeInstances, minGuaranteeDate, FiFo);
        this.m_lastStorages = this.m_map.get(this.m_lastPP);
        if (this.m_lastStorages == null) {
            this.m_lastStorages = MStorageOnHand.getWarehouse((Properties)this.getCtx(), (int)M_Warehouse_ID, (int)M_Product_ID, (int)M_AttributeSetInstance_ID, (int)M_AttributeSet_ID, (boolean)allAttributeInstances, (Timestamp)minGuaranteeDate, (boolean)FiFo, (String)this.get_TrxName());
            this.m_map.put(this.m_lastPP, this.m_lastStorages);
        }
        return this.m_lastStorages;
    }

    private void completeMovement() {
        if (this.m_movement != null) {
            if (!this.m_movement.processIt(this.p_docAction)) {
                this.log.warning("Failed: " + (Object)this.m_movement);
            }
            this.m_movement.saveEx();
            this.addLog(this.m_movement.getM_Movement_ID(), this.m_movement.getMovementDate(), null, this.m_movement.getDocumentNo());
            ++this.m_created;
            this.m_map = new HashMap();
            if (this.m_lastPP != null && this.m_lastStorages != null) {
                this.m_map.put(this.m_lastPP, this.m_lastStorages);
            }
        }
        this.m_movement = null;
        this.m_line = 0;
    }

    class SParameter {
        public int M_Warehouse_ID;
        public int M_Product_ID;
        public int M_AttributeSetInstance_ID;
        public int M_AttributeSet_ID;
        public boolean allAttributeInstances;
        public Timestamp minGuaranteeDate;
        public boolean FiFo;

        protected SParameter(int p_Warehouse_ID, int p_Product_ID, int p_AttributeSetInstance_ID, int p_AttributeSet_ID, boolean p_allAttributeInstances, Timestamp p_minGuaranteeDate, boolean p_FiFo) {
            this.M_Warehouse_ID = p_Warehouse_ID;
            this.M_Product_ID = p_Product_ID;
            this.M_AttributeSetInstance_ID = p_AttributeSetInstance_ID;
            this.M_AttributeSet_ID = p_AttributeSet_ID;
            this.allAttributeInstances = p_allAttributeInstances;
            this.minGuaranteeDate = p_minGuaranteeDate;
            this.FiFo = p_FiFo;
        }

        public boolean equals(Object obj) {
            if (obj != null && obj instanceof SParameter) {
                boolean eq;
                SParameter cmp = (SParameter)obj;
                boolean bl = eq = cmp.M_Warehouse_ID == this.M_Warehouse_ID && cmp.M_Product_ID == this.M_Product_ID && cmp.M_AttributeSetInstance_ID == this.M_AttributeSetInstance_ID && cmp.M_AttributeSet_ID == this.M_AttributeSet_ID && cmp.allAttributeInstances == this.allAttributeInstances && cmp.FiFo == this.FiFo;
                if (!(!eq || cmp.minGuaranteeDate == null && this.minGuaranteeDate == null || cmp.minGuaranteeDate != null && this.minGuaranteeDate != null && cmp.minGuaranteeDate.equals(this.minGuaranteeDate))) {
                    eq = false;
                }
                return eq;
            }
            return false;
        }

        public int hashCode() {
            long hash = this.M_Warehouse_ID + this.M_Product_ID * 2 + this.M_AttributeSetInstance_ID * 3 + this.M_AttributeSet_ID * 4;
            if (this.allAttributeInstances) {
                hash *= -1L;
            }
            if (this.FiFo) {
                // empty if block
            }
            if ((hash *= -2L) < 0L) {
                hash = -hash + 7L;
            }
            while (hash > Integer.MAX_VALUE) {
                hash -= Integer.MAX_VALUE;
            }
            if (this.minGuaranteeDate != null) {
                while ((hash += (long)this.minGuaranteeDate.hashCode()) > Integer.MAX_VALUE) {
                    hash -= Integer.MAX_VALUE;
                }
            }
            return (int)hash;
        }
    }
}

