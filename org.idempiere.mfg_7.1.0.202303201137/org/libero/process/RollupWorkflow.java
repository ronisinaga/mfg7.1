/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_WF_Node
 *  org.compiere.model.I_M_CostElement
 *  org.compiere.model.MAcctSchema
 *  org.compiere.model.MCost
 *  org.compiere.model.MCostElement
 *  org.compiere.model.MProduct
 *  org.compiere.model.Query
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.Env
 *  org.compiere.wf.MWFNode
 *  org.compiere.wf.MWorkflow
 *  org.eevolution.model.MPPProductPlanning
 */
package org.libero.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.model.engines.CostDimension;
import org.adempiere.model.engines.CostEngine;
import org.adempiere.model.engines.CostEngineFactory;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MCostElement;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.wf.MWFNode;
import org.compiere.wf.MWorkflow;
import org.eevolution.model.MPPProductPlanning;
import org.libero.model.RoutingService;
import org.libero.model.RoutingServiceFactory;

public class RollupWorkflow
extends SvrProcess {
    private int p_AD_Org_ID = 0;
    private int p_C_AcctSchema_ID = 0;
    private int p_M_CostType_ID = 0;
    private int p_M_Product_ID = 0;
    private int p_M_Product_Category_ID = 0;
    private String p_ConstingMethod = "S";
    private MAcctSchema m_as = null;
    private RoutingService m_routingService = null;
    private int p_S_Resource_ID;
    private int p_M_Warehouse_ID;
    private int p_workflow_ID;

    protected void prepare() {
        for (ProcessInfoParameter para : this.getParameter()) {
            String name = para.getParameterName();
            if (para.getParameter() == null) continue;
            if (name.equals("AD_Org_ID")) {
                this.p_AD_Org_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("C_AcctSchema_ID")) {
                this.p_C_AcctSchema_ID = para.getParameterAsInt();
                this.m_as = MAcctSchema.get((Properties)this.getCtx(), (int)this.p_C_AcctSchema_ID);
                continue;
            }
            if (name.equals("M_CostType_ID")) {
                this.p_M_CostType_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("CostingMethod")) {
                this.p_ConstingMethod = (String)para.getParameter();
                continue;
            }
            if (name.equals("M_Product_ID")) {
                this.p_M_Product_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("M_Product_Category_ID")) {
                this.p_M_Product_Category_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("S_Resource_ID")) {
                this.p_S_Resource_ID = para.getParameter() == null ? null : Integer.valueOf(para.getParameterAsInt());
                continue;
            }
            if (name.equals("M_Warehouse_ID")) {
                this.p_M_Warehouse_ID = para.getParameter() == null ? null : Integer.valueOf(para.getParameterAsInt());
                continue;
            }
            if (name.equals("AD_Workflow_ID")) {
                this.p_workflow_ID = para.getParameterAsInt();
                continue;
            }
            this.log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
    }

    protected String doIt() throws Exception {
        this.m_routingService = RoutingServiceFactory.get().getRoutingService(this.getAD_Client_ID());
        for (MProduct product : this.getProducts()) {
            System.out.println(product.getM_Product_ID());
            this.log.info("Product: " + (Object)product);
            int AD_Workflow_ID = 0;
            MPPProductPlanning pp = null;
            if (AD_Workflow_ID <= 0) {
                AD_Workflow_ID = MWorkflow.getWorkflowSearchKey((MProduct)product);
            }
            if (AD_Workflow_ID <= 0) {
                pp = MPPProductPlanning.find((Properties)this.getCtx(), (int)this.p_AD_Org_ID, (int)this.p_M_Warehouse_ID, (int)this.p_S_Resource_ID, (int)product.get_ID(), (String)this.get_TrxName());
                if (pp != null) {
                    AD_Workflow_ID = pp.getAD_Workflow_ID();
                } else {
                    this.createNotice(product, "@NotFound@ @PP_Product_Planning_ID@");
                }
            }
            if (AD_Workflow_ID <= 0) {
                this.createNotice(product, "@NotFound@ @AD_Workflow_ID@");
                continue;
            }
            System.out.println("swfsfs" + AD_Workflow_ID);
            MWorkflow workflow = new MWorkflow(this.getCtx(), AD_Workflow_ID, this.get_TrxName());
            this.rollup(product, workflow);
            if (pp == null) continue;
            pp.setYield(workflow.getYield());
            pp.saveEx();
        }
        return "@OK@";
    }

    private Collection<MProduct> getProducts() {
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuffer whereClause = new StringBuffer("AD_Client_ID=?");
        params.add(this.getAD_Client_ID());
        whereClause.append(" AND (").append("ProductType").append("=?");
        params.add("I");
        whereClause.append(" OR ").append("ProductType").append("=?");
        params.add("R");
        whereClause.append(") AND ").append("IsBOM").append("=?");
        params.add(true);
        if (this.p_M_Product_ID > 0) {
            whereClause.append(" AND ").append("M_Product_ID").append("=?");
            params.add(this.p_M_Product_ID);
        } else if (this.p_M_Product_Category_ID > 0) {
            whereClause.append(" AND ").append("M_Product_Category_ID").append("=?");
            params.add(this.p_M_Product_Category_ID);
        }
        List products = new Query(this.getCtx(), "M_Product", whereClause.toString(), this.get_TrxName()).setOrderBy("LowLevel").setParameters(params).list();
        return products;
    }

    public void rollup(MProduct product, MWorkflow workflow) {
        MWFNode node;
        MWFNode[] nodes;
        this.log.info("Workflow: " + (Object)workflow);
        workflow.setCost(Env.ZERO);
        double Yield = 1.0;
        int QueuingTime = 0;
        int SetupTime = 0;
        int Duration = 0;
        int WaitingTime = 0;
        int jmltenagakerja = 0;
        int MovingTime = 0;
        int WorkingTime = 0;
        MWFNode[] arrmWFNode = nodes = workflow.getNodes(false, this.getAD_Client_ID());
        int n = nodes.length;
        for (int i = 0; i < n; ++i) {
            node = arrmWFNode[i];
            System.out.println("node" + node.get_ValueAsInt("jmltenagakerja"));
            node.setCost(Env.ZERO);
            if (node.getYield() != 0) {
                Yield *= (double)node.getYield() / 100.0;
            }
            long nodeDuration = node.getDuration();
            QueuingTime += node.getQueuingTime();
            SetupTime += node.getSetupTime();
            Duration = (int)((long)Duration + nodeDuration);
            WaitingTime += node.getWaitingTime();
            MovingTime += node.getMovingTime();
            WorkingTime += node.getWorkingTime();
            jmltenagakerja += node.get_ValueAsInt("jmltenagakerja");
        }
        workflow.setCost(Env.ZERO);
        workflow.setYield((int)(Yield * 100.0));
        workflow.setQueuingTime(QueuingTime);
        workflow.setSetupTime(SetupTime);
        workflow.setDuration(Duration);
        workflow.setWaitingTime(WaitingTime);
        workflow.setMovingTime(MovingTime);
        workflow.setWorkingTime(WorkingTime);
        workflow.set_CustomColumn("jmltenagakerja", (Object)jmltenagakerja);
        for (MCostElement element : MCostElement.getByCostingMethod((Properties)this.getCtx(), (String)this.p_ConstingMethod)) {
            if (!CostEngine.isActivityControlElement((I_M_CostElement)element)) continue;
            CostDimension d = new CostDimension(product, this.m_as, this.p_M_CostType_ID, this.p_AD_Org_ID, 0, element.get_ID());
            List costs = d.toQuery(MCost.class, this.get_TrxName()).list();
            System.out.println("safafafafaf" + costs);
            for (MCost cost : costs) {
                System.out.println("cost" + cost.getCurrentCostPrice());
                int precision = MAcctSchema.get((Properties)Env.getCtx(), (int)cost.getC_AcctSchema_ID()).getCostingPrecision();
                BigDecimal segmentCost = Env.ZERO;
                MWFNode[] arrmWFNode2 = nodes;
                int n2 = nodes.length;
                for (int i = 0; i < n2; ++i) {
                    MWFNode node2 = arrmWFNode2[i];
                    System.out.println("afafaf" + node2.getS_Resource_ID());
                    CostEngine costEngine = CostEngineFactory.getCostEngine(node2.getAD_Client_ID());
                    BigDecimal rate = costEngine.getResourceActualCostRate(null, node2.getS_Resource_ID(), d, this.get_TrxName());
                    BigDecimal baseValue = this.m_routingService.getResourceBaseValue(node2.getS_Resource_ID(), (I_AD_WF_Node)node2);
                    BigDecimal nodeCost = baseValue.multiply(rate).multiply(new BigDecimal(node2.get_ValueAsInt("jmltenagakerja")));
                    if (nodeCost.scale() > precision) {
                        nodeCost = nodeCost.setScale(precision, RoundingMode.HALF_UP);
                    }
                    segmentCost = segmentCost.add(nodeCost);
                    this.log.info("Element : " + (Object)element + ", Node=" + (Object)node2 + ", BaseValue=" + baseValue + ", rate=" + rate + ", nodeCost=" + nodeCost + " => Cost=" + segmentCost);
                    node2.setCost(node2.getCost().add(nodeCost));
                }
                System.out.println("gasgsg" + segmentCost);
                cost.setCurrentCostPrice(segmentCost);
                cost.saveEx();
                workflow.setCost(workflow.getCost().add(segmentCost));
            }
        }
        arrmWFNode = nodes;
        n = nodes.length;
        for (int i = 0; i < n; ++i) {
            node = arrmWFNode[i];
            node.saveEx();
        }
        workflow.saveEx();
        this.log.info("Product: " + product.getName() + " WFCost: " + workflow.getCost());
    }

    private void createNotice(MProduct product, String msg) {
        String productValue = product != null ? product.getValue() : "-";
        this.addLog("WARNING: Product " + productValue + ": " + msg);
    }
}

