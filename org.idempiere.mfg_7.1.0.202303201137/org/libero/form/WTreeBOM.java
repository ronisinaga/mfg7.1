/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.webui.component.Checkbox
 *  org.adempiere.webui.component.ConfirmPanel
 *  org.adempiere.webui.component.Label
 *  org.adempiere.webui.component.ListModelTable
 *  org.adempiere.webui.component.Panel
 *  org.adempiere.webui.component.SimpleTreeModel
 *  org.adempiere.webui.component.WListbox
 *  org.adempiere.webui.editor.WSearchEditor
 *  org.adempiere.webui.event.WTableModelEvent
 *  org.adempiere.webui.event.WTableModelListener
 *  org.adempiere.webui.panel.ADForm
 *  org.adempiere.webui.panel.CustomForm
 *  org.adempiere.webui.panel.IFormController
 *  org.adempiere.webui.util.ZKUpdateUtil
 *  org.compiere.model.Lookup
 *  org.compiere.model.MColumn
 *  org.compiere.model.MLookup
 *  org.compiere.model.MLookupFactory
 *  org.compiere.model.MProduct
 *  org.compiere.util.KeyNamePair
 *  org.compiere.util.Language
 *  org.compiere.util.Msg
 *  org.eevolution.model.MPPProductBOM
 *  org.eevolution.model.MPPProductBOMLine
 *  org.zkoss.zk.ui.Component
 *  org.zkoss.zk.ui.HtmlBasedComponent
 *  org.zkoss.zk.ui.event.Event
 *  org.zkoss.zk.ui.event.EventListener
 *  org.zkoss.zul.Borderlayout
 *  org.zkoss.zul.Center
 *  org.zkoss.zul.DefaultTreeNode
 *  org.zkoss.zul.North
 *  org.zkoss.zul.South
 *  org.zkoss.zul.Space
 *  org.zkoss.zul.Tree
 *  org.zkoss.zul.TreeModel
 *  org.zkoss.zul.Treecol
 *  org.zkoss.zul.Treecols
 *  org.zkoss.zul.TreeitemRenderer
 *  org.zkoss.zul.West
 */
package org.libero.form;

import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.SimpleTreeModel;
import org.adempiere.webui.component.WListbox;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.event.WTableModelEvent;
import org.adempiere.webui.event.WTableModelListener;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.util.ZKUpdateUtil;
import org.compiere.model.Lookup;
import org.compiere.model.MColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MProduct;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;
import org.compiere.util.Msg;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.libero.form.TreeBOM;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Space;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.West;

