/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MOrder
 *  org.compiere.model.MOrderLine
 *  org.compiere.model.MProduct
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.eevolution.model.MPPProductBOM
 *  org.eevolution.model.MPPProductBOMLine
 *  org.eevolution.model.MPPProductPlanning
 */
package org.libero.process;

import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.eevolution.model.MPPProductPlanning;

public class CalculateProductPlanningFromSalesOrder
extends SvrProcess {
    private int p_AD_Workflow_ID = 0;
    private int count_created = 0;
    private int count_updated = 0;
    private int count_error = 0;
    MOrder order = null;

    protected void prepare() {
        for (ProcessInfoParameter para : this.getParameter()) {
            String name = para.getParameterName();
            if (para.getParameter() == null) continue;
            if (name.equals("AD_Workflow_ID")) {
                this.p_AD_Workflow_ID = para.getParameterAsInt();
                continue;
            }
            this.log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
        this.order = new MOrder(this.getCtx(), this.getRecord_ID(), this.get_TrxName());
    }

    protected String doIt() throws Exception {
        for (MOrderLine orderLine : this.order.getLines()) {
            MProduct M_Product = MProduct.get((Properties)this.getCtx(), (int)orderLine.getM_Product_ID());
            for (MPPProductBOM bom : MPPProductBOM.getProductBOMs((MProduct)M_Product)) {
                this.createPlanning(M_Product.getM_Product_ID(), this.order, bom);
                this.parent(bom);
            }
        }
        return "@Created@ #" + this.count_created + " @Updated@ #" + this.count_updated + " @Error@ #" + this.count_error;
    }

    public void parent(MPPProductBOM bom) {
        for (MPPProductBOMLine bomline : bom.getLines()) {
            MProduct component = MProduct.get((Properties)this.getCtx(), (int)bomline.getM_Product_ID());
            this.createPlanning(component.getM_Product_ID(), this.order, bom);
            this.component(component);
        }
    }

    public void component(MProduct product) {
        for (MPPProductBOM bom : MPPProductBOM.getProductBOMs((MProduct)product)) {
            this.parent(bom);
        }
    }

    private void createPlanning(int M_Product_ID, MOrder order, MPPProductBOM bom) {
        boolean isNew;
        MPPProductPlanning pp = MPPProductPlanning.get((Properties)this.getCtx(), (int)this.getAD_Client_ID(), (int)order.getAD_Org_ID(), (int)order.getM_Warehouse_ID(), (int)1000000, (int)M_Product_ID, (String)this.get_TrxName());
        boolean bl = isNew = pp == null;
        if (pp == null) {
            pp = new MPPProductPlanning(this.getCtx(), 0, this.get_TrxName());
            pp.setAD_Org_ID(order.getAD_Org_ID());
            pp.setM_Warehouse_ID(order.getM_Warehouse_ID());
            pp.setS_Resource_ID(1000000);
            pp.setM_Product_ID(M_Product_ID);
        }
        pp.setDD_NetworkDistribution_ID(0);
        pp.setAD_Workflow_ID(this.p_AD_Workflow_ID);
        pp.setPP_Product_BOM_ID(bom.getPP_Product_BOM_ID());
        pp.setIsCreatePlan(true);
        pp.setIsMPS(false);
        pp.setIsRequiredMRP(true);
        pp.setIsRequiredDRP(true);
        pp.setPlanner_ID(this.getAD_User_ID());
        pp.setOrder_Policy("LFL");
        pp.setIsPhantom(false);
        if (!pp.save()) {
            ++this.count_error;
        }
        if (isNew) {
            ++this.count_created;
        } else {
            ++this.count_updated;
        }
    }
}

