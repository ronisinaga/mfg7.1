/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_S_Resource
 *  org.compiere.model.MResourceType
 *  org.compiere.model.MResourceUnAvailable
 *  org.compiere.model.PO
 *  org.compiere.model.POResultSet
 *  org.compiere.model.Query
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.compiere.util.TimeUtil
 */
package org.libero.model.reasoner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.compiere.model.I_S_Resource;
import org.compiere.model.MResourceType;
import org.compiere.model.MResourceUnAvailable;
import org.compiere.model.PO;
import org.compiere.model.POResultSet;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.libero.model.MPPOrder;
import org.libero.model.MPPOrderNode;

public class CRPReasoner {
    public Properties getCtx() {
        return this.getCtx(null);
    }

    private Properties getCtx(Object o) {
        if (o instanceof PO) {
            return ((PO)o).getCtx();
        }
        return Env.getCtx();
    }

    private String getSQLDayRestriction(Timestamp dateTime, I_S_Resource r, List<Object> params) {
        MResourceType rt = MResourceType.get((Properties)this.getCtx(), (int)r.getS_ResourceType_ID());
        Timestamp dayStart = rt.getDayStart(dateTime);
        Timestamp dayEnd = rt.getDayEnd(dateTime);
        String whereClause = "(DateStartSchedule<=? AND DateFinishSchedule>=? AND DateFinishSchedule<=?)";
        params.add(dayStart);
        params.add(dayStart);
        params.add(dayEnd);
        whereClause = String.valueOf(whereClause) + " OR (DateStartSchedule>=? AND DateStartSchedule<=? AND DateFinishSchedule>=? AND DateFinishSchedule<=?)";
        params.add(dayStart);
        params.add(dayEnd);
        params.add(dayStart);
        params.add(dayEnd);
        whereClause = String.valueOf(whereClause) + " OR (DateStartSchedule>=? AND DateStartSchedule<=? AND DateFinishSchedule>=?)";
        params.add(dayStart);
        params.add(dayEnd);
        params.add(dayEnd);
        whereClause = String.valueOf(whereClause) + " OR (DateStartSchedule<=? AND DateFinishSchedule>=?)";
        params.add(dayStart);
        params.add(dayEnd);
        return "(" + whereClause + ")";
    }

    public Query getPPOrdersNotCompletedQuery(int S_Resource_ID, String trxName) {
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuffer whereClause = new StringBuffer();
        whereClause.append("AD_Client_ID=?");
        params.add(Env.getAD_Client_ID((Properties)this.getCtx()));
        whereClause.append(" AND ").append("DocStatus").append(" NOT IN (?,?,?)");
        params.add("VO");
        params.add("RE");
        params.add("CL");
        if (S_Resource_ID > 0) {
            whereClause.append(" AND ").append("S_Resource_ID").append("=?");
            params.add(S_Resource_ID);
        }
        return new Query(this.getCtx(), "PP_Order", whereClause.toString(), trxName).setParameters(params).setOnlyActiveRecords(true).setOrderBy("DatePromised");
    }

    public MPPOrder[] getPPOrders(Timestamp dateTime, I_S_Resource r) {
        if (!this.isAvailable(r, dateTime)) {
            return new MPPOrder[0];
        }
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(r.getS_Resource_ID());
        String whereClause = "EXISTS (SELECT 1 FROM PP_Order_Node WHERE  PP_Order_Node.PP_Order_ID=PP_Order.PP_Order_ID AND S_Resource_ID=? AND " + this.getSQLDayRestriction(dateTime, r, params) + ")" + " AND AD_Client_ID=?";
        params.add(r.getAD_Client_ID());
        List list = new Query(this.getCtx((Object)r), "PP_Order", whereClause, null).setParameters(params).list();
        return list.toArray((T[])new MPPOrder[list.size()]);
    }

    public MPPOrderNode[] getPPOrderNodes(Timestamp dateTime, I_S_Resource r) {
        if (!this.isAvailable(r, dateTime)) {
            return new MPPOrderNode[0];
        }
        ArrayList<Object> params = new ArrayList<Object>();
        String whereClause = "S_Resource_ID=? AND AD_Client_ID=?";
        params.add(r.getS_Resource_ID());
        params.add(r.getAD_Client_ID());
        whereClause = String.valueOf(whereClause) + " AND " + this.getSQLDayRestriction(dateTime, r, params);
        List list = new Query(this.getCtx((Object)r), "PP_Order_Node", whereClause, null).setParameters(params).list();
        return list.toArray(new MPPOrderNode[list.size()]);
    }

    public boolean isAvailable(I_S_Resource r, Timestamp dateTime) {
        MResourceType t = MResourceType.get((Properties)this.getCtx((Object)r), (int)r.getS_ResourceType_ID());
        return t.isDayAvailable(dateTime) && !MResourceUnAvailable.isUnAvailable((I_S_Resource)r, (Timestamp)dateTime);
    }

    public boolean isAvailable(I_S_Resource r) {
        MResourceType t = MResourceType.get((Properties)this.getCtx((Object)r), (int)r.getS_ResourceType_ID());
        return t.isAvailable();
    }

    private Timestamp getAvailableDate(MResourceType t, Timestamp dateTime, boolean isScheduleBackward) {
        Timestamp date = dateTime;
        int direction = isScheduleBackward ? -1 : 1;
        int i = 0;
        do {
            if (t.isDayAvailable(date)) {
                return date;
            }
            date = TimeUtil.addDays((Timestamp)date, (int)direction);
        } while (++i < 7);
        return date;
    }

    public Timestamp getAvailableDate(I_S_Resource r, Timestamp dateTime, boolean isScheduleBackward) {
        int direction;
        String orderByClause;
        String whereClause;
        MResourceType t = MResourceType.get((Properties)this.getCtx((Object)r), (int)r.getS_ResourceType_ID());
        Timestamp date = dateTime;
        ArrayList<Comparable<Date>> params = new ArrayList<Comparable<Date>>();
        if (isScheduleBackward) {
            whereClause = "DateFrom <= ?";
            params.add(date);
            orderByClause = "DateFrom DESC";
            direction = 1;
        } else {
            whereClause = "DateTo >= ?";
            params.add(date);
            orderByClause = "DateTo";
            direction = -1;
        }
        whereClause = String.valueOf(whereClause) + " AND S_Resource_ID=? AND AD_Client_ID=?";
        params.add(Integer.valueOf(r.getS_Resource_ID()));
        params.add(Integer.valueOf(r.getAD_Client_ID()));
        POResultSet rs = new Query(this.getCtx((Object)r), "S_ResourceUnAvailable", whereClause, null).setOrderBy(orderByClause).setParameters(params).scroll();
        try {
            while (rs.hasNext()) {
                MResourceUnAvailable rua = (MResourceUnAvailable)rs.next();
                if (rua.isUnAvailable(date)) {
                    date = TimeUtil.addDays((Timestamp)rua.getDateTo(), (int)(1 * direction));
                }
                date = this.getAvailableDate(t, dateTime, isScheduleBackward);
            }
        }
        finally {
            DB.close((POResultSet)rs);
        }
        date = this.getAvailableDate(t, dateTime, isScheduleBackward);
        return date;
    }
}

