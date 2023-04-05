/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.adempiere.util.Callback
 *  org.adempiere.webui.component.ConfirmPanel
 *  org.adempiere.webui.component.Grid
 *  org.adempiere.webui.component.GridFactory
 *  org.adempiere.webui.component.Label
 *  org.adempiere.webui.component.ListItem
 *  org.adempiere.webui.component.Listbox
 *  org.adempiere.webui.component.Row
 *  org.adempiere.webui.component.Rows
 *  org.adempiere.webui.editor.WNumberEditor
 *  org.adempiere.webui.panel.ADForm
 *  org.adempiere.webui.session.SessionManager
 *  org.adempiere.webui.window.FDialog
 *  org.compiere.model.MInvoice
 *  org.compiere.model.MInvoiceLine
 *  org.compiere.model.MOrder
 *  org.compiere.model.MOrderLine
 *  org.compiere.model.MProduct
 *  org.compiere.model.MProductPricing
 *  org.compiere.model.MProject
 *  org.compiere.model.MProjectLine
 *  org.compiere.model.MProjectPhase
 *  org.compiere.model.MProjectTask
 *  org.compiere.model.MRole
 *  org.compiere.model.Query
 *  org.compiere.util.CLogger
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.compiere.util.KeyNamePair
 *  org.compiere.util.Msg
 *  org.compiere.util.Trx
 *  org.zkoss.zk.ui.Component
 *  org.zkoss.zk.ui.event.Event
 *  org.zkoss.zk.ui.event.EventListener
 *  org.zkoss.zul.Caption
 *  org.zkoss.zul.Decimalbox
 *  org.zkoss.zul.Groupbox
 *  org.zkoss.zul.Hlayout
 *  org.zkoss.zul.Separator
 *  org.zkoss.zul.Space
 *  org.zkoss.zul.Tree
 *  org.zkoss.zul.TreeModel
 *  org.zkoss.zul.Treecol
 *  org.zkoss.zul.Treecols
 *  org.zkoss.zul.Treeitem
 *  org.zkoss.zul.TreeitemRenderer
 */
package org.libero.form;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Callback;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WNumberEditor;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPricing;
import org.compiere.model.MProject;
import org.compiere.model.MProjectLine;
import org.compiere.model.MProjectPhase;
import org.compiere.model.MProjectTask;
import org.compiere.model.MRole;
import org.compiere.model.Query;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.libero.bom.drop.ISupportRadioNode;
import org.libero.bom.drop.ProductBOMRendererListener;
import org.libero.bom.drop.ProductBOMTreeNode;
import org.libero.bom.drop.SupportRadioTreeModel;
import org.libero.bom.drop.SupportRadioTreeitemRenderer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;

