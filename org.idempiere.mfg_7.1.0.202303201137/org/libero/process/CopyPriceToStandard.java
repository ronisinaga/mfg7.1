/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.compiere.model.MAcctSchema
 *  org.compiere.model.MConversionRate
 *  org.compiere.model.MCost
 *  org.compiere.model.MCostElement
 *  org.compiere.model.MPriceListVersion
 *  org.compiere.model.MProduct
 *  org.compiere.model.MProductPrice
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 */
package org.libero.process;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.engines.CostDimension;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCost;
import org.compiere.model.MCostElement;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

public class CopyPriceToStandard
extends SvrProcess {
    private int p_AD_Org_ID = 0;
    private int p_C_AcctSchema_ID = 0;
    private int p_M_CostType_ID = 0;
    private int p_M_CostElement_ID = 0;
    private int p_M_PriceList_Version_ID = 0;

    protected void prepare() {
        ProcessInfoParameter[] para = this.getParameter();
        for (int i = 0; i < para.length; ++i) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) continue;
            if (name.equals("M_CostType_ID")) {
                this.p_M_CostType_ID = ((BigDecimal)para[i].getParameter()).intValue();
                continue;
            }
            if (name.equals("AD_Org_ID")) {
                this.p_AD_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();
                continue;
            }
            if (name.equals("C_AcctSchema_ID")) {
                this.p_C_AcctSchema_ID = ((BigDecimal)para[i].getParameter()).intValue();
                continue;
            }
            if (name.equals("M_CostElement_ID")) {
                this.p_M_CostElement_ID = ((BigDecimal)para[i].getParameter()).intValue();
                continue;
            }
            if (name.equals("M_PriceList_Version_ID")) {
                this.p_M_PriceList_Version_ID = ((BigDecimal)para[i].getParameter()).intValue();
                continue;
            }
            this.log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
    }

    protected String doIt() throws Exception {
        MAcctSchema as = MAcctSchema.get((Properties)this.getCtx(), (int)this.p_C_AcctSchema_ID);
        MCostElement element = MCostElement.get((Properties)this.getCtx(), (int)this.p_M_CostElement_ID);
        if (!"M".equals(element.getCostElementType())) {
            throw new AdempiereException("Only Material Cost Elements are allowed");
        }
        int count_updated = 0;
        MPriceListVersion plv = new MPriceListVersion(this.getCtx(), this.p_M_PriceList_Version_ID, this.get_TrxName());
        block0: for (MProductPrice pprice : plv.getProductPrice(" AND PriceStd<>0")) {
            BigDecimal price = pprice.getPriceStd();
            int C_Currency_ID = plv.getPriceList().getC_Currency_ID();
            if (C_Currency_ID != as.getC_Currency_ID()) {
                price = MConversionRate.convert((Properties)this.getCtx(), (BigDecimal)pprice.getPriceStd(), (int)C_Currency_ID, (int)as.getC_Currency_ID(), (int)this.getAD_Client_ID(), (int)this.p_AD_Org_ID);
            }
            MProduct product = MProduct.get((Properties)this.getCtx(), (int)pprice.getM_Product_ID());
            CostDimension d = new CostDimension(product, as, this.p_M_CostType_ID, this.p_AD_Org_ID, 0, this.p_M_CostElement_ID);
            List costs = d.toQuery(MCost.class, this.get_TrxName()).list();
            for (MCost cost : costs) {
                if (cost.getM_CostElement_ID() != element.get_ID()) continue;
                cost.setFutureCostPrice(price);
                cost.saveEx();
                ++count_updated;
                continue block0;
            }
        }
        return "@Updated@ #" + count_updated;
    }
}

