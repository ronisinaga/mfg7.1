/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.adempiere.exceptions.FillMandatoryException
 *  org.compiere.model.MAcctSchema
 *  org.compiere.model.MCost
 *  org.compiere.model.MCostElement
 *  org.compiere.model.MProduct
 *  org.compiere.model.Query
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.Env
 *  org.eevolution.model.MPPProductBOM
 *  org.eevolution.model.MPPProductBOMLine
 */
package org.libero.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.engines.CostEngine;
import org.adempiere.model.engines.CostEngineFactory;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MCostElement;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.libero.tables.X_T_BOMLine;

public class CostBillOfMaterial
extends SvrProcess {
    private static final String LEVELS = "....................";
    private int p_AD_Org_ID = 0;
    private int p_C_AcctSchema_ID = 0;
    private int p_M_Product_ID = 0;
    private int p_M_CostType_ID = 0;
    private String p_ConstingMethod = "S";
    private boolean p_implosion = false;
    private int m_LevelNo = 0;
    private int m_SeqNo = 0;
    private MAcctSchema m_as = null;
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
                this.m_as = MAcctSchema.get((Properties)this.getCtx(), (int)this.p_C_AcctSchema_ID);
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
            this.log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
    }

    protected String doIt() throws Exception {
        if (this.p_M_Product_ID == 0) {
            throw new FillMandatoryException(new String[]{"M_Product_ID"});
        }
        this.explodeProduct(this.p_M_Product_ID, false);
        return "";
    }

    private void explodeProduct(int M_Product_ID, boolean isComponent) {
        MProduct product = MProduct.get((Properties)this.getCtx(), (int)M_Product_ID);
        List<MPPProductBOM> list = this.getBOMs(product, isComponent);
        if (!isComponent && list.size() == 0) {
            throw new AdempiereException("@Error@ Product is not a BOM");
        }
        for (MPPProductBOM bom : list) {
            if (!isComponent) {
                this.createLines(bom, null);
            }
            ++this.m_LevelNo;
            for (MPPProductBOMLine bomLine : bom.getLines()) {
                if (!bomLine.isActive()) continue;
                this.createLines(bom, bomLine);
                this.explodeProduct(bomLine.getM_Product_ID(), true);
            }
            --this.m_LevelNo;
        }
    }

    private List<MPPProductBOM> getBOMs(MProduct product, boolean includeAlternativeBOMs) {
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuffer whereClause = new StringBuffer();
        whereClause.append("M_Product_ID").append("=?");
        params.add(product.get_ID());
        if (includeAlternativeBOMs) {
            whereClause.append(" AND ").append("Value").append("=?");
            params.add(product.getValue());
        }
        List list = new Query(this.getCtx(), "PP_Product_BOM", whereClause.toString(), null).setParameters(params).setOnlyActiveRecords(true).setOrderBy("Value").list();
        return list;
    }

    private void createLines(MPPProductBOM bom, MPPProductBOMLine bomLine) {
        BigDecimal qty;
        MProduct product;
        if (bomLine != null) {
            product = MProduct.get((Properties)this.getCtx(), (int)bomLine.getM_Product_ID());
            qty = bomLine.getQty();
        } else if (bom != null) {
            product = MProduct.get((Properties)this.getCtx(), (int)bom.getM_Product_ID());
            qty = Env.ONE;
        } else {
            throw new AdempiereException("@NotFound@ @PP_Product_BOM_ID@");
        }
        for (MCostElement costElement : this.getCostElements()) {
            X_T_BOMLine tboml = new X_T_BOMLine(this.getCtx(), 0, this.get_TrxName());
            tboml.setAD_Org_ID(this.p_AD_Org_ID);
            tboml.setSel_Product_ID(this.p_M_Product_ID);
            tboml.setImplosion(this.p_implosion);
            tboml.setC_AcctSchema_ID(this.p_C_AcctSchema_ID);
            tboml.setM_CostType_ID(this.p_M_CostType_ID);
            tboml.setCostingMethod(this.p_ConstingMethod);
            tboml.setAD_PInstance_ID(this.getAD_PInstance_ID());
            tboml.setM_CostElement_ID(costElement.get_ID());
            tboml.setM_Product_ID(product.get_ID());
            tboml.setQtyBOM(qty);
            tboml.setSeqNo(this.m_SeqNo);
            tboml.setLevelNo(this.m_LevelNo);
            tboml.setLevels(String.valueOf(LEVELS.substring(0, this.m_LevelNo)) + this.m_LevelNo);
            CostEngine engine = CostEngineFactory.getCostEngine(this.getAD_Client_ID());
            Collection<MCost> costs = engine.getByElement(product, this.m_as, this.p_M_CostType_ID, this.p_AD_Org_ID, 0, costElement.getM_CostElement_ID());
            BigDecimal currentCostPrice = Env.ZERO;
            BigDecimal currentCostPriceLL = Env.ZERO;
            BigDecimal futureCostPrice = Env.ZERO;
            BigDecimal futureCostPriceLL = Env.ZERO;
            boolean isCostFrozen = false;
            for (MCost cost : costs) {
                currentCostPrice = currentCostPrice.add(cost.getCurrentCostPrice());
                currentCostPriceLL = currentCostPriceLL.add(cost.getCurrentCostPriceLL());
                futureCostPrice = futureCostPrice.add(cost.getFutureCostPrice());
                futureCostPriceLL = futureCostPriceLL.add(cost.getFutureCostPriceLL());
                isCostFrozen = cost.isCostFrozen();
            }
            tboml.setCurrentCostPrice(currentCostPrice);
            tboml.setCurrentCostPriceLL(currentCostPriceLL);
            tboml.setFutureCostPrice(currentCostPrice);
            tboml.setFutureCostPriceLL(currentCostPriceLL);
            tboml.setIsCostFrozen(isCostFrozen);
            if (bomLine != null) {
                tboml.setPP_Product_BOM_ID(bomLine.getPP_Product_BOM_ID());
                tboml.setPP_Product_BOMLine_ID(bomLine.getPP_Product_BOMLine_ID());
            } else if (bom != null) {
                tboml.setPP_Product_BOM_ID(bom.getPP_Product_BOM_ID());
            }
            tboml.saveEx();
            ++this.m_SeqNo;
        }
    }

    public Collection<MCostElement> getCostElements() {
        if (this.m_costElements == null) {
            this.m_costElements = MCostElement.getByCostingMethod((Properties)this.getCtx(), (String)this.p_ConstingMethod);
        }
        return this.m_costElements;
    }
}

