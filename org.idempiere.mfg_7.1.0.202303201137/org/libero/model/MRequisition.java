/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MRequisition
 *  org.compiere.model.MRequisitionLine
 *  org.compiere.model.MStorageReservation
 *  org.compiere.model.Query
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 */
package org.libero.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MStorageReservation;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.libero.model.MPPMRP;

public class MRequisition
extends org.compiere.model.MRequisition {
    private static final long serialVersionUID = 8844916612112952581L;
    private int C_BPartner_ID;
    private int M_Product_ID;
    private BigDecimal QtyPlanned;
    private int M_RequisitionLine_ID;

    public MRequisition(Properties ctx, int M_Requisition_ID, String trxName) {
        super(ctx, M_Requisition_ID, trxName);
    }

    private void setPriceList(int C_BPartner_ID) {
        this.C_BPartner_ID = C_BPartner_ID;
        int M_PriceList_ID = DB.getSQLValueEx((String)this.get_TrxName(), (String)"SELECT COALESCE(bp.PO_PriceList_ID,bpg.PO_PriceList_ID) FROM C_BPartner bp INNER JOIN C_BP_Group bpg ON (bpg.C_BP_Group_ID=bp.C_BP_Group_ID) WHERE bp.C_BPartner_ID=?", (Object[])new Object[]{C_BPartner_ID});
        if (M_PriceList_ID > 0) {
            this.setM_PriceList_ID(M_PriceList_ID);
        }
    }

    public void create(int PP_MRP_ID, BigDecimal QtyPlanned, int M_Product_ID, int C_BPartner, int AD_Org_ID, int AD_User_ID, Timestamp DateRequired, String description, int M_Warehouse_ID, int C_DocType_ID) {
        this.QtyPlanned = QtyPlanned;
        this.M_Product_ID = M_Product_ID;
        BigDecimal available = MStorageReservation.getQtyAvailable((int)M_Warehouse_ID, (int)M_Product_ID, (int)0, (String)this.get_TrxName());
        if (QtyPlanned.compareTo(available) < 1) {
            return;
        }
        QtyPlanned = QtyPlanned.subtract(available);
        this.setPriceList(C_BPartner);
        this.setAD_Org_ID(AD_Org_ID);
        this.setAD_User_ID(AD_User_ID);
        this.setDateRequired(DateRequired);
        this.setDescription(description);
        this.setM_Warehouse_ID(M_Warehouse_ID);
        this.setC_DocType_ID(C_DocType_ID);
        this.saveEx(this.get_TrxName());
        this.createLine();
    }

    private void createLine() {
        MRequisitionLine reqline = new MRequisitionLine((org.compiere.model.MRequisition)this);
        reqline.setLine(10);
        reqline.setAD_Org_ID(this.getAD_Org_ID());
        reqline.setC_BPartner_ID(this.C_BPartner_ID);
        reqline.setM_Product_ID(this.M_Product_ID);
        reqline.setPrice();
        reqline.setPriceActual(Env.ZERO);
        reqline.setQty(this.QtyPlanned);
        reqline.saveEx(this.get_TrxName());
        this.M_RequisitionLine_ID = reqline.get_ID();
    }

    private void setCorrectDates() {
        List mrpList = new Query(this.getCtx(), "PP_MRP", "M_Requisition_ID=?", this.get_TrxName()).setParameters(new Object[]{this.getM_Requisition_ID()}).list();
        for (MPPMRP mrp : mrpList) {
            mrp.setDatePromised(this.getDateRequired());
            mrp.setM_RequisitionLine_ID(this.M_RequisitionLine_ID);
            mrp.saveEx(this.get_TrxName());
        }
    }
}

