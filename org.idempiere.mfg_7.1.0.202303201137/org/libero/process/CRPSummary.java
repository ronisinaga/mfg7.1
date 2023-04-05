/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MResource
 *  org.compiere.model.MResourceType
 *  org.compiere.model.MUOM
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.CPreparedStatement
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 */
package org.libero.process;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MResource;
import org.compiere.model.MResourceType;
import org.compiere.model.MUOM;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.libero.tables.X_T_MRP_CRP;

public class CRPSummary
extends SvrProcess {
    private int p_S_Resource_ID = 0;
    private Timestamp p_DateFrom = null;
    private Timestamp p_DateTo = null;
    private String p_FrequencyType = null;
    private int AD_Client_ID = 0;
    private int AD_PInstance_ID = 0;

    protected void prepare() {
        this.AD_Client_ID = Integer.parseInt(Env.getContext((Properties)Env.getCtx(), (String)"#AD_Client_ID"));
        ProcessInfoParameter[] para = this.getParameter();
        this.AD_PInstance_ID = this.getAD_PInstance_ID();
        for (int i = 0; i < para.length; ++i) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) continue;
            if (name.equals("S_Resource_ID")) {
                this.p_S_Resource_ID = ((BigDecimal)para[i].getParameter()).intValue();
                continue;
            }
            if (name.equals("DateFrom")) {
                this.p_DateFrom = (Timestamp)para[i].getParameter();
                continue;
            }
            if (name.equals("DateTo")) {
                this.p_DateTo = (Timestamp)para[i].getParameter();
                continue;
            }
            if (name.equals("FrequencyType")) {
                this.p_FrequencyType = (String)para[i].getParameter();
                continue;
            }
            this.log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
    }

    protected String doIt() throws Exception {
        return this.runCRP();
    }

    protected String runCRP() {
        return "";
    }

    public static Timestamp addSecond(Timestamp dateTime, long offset) {
        if (dateTime == null) {
            dateTime = new Timestamp(System.currentTimeMillis());
        }
        if (offset == 0L) {
            return dateTime;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dateTime);
        cal.add(13, new Long(offset).intValue());
        return new Timestamp(cal.getTimeInMillis());
    }

    public long getHoursAvailable(Timestamp time1, Timestamp time2) {
        GregorianCalendar g1 = new GregorianCalendar();
        g1.setTimeInMillis(time1.getTime());
        GregorianCalendar g2 = new GregorianCalendar();
        g1.setTimeInMillis(time2.getTime());
        Date d1 = g1.getTime();
        Date d2 = g2.getTime();
        long l1 = d1.getTime();
        long l2 = d2.getTime();
        long difference = l2 - l1;
        System.out.println("Elapsed milliseconds: " + difference);
        return difference;
    }

    public int getNotAvailbleDays(Timestamp start, Timestamp end, MResourceType t) {
        GregorianCalendar gc1;
        GregorianCalendar gc2;
        if (!t.isDateSlot()) {
            return 0;
        }
        GregorianCalendar g1 = new GregorianCalendar();
        g1.setTimeInMillis(start.getTime());
        GregorianCalendar g2 = new GregorianCalendar();
        g2.setTimeInMillis(end.getTime());
        if (g2.after(g1)) {
            gc2 = (GregorianCalendar)g2.clone();
            gc1 = (GregorianCalendar)g1.clone();
        } else {
            gc2 = (GregorianCalendar)g1.clone();
            gc1 = (GregorianCalendar)g2.clone();
        }
        gc1.clear(14);
        gc1.clear(13);
        gc1.clear(12);
        gc1.clear(11);
        gc2.clear(14);
        gc2.clear(13);
        gc2.clear(12);
        gc2.clear(11);
        int DaysNotAvialable = 0;
        while (gc1.before(gc2)) {
            gc1.add(5, 1);
            switch (gc1.get(7)) {
                case 1: {
                    if (t.isOnSunday()) break;
                    ++DaysNotAvialable;
                    break;
                }
                case 2: {
                    if (t.isOnMonday()) break;
                    ++DaysNotAvialable;
                    break;
                }
                case 3: {
                    if (t.isOnTuesday()) break;
                    ++DaysNotAvialable;
                    break;
                }
                case 4: {
                    if (t.isOnWednesday()) break;
                    ++DaysNotAvialable;
                    break;
                }
                case 5: {
                    if (t.isOnThursday()) break;
                    ++DaysNotAvialable;
                    break;
                }
                case 6: {
                    if (t.isOnFriday()) break;
                    ++DaysNotAvialable;
                    break;
                }
                case 7: {
                    if (t.isOnSaturday()) break;
                    ++DaysNotAvialable;
                }
            }
        }
        System.out.println("DaysNotAvialable" + DaysNotAvialable);
        return DaysNotAvialable;
    }

    public void Summary(Timestamp start, Timestamp finish, MResource r) {
        GregorianCalendar gc1 = new GregorianCalendar();
        gc1.setTimeInMillis(start.getTime());
        gc1.clear(14);
        gc1.clear(13);
        gc1.clear(12);
        gc1.clear(11);
        GregorianCalendar gc2 = new GregorianCalendar();
        gc2.setTimeInMillis(finish.getTime());
        gc2.clear(14);
        gc2.clear(13);
        gc2.clear(12);
        gc2.clear(11);
        MResourceType t = MResourceType.get((Properties)Env.getCtx(), (int)r.getS_ResourceType_ID());
        long hours = 0L;
        hours = t.isTimeSlot() ? this.getHoursAvailable(t.getTimeSlotStart(), t.getTimeSlotStart()) : 24L;
        boolean available = false;
        ArrayList<Col> list = new ArrayList<Col>();
        int col = 0;
        int summary = 0;
        Col cols = new Col();
        cols.setFrom("Past Due");
        cols.setTo(start.toString());
        cols.setDays(0);
        cols.setCapacity(0);
        cols.setLoad(0);
        cols.setSummary(0);
        list.add(0, cols);
        ++col;
        while (gc1.before(gc2)) {
            gc1.add(5, 1);
            switch (gc1.get(7)) {
                case 1: {
                    if (!t.isOnSunday()) break;
                    available = true;
                    break;
                }
                case 2: {
                    if (!t.isOnMonday()) break;
                    available = true;
                    break;
                }
                case 3: {
                    if (!t.isOnTuesday()) break;
                    available = true;
                    break;
                }
                case 4: {
                    if (!t.isOnWednesday()) break;
                    available = true;
                    break;
                }
                case 5: {
                    if (!t.isOnThursday()) break;
                    available = true;
                    break;
                }
                case 6: {
                    if (!t.isOnFriday()) break;
                    available = true;
                    break;
                }
                case 7: {
                    if (!t.isOnSaturday()) break;
                    available = true;
                }
            }
            if (!available) continue;
            cols = new Col();
            cols.setFrom(gc1.getTime().toString());
            cols.setTo(gc1.getTime().toString());
            cols.setDays(1);
            Long Hours = new Long(hours);
            cols.setCapacity(Hours.intValue());
            int C_UOM_ID = DB.getSQLValue(null, (String)"SELECT C_UOM_ID FROM M_Product WHERE S_Resource_ID = ? ", (int)r.getS_Resource_ID());
            MUOM oum = MUOM.get((Properties)this.getCtx(), (int)C_UOM_ID);
            if (oum.isHour()) {
                Timestamp date = new Timestamp(gc1.getTimeInMillis());
                int seconds = this.getLoad(r.getS_Resource_ID(), date, date);
                cols.setLoad(seconds / 3600);
            }
            cols.setSummary(summary + cols.getDifference());
            summary = cols.getSummary();
            list.add(col, cols);
        }
        col = 0;
        boolean newrow = true;
        Col[] lines = new Col[list.size()];
        for (int z = 0; z <= lines.length; ++z) {
        }
        for (int i = 0; i <= lines.length; ++i) {
            if (newrow) {
                X_T_MRP_CRP crp = new X_T_MRP_CRP(this.getCtx(), 0, null);
                crp.setDescription("CRP Resource" + r.getName());
            }
            switch (col) {
                case 0: {
                    ++col;
                }
                case 1: {
                    ++col;
                }
                case 2: {
                    ++col;
                }
                case 3: {
                    ++col;
                }
                case 4: {
                    ++col;
                }
                case 5: {
                    ++col;
                }
                case 6: {
                    ++col;
                }
                case 7: {
                    ++col;
                }
                case 8: {
                    ++col;
                }
                case 9: {
                    ++col;
                }
                case 10: {
                    ++col;
                }
                case 11: {
                    ++col;
                }
                case 12: {
                    col = 0;
                    newrow = true;
                }
            }
            ++col;
        }
    }

    int getLoad(int S_Resource_ID, Timestamp start, Timestamp end) {
        int load = 0;
        String sql = "SELECT SUM( CASE WHEN ow.DurationUnit = 's'  THEN 1 * (onode.QueuingTime + onode.SetupTime + (onode.Duration * (o.QtyOrdered - o.QtyDelivered - o.QtyScrap)) + onode.MovingTime + onode.WaitingTime) WHEN ow.DurationUnit = 'm' THEN 60 * (onode.QueuingTime + onode.SetupTime + (onode.Duration * (o.QtyOrdered - o.QtyDelivered - o.QtyScrap)) + onode.MovingTime + onode.WaitingTime) WHEN ow.DurationUnit = 'h'  THEN 3600 * (onode.QueuingTime + onode.SetupTime + (onode.Duration * (o.QtyOrdered - o.QtyDelivered - o.QtyScrap)) + onode.MovingTime + onode.WaitingTime) WHEN ow.DurationUnit = 'Y'  THEN 31536000 *  (onode.QueuingTime + onode.SetupTime + (onode.Duration * (o.QtyOrdered - o.QtyDelivered - o.QtyScrap)) + onode.MovingTime + onode.WaitingTime) WHEN ow.DurationUnit = 'M' THEN 2592000 * (onode.QueuingTime + onode.SetupTime + (onode.Duration * (o.QtyOrdered - o.QtyDelivered - o.QtyScrap)) + onode.MovingTime + onode.WaitingTime) WHEN ow.DurationUnit = 'D' THEN 86400 END ) AS Load FROM PP_Order_Node onode INNER JOIN PP_Order_Workflow ow ON (ow.PP_Order_Workflow_ID =  onode.PP_Order_Workflow_ID) INNER JOIN PP_Order o ON (o.PP_Order_ID = onode.PP_Order_ID)  WHERE onode. = ?  AND onode.DateStartSchedule => ? AND onode.DateFinishSchedule =< ? AND onode.AD_Client_ID = ?";
        try {
            CPreparedStatement pstmt = null;
            pstmt = DB.prepareStatement((String)sql, (String)this.get_TrxName());
            pstmt.setInt(1, S_Resource_ID);
            pstmt.setTimestamp(1, start);
            pstmt.setTimestamp(2, end);
            pstmt.setInt(3, this.AD_Client_ID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                load = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            return load;
        }
        catch (Exception e) {
            this.log.log(Level.SEVERE, "doIt - " + sql, (Throwable)e);
            return 0;
        }
    }

    private class Col {
        int Day = 0;
        String From = null;
        String To = null;
        int Capacity = 0;
        int Load = 0;
        int Summary = 0;

        void setDays(int day) {
            this.Day = day;
        }

        int getDays() {
            return this.Day;
        }

        void setCapacity(int capacity) {
            this.Capacity = capacity;
        }

        int getCapacity() {
            return this.Capacity;
        }

        void setLoad(int load) {
            this.Load = load;
        }

        int getLoad() {
            return this.Load;
        }

        int getDifference() {
            return this.Capacity - this.Load;
        }

        void setSummary(int summary) {
            this.Summary = summary;
        }

        int getSummary() {
            return this.Summary;
        }

        void setFrom(String from) {
            this.From = from;
        }

        String getFrom() {
            return this.From;
        }

        void setTo(String to) {
            this.To = to;
        }

        String getTo() {
            return this.To;
        }
    }
}

