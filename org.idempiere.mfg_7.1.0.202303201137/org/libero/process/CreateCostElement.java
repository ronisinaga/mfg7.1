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
 */
package org.libero.process;

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

public class CreateCostElement
extends SvrProcess {
    private Integer p_AD_Org_ID = null;
    private int p_C_AcctSchema_ID = 0;
    private int p_M_CostType_ID = 0;
    private int p_M_CostElement_ID = 0;
    private int p_M_Product_Category_ID = 0;
    private int p_M_Product_ID = 0;
    private int p_M_AttributeSetInstance_ID = 0;
    private Collection<MCostElement> m_costElements = null;
    private int[] m_productIDs = null;

    protected void prepare() {
        ProcessInfoParameter[] para = this.getParameter();
        for (int i = 0; i < para.length; ++i) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) continue;
            if (name.equals("AD_Org_ID")) {
                this.p_AD_Org_ID = para[i].getParameterAsInt();
                continue;
            }
            if (name.equals("C_AcctSchema_ID")) {
                this.p_C_AcctSchema_ID = para[i].getParameterAsInt();
                continue;
            }
            if (name.equals("M_CostType_ID")) {
                this.p_M_CostType_ID = para[i].getParameterAsInt();
                continue;
            }
            if (name.equals("M_CostElement_ID")) {
                this.p_M_CostElement_ID = para[i].getParameterAsInt();
                continue;
            }
            if (name.equals("M_Product_Category_ID")) {
                this.p_M_Product_Category_ID = para[i].getParameterAsInt();
                continue;
            }
            if (name.equals("M_Product_ID")) {
                this.p_M_Product_ID = para[i].getParameterAsInt();
                continue;
            }
            if (name.equals("M_AttributeSetInstance_ID")) {
                this.p_M_AttributeSetInstance_ID = para[i].getParameterAsInt();
                continue;
            }
            this.log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
    }

    protected String doIt() throws Exception {
        MAcctSchema as = MAcctSchema.get((Properties)this.getCtx(), (int)this.p_C_AcctSchema_ID);
        int count_costs = 0;
        int count_all = 0;
        for (int org_id : this.getOrgs(as)) {
            for (int product_id : this.getProduct_IDs()) {
                for (MCostElement element : this.getElements()) {
                    CostDimension d = new CostDimension(this.getAD_Client_ID(), org_id, product_id, 0, this.p_M_CostType_ID, as.get_ID(), element.get_ID());
                    MCost cost = (MCost)d.toQuery(MCost.class, this.get_TrxName()).firstOnly();
                    if (cost == null) {
                        MProduct product = MProduct.get((Properties)this.getCtx(), (int)product_id);
                        cost = new MCost(product, d.getM_AttributeSetInstance_ID(), as, d.getAD_Org_ID(), d.getM_CostElement_ID());
                        cost.setM_CostType_ID(d.getM_CostType_ID());
                        cost.saveEx(this.get_TrxName());
                        ++count_costs;
                    }
                    ++count_all;
                }
            }
        }
        return "@Created@ #" + count_costs + " / " + count_all;
    }

    private int[] getOrgs(MAcctSchema as) {
        String whereClause = "";
        ArrayList<Integer> params = new ArrayList<Integer>();
        String CostingLevel = as.getCostingLevel();
        if ("C".equals(CostingLevel)) {
            this.p_AD_Org_ID = 0;
            this.p_M_AttributeSetInstance_ID = 0;
            return new int[1];
        }
        if (this.p_AD_Org_ID != null) {
            whereClause = "AD_Org_ID=?";
            params.add(this.p_AD_Org_ID);
        }
        return new Query(this.getCtx(), "AD_Org", whereClause, this.get_TrxName()).setParameters(params).setClient_ID().getIDs();
    }

    private Collection<MCostElement> getElements() {
        if (this.m_costElements != null) {
            return this.m_costElements;
        }
        String whereClauseElements = "";
        ArrayList<Integer> paramsElements = new ArrayList<Integer>();
        if (this.p_M_CostElement_ID > 0) {
            whereClauseElements = "M_CostElement_ID=?";
            paramsElements.add(this.p_M_CostElement_ID);
        }
        this.m_costElements = new Query(this.getCtx(), "M_CostElement", whereClauseElements, this.get_TrxName()).setParameters(paramsElements).setOnlyActiveRecords(true).setClient_ID().list();
        return this.m_costElements;
    }

    private int[] getProduct_IDs() {
        if (this.m_productIDs != null) {
            return this.m_productIDs;
        }
        String whereClauseProducts = "";
        ArrayList<Integer> paramsProducts = new ArrayList<Integer>();
        if (this.p_M_Product_Category_ID > 0) {
            whereClauseProducts = "M_Product_Category_ID=?";
            paramsProducts.add(this.p_M_Product_Category_ID);
            this.p_M_Product_ID = 0;
        }
        if (this.p_M_Product_ID > 0) {
            if (whereClauseProducts.length() > 0) {
                whereClauseProducts = String.valueOf(whereClauseProducts) + " AND ";
            }
            whereClauseProducts = String.valueOf(whereClauseProducts) + "M_Product_ID=?";
            paramsProducts.add(this.p_M_Product_ID);
        } else {
            this.p_M_AttributeSetInstance_ID = 0;
        }
        this.m_productIDs = new Query(this.getCtx(), "M_Product", whereClauseProducts, this.get_TrxName()).setClient_ID().setParameters(paramsProducts).getIDs();
        return this.m_productIDs;
    }
}

