/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.Query
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.DB
 */
package org.libero.process;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.libero.model.MPPCostCollector;
import org.libero.model.MPPOrder;

public class ProsesVoidManufacturing
extends SvrProcess {
    private int p_PP_Cost_Collector_ID = 0;

    protected void prepare() {
        ProcessInfoParameter[] para = this.getParameter();
        for (int i = 0; i < para.length; ++i) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) continue;
            if (name.equals("PP_Cost_Collector_ID")) {
                this.p_PP_Cost_Collector_ID = ((BigDecimal)para[i].getParameter()).intValue();
                continue;
            }
            this.log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
    }

    protected String doIt() throws Exception {
        if (this.p_PP_Cost_Collector_ID > 0) {
            List list = new Query(this.getCtx(), "PP_Cost_Collector", " docstatus = 'CO' and pp_order_id = " + this.getRecord_ID() + " and description = '" + DB.getSQLValueString(null, (String)"select description from pp_cost_collector where pp_cost_collector_id = ?", (int)this.p_PP_Cost_Collector_ID) + "'", this.get_TrxName()).list();
            for (MPPCostCollector mppCostCollector : list) {
                mppCostCollector.voidIt();
                mppCostCollector.setDocStatus("VO");
                mppCostCollector.setDocAction("VO");
                mppCostCollector.saveEx(this.get_TrxName());
            }
        } else {
            MPPOrder mppOrder = new MPPOrder(this.getCtx(), this.getRecord_ID(), this.get_TrxName());
            List list = new Query(this.getCtx(), "PP_Cost_Collector", " PP_Order_ID = " + mppOrder.getPP_Order_ID(), this.get_TrxName()).list();
            for (MPPCostCollector mppCostCollector : list) {
                mppCostCollector.voidIt();
                mppCostCollector.setDocStatus("VO");
                mppCostCollector.setDocAction("VO");
                mppCostCollector.saveEx(this.get_TrxName());
            }
        }
        return null;
    }
}

