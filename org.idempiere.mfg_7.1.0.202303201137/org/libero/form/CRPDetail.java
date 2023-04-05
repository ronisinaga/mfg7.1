/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MResource
 *  org.compiere.model.MUOM
 *  org.compiere.util.CLogger
 *  org.compiere.util.Env
 *  org.compiere.util.Msg
 *  org.jfree.chart.ChartFactory
 *  org.jfree.chart.JFreeChart
 *  org.jfree.chart.axis.CategoryAxis
 *  org.jfree.chart.axis.CategoryLabelPositions
 *  org.jfree.chart.labels.CategoryItemLabelGenerator
 *  org.jfree.chart.labels.ItemLabelAnchor
 *  org.jfree.chart.labels.ItemLabelPosition
 *  org.jfree.chart.labels.StandardCategoryItemLabelGenerator
 *  org.jfree.chart.plot.CategoryPlot
 *  org.jfree.chart.plot.PlotOrientation
 *  org.jfree.chart.renderer.category.BarRenderer3D
 *  org.jfree.data.category.CategoryDataset
 *  org.jfree.ui.TextAnchor
 */
package org.libero.form;

import java.awt.Color;
import java.awt.Paint;
import java.util.Properties;
import org.compiere.model.MResource;
import org.compiere.model.MUOM;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.TextAnchor;
import org.libero.tools.worker.SingleWorker;

public class CRPDetail {
    public SingleWorker worker;
    public static CLogger log = CLogger.getCLogger(CRPDetail.class);

    public JFreeChart createChart(CategoryDataset dataset, String title, MUOM uom) {
        JFreeChart chart = ChartFactory.createBarChart3D((String)title, (String)Msg.translate((Properties)Env.getCtx(), (String)"Day"), (String)Msg.translate((Properties)Env.getCtx(), (String)(uom == null ? "" : uom.getName())), (CategoryDataset)dataset, (PlotOrientation)PlotOrientation.VERTICAL, (boolean)true, (boolean)true, (boolean)false);
        chart.setBackgroundPaint((Paint)Color.WHITE);
        chart.setAntiAlias(true);
        chart.setBorderVisible(true);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint((Paint)Color.GRAY);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint((Paint)Color.GRAY);
        BarRenderer3D barrenderer = (BarRenderer3D)plot.getRenderer();
        barrenderer.setDrawBarOutline(false);
        barrenderer.setBaseItemLabelGenerator((CategoryItemLabelGenerator)new LabelGenerator());
        barrenderer.setBaseItemLabelsVisible(true);
        barrenderer.setSeriesPaint(0, (Paint)new Color(10, 80, 150, 128));
        barrenderer.setSeriesPaint(1, (Paint)new Color(180, 60, 50, 128));
        ItemLabelPosition itemlabelposition = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER);
        barrenderer.setPositiveItemLabelPosition(itemlabelposition);
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions((double)0.5235987755982988));
        return chart;
    }

    public MUOM getSourceUOM(Object value) {
        MResource r = this.getResource(value);
        int uom_id = r.getResourceType().getC_UOM_ID();
        return uom_id > 0 ? MUOM.get((Properties)Env.getCtx(), (int)uom_id) : null;
    }

    public MResource getResource(Object value) {
        MResource r = null;
        if (value != null) {
            r = MResource.get((Properties)Env.getCtx(), (int)((Integer)value));
        }
        return r;
    }

    public MUOM getTargetUOM(Object value) {
        MUOM u = null;
        if (value != null) {
            u = MUOM.get((Properties)Env.getCtx(), (int)((Integer)value));
        }
        return u;
    }

    class LabelGenerator
    extends StandardCategoryItemLabelGenerator {
        LabelGenerator() {
        }

        public String generateItemLabel(CategoryDataset categorydataset, int i, int j) {
            return categorydataset.getRowKey(i).toString();
        }
    }
}

