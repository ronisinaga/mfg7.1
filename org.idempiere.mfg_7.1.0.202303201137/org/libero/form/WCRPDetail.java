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
 *  org.adempiere.webui.component.SimpleTreeModel
 *  org.adempiere.webui.editor.WDateEditor
 *  org.adempiere.webui.editor.WSearchEditor
 *  org.adempiere.webui.panel.CustomForm
 *  org.adempiere.webui.panel.IFormController
 *  org.compiere.model.Lookup
 *  org.compiere.model.MColumn
 *  org.compiere.model.MLookup
 *  org.compiere.model.MLookupFactory
 *  org.compiere.model.MResource
 *  org.compiere.util.Env
 *  org.compiere.util.Msg
 *  org.jfree.chart.JFreeChart
 *  org.jfree.chart.encoders.EncoderUtil
 *  org.zkoss.image.AImage
 *  org.zkoss.image.Image
 *  org.zkoss.zk.ui.Component
 *  org.zkoss.zk.ui.event.Event
 *  org.zkoss.zk.ui.event.EventListener
 *  org.zkoss.zul.Center
 *  org.zkoss.zul.DefaultTreeNode
 *  org.zkoss.zul.Hbox
 *  org.zkoss.zul.Image
 *  org.zkoss.zul.North
 *  org.zkoss.zul.South
 *  org.zkoss.zul.Tree
 *  org.zkoss.zul.TreeModel
 *  org.zkoss.zul.Treecol
 *  org.zkoss.zul.Treecols
 *  org.zkoss.zul.TreeitemRenderer
 *  org.zkoss.zul.West
 */
package org.libero.form;

import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.webui.component.Borderlayout;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.SimpleTreeModel;
import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;
import org.compiere.model.Lookup;
import org.compiere.model.MColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MResource;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.encoders.EncoderUtil;
import org.libero.form.CRPDetail;
import org.libero.form.crp.CRPDatasetFactory;
import org.libero.form.crp.CRPModel;
import org.libero.tools.worker.SingleWorker;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Center;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.West;

