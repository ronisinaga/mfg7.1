/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.base.event.AbstractEventHandler
 *  org.adempiere.base.event.LoginEventData
 *  org.adempiere.exceptions.AdempiereException
 *  org.compiere.model.MForecastLine
 *  org.compiere.model.MInOut
 *  org.compiere.model.MInOutLine
 *  org.compiere.model.MMovement
 *  org.compiere.model.MMovementLine
 *  org.compiere.model.MOrder
 *  org.compiere.model.MOrderLine
 *  org.compiere.model.MProduct
 *  org.compiere.model.MRMALine
 *  org.compiere.model.MRequisition
 *  org.compiere.model.MRequisitionLine
 *  org.compiere.model.PO
 *  org.compiere.model.Query
 *  org.compiere.model.X_M_Forecast
 *  org.compiere.process.DocAction
 *  org.compiere.util.CLogger
 *  org.compiere.util.Env
 *  org.compiere.util.Msg
 *  org.eevolution.model.MDDOrder
 *  org.eevolution.model.MDDOrderLine
 *  org.osgi.service.event.Event
 */
package org.idempiere.component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import org.adempiere.base.event.AbstractEventHandler;
import org.adempiere.base.event.LoginEventData;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MForecastLine;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MRMALine;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_M_Forecast;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.eevolution.model.MDDOrder;
import org.eevolution.model.MDDOrderLine;
import org.libero.model.MPPCostCollector;
import org.libero.model.MPPMRP;
import org.libero.model.MPPOrder;
import org.libero.model.MPPOrderBOMLine;
import org.osgi.service.event.Event;

