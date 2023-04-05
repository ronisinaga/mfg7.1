/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MAcctSchema
 *  org.compiere.model.MCost
 *  org.compiere.model.MCostElement
 *  org.compiere.model.MProduct
 *  org.compiere.model.Query
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.eevolution.model.MPPProductBOM
 *  org.eevolution.model.MPPProductBOMLine
 *  org.eevolution.model.MPPProductPlanning
 */
package org.libero.process;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.model.engines.CostDimension;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MCostElement;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.eevolution.model.MPPProductPlanning;
import org.libero.model.MPPMRP;

public class RollupBillOfMaterial
extends SvrProcess {
    private int p_AD_Org_ID = 0;
    private int p_C_AcctSchema_ID = 0;
    private int p_M_CostType_ID = 0;
    private String p_ConstingMethod = "";
    private int p_M_Product_ID = 0;
    private int p_M_Product_Category_ID = 0;
    private String p_ProductType = null;
    private int p_S_Resource_ID;
    private int p_M_Warehouse_ID;
    private Collection<MCostElement> m_costElements = null;

    protected void prepare() {
        for (ProcessInfoParameter para : this.getParameter()) {
            String name = para.getParameterName();
            if (para.getParameter() == null) continue;
            if (name.equals("AD_Org_ID")) {
                this.p_AD_Org_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("C_AcctSchema_ID")) {
                this.p_C_AcctSchema_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("M_CostType_ID")) {
                this.p_M_CostType_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("CostingMethod")) {
                this.p_ConstingMethod = (String)para.getParameter();
                continue;
            }
            if (name.equals("M_Product_ID")) {
                this.p_M_Product_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("M_Product_Category_ID")) {
                this.p_M_Product_Category_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("ProductType")) {
                this.p_ProductType = para.getParameter() == null ? null : para.getParameter().toString();
                continue;
            }
            if (name.equals("S_Resource_ID")) {
                this.p_S_Resource_ID = para.getParameter() == null ? null : Integer.valueOf(para.getParameterAsInt());
                continue;
            }
            if (name.equals("M_Warehouse_ID")) {
                this.p_M_Warehouse_ID = para.getParameter() == null ? null : Integer.valueOf(para.getParameterAsInt());
                continue;
            }
            this.log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
    }

    protected String doIt() throws Exception {
        int maxLowLevel;
        this.resetCostsLLForLLC0();
        for (int lowLevel = maxLowLevel = MPPMRP.getMaxLowLevel(this.getCtx(), this.get_TrxName()); lowLevel >= 0; --lowLevel) {
            for (MProduct product : this.getProducts(lowLevel)) {
                MPPProductBOM bom;
                System.out.println(product.getName());
                MPPProductPlanning pp = MPPProductPlanning.find((Properties)this.getCtx(), (int)this.p_AD_Org_ID, (int)this.p_M_Warehouse_ID, (int)this.p_S_Resource_ID, (int)product.getM_Product_ID(), (String)this.get_TrxName());
                System.out.println("sagagag" + (Object)pp);
                int PP_Product_BOM_ID = 0;
                if (pp != null) {
                    PP_Product_BOM_ID = pp.getPP_Product_BOM_ID();
                } else {
                    this.createNotice(product, "@NotFound@ @PP_Product_Planning_ID@");
                }
                if (PP_Product_BOM_ID <= 0) {
                    PP_Product_BOM_ID = MPPProductBOM.getBOMSearchKey((MProduct)product);
                }
                if ((bom = MPPProductBOM.get((Properties)this.getCtx(), (int)PP_Product_BOM_ID)) == null) {
                    this.createNotice(product, "@NotFound@ @PP_Product_BOM_ID@");
                }
                this.rollup(product, bom);
            }
        }
        return "@OK@";
    }

    protected void rollup(MProduct product, MPPProductBOM bom) {
        for (MCostElement element : this.getCostElements()) {
            System.out.println("yoyo" + element.getM_CostElement_ID() + " " + product.getM_Product_ID());
            for (MCost cost : this.getCosts(product, element.get_ID())) {
                System.out.println("Calculate Lower Cost for: " + (Object)bom);
                this.log.info("Calculate Lower Cost for: " + (Object)bom);
                BigDecimal price = this.getCurrentCostPriceLL(bom, element);
                this.log.info(String.valueOf(element.getName()) + " Cost Low Level:" + price);
                cost.setCurrentCostPriceLL(price);
                this.updateCoProductCosts(bom, cost);
                cost.saveEx();
            }
        }
    }

    private void updateCoProductCosts(MPPProductBOM bom, MCost baseCost) {
        System.out.println("sfsfs" + (Object)bom);
        if (bom == null) {
            return;
        }
        BigDecimal costPriceTotal = Env.ZERO;
        for (MPPProductBOMLine bomline : bom.getLines()) {
            if (!bomline.isCoProduct()) continue;
            System.out.println(baseCost.getCurrentCostPriceLL());
            BigDecimal costPrice = baseCost.getCurrentCostPriceLL().multiply(bomline.getCostAllocationPerc(true));
            System.out.println("sfafaf" + costPrice);
            MCost cost = MCost.get((Properties)baseCost.getCtx(), (int)baseCost.getAD_Client_ID(), (int)baseCost.getAD_Org_ID(), (int)bomline.getM_Product_ID(), (int)baseCost.getM_CostType_ID(), (int)baseCost.getC_AcctSchema_ID(), (int)baseCost.getM_CostElement_ID(), (int)0, (String)baseCost.get_TrxName());
            if (cost == null) {
                cost = new MCost(baseCost.getCtx(), 0, baseCost.get_TrxName());
                cost.setAD_Org_ID(baseCost.getAD_Org_ID());
                cost.setM_Product_ID(bomline.getM_Product_ID());
                cost.setM_CostType_ID(baseCost.getM_CostType_ID());
                cost.setC_AcctSchema_ID(baseCost.getC_AcctSchema_ID());
                cost.setM_CostElement_ID(baseCost.getM_CostElement_ID());
                cost.setM_AttributeSetInstance_ID(0);
            }
            cost.setCurrentCostPriceLL(costPrice);
            cost.saveEx();
            costPriceTotal = costPriceTotal.add(costPrice);
        }
        if (costPriceTotal.signum() != 0) {
            baseCost.setCurrentCostPriceLL(costPriceTotal);
        }
    }

    private BigDecimal getCurrentCostPriceLL(MPPProductBOM bom, MCostElement element) {
        this.log.info("Element: " + (Object)element);
        System.out.println("Element: " + element.getM_CostElement_ID() + " " + (Object)bom);
        BigDecimal costPriceLL = Env.ZERO;
        if (bom == null) {
            return costPriceLL;
        }
        for (MPPProductBOMLine bomline : bom.getLines()) {
            if (bomline.isCoProduct()) continue;
            System.out.println("tes" + bomline.getM_Product_ID());
            MProduct component = MProduct.get((Properties)this.getCtx(), (int)bomline.getM_Product_ID());
            for (MCost cost : this.getCosts(component, element.get_ID())) {
                BigDecimal qty = bomline.getQty(true);
                if (bomline.isByProduct()) {
                    cost.setCurrentCostPriceLL(Env.ZERO);
                }
                BigDecimal costPrice = cost.getCurrentCostPrice().add(cost.getCurrentCostPriceLL());
                System.out.println("costpricessgs" + costPrice);
                BigDecimal componentCost = costPrice.multiply(qty);
                costPriceLL = costPriceLL.add(componentCost);
                this.log.info("CostElement: " + element.getName() + ", Component: " + component.getValue() + ", CostPrice: " + costPrice + ", Qty: " + qty + ", Cost: " + componentCost + " => Total Cost Element: " + costPriceLL);
            }
        }
        return costPriceLL;
    }

    private Collection<MCost> getCosts(MProduct product, int M_CostElement_ID) {
        MAcctSchema as = MAcctSchema.get((Properties)this.getCtx(), (int)this.p_C_AcctSchema_ID);
        CostDimension d = new CostDimension(product, as, this.p_M_CostType_ID, this.p_AD_Org_ID, DB.getSQLValue(null, (String)"select m_attributesetinstance_id from m_cost where m_product_id = ?", (int)product.getM_Product_ID()), M_CostElement_ID);
        return d.toQuery(MCost.class, this.get_TrxName()).list();
    }

    private Collection<MProduct> getProducts(int lowLevel) {
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuffer whereClause = new StringBuffer("AD_Client_ID=?").append(" AND ").append("LowLevel").append("=?");
        params.add(this.getAD_Client_ID());
        params.add(lowLevel);
        whereClause.append(" AND ").append("IsBOM").append("=?");
        params.add(true);
        if (this.p_M_Product_ID > 0) {
            whereClause.append(" AND ").append("M_Product_ID").append("=?");
            params.add(this.p_M_Product_ID);
        } else if (this.p_M_Product_Category_ID > 0) {
            whereClause.append(" AND ").append("M_Product_Category_ID").append("=?");
            params.add(this.p_M_Product_Category_ID);
        }
        if (this.p_M_Product_ID <= 0 && this.p_ProductType != null) {
            whereClause.append(" AND ").append("ProductType").append("=?");
            params.add(this.p_ProductType);
        }
        return new Query(this.getCtx(), "M_Product", whereClause.toString(), this.get_TrxName()).setParameters(params).list();
    }

    private void resetCostsLLForLLC0() {
        ArrayList<Integer> params = new ArrayList<Integer>();
        StringBuffer productWhereClause = new StringBuffer();
        productWhereClause.append("AD_Client_ID=? AND LowLevel=?");
        params.add(this.getAD_Client_ID());
        params.add(0);
        if (this.p_M_Product_ID > 0) {
            productWhereClause.append(" AND ").append("M_Product_ID").append("=?");
            params.add(this.p_M_Product_ID);
        } else if (this.p_M_Product_Category_ID > 0) {
            productWhereClause.append(" AND ").append("M_Product_Category_ID").append("=?");
            params.add(this.p_M_Product_Category_ID);
        }
        String sql = "UPDATE M_Cost c SET CurrentCostPriceLL=0 WHERE EXISTS (SELECT 1 FROM M_Product p WHERE p.M_Product_ID=c.M_Product_ID AND " + productWhereClause + ")";
        int no = DB.executeUpdateEx((String)sql, (Object[])params.toArray(), (String)this.get_TrxName());
        this.log.info("Updated #" + no);
    }

    private Collection<MCostElement> getCostElements() {
        if (this.m_costElements == null) {
            this.m_costElements = MCostElement.getByCostingMethod((Properties)this.getCtx(), (String)this.p_ConstingMethod);
        }
        return this.m_costElements;
    }

    private void createNotice(MProduct product, String msg) {
        String productValue = product != null ? product.getValue() : "-";
        this.addLog("WARNING: Product " + productValue + ": " + msg);
    }
}