public class WCRPDetail
extends CRPDetail
implements IFormController,
EventListener {
    CustomForm m_frame = new CustomForm();
    private WSearchEditor resource;
    private WDateEditor dateFrom;
    private WDateEditor dateTo;
    private Hbox chartPanel = new Hbox();
    private org.zkoss.zul.Image chart = new org.zkoss.zul.Image();
    private Hbox treePanel = new Hbox();
    private Tree tree = new Tree();
    private Center center = new Center();
    private West west = new West();
    private Borderlayout mainLayout = new Borderlayout();
    private SingleWorker worker;
    protected CRPModel model;

    public void onEvent(Event event) throws Exception {
        String cmd = event.getTarget().getId();
        if (cmd.equals("Ok")) {
            this.handleActionEvent(event);
        }
        if (cmd.equals("Cancel")) {
            this.dispose();
        }
    }

    public WCRPDetail() {
        this.m_frame.setWidth("99%");
        this.m_frame.setHeight("100%");
        this.m_frame.setStyle("position: absolute; padding: 0; margin: 0");
        this.m_frame.appendChild((Component)this.mainLayout);
        this.mainLayout.setWidth("100%");
        this.mainLayout.setHeight("100%");
        this.mainLayout.setStyle("position: absolute");
        this.init();
    }

    public void init() {
        this.fillPicks();
        this.jbInit();
    }

    private void jbInit() {
        this.dateFrom = new WDateEditor("DateFrom", true, false, true, "DateFrom");
        this.dateTo = new WDateEditor("DateTo", true, false, true, "DateTo");
        Rows rows = new Rows();
        Row row = null;
        new GridFactory();
        Grid northPanel = GridFactory.newGridLayout();
        rows.setParent((Component)northPanel);
        row = rows.newRow();
        row.appendChild(new Label(Msg.translate((Properties)Env.getCtx(), (String)"S_Resource_ID")).rightAlign());
        row.appendChild((Component)this.resource.getComponent());
        row.appendChild(new Label(Msg.translate((Properties)Env.getCtx(), (String)"DateFrom")).rightAlign());
        row.appendChild((Component)this.dateFrom.getComponent());
        row.appendChild(new Label(Msg.translate((Properties)Env.getCtx(), (String)"DateTo")).rightAlign());
        row.appendChild((Component)this.dateTo.getComponent());
        ConfirmPanel confirmPanel = new ConfirmPanel(true);
        confirmPanel.addActionListener((EventListener)this);
        North north = new North();
        north.appendChild((Component)northPanel);
        this.mainLayout.appendChild((Component)north);
        South south = new South();
        south.appendChild((Component)confirmPanel);
        this.mainLayout.appendChild((Component)south);
    }

    private void fillPicks() {
        Properties ctx = Env.getCtx();
        MLookup resourceL = MLookupFactory.get((Properties)ctx, (int)0, (int)0, (int)MColumn.getColumn_ID((String)"S_Resource", (String)"S_Resource_ID"), (int)19);
        this.resource = new WSearchEditor("S_Resource_ID", false, false, true, (Lookup)resourceL);
    }

    private void handleActionEvent(Event e) {
        Timestamp df = this.getDateFrom();
        Timestamp dt = this.getDateTo();
        MResource r = this.getResource(this.resource.getValue());
        if (df != null && dt != null && r != null) {
            this.model = CRPDatasetFactory.get(df, dt, r);
            JFreeChart jfreechart = this.createChart(this.model.getDataset(), this.getChartTitle(), this.getSourceUOM(this.resource.getValue()));
            this.renderChart(jfreechart);
            this.tree = this.getTree();
            this.mainLayout.removeChild((Component)this.center);
            this.treePanel = new Hbox();
            this.treePanel.appendChild((Component)this.tree);
            this.tree.setStyle("border: none");
            this.center = new Center();
            this.center.appendChild((Component)this.treePanel);
            this.center.setAutoscroll(true);
            this.mainLayout.appendChild((Component)this.center);
        }
    }

    private Tree getTree() {
        Tree tree = new Tree();
        List nodes = this.model.getDataset().getColumnKeys();
        DefaultTreeNode root = new DefaultTreeNode((Object)this.getResource(this.resource.getValue()).getName(), new ArrayList());
        for (String node : nodes) {
            root.getChildren().add(new DefaultTreeNode((Object)node, new ArrayList()));
        }
        Treecols treeCols = new Treecols();
        tree.appendChild((Component)treeCols);
        Treecol treeCol = new Treecol();
        treeCols.appendChild((Component)treeCol);
        SimpleTreeModel model = new SimpleTreeModel(root);
        tree.setPageSize(-1);
        tree.setTreeitemRenderer((TreeitemRenderer)model);
        tree.setModel((TreeModel)model);
        return tree;
    }

    private String getChartTitle() {
        MResource r = this.getResource(this.resource.getValue());
        String title = r.getName() != null ? r.getName() : "";
        title = String.valueOf(title) + " " + r.getDescription() != null ? r.getDescription() : "";
        return title;
    }

    public Timestamp getDateFrom() {
        Timestamp t = null;
        if (this.dateFrom.getValue() != null) {
            t = this.dateFrom.getValue();
        }
        return t;
    }

    public Timestamp getDateTo() {
        Timestamp t = null;
        if (this.dateTo.getValue() != null) {
            t = this.dateTo.getValue();
        }
        return t;
    }

    private void renderChart(JFreeChart jchart) {
        BufferedImage bi = jchart.createBufferedImage(700, 500, 3, null);
        try {
            byte[] bytes = EncoderUtil.encode((BufferedImage)bi, (String)"png", (boolean)true);
            AImage image = new AImage("", bytes);
            this.mainLayout.removeChild((Component)this.west);
            this.chartPanel = new Hbox();
            this.chart = new org.zkoss.zul.Image();
            this.chart.setContent((Image)image);
            this.chartPanel.appendChild((Component)this.chart);
            this.west = new West();
            this.west.appendChild((Component)this.chartPanel);
            this.west.setSplittable(true);
            this.west.setSize("70%");
            this.west.setAutoscroll(true);
            this.west.setOpen(true);
            this.mainLayout.appendChild((Component)this.west);
        }
        catch (Exception e) {
            log.log(Level.SEVERE, "WCRP.init", (Object)e.getMessage());
        }
    }

    public void dispose() {
        this.m_frame.dispose();
    }

    public CustomForm getForm() {
        return this.m_frame;
    }
}

