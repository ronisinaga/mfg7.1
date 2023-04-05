/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.adempiere.webui.component.Button
 *  org.adempiere.webui.component.Combobox
 *  org.adempiere.webui.component.Grid
 *  org.adempiere.webui.component.GridFactory
 *  org.adempiere.webui.component.Label
 *  org.adempiere.webui.component.ListboxFactory
 *  org.adempiere.webui.component.Panel
 *  org.adempiere.webui.component.Row
 *  org.adempiere.webui.component.Rows
 *  org.adempiere.webui.component.Tab
 *  org.adempiere.webui.component.Tabbox
 *  org.adempiere.webui.component.Tabs
 *  org.adempiere.webui.component.Textbox
 *  org.adempiere.webui.component.WListbox
 *  org.adempiere.webui.editor.WDateEditor
 *  org.adempiere.webui.editor.WLocatorEditor
 *  org.adempiere.webui.editor.WNumberEditor
 *  org.adempiere.webui.editor.WPAttributeEditor
 *  org.adempiere.webui.editor.WSearchEditor
 *  org.adempiere.webui.event.ValueChangeEvent
 *  org.adempiere.webui.event.ValueChangeListener
 *  org.adempiere.webui.event.WTableModelEvent
 *  org.adempiere.webui.event.WTableModelListener
 *  org.adempiere.webui.panel.ADForm
 *  org.adempiere.webui.panel.CustomForm
 *  org.adempiere.webui.panel.IFormController
 *  org.adempiere.webui.session.SessionManager
 *  org.compiere.minigrid.IDColumn
 *  org.compiere.minigrid.IMiniTable
 *  org.compiere.model.GridField
 *  org.compiere.model.GridFieldVO
 *  org.compiere.model.Lookup
 *  org.compiere.model.MColumn
 *  org.compiere.model.MDocType
 *  org.compiere.model.MLocatorLookup
 *  org.compiere.model.MLookup
 *  org.compiere.model.MLookupFactory
 *  org.compiere.model.MProduct
 *  org.compiere.model.MTab
 *  org.compiere.model.MWindow
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.compiere.util.KeyNamePair
 *  org.compiere.util.Language
 *  org.compiere.util.Msg
 *  org.compiere.util.Trx
 *  org.compiere.util.TrxRunnable
 *  org.zkoss.zk.ui.Component
 *  org.zkoss.zk.ui.event.Event
 *  org.zkoss.zk.ui.event.EventListener
 *  org.zkoss.zul.Borderlayout
 *  org.zkoss.zul.Center
 *  org.zkoss.zul.Html
 *  org.zkoss.zul.Messagebox
 *  org.zkoss.zul.North
 *  org.zkoss.zul.South
 *  org.zkoss.zul.Space
 *  org.zkoss.zul.Tabpanel
 *  org.zkoss.zul.Tabpanels
 */
package org.libero.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Combobox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Tab;
import org.adempiere.webui.component.Tabbox;
import org.adempiere.webui.component.Tabs;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.component.WListbox;
import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.editor.WLocatorEditor;
import org.adempiere.webui.editor.WNumberEditor;
import org.adempiere.webui.editor.WPAttributeEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.event.WTableModelEvent;
import org.adempiere.webui.event.WTableModelListener;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.session.SessionManager;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldVO;
import org.compiere.model.Lookup;
import org.compiere.model.MColumn;
import org.compiere.model.MDocType;
import org.compiere.model.MLocatorLookup;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MProduct;
import org.compiere.model.MTab;
import org.compiere.model.MWindow;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.util.TrxRunnable;
import org.libero.form.OrderReceiptIssue;
import org.libero.model.MPPCostCollector;
import org.libero.model.MPPOrder;
import org.libero.model.MPPOrderNode;
import org.libero.model.MPPOrderWorkflow;
import org.libero.tables.I_PP_Order_Node;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Html;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Space;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;