public class MFG_Validator
extends AbstractEventHandler {
    private static CLogger log = CLogger.getCLogger(MFG_Validator.class);
    private String trxName = "";
    private PO po = null;
    private static final int X_RV_PP_Product_BOMLine_Table_ID = 1000000;
    private static final String X_RV_PP_Product_BOMLine_Table_Name = "RV_PP_SalesOrder";

    protected void initialize() {
        this.registerEvent("adempiere/afterLogin");
        this.registerTableEvent("adempiere/po/beforeNew", "M_Movement");
        this.registerTableEvent("adempiere/po/afterNew", "C_Order");
        this.registerTableEvent("adempiere/po/afterNew", "C_OrderLine");
        this.registerTableEvent("adempiere/po/afterNew", "M_Requisition");
        this.registerTableEvent("adempiere/po/afterNew", "M_RequisitionLine");
        this.registerTableEvent("adempiere/po/afterNew", "M_Forecast");
        this.registerTableEvent("adempiere/po/afterNew", "M_ForecastLine");
        this.registerTableEvent("adempiere/po/afterNew", "DD_Order");
        this.registerTableEvent("adempiere/po/afterNew", "DD_OrderLine");
        this.registerTableEvent("adempiere/po/afterNew", "PP_Order");
        this.registerTableEvent("adempiere/po/afterNew", "PP_Order_BOMLine");
        this.registerTableEvent("adempiere/po/afterNew", "C_Order");
        this.registerTableEvent("adempiere/po/afterNew", "C_Order");
        this.registerTableEvent("adempiere/po/beforeChange", "M_Product");
        this.registerTableEvent("adempiere/po/afterChange", "C_Order");
        this.registerTableEvent("adempiere/po/afterChange", "C_OrderLine");
        this.registerTableEvent("adempiere/po/afterChange", "M_Requisition");
        this.registerTableEvent("adempiere/po/afterChange", "M_RequisitionLine");
        this.registerTableEvent("adempiere/po/afterChange", "M_Forecast");
        this.registerTableEvent("adempiere/po/afterChange", "M_ForecastLine");
        this.registerTableEvent("adempiere/po/afterChange", "DD_Order");
        this.registerTableEvent("adempiere/po/afterChange", "DD_OrderLine");
        this.registerTableEvent("adempiere/po/afterChange", "PP_Order");
        this.registerTableEvent("adempiere/po/afterChange", "PP_Order_BOMLine");
        this.registerTableEvent("adempiere/po/afterChange", "M_Forecast");
        this.registerTableEvent("adempiere/po/afterChange", "M_ForecastLine");
        this.registerTableEvent("adempiere/po/beforeDelete", "C_Order");
        this.registerTableEvent("adempiere/po/beforeDelete", "C_OrderLine");
        this.registerTableEvent("adempiere/po/beforeDelete", "M_Requisition");
        this.registerTableEvent("adempiere/po/beforeDelete", "M_RequisitionLine");
        this.registerTableEvent("adempiere/po/beforeDelete", "M_Forecast");
        this.registerTableEvent("adempiere/po/beforeDelete", "M_ForecastLine");
        this.registerTableEvent("adempiere/po/beforeDelete", "DD_Order");
        this.registerTableEvent("adempiere/po/beforeDelete", "DD_OrderLine");
        this.registerTableEvent("adempiere/po/beforeDelete", "PP_Order");
        this.registerTableEvent("adempiere/po/beforeDelete", "PP_Order_BOMLine");
        this.registerTableEvent("adempiere/po/beforeDelete", "M_Forecast");
        this.registerTableEvent("adempiere/po/beforeDelete", "M_ForecastLine");
        this.registerTableEvent("adempiere/doc/beforePrepare", "M_Forecast");
        this.registerTableEvent("adempiere/doc/beforeComplete", "M_ForecastLine");
        this.registerTableEvent("adempiere/doc/afterComplete", "M_Movement");
        this.registerTableEvent("adempiere/doc/afterComplete", "M_InOut");
        this.registerTableEvent("adempiere/doc/afterComplete", "C_Order");
        this.registerTableEvent("adempiere/doc/beforeReactivate", "DD_Order");
        this.registerTableEvent("adempiere/doc/afterComplete", "PP_Order");
        log.info("MFG MODEL VALIDATOR IS NOW INITIALIZED");
    }

    protected void doHandleEvent(Event event) {
        String type = event.getTopic();
        DocAction doc = null;
        boolean isDelete = false;
        boolean isReleased = false;
        boolean isVoided = false;
        boolean isChange = false;
        if (type.equals("adempiere/afterLogin")) {
            LoginEventData eventData = (LoginEventData)this.getEventData(event);
            log.fine(" topic=" + event.getTopic() + " AD_Client_ID=" + eventData.getAD_Client_ID() + " AD_Org_ID=" + eventData.getAD_Org_ID() + " AD_Role_ID=" + eventData.getAD_Role_ID() + " AD_User_ID=" + eventData.getAD_User_ID());
        } else {
            MMovement move;
            X_M_Forecast fl;
            Object order;
            this.setPo(this.getPO(event));
            this.setTrxName(this.po.get_TrxName());
            log.info(" topic=" + event.getTopic() + " po=" + (Object)this.po);
            isChange = "adempiere/po/afterNew" == type || "adempiere/po/afterChange" == type && MPPMRP.isChanged(this.po);
            isDelete = "adempiere/po/beforeDelete" == type;
            isReleased = false;
            isVoided = false;
            if (this.po instanceof DocAction) {
                doc = (DocAction)this.po;
            } else if (this.po instanceof MOrderLine) {
                doc = ((MOrderLine)this.po).getParent();
            }
            if (doc != null) {
                String docStatus = doc.getDocStatus();
                isReleased = "IP".equals(docStatus) || "CO".equals(docStatus);
                isVoided = "VO".equals(docStatus);
            }
            if (this.po instanceof MProduct && "adempiere/po/beforeChange" == type && this.po.is_ValueChanged("C_UOM_ID") && MPPMRP.hasProductRecords((MProduct)this.po)) {
                throw new AdempiereException("@SaveUomError@");
            }
            if (isDelete || isVoided || !this.po.isActive()) {
                this.logEvent(event, this.po, type);
                MPPMRP.deleteMRP(this.po);
            } else if (this.po instanceof MOrder) {
                order = (MOrder)this.po;
                if (isChange && !order.isSOTrx()) {
                    this.logEvent(event, this.po, type);
                    MPPMRP.C_Order(order);
                } else if (type == "adempiere/po/afterChange" && order.isSOTrx() && (isReleased || MPPMRP.isChanged((PO)order))) {
                    this.logEvent(event, this.po, type);
                    MPPMRP.C_Order(order);
                }
            } else if (this.po instanceof MOrderLine && isChange) {
                MOrderLine ol = (MOrderLine)this.po;
                MOrder order2 = ol.getParent();
                if (!order2.isSOTrx()) {
                    this.logEvent(event, this.po, type);
                    MPPMRP.C_OrderLine(ol);
                } else if (order2.isSOTrx() && isReleased) {
                    System.out.println("sdsdsf");
                    this.logEvent(event, this.po, type);
                }
            } else if (this.po instanceof MRequisition && isChange) {
                MRequisition r = (MRequisition)this.po;
                this.logEvent(event, this.po, type);
                log.warning(event.getTopic());
                MPPMRP.M_Requisition(r);
            } else if (this.po instanceof MRequisitionLine && isChange) {
                MRequisitionLine rl = (MRequisitionLine)this.po;
                this.logEvent(event, this.po, type);
                MPPMRP.M_RequisitionLine(rl);
            } else if (this.po instanceof X_M_Forecast && isChange) {
                fl = (X_M_Forecast)this.po;
                this.logEvent(event, this.po, type);
                MPPMRP.M_Forecast(fl);
            } else if (this.po instanceof MForecastLine && isChange) {
                fl = (MForecastLine)this.po;
                this.logEvent(event, this.po, type);
                MPPMRP.M_ForecastLine((MForecastLine)fl);
                MPPMRP.createMOFromForecast((MForecastLine)fl, fl.getQty());
            } else if (this.po instanceof MPPOrder && isChange) {
                order = (MPPOrder)this.po;
                this.logEvent(event, this.po, type);
                System.out.println("afagagag");
                MPPMRP.PP_Order((MPPOrder)((Object)order));
            } else if (this.po instanceof MPPOrderBOMLine && isChange) {
                MPPOrderBOMLine obl = (MPPOrderBOMLine)this.po;
                this.logEvent(event, this.po, type);
                MPPMRP.PP_Order_BOMLine(obl);
            }
            if (event.getTopic().equals("adempiere/po/afterNew")) {
                this.po = this.getPO(event);
                log.info(" topic=" + event.getTopic() + " po=" + (Object)this.po);
            } else if (event.getTopic().equals("adempiere/po/beforeChange")) {
                this.po = this.getPO(event);
                log.info(" topic=" + event.getTopic() + " po=" + (Object)this.po);
                if (this.po.get_TableName().equals("M_Product")) {
                    this.logEvent(event, this.po, type);
                }
            }
            if (this.po instanceof MInOut && type == "adempiere/doc/afterComplete") {
                this.logEvent(event, this.po, type);
                MInOut inout = (MInOut)this.po;
                if (inout.isSOTrx()) {
                    for (MInOutLine outline : inout.getLines()) {
                        this.updateMPPOrder(outline);
                    }
                } else {
                    for (MInOutLine line : inout.getLines()) {
                        List olines = new Query(this.po.getCtx(), "C_OrderLine", "C_OrderLine_ID=? AND PP_Cost_Collector_ID IS NOT NULL", this.trxName).setParameters(new Object[]{line.getC_OrderLine_ID()}).list();
                        for (MOrderLine oline : olines) {
                            if (oline.getQtyOrdered().compareTo(oline.getQtyDelivered()) < 0) continue;
                            MPPCostCollector cc = new MPPCostCollector(this.po.getCtx(), oline.getPP_Cost_Collector_ID(), this.trxName);
                            String docStatus = cc.completeIt();
                            cc.setDocStatus(docStatus);
                            cc.setDocAction("CL");
                            cc.saveEx(this.trxName);
                            return;
                        }
                    }
                }
            } else if (this.po instanceof MMovement && type == "adempiere/doc/afterReverseCorrect") {
                this.logEvent(event, this.po, type);
                move = (MMovement)this.po;
                MFG_Validator.updateDDOrderQtyInOutBoundReverse(move);
            } else if (this.po instanceof MMovement && type == "adempiere/doc/afterComplete") {
                this.logEvent(event, this.po, type);
                move = (MMovement)this.po;
                MFG_Validator.updateDDOrderQtyInOutBound(move);
            } else if (this.po instanceof MDDOrder && type == "adempiere/doc/beforeReactivate") {
                this.logEvent(event, this.po, type);
                MDDOrder ddorder = (MDDOrder)this.po;
                System.out.println("sfafafafa" + ddorder.getDocumentNo());
                MFG_Validator.checkActiveInOutBound(ddorder);
            } else if (this.po instanceof MPPOrder && type == "adempiere/doc/afterComplete") {
                this.logEvent(event, this.po, type);
                MPPOrder cfr_ignored_0 = (MPPOrder)this.po;
            }
        }
    }

    private boolean isInTransit(MDDOrder order) {
        for (MDDOrderLine line : order.getLines(true, null)) {
            if (line.getQtyInTransit().signum() == 0) continue;
            return true;
        }
        return false;
    }

    private void updateMPPOrder(MInOutLine outline) {
        MPPOrder order = null;
        BigDecimal qtyShipment = Env.ZERO;
        MInOut inout = outline.getParent();
        String movementType = inout.getMovementType();
        int C_OrderLine_ID = 0;
        if ("C-".equals(movementType)) {
            C_OrderLine_ID = outline.getC_OrderLine_ID();
            qtyShipment = outline.getMovementQty();
        } else if ("C+".equals(movementType)) {
            MRMALine rmaline = new MRMALine(outline.getCtx(), outline.getM_RMALine_ID(), null);
            MInOutLine line = (MInOutLine)rmaline.getM_InOutLine();
            C_OrderLine_ID = line.getC_OrderLine_ID();
            qtyShipment = outline.getMovementQty().negate();
        }
        order = (MPPOrder)new Query(outline.getCtx(), "PP_Order", " C_OrderLine_ID = ?  AND DocStatus IN  (?,?) AND EXISTS (SELECT 1 FROM  PP_Order_BOM  WHERE PP_Order_BOM.PP_Order_ID=PP_Order.PP_Order_ID AND PP_Order_BOM.BOMType =? )", outline.get_TrxName()).setParameters(new Object[]{C_OrderLine_ID, "IP", "CO", "K"}).firstOnly();
        if (order == null) {
            return;
        }
        if ("IP".equals(order.getDocStatus())) {
            order.completeIt();
            order.setDocStatus("CO");
            order.setDocAction("CL");
            order.saveEx(this.trxName);
        }
        if ("CO".equals(order.getDocStatus())) {
            String description = order.getDescription() != null ? order.getDescription() : Msg.translate((Properties)inout.getCtx(), (String)"M_InOut_ID") + " : " + Msg.translate((Properties)inout.getCtx(), (String)"DocumentNo");
            order.setDescription(description);
            order.updateMakeToKit(qtyShipment);
            order.saveEx(this.trxName);
        }
        if (order.getQtyToDeliver().compareTo(Env.ZERO) == 0) {
            order.closeIt();
            order.setDocStatus("CL");
            order.setDocAction("--");
            order.saveEx(this.trxName);
        }
    }

    private void logEvent(Event event, PO po, String msg) {
        log.info("LiberoMFG >> ModelValidator // " + event.getTopic() + " po=" + (Object)po + " MESSAGE =" + msg);
    }

    private void setPo(PO eventPO) {
        this.po = eventPO;
    }

    private void setTrxName(String get_TrxName) {
        this.trxName = get_TrxName;
    }

    private static String updateDDOrderQtyInOutBound(MMovement move) {
        boolean isOutbound = false;
        boolean isInbound = false;
        if (move.get_ValueAsBoolean("IsOutbound")) {
            isOutbound = true;
        } else if (move.get_ValueAsBoolean("IsInbound")) {
            isInbound = true;
        }
        if (isOutbound || isInbound) {
            MMovementLine[] moveLines;
            MMovementLine[] arrmMovementLine = moveLines = move.getLines(false);
            int n = moveLines.length;
            for (int i = 0; i < n; ++i) {
                String jml;
                MMovementLine moveLine = arrmMovementLine[i];
                int DD_OrderLine_ID = moveLine.get_ValueAsInt("DD_OrderLine_ID");
                if (DD_OrderLine_ID <= 0) {
                    return "Bound move line : " + moveLine.get_Value("Line") + " have no referenced DD_OrderLine_ID";
                }
                MDDOrderLine ddLine = new MDDOrderLine(move.getCtx(), DD_OrderLine_ID, move.get_TrxName());
                if (isOutbound && moveLine.getMovementQty().compareTo(BigDecimal.ZERO) > 0) {
                    jml = ddLine.get_ValueAsString("qtyoutbound");
                    BigDecimal qtyDDOutBound = new BigDecimal(jml);
                    qtyDDOutBound = qtyDDOutBound.add(moveLine.getMovementQty());
                    ddLine.set_ValueOfColumn("qtyoutbound", (Object)qtyDDOutBound);
                } else if (isInbound && moveLine.getMovementQty().compareTo(BigDecimal.ZERO) > 0) {
                    jml = ddLine.get_ValueAsString("qtyinbound");
                    BigDecimal qtyDDInBound = new BigDecimal(jml);
                    qtyDDInBound = qtyDDInBound.add(moveLine.getMovementQty());
                    ddLine.set_ValueOfColumn("qtyinbound", (Object)qtyDDInBound);
                }
                ddLine.saveEx(move.get_TrxName());
            }
        }
        return "";
    }

    private static String updateDDOrderQtyInOutBoundReverse(MMovement move) {
        boolean isOutbound = false;
        boolean isInbound = false;
        if (move.get_ValueAsBoolean("IsOutbound")) {
            isOutbound = true;
        } else if (move.get_ValueAsBoolean("IsInbound")) {
            isInbound = true;
        }
        if (isOutbound || isInbound) {
            MMovementLine[] moveLines;
            MMovementLine[] arrmMovementLine = moveLines = move.getLines(false);
            int n = moveLines.length;
            for (int i = 0; i < n; ++i) {
                String jml;
                MMovementLine moveLine = arrmMovementLine[i];
                int DD_OrderLine_ID = moveLine.get_ValueAsInt("DD_OrderLine_ID");
                if (DD_OrderLine_ID <= 0) {
                    return "Bound move line : " + moveLine.get_Value("Line") + " have no referenced DD_OrderLine_ID";
                }
                MDDOrderLine ddLine = new MDDOrderLine(move.getCtx(), DD_OrderLine_ID, move.get_TrxName());
                if (isOutbound && moveLine.getMovementQty().compareTo(BigDecimal.ZERO) > 0) {
                    jml = ddLine.get_ValueAsString("QtyOutbound");
                    BigDecimal qtyDDOutBound = new BigDecimal(jml);
                    qtyDDOutBound = qtyDDOutBound.add(moveLine.getMovementQty().negate());
                    ddLine.set_ValueOfColumn("QtyOutbound", (Object)qtyDDOutBound);
                } else if (isInbound && moveLine.getMovementQty().compareTo(BigDecimal.ZERO) > 0) {
                    jml = ddLine.get_ValueAsString("qtyinbound");
                    BigDecimal qtyDDInBound = new BigDecimal(jml);
                    qtyDDInBound = qtyDDInBound.add(moveLine.getMovementQty().negate());
                    ddLine.set_ValueOfColumn("QtyInbound", (Object)qtyDDInBound);
                }
                ddLine.saveEx(move.get_TrxName());
            }
        }
        return "";
    }

    private static String checkActiveInOutBound(MDDOrder ddOrder) {
        String sqlWhere = "DD_Order_ID=" + ddOrder.getDD_Order_ID() + " AND DocStatus IN ('CO','CL') AND AD_Client_ID=" + ddOrder.getAD_Client_ID();
        boolean match = new Query(ddOrder.getCtx(), "M_Movement", sqlWhere, ddOrder.get_TrxName()).match();
        if (match) {
            throw new AdempiereException("Active OutBound Or InBound Exist");
        }
        return "";
    }
}

