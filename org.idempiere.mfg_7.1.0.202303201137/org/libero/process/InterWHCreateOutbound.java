/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.compiere.model.MDocType
 *  org.compiere.model.MLocator
 *  org.compiere.model.MMovement
 *  org.compiere.model.MMovementLine
 *  org.compiere.model.MOrg
 *  org.compiere.model.MWarehouse
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.DB
 *  org.compiere.util.Msg
 *  org.eevolution.model.MDDOrder
 *  org.eevolution.model.MDDOrderLine
 */
package org.libero.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MLocator;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.MOrg;
import org.compiere.model.MWarehouse;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.eevolution.model.MDDOrder;
import org.eevolution.model.MDDOrderLine;

public class InterWHCreateOutbound
extends SvrProcess {
    private int p_Locator = 0;
    private int p_C_DocType_ID = 0;
    private int p_DD_Order_ID = 0;
    private Timestamp p_MovementDate = null;

    protected void prepare() {
        ProcessInfoParameter[] para = this.getParameter();
        for (int i = 0; i < para.length; ++i) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) continue;
            if (name.equals("M_Locator_ID")) {
                this.p_Locator = para[i].getParameterAsInt();
                continue;
            }
            if (name.equals("C_DocType_ID")) {
                this.p_C_DocType_ID = para[i].getParameterAsInt();
                continue;
            }
            if (name.equals("DD_Order_ID")) {
                this.p_DD_Order_ID = para[i].getParameterAsInt();
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
        if (this.p_MovementDate == null) {
            return "Error: No Movement Date";
        }
        MDDOrder interWH = new MDDOrder(this.getCtx(), this.p_DD_Order_ID, this.get_TrxName());
        if (!interWH.getDocStatus().equals("CO")) {
            return "Error: Only Completed Inter-warehouse Document Can be Processed";
        }
        if (this.p_C_DocType_ID <= 0) {
            return "Error: No Document Type Selected for Inbound Movement";
        }
        MDocType docType = new MDocType(this.getCtx(), this.p_C_DocType_ID, this.get_TrxName());
        if (!docType.getDocBaseType().equals("MMM")) {
            return "Error: Selected Document Type Is Not Material Movement";
        }
        if (this.p_Locator <= 0) {
            return "Error: No Source Locator Selected";
        }
        MLocator locator = new MLocator(this.getCtx(), this.p_Locator, this.get_TrxName());
        if (locator.getM_Warehouse_ID() != interWH.getM_Warehouse_ID()) {
            return "Error: Selected Locator Is Not in The Source Warehouse";
        }
        if (DB.getSQLValue((String)this.get_TrxName(), (String)("select count(*) from m_movement where dd_order_id = " + this.p_DD_Order_ID + " and docstatus in ('DR','IN') and IsOutbound = 'Y'")) > 0) {
            return "Error: Movement Created Draft ";
        }
        new MWarehouse(this.getCtx(), interWH.getM_Warehouse_ID(), this.get_TrxName());
        MWarehouse whTo = new MWarehouse(this.getCtx(), interWH.get_ValueAsInt("M_WarehouseTo_ID"), this.get_TrxName());
        String sqlWHTransit = "SELECT M_Warehouse_ID FROM M_Warehouse WHERE IsIntransit='Y' AND IsActive='Y' AND AD_Org_ID=" + whTo.getAD_Org_ID();
        int M_WareHouse_InTransit_ID = DB.getSQLValue((String)this.get_TrxName(), (String)sqlWHTransit);
        if (M_WareHouse_InTransit_ID <= 0) {
            MOrg org = new MOrg(this.getCtx(), whTo.getAD_Org_ID(), this.get_TrxName());
            throw new AdempiereException("Warehouse.InTransit='Y' of organization " + org.getName() + " not exist");
        }
        MWarehouse whTransit = new MWarehouse(this.getCtx(), M_WareHouse_InTransit_ID, this.get_TrxName());
        String sqlLocatorTransit = "SELECT M_Locator_ID FROM M_Locator WHERE  IsActive='Y' AND M_Warehouse_ID=" + whTransit.getM_Warehouse_ID();
        int locator_InTransit_ID = DB.getSQLValue((String)this.get_TrxName(), (String)sqlLocatorTransit);
        if (locator_InTransit_ID <= 0) {
            throw new AdempiereException("Locator.IsIntransit='Y' of warehouse " + whTransit.getName() + " not exist");
        }
        int M_WarehouseTo_ID = whTransit.getM_Warehouse_ID();
        MMovement outbound = new MMovement(this.getCtx(), 0, this.get_TrxName());
        outbound.setAD_Org_ID(interWH.getAD_Org_ID());
        if (interWH.getAD_OrgTrx_ID() > 0) {
            outbound.setAD_OrgTrx_ID(interWH.getAD_OrgTrx_ID());
        }
        outbound.setMovementDate(this.p_MovementDate);
        outbound.setC_Project_ID(interWH.getC_Project_ID());
        outbound.setC_BPartner_ID(interWH.getC_BPartner_ID());
        outbound.setC_BPartner_Location_ID(interWH.getC_BPartner_Location_ID());
        outbound.setM_Shipper_ID(interWH.getM_Shipper_ID());
        outbound.setDocAction("CO");
        outbound.setDocStatus("DR");
        outbound.setC_DocType_ID(this.p_C_DocType_ID);
        outbound.set_ValueOfColumn("M_Warehouse_ID", (Object)interWH.getM_Warehouse_ID());
        outbound.set_ValueOfColumn("M_WarehouseTo_ID", (Object)M_WarehouseTo_ID);
        outbound.setDD_Order_ID(interWH.getDD_Order_ID());
        outbound.set_ValueOfColumn("IsOutbound", (Object)"Y");
        outbound.saveEx();
        MDDOrderLine[] lines = interWH.getLines();
        new MWarehouse(this.getCtx(), outbound.get_ValueAsInt("M_Warehouse_ID"), this.get_TrxName());
        MDDOrderLine[] arrmDDOrderLine = lines;
        int n = lines.length;
        for (int i = 0; i < n; ++i) {
            MDDOrderLine line = arrmDDOrderLine[i];
            String cfr_ignored_0 = "DD_OrderLine_ID=" + line.getDD_OrderLine_ID() + " AND IsOutbound='Y' AND DocStatus IN ('CO','CL')";
            String jml = line.get_ValueAsString("QtyOutbound");
            BigDecimal qtyEntOutbound = new BigDecimal(jml);
            if (line.getQtyEntered().subtract(qtyEntOutbound).compareTo(BigDecimal.ZERO) <= 0) continue;
            MMovementLine moveLine = new MMovementLine(outbound);
            moveLine.setLine(line.getLine());
            moveLine.setM_Product_ID(line.getM_Product_ID());
            moveLine.setMovementQty(line.getQtyEntered().subtract(qtyEntOutbound));
            if (line.getM_AttributeSetInstance_ID() > 0) {
                moveLine.setM_AttributeSetInstance_ID(line.getM_AttributeSetInstance_ID());
            }
            moveLine.setM_Locator_ID(this.p_Locator);
            moveLine.setM_LocatorTo_ID(locator_InTransit_ID);
            moveLine.set_ValueOfColumn("C_UOM_ID", (Object)line.getC_UOM_ID());
            moveLine.setDD_OrderLine_ID(line.getDD_OrderLine_ID());
            moveLine.saveEx();
        }
        outbound.saveEx();
        interWH.saveEx();
        String message = Msg.parseTranslation((Properties)this.getCtx(), (String)("@GeneratedOutbound@" + outbound.getDocumentNo()));
        this.addBufferLog(0, null, null, message, outbound.get_Table_ID(), outbound.getM_Movement_ID());
        return "Successfully Created Outbound Movement #" + outbound.getDocumentNo();
    }

    public boolean isDisallowNegativeInv(MWarehouse warehouse) {
        return warehouse.isDisallowNegativeInv();
    }
}

