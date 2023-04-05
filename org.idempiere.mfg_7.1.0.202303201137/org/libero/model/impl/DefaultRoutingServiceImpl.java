/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.compiere.model.I_AD_WF_Node
 *  org.compiere.model.I_AD_Workflow
 *  org.compiere.model.I_C_UOM
 *  org.compiere.model.I_S_Resource
 *  org.compiere.model.MProcess
 *  org.compiere.model.MResource
 *  org.compiere.model.MResourceAssignment
 *  org.compiere.model.MUOM
 *  org.compiere.model.PO
 *  org.compiere.model.Query
 *  org.compiere.process.ProcessInfo
 *  org.compiere.util.CLogger
 *  org.compiere.util.Env
 *  org.compiere.util.TimeUtil
 *  org.compiere.wf.MWFActivity
 *  org.compiere.wf.MWFNode
 *  org.compiere.wf.MWFProcess
 *  org.compiere.wf.MWorkflow
 */
package org.libero.model.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_S_Resource;
import org.compiere.model.MProcess;
import org.compiere.model.MResource;
import org.compiere.model.MResourceAssignment;
import org.compiere.model.MUOM;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfo;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.wf.MWFActivity;
import org.compiere.wf.MWFNode;
import org.compiere.wf.MWFProcess;
import org.compiere.wf.MWorkflow;
import org.libero.exceptions.CRPException;
import org.libero.model.MPPMRP;
import org.libero.model.MPPOrderNode;
import org.libero.model.RoutingService;
import org.libero.model.reasoner.CRPReasoner;
import org.libero.process.CRP;
import org.libero.tables.I_PP_Cost_Collector;
import org.libero.tables.I_PP_Order_Node;

