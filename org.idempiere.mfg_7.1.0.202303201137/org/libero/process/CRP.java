/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_S_Resource
 *  org.compiere.model.MResource
 *  org.compiere.model.MResourceType
 *  org.compiere.model.MSysConfig
 *  org.compiere.model.Query
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.TimeUtil
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.I_S_Resource;
import org.compiere.model.MResource;
import org.compiere.model.MResourceType;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Order;
import org.libero.exceptions.CRPException;
import org.libero.model.MPPMRP;
import org.libero.model.MPPOrder;
import org.libero.model.MPPOrderNode;
import org.libero.model.MPPOrderWorkflow;
import org.libero.model.RoutingService;
import org.libero.model.RoutingServiceFactory;
import org.libero.model.reasoner.CRPReasoner;
import org.libero.tables.X_PP_Order_Node;

public class CRP
extends SvrProcess {
    public static final String FORWARD_SCHEDULING = "F";
    public static final String BACKWARD_SCHEDULING = "B";
    private int p_S_Resource_ID;
    private String p_ScheduleType;
    private int p_MaxIterationsNo = -1;
    public static final String SYSCONFIG_MaxIterationsNo = "CRP.MaxIterationsNo";
    public static final int DEFAULT_MaxIterationsNo = 1000;
    public RoutingService routingService = null;
    private CRPReasoner reasoner;
    int mTotalPPOrder;
    int mSkippedPPOrder;
    int mProcessedPPOrder;

    protected void prepare() {
        for (ProcessInfoParameter para : this.getParameter()) {
            String name = para.getParameterName();
            if (para.getParameter() == null) {
                // empty if block
            }
            if (name.equals("S_Resource_ID")) {
                this.p_S_Resource_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("ScheduleType")) {
                this.p_ScheduleType = (String)para.getParameter();
                continue;
            }
            this.log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
        this.p_MaxIterationsNo = MSysConfig.getIntValue((String)SYSCONFIG_MaxIterationsNo, (int)1000, (int)this.getAD_Client_ID());
    }

    protected String doIt() throws Exception {
        this.reasoner = new CRPReasoner();
        this.routingService = RoutingServiceFactory.get().getRoutingService(this.getAD_Client_ID());
        return this.runCRP();
    }

    private String runCRP() {
        this.mTotalPPOrder = 0;
        this.mSkippedPPOrder = 0;
        this.mProcessedPPOrder = 0;
        Iterator it = this.reasoner.getPPOrdersNotCompletedQuery(this.p_S_Resource_ID, this.get_TrxName()).iterate();
        while (it.hasNext()) {
            MPPOrder order = (MPPOrder)((Object)it.next());
            try {
                this.runCRP(order);
            }
            catch (Exception e) {
                if (e instanceof CRPException) {
                    CRPException crpEx = (CRPException)((Object)e);
                    crpEx.setPP_Order((I_PP_Order)order);
                    throw crpEx;
                }
                CRPException crpEx = new CRPException(e);
                throw crpEx;
            }
        }
        return "Total Orders: " + Integer.toString(this.mTotalPPOrder) + " Processed: " + Integer.toString(this.mProcessedPPOrder) + " Skip: " + Integer.toString(this.mSkippedPPOrder);
    }

    public void runCRP(MPPOrder order) {
        BigDecimal duration;
        long nodeMillis;
        MResource resource;
        X_PP_Order_Node node;
        int nodeId;
        Timestamp date;
        ++this.mTotalPPOrder;
        this.log.info("PP_Order DocumentNo:" + order.getDocumentNo());
        MPPOrderWorkflow owf = order.getMPPOrderWorkflow();
        if (owf == null) {
            this.addLog("WARNING: No workflow found - " + (Object)((Object)order));
            return;
        }
        this.log.info("PP_Order Workflow:" + owf.getName());
        ArrayList<Integer> visitedNodes = new ArrayList<Integer>();
        String whereClause = "PP_Order_ID=? AND AD_Client_ID=? AND OrderType = ?";
        RoutingService routingService = RoutingServiceFactory.get().getRoutingService(this.getCtx());
        MPPMRP mrp = (MPPMRP)new Query(this.getCtx(), "PP_MRP", whereClause, this.get_TrxName()).setParameters(new Object[]{order.get_ID(), order.getAD_Client_ID(), "SOO"}).firstOnly();
        if (mrp == null) {
            whereClause = String.valueOf(whereClause) + " AND " + "TypeMRP" + "=?";
            mrp = (MPPMRP)new Query(this.getCtx(), "PP_MRP", whereClause, this.get_TrxName()).setParameters(new Object[]{order.get_ID(), order.getAD_Client_ID(), "MOP", "S"}).firstOnly();
        }
        if (mrp == null) {
            this.log.info("MRP Order of PP Order " + order.getDocumentNo() + " not found !!!");
            ++this.mSkippedPPOrder;
            return;
        }
        ++this.mProcessedPPOrder;
        if (this.p_ScheduleType.equals(FORWARD_SCHEDULING)) {
            date = order.getDateStartSchedule();
            nodeId = owf.getPP_Order_Node_ID();
            node = null;
            while (nodeId != 0) {
                node = owf.getNode(nodeId);
                if (visitedNodes.contains(nodeId)) {
                    throw new CRPException("Cyclic transition found").setPP_Order_Node(node);
                }
                visitedNodes.add(nodeId);
                this.log.info("PP_Order Node:" + node.getName() != null ? node.getName() : (" Description:" + node.getDescription() != null ? node.getDescription() : ""));
                resource = MResource.get((Properties)this.getCtx(), (int)node.getS_Resource_ID());
                if (resource == null) {
                    nodeId = owf.getNext(nodeId, this.getAD_Client_ID());
                    continue;
                }
                if (!this.reasoner.isAvailable((I_S_Resource)resource)) {
                    throw new CRPException("@ResourceNotInSlotDay@").setS_Resource((I_S_Resource)resource);
                }
                nodeMillis = routingService.calculateMillisFor((MPPOrderNode)node, owf.getDurationBaseSec());
                Timestamp dateFinish = this.scheduleForward(date, nodeMillis, resource);
                node.setDateStartSchedule(date);
                node.setDateFinishSchedule(dateFinish);
                node.saveEx();
                duration = BigDecimal.valueOf(nodeMillis * 1000L * 60L);
                routingService.createResourceAssign(mrp, this.getCtx(), duration, node.getAD_WF_Node(), date, dateFinish);
                date = node.getDateFinishSchedule();
                nodeId = owf.getNext(nodeId, this.getAD_Client_ID());
            }
            if (node != null && node.getDateFinishSchedule() != null) {
                order.setDateFinishSchedule(node.getDateFinishSchedule());
            }
        } else if (this.p_ScheduleType.equals(BACKWARD_SCHEDULING)) {
            date = order.getDateFinishSchedule();
            nodeId = owf.getNodeLastID(this.getAD_Client_ID());
            node = null;
            while (nodeId != 0) {
                node = owf.getNode(nodeId);
                if (visitedNodes.contains(nodeId)) {
                    throw new CRPException("Cyclic transition found - ").setPP_Order_Node(node);
                }
                visitedNodes.add(nodeId);
                this.log.info("PP_Order Node:" + node.getName() != null ? node.getName() : (" Description:" + node.getDescription() != null ? node.getDescription() : ""));
                resource = MResource.get((Properties)this.getCtx(), (int)node.getS_Resource_ID());
                if (resource == null) {
                    nodeId = owf.getPrevious(nodeId, this.getAD_Client_ID());
                    continue;
                }
                if (!this.reasoner.isAvailable((I_S_Resource)resource)) {
                    throw new CRPException("@ResourceNotInSlotDay@").setS_Resource((I_S_Resource)resource);
                }
                nodeMillis = routingService.calculateMillisFor((MPPOrderNode)node, owf.getDurationBaseSec());
                Timestamp dateStart = this.scheduleBackward(date, nodeMillis, resource);
                node.setDateStartSchedule(dateStart);
                node.setDateFinishSchedule(date);
                node.saveEx();
                duration = BigDecimal.valueOf(nodeMillis / 1000L / 60L);
                routingService.createResourceAssign(mrp, this.getCtx(), duration, node.getAD_WF_Node(), dateStart, date);
                date = node.getDateStartSchedule();
                nodeId = owf.getPrevious(nodeId, this.getAD_Client_ID());
            }
            if (node != null && node.getDateStartSchedule() != null) {
                order.setDateStartSchedule(node.getDateStartSchedule());
            }
        } else {
            throw new CRPException("Unknown scheduling method - " + this.p_ScheduleType);
        }
        order.saveEx(this.get_TrxName());
        whereClause = "PP_Order_ID=? AND AD_Client_ID=? AND ( DocStatus=? OR DocStatus=? ) AND ( OrderType=? OR OrderType=?)";
        List mrpset = new Query(this.getCtx(), "PP_MRP", whereClause, this.get_TrxName()).setParameters(new Object[]{order.get_ID(), order.getAD_Client_ID(), "DR", "IP", "SOO", "MOP"}).list();
        for (MPPMRP mrps : mrpset) {
            mrps.setDateStartSchedule(order.getDateStartSchedule());
            mrps.setDateFinishSchedule(order.getDateFinishSchedule());
            mrps.saveEx(this.get_TrxName());
        }
    }

    private long getAvailableDurationMillis(Timestamp dayStart, Timestamp dayEnd, I_S_Resource resource) {
        long availableDayDuration = dayEnd.getTime() - dayStart.getTime();
        this.log.info("--> availableDayDuration  " + availableDayDuration);
        if (availableDayDuration < 0L) {
            throw new CRPException("@TimeSlotStart@ > @TimeSlotEnd@ (" + dayEnd + " > " + dayStart + ")").setS_Resource(resource);
        }
        return availableDayDuration;
    }

    private Timestamp scheduleForward(Timestamp start, long nodeDurationMillis, MResource r) {
        Calendar cal = Calendar.getInstance();
        MResourceType t = r.getResourceType();
        int iteration = 0;
        Timestamp currentDate = start;
        Timestamp end = null;
        long remainingMillis = nodeDurationMillis;
        do {
            long availableDayDuration;
            cal.setTimeInMillis(currentDate.getTime());
            int hour = cal.get(11);
            int minute = cal.get(12);
            int second = cal.get(13);
            currentDate = this.reasoner.getAvailableDate((I_S_Resource)r, currentDate, false);
            cal.setTimeInMillis(currentDate.getTime());
            cal.set(11, hour);
            cal.set(12, minute);
            cal.set(13, second);
            currentDate.setTime(cal.getTimeInMillis());
            Timestamp dayStart = t.getDayStart(currentDate);
            if (iteration == 0 && currentDate.compareTo(dayStart) > 0) {
                dayStart = currentDate;
            }
            Timestamp dayEnd = t.getDayEnd(currentDate);
            if (currentDate.after(dayStart)) {
                currentDate.before(dayEnd);
            }
            if ((availableDayDuration = this.getAvailableDurationMillis(dayStart, dayEnd, (I_S_Resource)r)) >= remainingMillis) {
                end = new Timestamp(dayStart.getTime() + remainingMillis);
                remainingMillis = 0L;
                break;
            }
            currentDate = TimeUtil.addDays((Timestamp)TimeUtil.getDayBorder((Timestamp)currentDate, null, (boolean)false), (int)1);
            remainingMillis -= availableDayDuration;
            if (++iteration <= this.p_MaxIterationsNo) continue;
            throw new CRPException("Maximum number of iterations exceeded (" + this.p_MaxIterationsNo + ")" + " - Date:" + currentDate + ", RemainingMillis:" + remainingMillis);
        } while (remainingMillis > 0L);
        return end;
    }

    private Timestamp scheduleBackward(Timestamp end, long nodeDurationMillis, MResource r) {
        Calendar cal = Calendar.getInstance();
        MResourceType t = r.getResourceType();
        this.log.info("--> ResourceType " + (Object)t);
        Timestamp start = null;
        Timestamp currentDate = end;
        long remainingMillis = nodeDurationMillis;
        int iteration = 0;
        do {
            long availableDayDuration;
            this.log.info("--> end=" + currentDate);
            this.log.info("--> nodeDuration=" + remainingMillis);
            cal.setTimeInMillis(currentDate.getTime());
            int hour = cal.get(11);
            int minute = cal.get(12);
            int second = cal.get(13);
            currentDate = this.reasoner.getAvailableDate((I_S_Resource)r, currentDate, true);
            cal.setTimeInMillis(currentDate.getTime());
            cal.set(11, hour);
            cal.set(12, minute);
            cal.set(13, second);
            currentDate.setTime(cal.getTimeInMillis());
            this.log.info("--> end(available)=" + currentDate);
            Timestamp dayEnd = t.getDayEnd(currentDate);
            if (iteration == 0 && currentDate.compareTo(dayEnd) < 0) {
                dayEnd = currentDate;
            }
            Timestamp dayStart = t.getDayStart(currentDate);
            this.log.info("--> dayStart=" + dayStart + ", dayEnd=" + dayEnd);
            if (currentDate.before(dayEnd)) {
                currentDate.after(dayStart);
            }
            if ((availableDayDuration = this.getAvailableDurationMillis(dayStart, dayEnd, (I_S_Resource)r)) >= remainingMillis) {
                this.log.info("--> availableDayDuration >= nodeDuration true " + availableDayDuration + "|" + remainingMillis);
                start = new Timestamp(dayEnd.getTime() - remainingMillis);
                remainingMillis = 0L;
                break;
            }
            this.log.info("--> availableDayDuration >= nodeDuration false " + availableDayDuration + "|" + remainingMillis);
            this.log.info("--> nodeDuration-availableDayDuration " + (remainingMillis - availableDayDuration));
            currentDate = TimeUtil.addDays((Timestamp)TimeUtil.getDayBorder((Timestamp)currentDate, null, (boolean)true), (int)-1);
            remainingMillis -= availableDayDuration;
            if (++iteration <= this.p_MaxIterationsNo) continue;
            throw new CRPException("Maximum number of iterations exceeded (" + this.p_MaxIterationsNo + ")" + " - Date:" + start + ", RemainingMillis:" + remainingMillis);
        } while (remainingMillis > 0L);
        this.log.info("         -->  start=" + start + " <---------------------------------------- ");
        return start;
    }

    public Timestamp scheduleBackward(Timestamp end, long nodeDurationMillis, MResource r, int AD_Client_ID) {
        if (this.reasoner == null) {
            this.reasoner = new CRPReasoner();
        }
        this.p_MaxIterationsNo = MSysConfig.getIntValue((String)SYSCONFIG_MaxIterationsNo, (int)1000, (int)AD_Client_ID);
        return this.scheduleBackward(end, nodeDurationMillis, r);
    }
}