public class WBOMDropConfigurator
extends ADForm
implements EventListener<Event>,
PropertyChangeListener {
    private static final long serialVersionUID = 8864346687201400591L;
    private MProduct m_product;
    private BigDecimal m_qty = Env.ONE;
    private Tree testProductBOMTree;
    private int m_bomLine = 0;
    private static CLogger log = CLogger.getCLogger(WBOMDropConfigurator.class);
    private ConfirmPanel confirmPanel = new ConfirmPanel(true);
    private Grid selectionPanel = GridFactory.newGridLayout();
    private Listbox productField = new Listbox();
    private Listbox priceListField = new Listbox();
    private Decimalbox productQty = new Decimalbox();
    private Listbox orderField = new Listbox();
    private Listbox invoiceField = new Listbox();
    private Listbox projectPhaseField = new Listbox();
    private Listbox projectTaskField = new Listbox();
    private Listbox projectField = new Listbox();
    private Label totalDisplay = new Label();
    private Groupbox grpSelectionPanel = new Groupbox();
    Integer lineCount = 0;

    protected void initForm() {
        log.info("");
        try {
            this.confirmPanel = new ConfirmPanel(true);
            this.createSelectionPanel(true, true, true);
            this.createMainPanel();
            this.confirmPanel.addActionListener("onClick", (EventListener)this);
        }
        catch (Exception e) {
            log.log(Level.SEVERE, "", (Throwable)e);
        }
    }

    public void dispose() {
        if (this.selectionPanel != null) {
            this.selectionPanel.getChildren().clear();
        }
        this.selectionPanel = null;
    }

    private void createSelectionPanel(boolean order, boolean invoice, boolean project) {
        Caption caption = new Caption(Msg.translate((Properties)Env.getCtx(), (String)"Selection"));
        this.grpSelectionPanel.appendChild((Component)caption);
        this.grpSelectionPanel.appendChild((Component)this.selectionPanel);
        this.productField.setRows(1);
        this.productField.setMold("select");
        KeyNamePair[] keyNamePair = this.getProducts();
        for (int i = 0; i < keyNamePair.length; ++i) {
            this.productField.addItem(keyNamePair[i]);
        }
        Rows rows = this.selectionPanel.newRows();
        Row boxProductQty = rows.newRow();
        Label lblProduct = new Label(Msg.translate((Properties)Env.getCtx(), (String)"M_Product_ID"));
        Label lblQty = new Label(Msg.translate((Properties)Env.getCtx(), (String)"Qty"));
        this.productQty.setValue(Env.ONE);
        this.productField.addEventListener("onSelect", (EventListener)this);
        this.productQty.addEventListener("onChange", (EventListener)this);
        this.productField.setWidth("99%");
        boxProductQty.appendChild(lblProduct.rightAlign());
        boxProductQty.appendChild((Component)this.productField);
        boxProductQty.appendChild(lblQty.rightAlign());
        boxProductQty.appendChild((Component)this.productQty);
        if (order) {
            int i;
            keyNamePair = this.getOrders();
            this.orderField.setRows(1);
            this.orderField.setMold("select");
            this.orderField.setWidth("99%");
            for (i = 0; i < keyNamePair.length; ++i) {
                this.orderField.addItem(keyNamePair[i]);
            }
            keyNamePair = this.getPriceList();
            this.priceListField.setRows(1);
            this.priceListField.setMold("select");
            this.priceListField.setWidth("99%");
            for (i = 0; i < keyNamePair.length; ++i) {
                this.priceListField.addItem(keyNamePair[i]);
            }
            Label lblOrder = new Label(Msg.translate((Properties)Env.getCtx(), (String)"C_Order_ID"));
            Label lblPriceList = new Label(Msg.translate((Properties)Env.getCtx(), (String)"Price"));
            Row boxOrder = rows.newRow();
            this.orderField.addEventListener("onClick", (EventListener)this);
            this.priceListField.addEventListener("onClick", (EventListener)this);
            boxOrder.appendChild(lblOrder.rightAlign());
            boxOrder.appendChild((Component)this.orderField);
            boxOrder.appendChild(lblPriceList.rightAlign());
            boxOrder.appendChild((Component)this.priceListField);
        }
        if (invoice) {
            this.invoiceField.setRows(1);
            this.invoiceField.setMold("select");
            this.invoiceField.setWidth("99%");
            keyNamePair = this.getInvoices();
            for (int i = 0; i < keyNamePair.length; ++i) {
                this.invoiceField.addItem(keyNamePair[i]);
            }
            Label lblInvoice = new Label(Msg.translate((Properties)Env.getCtx(), (String)"C_Invoice_ID"));
            Row boxInvoices = rows.newRow();
            this.invoiceField.addEventListener("onSelect", (EventListener)this);
            boxInvoices.appendChild(lblInvoice.rightAlign());
            boxInvoices.appendChild((Component)this.invoiceField);
            boxInvoices.appendChild((Component)new Space());
            boxInvoices.appendChild((Component)new Space());
        }
        if (project) {
            this.projectField.setRows(1);
            this.projectField.setMold("select");
            this.projectField.setWidth("99%");
            keyNamePair = this.getProjects();
            for (int i = 0; i < keyNamePair.length; ++i) {
                this.projectField.addItem(keyNamePair[i]);
            }
            Label lblProject = new Label(Msg.translate((Properties)Env.getCtx(), (String)"C_Project_ID"));
            Row boxProject = rows.newRow();
            this.projectField.addEventListener("onSelect", (EventListener)this);
            boxProject.appendChild(lblProject.rightAlign());
            boxProject.appendChild((Component)this.projectField);
            boxProject.appendChild((Component)new Space());
            boxProject.appendChild((Component)new Space());
            this.projectPhaseField.setRows(1);
            this.projectPhaseField.setMold("select");
            this.projectPhaseField.setWidth("99%");
            keyNamePair = this.getProjectPhases();
            for (int i = 0; i < keyNamePair.length; ++i) {
                this.projectPhaseField.addItem(keyNamePair[i]);
            }
            Label lblProjectPhase = new Label(Msg.translate((Properties)Env.getCtx(), (String)"C_ProjectPhase_ID"));
            Row boxProjectPhase = rows.newRow();
            this.projectPhaseField.addEventListener("onSelect", (EventListener)this);
            boxProjectPhase.appendChild(lblProjectPhase.rightAlign());
            boxProjectPhase.appendChild((Component)this.projectPhaseField);
            this.projectTaskField.setRows(1);
            this.projectTaskField.setMold("select");
            this.projectTaskField.setWidth("99%");
            keyNamePair = this.getProjectTasks();
            for (int i = 0; i < keyNamePair.length; ++i) {
                this.projectTaskField.addItem(keyNamePair[i]);
            }
            Label lblProjectTask = new Label(Msg.translate((Properties)Env.getCtx(), (String)"C_ProjectTask_ID"));
            this.projectTaskField.addEventListener("onSelect", (EventListener)this);
            boxProjectPhase.appendChild(lblProjectTask.rightAlign());
            boxProjectPhase.appendChild((Component)this.projectTaskField);
        }
        this.confirmPanel.setEnabled("Ok", false);
    }

    private KeyNamePair[] getProjectTasks() {
        String sql = "SELECT C_ProjectTask_ID, Name FROM C_ProjectTask WHERE IsActive='Y'";
        return DB.getKeyNamePairs((String)MRole.getDefault().addAccessSQL(sql, "C_ProjectTask", false, false), (boolean)true);
    }

    private KeyNamePair[] getProjectPhases() {
        String sql = "SELECT C_ProjectPhase_ID, Name FROM C_ProjectPhase WHERE IsActive='Y'";
        return DB.getKeyNamePairs((String)MRole.getDefault().addAccessSQL(sql, "C_ProjectPhase", false, false), (boolean)true);
    }

    private KeyNamePair[] getProducts() {
        String sql = "SELECT M_Product_ID, Name FROM M_Product WHERE IsBOM='Y' AND IsVerified='Y' AND IsActive='Y' ORDER BY Name";
        return DB.getKeyNamePairs((String)MRole.getDefault().addAccessSQL(sql, "M_Product", false, false), (boolean)true);
    }

    private KeyNamePair[] getOrders() {
        String sql = "SELECT C_Order_ID, DocumentNo || '_' || GrandTotal FROM C_Order WHERE Processed='N' AND DocStatus='DR' ORDER BY DocumentNo";
        return DB.getKeyNamePairs((String)MRole.getDefault().addAccessSQL(sql, "C_Order", false, false), (boolean)true);
    }

    private KeyNamePair[] getPriceList() {
        String sql = "SELECT M_PriceList_Version_ID, Name  FROM M_PriceList_Version WHERE IsActive='Y'";
        return DB.getKeyNamePairs((String)MRole.getDefault().addAccessSQL(sql, "M_PriceList_Version", false, false), (boolean)true);
    }

    private KeyNamePair[] getProjects() {
        String sql = "SELECT C_Project_ID, Name FROM C_Project WHERE Processed='N' AND IsSummary='N' AND IsActive='Y' AND ProjectCategory<>'S' ORDER BY Name";
        return DB.getKeyNamePairs((String)MRole.getDefault().addAccessSQL(sql, "C_Project", false, false), (boolean)true);
    }

    private KeyNamePair[] getInvoices() {
        String sql = "SELECT C_Invoice_ID, DocumentNo || '_' || GrandTotal FROM C_Invoice WHERE Processed='N' AND DocStatus='DR' ORDER BY DocumentNo";
        return DB.getKeyNamePairs((String)MRole.getDefault().addAccessSQL(sql, "C_Invoice", false, false), (boolean)true);
    }

    private void createMainPanel() {
        if (log.isLoggable(Level.CONFIG)) {
            log.config(": " + (Object)this.m_product);
        }
        this.getChildren().clear();
        ProductBOMRendererListener.setGrandTotal(Env.ZERO);
        this.appendChild((Component)new Separator());
        this.appendChild((Component)this.grpSelectionPanel);
        this.appendChild((Component)new Separator());
        Hlayout row = new Hlayout();
        this.totalDisplay.setValue(String.valueOf(Msg.translate((Properties)Env.getCtx(), (String)"GrandTotal")) + " " + Msg.translate((Properties)Env.getCtx(), (String)"Price") + ": " + ProductBOMRendererListener.getGrandTotal());
        this.totalDisplay.setStyle("font-size:32px;color:gray;font-weight: bold;");
        row.appendChild((Component)this.totalDisplay);
        row.appendChild((Component)this.confirmPanel);
        row.setStyle("text-align:right");
        this.appendChild((Component)row);
        this.appendChild((Component)new Separator());
        this.setBorder("normal");
        this.setContentStyle("overflow: auto");
        if (this.m_product != null && this.m_product.get_ID() > 0) {
            if (this.m_product.getDescription() == null || this.m_product.getDescription().length() > 0) {
                // empty if block
            }
            this.m_bomLine = 0;
            this.testProductBOMTree = new Tree();
            this.testProductBOMTree.appendChild((Component)new Treecols());
            this.testProductBOMTree.getTreecols().appendChild((Component)new Treecol(this.m_product.getName()));
            SupportRadioTreeModel model = new SupportRadioTreeModel(new ProductBOMTreeNode(this.m_product, this.m_qty));
            SupportRadioTreeitemRenderer renderer = new SupportRadioTreeitemRenderer();
            renderer.isOpen = true;
            ProductBOMRendererListener rendererListener = new ProductBOMRendererListener();
            rendererListener.setTree(this.testProductBOMTree);
            rendererListener.addPropertyChangeListener(this);
            renderer.setRendererListener(rendererListener);
            this.testProductBOMTree.setItemRenderer((TreeitemRenderer)renderer);
            this.testProductBOMTree.setModel((TreeModel)model);
            this.appendChild((Component)this.testProductBOMTree);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("GrandTotal")) {
            this.totalDisplay.setValue(String.valueOf(Msg.translate((Properties)Env.getCtx(), (String)"GrandTotal")) + " " + Msg.translate((Properties)Env.getCtx(), (String)"Price") + ": " + ProductBOMRendererListener.getGrandTotal());
        }
    }

    public void onEvent(Event e) throws Exception {
        boolean OK;
        boolean valid;
        KeyNamePair pp;
        ListItem listitem;
        Component source;
        if (log.isLoggable(Level.CONFIG)) {
            log.config(e.getName());
        }
        if ((source = e.getTarget()) == this.productField || source == this.productQty) {
            this.m_qty = this.productQty.getValue();
            if (source == this.productQty && this.testProductBOMTree != null) {
                ((ProductBOMTreeNode)this.testProductBOMTree.getModel().getRoot()).setQty(this.m_qty);
            }
            listitem = this.productField.getSelectedItem();
            pp = null;
            if (listitem != null) {
                pp = listitem.toKeyNamePair();
            }
            this.m_product = pp != null ? MProduct.get((Properties)Env.getCtx(), (int)pp.getKey()) : null;
            this.createMainPanel();
        } else if (source == this.priceListField) {
            listitem = this.priceListField.getSelectedItem();
            pp = null;
            if (listitem != null) {
                pp = listitem.toKeyNamePair();
            }
            ProductBOMTreeNode.PriceListVersion = pp.getKey();
        } else if (source == this.orderField) {
            listitem = this.orderField.getSelectedItem();
            pp = null;
            if (listitem != null) {
                pp = listitem.toKeyNamePair();
            }
            boolean bl = valid = pp != null && pp.getKey() > 0;
            if (this.invoiceField != null) {
                this.invoiceField.setEnabled(!valid);
            }
            if (this.projectField != null) {
                this.projectField.setEnabled(!valid);
                this.projectPhaseField.setEnabled(!valid);
                this.projectTaskField.setEnabled(!valid);
            }
        } else if (source == this.invoiceField) {
            listitem = this.invoiceField.getSelectedItem();
            pp = null;
            if (listitem != null) {
                pp = listitem.toKeyNamePair();
            }
            boolean bl = valid = pp != null && pp.getKey() > 0;
            if (this.orderField != null) {
                this.orderField.setEnabled(!valid);
            }
            if (this.projectField != null) {
                this.projectField.setEnabled(!valid);
            }
        } else if (source == this.projectField) {
            listitem = this.projectField.getSelectedItem();
            pp = null;
            if (listitem != null) {
                pp = listitem.toKeyNamePair();
            }
            boolean bl = valid = pp != null && pp.getKey() > 0;
            if (this.orderField != null) {
                this.orderField.setEnabled(!valid);
            }
            if (this.invoiceField != null) {
                this.invoiceField.setEnabled(!valid);
            }
        } else if (this.confirmPanel.getButton("Ok").equals((Object)e.getTarget())) {
            if (this.onSave()) {
                SessionManager.getAppDesktop().closeActiveWindow();
            }
        } else if (this.confirmPanel.getButton("Cancel").equals((Object)e.getTarget())) {
            SessionManager.getAppDesktop().closeActiveWindow();
        } else {
            super.onEvent(e);
        }
        boolean bl = OK = this.m_product != null;
        if (OK) {
            ListItem listitem2;
            pp = null;
            if (this.orderField != null && (listitem2 = this.orderField.getSelectedItem()) != null) {
                pp = listitem2.toKeyNamePair();
            }
            if ((pp == null || pp.getKey() <= 0) && this.invoiceField != null && (listitem = this.invoiceField.getSelectedItem()) != null) {
                pp = listitem.toKeyNamePair();
            }
            if ((pp == null || pp.getKey() <= 0) && this.projectField != null && (listitem = this.projectField.getSelectedItem()) != null) {
                pp = listitem.toKeyNamePair();
            }
            OK = pp != null && pp.getKey() > 0;
        }
        this.confirmPanel.setEnabled("Ok", OK);
    }

    private boolean onSave() {
        String trxName = Trx.createTrxName((String)"BDP");
        try (Trx localTrx = Trx.get((String)trxName, (boolean)true);){
            if (this.cmd_save(localTrx)) {
                localTrx.commit();
                return true;
            }
            localTrx.rollback();
            return false;
        }
    }

    private boolean cmd_save(Trx trx) {
        ListItem listitem = this.orderField.getSelectedItem();
        KeyNamePair pp = null;
        if (listitem != null) {
            pp = listitem.toKeyNamePair();
        }
        if (pp != null && pp.getKey() > 0) {
            return this.cmd_saveOrder(pp.getKey(), trx);
        }
        listitem = this.invoiceField.getSelectedItem();
        pp = null;
        if (listitem != null) {
            pp = listitem.toKeyNamePair();
        }
        if (pp != null && pp.getKey() > 0) {
            return this.cmd_saveInvoice(pp.getKey(), trx);
        }
        listitem = this.projectField.getSelectedItem();
        pp = null;
        if (listitem != null) {
            pp = listitem.toKeyNamePair();
        }
        if (pp != null && pp.getKey() > 0) {
            return this.cmd_saveProject(pp.getKey(), trx);
        }
        listitem = this.projectPhaseField.getSelectedItem();
        pp = null;
        if (listitem != null) {
            pp = listitem.toKeyNamePair();
        }
        if (pp != null && pp.getKey() > 0) {
            return this.cmd_saveProjectPhase(pp.getKey(), trx);
        }
        listitem = this.projectTaskField.getSelectedItem();
        pp = null;
        if (listitem != null) {
            pp = listitem.toKeyNamePair();
        }
        if (pp != null && pp.getKey() > 0) {
            return this.cmd_saveProjectTask(pp.getKey(), trx);
        }
        log.log(Level.SEVERE, "Nothing selected");
        return false;
    }

    protected void travellerTreenode(Tree tree, ISupportRadioNode nodeModel, boolean isRootNode, Callback<TreeItemData> processNode) {
        if (!isRootNode && !nodeModel.isChecked()) {
            return;
        }
        ProductBOMTreeNode node = (ProductBOMTreeNode)nodeModel;
        int[] nodePath = tree.getModel().getPath((Object)nodeModel);
        Treeitem treeItem = tree.renderItemByPath(nodePath);
        if (node.getChildCount() == 0) {
            processNode.onCallback((Object)new TreeItemData(nodeModel, (WNumberEditor)treeItem.getAttribute("qty_component")));
        }
        for (int i = 0; i < nodeModel.getChildCount(); ++i) {
            ISupportRadioNode childNode = nodeModel.getChild(i);
            this.travellerTreenode(tree, childNode, false, processNode);
        }
    }

    private boolean cmd_saveOrder(int C_Order_ID, final Trx trx) {
        MOrder order;
        if (log.isLoggable(Level.CONFIG)) {
            log.config("C_Order_ID=" + C_Order_ID);
        }
        if ((order = new MOrder(Env.getCtx(), C_Order_ID, trx != null ? trx.getTrxName() : null)).get_ID() == 0) {
            log.log(Level.SEVERE, "Not found - C_Order_ID=" + C_Order_ID);
            return false;
        }
        this.lineCount = 0;
        try {
            ISupportRadioNode productRootNode = (ISupportRadioNode)this.testProductBOMTree.getModel().getRoot();
            this.travellerTreenode(this.testProductBOMTree, productRootNode, true, new Callback<TreeItemData>(){

                public void onCallback(TreeItemData itemData) {
                    ProductBOMTreeNode productNode = (ProductBOMTreeNode)itemData.dataNode;
                    BigDecimal qty = productNode.getTotQty();
                    int M_Product_ID = productNode.getProductID();
                    MOrderLine ol = new MOrderLine(order);
                    ol.setM_Product_ID(M_Product_ID, true);
                    ol.setQty(qty);
                    ol.setPrice();
                    ol.setTax();
                    ol.saveEx(trx.getTrxName());
                    WBOMDropConfigurator.this.lineCount = WBOMDropConfigurator.this.lineCount + 1;
                }
            });
            order.saveEx(trx.getTrxName());
        }
        catch (Exception e) {
            log.log(Level.SEVERE, "Line not saved");
            if (trx != null) {
                trx.rollback();
            }
            throw new AdempiereException(e.getMessage());
        }
        FDialog.info((int)-1, (Component)this, (String)(String.valueOf(Msg.translate((Properties)Env.getCtx(), (String)"C_Order_ID")) + " : " + order.getDocumentInfo() + " , " + Msg.translate((Properties)Env.getCtx(), (String)"NoOfLines") + " " + Msg.translate((Properties)Env.getCtx(), (String)"Inserted") + " = " + this.lineCount));
        if (log.isLoggable(Level.CONFIG)) {
            log.config("#" + this.lineCount);
        }
        return true;
    }

    private boolean cmd_saveInvoice(int C_Invoice_ID, final Trx trx) {
        MInvoice invoice;
        if (log.isLoggable(Level.CONFIG)) {
            log.config("C_Invoice_ID=" + C_Invoice_ID);
        }
        if ((invoice = new MInvoice(Env.getCtx(), C_Invoice_ID, trx != null ? trx.getTrxName() : null)).get_ID() == 0) {
            log.log(Level.SEVERE, "Not found - C_Invoice_ID=" + C_Invoice_ID);
            return false;
        }
        this.lineCount = 0;
        try {
            ISupportRadioNode productRootNode = (ISupportRadioNode)this.testProductBOMTree.getModel().getRoot();
            this.travellerTreenode(this.testProductBOMTree, productRootNode, true, new Callback<TreeItemData>(){

                public void onCallback(TreeItemData itemData) {
                    ProductBOMTreeNode productNode = (ProductBOMTreeNode)itemData.dataNode;
                    BigDecimal qty = productNode.getTotQty();
                    int M_Product_ID = productNode.getProductID();
                    MInvoiceLine il = new MInvoiceLine(invoice);
                    il.setM_Product_ID(M_Product_ID, true);
                    il.setQty(qty);
                    il.setPrice();
                    il.setTax();
                    il.saveEx(trx.getTrxName());
                    WBOMDropConfigurator.this.lineCount = WBOMDropConfigurator.this.lineCount + 1;
                }
            });
            invoice.save(trx.getTrxName());
        }
        catch (Exception e) {
            log.log(Level.SEVERE, "Line not saved");
            if (trx != null) {
                trx.rollback();
            }
            throw new AdempiereException(e.getMessage());
        }
        FDialog.info((int)-1, (Component)this, (String)(String.valueOf(Msg.translate((Properties)Env.getCtx(), (String)"C_Invoice_ID")) + " : " + invoice.getDocumentInfo() + " , " + Msg.translate((Properties)Env.getCtx(), (String)"NoOfLines") + " " + Msg.translate((Properties)Env.getCtx(), (String)"Inserted") + " = " + this.lineCount));
        if (log.isLoggable(Level.CONFIG)) {
            log.config("#" + this.lineCount);
        }
        return true;
    }

    private boolean cmd_saveProject(int C_Project_ID, final Trx trx) {
        MProject project;
        if (log.isLoggable(Level.CONFIG)) {
            log.config("C_Project_ID=" + C_Project_ID);
        }
        if ((project = new MProject(Env.getCtx(), C_Project_ID, trx != null ? trx.getTrxName() : null)).get_ID() == 0) {
            log.log(Level.SEVERE, "Not found - C_Project_ID=" + C_Project_ID);
            return false;
        }
        this.lineCount = 0;
        try {
            ISupportRadioNode productRootNode = (ISupportRadioNode)this.testProductBOMTree.getModel().getRoot();
            this.travellerTreenode(this.testProductBOMTree, productRootNode, true, new Callback<TreeItemData>(){

                public void onCallback(TreeItemData itemData) {
                    ProductBOMTreeNode productNode = (ProductBOMTreeNode)itemData.dataNode;
                    BigDecimal qty = productNode.getTotQty();
                    int M_Product_ID = productNode.getProductID();
                    MProjectLine pl = new MProjectLine(project);
                    pl.setM_Product_ID(M_Product_ID);
                    pl.setPlannedQty(qty);
                    pl.setPlannedPrice(WBOMDropConfigurator.this.getStandardPrice(M_Product_ID, qty.doubleValue(), project));
                    pl.saveEx(trx.getTrxName());
                    WBOMDropConfigurator.this.lineCount = WBOMDropConfigurator.this.lineCount + 1;
                }
            });
            project.saveEx(trx.getTrxName());
            project.load(trx.getTrxName(), new String[0]);
        }
        catch (Exception e) {
            log.log(Level.SEVERE, "Line not saved");
            if (trx != null) {
                trx.rollback();
            }
            throw new AdempiereException(e.getMessage());
        }
        FDialog.info((int)-1, (Component)this, (String)(String.valueOf(Msg.translate((Properties)Env.getCtx(), (String)"C_Project_ID")) + " : " + project.getName() + " , " + Msg.translate((Properties)Env.getCtx(), (String)"NoOfLines") + " " + Msg.translate((Properties)Env.getCtx(), (String)"Inserted") + " = " + this.lineCount));
        if (log.isLoggable(Level.CONFIG)) {
            log.config("#" + this.lineCount);
        }
        return true;
    }

    private boolean cmd_saveProjectPhase(int C_ProjectPhase_ID, final Trx trx) {
        MProjectPhase phase;
        if (log.isLoggable(Level.CONFIG)) {
            log.config("C_ProjectPhase_ID=" + C_ProjectPhase_ID);
        }
        if ((phase = new MProjectPhase(Env.getCtx(), C_ProjectPhase_ID, trx != null ? trx.getTrxName() : null)).get_ID() == 0) {
            log.log(Level.SEVERE, "Not found - C_ProjectPhase_ID=" + C_ProjectPhase_ID);
            return false;
        }
        final MProject project = (MProject)new Query(Env.getCtx(), "C_Project", "C_Project_ID=?", phase.get_TrxName()).setParameters(new Object[]{phase.getC_Project_ID()}).first();
        this.lineCount = 0;
        try {
            ISupportRadioNode productRootNode = (ISupportRadioNode)this.testProductBOMTree.getModel().getRoot();
            this.travellerTreenode(this.testProductBOMTree, productRootNode, true, new Callback<TreeItemData>(){

                public void onCallback(TreeItemData itemData) {
                    ProductBOMTreeNode productNode = (ProductBOMTreeNode)itemData.dataNode;
                    BigDecimal qty = productNode.getTotQty();
                    int M_Product_ID = productNode.getProductID();
                    MProjectLine pl = new MProjectLine(project);
                    pl.setM_Product_ID(M_Product_ID);
                    pl.setPlannedQty(qty);
                    pl.setPlannedPrice(WBOMDropConfigurator.this.getStandardPrice(M_Product_ID, qty.doubleValue(), project));
                    pl.saveEx(trx.getTrxName());
                    WBOMDropConfigurator.this.lineCount = WBOMDropConfigurator.this.lineCount + 1;
                }
            });
            project.saveEx(trx.getTrxName());
            project.load(trx.getTrxName(), new String[0]);
        }
        catch (Exception e) {
            log.log(Level.SEVERE, "Line not saved");
            if (trx != null) {
                trx.rollback();
            }
            throw new AdempiereException(e.getMessage());
        }
        FDialog.info((int)-1, (Component)this, (String)(String.valueOf(Msg.translate((Properties)Env.getCtx(), (String)"C_Project_ID")) + " : " + project.getName() + " , " + Msg.translate((Properties)Env.getCtx(), (String)"NoOfLines") + " " + Msg.translate((Properties)Env.getCtx(), (String)"Inserted") + " = " + this.lineCount));
        if (log.isLoggable(Level.CONFIG)) {
            log.config("#" + this.lineCount);
        }
        return true;
    }

    private boolean cmd_saveProjectTask(int C_ProjectTask_ID, final Trx trx) {
        MProjectTask task;
        if (log.isLoggable(Level.CONFIG)) {
            log.config("C_ProjectTask_ID=" + C_ProjectTask_ID);
        }
        if ((task = new MProjectTask(Env.getCtx(), C_ProjectTask_ID, trx != null ? trx.getTrxName() : null)).get_ID() == 0) {
            log.log(Level.SEVERE, "Not found - C_ProjectTask_ID=" + C_ProjectTask_ID);
            return false;
        }
        MProjectPhase phase = (MProjectPhase)new Query(Env.getCtx(), "C_ProjectPhase", "C_ProjectPhase_ID=?", task.get_TrxName()).setParameters(new Object[]{task.getC_ProjectPhase_ID()}).first();
        final MProject project = (MProject)new Query(Env.getCtx(), "C_Project", "C_Project_ID=?", task.get_TrxName()).setParameters(new Object[]{phase.getC_Project_ID()}).first();
        this.lineCount = 0;
        try {
            ISupportRadioNode productRootNode = (ISupportRadioNode)this.testProductBOMTree.getModel().getRoot();
            this.travellerTreenode(this.testProductBOMTree, productRootNode, true, new Callback<TreeItemData>(){

                public void onCallback(TreeItemData itemData) {
                    ProductBOMTreeNode productNode = (ProductBOMTreeNode)itemData.dataNode;
                    BigDecimal qty = productNode.getTotQty();
                    int M_Product_ID = productNode.getProductID();
                    MProjectLine pl = new MProjectLine(project);
                    pl.setM_Product_ID(M_Product_ID);
                    pl.setPlannedQty(qty);
                    pl.setPlannedPrice(WBOMDropConfigurator.this.getStandardPrice(M_Product_ID, qty.doubleValue(), project));
                    pl.saveEx(trx.getTrxName());
                    WBOMDropConfigurator.this.lineCount = WBOMDropConfigurator.this.lineCount + 1;
                }
            });
            project.saveEx(trx.getTrxName());
            project.load(trx.getTrxName(), new String[0]);
        }
        catch (Exception e) {
            log.log(Level.SEVERE, "Line not saved");
            if (trx != null) {
                trx.rollback();
            }
            throw new AdempiereException(e.getMessage());
        }
        FDialog.info((int)-1, (Component)this, (String)(String.valueOf(Msg.translate((Properties)Env.getCtx(), (String)"C_Project_ID")) + " : " + project.getName() + " , " + Msg.translate((Properties)Env.getCtx(), (String)"NoOfLines") + " " + Msg.translate((Properties)Env.getCtx(), (String)"Inserted") + " = " + this.lineCount));
        if (log.isLoggable(Level.CONFIG)) {
            log.config("#" + this.lineCount);
        }
        return true;
    }

    private BigDecimal getStandardPrice(int M_Product_ID, Double plannedQty, MProject project) {
        MProductPricing pp = new MProductPricing(M_Product_ID, project.getC_BPartner_ID(), new BigDecimal(plannedQty), true);
        pp.setM_PriceList_ID(project.getM_PriceList_ID());
        if (pp.calculatePrice()) {
            return pp.getPriceStd();
        }
        return Env.ZERO;
    }

    class TreeItemData {
        ISupportRadioNode dataNode;
        WNumberEditor inputQty;

        public TreeItemData(ISupportRadioNode dataNode, WNumberEditor inputQty) {
            this.dataNode = dataNode;
            this.inputQty = inputQty;
        }
    }
}