public class DefaultRoutingServiceImpl
implements RoutingService {
    private final CLogger log = CLogger.getCLogger(this.getClass());
    private Timestamp startAssignTime;

    @Override
    public BigDecimal estimateWorkingTime(I_AD_WF_Node node) {
        double duration = node.getUnitsCycles().signum() == 0 ? (double)node.getDuration() : (double)node.getDuration() / node.getUnitsCycles().doubleValue();
        return BigDecimal.valueOf(duration);
    }

    @Override
    public BigDecimal estimateWorkingTime(I_PP_Order_Node node, BigDecimal qty) {
        double unitDuration = node.getDuration();
        double cycles = this.calculateCycles(node.getUnitsCycles(), qty);
        BigDecimal duration = BigDecimal.valueOf(unitDuration * cycles);
        return duration;
    }

    public BigDecimal estimateWorkingTime(I_AD_WF_Node node, BigDecimal qty) {
        double unitDuration = node.getDuration();
        double cycles = this.calculateCycles(node.getUnitsCycles().intValue(), qty);
        BigDecimal duration = BigDecimal.valueOf(unitDuration * cycles);
        return duration;
    }

    @Override
    public BigDecimal estimateWorkingTime(I_PP_Cost_Collector cc) {
        String trxName = cc instanceof PO ? ((PO)cc).get_TrxName() : null;
        BigDecimal qty = cc.getMovementQty();
        MPPOrderNode node = MPPOrderNode.get(Env.getCtx(), cc.getPP_Order_Node_ID(), trxName);
        return this.estimateWorkingTime(node, qty);
    }

    protected int calculateCycles(int unitsCycle, BigDecimal qty) {
        BigDecimal cycles = qty;
        BigDecimal unitsCycleBD = BigDecimal.valueOf(unitsCycle);
        if (unitsCycleBD.signum() > 0) {
            cycles = qty.divide(unitsCycleBD, 0, RoundingMode.UP);
        }
        return cycles.intValue();
    }

    protected BigDecimal calculateDuration(I_AD_WF_Node node, I_PP_Cost_Collector cc) {
        double totalDuration;
        if (node == null) {
            node = cc.getPP_Order_Node().getAD_WF_Node();
        }
        if (node == null) {
            throw new AdempiereException("calculateDuration not supported using Node null!!!");
        }
        I_AD_Workflow workflow = node.getAD_Workflow();
        double batchSize = workflow.getQtyBatchSize().doubleValue();
        BigDecimal batchS = Env.ONE;
        double queuingTime = 0.0;
        double waitingTime = 0.0;
        double movingTime = 0.0;
        if (node != null && cc == null) {
            queuingTime = node.getQueuingTime();
            waitingTime += (double)node.getWaitingTime();
            movingTime += (double)node.getMovingTime();
        } else if (cc != null) {
            queuingTime = cc.getPP_Order_Node().getQueuingTime();
            waitingTime += (double)cc.getPP_Order_Node().getWaitingTime();
            movingTime += (double)cc.getPP_Order_Node().getMovingTime();
        }
        if (cc != null) {
            double setupTime = cc.getSetupTimeReal().doubleValue();
            double duration = cc.getDurationReal().doubleValue();
            batchS = cc.getPP_Order().getQtyBatchs();
            totalDuration = batchSize > 1.0 ? (queuingTime + waitingTime + movingTime) * batchS.doubleValue() + setupTime + duration : queuingTime + waitingTime + movingTime + setupTime + duration;
        } else {
            double setupTime = node.getSetupTime();
            double duration = this.estimateWorkingTime(node).doubleValue();
            totalDuration = batchSize > 1.0 ? (setupTime + queuingTime + waitingTime + movingTime) / batchSize + duration : setupTime + queuingTime + waitingTime + movingTime + duration;
        }
        return BigDecimal.valueOf(totalDuration);
    }

    @Override
    public BigDecimal calculateDuration(MPPMRP mrp, I_AD_Workflow wf, I_S_Resource plant, BigDecimal qty, Timestamp DemandDateStartSchedule) {
        if (plant == null) {
            return Env.ZERO;
        }
        Properties ctx = mrp.getCtx();
        CRPReasoner reasoner = new CRPReasoner();
        BigDecimal duration = Env.ZERO;
        MWFNode[] nodes = ((MWorkflow)wf).getNodes(true, Env.getAD_Client_ID((Properties)ctx));
        Timestamp counter = DemandDateStartSchedule;
        CRP crp = new CRP();
        ArrayList<MWFNode> list = new ArrayList<MWFNode>();
        for (int n = nodes.length - 1; n >= 0; --n) {
            list.add(nodes[n]);
        }
        list.toArray((T[])nodes);
        ArrayList<Integer> visitedNodes = new ArrayList<Integer>();
        MWFNode[] arrmWFNode = nodes;
        int n = nodes.length;
        for (int i = 0; i < n; ++i) {
            MWFNode node = arrmWFNode[i];
            int nodeId = node.getAD_WF_Node_ID();
            if (visitedNodes.contains(nodeId)) {
                throw new CRPException("Cyclic transition found " + node.getName());
            }
            visitedNodes.add(nodeId);
            this.log.info("PP_Order Node:" + node.getName() != null ? node.getName() : (" Description:" + node.getDescription() != null ? node.getDescription() : ""));
            MResource resource = MResource.get((Properties)ctx, (int)node.getS_Resource_ID());
            if (resource == null) continue;
            if (!reasoner.isAvailable((I_S_Resource)resource)) {
                throw new CRPException("@ResourceNotInSlotDay@").setS_Resource((I_S_Resource)resource);
            }
            long nodeMillis = this.calculateMillisFor((I_AD_WF_Node)node, this.getDurationBaseSec(wf.getDurationUnit()), qty);
            Timestamp dateStart = crp.scheduleBackward(counter, nodeMillis, resource, Env.getAD_Client_ID((Properties)ctx));
            this.createWFActivity(mrp, wf);
            BigDecimal durationRealThisNode = BigDecimal.valueOf(nodeMillis / 1000L / 60L);
            BigDecimal durationThisNodeBG = BigDecimal.valueOf((counter.getTime() - dateStart.getTime()) / 1000L / 60L);
            this.createResourceAssign(mrp, ctx, durationThisNodeBG, (I_AD_WF_Node)node, dateStart, durationRealThisNode);
            duration = duration.add(durationThisNodeBG);
            mrp.setDateStartSchedule(dateStart);
            mrp.setDateFinishSchedule(DemandDateStartSchedule);
            mrp.saveEx(mrp.get_TrxName());
            counter = dateStart;
        }
        return duration;
    }

    @Override
    public long calculateMillisFor(MPPOrderNode node, long commonBase) {
        BigDecimal qty = node.getQtyToDeliver();
        long totalDuration = node.getQueuingTime() + node.getSetupTime() + node.getMovingTime() + node.getWaitingTime();
        BigDecimal workingTime = this.estimateWorkingTime(node, qty);
        BigDecimal qtyBatchSize = node.getPP_Order_Workflow().getQtyBatchSize();
        if (qtyBatchSize.compareTo(Env.ONE) == 1) {
            BigDecimal qtyBatchs = qty.divide(qtyBatchSize, 0, 0);
            totalDuration *= qtyBatchs.longValue();
        }
        totalDuration = (long)((double)totalDuration + workingTime.doubleValue());
        return totalDuration * commonBase * 1000L;
    }

    @Override
    public long calculateMillisFor(I_AD_WF_Node node, long commonBase, BigDecimal qty) {
        long totalDuration = node.getQueuingTime() + node.getSetupTime() + node.getMovingTime() + node.getWaitingTime();
        BigDecimal workingTime = this.estimateWorkingTime(node, qty);
        BigDecimal qtyBatchSize = node.getWorkflow().getQtyBatchSize();
        if (qtyBatchSize.compareTo(Env.ONE) == 1) {
            BigDecimal qtyBatchs = qty.divide(qtyBatchSize, 0, 0);
            totalDuration *= qtyBatchs.longValue();
        }
        totalDuration = (long)((double)totalDuration + workingTime.doubleValue());
        return totalDuration * commonBase * 1000L;
    }

    private MResourceAssignment createResourceAssign(MPPMRP mrp, Properties ctx, BigDecimal duration, I_AD_WF_Node node) {
        MResourceAssignment ra;
        MResourceAssignment resourceschedule = (MResourceAssignment)new Query(Env.getCtx(), "S_ResourceAssignment", "S_Resource_ID=?", null).setParameters(new Object[]{node.getS_Resource_ID()}).first();
        Date date = new Date();
        this.startAssignTime = new Timestamp(date.getTime());
        if (resourceschedule != null) {
            if (resourceschedule.getName().equals("MRP:" + mrp.get_ID() + " Order:" + mrp.getC_Order().getDocumentNo())) {
                ra = resourceschedule;
            } else if (resourceschedule.getName().equals("MRP:" + mrp.get_ID() + " MO:" + mrp.getPP_Order().getDocumentNo())) {
                ra = resourceschedule;
            } else {
                this.startAssignTime = resourceschedule.getAssignDateTo();
                ra = new MResourceAssignment(ctx, 0, mrp.get_TrxName());
            }
        } else {
            ra = new MResourceAssignment(ctx, 0, mrp.get_TrxName());
        }
        ra.setAD_Org_ID(mrp.getAD_Org_ID());
        ra.setName("MRP:" + mrp.get_ID() + " Order:" + mrp.getC_Order().getDocumentNo());
        ra.setAssignDateFrom(this.startAssignTime);
        ra.setAssignDateTo(TimeUtil.addMinutess((Timestamp)this.startAssignTime, (int)duration.intValueExact()));
        ra.setS_Resource_ID(node.getS_Resource_ID());
        ra.setDescription(String.valueOf(mrp.getC_OrderLine().getM_Product().getName()) + " " + mrp.getC_OrderLine().getQtyOrdered());
        ra.saveEx(mrp.get_TrxName());
        return ra;
    }

    private MResourceAssignment createResourceAssign(MPPMRP mrp, Properties ctx, BigDecimal duration, I_AD_WF_Node node, Timestamp startDateTime, BigDecimal durationRealMinutes) {
        String m_name = "MRP:" + mrp.get_ID();
        if (mrp.getC_Order().getDocumentNo() != null) {
            m_name = m_name.concat(" Order:" + mrp.getC_Order().getDocumentNo());
        } else if (mrp.getPP_Order().getDocumentNo() != null) {
            m_name = m_name.concat(" MO:" + mrp.getPP_Order().getDocumentNo());
        }
        MResourceAssignment resourceschedule = (MResourceAssignment)new Query(Env.getCtx(), "S_ResourceAssignment", "S_Resource_ID=? AND Name=?", null).setParameters(new Object[]{node.getS_Resource_ID(), m_name}).first();
        this.startAssignTime = new Timestamp(startDateTime.getTime());
        MResourceAssignment ra = resourceschedule != null ? (resourceschedule.getName().equals(m_name) ? resourceschedule : new MResourceAssignment(ctx, 0, mrp.get_TrxName())) : new MResourceAssignment(ctx, 0, mrp.get_TrxName());
        ra.setAD_Org_ID(mrp.getAD_Org_ID());
        ra.setName(m_name);
        ra.setAssignDateFrom(this.startAssignTime);
        ra.setAssignDateTo(TimeUtil.addMinutess((Timestamp)this.startAssignTime, (int)duration.intValueExact()));
        ra.setS_Resource_ID(node.getS_Resource_ID());
        if (mrp.getC_OrderLine().getM_Product() != null && mrp.getC_OrderLine().getQtyOrdered().compareTo(Env.ZERO) != 0) {
            ra.setDescription(String.valueOf(mrp.getC_OrderLine().getM_Product().getName()) + " " + mrp.getC_OrderLine().getQtyOrdered());
        } else {
            ra.setDescription(String.valueOf(mrp.getM_Product().getName()) + " " + mrp.getQty());
        }
        String baseUOMResource = node.getS_Resource().getS_ResourceType().getC_UOM().getUOMSymbol().trim();
        double durationBaseSec = this.getDurationBaseSec(baseUOMResource);
        if (durationBaseSec == 0.0) {
            throw new AdempiereException("@NotSupported@ @C_UOM_ID@ - " + baseUOMResource);
        }
        BigDecimal durationRealUOM = durationRealMinutes.multiply(BigDecimal.valueOf(60L)).divide(BigDecimal.valueOf(durationBaseSec), 8, RoundingMode.UP);
        ra.setQty(durationRealUOM);
        ra.saveEx(mrp.get_TrxName());
        return ra;
    }

    @Override
    public MResourceAssignment createResourceAssign(MPPMRP mrp, Properties ctx, BigDecimal durationRealMinutes, I_AD_WF_Node node, Timestamp startDateTime, Timestamp finishDateTime) {
        String m_name;
        String m_prefix = m_name = "MRP:" + mrp.get_ID();
        if (mrp.getPP_Order().getDocumentNo() != null && mrp.getC_Order().getDocumentNo() != null) {
            m_name = m_name.concat(" MO:" + mrp.getPP_Order().getDocumentNo() + " Order:" + mrp.getC_Order().getDocumentNo());
        } else if (mrp.getC_Order().getDocumentNo() != null) {
            m_name = m_name.concat(" Order:" + mrp.getC_Order().getDocumentNo());
        } else if (mrp.getPP_Order().getDocumentNo() != null) {
            m_name = m_name.concat(" MO:" + mrp.getPP_Order().getDocumentNo());
        }
        MResourceAssignment resourceschedule = (MResourceAssignment)new Query(Env.getCtx(), "S_ResourceAssignment", "S_Resource_ID=? AND Name=?", null).setParameters(new Object[]{node.getS_Resource_ID(), m_name}).first();
        if (resourceschedule == null) {
            resourceschedule = (MResourceAssignment)new Query(Env.getCtx(), "S_ResourceAssignment", "S_Resource_ID=? AND Name like ?", null).setParameters(new Object[]{node.getS_Resource_ID(), String.valueOf(m_prefix) + '%'}).first();
        }
        Timestamp startAssignTime = new Timestamp(startDateTime.getTime());
        MResourceAssignment ra = resourceschedule != null ? (resourceschedule.getName().startsWith(m_prefix) ? resourceschedule : new MResourceAssignment(ctx, 0, mrp.get_TrxName())) : new MResourceAssignment(ctx, 0, mrp.get_TrxName());
        ra.setAD_Org_ID(mrp.getAD_Org_ID());
        ra.setName(m_name);
        ra.setAssignDateFrom(startAssignTime);
        ra.setAssignDateTo(finishDateTime);
        ra.setS_Resource_ID(node.getS_Resource_ID());
        if (mrp.getC_OrderLine().getM_Product() != null && mrp.getC_OrderLine().getQtyOrdered().compareTo(Env.ZERO) != 0) {
            ra.setDescription(String.valueOf(mrp.getC_OrderLine().getM_Product().getName()) + " " + mrp.getC_OrderLine().getQtyOrdered());
        } else {
            ra.setDescription(String.valueOf(mrp.getM_Product().getName()) + " " + mrp.getQty());
        }
        String baseUOMResource = node.getS_Resource().getS_ResourceType().getC_UOM().getUOMSymbol().trim();
        double durationBaseSec = this.getDurationBaseSec(baseUOMResource);
        if (durationBaseSec == 0.0) {
            throw new AdempiereException("@NotSupported@ @C_UOM_ID@ - " + baseUOMResource);
        }
        BigDecimal durationRealUOM = durationRealMinutes.multiply(BigDecimal.valueOf(60L)).divide(BigDecimal.valueOf(durationBaseSec), 8, RoundingMode.UP);
        ra.setQty(durationRealUOM);
        ra.saveEx(mrp.get_TrxName());
        return ra;
    }

    private void createWFActivity(MPPMRP mrp, I_AD_Workflow wf) {
        if (wf != null) {
            try {
                int Record_ID = mrp.get_ID();
                MWFActivity act = (MWFActivity)new Query(Env.getCtx(), "AD_WF_Activity", "Record_ID=?", mrp.get_TrxName()).setParameters(new Object[]{Record_ID}).first();
                if (act != null) {
                    if (act.getWFState().equals("OS")) {
                        act.delete(true);
                    } else {
                        this.log.severe("Workflow Activity Was Created and Processed Before This!");
                    }
                }
                int Table_ID = 53027;
                int AD_Process_ID = MProcess.getProcess_ID((String)"MFG_WF_Activity", (String)mrp.get_TrxName());
                MPPMRP po = mrp;
                wf.setAD_Table_ID(po.get_Table_ID());
                ProcessInfo pi = new ProcessInfo(wf.getName(), AD_Process_ID, Table_ID, Record_ID);
                pi.setTransactionName(mrp.get_TrxName());
                pi.setPO((PO)po);
                MWFProcess wfProcess = new MWFProcess((MWorkflow)wf, pi, mrp.get_TrxName());
                wfProcess.startWork();
            }
            catch (Exception exception) {
                this.log.warning("Workflow Activity failed to work");
            }
        }
    }

    protected BigDecimal convertDurationToResourceUOM(BigDecimal duration, int S_Resource_ID, I_AD_WF_Node node) {
        MResource resource = MResource.get((Properties)Env.getCtx(), (int)S_Resource_ID);
        MWorkflow wf = MWorkflow.get((Properties)Env.getCtx(), (int)node.getAD_Workflow_ID());
        MUOM resourceUOM = MUOM.get((Properties)Env.getCtx(), (int)resource.getC_UOM_ID());
        return this.convertDuration(duration, wf.getDurationUnit(), (I_C_UOM)resourceUOM);
    }

    @Override
    public BigDecimal getResourceBaseValue(int S_Resource_ID, I_PP_Cost_Collector cc) {
        return this.getResourceBaseValue(S_Resource_ID, null, cc);
    }

    @Override
    public BigDecimal getResourceBaseValue(int S_Resource_ID, I_AD_WF_Node node) {
        return this.getResourceBaseValue(S_Resource_ID, node, null);
    }

    protected BigDecimal getResourceBaseValue(int S_Resource_ID, I_AD_WF_Node node, I_PP_Cost_Collector cc) {
        if (node == null) {
            node = cc.getPP_Order_Node().getAD_WF_Node();
        }
        Properties ctx = node instanceof PO ? ((PO)node).getCtx() : Env.getCtx();
        MResource resource = MResource.get((Properties)ctx, (int)S_Resource_ID);
        MUOM resourceUOM = MUOM.get((Properties)ctx, (int)resource.getC_UOM_ID());
        System.out.println("uom" + (Object)resourceUOM);
        if (this.isTime((I_C_UOM)resourceUOM)) {
            BigDecimal duration = this.calculateDuration(node, cc);
            MWorkflow wf = MWorkflow.get((Properties)ctx, (int)node.getAD_Workflow_ID());
            BigDecimal convertedDuration = this.convertDuration(duration, wf.getDurationUnit(), (I_C_UOM)resourceUOM);
            System.out.println("sfsfsfhjl" + convertedDuration);
            return convertedDuration;
        }
        throw new AdempiereException("@NotSupported@ @C_UOM_ID@ - " + (Object)resourceUOM);
    }

    protected I_AD_WF_Node getAD_WF_Node(I_PP_Cost_Collector cc) {
        I_PP_Order_Node activity = cc.getPP_Order_Node();
        return activity.getAD_WF_Node();
    }

    public long getDurationBaseSec(String durationUnit) {
        if (durationUnit == null) {
            return 0L;
        }
        if ("s".equals(durationUnit)) {
            return 1L;
        }
        if ("m".equals(durationUnit)) {
            return 60L;
        }
        if ("h".equals(durationUnit)) {
            return 3600L;
        }
        if ("D".equals(durationUnit)) {
            return 86400L;
        }
        if ("M".equals(durationUnit)) {
            return 2592000L;
        }
        if ("Y".equals(durationUnit)) {
            return 31536000L;
        }
        return 0L;
    }

    public long getDurationBaseSec(I_C_UOM uom) {
        MUOM uomImpl = (MUOM)uom;
        if (uomImpl.isWeek()) {
            return 604800L;
        }
        if (uomImpl.isDay()) {
            return 86400L;
        }
        if (uomImpl.isHour()) {
            return 3600L;
        }
        if (uomImpl.isMinute()) {
            return 60L;
        }
        if (uomImpl.isSecond()) {
            return 1L;
        }
        throw new AdempiereException("@NotSupported@ @C_UOM_ID@=" + uom.getName());
    }

    public boolean isTime(I_C_UOM uom) {
        String x12de355 = uom.getX12DE355();
        return "03".equals(x12de355) || "MJ".equals(x12de355) || "HR".equals(x12de355) || "DA".equals(x12de355) || "WD".equals(x12de355) || "WK".equals(x12de355) || "MO".equals(x12de355) || "WM".equals(x12de355) || "YR".equals(x12de355);
    }

    public BigDecimal convertDuration(BigDecimal duration, String fromDurationUnit, I_C_UOM toUOM) {
        double fromMult = this.getDurationBaseSec(fromDurationUnit);
        double toDiv = this.getDurationBaseSec(toUOM);
        BigDecimal convertedDuration = BigDecimal.valueOf(duration.doubleValue() * fromMult / toDiv);
        return convertedDuration;
    }

    @Override
    public Timestamp getStartAssignTime() {
        return this.startAssignTime;
    }
}

