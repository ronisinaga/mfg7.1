/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MDocType
 *  org.compiere.model.MLocator
 *  org.compiere.model.MMovement
 *  org.compiere.model.MMovementLine
 *  org.compiere.model.MWarehouse
 *  org.compiere.model.Query
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.Msg
 *  org.eevolution.model.MDDOrder
 */
package org.libero.process;

import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MDocType;
import org.compiere.model.MLocator;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.MWarehouse;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.eevolution.model.MDDOrder;

public class InterWHCreateInbound
extends SvrProcess {
    private int p_M_Warehouse_ID = 0;
    private int p_M_WarehouseZone_ID = 0;
    private int p_Locator = 0;
    private int p_C_DocType_ID = 0;
    private int p_DD_Order_ID = 0;
    private int p_M_MovementOutBound_ID = 0;
    private Timestamp p_MovementDate = null;

    protected void prepare() {
        ProcessInfoParameter[] para = this.getParameter();
        for (int i = 0; i < para.length; ++i) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) continue;
            if (name.equals("DD_Order_ID")) {
                this.p_DD_Order_ID = para[i].getParameterAsInt();
                continue;
            }
            if (name.equals("M_Warehouse_ID")) {
                this.p_M_Warehouse_ID = para[i].getParameterAsInt();
                continue;
            }
            if (name.equals("M_WarehouseZone_ID")) {
                this.p_M_WarehouseZone_ID = para[i].getParameterAsInt();
                continue;
            }
            if (name.equals("M_Locator_ID")) {
                this.p_Locator = para[i].getParameterAsInt();
                continue;
            }
            if (name.equals("C_DocType_ID")) {
                this.p_C_DocType_ID = para[i].getParameterAsInt();
                continue;
            }
            if (name.equals("M_Movement_ID")) {
                this.p_M_MovementOutBound_ID = para[i].getParameterAsInt();
                continue;
            }
            if (name.equals("MovementDate")) {
                this.p_MovementDate = para[i].getParameterAsTimestamp();
                continue;
            }
            this.log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
    }

    protected String doIt() throws Exception {
        if (this.p_DD_Order_ID <= 0) {
            return "Error: No Selected Inter-warehouse Document";
        }
        if (this.p_M_MovementOutBound_ID <= 0) {
            return "Error: No Outbound Document";
        }
        if (this.p_MovementDate == null) {
            return "Error: No Movement Date";
        }
        MDDOrder interWH = new MDDOrder(this.getCtx(), this.p_DD_Order_ID, this.get_TrxName());
        if (!interWH.getDocStatus().equals("CO")) {
            return "Error: Only Completed Inter-warehouse Document Can be Processed";
        }
        if (interWH.get_ValueAsInt("M_MovementIn_ID") > 0) {
            return "Error: Inbound Movement Has Been Created";
        }
        if (this.p_C_DocType_ID <= 0) {
            return "Error: No Document Type Selected for Inbound Movement";
        }
        MDocType docType = new MDocType(this.getCtx(), this.p_C_DocType_ID, this.get_TrxName());
        if (!docType.getDocBaseType().equals("MMM")) {
            return "Error: Selected Document Type Is Not Material Movement";
        }
        if (this.p_Locator <= 0) {
            return "Error: No Destination Locator Selected";
        }
        MLocator locator = new MLocator(this.getCtx(), this.p_Locator, this.get_TrxName());
        if (locator.getM_Warehouse_ID() != this.p_M_Warehouse_ID) {
            return "Error: Selected Locator Is Not in The Destination Warehouse";
        }
        boolean alreadyInbounded = new Query(this.getCtx(), "M_Movement", "M_OutBoundFrom_ID=" + this.p_M_MovementOutBound_ID + " AND DocStatus NOT IN ('VO','RE')", this.get_TrxName()).match();
        if (alreadyInbounded) {
            return "Error: This OutBound is already inbounded";
        }
        new MWarehouse(this.getCtx(), interWH.getM_Warehouse_ID(), this.get_TrxName());
        new MWarehouse(this.getCtx(), interWH.get_ValueAsInt("M_WarehouseTo_ID"), this.get_TrxName());
        MMovement outbound = new MMovement(this.getCtx(), this.p_M_MovementOutBound_ID, this.get_TrxName());
        MMovementLine[] lines = outbound.getLines(true);
        MMovement inbound = new MMovement(this.getCtx(), 0, this.get_TrxName());
        inbound.setAD_Org_ID(interWH.getAD_Org_ID());
        inbound.setMovementDate(this.p_MovementDate);
        inbound.setC_DocType_ID(this.p_C_DocType_ID);
        inbound.setDocStatus("DR");
        inbound.setDocAction("CO");
        inbound.setDD_Order_ID(interWH.getDD_Order_ID());
        inbound.setC_Project_ID(interWH.getC_Project_ID());
        inbound.setC_BPartner_ID(outbound.getC_BPartner_ID());
        inbound.setC_BPartner_Location_ID(outbound.getC_BPartner_Location_ID());
        inbound.setM_Shipper_ID(outbound.getM_Shipper_ID());
        inbound.setAD_User_ID(outbound.getAD_User_ID());
        inbound.set_ValueOfColumn("IsInbound", (Object)"Y");
        inbound.set_ValueOfColumn("M_OutBoundFrom_ID", (Object)this.p_M_MovementOutBound_ID);
        inbound.set_ValueOfColumn("kendaraan", outbound.get_Value("kendaraan"));
        inbound.set_ValueOfColumn("Pengirim", outbound.get_Value("Pengirim"));
        inbound.set_ValueOfColumn("driver", outbound.get_Value("driver"));
        inbound.set_ValueOfColumn("datesendmovement", outbound.get_Value("datesendmovement"));
        inbound.set_ValueOfColumn("datereceivedmovement", outbound.get_Value("datereceivedmovement"));
        inbound.set_ValueOfColumn("salesrep_id", outbound.get_Value("salesrep_id"));
        inbound.set_ValueOfColumn("c_order_id", outbound.get_Value("c_order_id"));
        inbound.set_ValueOfColumn("salesrep_id", outbound.get_Value("salesrep_id"));
        inbound.set_ValueOfColumn("salesrep_id", outbound.get_Value("salesrep_id"));
        inbound.saveEx();
        MMovementLine[] arrmMovementLine = lines;
        int n = lines.length;
        for (int i = 0; i < n; ++i) {
            MMovementLine line = arrmMovementLine[i];
            MMovementLine moveLine = new MMovementLine(inbound);
            moveLine.setLine(line.getLine());
            moveLine.setAD_Org_ID(interWH.getAD_Org_ID());
            moveLine.setM_Product_ID(line.getM_Product_ID());
            moveLine.set_ValueOfColumn("QtyEntered", line.get_Value("QtyEntered"));
            moveLine.setMovementQty(line.getMovementQty());
            moveLine.setM_Locator_ID(line.getM_LocatorTo_ID());
            moveLine.setM_LocatorTo_ID(this.p_Locator);
            moveLine.set_ValueOfColumn("C_UOM_ID", line.get_Value("C_UOM_ID"));
            moveLine.setDD_OrderLine_ID(line.getDD_OrderLine_ID());
            moveLine.set_ValueOfColumn("M_OutBoundLineFrom_ID", (Object)line.getM_MovementLine_ID());
            moveLine.setM_AttributeSetInstance_ID(line.getM_AttributeSetInstance_ID());
            moveLine.saveEx();
        }
        interWH.saveEx();
        String message = Msg.parseTranslation((Properties)this.getCtx(), (String)("@GeneratedInbound@" + inbound.getDocumentNo()));
        this.addBufferLog(0, null, null, message, inbound.get_Table_ID(), inbound.getM_Movement_ID());
        return "";
    }
}

