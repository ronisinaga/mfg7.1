/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.compiere.model.MClient
 *  org.compiere.model.MDocType
 *  org.compiere.model.Query
 *  org.compiere.util.CCache
 *  org.compiere.util.Env
 *  org.compiere.util.Msg
 *  org.compiere.wf.MWorkflow
 */
package org.libero.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.Query;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.wf.MWorkflow;
import org.libero.model.MPPCostCollector;
import org.libero.model.MPPOrder;
import org.libero.model.MPPOrderNode;
import org.libero.model.MPPOrderNodeNext;
import org.libero.model.RoutingService;
import org.libero.model.RoutingServiceFactory;
import org.libero.tables.X_PP_Order_Workflow;

public class MPPOrderWorkflow
extends X_PP_Order_Workflow {
    private static final long serialVersionUID = 1L;
    private static CCache<Integer, MPPOrderWorkflow> s_cache = new CCache("PP_Order_Workflow", 20);
    private List<MPPOrderNode> m_nodes = null;
    private MPPOrder m_order = null;

    public static MPPOrderWorkflow get(Properties ctx, int PP_Order_Workflow_ID) {
        if (PP_Order_Workflow_ID <= 0) {
            return null;
        }
        MPPOrderWorkflow retValue = (MPPOrderWorkflow)s_cache.get((Object)PP_Order_Workflow_ID);
        if (retValue != null) {
            return retValue;
        }
        retValue = new MPPOrderWorkflow(ctx, PP_Order_Workflow_ID, null);
        if (retValue.get_ID() != 0) {
            s_cache.put((Object)PP_Order_Workflow_ID, (Object)retValue);
        }
        return retValue;
    }

    public MPPOrderWorkflow(Properties ctx, int PP_Order_Workflow_ID, String trxName) {
        super(ctx, PP_Order_Workflow_ID, trxName);
        if (PP_Order_Workflow_ID == 0) {
            this.setAccessLevel("1");
            this.setAuthor(MClient.get((Properties)ctx).getName());
            this.setDurationUnit("D");
            this.setDuration(1);
            this.setEntityType("U");
            this.setIsDefault(false);
            this.setPublishStatus("U");
            this.setVersion(0);
            this.setCost(Env.ZERO);
            this.setWaitingTime(0);
            this.setWorkingTime(0);
        }
    }

    public MPPOrderWorkflow(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    public MPPOrderWorkflow(MWorkflow workflow, int PP_Order_ID, String trxName) {
        this(workflow.getCtx(), 0, trxName);
        this.setPP_Order_ID(PP_Order_ID);
        this.setValue(workflow.getValue());
        this.setWorkflowType(workflow.getWorkflowType());
        this.setQtyBatchSize(workflow.getQtyBatchSize());
        this.setName(workflow.getName());
        this.setAccessLevel(workflow.getAccessLevel());
        this.setAuthor(workflow.getAuthor());
        this.setDurationUnit(workflow.getDurationUnit());
        this.setDuration(workflow.getDuration());
        this.setEntityType(workflow.getEntityType());
        this.setIsDefault(workflow.isDefault());
        this.setPublishStatus(workflow.getPublishStatus());
        this.setVersion(workflow.getVersion());
        this.setCost(workflow.getCost());
        this.setWaitingTime(workflow.getWaitingTime());
        this.setWorkingTime(workflow.getWorkingTime());
        this.setAD_WF_Responsible_ID(workflow.getAD_WF_Responsible_ID());
        this.setAD_Workflow_ID(workflow.getAD_Workflow_ID());
        this.setLimit(workflow.getLimit());
        this.setPriority(workflow.getPriority());
        this.setS_Resource_ID(workflow.getS_Resource_ID());
        this.setQueuingTime(workflow.getQueuingTime());
        this.setSetupTime(workflow.getSetupTime());
        this.setMovingTime(workflow.getMovingTime());
        this.setProcessType(workflow.getProcessType());
        this.setAD_Table_ID(workflow.getAD_Table_ID());
        this.setAD_WF_Node_ID(workflow.getAD_WF_Node_ID());
        this.setAD_WorkflowProcessor_ID(workflow.getAD_WorkflowProcessor_ID());
        this.setDescription(workflow.getDescription());
        this.setValidFrom(workflow.getValidFrom());
        this.setValidTo(workflow.getValidTo());
    }

    public List<MPPOrderNode> getNodes(boolean requery) {
        if (this.m_nodes == null || requery) {
            this.m_nodes = new Query(this.getCtx(), "PP_Order_Node", "PP_Order_Workflow_ID=?", this.get_TrxName()).setParameters(new Object[]{this.get_ID()}).setOnlyActiveRecords(true).list();
            this.log.fine("#" + this.m_nodes.size());
        }
        return this.m_nodes;
    }

    protected List<MPPOrderNode> getNodes() {
        return this.getNodes(false);
    }

    public int getNodeCount() {
        return this.getNodes().size();
    }

    public MPPOrderNode[] getNodes(boolean ordered, int AD_Client_ID) {
        if (ordered) {
            return this.getNodesInOrder(AD_Client_ID);
        }
        ArrayList<MPPOrderNode> list = new ArrayList<MPPOrderNode>();
        for (MPPOrderNode node : this.getNodes()) {
            if (node.getAD_Client_ID() != 0 && node.getAD_Client_ID() != AD_Client_ID) continue;
            list.add(node);
        }
        return list.toArray(new MPPOrderNode[list.size()]);
    }

    public MPPOrderNode getFirstNode() {
        return this.getNode(this.getPP_Order_Node_ID());
    }

    private MPPOrderNode getNode(int PP_Order_Node_ID, int AD_Client_ID) {
        if (PP_Order_Node_ID <= 0) {
            return null;
        }
        for (MPPOrderNode node : this.getNodes()) {
            if (node.getPP_Order_Node_ID() != PP_Order_Node_ID) continue;
            if (AD_Client_ID >= 0) {
                if (node.getAD_Client_ID() == 0 || node.getAD_Client_ID() == AD_Client_ID) {
                    return node;
                }
                return null;
            }
            return node;
        }
        return null;
    }

    public MPPOrderNode getNode(int PP_Order_Node_ID) {
        return this.getNode(PP_Order_Node_ID, -1);
    }

    public MPPOrderNode[] getNextNodes(int PP_Order_Node_ID, int AD_Client_ID) {
        MPPOrderNode node = this.getNode(PP_Order_Node_ID);
        if (node == null || node.getNextNodeCount() == 0) {
            return null;
        }
        ArrayList<MPPOrderNode> list = new ArrayList<MPPOrderNode>();
        for (MPPOrderNodeNext nextTr : node.getTransitions(AD_Client_ID)) {
            MPPOrderNode next = this.getNode(nextTr.getPP_Order_Next_ID(), AD_Client_ID);
            if (next == null) continue;
            list.add(next);
        }
        return list.toArray(new MPPOrderNode[list.size()]);
    }

    private MPPOrderNode[] getNodesInOrder(int AD_Client_ID) {
        ArrayList<MPPOrderNode> list = new ArrayList<MPPOrderNode>();
        this.addNodesSF(list, this.getPP_Order_Node_ID(), AD_Client_ID);
        if (this.getNodeCount() != list.size()) {
            for (MPPOrderNode node : this.getNodes()) {
                if (node.getAD_Client_ID() != 0 && node.getAD_Client_ID() != AD_Client_ID) continue;
                boolean found = false;
                for (MPPOrderNode existing : list) {
                    if (existing.getPP_Order_Node_ID() != node.getPP_Order_Node_ID()) continue;
                    found = true;
                    break;
                }
                if (found) continue;
                this.log.log(Level.WARNING, "Added Node w/o transition: " + node);
                list.add(node);
            }
        }
        MPPOrderNode[] nodeArray = new MPPOrderNode[list.size()];
        list.toArray(nodeArray);
        return nodeArray;
    }

    private void addNodesSF(Collection<MPPOrderNode> list, int PP_Order_Node_ID, int AD_Client_ID) {
        MPPOrderNode node = this.getNode(PP_Order_Node_ID, AD_Client_ID);
        if (node != null) {
            if (!list.contains(node)) {
                list.add(node);
            }
            ArrayList<Integer> nextNodes = new ArrayList<Integer>();
            for (MPPOrderNodeNext next : node.getTransitions(AD_Client_ID)) {
                MPPOrderNode child = this.getNode(next.getPP_Order_Next_ID(), AD_Client_ID);
                if (child == null) continue;
                if (!list.contains(child)) {
                    list.add(child);
                    nextNodes.add(next.getPP_Order_Next_ID());
                    continue;
                }
                this.log.saveError("Error", "Cyclic transition found - " + node + " -> " + child);
            }
            Iterator iterator = nextNodes.iterator();
            while (iterator.hasNext()) {
                int pp_Order_Next_ID = (Integer)iterator.next();
                this.addNodesSF(list, pp_Order_Next_ID, AD_Client_ID);
            }
        }
    }

    public int getNext(int PP_Order_Node_ID, int AD_Client_ID) {
        MPPOrderNode[] nodes = this.getNodesInOrder(AD_Client_ID);
        for (int i = 0; i < nodes.length; ++i) {
            if (nodes[i].getPP_Order_Node_ID() != PP_Order_Node_ID) continue;
            MPPOrderNodeNext[] nexts = nodes[i].getTransitions(AD_Client_ID);
            if (nexts.length > 0) {
                return nexts[0].getPP_Order_Next_ID();
            }
            return 0;
        }
        return 0;
    }

    public MPPOrderNodeNext[] getNodeNexts(int PP_Order_Node_ID, int AD_Client_ID) {
        MPPOrderNode[] nodes = this.getNodesInOrder(AD_Client_ID);
        for (int i = 0; i < nodes.length; ++i) {
            if (nodes[i].getPP_Order_Node_ID() != PP_Order_Node_ID) continue;
            return nodes[i].getTransitions(AD_Client_ID);
        }
        return new MPPOrderNodeNext[0];
    }

    public int getPrevious(int PP_Order_Node_ID, int AD_Client_ID) {
        MPPOrderNode[] nodes = this.getNodesInOrder(AD_Client_ID);
        for (int i = 0; i < nodes.length; ++i) {
            if (nodes[i].getPP_Order_Node_ID() != PP_Order_Node_ID) continue;
            if (i > 0) {
                return nodes[i - 1].getPP_Order_Node_ID();
            }
            return 0;
        }
        return 0;
    }

    public int getNodeLastID(int AD_Client_ID) {
        MPPOrderNode[] nodes = this.getNodesInOrder(AD_Client_ID);
        if (nodes.length > 0) {
            return nodes[nodes.length - 1].getPP_Order_Node_ID();
        }
        return 0;
    }

    public MPPOrderNode getLastNode(int AD_Client_ID) {
        MPPOrderNode[] nodes = this.getNodesInOrder(AD_Client_ID);
        if (nodes.length > 0) {
            return nodes[nodes.length - 1];
        }
        return null;
    }

    public boolean isFirst(int PP_Order_Node_ID, int AD_Client_ID) {
        return PP_Order_Node_ID == this.getPP_Order_Node_ID();
    }

    public boolean isLast(int PP_Order_Node_ID, int AD_Client_ID) {
        MPPOrderNode[] nodes = this.getNodesInOrder(AD_Client_ID);
        return PP_Order_Node_ID == nodes[nodes.length - 1].getPP_Order_Node_ID();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("MPPOrderWorkflow[");
        sb.append(this.get_ID()).append("-").append(this.getName()).append("]");
        return sb.toString();
    }

    protected boolean afterSave(boolean newRecord, boolean success) {
        this.log.fine("Success=" + success);
        if (success && newRecord) {
            MPPOrderNode[] nodes = this.getNodesInOrder(0);
            for (int i = 0; i < nodes.length; ++i) {
                nodes[i].saveEx(this.get_TrxName());
            }
        }
        return success;
    }

    public long getDurationBaseSec() {
        if (this.getDurationUnit() == null) {
            return 0L;
        }
        if ("s".equals(this.getDurationUnit())) {
            return 1L;
        }
        if ("m".equals(this.getDurationUnit())) {
            return 60L;
        }
        if ("h".equals(this.getDurationUnit())) {
            return 3600L;
        }
        if ("D".equals(this.getDurationUnit())) {
            return 86400L;
        }
        if ("M".equals(this.getDurationUnit())) {
            return 2592000L;
        }
        if ("Y".equals(this.getDurationUnit())) {
            return 31536000L;
        }
        return 0L;
    }

    public int getDurationCalendarField() {
        if (this.getDurationUnit() == null) {
            return 12;
        }
        if ("s".equals(this.getDurationUnit())) {
            return 13;
        }
        if ("m".equals(this.getDurationUnit())) {
            return 12;
        }
        if ("h".equals(this.getDurationUnit())) {
            return 10;
        }
        if ("D".equals(this.getDurationUnit())) {
            return 6;
        }
        if ("M".equals(this.getDurationUnit())) {
            return 2;
        }
        if ("Y".equals(this.getDurationUnit())) {
            return 1;
        }
        return 12;
    }

    public void closeActivities(MPPOrderNode activity, Timestamp movementDate, boolean milestone) {
        if (activity.getPP_Order_Workflow_ID() != this.get_ID()) {
            throw new AdempiereException("Activity and Order Workflow not matching (" + activity + ", PP_Order_Workflow_ID=" + this.get_ID() + ")");
        }
        MPPOrder order = this.getMPPOrder();
        int nodeId = activity.get_ID();
        while (nodeId != 0) {
            MPPOrderNode node = this.getNode(nodeId);
            if (milestone && node.isMilestone() && node.get_ID() != activity.get_ID()) break;
            if ("DR".equals(node.getDocStatus())) {
                BigDecimal qtyToDeliver = node.getQtyToDeliver();
                if (qtyToDeliver.signum() > 0) {
                    int setupTimeReal = node.getSetupTimeRequired() - node.getSetupTimeReal();
                    RoutingService routingService = RoutingServiceFactory.get().getRoutingService(node.getAD_Client_ID());
                    BigDecimal durationReal = routingService.estimateWorkingTime(node, qtyToDeliver);
                    MPPCostCollector.createCollector(order, order.getM_Product_ID(), order.getM_Locator_ID(), order.getM_AttributeSetInstance_ID(), node.getS_Resource_ID(), 0, node.get_ID(), MDocType.getDocType((String)"MCC"), "160", movementDate, qtyToDeliver, Env.ZERO, Env.ZERO, setupTimeReal, durationReal, Env.ZERO, Env.ZERO, Env.ZERO);
                    node.load(order.get_TrxName(), new String[0]);
                    node.closeIt();
                    node.saveEx();
                }
            } else if ("CO".equals(node.getDocStatus()) || "IP".equals(node.getDocStatus())) {
                node.closeIt();
                node.saveEx();
            }
            nodeId = this.getPrevious(nodeId, this.getAD_Client_ID());
        }
        this.m_nodes = null;
    }

    public void voidActivities() {
        for (MPPOrderNode node : this.getNodes(true, this.getAD_Client_ID())) {
            BigDecimal old = node.getQtyRequired();
            if (old.signum() == 0) continue;
            node.addDescription(String.valueOf(Msg.getMsg((Properties)this.getCtx(), (String)"Voided")) + " (" + old + ")");
            node.voidIt();
            node.saveEx();
        }
    }

    public MPPOrder getMPPOrder() {
        if (this.m_order == null) {
            this.m_order = new MPPOrder(this.getCtx(), this.getPP_Order_ID(), this.get_TrxName());
        }
        return this.m_order;
    }
}