public class WTreeBOM
extends TreeBOM
implements IFormController,
EventListener,
WTableModelListener {
    private static final long serialVersionUID = 8534705083972399511L;
    private int m_WindowNo = 0;
    private CustomForm m_frame = new CustomForm();
    private Tree m_tree = new Tree();
    private Borderlayout mainLayout = new Borderlayout();
    private Panel northPanel = new Panel();
    private Label labelProduct = new Label();
    private WSearchEditor fieldProduct;
    private Checkbox implosion = new Checkbox();
    private Label treeInfo = new Label();
    private Panel dataPane = new Panel();
    private Panel treePane = new Panel();
    private ConfirmPanel confirmPanel = new ConfirmPanel(true);
    private WListbox tableBOM = new WListbox();
    private Vector<Vector<Object>> dataBOM = new Vector();

    public WTreeBOM() {
        try {
            this.preInit();
            this.jbInit();
        }
        catch (Exception e) {
            log.log(Level.SEVERE, "VTreeBOM.init", (Throwable)e);
        }
    }

    private void loadTableBOM() {
        Vector<String> columnNames = new Vector<String>();
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"Select"));
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"IsActive"));
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"Line"));
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"ValidFrom"));
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"ValidTo"));
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"M_Product_ID"));
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"C_UOM_ID"));
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"IsQtyPercentage"));
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"QtyBatch"));
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"QtyBOM"));
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"IsCritical"));
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"LeadTimeOffset"));
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"Assay"));
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"Scrap"));
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"IssueMethod"));
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"BackflushGroup"));
        columnNames.add(Msg.translate((Properties)this.getCtx(), (String)"Forecast"));
        this.tableBOM.clear();
        this.tableBOM.getModel().removeTableModelListener((WTableModelListener)this);
        ListModelTable model = new ListModelTable(this.dataBOM);
        model.addTableModelListener((WTableModelListener)this);
        this.tableBOM.setData(model, columnNames);
        this.tableBOM.setColumnClass(0, Boolean.class, false);
        this.tableBOM.setColumnClass(1, Boolean.class, false);
        this.tableBOM.setColumnClass(2, Integer.class, false);
        this.tableBOM.setColumnClass(3, Timestamp.class, false);
        this.tableBOM.setColumnClass(4, Timestamp.class, false);
        this.tableBOM.setColumnClass(5, KeyNamePair.class, false);
        this.tableBOM.setColumnClass(6, KeyNamePair.class, false);
        this.tableBOM.setColumnClass(7, Boolean.class, false);
        this.tableBOM.setColumnClass(8, BigDecimal.class, false);
        this.tableBOM.setColumnClass(9, BigDecimal.class, false);
        this.tableBOM.setColumnClass(10, Boolean.class, false);
        this.tableBOM.setColumnClass(11, BigDecimal.class, false);
        this.tableBOM.setColumnClass(12, BigDecimal.class, false);
        this.tableBOM.setColumnClass(13, Integer.class, false);
        this.tableBOM.setColumnClass(14, String.class, false);
        this.tableBOM.setColumnClass(15, String.class, false);
        this.tableBOM.setColumnClass(16, BigDecimal.class, false);
        this.tableBOM.autoSize();
    }

    private void preInit() throws Exception {
        Properties ctx = this.getCtx();
        Language language = Language.getLoginLanguage();
        MLookup m_fieldProduct = MLookupFactory.get((Properties)ctx, (int)this.m_WindowNo, (int)MColumn.getColumn_ID((String)"M_Product", (String)"M_Product_ID"), (int)30, (Language)language, (String)"M_Product_ID", (int)0, (boolean)false, (String)" M_Product.IsSummary = 'N'");
        this.fieldProduct = new WSearchEditor("M_Product_ID", true, false, true, (Lookup)m_fieldProduct){

            public void setValue(Object value) {
                super.setValue(value);
                WTreeBOM.this.action_loadBOM();
            }
        };
        this.implosion.addActionListener((EventListener)this);
    }

    private void jbInit() {
        this.m_frame.setWidth("99%");
        this.m_frame.setHeight("100%");
        this.m_frame.setStyle("position: absolute; padding: 0; margin: 0");
        this.m_frame.appendChild((Component)this.mainLayout);
        this.mainLayout.setWidth("100%");
        this.mainLayout.setHeight("100%");
        this.mainLayout.setStyle("position: absolute");
        this.labelProduct.setText(Msg.getElement((Properties)this.getCtx(), (String)"M_Product_ID"));
        this.implosion.setText(Msg.getElement((Properties)this.getCtx(), (String)"Implosion"));
        North north = new North();
        this.mainLayout.appendChild((Component)north);
        north.appendChild((Component)this.northPanel);
        north.setHeight("28px");
        this.northPanel.appendChild((Component)this.labelProduct);
        this.northPanel.appendChild((Component)new Space());
        ZKUpdateUtil.setWidth((HtmlBasedComponent)this.fieldProduct.getComponent(), (String)"300px");
        this.northPanel.appendChild((Component)this.fieldProduct.getComponent());
        this.northPanel.appendChild((Component)new Space());
        this.northPanel.appendChild((Component)this.implosion);
        this.northPanel.appendChild((Component)new Space());
        this.northPanel.appendChild((Component)this.treeInfo);
        South south = new South();
        this.mainLayout.appendChild((Component)south);
        south.appendChild((Component)this.confirmPanel);
        this.confirmPanel.addActionListener((EventListener)this);
        West west = new West();
        this.mainLayout.appendChild((Component)west);
        west.setSplittable(true);
        west.appendChild((Component)this.treePane);
        this.treePane.appendChild((Component)this.m_tree);
        this.m_tree.setStyle("border: none");
        west.setWidth("25%");
        west.setAutoscroll(true);
        Center center = new Center();
        this.mainLayout.appendChild((Component)center);
        center.appendChild((Component)this.dataPane);
        this.dataPane.appendChild((Component)this.tableBOM);
        this.tableBOM.setVflex(true);
        this.tableBOM.setFixedLayout(true);
        center.setFlex(true);
        center.setAutoscroll(true);
    }

    public void dispose() {
        if (this.m_frame != null) {
            this.m_frame.dispose();
        }
        this.m_frame = null;
    }

    public void vetoableChange(PropertyChangeEvent e) {
        String name = e.getPropertyName();
        Object value = e.getNewValue();
        if (value == null) {
            return;
        }
        if (name.equals("M_Product_ID") && this.fieldProduct != null) {
            this.action_loadBOM();
        }
    }

    public void onEvent(Event event) throws Exception {
        if (event.getTarget().equals((Object)this.implosion)) {
            this.action_loadBOM();
        }
        if (event.getName().equals("onOK")) {
            this.action_loadBOM();
        }
        if (event.getName().equals("onCancel")) {
            this.dispose();
        }
    }

    private void action_loadBOM() {
        int M_Product_ID = this.getM_Product_ID();
        if (M_Product_ID == 0) {
            return;
        }
        MProduct product = MProduct.get((Properties)this.getCtx(), (int)M_Product_ID);
        DefaultTreeNode parent = new DefaultTreeNode((Object)this.productSummary(product, false), new ArrayList());
        this.dataBOM.clear();
        if (this.isImplosion()) {
            try {
                this.m_tree.setModel(null);
            }
            catch (Exception exception) {}
            if (this.m_tree.getTreecols() != null) {
                this.m_tree.getTreecols().detach();
            }
            if (this.m_tree.getTreefoot() != null) {
                this.m_tree.getTreefoot().detach();
            }
            if (this.m_tree.getTreechildren() != null) {
                this.m_tree.getTreechildren().detach();
            }
            for (MPPProductBOMLine bomline : MPPProductBOMLine.getByProduct((MProduct)product)) {
                parent.getChildren().add(this.parent(bomline));
            }
            Treecols treeCols = new Treecols();
            this.m_tree.appendChild((Component)treeCols);
            Treecol treeCol = new Treecol();
            treeCols.appendChild((Component)treeCol);
            SimpleTreeModel model = new SimpleTreeModel(parent);
            this.m_tree.setPageSize(-1);
            this.m_tree.setTreeitemRenderer((TreeitemRenderer)model);
            this.m_tree.setModel((TreeModel)model);
        } else {
            try {
                this.m_tree.setModel(null);
            }
            catch (Exception exception) {}
            if (this.m_tree.getTreecols() != null) {
                this.m_tree.getTreecols().detach();
            }
            if (this.m_tree.getTreefoot() != null) {
                this.m_tree.getTreefoot().detach();
            }
            if (this.m_tree.getTreechildren() != null) {
                this.m_tree.getTreechildren().detach();
            }
            for (MPPProductBOM bom : MPPProductBOM.getProductBOMs((MProduct)product)) {
                parent.getChildren().add(this.parent(bom));
            }
            Treecols treeCols = new Treecols();
            this.m_tree.appendChild((Component)treeCols);
            Treecol treeCol = new Treecol();
            treeCols.appendChild((Component)treeCol);
            SimpleTreeModel model = new SimpleTreeModel(parent);
            this.m_tree.setPageSize(-1);
            this.m_tree.setTreeitemRenderer((TreeitemRenderer)model);
            this.m_tree.setModel((TreeModel)model);
        }
        this.m_tree.addEventListener("onSelection", (EventListener)this);
        this.loadTableBOM();
    }

    public DefaultTreeNode parent(MPPProductBOMLine bomline) {
        MProduct M_Product = MProduct.get((Properties)this.getCtx(), (int)bomline.getM_Product_ID());
        MPPProductBOM bomproduct = new MPPProductBOM(this.getCtx(), bomline.getPP_Product_BOM_ID(), null);
        DefaultTreeNode parent = new DefaultTreeNode((Object)this.productSummary(M_Product, false), new ArrayList());
        Vector<Object> line = new Vector<Object>(17);
        line.add(new Boolean(false));
        line.add(new Boolean(true));
        line.add(new Integer(bomline.getLine()));
        line.add(bomline.getValidFrom());
        line.add(bomline.getValidTo());
        KeyNamePair pp = new KeyNamePair(M_Product.getM_Product_ID(), M_Product.getName());
        line.add((Object)pp);
        KeyNamePair uom = new KeyNamePair(bomline.getC_UOM_ID(), bomline.getC_UOM().getUOMSymbol());
        line.add((Object)uom);
        line.add(new Boolean(bomline.isQtyPercentage()));
        line.add(bomline.getQtyBatch());
        line.add(bomline.getQtyBOM() != null ? bomline.getQtyBOM() : new BigDecimal(0));
        line.add(new Boolean(bomline.isCritical()));
        line.add(bomline.getLeadTimeOffset());
        line.add(bomline.getAssay());
        line.add(bomline.getScrap());
        line.add(bomline.getIssueMethod());
        line.add(bomline.getBackflushGroup());
        line.add(bomline.getForecast());
        this.dataBOM.add(line);
        Iterator iterator = MPPProductBOM.getProductBOMs((MProduct)((MProduct)bomproduct.getM_Product())).iterator();
        if (iterator.hasNext()) {
            MPPProductBOM bom = (MPPProductBOM)iterator.next();
            MProduct component = MProduct.get((Properties)this.getCtx(), (int)bom.getM_Product_ID());
            return this.component(component);
        }
        return parent;
    }

    public DefaultTreeNode parent(MPPProductBOM bom) {
        DefaultTreeNode parent = new DefaultTreeNode((Object)this.productSummary(bom), new ArrayList());
        for (MPPProductBOMLine bomline : bom.getLines()) {
            MProduct component = MProduct.get((Properties)this.getCtx(), (int)bomline.getM_Product_ID());
            Vector<Object> line = new Vector<Object>(17);
            line.add(new Boolean(false));
            line.add(new Boolean(true));
            line.add(new Integer(bomline.getLine()));
            line.add(bomline.getValidFrom());
            line.add(bomline.getValidTo());
            KeyNamePair pp = new KeyNamePair(component.getM_Product_ID(), component.getName());
            line.add((Object)pp);
            KeyNamePair uom = new KeyNamePair(bomline.getC_UOM_ID(), bomline.getC_UOM().getUOMSymbol());
            line.add((Object)uom);
            line.add(new Boolean(bomline.isQtyPercentage()));
            line.add(bomline.getQtyBatch());
            line.add(bomline.getQtyBOM());
            line.add(new Boolean(bomline.isCritical()));
            line.add(bomline.getLeadTimeOffset());
            line.add(bomline.getAssay());
            line.add(bomline.getScrap());
            line.add(bomline.getIssueMethod());
            line.add(bomline.getBackflushGroup());
            line.add(bomline.getForecast());
            this.dataBOM.add(line);
            parent.getChildren().add(this.component(component));
        }
        return parent;
    }

    public DefaultTreeNode component(MProduct product) {
        if (this.isImplosion()) {
            DefaultTreeNode parent = new DefaultTreeNode((Object)this.productSummary(product, false), new ArrayList());
            for (MPPProductBOMLine bomline : MPPProductBOMLine.getByProduct((MProduct)product)) {
                parent.getChildren().add(this.parent(bomline));
            }
            return parent;
        }
        Iterator iterator = MPPProductBOM.getProductBOMs((MProduct)product).iterator();
        if (iterator.hasNext()) {
            MPPProductBOM bom = (MPPProductBOM)iterator.next();
            return this.parent(bom);
        }
        return new DefaultTreeNode((Object)this.productSummary(product, true), new ArrayList());
    }

    private int getM_Product_ID() {
        Integer Product = (Integer)this.fieldProduct.getValue();
        if (Product == null) {
            return 0;
        }
        return Product;
    }

    private boolean isImplosion() {
        return this.implosion.isSelected();
    }

    public ADForm getForm() {
        return this.m_frame;
    }

    public void tableChanged(WTableModelEvent event) {
    }
}

