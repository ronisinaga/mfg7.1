/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.DBException
 *  org.adempiere.webui.apps.AEnv
 *  org.adempiere.webui.component.Borderlayout
 *  org.adempiere.webui.component.Button
 *  org.adempiere.webui.component.Checkbox
 *  org.adempiere.webui.component.ConfirmPanel
 *  org.adempiere.webui.component.Grid
 *  org.adempiere.webui.component.GridFactory
 *  org.adempiere.webui.component.Label
 *  org.adempiere.webui.component.Panel
 *  org.adempiere.webui.component.Row
 *  org.adempiere.webui.component.Rows
 *  org.adempiere.webui.component.Tab
 *  org.adempiere.webui.component.Tabbox
 *  org.adempiere.webui.component.Textbox
 *  org.adempiere.webui.component.WListbox
 *  org.adempiere.webui.editor.WDateEditor
 *  org.adempiere.webui.editor.WNumberEditor
 *  org.adempiere.webui.editor.WSearchEditor
 *  org.adempiere.webui.event.WTableModelEvent
 *  org.adempiere.webui.event.WTableModelListener
 *  org.adempiere.webui.panel.ADForm
 *  org.adempiere.webui.panel.CustomForm
 *  org.adempiere.webui.panel.IFormController
 *  org.adempiere.webui.panel.StatusBarPanel
 *  org.adempiere.webui.window.WPAttributeInstance
 *  org.compiere.minigrid.IDColumn
 *  org.compiere.model.Lookup
 *  org.compiere.model.MColumn
 *  org.compiere.model.MLookup
 *  org.compiere.model.MLookupFactory
 *  org.compiere.model.MProduct
 *  org.compiere.model.MQuery
 *  org.compiere.model.MRefList
 *  org.compiere.model.MRole
 *  org.compiere.model.MUOM
 *  org.compiere.model.MWarehouse
 *  org.compiere.util.CPreparedStatement
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.compiere.util.KeyNamePair
 *  org.compiere.util.Language
 *  org.compiere.util.Msg
 *  org.eevolution.model.MPPProductPlanning
 *  org.zkoss.zk.ui.Component
 *  org.zkoss.zk.ui.event.Event
 *  org.zkoss.zk.ui.event.EventListener
 *  org.zkoss.zul.Center
 *  org.zkoss.zul.Div
 *  org.zkoss.zul.Menuitem
 *  org.zkoss.zul.Menupopup
 *  org.zkoss.zul.North
 *  org.zkoss.zul.South
 *  org.zkoss.zul.Space
 *  org.zkoss.zul.event.ListDataEvent
 *  org.zkoss.zul.event.ListDataListener
 */
package org.libero.form;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.exceptions.DBException;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Borderlayout;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Tab;
import org.adempiere.webui.component.Tabbox;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.component.WListbox;
import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.editor.WNumberEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.event.WTableModelEvent;
import org.adempiere.webui.event.WTableModelListener;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.panel.StatusBarPanel;
import org.adempiere.webui.window.WPAttributeInstance;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.Lookup;
import org.compiere.model.MColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MProduct;
import org.compiere.model.MQuery;
import org.compiere.model.MRefList;
import org.compiere.model.MRole;
import org.compiere.model.MUOM;
import org.compiere.model.MWarehouse;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;
import org.compiere.util.Msg;
import org.eevolution.model.MPPProductPlanning;
import org.libero.form.MRPDetailed;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Space;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.event.ListDataListener;

