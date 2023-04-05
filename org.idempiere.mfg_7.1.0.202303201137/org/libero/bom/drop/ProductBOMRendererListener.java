/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.webui.component.NumberBox
 *  org.adempiere.webui.editor.WNumberEditor
 *  org.adempiere.webui.event.ValueChangeEvent
 *  org.adempiere.webui.event.ValueChangeListener
 *  org.compiere.util.CLogger
 *  org.compiere.util.Env
 *  org.compiere.util.Msg
 *  org.zkoss.zk.ui.Component
 *  org.zkoss.zul.Label
 *  org.zkoss.zul.Tree
 *  org.zkoss.zul.Treecell
 *  org.zkoss.zul.Treechildren
 *  org.zkoss.zul.Treecol
 *  org.zkoss.zul.Treeitem
 *  org.zkoss.zul.Treerow
 */
package org.libero.bom.drop;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.adempiere.webui.component.NumberBox;
import org.adempiere.webui.editor.WNumberEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.libero.bom.drop.IRendererListener;
import org.libero.bom.drop.ISupportRadioNode;
import org.libero.bom.drop.ProductBOMTreeNode;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

public class ProductBOMRendererListener
implements IRendererListener,
PropertyChangeListener,
ValueChangeListener {
    protected static CLogger log = CLogger.getCLogger(ProductBOMRendererListener.class);
    public static final String QTY_COMPONENT = "qty_component";
    public static final String TOTAL_QTY = "total_qty";
    public static final String TOTAL_PRICE = "total_price";
    public static final String Tree_ITEM = "tree_item";
    protected Tree tree;
    private static BigDecimal GrandTotal = Env.ZERO;
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public void render(Treeitem item, Treerow row, ISupportRadioNode data, int index) {
        if (data != null && data instanceof ProductBOMTreeNode) {
            BigDecimal buffer = Env.ZERO;
            ProductBOMTreeNode productBOMTreeNode = (ProductBOMTreeNode)data;
            Treecell available = new Treecell();
            row.appendChild((Component)available);
            Treecell inputcell = new Treecell();
            row.appendChild((Component)inputcell);
            Treecell pricecell = new Treecell();
            row.appendChild((Component)pricecell);
            Treecell totcell = new Treecell();
            row.appendChild((Component)totcell);
            Treecell totalpricecell = new Treecell();
            row.appendChild((Component)totalpricecell);
            if (item.getTree().getTreecols() != null && item.getTree().getTreecols().getChildren().size() < row.getChildren().size()) {
                item.getTree().getTreecols().appendChild((Component)new Treecol());
                item.getTree().getTreecols().appendChild((Component)new Treecol(Msg.translate((Properties)Env.getCtx(), (String)"QtyAvailable")));
                item.getTree().getTreecols().appendChild((Component)new Treecol(Msg.translate((Properties)Env.getCtx(), (String)"Qty")));
                item.getTree().getTreecols().appendChild((Component)new Treecol(Msg.translate((Properties)Env.getCtx(), (String)"Price")));
                item.getTree().getTreecols().appendChild((Component)new Treecol(Msg.translate((Properties)Env.getCtx(), (String)"TotalQty")));
                item.getTree().getTreecols().appendChild((Component)new Treecol(Msg.translate((Properties)Env.getCtx(), (String)"TotalPrice")));
            }
            boolean editQty = false;
            editQty = "VA".equals(productBOMTreeNode.getComponentType());
            productBOMTreeNode.getLabel();
            Label availableQty = new Label();
            WNumberEditor inputQty = new WNumberEditor();
            NumberBox price = new NumberBox(false);
            NumberBox totQty = new NumberBox(false);
            NumberBox totPrice = new NumberBox(false);
            price.setEnabled(false);
            totQty.setEnabled(false);
            totPrice.setEnabled(false);
            price.getDecimalbox().setScale(2);
            totPrice.getDecimalbox().setStyle("text-align:right");
            inputQty.setReadWrite(editQty);
            totQty.getDecimalbox().setScale(2);
            totPrice.getDecimalbox().setScale(2);
            available.appendChild((Component)availableQty);
            inputcell.appendChild((Component)inputQty.getComponent());
            pricecell.appendChild((Component)price);
            totcell.appendChild((Component)totQty);
            totalpricecell.appendChild((Component)totPrice);
            if (productBOMTreeNode.productBOMLine != null) {
                availableQty.setValue(productBOMTreeNode.getQtyAvailable().toString());
                inputQty.setValue((Object)productBOMTreeNode.getQty());
                price.setValue((Object)productBOMTreeNode.getRowPrice());
                totQty.setValue((Object)productBOMTreeNode.getTotQty());
                totPrice.setValue((Object)productBOMTreeNode.calculateRowTotalPrice(productBOMTreeNode.getTotQty()));
                item.setAttribute(TOTAL_QTY, (Object)totQty);
                totQty.setAttribute(Tree_ITEM, (Object)item);
                item.setAttribute(TOTAL_PRICE, (Object)totPrice);
                totPrice.setAttribute(Tree_ITEM, (Object)item);
                if (!ProductBOMRendererListener.isParentChecked(item)) {
                    totQty.setValue((Object)Env.ZERO);
                    totPrice.setValue((Object)Env.ZERO);
                }
                if (productBOMTreeNode.getChildCount() > 0) {
                    totPrice.setValue((Object)Env.ZERO);
                }
                GrandTotal = GrandTotal.add(totPrice.getValue());
                this.propertyChangeSupport.firePropertyChange("GrandTotal", buffer, GrandTotal);
                buffer = GrandTotal;
            } else {
                log.warning(data.toString());
            }
            item.setAttribute(QTY_COMPONENT, (Object)inputQty);
            inputQty.getComponent().setAttribute(Tree_ITEM, (Object)item);
            productBOMTreeNode.addPropertyChangeListener(this);
            inputQty.addValueChangeListener((ValueChangeListener)this);
        }
    }

    private void rollUpParentNodeTotalPricing(Treeitem treeItem) {
        ProductBOMTreeNode rootNode = (ProductBOMTreeNode)this.tree.getModel().getRoot();
        if (rootNode.getChildCount() > 0) {
            BigDecimal grandtotalprice = this.rollupRoutine(rootNode.bomChilds);
            if (grandtotalprice.compareTo(GrandTotal) == 0) {
                log.info("Grand Total CORRECT = Sum of Parent Nodes");
            } else {
                log.info("Grand Total ERROR != Sum sub parent nodes");
            }
        }
    }

    private BigDecimal rollupRoutine(List<ProductBOMTreeNode> bomchildren) {
        BigDecimal nodeTotalPrice = Env.ZERO;
        for (ProductBOMTreeNode node : bomchildren) {
            if (node.getChildCount() > 0) {
                BigDecimal totalPrice = this.rollupRoutine(node.bomChilds);
                int[] pathToNode = this.tree.getModel().getPath((Object)node);
                Treeitem treeItem = this.tree.renderItemByPath(pathToNode);
                NumberBox itemTotalPrice = (NumberBox)treeItem.getAttribute(TOTAL_PRICE);
                itemTotalPrice.setValue((Object)totalPrice);
                itemTotalPrice.getDecimalbox().setStyle("font-size:16px;color:gray;text-align:right;font-weight: bold");
                if (treeItem.getLevel() > 0) {
                    Integer fontsize = treeItem.getRoot().getChildren().size() - treeItem.getLevel() + 13;
                    itemTotalPrice.getDecimalbox().setStyle("font-size:" + fontsize.toString() + "px;color:gray;text-align:right;font-weight: bold");
                }
                nodeTotalPrice = nodeTotalPrice.add(itemTotalPrice.getValue());
                continue;
            }
            int[] pathToNode = this.tree.getModel().getPath((Object)node);
            Treeitem bomitem = this.tree.renderItemByPath(pathToNode);
            NumberBox itemTotalPrice = (NumberBox)bomitem.getAttribute(TOTAL_PRICE);
            nodeTotalPrice = nodeTotalPrice.add(itemTotalPrice.getValue());
        }
        return nodeTotalPrice;
    }

    private static boolean isParentChecked(Treeitem thisItem) {
        if (thisItem == null) {
            return true;
        }
        Treeitem parentItem = thisItem;
        while (parentItem != null) {
            ProductBOMTreeNode dataItem = (ProductBOMTreeNode)parentItem.getAttribute("REF_DATA_MODEL");
            if (!dataItem.isChecked() || dataItem.getTotQty().compareTo(Env.ZERO) == 0) {
                return false;
            }
            thisItem = parentItem;
            parentItem = thisItem.getParentItem();
        }
        return true;
    }

    @Override
    public void onchecked(Treeitem item, ISupportRadioNode data, boolean isChecked) {
        BigDecimal totQty = Env.ZERO;
        BigDecimal totPrice = Env.ZERO;
        if (ProductBOMRendererListener.isParentChecked(item)) {
            totQty = ((ProductBOMTreeNode)data).getQty();
            Treeitem parent = item.getParentItem();
            if (parent != null) {
                NumberBox totalQtyComponent = (NumberBox)parent.getAttribute(TOTAL_QTY);
                totQty = totQty.multiply(totalQtyComponent.getValue());
            }
            totPrice = ((ProductBOMTreeNode)data).calculateRowTotalPrice(totQty);
            if (item.isEmpty()) {
                GrandTotal = GrandTotal.add(totPrice);
            }
        } else if (item.isEmpty()) {
            NumberBox totalQtyComponent = (NumberBox)item.getAttribute(TOTAL_QTY);
            GrandTotal = GrandTotal.subtract(((ProductBOMTreeNode)data).calculateRowTotalPrice(totalQtyComponent.getValue()));
        }
        int[] pathToNode = this.tree.getModel().getPath((Object)data);
        Treeitem treeItem = this.tree.renderItemByPath(pathToNode);
        NumberBox totalQtyComponent = (NumberBox)item.getAttribute(TOTAL_QTY);
        totalQtyComponent.setValue((Object)totQty);
        NumberBox totPriceComponent = (NumberBox)treeItem.getAttribute(TOTAL_PRICE);
        BigDecimal oldvalue = totPriceComponent.getValue();
        this.propertyChangeSupport.firePropertyChange("GrandTotal", totPrice, oldvalue);
        totPriceComponent.setValue((Object)totPrice);
        if (!treeItem.isEmpty()) {
            this.cascadeChildren(treeItem);
        }
        this.rollUpParentNodeTotalPricing(treeItem);
    }

    private void cascadeChildren(Treeitem treeItem) {
        Treechildren tch = treeItem.getTreechildren();
        if (tch != null) {
            Collection children = tch.getItems();
            for (Treeitem child : children) {
                ProductBOMTreeNode treeNode = (ProductBOMTreeNode)child.getAttribute("REF_DATA_MODEL");
                int[] pathToNode = this.tree.getModel().getPath((Object)treeNode);
                child = this.tree.renderItemByPath(pathToNode);
                Treeitem parentTreeItem = child.getParentItem();
                ProductBOMTreeNode parentNode = (ProductBOMTreeNode)parentTreeItem.getAttribute("REF_DATA_MODEL");
                BigDecimal totQty = Env.ZERO;
                if (ProductBOMRendererListener.isParentChecked(child)) {
                    totQty = treeNode.getQty().multiply(parentNode.getTotQty());
                }
                NumberBox totQtyComponent = (NumberBox)child.getAttribute(TOTAL_QTY);
                totQtyComponent.setValue((Object)totQty);
                NumberBox totPriceComponent = (NumberBox)child.getAttribute(TOTAL_PRICE);
                BigDecimal oldPrice = totPriceComponent.getValue();
                BigDecimal totPrice = totQty.multiply(treeNode.getPriceStdAmt());
                GrandTotal = GrandTotal.subtract(oldPrice);
                GrandTotal = GrandTotal.add(totPrice);
                this.propertyChangeSupport.firePropertyChange("GrandTotal", totPrice, oldPrice);
                totPriceComponent.setValue((Object)totPrice);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ProductBOMTreeNode nodeChange = (ProductBOMTreeNode)evt.getSource();
        int[] pathToNode = this.tree.getModel().getPath((Object)nodeChange);
        Treeitem treeItem = this.tree.renderItemByPath(pathToNode);
        WNumberEditor editor = (WNumberEditor)treeItem.getAttribute(QTY_COMPONENT);
        BigDecimal newQty = (BigDecimal)evt.getNewValue();
        editor.setValue((Object)newQty);
        Treeitem parent = treeItem.getParentItem();
        BigDecimal newPrice = nodeChange.getPriceStdAmt();
        BigDecimal parentTotQty = Env.ONE;
        if (ProductBOMRendererListener.isParentChecked(treeItem) && parent != null) {
            ProductBOMTreeNode parentNode = (ProductBOMTreeNode)parent.getAttribute("REF_DATA_MODEL");
            parentTotQty = parentNode.getTotQty();
        }
        BigDecimal totQty = newQty.multiply(parentTotQty);
        newPrice = newPrice.multiply(totQty);
        NumberBox totQtyComponent = (NumberBox)treeItem.getAttribute(TOTAL_QTY);
        totQtyComponent.setValue((Object)totQty);
        NumberBox totPriceComponent = (NumberBox)treeItem.getAttribute(TOTAL_PRICE);
        GrandTotal = GrandTotal.subtract(totPriceComponent.getValue());
        GrandTotal = GrandTotal.add(newPrice);
        this.propertyChangeSupport.firePropertyChange("GrandTotal", totPriceComponent.getValue(), newPrice);
        totPriceComponent.setValue((Object)newPrice);
        nodeChange.setTotQty(totQty);
        if (!treeItem.isEmpty()) {
            this.cascadeChildren(treeItem);
        }
    }

    public void valueChange(ValueChangeEvent evt) {
        Treeitem treeItem = (Treeitem)((WNumberEditor)evt.getSource()).getComponent().getAttribute(Tree_ITEM);
        ProductBOMTreeNode nodeModel = (ProductBOMTreeNode)treeItem.getAttribute("REF_DATA_MODEL");
        nodeModel.setQty((BigDecimal)evt.getNewValue());
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public static String getGrandTotal() {
        return GrandTotal.setScale(2).toString();
    }

    public static void setGrandTotal(BigDecimal a) {
        GrandTotal = a;
    }
}