public class WOrderReceiptIssue
extends OrderReceiptIssue
implements IFormController,
EventListener,
ValueChangeListener,
Serializable,
WTableModelListener {
    private static final long serialVersionUID = -3451096834043054791L;
    private int m_WindowNo = 0;
    private String m_sql;
    private MPPOrder m_PP_order = null;
    private Panel Generate = new Panel();
    private Panel PanelBottom = new Panel();
    private Panel mainPanel = new Panel();
    private Panel northPanel = new Panel();
    private Button Process = new Button();
    private Label attributeLabel = new Label();
    private Label orderedQtyLabel = new Label();
    private Label deliveredQtyLabel = new Label();
    private Label openQtyLabel = new Label();
    private Label orderLabel = new Label();
    private Label toDeliverQtyLabel = new Label();
    private Label movementDateLabel = new Label();
    private Label rejectQtyLabel = new Label();
    private Label resourceLabel = new Label();
    private Label DescriptionLabel = new Label();
    private CustomForm form = new CustomForm();
    private Borderlayout ReceiptIssueOrder = new Borderlayout();
    private Tabbox TabsReceiptsIssue = new Tabbox();
    private Html info = new Html();
    private Grid fieldGrid = GridFactory.newGridLayout();
    private WPAttributeEditor attribute = null;
    private Label warehouseLabel = new Label();
    private Label scrapQtyLabel = new Label();
    private Label productLabel = new Label(Msg.translate((Properties)Env.getCtx(), (String)"M_Product_ID"));
    private Label uomLabel = new Label(Msg.translate((Properties)Env.getCtx(), (String)"C_UOM_ID"));
    private Label uomorderLabel = new Label(Msg.translate((Properties)Env.getCtx(), (String)"Altert UOM"));
    private Label locatorLabel = new Label(Msg.translate((Properties)Env.getCtx(), (String)"M_Locator_ID"));
    private Label backflushGroupLabel = new Label(Msg.translate((Properties)Env.getCtx(), (String)"BackflushGroup"));
    private Label labelcombo = new Label(Msg.translate((Properties)Env.getCtx(), (String)"DeliveryRule"));
    private Label QtyBatchsLabel = new Label();
    private Label QtyBatchSizeLabel = new Label();
    private Textbox backflushGroup = new Textbox();
    private Textbox description = new Textbox();
    private WNumberEditor orderedQtyField = new WNumberEditor("QtyOrdered", false, false, false, 29, "QtyOrdered");
    private WNumberEditor deliveredQtyField = new WNumberEditor("QtyDelivered", false, false, false, 29, "QtyDelivered");
    private WNumberEditor openQtyField = new WNumberEditor("QtyOpen", false, false, false, 29, "QtyOpen");
    private WNumberEditor toDeliverQty = new WNumberEditor("QtyToDeliver", true, false, true, 29, "QtyToDeliver");
    private WNumberEditor rejectQty = new WNumberEditor("Qtyreject", false, false, true, 29, "QtyReject");
    private WNumberEditor scrapQtyField = new WNumberEditor("Qtyscrap", false, false, true, 29, "Qtyscrap");
    private WNumberEditor qtyBatchsField = new WNumberEditor("QtyBatchs", false, false, false, 29, "QtyBatchs");
    private WNumberEditor qtyBatchSizeField = new WNumberEditor("QtyBatchSize", false, false, false, 29, "QtyBatchSize");
    private WSearchEditor orderField = null;
    private WSearchEditor resourceField = null;
    private WSearchEditor warehouseField = null;
    private WSearchEditor productField = null;
    private WSearchEditor uomField = null;
    private WSearchEditor uomorderField = null;
    private WListbox issue = ListboxFactory.newDataTable();
    private WDateEditor movementDateField = new WDateEditor("MovementDate", true, false, true, "MovementDate");
    private WLocatorEditor locatorField = null;
    private Combobox pickcombo = new Combobox();

    public WOrderReceiptIssue() {
        Env.setContext((Properties)Env.getCtx(), (int)this.form.getWindowNo(), (String)"IsSOTrx", (String)"Y");
        try {
            this.fillPicks();
            this.jbInit();
            this.dynInit();
            this.pickcombo.addEventListener("onChange", (EventListener)this);
        }
        catch (Exception e) {
            throw new AdempiereException((Throwable)e);
        }
    }

    private void fillPicks() throws Exception {
        Properties ctx = Env.getCtx();
        Language language = Language.getLoginLanguage();
        MLookup orderLookup = MLookupFactory.get((Properties)ctx, (int)this.m_WindowNo, (int)MColumn.getColumn_ID((String)"PP_Order", (String)"PP_Order_ID"), (int)30, (Language)language, (String)"PP_Order_ID", (int)0, (boolean)false, (String)"PP_Order.DocStatus = 'CO'");
        this.orderField = new WSearchEditor("PP_Order_ID", false, false, true, (Lookup)orderLookup);
        this.orderField.addValueChangeListener((ValueChangeListener)this);
        MLookup resourceLookup = MLookupFactory.get((Properties)ctx, (int)this.m_WindowNo, (int)0, (int)MColumn.getColumn_ID((String)"PP_Order", (String)"S_Resource_ID"), (int)19);
        this.resourceField = new WSearchEditor("S_Resource_ID", false, false, false, (Lookup)resourceLookup);
        MLookup warehouseLookup = MLookupFactory.get((Properties)ctx, (int)this.m_WindowNo, (int)0, (int)MColumn.getColumn_ID((String)"PP_Order", (String)"M_Warehouse_ID"), (int)19);
        this.warehouseField = new WSearchEditor("M_Warehouse_ID", false, false, false, (Lookup)warehouseLookup);
        MLookup productLookup = MLookupFactory.get((Properties)ctx, (int)this.m_WindowNo, (int)0, (int)MColumn.getColumn_ID((String)"PP_Order", (String)"M_Product_ID"), (int)19);
        this.productField = new WSearchEditor("M_Product_ID", false, false, false, (Lookup)productLookup);
        MLookup uomLookup = MLookupFactory.get((Properties)ctx, (int)this.m_WindowNo, (int)0, (int)MColumn.getColumn_ID((String)"PP_Order", (String)"C_UOM_ID"), (int)19);
        this.uomField = new WSearchEditor("C_UOM_ID", false, false, false, (Lookup)uomLookup);
        MLookup uomOrderLookup = MLookupFactory.get((Properties)ctx, (int)this.m_WindowNo, (int)0, (int)MColumn.getColumn_ID((String)"PP_Order", (String)"C_UOM_ID"), (int)19);
        this.uomorderField = new WSearchEditor("C_UOM_ID", false, false, false, (Lookup)uomOrderLookup);
        MLocatorLookup locatorL = new MLocatorLookup(ctx, this.m_WindowNo);
        this.locatorField = new WLocatorEditor("M_Locator_ID", true, false, true, locatorL, this.m_WindowNo);
        int m_Window = MWindow.getWindow_ID((String)"Manufacturing Order");
        GridFieldVO vo = GridFieldVO.createStdField((Properties)ctx, (int)this.m_WindowNo, (int)0, (int)m_Window, (int)MTab.getTab_ID((int)m_Window, (String)"Manufacturing Order"), (boolean)false, (boolean)false, (boolean)false);
        vo.AD_Column_ID = MColumn.getColumn_ID((String)"PP_Order", (String)"M_AttributeSetInstance_ID");
        vo.ColumnName = "M_AttributeSetInstance_ID";
        vo.displayType = 35;
        GridField field = new GridField(vo);
        this.attribute = new WPAttributeEditor(field.getGridTab(), field);
        this.attribute.setValue((Object)0);
        this.scrapQtyField.setValue((Object)Env.ZERO);
        this.rejectQty.setValue((Object)Env.ZERO);
        this.pickcombo.appendItem(Msg.translate((Properties)Env.getCtx(), (String)"IsBackflush"), (Object)1);
        this.pickcombo.appendItem(Msg.translate((Properties)Env.getCtx(), (String)"OnlyIssue"), (Object)2);
        this.pickcombo.appendItem(Msg.translate((Properties)Env.getCtx(), (String)"OnlyReceipt"), (Object)3);
        this.pickcombo.addEventListener("onChange", (EventListener)this);
        this.Process.addActionListener((EventListener)this);
        this.toDeliverQty.addValueChangeListener((ValueChangeListener)this);
        this.scrapQtyField.addValueChangeListener((ValueChangeListener)this);
    }

    private void jbInit() throws Exception {
        Center center = new Center();
        South south = new South();
        North north = new North();
        this.form.appendChild((Component)this.mainPanel);
        this.mainPanel.appendChild((Component)this.TabsReceiptsIssue);
        this.mainPanel.setStyle("width: 100%; height: 100%; padding: 0; margin: 0");
        this.ReceiptIssueOrder.setWidth("100%");
        this.ReceiptIssueOrder.setHeight("99%");
        this.ReceiptIssueOrder.appendChild((Component)north);
        this.description.setWidth("100%");
        north.appendChild((Component)this.northPanel);
        this.northPanel.appendChild((Component)this.fieldGrid);
        this.orderLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"PP_Order_ID"));
        Rows tmpRows = this.fieldGrid.newRows();
        Row tmpRow = tmpRows.newRow();
        tmpRow.appendChild(this.orderLabel.rightAlign());
        tmpRow.appendChild((Component)this.orderField.getComponent());
        this.resourceLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"S_Resource_ID"));
        tmpRow.appendChild(this.resourceLabel.rightAlign());
        tmpRow.appendChild((Component)this.resourceField.getComponent());
        this.warehouseLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"M_Warehouse_ID"));
        tmpRow.appendChild(this.warehouseLabel.rightAlign());
        tmpRow.appendChild((Component)this.warehouseField.getComponent());
        tmpRow = tmpRows.newRow();
        tmpRow.appendChild(this.productLabel.rightAlign());
        tmpRow.appendChild((Component)this.productField.getComponent());
        tmpRow.appendChild(this.uomLabel.rightAlign());
        tmpRow.appendChild((Component)this.uomField.getComponent());
        tmpRow.appendChild(this.uomorderLabel.rightAlign());
        tmpRow.appendChild((Component)this.uomorderField.getComponent());
        tmpRow = tmpRows.newRow();
        this.orderedQtyLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"QtyOrdered"));
        tmpRow.appendChild(this.orderedQtyLabel.rightAlign());
        tmpRow.appendChild((Component)this.orderedQtyField.getComponent());
        this.deliveredQtyLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"QtyDelivered"));
        tmpRow.appendChild(this.deliveredQtyLabel.rightAlign());
        tmpRow.appendChild((Component)this.deliveredQtyField.getComponent());
        this.openQtyLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"QtyOpen"));
        tmpRow.appendChild(this.openQtyLabel.rightAlign());
        tmpRow.appendChild((Component)this.openQtyField.getComponent());
        tmpRow = tmpRows.newRow();
        tmpRow.appendChild(this.productLabel.rightAlign());
        tmpRow.appendChild((Component)this.productField.getComponent());
        tmpRow.appendChild(this.uomLabel.rightAlign());
        tmpRow.appendChild((Component)this.uomField.getComponent());
        tmpRow.appendChild(this.uomorderLabel.rightAlign());
        tmpRow.appendChild((Component)this.uomorderField.getComponent());
        tmpRow = tmpRows.newRow();
        this.QtyBatchsLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"QtyBatchs"));
        tmpRow.appendChild(this.QtyBatchsLabel.rightAlign());
        tmpRow.appendChild((Component)this.qtyBatchsField.getComponent());
        this.QtyBatchSizeLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"QtyBatchSize"));
        tmpRow.appendChild(this.QtyBatchSizeLabel.rightAlign());
        tmpRow.appendChild((Component)this.qtyBatchSizeField.getComponent());
        this.openQtyLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"QtyOpen"));
        tmpRow.appendChild(this.openQtyLabel.rightAlign());
        tmpRow.appendChild((Component)this.openQtyField.getComponent());
        tmpRow = tmpRows.newRow();
        tmpRow.appendChild(this.labelcombo.rightAlign());
        tmpRow.appendChild((Component)this.pickcombo);
        tmpRow.appendChild(this.backflushGroupLabel.rightAlign());
        tmpRow.appendChild((Component)this.backflushGroup);
        tmpRow.appendChild((Component)new Space());
        tmpRow.appendChild((Component)new Space());
        tmpRow = tmpRows.newRow();
        this.toDeliverQtyLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"QtyToDeliver"));
        tmpRow.appendChild(this.toDeliverQtyLabel.rightAlign());
        tmpRow.appendChild((Component)this.toDeliverQty.getComponent());
        this.scrapQtyLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"QtyScrap"));
        tmpRow.appendChild(this.scrapQtyLabel.rightAlign());
        tmpRow.appendChild((Component)this.scrapQtyField.getComponent());
        this.rejectQtyLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"QtyReject"));
        tmpRow.appendChild(this.rejectQtyLabel.rightAlign());
        tmpRow.appendChild((Component)this.rejectQty.getComponent());
        tmpRow = tmpRows.newRow();
        this.movementDateLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"MovementDate"));
        tmpRow.appendChild(this.movementDateLabel.rightAlign());
        tmpRow.appendChild((Component)this.movementDateField.getComponent());
        this.locatorLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"M_Locator_ID"));
        tmpRow.appendChild(this.locatorLabel.rightAlign());
        tmpRow.appendChild((Component)this.locatorField.getComponent());
        this.attributeLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"M_AttributeSetInstance_ID"));
        tmpRow.appendChild(this.attributeLabel.rightAlign());
        tmpRow.appendChild((Component)this.attribute.getComponent());
        this.ReceiptIssueOrder.appendChild((Component)center);
        center.appendChild((Component)this.issue);
        this.ReceiptIssueOrder.appendChild((Component)south);
        south.appendChild((Component)this.PanelBottom);
        tmpRow = tmpRows.newRow();
        this.DescriptionLabel.setText(Msg.translate((Properties)Env.getCtx(), (String)"Description"));
        tmpRow.appendChild(this.DescriptionLabel.rightAlign());
        tmpRow.appendChild((Component)this.description);
        this.Process.setLabel(Msg.translate((Properties)Env.getCtx(), (String)"OK"));
        this.PanelBottom.appendChild((Component)this.Process);
        this.PanelBottom.setWidth("100%");
        this.PanelBottom.setStyle("text-align:center");
        Tabs tabs = new Tabs();
        Tab tab1 = new Tab();
        Tab tab2 = new Tab();
        tab1.setLabel(Msg.translate((Properties)Env.getCtx(), (String)"IsShipConfirm"));
        tab2.setLabel(Msg.translate((Properties)Env.getCtx(), (String)"Generate"));
        tabs.appendChild((Component)tab1);
        tabs.appendChild((Component)tab2);
        this.TabsReceiptsIssue.appendChild((Component)tabs);
        Tabpanels tabps = new Tabpanels();
        Tabpanel tabp1 = new Tabpanel();
        Tabpanel tabp2 = new Tabpanel();
        this.TabsReceiptsIssue.appendChild((Component)tabps);
        this.TabsReceiptsIssue.setWidth("100%");
        this.TabsReceiptsIssue.setHeight("100%");
        tabps.appendChild((Component)tabp1);
        tabps.appendChild((Component)tabp2);
        tabp1.appendChild((Component)this.ReceiptIssueOrder);
        tabp1.setWidth("100%");
        tabp1.setHeight("100%");
        tabp2.appendChild((Component)this.Generate);
        tabp2.setWidth("100%");
        tabp2.setHeight("100%");
        this.Generate.appendChild((Component)this.info);
        this.Generate.setVisible(true);
        this.info.setVisible(true);
        this.TabsReceiptsIssue.addEventListener("onChange", (EventListener)this);
    }

    public void dynInit() {
        this.disableToDeliver();
        this.prepareTable((IMiniTable)this.issue);
        this.issue.autoSize();
        this.issue.getModel().addTableModelListener((WTableModelListener)this);
        this.issue.setRowCount(0);
    }

    public void prepareTable(IMiniTable miniTable) {
        this.configureMiniTable(miniTable);
    }

    public void onEvent(Event e) throws Exception {
        if (e.getName().equals("onCancel")) {
            this.dispose();
            return;
        }
        if (e.getTarget().equals((Object)this.Process)) {
            if (this.getMovementDate() == null) {
                Messagebox.show((String)Msg.getMsg((Properties)Env.getCtx(), (String)"NoDate"), (String)"Info", (int)1, (String)"z-messagebox-icon z-messagebox-information");
                return;
            }
            if ((this.isOnlyReceipt() || this.isBackflush()) && this.getM_Locator_ID() <= 0) {
                Messagebox.show((String)Msg.getMsg((Properties)Env.getCtx(), (String)"NoLocator"), (String)"Info", (int)1, (String)"z-messagebox-icon z-messagebox-information");
                return;
            }
            this.TabsReceiptsIssue.setSelectedIndex(1);
            this.generateSummaryTable();
            int result = -1;
            result = Messagebox.show((String)Msg.getMsg((Properties)Env.getCtx(), (String)"Update"), (String)"", (int)3, (String)"z-messagebox-icon z-messagebox-question");
            if (result == 1) {
                boolean isCloseDocument;
                boolean bl = isCloseDocument = Messagebox.show((String)Msg.parseTranslation((Properties)Env.getCtx(), (String)("@IsCloseDocument@ : &&&&" + this.getPP_Order().getDocumentNo())), (String)"", (int)3, (String)"z-messagebox-icon z-messagebox-question") == 1;
                if (this.cmd_process(isCloseDocument, (IMiniTable)this.issue)) {
                    this.dispose();
                    return;
                }
            }
            this.TabsReceiptsIssue.setSelectedIndex(0);
        }
        if (e.getTarget().equals((Object)this.pickcombo)) {
            if (this.isOnlyReceipt()) {
                this.enableToDeliver();
                this.locatorLabel.setVisible(true);
                this.locatorField.setVisible(true);
                this.attribute.setVisible(true);
                this.attributeLabel.setVisible(true);
                this.issue.setVisible(false);
            } else if (this.isOnlyIssue()) {
                this.disableToDeliver();
                this.locatorLabel.setVisible(false);
                this.locatorField.setVisible(false);
                this.attribute.setVisible(false);
                this.attributeLabel.setVisible(false);
                this.issue.setVisible(true);
                this.executeQuery();
            } else if (this.isBackflush()) {
                this.enableToDeliver();
                this.locatorLabel.setVisible(true);
                this.locatorField.setVisible(true);
                this.attribute.setVisible(true);
                this.attributeLabel.setVisible(true);
                this.issue.setVisible(true);
                this.executeQuery();
            }
            this.setToDeliverQty(this.getOpenQty());
        }
    }

    public void enableToDeliver() {
        this.setToDeliver(true);
    }

    public void disableToDeliver() {
        this.setToDeliver(false);
    }

    private void setToDeliver(Boolean state) {
        this.toDeliverQty.getComponent().setEnabled(state.booleanValue());
        this.scrapQtyLabel.setVisible(state.booleanValue());
        this.scrapQtyField.setVisible(state.booleanValue());
        this.rejectQtyLabel.setVisible(state.booleanValue());
        this.rejectQty.setVisible(state.booleanValue());
    }

    public void executeQuery() {
        this.m_sql = String.valueOf(this.m_sql) + " ORDER BY obl." + "Line";
        this.issue.clearTable();
        this.executeQuery((IMiniTable)this.issue);
        this.issue.repaint();
    }

    public void valueChange(ValueChangeEvent e) {
        String name = e.getPropertyName();
        Object value = e.getNewValue();
        if (value == null) {
            return;
        }
        if (name.equals("PP_Order_ID")) {
            this.orderField.setValue(value);
            MPPOrder pp_order = this.getPP_Order();
            if (pp_order != null) {
                this.setS_Resource_ID(pp_order.getS_Resource_ID());
                this.setM_Warehouse_ID(pp_order.getM_Warehouse_ID());
                this.setDeliveredQty(pp_order.getQtyDelivered());
                this.setOrderedQty(pp_order.getQtyOrdered());
                this.setQtyBatchs(pp_order.getQtyBatchs());
                this.setQtyBatchSize(pp_order.getQtyBatchSize());
                this.setOpenQty(pp_order.getQtyOrdered().subtract(pp_order.getQtyDelivered()));
                this.setToDeliverQty(this.getOpenQty());
                this.setM_Product_ID(pp_order.getM_Product_ID());
                MProduct m_product = MProduct.get((Properties)Env.getCtx(), (int)pp_order.getM_Product_ID());
                this.setC_UOM_ID(m_product.getC_UOM_ID());
                this.setOrder_UOM_ID(pp_order.getC_UOM_ID());
                this.setM_AttributeSetInstance_ID(pp_order.getM_Product().getM_AttributeSetInstance_ID());
                String docBaseType = MDocType.get((Properties)pp_order.getCtx(), (int)pp_order.getC_DocType_ID()).getDocBaseType();
                if (docBaseType.equals("MOF")) {
                    this.pickcombo.setSelectedIndex(1);
                } else {
                    this.pickcombo.setSelectedIndex(0);
                }
                Event ev = new Event("onChange", (Component)this.pickcombo);
                try {
                    this.onEvent(ev);
                }
                catch (Exception e1) {
                    throw new AdempiereException((Throwable)e1);
                }
            }
        }
        if ((name.equals(this.toDeliverQty.getColumnName()) || name.equals(this.scrapQtyField.getColumnName())) && this.getPP_Order_ID() > 0 && this.isBackflush()) {
            this.executeQuery();
        }
    }

    @Override
    public void showMessage(String message, boolean error) {
        try {
            if (!error) {
                Messagebox.show((String)message, (String)"Info", (int)1, (String)"z-messagebox-icon z-messagebox-information");
            } else {
                Messagebox.show((String)message, (String)"", (int)1, (String)"z-messagebox-icon z-messagebox-error");
            }
        }
        catch (Exception exception) {}
    }

    private void generateSummaryTable() {
        this.info.setContent(this.generateSummaryTable((IMiniTable)this.issue, this.productField.getDisplay(), this.uomField.getDisplay(), this.attribute.getDisplay(), this.toDeliverQty.getDisplay(), this.deliveredQtyField.getDisplay(), this.scrapQtyField.getDisplay(), this.isBackflush(), this.isOnlyIssue(), this.isOnlyReceipt()));
    }

    @Override
    protected boolean isOnlyReceipt() {
        super.setIsOnlyReceipt(this.pickcombo.getText().equals("OnlyReceipt"));
        return super.isOnlyReceipt();
    }

    @Override
    protected boolean isOnlyIssue() {
        super.setIsOnlyIssue(this.pickcombo.getText().equals("OnlyIssue"));
        return super.isOnlyIssue();
    }

    @Override
    protected boolean isBackflush() {
        super.setIsBackflush(this.pickcombo.getText().equals("IsBackflush"));
        return super.isBackflush();
    }

    @Override
    protected Timestamp getMovementDate() {
        return this.movementDateField.getValue();
    }

    @Override
    protected BigDecimal getOrderedQty() {
        BigDecimal bd = this.orderedQtyField.getValue();
        return bd != null ? bd : Env.ZERO;
    }

    @Override
    protected void setOrderedQty(BigDecimal qty) {
        this.orderedQtyField.setValue((Object)qty);
    }

    @Override
    protected BigDecimal getDeliveredQty() {
        BigDecimal bd = this.deliveredQtyField.getValue();
        return bd != null ? bd : Env.ZERO;
    }

    @Override
    protected void setDeliveredQty(BigDecimal qty) {
        this.deliveredQtyField.setValue((Object)qty);
    }

    @Override
    protected BigDecimal getToDeliverQty() {
        BigDecimal bd = this.toDeliverQty.getValue();
        return bd != null ? bd : Env.ZERO;
    }

    @Override
    protected void setToDeliverQty(BigDecimal qty) {
        this.toDeliverQty.setValue((Object)qty);
    }

    @Override
    protected BigDecimal getScrapQty() {
        BigDecimal bd = this.scrapQtyField.getValue();
        return bd != null ? bd : Env.ZERO;
    }

    @Override
    protected BigDecimal getRejectQty() {
        BigDecimal bd = this.rejectQty.getValue();
        return bd != null ? bd : Env.ZERO;
    }

    @Override
    protected BigDecimal getOpenQty() {
        BigDecimal bd = this.openQtyField.getValue();
        return bd != null ? bd : Env.ZERO;
    }

    @Override
    protected void setOpenQty(BigDecimal qty) {
        this.openQtyField.setValue((Object)qty);
    }

    @Override
    protected BigDecimal getQtyBatchs() {
        BigDecimal bd = this.qtyBatchsField.getValue();
        return bd != null ? bd : Env.ZERO;
    }

    @Override
    protected void setQtyBatchs(BigDecimal qty) {
        this.qtyBatchsField.setValue((Object)qty);
    }

    @Override
    protected BigDecimal getQtyBatchSize() {
        BigDecimal bd = this.qtyBatchSizeField.getValue();
        return bd != null ? bd : Env.ZERO;
    }

    @Override
    protected void setQtyBatchSize(BigDecimal qty) {
        this.qtyBatchSizeField.setValue((Object)qty);
    }

    @Override
    protected int getM_AttributeSetInstance_ID() {
        Integer ii = (Integer)this.attribute.getValue();
        return ii != null ? ii : 0;
    }

    @Override
    protected void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
        this.attribute.setValue((Object)M_AttributeSetInstance_ID);
    }

    @Override
    protected int getM_Locator_ID() {
        Integer ii = (Integer)this.locatorField.getValue();
        return ii != null ? ii : 0;
    }

    @Override
    protected void setM_Locator_ID(int M_Locator_ID) {
        this.locatorField.setValue((Object)M_Locator_ID);
    }

    @Override
    protected int getPP_Order_ID() {
        Integer ii = (Integer)this.orderField.getValue();
        return ii != null ? ii : 0;
    }

    @Override
    protected MPPOrder getPP_Order() {
        int id = this.getPP_Order_ID();
        if (id <= 0) {
            this.m_PP_order = null;
            return null;
        }
        if (this.m_PP_order == null || this.m_PP_order.get_ID() != id) {
            this.m_PP_order = new MPPOrder(Env.getCtx(), id, null);
        }
        return this.m_PP_order;
    }

    protected int getS_Resource_ID() {
        Integer ii = (Integer)this.resourceField.getValue();
        return ii != null ? ii : 0;
    }

    protected void setS_Resource_ID(int S_Resource_ID) {
        this.resourceField.setValue((Object)S_Resource_ID);
    }

    protected int getM_Warehouse_ID() {
        Integer ii = (Integer)this.warehouseField.getValue();
        return ii != null ? ii : 0;
    }

    protected void setM_Warehouse_ID(int M_Warehouse_ID) {
        this.warehouseField.setValue((Object)M_Warehouse_ID);
    }

    protected int getM_Product_ID() {
        Integer ii = (Integer)this.productField.getValue();
        return ii != null ? ii : 0;
    }

    protected void setM_Product_ID(int M_Product_ID) {
        this.productField.setValue((Object)M_Product_ID);
        Env.setContext((Properties)Env.getCtx(), (int)this.m_WindowNo, (String)"M_Product_ID", (int)M_Product_ID);
    }

    protected int getC_UOM_ID() {
        Integer ii = (Integer)this.uomField.getValue();
        return ii != null ? ii : 0;
    }

    protected void setC_UOM_ID(int C_UOM_ID) {
        this.uomField.setValue((Object)C_UOM_ID);
    }

    protected int getOrder_UOM_ID() {
        Integer ii = (Integer)this.uomorderField.getValue();
        return ii != null ? ii : 0;
    }

    protected void setOrder_UOM_ID(int C_UOM_ID) {
        this.uomorderField.setValue((Object)C_UOM_ID);
    }

    public void dispose() {
        SessionManager.getAppDesktop().closeActiveWindow();
    }

    public ADForm getForm() {
        return this.form;
    }

    public void tableChanged(WTableModelEvent event) {
    }

    private BigDecimal getValueBigDecimal(IMiniTable issue, int row, int col) {
        BigDecimal bd = (BigDecimal)issue.getValueAt(row, col);
        return bd == null ? Env.ZERO : bd;
    }

    public boolean cmd_process(final boolean isCloseDocument, final IMiniTable issue) {
        if ((this.isOnlyReceipt() || this.isBackflush()) && this.getM_Locator_ID() <= 0) {
            this.showMessage(Msg.getMsg((Properties)Env.getCtx(), (String)"NoLocator"), false);
        }
        if (this.getPP_Order() == null || this.getMovementDate() == null) {
            return false;
        }
        if (this.description.getValue().equals("")) {
            this.showMessage(Msg.getMsg((Properties)Env.getCtx(), (String)"NoDescription"), false);
            return false;
        }
        for (int i = 0; i < issue.getRowCount(); ++i) {
            IDColumn id = (IDColumn)issue.getValueAt(i, 0);
            KeyNamePair key = new KeyNamePair(id.getRecord_ID().intValue(), id.isSelected() ? "Y" : "N");
            boolean isSelected = key.getName().equals("Y");
            if (!isSelected || this.getValueBigDecimal(issue, i, 10).compareTo(this.getValueBigDecimal(issue, i, 8)) >= 0 || this.getValueBigDecimal(issue, i, 8).compareTo(BigDecimal.ZERO) <= 0) continue;
            this.showMessage(Msg.getMsg((Properties)Env.getCtx(), (String)(issue.getValueAt(i, 2) + " Qty Onhand " + this.getValueBigDecimal(issue, i, 10))), false);
            return false;
        }
        try {
            try {
                Trx.run((TrxRunnable)new TrxRunnable(){

                    public void run(String trxName) {
                        MPPOrder order = new MPPOrder(Env.getCtx(), WOrderReceiptIssue.this.getPP_Order_ID(), trxName);
                        if (WOrderReceiptIssue.this.isBackflush() || WOrderReceiptIssue.this.isOnlyIssue()) {
                            WOrderReceiptIssue.this.createIssue(order, issue);
                        }
                        if (WOrderReceiptIssue.this.isOnlyReceipt() || WOrderReceiptIssue.this.isBackflush()) {
                            MPPOrder.createReceipt(order, WOrderReceiptIssue.this.getMovementDate(), WOrderReceiptIssue.this.getDeliveredQty(), WOrderReceiptIssue.this.getToDeliverQty(), WOrderReceiptIssue.this.getScrapQty(), WOrderReceiptIssue.this.getRejectQty(), WOrderReceiptIssue.this.getM_Locator_ID(), WOrderReceiptIssue.this.getM_AttributeSetInstance_ID());
                            if (isCloseDocument && WOrderReceiptIssue.this.getToDeliverQty().compareTo(WOrderReceiptIssue.this.getOrderedQty().subtract(WOrderReceiptIssue.this.getDeliveredQty())) == 0) {
                                order.setDateFinish(WOrderReceiptIssue.this.getMovementDate());
                                order.closeIt();
                                order.saveEx();
                            } else {
                                MPPOrderWorkflow orderWorkflow = order.getMPPOrderWorkflow();
                                if (orderWorkflow != null) {
                                    for (MPPOrderNode node : orderWorkflow.getNodes(true)) {
                                        System.out.println("afagfghh" + node);
                                        WOrderReceiptIssue.this.autoReportActivities(node, order, WOrderReceiptIssue.this.getToDeliverQty());
                                        WOrderReceiptIssue.this.createUsageVarianceCost(node, order, WOrderReceiptIssue.this.getToDeliverQty());
                                    }
                                }
                            }
                        }
                    }
                });
            }
            catch (Exception e) {
                e.printStackTrace();
                this.showMessage(e.getLocalizedMessage(), true);
                this.m_PP_order = null;
                return false;
            }
        }
        finally {
            this.m_PP_order = null;
        }
        return true;
    }

    public void autoReportActivities(I_PP_Order_Node orderNode, MPPOrder order, BigDecimal Qty) {
        MPPOrderNode node = (MPPOrderNode)orderNode;
        BigDecimal setupTimeReal = BigDecimal.valueOf(node.getSetupTimeReal());
        BigDecimal.valueOf(node.getDurationReal());
        BigDecimal setupTimeVariancePrev = node.getSetupTimeUsageVariance();
        node.getDurationUsageVariance();
        BigDecimal setupTimeRequired = BigDecimal.valueOf(node.getSetupTimeRequired());
        BigDecimal.valueOf(node.getDurationRequired());
        BigDecimal setupTimeVariance = setupTimeRequired.subtract(setupTimeReal).subtract(setupTimeVariancePrev);
        BigDecimal durationVariance = new BigDecimal(node.getDuration()).multiply(Qty);
        MPPCostCollector.createCollector(order, this.getM_Product_ID(), this.getM_Locator_ID(), this.getM_AttributeSetInstance_ID(), node.getS_Resource_ID(), 0, node.getPP_Order_Node_ID(), MDocType.getDocType((String)"MCC"), "160", order.getUpdated(), Qty, Env.ZERO, Env.ZERO, setupTimeVariance.intValueExact(), durationVariance, Env.ZERO, node.getQtyRequired().subtract(Qty.add(node.getQtyDelivered())), Env.ZERO);
    }

    public void createUsageVarianceCost(I_PP_Order_Node orderNode, MPPOrder order, BigDecimal Qty) {
        MPPOrderNode node = (MPPOrderNode)orderNode;
        BigDecimal setupTimeReal = BigDecimal.valueOf(node.getSetupTimeReal());
        BigDecimal durationReal = BigDecimal.valueOf(node.getDurationReal());
        BigDecimal setupTimeVariancePrev = node.getSetupTimeUsageVariance();
        BigDecimal durationVariancePrev = node.getDurationUsageVariance();
        BigDecimal setupTimeRequired = BigDecimal.valueOf(node.getSetupTimeRequired());
        BigDecimal durationRequired = BigDecimal.valueOf(node.getDurationRequired());
        node.getQtyToDeliver().subtract(new BigDecimal(node.get_ValueAsString("qtyreserved")));
        BigDecimal setupTimeVariance = setupTimeRequired.subtract(setupTimeReal).subtract(setupTimeVariancePrev);
        BigDecimal durationVariance = durationRequired.subtract(durationReal).subtract(durationVariancePrev);
        BigDecimal costvarian = node.getPP_Order_Workflow().getCost().subtract(DB.getSQLValueBD(null, (String)("select sum(currentcostprice) from m_cost where M_CostElement_ID != 1000000 and m_product_id = " + node.getPP_Order_Workflow().getPP_Order().getM_Product_ID()), (Object[])new Object[0]));
        System.out.println("Tes" + costvarian);
        if (costvarian.compareTo(BigDecimal.ZERO) > 0) {
            System.out.println("asfagagalk;k;jl");
            MPPCostCollector.createCollector(order, this.getM_Product_ID(), this.getM_Locator_ID(), this.getM_AttributeSetInstance_ID(), node.getS_Resource_ID(), 0, node.getPP_Order_Node_ID(), MDocType.getDocType((String)"MCC"), "120", order.getUpdated(), Qty, Env.ZERO, Env.ZERO, setupTimeVariance.intValueExact(), durationVariance, costvarian, Env.ZERO, new BigDecimal(node.get_ValueAsInt("jmltenagakerja")));
        }
    }
}

