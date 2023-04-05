/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MResource
 *  org.compiere.model.MResourceType
 *  org.compiere.model.MUOM
 *  org.compiere.util.CLogger
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.compiere.util.Msg
 *  org.jfree.chart.ChartFactory
 *  org.jfree.chart.JFreeChart
 *  org.jfree.chart.plot.PlotOrientation
 *  org.jfree.data.category.CategoryDataset
 *  org.jfree.data.category.DefaultCategoryDataset
 */
package org.libero.form;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.Properties;
import org.compiere.model.MResource;
import org.compiere.model.MResourceType;
import org.compiere.model.MUOM;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.libero.form.crp.CRPDatasetFactory;
import org.libero.form.crp.CRPModel;

public class CRP {
    public int m_WindowNo = 0;
    public static CLogger log = CLogger.getCLogger(CRP.class);
    public int AD_Client_ID = Integer.parseInt(Env.getContext((Properties)Env.getCtx(), (String)"#AD_Client_ID"));
    protected CRPModel model;

    protected JFreeChart createChart(CategoryDataset dataset, String title, MUOM uom) {
        JFreeChart chart = ChartFactory.createBarChart3D((String)title, (String)" ", (String)" ", (CategoryDataset)dataset, (PlotOrientation)PlotOrientation.VERTICAL, (boolean)true, (boolean)true, (boolean)false);
        chart = uom == null || uom.isHour() ? ChartFactory.createBarChart3D((String)title, (String)Msg.translate((Properties)Env.getCtx(), (String)"Days"), (String)Msg.translate((Properties)Env.getCtx(), (String)"Hours"), (CategoryDataset)dataset, (PlotOrientation)PlotOrientation.VERTICAL, (boolean)true, (boolean)true, (boolean)false) : ChartFactory.createBarChart3D((String)title, (String)Msg.translate((Properties)Env.getCtx(), (String)"Days"), (String)Msg.translate((Properties)Env.getCtx(), (String)"Kilo"), (CategoryDataset)dataset, (PlotOrientation)PlotOrientation.VERTICAL, (boolean)true, (boolean)true, (boolean)false);
        return chart;
    }

