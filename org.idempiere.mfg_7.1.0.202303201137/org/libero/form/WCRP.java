/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.webui.component.Borderlayout
 *  org.adempiere.webui.component.ConfirmPanel
 *  org.adempiere.webui.component.Grid
 *  org.adempiere.webui.component.GridFactory
 *  org.adempiere.webui.component.Label
 *  org.adempiere.webui.component.Row
 *  org.adempiere.webui.component.Rows
 *  org.adempiere.webui.editor.WDateEditor
 *  org.adempiere.webui.editor.WSearchEditor
 *  org.adempiere.webui.panel.CustomForm
 *  org.adempiere.webui.panel.IFormController
 *  org.adempiere.webui.session.SessionManager
 *  org.compiere.model.Lookup
 *  org.compiere.model.MColumn
 *  org.compiere.model.MLookup
 *  org.compiere.model.MLookupFactory
 *  org.compiere.model.MResource
 *  org.compiere.model.MUOM
 *  org.compiere.util.Env
 *  org.compiere.util.Msg
 *  org.jfree.chart.ChartFactory
 *  org.jfree.chart.JFreeChart
 *  org.jfree.chart.encoders.EncoderUtil
 *  org.jfree.chart.plot.PlotOrientation
 *  org.jfree.data.category.CategoryDataset
 *  org.jfree.data.category.DefaultCategoryDataset
 *  org.zkoss.image.AImage
 *  org.zkoss.image.Image
 *  org.zkoss.zk.ui.Component
 *  org.zkoss.zk.ui.event.Event
 *  org.zkoss.zk.ui.event.EventListener
 *  org.zkoss.zul.Center
 *  org.zkoss.zul.Hbox
 *  org.zkoss.zul.Image
 *  org.zkoss.zul.North
 *  org.zkoss.zul.South
 */
package org.libero.form;

import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.webui.component.Borderlayout;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.session.SessionManager;
import org.compiere.model.Lookup;
import org.compiere.model.MColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MResource;
import org.compiere.model.MUOM;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.libero.form.CRP;
import org.libero.form.crp.CRPModel;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Center;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.North;
import org.zkoss.zul.South;

public class WCRP
extends CRP
implements IFormController,
EventListener {
    CustomForm m_frame = new CustomForm();
    Borderlayout mainLayout = new Borderlayout();
    private Grid northPanel = GridFactory.newGridLayout();
    private Hbox centerPanel = new Hbox();
    private Borderlayout centerLayout = new Borderlayout();
    private ConfirmPanel confirmPanel = new ConfirmPanel(true);
    private Hashtable hash = new Hashtable();
    private WSearchEditor resource = null;
    private Label resourceLabel = new Label();
    private WDateEditor dateFrom = new WDateEditor("DateFrom", true, false, true, "DateFrom");
    private Label dateFromLabel = new Label();
    private Hbox chartPanel = new Hbox();
    private org.zkoss.zul.Image chart = new org.zkoss.zul.Image();
    protected CRPModel model;

    public WCRP() {
        this.m_frame.setWidth("99%");
        this.m_frame.setHeight("100%");
        this.m_frame.setStyle("position: absolute; padding: 0; margin: 0");
        this.m_frame.appendChild((Component)this.mainLayout);
        this.mainLayout.setWidth("100%");
        this.mainLayout.setHeight("100%");
        this.mainLayout.setStyle("position: absolute");
        this.init();
    }

    public void dispose() {
        SessionManager.getAppDesktop().closeWindow(this.m_WindowNo);
    }

    private void fillPicks() throws Exception {
        Properties ctx = Env.getCtx();
        MLookup resourceL = MLookupFactory.get((Properties)ctx, (int)this.m_WindowNo, (int)0, (int)MColumn.getColumn_ID((String)"M_Product", (String)"S_Resource_ID"), (int)19);
        this.resource = new WSearchEditor("S_Resource_ID", false, false, true, (Lookup)resourceL);
    }

    public CustomForm getForm() {
        return this.m_frame;
    }

    public void init() {
        try {
            this.fillPicks();
            this.jbInit();
            North north = new North();
            north.appendChild((Component)this.northPanel);
            this.mainLayout.appendChild((Component)north);
            Center center = new Center();
            center.appendChild((Component)this.centerPanel);
            this.mainLayout.appendChild((Component)center);
            South south = new South();
            south.appendChild((Component)this.confirmPanel);
            this.mainLayout.appendChild((Component)south);
        }
        catch (Exception e) {
            log.log(Level.SEVERE, "VCRP.init", (Throwable)e);
        }
    }

    private void jbInit() throws Exception {
        this.resourceLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"S_Resource_ID"));
        this.dateFromLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"DateFrom"));
        Rows rows = new Rows();
        Row row = null;
        rows.setParent((Component)this.northPanel);
        row = rows.newRow();
        row.appendChild(this.resourceLabel.rightAlign());
        row.appendChild((Component)this.resource.getComponent());
        row.appendChild(this.dateFromLabel.rightAlign());
        row.appendChild((Component)this.dateFrom.getComponent());
        this.centerPanel.appendChild((Component)this.chartPanel);
        JFreeChart jchart = ChartFactory.createBarChart3D((String)"", (String)Msg.translate((Properties)Env.getCtx(), (String)"Days"), (String)Msg.translate((Properties)Env.getCtx(), (String)"Hours"), (CategoryDataset)new DefaultCategoryDataset(), (PlotOrientation)PlotOrientation.VERTICAL, (boolean)true, (boolean)true, (boolean)false);
        this.renderChart(jchart);
        this.confirmPanel.addActionListener((EventListener)this);
    }

    public void onEvent(Event event) throws Exception {
        String cmd = event.getTarget().getId();
        if (cmd.equals("Ok")) {
            Timestamp date = null;
            if (this.dateFrom.getValue() != null) {
                date = this.dateFrom.getValue();
            }
            int S_Resource_ID = 0;
            if (this.resource.getValue() != null) {
                S_Resource_ID = (Integer)this.resource.getValue();
            }
            if (date != null && S_Resource_ID != 0) {
                MResource r = MResource.get((Properties)Env.getCtx(), (int)S_Resource_ID);
                int uom_id = r.getResourceType().getC_UOM_ID();
                MUOM uom = MUOM.get((Properties)Env.getCtx(), (int)uom_id);
                CategoryDataset dataset = null;
                dataset = uom.isHour() ? this.createDataset(date, r) : this.createWeightDataset(date, r);
                String title = r.getName() != null ? r.getName() : "";
                title = String.valueOf(title) + " " + r.getDescription() != null ? r.getDescription() : "";
                JFreeChart jfreechart = this.createChart(dataset, title, uom);
                this.renderChart(jfreechart);
            }
        }
        if (cmd.equals("Cancel")) {
            this.dispose();
        }
    }

    private void renderChart(JFreeChart jchart) {
        BufferedImage bi = jchart.createBufferedImage(700, 500, 3, null);
        try {
            byte[] bytes = EncoderUtil.encode((BufferedImage)bi, (String)"png", (boolean)true);
            AImage image = new AImage("", bytes);
            this.chartPanel.removeChild((Component)this.chart);
            this.chart = new org.zkoss.zul.Image();
            this.chart.setContent((Image)image);
            this.chartPanel.appendChild((Component)this.chart);
            this.chartPanel.setVisible(true);
        }
        catch (Exception exception) {}
    }
}