public class WMRPDetailed
extends MRPDetailed
implements IFormController,
EventListener,
ListDataListener,
WTableModelListener {
    private CustomForm m_frame = new CustomForm();
    private StatusBarPanel statusBar = new StatusBarPanel();
    protected WListbox p_table = new WListbox();
    private Panel panel = new Panel();
    private Panel southPanel = new Panel();
    private Borderlayout southLayout = new Borderlayout();
    ConfirmPanel confirmPanel = new ConfirmPanel(true, true, true, true, true, true, true);
    private Grid parameterPanel = GridFactory.newGridLayout();
    private Menupopup popup = new Menupopup();
    private Menuitem calcMenu = new Menuitem(Msg.getMsg((Properties)Env.getCtx(), (String)"Calculator"), "/images/Calculator16.png");
    private Label lProduct_ID = new Label(Msg.translate((Properties)this.getCtx(), (String)"M_Product_ID"));
    private WSearchEditor fProduct_ID;
    private Label lAttrSetInstance_ID = new Label(Msg.translate((Properties)this.getCtx(), (String)"M_AttributeSetInstance_ID"));
    private Button fAttrSetInstance_ID = this.fAttrSetInstance_ID = new Button();
    private Label lResource_ID = new Label(Msg.translate((Properties)this.getCtx(), (String)"S_Resource_ID"));
    private WSearchEditor fResource_ID;
    private Label lWarehouse_ID = new Label(Msg.translate((Properties)this.getCtx(), (String)"M_Warehouse_ID"));
    private WSearchEditor fWarehouse_ID;
    private Label lPlanner_ID = new Label(Msg.translate((Properties)this.getCtx(), (String)"Planner_ID"));
    private WSearchEditor fPlanner_ID;
    private Label lorder = new Label(Msg.translate((Properties)this.getCtx(), (String)"C_Order_ID"));
    private WSearchEditor forder_id;
    private Label lMO_ID = new Label(Msg.translate((Properties)this.getCtx(), (String)"PP_Order_ID"));
    private WSearchEditor fMO_ID;
    private Tabbox OrderPlanning;
    private Panel PanelBottom;
    private Panel PanelCenter;
    private Panel PanelFind;
    private Tab PanelOrder;
    private Tab Results;
    private Borderlayout mainLayout = new Borderlayout();
    private Label lDateFrom = new Label(Msg.translate((Properties)this.getCtx(), (String)"DateFrom"));
    private WDateEditor fDateFrom = new WDateEditor();
    private Label lDateTo = new Label(Msg.translate((Properties)this.getCtx(), (String)"DateTo"));
    private WDateEditor fDateTo = new WDateEditor();
    private Label lType = new Label();
    private Textbox fType = new Textbox();
    private Label lUOM = new Label();
    private Textbox fUOM = new Textbox();
    private Label lOrderPeriod = new Label();
    private WNumberEditor fOrderPeriod = new WNumberEditor();
    private Label lTimefence = new Label();
    private WNumberEditor fTimefence = new WNumberEditor();
    private Label lLeadtime = new Label();
    private WNumberEditor fLeadtime = new WNumberEditor();
    private Label lReplenishMin = new Label();
    private WNumberEditor fReplenishMin = new WNumberEditor();
    private Label lMinOrd = new Label();
    private WNumberEditor fMinOrd = new WNumberEditor();
    private Label lMaxOrd = new Label();
    private WNumberEditor fMaxOrd = new WNumberEditor();
    private Label lOrdMult = new Label();
    private WNumberEditor fOrdMult = new WNumberEditor();
    private Label lOrderQty = new Label();
    private WNumberEditor fOrderQty = new WNumberEditor();
    private Label lYield = new Label();
    private WNumberEditor fYield = new WNumberEditor();
    private Label lOnhand = new Label();
    private WNumberEditor fOnhand = new WNumberEditor();
    private Label lSafetyStock = new Label();
    private WNumberEditor fSafetyStock = new WNumberEditor();
    private Label lOrdered = new Label();
    private WNumberEditor fOrdered = new WNumberEditor();
    private Label lReserved = new Label();
    private WNumberEditor fReserved = new WNumberEditor();
    private Label lAvailable = new Label();
    private WNumberEditor fAvailable = new WNumberEditor();
    private Label lSupplyType = new Label(Msg.translate((Properties)this.getCtx(), (String)"TypeMRP"));
    private WSearchEditor fSupplyType = null;
    private Checkbox fMaster = new Checkbox();
    private Checkbox fMRPReq = new Checkbox();
    private Checkbox fCreatePlan = new Checkbox();
    private int ASI_ID = 0;
    private boolean isBaseLanguage;

    public WMRPDetailed() {
        Env.getLanguage((Properties)Env.getCtx());
        this.isBaseLanguage = Language.getBaseAD_Language().compareTo(Env.getLoginLanguage((Properties)Env.getCtx()).getAD_Language()) == 0;
        this.init();
    }

    private void init() {
        try {
            this.statInit();
            this.fillPicks();
            this.jbInit();
        }
        catch (Exception e) {
            log.log(Level.SEVERE, "VMRPDetailed.init", (Throwable)e);
        }
    }

    private void statInit() throws Exception {
        Language language = Language.getLoginLanguage();
        MLookup resourceL = MLookupFactory.get((Properties)this.getCtx(), (int)this.p_WindowNo, (int)MColumn.getColumn_ID((String)"S_Resource", (String)"S_Resource_ID"), (int)19, (Language)language, (String)"S_Resource_ID", (int)0, (boolean)false, (String)"S_Resource.ManufacturingResourceType= 'PT'");
        this.fResource_ID = new WSearchEditor("S_Resource_ID", false, false, true, (Lookup)resourceL){
            private final long serialVersionUID = 1L;
            {
                this.serialVersionUID = 1L;
            }

            public void setValue(Object arg0) {
                super.setValue(arg0);
            }
        };
        this.fPlanner_ID = new WSearchEditor("Planner_ID", false, false, true, (Lookup)MLookupFactory.get((Properties)this.getCtx(), (int)this.p_WindowNo, (int)0, (int)MColumn.getColumn_ID((String)"PP_Product_Planning", (String)"Planner_ID"), (int)18)){
            private final long serialVersionUID = 1L;
            {
                this.serialVersionUID = 1L;
            }

            public void setValue(Object arg0) {
                super.setValue(arg0);
            }
        };
        this.fWarehouse_ID = new WSearchEditor("M_Warehouse_ID", false, false, true, (Lookup)MLookupFactory.get((Properties)this.getCtx(), (int)this.p_WindowNo, (int)0, (int)MColumn.getColumn_ID((String)"M_Warehouse", (String)"M_Warehouse_ID"), (int)19)){
            private final long serialVersionUID = 1L;
            {
                this.serialVersionUID = 1L;
            }

            public void setValue(Object arg0) {
                super.setValue(arg0);
            }
        };
        this.fMaster.setSelected(false);
        this.fMaster.setEnabled(false);
        this.fMRPReq.setSelected(false);
        this.fMRPReq.setEnabled(false);
        this.fCreatePlan.setSelected(false);
        this.fCreatePlan.setEnabled(false);
        this.lUOM.setText(Msg.translate((Properties)this.getCtx(), (String)"C_UOM_ID"));
        this.fUOM.setReadonly(true);
        this.lType.setText(Msg.translate((Properties)this.getCtx(), (String)"Order_Policy"));
        this.fType.setReadonly(true);
        this.lOrderPeriod.setText(Msg.translate((Properties)this.getCtx(), (String)"Order_Period"));
        this.fOrderPeriod.setReadWrite(false);
        this.lTimefence.setText(Msg.translate((Properties)this.getCtx(), (String)"TimeFence"));
        this.fTimefence.setReadWrite(false);
        this.lLeadtime.setText(Msg.translate((Properties)this.getCtx(), (String)"DeliveryTime_Promised"));
        this.fLeadtime.setReadWrite(false);
        this.lMinOrd.setText(Msg.translate((Properties)this.getCtx(), (String)"Order_Min"));
        this.fMinOrd.setReadWrite(false);
        this.lMaxOrd.setText(Msg.translate((Properties)this.getCtx(), (String)"Order_Max"));
        this.fMaxOrd.setReadWrite(false);
        this.lOrdMult.setText(Msg.translate((Properties)this.getCtx(), (String)"Order_Pack"));
        this.fOrdMult.setReadWrite(false);
        this.lOrderQty.setText(Msg.translate((Properties)this.getCtx(), (String)"Order_Qty"));
        this.fOrderQty.setReadWrite(false);
        this.lYield.setText(Msg.translate((Properties)this.getCtx(), (String)"Yield"));
        this.fYield.setReadWrite(false);
        this.lOnhand.setText(Msg.translate((Properties)this.getCtx(), (String)"QtyOnHand"));
        this.fOnhand.setReadWrite(false);
        this.lSafetyStock.setText(Msg.translate((Properties)this.getCtx(), (String)"SafetyStock"));
        this.fSafetyStock.setReadWrite(false);
        this.lReserved.setText(Msg.translate((Properties)this.getCtx(), (String)"Qty"));
        this.fReserved.setReadWrite(false);
        this.lAvailable.setText(Msg.translate((Properties)this.getCtx(), (String)"QtyAvailable"));
        this.fAvailable.setReadWrite(false);
        this.lOrdered.setText(Msg.translate((Properties)this.getCtx(), (String)"QtyOrdered"));
        this.fOrdered.setReadWrite(false);
        this.fProduct_ID = new WSearchEditor("M_Product_ID", true, false, true, (Lookup)MLookupFactory.get((Properties)this.getCtx(), (int)this.p_WindowNo, (int)0, (int)MColumn.getColumn_ID((String)"M_Product", (String)"M_Product_ID"), (int)30)){
            private final long serialVersionUID = 1L;
            {
                this.serialVersionUID = 1L;
            }

            public void setValue(Object arg0) {
                super.setValue(arg0);
            }
        };
        this.forder_id = new WSearchEditor("C_Order_ID", true, false, true, (Lookup)MLookupFactory.get((Properties)this.getCtx(), (int)this.p_WindowNo, (int)0, (int)MColumn.getColumn_ID((String)"C_Order", (String)"C_Order_ID"), (int)30)){
            private final long serialVersionUID = 1L;
            {
                this.serialVersionUID = 1L;
            }

            public void setValue(Object arg0) {
                super.setValue(arg0);
            }
        };
        this.fMO_ID = new WSearchEditor("PP_Order_ID", true, false, true, (Lookup)MLookupFactory.get((Properties)this.getCtx(), (int)this.p_WindowNo, (int)0, (int)MColumn.getColumn_ID((String)"PP_Order", (String)"PP_Order_ID"), (int)30)){
            private final long serialVersionUID = 1L;
            {
                this.serialVersionUID = 1L;
            }

            public void setValue(Object arg0) {
                super.setValue(arg0);
            }
        };
        this.fMaster.setText(Msg.translate((Properties)this.getCtx(), (String)"IsMPS"));
        this.fMRPReq.setText(Msg.translate((Properties)this.getCtx(), (String)"IsRequiredMRP"));
        this.fCreatePlan.setText(Msg.translate((Properties)this.getCtx(), (String)"IsCreatePlan"));
        this.fAttrSetInstance_ID = new Button(){
            private final long serialVersionUID = 1L;
            private Object m_value;

            public void setLabel(String text) {
                if (text == null) {
                    text = "---";
                }
                if (text.length() > 23) {
                    text = String.valueOf(text.substring(0, 20)) + "...";
                }
                super.setLabel(text);
            }

            public void setValue(Object arg0) {
                int i;
                this.m_value = arg0;
                int n = i = arg0 instanceof Integer ? (Integer)arg0 : 0;
                if (i == 0) {
                    this.setLabel(null);
                }
            }

            public Object getValue() {
                return this.m_value;
            }
        };
        this.fAttrSetInstance_ID.addActionListener(new EventListener(){

            public void onEvent(Event event) throws Exception {
                WMRPDetailed.this.selectAttributeSetInstance();
            }
        });
        this.fDateFrom.getComponent().setTooltiptext(Msg.translate((Properties)this.getCtx(), (String)"DateFrom"));
        this.fDateTo.getComponent().setTooltiptext(Msg.translate((Properties)this.getCtx(), (String)"DateTo"));
        this.fSupplyType = new WSearchEditor("TypeMRP", false, false, true, (Lookup)MLookupFactory.get((Properties)this.getCtx(), (int)this.p_WindowNo, (int)0, (int)MColumn.getColumn_ID((String)"PP_MRP", (String)"TypeMRP"), (int)17));
        Rows rows = null;
        Row row = null;
        rows = new Rows();
        rows.setParent((Component)this.parameterPanel);
        row = rows.newRow();
        row.appendChild(this.lProduct_ID.rightAlign());
        row.appendChild((Component)this.fProduct_ID.getComponent());
        row.appendChild(this.lUOM.rightAlign());
        row.appendChild((Component)this.fUOM);
        row.appendChild(this.lType.rightAlign());
        row.appendChild((Component)this.fType);
        row = rows.newRow();
        row.appendChild(this.lAttrSetInstance_ID.rightAlign());
        row.appendChild((Component)this.fAttrSetInstance_ID);
        row.appendChild(this.lOnhand.rightAlign());
        row.appendChild((Component)this.fOnhand.getComponent());
        row.appendChild(this.lOrderPeriod.rightAlign());
        row.appendChild((Component)this.fOrderPeriod.getComponent());
        row = rows.newRow();
        row.appendChild(this.lPlanner_ID.rightAlign());
        row.appendChild((Component)this.fPlanner_ID.getComponent());
        row.appendChild(this.lSafetyStock.rightAlign());
        row.appendChild((Component)this.fSafetyStock.getComponent());
        row.appendChild(this.lMinOrd.rightAlign());
        row.appendChild((Component)this.fMinOrd.getComponent());
        row = rows.newRow();
        row.appendChild(this.lWarehouse_ID.rightAlign());
        row.appendChild((Component)this.fWarehouse_ID.getComponent());
        row.appendChild(this.lReserved.rightAlign());
        row.appendChild((Component)this.fReserved.getComponent());
        row.appendChild(this.lMaxOrd.rightAlign());
        row.appendChild((Component)this.fMaxOrd.getComponent());
        row = rows.newRow();
        row.appendChild(this.lResource_ID.rightAlign());
        row.appendChild((Component)this.fResource_ID.getComponent());
        row.appendChild(this.lAvailable.rightAlign());
        row.appendChild((Component)this.fAvailable.getComponent());
        row.appendChild(this.lOrdMult.rightAlign());
        row.appendChild((Component)this.fOrdMult.getComponent());
        row = rows.newRow();
        row.appendChild(this.lDateFrom.rightAlign());
        row.appendChild((Component)this.fDateFrom.getComponent());
        row.appendChild(this.lOrdered.rightAlign());
        row.appendChild((Component)this.fOrdered.getComponent());
        row.appendChild(this.lOrderQty.rightAlign());
        row.appendChild((Component)this.fOrderQty.getComponent());
        row = rows.newRow();
        row.appendChild(this.lDateTo.rightAlign());
        row.appendChild((Component)this.fDateTo.getComponent());
        row.appendChild(this.lorder.rightAlign());
        row.appendChild((Component)this.forder_id.getComponent());
        row.appendChild(this.lTimefence.rightAlign());
        row.appendChild((Component)this.fTimefence.getComponent());
        row = rows.newRow();
        row.appendChild((Component)new Space());
        row.appendChild((Component)this.fMaster);
        row.appendChild((Component)new Space());
        row.appendChild((Component)this.fCreatePlan);
        row.appendChild(this.lLeadtime.rightAlign());
        row.appendChild((Component)this.fLeadtime.getComponent());
        row = rows.newRow();
        row.appendChild(this.lMO_ID.rightAlign());
        row.appendChild((Component)this.fMO_ID.getComponent());
        row.appendChild((Component)new Space());
        row.appendChild((Component)this.fMRPReq);
        row.appendChild(this.lYield.rightAlign());
        row.appendChild((Component)this.fYield.getComponent());
    }

    private void selectAttributeSetInstance() {
        int m_warehouse_id = 0;
        int m_product_id = 0;
        if (m_product_id <= 0) {
            return;
        }
        MProduct product = MProduct.get((Properties)this.getCtx(), (int)m_product_id);
        MWarehouse wh = MWarehouse.get((Properties)this.getCtx(), (int)m_warehouse_id);
        String title = String.valueOf(product.get_Translation("Name")) + " - " + wh.get_Translation("Name");
        WPAttributeInstance pai = new WPAttributeInstance(title, m_warehouse_id, 0, m_product_id, 0);
        if (pai.getM_AttributeSetInstance_ID() != -1) {
            this.fAttrSetInstance_ID.setLabel(pai.getM_AttributeSetInstanceName());
            this.ASI_ID = new Integer(pai.getM_AttributeSetInstance_ID());
        } else {
            this.ASI_ID = 0;
        }
    }

    private boolean isAttributeSetInstance() {
        return this.getM_AttributeSetInstance_ID() > 0;
    }

    private void initComponents() {
        this.OrderPlanning = new Tabbox();
        this.PanelOrder = new Tab();
        this.PanelFind = new Panel();
        this.PanelCenter = new Panel();
        this.PanelBottom = new Panel();
        this.Results = new Tab();
        Borderlayout PanelOrderLayout = new Borderlayout();
        this.PanelOrder.appendChild((Component)PanelOrderLayout);
        North north = new North();
        PanelOrderLayout.appendChild((Component)north);
        north.appendChild((Component)this.PanelFind);
        Center center = new Center();
        PanelOrderLayout.appendChild((Component)center);
        center.appendChild((Component)this.PanelCenter);
        South south = new South();
        PanelOrderLayout.appendChild((Component)south);
        south.appendChild((Component)this.PanelBottom);
        this.OrderPlanning.appendChild((Component)this.PanelOrder);
        this.OrderPlanning.appendChild((Component)this.Results);
        this.PanelOrder.setLabel("Order");
        this.Results.setLabel("Result");
        Center center2 = new Center();
        this.mainLayout.appendChild((Component)center2);
        center2.appendChild((Component)this.OrderPlanning);
        this.m_frame.setWidth("99%");
        this.m_frame.setHeight("100%");
        this.m_frame.setStyle("position: absolute; padding: 0; margin: 0");
        this.m_frame.appendChild((Component)this.mainLayout);
        this.mainLayout.setWidth("100%");
        this.mainLayout.setHeight("100%");
        this.mainLayout.setStyle("position: absolute");
    }

    protected void jbInit() throws Exception {
        this.m_frame.setWidth("99%");
        this.m_frame.setHeight("100%");
        this.m_frame.setStyle("position: absolute; padding: 0; margin: 0");
        this.m_frame.appendChild((Component)this.mainLayout);
        this.mainLayout.setWidth("100%");
        this.mainLayout.setHeight("100%");
        this.mainLayout.setStyle("position: absolute");
        North north = new North();
        north.appendChild((Component)this.parameterPanel);
        this.mainLayout.appendChild((Component)north);
        Center center = new Center();
        center.appendChild((Component)this.p_table);
        this.mainLayout.appendChild((Component)center);
        this.p_table.setVflex(true);
        this.p_table.setFixedLayout(true);
        center.setFlex(true);
        Div div = new Div();
        div.appendChild((Component)this.confirmPanel);
        div.appendChild((Component)this.statusBar);
        South south = new South();
        south.appendChild((Component)div);
        this.mainLayout.appendChild((Component)south);
        this.confirmPanel.addActionListener((EventListener)this);
        this.confirmPanel.setVisible("Reset", this.hasReset());
        this.confirmPanel.setVisible("Customize", this.hasCustomize());
        this.confirmPanel.setVisible("History", this.hasHistory());
        this.confirmPanel.setVisible("Zoom", this.hasZoom());
        South southPanel = new South();
        southPanel.appendChild((Component)this.southLayout);
        Button print = this.confirmPanel.createButton("Print");
        print.addActionListener((EventListener)this);
        this.confirmPanel.addButton(print);
        this.popup.appendChild((Component)this.calcMenu);
        this.calcMenu.addEventListener("onClick", (EventListener)this);
        this.p_table.getModel().addListDataListener((ListDataListener)this);
        this.enableButtons();
    }

    private void fillPicks() throws Exception {
        this.m_keyColumnIndex = 0;
        this.m_sqlMain = this.p_table.prepareTable(this.m_layout, this.getTableName(), this.getWhereClause(this.getSQLWhere()), false, "RV_PP_MRP", false);
    }

    public ADForm getForm() {
        return this.m_frame;
    }

    public void onEvent(Event event) throws Exception {
        String cmd = event.getTarget().getId();
        if (cmd.equals("Ok")) {
            this.m_frame.dispose();
        } else if (cmd.equals("Cancel")) {
            this.m_cancel = true;
            this.m_frame.dispose();
        } else if (cmd.equals("Zoom")) {
            this.zoom();
        } else if (cmd.equals("Refresh")) {
            this.executeQuery();
        }
    }

    public void dispose() {
        if (this.m_frame != null) {
            this.m_frame.dispose();
        }
        this.m_frame = null;
    }

    private String getSQLWhere() {
        StringBuffer sql = new StringBuffer();
        if (this.fProduct_ID.getValue() != null) {
            sql.append(" AND " + this.getTableName() + ".M_Product_ID=?");
            sql.append(" AND ((" + this.getTableName() + ".OrderType IN ('SOO','MOP','POO','POR','STK','DOO')) OR (" + this.getTableName() + ".OrderType='FCT' AND " + this.getTableName() + ".DatePromised >= SYSDATE))");
            this.fillHead();
            this.setMRP();
        }
        if (this.isAttributeSetInstance()) {
            sql.append(" AND " + this.getTableName() + ".M_AttributeSetInstance_ID=?");
            this.fillHead();
            this.setMRP();
        }
        if (this.fResource_ID.getValue() != null) {
            sql.append(" AND " + this.getTableName() + ".S_Resource_ID=?");
        }
        if (this.fPlanner_ID.getValue() != null) {
            sql.append(" AND " + this.getTableName() + ".Planner_ID=?");
        }
        if (this.fWarehouse_ID.getValue() != null) {
            sql.append(" AND " + this.getTableName() + ".M_Warehouse_ID=?");
        }
        if (this.fDateFrom.getValue() != null || this.fDateFrom.getValue() != null) {
            Timestamp from = this.fDateFrom.getValue();
            Timestamp to = this.fDateTo.getValue();
            if (from == null && to != null) {
                sql.append(" AND TRUNC(" + this.getTableName() + ".DatePromised) <= ?");
            } else if (from != null && to == null) {
                sql.append(" AND TRUNC(" + this.getTableName() + ".DatePromised) >= ?");
            } else if (from != null && to != null) {
                sql.append(" AND TRUNC(" + this.getTableName() + ".DatePromised) BETWEEN ? AND ?");
            }
        }
        if (this.forder_id.getValue() != null) {
            sql.append(" AND " + this.getTableName() + ".C_Order_ID=?");
        }
        if (this.fMO_ID.getValue() != null) {
            sql.append(" AND " + this.getTableName() + ".idmoref=?");
        }
        log.fine("MRP Info.setWhereClause=" + sql.toString());
        return sql.toString();
    }

    private void fillHead() {
        MPPProductPlanning pp = MPPProductPlanning.find((Properties)this.getCtx(), (int)this.getAD_Org_ID(), (int)this.getM_Warehouse_ID(), (int)this.getS_Resource_ID(), (int)this.getM_Product_ID(), null);
        if (pp == null) {
            pp = new MPPProductPlanning(this.getCtx(), 0, null);
        }
        this.fMaster.setSelected(pp.isMPS());
        this.fMRPReq.setSelected(pp.isRequiredMRP());
        this.fCreatePlan.setSelected(pp.isCreatePlan());
        this.fOrderPeriod.setValue((Object)pp.getOrder_Period());
        this.fLeadtime.setValue((Object)pp.getDeliveryTime_Promised());
        this.fTimefence.setValue((Object)pp.getTimeFence());
        this.fMinOrd.setValue((Object)pp.getOrder_Min());
        this.fMaxOrd.setValue((Object)pp.getOrder_Max());
        this.fOrdMult.setValue((Object)pp.getOrder_Pack());
        this.fOrderQty.setValue((Object)pp.getOrder_Qty());
        this.fYield.setValue((Object)pp.getYield());
        this.fType.setText(MRefList.getListName((Properties)this.getCtx(), (int)53228, (String)pp.getOrder_Policy()));
        this.fSafetyStock.setValue((Object)pp.getSafetyStock());
    }

    private void setMRP() {
        int M_Product_ID = this.getM_Product_ID();
        this.getM_AttributeSetInstance_ID();
        int M_Warehouse_ID = this.getM_Warehouse_ID();
        if (M_Product_ID <= 0) {
            return;
        }
        CPreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            try {
                StringBuffer sql = new StringBuffer("SELECT ").append("BOMQtyOnHandASI(M_Product_ID,?,?,?) as qtyonhand, ").append("BOMQtyReservedASI(M_Product_ID,?,?,?) as qtyreserved, ").append("BOMQtyAvailableASI(M_Product_ID,?,?,?) as qtyavailable, ").append("BOMQtyOrderedASI(M_Product_ID,?,?,?) as qtyordered").append(" FROM M_Product WHERE M_Product_ID=?");
                pstmt = DB.prepareStatement((String)sql.toString(), null);
                DB.setParameters((PreparedStatement)pstmt, (Object[])new Object[]{this.getM_AttributeSetInstance_ID(), this.getM_Warehouse_ID(), 0, this.getM_AttributeSetInstance_ID(), this.getM_Warehouse_ID(), 0, this.getM_AttributeSetInstance_ID(), this.getM_Warehouse_ID(), 0, this.getM_AttributeSetInstance_ID(), this.getM_Warehouse_ID(), 0, this.getM_Product_ID()});
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    this.fOnhand.setValue((Object)rs.getBigDecimal(1));
                    this.fReserved.setValue((Object)rs.getBigDecimal(2));
                    this.fAvailable.setValue((Object)rs.getBigDecimal(3));
                    this.fOrdered.setValue((Object)rs.getBigDecimal(4));
                }
            }
            catch (SQLException ex) {
                throw new DBException((Exception)ex);
            }
        }
        catch (Throwable throwable) {
            DB.close(rs, pstmt);
            rs = null;
            pstmt = null;
            throw throwable;
        }
        DB.close((ResultSet)rs, (Statement)pstmt);
        rs = null;
        pstmt = null;
        int uom_id = MProduct.get((Properties)this.getCtx(), (int)M_Product_ID).getC_UOM_ID();
        MUOM um = MUOM.get((Properties)this.getCtx(), (int)uom_id);
        KeyNamePair kum = new KeyNamePair(um.getC_UOM_ID(), um.get_Translation("Name"));
        this.fUOM.setText(kum.toString());
        BigDecimal replenishLevelMin = Env.ZERO;
        if (this.getM_Warehouse_ID() > 0) {
            String sql = "SELECT Level_Min FROM M_Replenish WHERE AD_Client_ID=? AND M_Product_ID=? AND M_Warehouse_ID=?";
            replenishLevelMin = DB.getSQLValueBD(null, (String)sql, (Object[])new Object[]{this.AD_Client_ID, M_Product_ID, M_Warehouse_ID});
        }
        this.fReplenishMin.setValue((Object)replenishLevelMin);
    }

    @Override
    public void zoom() {
        super.zoom();
        AEnv.zoom((int)this.AD_Window_ID, (MQuery)this.query);
    }

    void enableButtons() {
        boolean enable = true;
        this.confirmPanel.getOKButton().setEnabled(true);
        if (this.hasHistory()) {
            this.confirmPanel.getButton("History").setEnabled(enable);
        }
        if (this.hasZoom()) {
            this.confirmPanel.getButton("Zoom").setEnabled(enable);
        }
    }

    void executeQuery() {
        this.work();
    }

    protected void setParameters(PreparedStatement pstmt, boolean forCount) throws SQLException {
        int order_id;
        int index = 1;
        if (this.getM_Product_ID() > 0) {
            int product_id = this.getM_Product_ID();
            pstmt.setInt(index++, product_id);
            log.fine("Product=" + product_id);
        }
        if (this.isAttributeSetInstance()) {
            int asi = this.getM_AttributeSetInstance_ID();
            pstmt.setInt(index++, asi);
            log.fine("AttributeSetInstance=" + asi);
        }
        if (this.getS_Resource_ID() > 0) {
            int resource_id = this.getS_Resource_ID();
            pstmt.setInt(index++, resource_id);
            log.fine("Resource=" + resource_id);
        }
        if (this.getOrderID() > 0) {
            order_id = this.getOrderID();
            pstmt.setInt(index++, order_id);
            log.fine("Resource=" + order_id);
        }
        if (this.getMoID() > 0) {
            order_id = this.getMoID();
            pstmt.setInt(index++, order_id);
            log.fine("Resource=" + order_id);
        }
        if (this.getM_Warehouse_ID() > 0) {
            int warehouse_id = this.getM_Warehouse_ID();
            pstmt.setInt(index++, this.getM_Warehouse_ID());
            log.fine("Warehouse=" + warehouse_id);
        }
        if (this.getPlanner_ID() > 0) {
            int planner_id = this.getPlanner_ID();
            pstmt.setInt(index++, planner_id);
            log.fine("Planner=" + planner_id);
        }
        if (this.getDueStart() != null || this.getDueEnd() != null) {
            Timestamp from = this.getDueStart();
            Timestamp to = this.getDueEnd();
            log.fine("Date From=" + from + ", Date To=" + to);
            if (from == null && to != null) {
                pstmt.setTimestamp(index++, to);
            } else if (from != null && to == null) {
                pstmt.setTimestamp(index++, from);
            } else if (from != null && to != null) {
                pstmt.setTimestamp(index++, from);
                pstmt.setTimestamp(index++, to);
            }
        }
    }

    protected int getM_Product_ID() {
        Object o = this.fProduct_ID.getValue();
        return o != null && o instanceof Integer ? (Integer)o : Integer.valueOf(0);
    }

    protected int getM_AttributeSetInstance_ID() {
        return this.ASI_ID;
    }

    protected int getAD_Org_ID() {
        int warehouse_id = this.getM_Warehouse_ID();
        if (warehouse_id <= 0) {
            return 0;
        }
        return MWarehouse.get((Properties)this.getCtx(), (int)warehouse_id).getAD_Org_ID();
    }

    protected int getM_Warehouse_ID() {
        Object o = this.fWarehouse_ID.getValue();
        return o != null && o instanceof Integer ? (Integer)o : Integer.valueOf(0);
    }

    protected int getS_Resource_ID() {
        Object o = this.fResource_ID.getValue();
        return o != null && o instanceof Integer ? (Integer)o : Integer.valueOf(0);
    }

    protected int getPlanner_ID() {
        Object o = this.fPlanner_ID.getValue();
        return o != null && o instanceof Integer ? (Integer)o : Integer.valueOf(0);
    }

    protected Timestamp getDueStart() {
        return this.fDateFrom.getValue();
    }

    protected Timestamp getDueEnd() {
        return this.fDateTo.getValue();
    }

    protected int getOrderID() {
        Object o = this.forder_id.getValue();
        return o != null && o instanceof Integer ? (Integer)o : Integer.valueOf(0);
    }

    protected BigDecimal getQtyOnHand() {
        BigDecimal bd = this.fOnhand.getValue();
        return bd != null ? bd : Env.ZERO;
    }

    protected int getMoID() {
        Object o = this.fMO_ID.getValue();
        return o != null && o instanceof Integer ? (Integer)o : Integer.valueOf(0);
    }

    public void onChange(ListDataEvent event) {
    }

    public void tableChanged(WTableModelEvent event) {
    }

    @Override
    protected Integer getSelectedRowKey() {
        int row = this.p_table.getSelectedRow();
        if (row != -1 && this.m_keyColumnIndex != -1) {
            Object data = this.p_table.getModel().getValueAt(row, this.m_keyColumnIndex);
            if (data instanceof IDColumn) {
                data = ((IDColumn)data).getRecord_ID();
            }
            if (data instanceof Integer) {
                return (Integer)data;
            }
        }
        return null;
    }

    @Override
    void zoom(int AD_Window_ID, MQuery zoomQuery) {
    }

    public void work() {
        log.fine("Info.Worker.run");
        StringBuffer sql = new StringBuffer(this.m_sqlMain);
        String dynWhere = this.getSQLWhere();
        if (dynWhere.length() > 0) {
            System.out.println("where" + dynWhere);
            sql.append(dynWhere);
        }
        String xSql = Msg.parseTranslation((Properties)this.getCtx(), (String)sql.toString());
        xSql = MRole.getDefault().addAccessSQL(xSql, this.getTableName(), true, false);
        try {
            CPreparedStatement pstmt = DB.prepareStatement((String)xSql, null);
            log.fine("SQL=" + xSql);
            this.setParameters((PreparedStatement)pstmt, false);
            ResultSet rs = pstmt.executeQuery();
            this.p_table.loadTable(rs);
            rs.close();
            pstmt.close();
        }
        catch (SQLException e) {
            log.log(Level.SEVERE, "Info.Worker.run - " + xSql, (Throwable)e);
        }
        if (this.getM_Product_ID() > 0) {
            BigDecimal OnHand = this.getQtyOnHand();
            for (int row = 0; row < this.p_table.getRowCount(); ++row) {
                Timestamp datepromised = (Timestamp)this.p_table.getValueAt(row, 5);
                Timestamp today = new Timestamp(System.currentTimeMillis());
                IDColumn id = (IDColumn)this.p_table.getValueAt(row, 0);
                String TypeMRP = DB.getSQLValueString(null, (String)("SELECT TypeMRP FROM " + this.getTableName() + " WHERE PP_MRP_ID=?"), (int)id.getRecord_ID());
                String OrderType = (String)this.p_table.getValueAt(row, 11);
                if ("D".equals(TypeMRP) || "FCT".equals(OrderType) && datepromised.after(today)) {
                    BigDecimal QtyGrossReqs = (BigDecimal)this.p_table.getValueAt(row, 6);
                    OnHand = OnHand.subtract(QtyGrossReqs);
                    this.p_table.setValueAt((Object)OnHand, row, 9);
                }
                if (!"S".equals(TypeMRP)) continue;
                BigDecimal QtyScheduledReceipts = (BigDecimal)this.p_table.getValueAt(row, 7);
                BigDecimal QtyPlan = (BigDecimal)this.p_table.getValueAt(row, 8);
                if (QtyPlan == null) {
                    QtyPlan = Env.ZERO;
                }
                if (QtyScheduledReceipts == null) {
                    QtyScheduledReceipts = Env.ZERO;
                }
                OnHand = OnHand.add(QtyScheduledReceipts.add(QtyPlan));
                this.p_table.setValueAt((Object)OnHand, row, 9);
            }
        }
    }
}