    protected CategoryDataset createDataset(Timestamp start, MResource resource) {
        GregorianCalendar gc1 = new GregorianCalendar();
        gc1.setTimeInMillis(start.getTime());
        gc1.clear(14);
        gc1.clear(13);
        gc1.clear(12);
        gc1.clear(11);
        Timestamp date = start;
        String namecapacity = Msg.translate((Properties)Env.getCtx(), (String)"Capacity");
        String nameload = Msg.translate((Properties)Env.getCtx(), (String)"Load");
        String namesummary = Msg.translate((Properties)Env.getCtx(), (String)"Summary");
        MResourceType t = MResourceType.get((Properties)Env.getCtx(), (int)resource.getS_ResourceType_ID());
        int days = 1;
        long hours = t.getTimeSlotHours();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int C_UOM_ID = DB.getSQLValue(null, (String)"SELECT C_UOM_ID FROM M_Product WHERE S_Resource_ID = ? ", (int)resource.getS_Resource_ID());
        MUOM uom = MUOM.get((Properties)Env.getCtx(), (int)C_UOM_ID);
        if (!uom.isHour()) {
            return dataset;
        }
        long summary = 0L;
        while (days < 32) {
            String day = new String(new Integer(date.getDate()).toString());
            long HoursLoad = this.getLoad(resource, date).longValue();
            Long Hours = new Long(hours);
            switch (gc1.get(7)) {
                case 1: {
                    ++days;
                    if (t.isOnSunday()) {
                        dataset.addValue((double)hours, (Comparable)((Object)namecapacity), (Comparable)((Object)day));
                        dataset.addValue((double)HoursLoad, (Comparable)((Object)nameload), (Comparable)((Object)day));
                        dataset.addValue((double)summary, (Comparable)((Object)namesummary), (Comparable)((Object)day));
                        summary = summary + (long)Hours.intValue() - HoursLoad;
                        gc1.add(5, 1);
                        date = new Timestamp(gc1.getTimeInMillis());
                        break;
                    }
                    dataset.addValue(0.0, (Comparable)((Object)namecapacity), (Comparable)((Object)day));
                    dataset.addValue((double)HoursLoad, (Comparable)((Object)nameload), (Comparable)((Object)day));
                    dataset.addValue((double)summary, (Comparable)((Object)namesummary), (Comparable)((Object)day));
                    summary -= HoursLoad;
                    gc1.add(5, 1);
                    date = new Timestamp(gc1.getTimeInMillis());
                    break;
                }
                case 2: {
                    ++days;
                    if (t.isOnMonday()) {
                        dataset.addValue((double)hours, (Comparable)((Object)namecapacity), (Comparable)((Object)day));
                        dataset.addValue((double)HoursLoad, (Comparable)((Object)nameload), (Comparable)((Object)day));
                        dataset.addValue((double)summary, (Comparable)((Object)namesummary), (Comparable)((Object)day));
                        summary = summary + (long)Hours.intValue() - HoursLoad;
                        gc1.add(5, 1);
                        date = new Timestamp(gc1.getTimeInMillis());
                        break;
                    }
                    dataset.addValue(0.0, (Comparable)((Object)namecapacity), (Comparable)((Object)day));
                    dataset.addValue((double)HoursLoad, (Comparable)((Object)nameload), (Comparable)((Object)day));
                    dataset.addValue((double)summary, (Comparable)((Object)namesummary), (Comparable)((Object)day));
                    summary -= HoursLoad;
                    gc1.add(5, 1);
                    date = new Timestamp(gc1.getTimeInMillis());
                    break;
                }
                case 3: {
                    ++days;
                    if (t.isOnTuesday()) {
                        dataset.addValue((double)hours, (Comparable)((Object)namecapacity), (Comparable)((Object)day));
                        dataset.addValue((double)HoursLoad, (Comparable)((Object)nameload), (Comparable)((Object)day));
                        dataset.addValue((double)summary, (Comparable)((Object)namesummary), (Comparable)((Object)day));
                        summary = summary + (long)Hours.intValue() - HoursLoad;
                        gc1.add(5, 1);
                        date = new Timestamp(gc1.getTimeInMillis());
                        break;
                    }
                    dataset.addValue(0.0, (Comparable)((Object)namecapacity), (Comparable)((Object)day));
                    dataset.addValue((double)HoursLoad, (Comparable)((Object)nameload), (Comparable)((Object)day));
                    dataset.addValue((double)summary, (Comparable)((Object)namesummary), (Comparable)((Object)day));
                    summary -= HoursLoad;
                    gc1.add(5, 1);
                    date = new Timestamp(gc1.getTimeInMillis());
                    break;
                }
                case 4: {
                    ++days;
                    if (t.isOnWednesday()) {
                        dataset.addValue((double)hours, (Comparable)((Object)namecapacity), (Comparable)((Object)day));
                        dataset.addValue((double)HoursLoad, (Comparable)((Object)nameload), (Comparable)((Object)day));
                        dataset.addValue((double)summary, (Comparable)((Object)namesummary), (Comparable)((Object)day));
                        summary = summary + (long)Hours.intValue() - HoursLoad;
                        gc1.add(5, 1);
                        date = new Timestamp(gc1.getTimeInMillis());
                        break;
                    }
                    dataset.addValue(0.0, (Comparable)((Object)namecapacity), (Comparable)((Object)day));
                    dataset.addValue((double)HoursLoad, (Comparable)((Object)nameload), (Comparable)((Object)day));
                    dataset.addValue((double)summary, (Comparable)((Object)namesummary), (Comparable)((Object)day));
                    summary -= HoursLoad;
                    gc1.add(5, 1);
                    date = new Timestamp(gc1.getTimeInMillis());
                    break;
                }
                case 5: {
                    ++days;
                    if (t.isOnThursday()) {
                        dataset.addValue((double)hours, (Comparable)((Object)namecapacity), (Comparable)((Object)day));
                        dataset.addValue((double)HoursLoad, (Comparable)((Object)nameload), (Comparable)((Object)day));
                        dataset.addValue((double)summary, (Comparable)((Object)namesummary), (Comparable)((Object)day));
                        summary = summary + (long)Hours.intValue() - HoursLoad;
                        gc1.add(5, 1);
                        date = new Timestamp(gc1.getTimeInMillis());
                        break;
                    }
                    dataset.addValue(0.0, (Comparable)((Object)namecapacity), (Comparable)((Object)day));
                    dataset.addValue((double)HoursLoad, (Comparable)((Object)nameload), (Comparable)((Object)day));
                    dataset.addValue((double)summary, (Comparable)((Object)namesummary), (Comparable)((Object)day));
                    summary -= HoursLoad;
                    gc1.add(5, 1);
                    date = new Timestamp(gc1.getTimeInMillis());
                    break;
                }
                case 6: {
                    ++days;
                    if (t.isOnFriday()) {
                        dataset.addValue((double)hours, (Comparable)((Object)namecapacity), (Comparable)((Object)day));
                        dataset.addValue((double)HoursLoad, (Comparable)((Object)nameload), (Comparable)((Object)day));
                        dataset.addValue((double)summary, (Comparable)((Object)namesummary), (Comparable)((Object)day));
                        summary = summary + (long)Hours.intValue() - HoursLoad;
                        gc1.add(5, 1);
                        date = new Timestamp(gc1.getTimeInMillis());
                        break;
                    }
                    dataset.addValue(0.0, (Comparable)((Object)namecapacity), (Comparable)((Object)day));
                    dataset.addValue((double)HoursLoad, (Comparable)((Object)nameload), (Comparable)((Object)day));
                    dataset.addValue((double)summary, (Comparable)((Object)namesummary), (Comparable)((Object)day));
                    summary -= HoursLoad;
                    gc1.add(5, 1);
                    date = new Timestamp(gc1.getTimeInMillis());
                    break;
                }
                case 7: {
                    ++days;
                    if (t.isOnSaturday()) {
                        dataset.addValue((double)hours, (Comparable)((Object)namecapacity), (Comparable)((Object)day));
                        dataset.addValue((double)HoursLoad, (Comparable)((Object)nameload), (Comparable)((Object)day));
                        dataset.addValue((double)summary, (Comparable)((Object)namesummary), (Comparable)((Object)day));
                        summary = summary + (long)Hours.intValue() - HoursLoad;
                        gc1.add(5, 1);
                        date = new Timestamp(gc1.getTimeInMillis());
                        break;
                    }
                    dataset.addValue(0.0, (Comparable)((Object)namecapacity), (Comparable)((Object)day));
                    dataset.addValue((double)HoursLoad, (Comparable)((Object)nameload), (Comparable)((Object)day));
                    dataset.addValue((double)summary, (Comparable)((Object)namesummary), (Comparable)((Object)day));
                    summary -= HoursLoad;
                    gc1.add(5, 1);
                    date = new Timestamp(gc1.getTimeInMillis());
                }
            }
        }
        return dataset;
    }

