/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MProduct
 *  org.compiere.model.MProductPrice
 *  org.compiere.model.MStorageOnHand
 *  org.compiere.model.Query
 *  org.compiere.util.CLogger
 *  org.compiere.util.Env
 *  org.eevolution.model.MPPProductBOM
 *  org.eevolution.model.MPPProductBOMLine
 */
package org.libero.bom.drop;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.Query;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.libero.bom.drop.ISupportRadioNode;

public class ProductBOMTreeNode
implements ISupportRadioNode {
    protected static CLogger log = CLogger.getCLogger(ProductBOMTreeNode.class);
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    MProduct product;
    MPPProductBOMLine productBOMLine;
    List<ProductBOMTreeNode> bomChilds;
    boolean invidateState = false;
    boolean isChecked = false;
    private ComparatorBOMTreeNode comparatorBOMTreeNode = new ComparatorBOMTreeNode();
    private BigDecimal unitQty = BigDecimal.ZERO;
    private BigDecimal totQty = BigDecimal.ZERO;
    private BigDecimal qtyAvailable;
    private BigDecimal priceStdAmt = Env.ZERO;
    private BigDecimal priceTotalAmt = Env.ZERO;
    public static int PriceListVersion = 0;

    public ProductBOMTreeNode(MProduct product, BigDecimal qty) {
        this.product = product;
        this.unitQty = qty;
    }

    public ProductBOMTreeNode(MPPProductBOMLine productBOMLine, BigDecimal parrentQty) {
        this.productBOMLine = productBOMLine;
        this.totQty = parrentQty.multiply(productBOMLine.getQty());
        this.unitQty = productBOMLine.getQty();
    }

    protected void initChilds() {
        MPPProductBOM bom;
        if (this.bomChilds != null) {
            return;
        }
        this.bomChilds = new ArrayList<ProductBOMTreeNode>();
        MPPProductBOMLine[] bomLines = null;
        if (this.product != null) {
            bom = (MPPProductBOM)new Query(Env.getCtx(), "PP_Product_BOM", "M_Product_ID=?", null).setParameters(new Object[]{this.product.get_ID()}).first();
            bomLines = bom.getLines();
        } else if (this.productBOMLine != null) {
            bom = (MPPProductBOM)new Query(Env.getCtx(), "PP_Product_BOM", "M_Product_ID=?", null).setParameters(new Object[]{this.productBOMLine.getM_Product_ID()}).first();
            if (bom != null) {
                bomLines = bom.getLines();
            }
        } else {
            this.invidateState = true;
            return;
        }
        if (bomLines != null) {
            MPPProductBOMLine[] arrmPPProductBOMLine = bomLines;
            int n = bomLines.length;
            for (int i = 0; i < n; ++i) {
                MPPProductBOMLine bomLine = arrmPPProductBOMLine[i];
                this.bomChilds.add(new ProductBOMTreeNode(bomLine, this.totQty.compareTo(Env.ZERO) > 0 ? this.totQty : this.unitQty));
            }
        }
        Collections.sort(this.bomChilds, this.comparatorBOMTreeNode);
    }

    @Override
    public boolean isLeaf() {
        this.initChilds();
        return this.bomChilds.size() == 0;
    }

    @Override
    public ISupportRadioNode getChild(int index) {
        this.initChilds();
        return this.bomChilds.get(index);
    }

    @Override
    public int getChildCount() {
        this.initChilds();
        return this.bomChilds.size();
    }

    @Override
    public boolean isRadio() {
        return !"OP".equals(this.getComponentType()) && !"CO".equals(this.getComponentType()) && !"VA".equals(this.getComponentType());
    }

    @Override
    public String getGroupName() {
        if (this.isRadio()) {
            return this.getComponentType();
        }
        return "";
    }

    @Override
    public boolean isChecked() {
        if ("CO".equals(this.getComponentType()) || "VA".equals(this.getComponentType())) {
            return true;
        }
        return this.isChecked;
    }

    @Override
    public boolean isDisable() {
        return "VA".equals(this.getComponentType()) || "CO".equals(this.getComponentType());
    }

    @Override
    public void setIsChecked(boolean isChecked) {
        if ("CO".equals(this.getComponentType()) || "VA".equals(this.getComponentType())) {
            return;
        }
        this.isChecked = isChecked;
    }

    @Override
    public void setIsDisable(boolean isDisable) {
    }

    protected String getComponentType(MPPProductBOMLine bomLine) {
        if (bomLine != null && bomLine.getComponentType() != null) {
            return bomLine.getComponentType();
        }
        return "CO";
    }

    protected String getComponentType() {
        return this.getComponentType(this.productBOMLine);
    }

    @Override
    public String getLabel() {
        String label = "";
        if (this.productBOMLine != null) {
            label = this.productBOMLine.getProduct().getName();
            this.qtyAvailable = this.getQtyOnHand(this.productBOMLine.getProduct());
        } else if (this.product != null) {
            label = this.product.getName();
            this.qtyAvailable = this.getQtyOnHand(this.product);
        }
        return label;
    }

    public BigDecimal getQtyAvailable() {
        return this.qtyAvailable;
    }

    private BigDecimal getQtyOnHand(MProduct prod) {
        MStorageOnHand[] storages;
        BigDecimal qtyOnHand = BigDecimal.ZERO;
        MStorageOnHand[] arrmStorageOnHand = storages = MStorageOnHand.getOfProduct((Properties)Env.getCtx(), (int)prod.getM_Product_ID(), (String)prod.get_TrxName());
        int n = storages.length;
        for (int i = 0; i < n; ++i) {
            MStorageOnHand storage = arrmStorageOnHand[i];
            if (storage == null) continue;
            qtyOnHand = qtyOnHand.add(storage.getQtyOnHand());
        }
        return qtyOnHand;
    }

    public int getProductID() {
        if (this.product == null && this.productBOMLine == null) {
            throw new IllegalStateException("no product info in this node");
        }
        MProduct productNode = this.product != null ? this.product : this.productBOMLine.getProduct();
        return productNode.get_ID();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public BigDecimal getQty() {
        return this.unitQty;
    }

    public BigDecimal getTotQty() {
        return this.totQty;
    }

    public void setTotQty(BigDecimal totQty) {
        this.totQty = totQty;
    }

    public void setQty(BigDecimal qty) {
        if (!this.unitQty.equals(qty)) {
            BigDecimal oldValue = this.unitQty;
            this.unitQty = qty;
            this.propertyChangeSupport.firePropertyChange("qty", oldValue, this.unitQty);
        }
    }

    public BigDecimal getRowPrice() {
        BigDecimal priceStd;
        MProductPrice price = (MProductPrice)new Query(Env.getCtx(), "M_ProductPrice", "M_Product_ID=? AND M_PriceList_Version_ID=?", null).setParameters(new Object[]{this.productBOMLine.getM_Product_ID(), PriceListVersion}).first();
        if (price == null) {
            this.priceStdAmt = Env.ZERO;
            return Env.ZERO;
        }
        this.priceStdAmt = priceStd = price.getPriceStd();
        return priceStd;
    }

    public BigDecimal calculateRowTotalPrice(BigDecimal qty) {
        this.priceTotalAmt = this.priceStdAmt.multiply(qty);
        return this.priceTotalAmt;
    }

    public BigDecimal getPriceStdAmt() {
        return this.priceStdAmt;
    }

    public BigDecimal getTotalPrice() {
        return this.priceTotalAmt;
    }

    class ComparatorBOMTreeNode
    implements Comparator<ProductBOMTreeNode> {
        ComparatorBOMTreeNode() {
        }

        @Override
        public int compare(ProductBOMTreeNode bom1, ProductBOMTreeNode bom2) {
            if (ProductBOMTreeNode.this.getComponentType(bom1.productBOMLine).equals(ProductBOMTreeNode.this.getComponentType(bom2.productBOMLine))) {
                return 0;
            }
            String t1 = String.valueOf(bom1.productBOMLine.getLine() + 100000);
            String t2 = String.valueOf(bom2.productBOMLine.getLine() + 100000);
            return t1.compareTo(t2);
        }
    }
}

