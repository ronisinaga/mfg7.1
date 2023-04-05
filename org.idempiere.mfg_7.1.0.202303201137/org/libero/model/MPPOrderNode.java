/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.Query
 *  org.compiere.util.CCache
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.compiere.util.Msg
 *  org.compiere.wf.MWFNode
 */
package org.libero.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.compiere.model.Query;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.wf.MWFNode;
import org.libero.model.MPPCostCollector;
import org.libero.model.MPPOrderNodeNext;
import org.libero.model.MPPOrderWorkflow;
import org.libero.model.RoutingService;
import org.libero.model.RoutingServiceFactory;
import org.libero.tables.I_PP_Order_Workflow;
import org.libero.tables.X_PP_Order_Node;

public class MPPOrderNode
extends X_PP_Order_Node {
    private static final long serialVersionUID = 1L;
    private static CCache<Integer, MPPOrderNode> s_cache = new CCache("PP_Order_Node", 50);
    MPPOrderWorkflow m_order_wf = null;
    private List<MPPOrderNodeNext> m_next = null;
    private long m_durationBaseMS = -1L;

    public static MPPOrderNode get(Properties ctx, int PP_Order_Node_ID) {
        return MPPOrderNode.get(ctx, PP_Order_Node_ID, null);
    }

    public static MPPOrderNode get(Properties ctx, int PP_Order_Node_ID, String trxName) {
        if (PP_Order_Node_ID <= 0) {
            return null;
        }
        MPPOrderNode retValue = null;
        if (trxName == null && (retValue = (MPPOrderNode)s_cache.get((Object)PP_Order_Node_ID)) != null) {
            return retValue;
        }
        retValue = new MPPOrderNode(ctx, PP_Order_Node_ID, trxName);
        if (retValue.getPP_Order_Node_ID() <= 0) {
            retValue = null;
        }
        if (retValue != null && trxName == null) {
            s_cache.put((Object)PP_Order_Node_ID, (Object)retValue);
        }
        return retValue;
    }

    public static boolean isLastNode(Properties ctx, int PP_Order_Node_ID, String trxName) {
        String whereClause = "PP_Order_Node_ID=?";
        return !new Query(ctx, "PP_Order_NodeNext", whereClause, trxName).setOnlyActiveRecords(true).setParameters(new Object[]{PP_Order_Node_ID}).match();
    }

    public MPPOrderNode(Properties ctx, int PP_Order_Node_ID, String trxName) {
        super(ctx, PP_Order_Node_ID, trxName);
        if (PP_Order_Node_ID == 0) {
            this.setDefault();
        }
        if (this.get_ID() != 0 && trxName == null) {
            s_cache.put((Object)this.getPP_Order_Node_ID(), (Object)this);
        }
    }

    public MPPOrderNode(MPPOrderWorkflow wf, String Value, String Name) {
        this(wf.getCtx(), 0, wf.get_TrxName());
        this.setClientOrg(wf);
        this.setPP_Order_Workflow_ID(wf.getPP_Order_Workflow_ID());
        this.setValue(Value);
        this.setName(Name);
        this.m_durationBaseMS = wf.getDurationBaseSec() * 1000L;
    }

    public MPPOrderNode(MWFNode wfNode, MPPOrderWorkflow PP_Order_Workflow, BigDecimal qtyOrdered, String trxName) {
        this(wfNode.getCtx(), 0, trxName);
        this.setPP_Order_ID(PP_Order_Workflow.getPP_Order_ID());
        this.setPP_Order_Workflow_ID(PP_Order_Workflow.getPP_Order_Workflow_ID());
        this.setAction(wfNode.getAction());
        this.setAD_WF_Node_ID(wfNode.getAD_WF_Node_ID());
        this.setAD_WF_Responsible_ID(wfNode.getAD_WF_Responsible_ID());
        this.setAD_Workflow_ID(wfNode.getAD_Workflow_ID());
        this.setIsSubcontracting(wfNode.isSubcontracting());
        this.setIsMilestone(wfNode.isMilestone());
        this.setC_BPartner_ID(wfNode.getC_BPartner_ID());
        this.setCost(wfNode.getCost());
        this.setDuration(wfNode.getDuration());
        this.setUnitsCycles(wfNode.getUnitsCycles().intValueExact());
        this.setOverlapUnits(wfNode.getOverlapUnits());
        this.setEntityType(wfNode.getEntityType());
        this.setIsCentrallyMaintained(wfNode.isCentrallyMaintained());
        this.setJoinElement(wfNode.getJoinElement());
        this.setLimit(wfNode.getLimit());
        this.setName(wfNode.getName());
        this.setPriority(wfNode.getPriority());
        this.setSplitElement(wfNode.getSplitElement());
        this.setSubflowExecution(wfNode.getSubflowExecution());
        this.setValue(wfNode.getValue());
        this.setS_Resource_ID(wfNode.getS_Resource_ID());
        this.setSetupTime(wfNode.getSetupTime());
        this.setSetupTimeRequired(wfNode.getSetupTime());
        this.setMovingTime(wfNode.getMovingTime());
        this.setWaitingTime(wfNode.getWaitingTime());
        this.setWorkingTime(wfNode.getWorkingTime());
        this.setQueuingTime(wfNode.getQueuingTime());
        this.setXPosition(wfNode.getXPosition());
        this.setYPosition(wfNode.getYPosition());
        this.setDocAction(wfNode.getDocAction());
        this.setAD_Column_ID(wfNode.getAD_Column_ID());
        this.setAD_Form_ID(wfNode.getAD_Form_ID());
        this.setAD_Image_ID(wfNode.getAD_Image_ID());
        this.setAD_Window_ID(wfNode.getAD_Window_ID());
        this.setAD_Process_ID(wfNode.getAD_Process_ID());
        this.setAttributeName(wfNode.getAttributeName());
        this.setAttributeValue(wfNode.getAttributeValue());
        this.setC_BPartner_ID(wfNode.getC_BPartner_ID());
        this.setStartMode(wfNode.getStartMode());
        this.setFinishMode(wfNode.getFinishMode());
        this.setValidFrom(wfNode.getValidFrom());
        this.setValidTo(wfNode.getValidTo());
        this.setQtyOrdered(qtyOrdered);
        this.setDocStatus("DR");
        this.set_ValueOfColumn("HR_Requirement_ID", wfNode.get_Value("HR_Requirement_ID"));
        this.set_ValueOfColumn("IsEmployee", wfNode.get_Value("IsEmployee"));
    }

    public MPPOrderNode(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
        if (trxName == null) {
            s_cache.put((Object)this.getPP_Order_Node_ID(), (Object)this);
        }
    }

    private List<MPPOrderNodeNext> getNodeNexts() {
        if (this.m_next != null) {
            return this.m_next;
        }
        boolean splitAnd = "A".equals(this.getSplitElement());
        String whereClause = "PP_Order_Node_ID=?";
        this.m_next = new Query(this.getCtx(), "PP_Order_NodeNext", whereClause, this.get_TrxName()).setParameters(new Object[]{this.get_ID()}).setOnlyActiveRecords(true).setOrderBy("SeqNo,PP_Order_Node_ID").list();
        for (MPPOrderNodeNext next : this.m_next) {
            next.setFromSplitAnd(splitAnd);
        }
        this.log.fine("#" + this.m_next.size());
        return this.m_next;
    }

    public void setQtyOrdered(BigDecimal qtyOrdered) {
        this.setQtyRequired(qtyOrdered);
        RoutingService routingService = RoutingServiceFactory.get().getRoutingService(this.getAD_Client_ID());
        BigDecimal workingTime = routingService.estimateWorkingTime(this, qtyOrdered);
        this.setDurationRequired(workingTime.intValueExact());
    }

    public BigDecimal getQtyToDeliver() {
        return this.getQtyRequired().subtract(this.getQtyDelivered());
    }

    public int getNextNodeCount() {
        return this.getNodeNexts().size();
    }

    public MPPOrderNodeNext[] getTransitions(int AD_Client_ID) {
        ArrayList<MPPOrderNodeNext> list = new ArrayList<MPPOrderNodeNext>();
        for (MPPOrderNodeNext next : this.getNodeNexts()) {
            if (next.getAD_Client_ID() != 0 && next.getAD_Client_ID() != AD_Client_ID) continue;
            list.add(next);
        }
        return list.toArray(new MPPOrderNodeNext[list.size()]);
    }

    public long getDurationMS() {
        long duration = super.getDuration();
        if (duration == 0L) {
            return 0L;
        }
        if (this.m_durationBaseMS == -1L) {
            this.m_durationBaseMS = this.getMPPOrderWorkflow().getDurationBaseSec() * 1000L;
        }
        return duration * this.m_durationBaseMS;
    }

    public long getLimitMS() {
        long limit = super.getLimit();
        if (limit == 0L) {
            return 0L;
        }
        if (this.m_durationBaseMS == -1L) {
            this.m_durationBaseMS = this.getMPPOrderWorkflow().getDurationBaseSec() * 1000L;
        }
        return limit * this.m_durationBaseMS;
    }

    public int getDurationCalendarField() {
        return this.getMPPOrderWorkflow().getDurationCalendarField();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("MPPOrderNode[");
        sb.append(this.get_ID()).append("-").append(this.getName()).append("]");
        return sb.toString();
    }

    public MPPOrderWorkflow getMPPOrderWorkflow() {
        if (this.m_order_wf == null) {
            this.m_order_wf = new MPPOrderWorkflow(this.getCtx(), this.getPP_Order_Workflow_ID(), this.get_TrxName());
        }
        return this.m_order_wf;
    }

    @Override
    public I_PP_Order_Workflow getPP_Order_Workflow() {
        return this.getMPPOrderWorkflow();
    }

    public void completeIt() {
        this.setDocStatus("CO");
        this.setDocAction("--");
        this.setDateFinish(true);
    }

    public void closeIt() {
        this.setDocStatus("CL");
        this.setDocAction("--");
        this.setDateFinish(false);
        int old = this.getDurationRequired();
        if (old != this.getDurationReal()) {
            this.addDescription(Msg.parseTranslation((Properties)this.getCtx(), (String)("@closed@ ( @Duration@ :" + old + ") ( @QtyRequired@ :" + this.getQtyRequired() + ")")));
            this.setDurationRequired(this.getDurationReal() + new BigDecimal(this.get_ValueAsString("qtyreserved")).intValueExact() * this.getDuration());
            this.setQtyRequired(this.getQtyDelivered().add(new BigDecimal(this.get_ValueAsString("qtyreserved"))));
        }
    }

    protected boolean beforeSave(boolean newRecord) {
        if (!newRecord) {
            int product = DB.getSQLValue(null, (String)("select m_product_id from m_product where name = '" + this.getS_Resource().getName() + "'"));
            BigDecimal cost = DB.getSQLValueBD(null, (String)("select sum(currentcostprice) from m_cost where M_CostElement_ID != 1000000 and m_product_id = " + product), (Object[])new Object[0]);
            this.setCost(new BigDecimal((this.getWaitingTime() + this.getDuration()) * Integer.parseInt(this.get_ValueAsString("jmltenagakerja"))).multiply(cost));
            MPPOrderWorkflow mppOrderWorkflow = new MPPOrderWorkflow(this.getCtx(), this.getPP_Order_Workflow_ID(), this.get_TrxName());
            mppOrderWorkflow.setCost(DB.getSQLValueBD((String)this.get_TrxName(), (String)("select coalesce(sum(cost),0) from PP_Order_Node where PP_Order_Node_ID !=  " + this.getPP_Order_Node_ID() + " and pp_order_id = " + this.getPP_Order_ID()), (Object[])new Object[0]).add(new BigDecimal((this.getWaitingTime() + this.getDuration()) * this.get_ValueAsInt("jmltenagakerja")).multiply(cost)));
            mppOrderWorkflow.save();
        }
        return true;
    }

    public void voidIt() {
        String docStatus = this.getDocStatus();
        if ("VO".equals(docStatus)) {
            this.log.warning("Activity already voided - " + this);
            return;
        }
        BigDecimal qtyRequired = this.getQtyRequired();
        if (qtyRequired.signum() != 0) {
            this.addDescription(String.valueOf(Msg.getMsg((Properties)this.getCtx(), (String)"Voided")) + " (" + qtyRequired + ")");
        }
        this.setDocStatus("VO");
        this.setDocAction("--");
        this.setQtyRequired(Env.ZERO);
        this.setSetupTimeRequired(0);
        this.setDurationRequired(0);
    }

    public void setInProgress(MPPCostCollector currentActivity) {
        if (this.isProcessed()) {
            throw new IllegalStateException("Cannot change status from " + this.getDocStatus() + " to " + "IP");
        }
        this.setDocStatus("IP");
        this.setDocAction("CO");
        if (currentActivity != null && this.getDateStart() == null) {
            this.setDateStart(currentActivity.getDateStart());
        }
    }

    public boolean isProcessed() {
        String status = this.getDocStatus();
        return "CO".equals(status) || "CL".equals(status);
    }

    public void addDescription(String description) {
        String desc = this.getDescription();
        if (desc == null) {
            this.setDescription(description);
        } else {
            this.setDescription(String.valueOf(desc) + " | " + description);
        }
    }

    private void setDefault() {
        this.setAction("Z");
        this.setCost(Env.ZERO);
        this.setDuration(0);
        this.setEntityType("U");
        this.setIsCentrallyMaintained(true);
        this.setJoinElement("X");
        this.setLimit(0);
        this.setSplitElement("X");
        this.setWaitingTime(0);
        this.setXPosition(0);
        this.setYPosition(0);
        this.setDocStatus("DR");
    }

    private void setDateFinish(boolean override) {
        if (!"CO".equals(this.getDocStatus()) && !"CL".equals(this.getDocStatus())) {
            throw new IllegalStateException("Calling setDateFinish when the activity is not completed/closed is not allowed");
        }
        if (!override && this.getDateFinish() != null) {
            this.log.fine("DateFinish already set : Date=" + this.getDateFinish() + ", Override=" + override);
            return;
        }
        Timestamp dateFinish = DB.getSQLValueTSEx((String)this.get_TrxName(), (String)"SELECT MAX(MovementDate) FROM PP_Cost_Collector WHERE PP_Order_Node_ID=? AND DocStatus IN (?,?,?) AND CostCollectorType=?", (Object[])new Object[]{this.get_ID(), "IP", "CO", "CL", "160"});
        if (dateFinish == null) {
            this.log.warning("Activity Completed/Closed but no cost collectors found!");
            return;
        }
        this.setDateFinish(dateFinish);
    }

    public BigDecimal getVariance(String costCollectorType, String columnName) {
        BigDecimal variance = new Query(this.getCtx(), "PP_Cost_Collector", "PP_Order_Node_ID=? AND PP_Order_ID=? AND DocStatus IN (?,?) AND CostCollectorType=?", this.get_TrxName()).setParameters(new Object[]{this.getPP_Order_Node_ID(), this.getPP_Order_ID(), "CO", "CL", costCollectorType}).sum(columnName);
        return variance;
    }

    public BigDecimal getSetupTimeUsageVariance() {
        return this.getVariance("120", "SetupTimeReal");
    }

    public BigDecimal getDurationUsageVariance() {
        return this.getVariance("120", "DurationReal");
    }
}