    protected CategoryDataset createWeightDataset(Timestamp start, MResource rosource) {
        GregorianCalendar gc1 = new GregorianCalendar();
        gc1.setTimeInMillis(start.getTime());
        gc1.clear(14);
        gc1.clear(13);
        gc1.clear(12);
        gc1.clear(11);
        String namecapacity = Msg.translate((Properties)Env.getCtx(), (String)"Capacity");
        String nameload = Msg.translate((Properties)Env.getCtx(), (String)"Load");
        String namesummary = Msg.translate((Properties)Env.getCtx(), (String)"Summary");
        String namepossiblecapacity = "Possible Capacity";
        MResourceType t = MResourceType.get((Properties)Env.getCtx(), (int)rosource.getS_ResourceType_ID());
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double currentweight = DB.getSQLValue(null, (String)"SELECT SUM( (mo.qtyordered-mo.qtydelivered)*(SELECT mp.weight FROM M_Product mp WHERE  mo.m_product_id=mp.m_product_id )) FROM PP_Order mo WHERE AD_Client_ID=?", (int)rosource.getAD_Client_ID());
        double dailyCapacity = rosource.getDailyCapacity().doubleValue();
        double utilization = rosource.getPercentUtilization().doubleValue();
        double summary = 0.0;
        int day = 0;
        while (day < 32) {
            ++day;
            switch (gc1.get(7)) {
                case 1: {
                    if (t.isOnSunday()) {
                        currentweight -= dailyCapacity * utilization / 100.0;
                        summary += dailyCapacity * utilization / 100.0;
                        dataset.addValue(dailyCapacity, (Comparable)((Object)namepossiblecapacity), (Comparable)new Integer(day));
                        dataset.addValue(dailyCapacity * utilization / 100.0, (Comparable)((Object)namecapacity), (Comparable)new Integer(day));
                        break;
                    }
                    dataset.addValue(0.0, (Comparable)((Object)namepossiblecapacity), (Comparable)new Integer(day));
                    dataset.addValue(0.0, (Comparable)((Object)namecapacity), (Comparable)new Integer(day));
                    break;
                }
                case 2: {
                    if (t.isOnMonday()) {
                        currentweight -= dailyCapacity * utilization / 100.0;
                        summary += dailyCapacity * utilization / 100.0;
                        dataset.addValue(dailyCapacity, (Comparable)((Object)namepossiblecapacity), (Comparable)new Integer(day));
                        dataset.addValue(dailyCapacity * utilization / 100.0, (Comparable)((Object)namecapacity), (Comparable)new Integer(day));
                        break;
                    }
                    dataset.addValue(0.0, (Comparable)((Object)namepossiblecapacity), (Comparable)new Integer(day));
                    dataset.addValue(0.0, (Comparable)((Object)namecapacity), (Comparable)new Integer(day));
                    break;
                }
                case 3: {
                    if (t.isOnTuesday()) {
                        currentweight -= dailyCapacity * utilization / 100.0;
                        summary += dailyCapacity * utilization / 100.0;
                        dataset.addValue(dailyCapacity, (Comparable)((Object)namepossiblecapacity), (Comparable)new Integer(day));
                        dataset.addValue(dailyCapacity * utilization / 100.0, (Comparable)((Object)namecapacity), (Comparable)new Integer(day));
                        break;
                    }
                    dataset.addValue(0.0, (Comparable)((Object)namepossiblecapacity), (Comparable)new Integer(day));
                    dataset.addValue(0.0, (Comparable)((Object)namecapacity), (Comparable)new Integer(day));
                    break;
                }
                case 4: {
                    if (t.isOnWednesday()) {
                        currentweight -= dailyCapacity * utilization / 100.0;
                        summary += dailyCapacity * utilization / 100.0;
                        dataset.addValue(dailyCapacity, (Comparable)((Object)namepossiblecapacity), (Comparable)new Integer(day));
                        dataset.addValue(dailyCapacity * utilization / 100.0, (Comparable)((Object)namecapacity), (Comparable)new Integer(day));
                        break;
                    }
                    dataset.addValue(0.0, (Comparable)((Object)namepossiblecapacity), (Comparable)new Integer(day));
                    dataset.addValue(0.0, (Comparable)((Object)namecapacity), (Comparable)new Integer(day));
                    break;
                }
                case 5: {
                    if (t.isOnThursday()) {
                        currentweight -= dailyCapacity * utilization / 100.0;
                        summary += dailyCapacity * utilization / 100.0;
                        dataset.addValue(dailyCapacity, (Comparable)((Object)namepossiblecapacity), (Comparable)new Integer(day));
                        dataset.addValue(dailyCapacity * utilization / 100.0, (Comparable)((Object)namecapacity), (Comparable)new Integer(day));
                        break;
                    }
                    dataset.addValue(0.0, (Comparable)((Object)namepossiblecapacity), (Comparable)new Integer(day));
                    dataset.addValue(0.0, (Comparable)((Object)namecapacity), (Comparable)new Integer(day));
                    break;
                }
                case 6: {
                    if (t.isOnFriday()) {
                        currentweight -= dailyCapacity * utilization / 100.0;
                        summary += dailyCapacity * utilization / 100.0;
                        dataset.addValue(dailyCapacity, (Comparable)((Object)namepossiblecapacity), (Comparable)new Integer(day));
                        dataset.addValue(dailyCapacity * utilization / 100.0, (Comparable)((Object)namecapacity), (Comparable)new Integer(day));
                        break;
                    }
                    dataset.addValue(0.0, (Comparable)((Object)namepossiblecapacity), (Comparable)new Integer(day));
                    dataset.addValue(0.0, (Comparable)((Object)namecapacity), (Comparable)new Integer(day));
                    break;
                }
                case 7: {
                    if (t.isOnSaturday()) {
                        currentweight -= dailyCapacity * utilization / 100.0;
                        summary += dailyCapacity * utilization / 100.0;
                        dataset.addValue(dailyCapacity, (Comparable)((Object)namepossiblecapacity), (Comparable)new Integer(day));
                        dataset.addValue(dailyCapacity * utilization / 100.0, (Comparable)((Object)namecapacity), (Comparable)new Integer(day));
                        break;
                    }
                    dataset.addValue(0.0, (Comparable)((Object)namepossiblecapacity), (Comparable)new Integer(day));
                    dataset.addValue(0.0, (Comparable)((Object)namecapacity), (Comparable)new Integer(day));
                }
            }
            dataset.addValue(currentweight, (Comparable)((Object)nameload), (Comparable)new Integer(day));
            dataset.addValue(summary, (Comparable)((Object)namesummary), (Comparable)new Integer(day));
            gc1.add(5, 1);
        }
        return dataset;
    }

    private BigDecimal getLoad(MResource resource, Timestamp start) {
        this.model = CRPDatasetFactory.get(start, start, resource);
        return this.model.calculateLoad(start, resource, null);
    }
}

