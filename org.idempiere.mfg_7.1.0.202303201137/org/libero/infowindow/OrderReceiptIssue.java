/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MStorageOnHand
 *  org.compiere.model.Query
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.Env
 */
package org.libero.infowindow;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.libero.model.MPPMRP;
import org.libero.model.MPPOrder;
import org.libero.model.MPPOrderBOMLine;

public class OrderReceiptIssue
extends SvrProcess {
    private String p_DeliveryRule;
    private boolean p_BackFlushGroup;
    private Timestamp p_MovementDate;
    private boolean firsttime = true;
    private int PP_Order_ID;
    private Timestamp minGuaranteeDate;
    private Timestamp movementDate;
    private BigDecimal qtyToDeliver;
    private BigDecimal qtyScrapComponent;

    protected void prepare() {
        ProcessInfoParameter[] para = this.getParameter();
        for (int i = 0; i < para.length; ++i) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) continue;
            if (name.equals("DeliveryRule")) {
                this.p_DeliveryRule = para[i].getParameterAsString();
                continue;
            }
            if (name.equals("BackFlushGroup")) {
                this.p_BackFlushGroup = para[i].getParameterAsBoolean();
                continue;
            }
            if (!name.equals("MovementDate")) continue;
            this.p_MovementDate = para[i].getParameterAsTimestamp();
        }
    }

    protected String doIt() throws Exception {
        String whereClause = "EXISTS (SELECT T_Selection_ID FROM T_Selection WHERE  T_Selection.AD_PInstance_ID=? AND T_Selection.T_Selection_ID=PP_MRP.PP_MRP_ID)";
        List mrpset = new Query(Env.getCtx(), "PP_MRP", whereClause, this.get_TrxName()).setParameters(new Object[]{this.getAD_PInstance_ID()}).list();
        for (MPPMRP mrp : mrpset) {
            if (this.firsttime) {
                this.PP_Order_ID = mrp.getPP_Order_ID();
                this.firsttime = false;
            }
            MPPOrderBOMLine bomline = (MPPOrderBOMLine)new Query(Env.getCtx(), "PP_Order_BOMLine", "PP_Order_ID=?", this.get_TrxName()).setParameters(new Object[]{mrp.getPP_Order_ID()}).first();
            if (this.p_DeliveryRule.equals("BackFlush") || this.p_DeliveryRule.equals("OnlyIssue")) {
                this.createIssue(bomline);
            }
            if (!this.p_DeliveryRule.equals("BackFlush") && !this.p_DeliveryRule.equals("OnlyReceipt")) continue;
            this.createReceipt(bomline);
        }
        return null;
    }

    private void createReceipt(MPPOrderBOMLine bomline) {
        new MPPOrder(Env.getCtx(), bomline.getPP_Order_ID(), bomline.get_TrxName());
    }

    private void createIssue(MPPOrderBOMLine bomline) {
        MPPOrder mo = new MPPOrder(Env.getCtx(), bomline.getPP_Order_ID(), bomline.get_TrxName());
        int M_Product_ID = bomline.getM_Product_ID();
        int PP_Order_BOMLine_ID = bomline.getPP_Order_BOMLine_ID();
        int M_AttributeSetInstance_ID = bomline.getM_AttributeSetInstance_ID();
        MStorageOnHand[] storages = MPPOrder.getStorages(Env.getCtx(), M_Product_ID, mo.getM_Warehouse_ID(), M_AttributeSetInstance_ID, this.minGuaranteeDate, bomline.get_TrxName());
        MPPOrder.createIssue(mo, PP_Order_BOMLine_ID, this.movementDate, this.qtyToDeliver, this.qtyScrapComponent, Env.ZERO, storages, false);
    }
}

