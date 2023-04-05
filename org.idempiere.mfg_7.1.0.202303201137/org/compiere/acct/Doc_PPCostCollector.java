/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.acct.Doc
 *  org.compiere.acct.DocLine
 *  org.compiere.acct.Fact
 *  org.compiere.acct.FactLine
 *  org.compiere.model.MAccount
 *  org.compiere.model.MAcctSchema
 *  org.compiere.model.MCostDetail
 *  org.compiere.model.MCostElement
 *  org.compiere.model.MDocType
 *  org.compiere.model.MProduct
 *  org.compiere.model.Query
 *  org.compiere.util.Env
 */
package org.compiere.acct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.acct.DocLine_CostCollector;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCostDetail;
import org.compiere.model.MCostElement;
import org.compiere.model.MDocType;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.libero.model.MPPCostCollector;
import org.libero.model.RoutingService;
import org.libero.model.RoutingServiceFactory;

public class Doc_PPCostCollector
extends Doc {
    protected DocLine_CostCollector m_line = null;
    protected MPPCostCollector m_cc = null;
    protected RoutingService m_routingService = null;
    private List<MCostDetail> m_costDetails = null;

    public Doc_PPCostCollector(MAcctSchema ass, ResultSet rs, String trxName) {
        super(ass, MPPCostCollector.class, rs, "MCC", trxName);
    }

    protected String loadDocumentDetails() {
        this.setC_Currency_ID(-2);
        this.m_cc = (MPPCostCollector)this.getPO();
        this.setDateDoc(this.m_cc.getMovementDate());
        this.setDateAcct(this.m_cc.getMovementDate());
        this.m_line = new DocLine_CostCollector(this.m_cc, this);
        this.m_line.setQty(this.m_cc.getMovementQty(), false);
        if (this.m_line.getM_Product_ID() == 0) {
            this.log.warning(String.valueOf(this.m_line.toString()) + " - No Product");
        }
        this.log.fine(this.m_line.toString());
        this.m_routingService = RoutingServiceFactory.get().getRoutingService(this.m_cc.getAD_Client_ID());
        return null;
    }

    public BigDecimal getBalance() {
        BigDecimal retValue = Env.ZERO;
        return retValue;
    }

    public ArrayList<Fact> createFacts(MAcctSchema as) {
        this.setC_Currency_ID(as.getC_Currency_ID());
        ArrayList<Fact> facts = new ArrayList<Fact>();
        if ("100".equals(this.m_cc.getCostCollectorType())) {
            facts.add(this.createMaterialReceipt(as));
        } else if ("110".equals(this.m_cc.getCostCollectorType())) {
            facts.add(this.createComponentIssue(as));
        } else if ("130".equals(this.m_cc.getCostCollectorType())) {
            facts.add(this.createVariance(as, 12));
        } else if ("120".equals(this.m_cc.getCostCollectorType())) {
            facts.add(this.createVariance(as, 13));
        } else if ("120".equals(this.m_cc.getCostCollectorType())) {
            facts.add(this.createVariance(as, 13));
        } else if ("140".equals(this.m_cc.getCostCollectorType())) {
            facts.add(this.createVariance(as, 14));
        } else if ("150".equals(this.m_cc.getCostCollectorType())) {
            facts.add(this.createVariance(as, 15));
        } else if ("160".equals(this.m_cc.getCostCollectorType())) {
            facts.addAll(this.createActivityControl(as));
        }
        return facts;
    }

    protected void createLines(MCostElement element, MAcctSchema as, Fact fact, MProduct product, MAccount debit, MAccount credit, BigDecimal cost, BigDecimal qty) {
        if (cost == null || debit == null || credit == null) {
            return;
        }
        this.log.info("CostElement: " + (Object)element + "Product: " + product.getName() + " Debit: " + debit.getDescription() + " Credit: " + credit.getDescription() + " Cost: " + cost + " Qty: " + qty);
        FactLine dr = null;
        FactLine cr = null;
        if (cost.signum() != 0) {
            dr = fact.createLine((DocLine)this.m_line, debit, as.getC_Currency_ID(), cost, null);
            dr.setQty(qty);
            String desc = element.getName();
            dr.addDescription(desc);
            dr.setC_Project_ID(this.m_cc.getC_Project_ID());
            dr.setC_Activity_ID(this.m_cc.getC_Activity_ID());
            dr.setC_Campaign_ID(this.m_cc.getC_Campaign_ID());
            dr.setM_Locator_ID(this.m_cc.getM_Locator_ID());
            cr = fact.createLine((DocLine)this.m_line, credit, as.getC_Currency_ID(), null, cost);
            cr.setQty(qty);
            cr.addDescription(desc);
            cr.setC_Project_ID(this.m_cc.getC_Project_ID());
            cr.setC_Activity_ID(this.m_cc.getC_Activity_ID());
            cr.setC_Campaign_ID(this.m_cc.getC_Campaign_ID());
            cr.setM_Locator_ID(this.m_cc.getM_Locator_ID());
        }
    }

    protected Fact createMaterialReceipt(MAcctSchema as) {
        Fact fact = new Fact((Doc)this, as, "A");
        MProduct product = this.m_cc.getM_Product();
        MAccount credit = this.m_line.getAccount(11, as);
        for (MCostDetail cd : this.getCostDetails()) {
            BigDecimal cost;
            MAccount debit;
            MCostElement element = MCostElement.get((Properties)this.getCtx(), (int)cd.getM_CostElement_ID());
            if (this.m_cc.getMovementQty().signum() != 0) {
                debit = this.m_line.getAccount(3, as);
                cost = cd.getAmt();
                if (cost.scale() > as.getStdPrecision()) {
                    cost = cost.setScale(as.getStdPrecision(), RoundingMode.HALF_UP);
                }
                this.createLines(element, as, fact, product, debit, credit, cost, this.m_cc.getMovementQty());
            }
            if (this.m_cc.getScrappedQty().signum() == 0) continue;
            debit = this.m_line.getAccount(22, as);
            cost = cd.getPrice().multiply(this.m_cc.getScrappedQty());
            if (cost.scale() > as.getStdPrecision()) {
                cost = cost.setScale(as.getStdPrecision(), RoundingMode.HALF_UP);
            }
            this.createLines(element, as, fact, product, debit, credit, cost, this.m_cc.getScrappedQty());
        }
        return fact;
    }

    protected Fact createComponentIssue(MAcctSchema as) {
        BigDecimal cost;
        MCostElement element;
        Fact fact = new Fact((Doc)this, as, "A");
        MProduct product = this.m_cc.getM_Product();
        MAccount debit = null;
        String docBaseType = MDocType.get((Properties)this.getCtx(), (int)this.m_cc.getPP_Order().getC_DocType_ID()).getDocBaseType();
        MAccount credit = this.m_line.getAccount(3, as);
        if (this.m_cc.isFloorStock()) {
            credit = this.m_line.getAccount(16, as);
        }
        if (this.m_cc.getScrappedQty().signum() != 0) {
            debit = this.m_line.getAccount(22, as);
            for (MCostDetail cd : this.getCostDetails()) {
                System.out.println("dsds" + cd.getAmt() + cd.getM_Product().getName() + this.m_cc.getPP_Cost_Collector_ID());
                element = MCostElement.get((Properties)this.getCtx(), (int)cd.getM_CostElement_ID());
                cost = cd.getPrice().multiply(this.m_cc.getScrappedQty());
                if (cost.scale() > as.getStdPrecision()) {
                    cost = cost.setScale(as.getStdPrecision(), RoundingMode.HALF_UP);
                }
                this.createLines(element, as, fact, product, debit, credit, cost, this.m_cc.getScrappedQty());
            }
        }
        for (MCostDetail cd : this.getCostDetails()) {
            debit = "MOF".equals(docBaseType) ? this.m_line.getAccount(12, as) : this.m_line.getAccount(11, as);
            System.out.println("dsds" + cd.getAmt() + cd.getM_Product().getName() + this.m_cc.getPP_Cost_Collector_ID());
            element = MCostElement.get((Properties)this.getCtx(), (int)cd.getM_CostElement_ID());
            cost = cd.getAmt().negate();
            if (cost.scale() > as.getStdPrecision()) {
                cost = cost.setScale(as.getStdPrecision(), RoundingMode.HALF_UP);
            }
            this.createLines(element, as, fact, product, debit, credit, cost, this.m_cc.getMovementQty());
        }
        return fact;
    }

    protected List<Fact> createActivityControl(MAcctSchema as) {
        ArrayList<Fact> facts = new ArrayList<Fact>();
        Fact fact = new Fact((Doc)this, as, "A");
        facts.add(fact);
        MProduct product = this.m_cc.getM_Product();
        MAccount debit = this.m_line.getAccount(11, as);
        for (MCostDetail cd : this.getCostDetails()) {
            BigDecimal costs = cd.getAmt().negate();
            if (costs.signum() == 0) continue;
            MCostElement element = MCostElement.get((Properties)this.getCtx(), (int)cd.getM_CostElement_ID());
            MAccount credit = this.m_line.getAccount(as, element);
            this.createLines(element, as, fact, product, debit, credit, costs, this.m_cc.getMovementQty());
        }
        return facts;
    }

    protected Fact createVariance(MAcctSchema as, int VarianceAcctType) {
        Fact fact = new Fact((Doc)this, as, "A");
        MProduct product = this.m_cc.getM_Product();
        MAccount debit = this.m_line.getAccount(VarianceAcctType, as);
        MAccount credit = this.m_line.getAccount(11, as);
        for (MCostDetail cd : this.getCostDetails()) {
            MCostElement element = MCostElement.get((Properties)this.getCtx(), (int)cd.getM_CostElement_ID());
            BigDecimal costs = cd.getAmt().negate();
            if (costs.scale() > as.getStdPrecision()) {
                costs = costs.setScale(as.getStdPrecision(), RoundingMode.HALF_UP);
            }
            BigDecimal qty = cd.getQty();
            this.createLines(element, as, fact, product, debit, credit, costs, qty);
        }
        return fact;
    }

    public Collection<MCostElement> getCostElements() {
        List elements = MCostElement.getByCostingMethod((Properties)this.getCtx(), (String)"S");
        return elements;
    }

    protected static final MProduct getProductForResource(Properties ctx, int S_Resource_ID, String trxName) {
        int M_Product_ID = new Query(ctx, "M_Product", "S_Resource_ID=?", trxName).setParameters(new Object[]{S_Resource_ID}).firstIdOnly();
        return MProduct.get((Properties)ctx, (int)M_Product_ID);
    }

    private List<MCostDetail> getCostDetails() {
        if (this.m_costDetails == null) {
            String whereClause = "PP_Cost_Collector_ID=?";
            this.m_costDetails = new Query(this.getCtx(), "M_CostDetail", whereClause, this.getTrxName()).setParameters(new Object[]{this.m_cc.getPP_Cost_Collector_ID()}).setOrderBy("M_CostDetail_ID").list();
        }
        return this.m_costDetails;
    }
}

