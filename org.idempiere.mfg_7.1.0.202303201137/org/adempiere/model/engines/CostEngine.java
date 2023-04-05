/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.compiere.model.I_AD_WF_Node
 *  org.compiere.model.I_M_CostElement
 *  org.compiere.model.MAcctSchema
 *  org.compiere.model.MCost
 *  org.compiere.model.MCostDetail
 *  org.compiere.model.MCostElement
 *  org.compiere.model.MProduct
 *  org.compiere.model.MTransaction
 *  org.compiere.model.PO
 *  org.compiere.model.Query
 *  org.compiere.util.CLogger
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.compiere.wf.MWorkflow
 */
package org.adempiere.model.engines;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.engines.CostDimension;
import org.adempiere.model.engines.IDocumentLine;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MCostDetail;
import org.compiere.model.MCostElement;
import org.compiere.model.MProduct;
import org.compiere.model.MTransaction;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.wf.MWorkflow;
import org.libero.model.MPPCostCollector;
import org.libero.model.MPPOrderBOMLine;
import org.libero.model.MPPOrderCost;
import org.libero.model.RoutingService;
import org.libero.model.RoutingServiceFactory;

public class CostEngine {
    protected transient CLogger log = CLogger.getCLogger(this.getClass());
    private CostDimension d = null;

    public String getCostingMethod() {
        return "S";
    }

    public String getCostingMethodFifo() {
        return "F";
    }

    public BigDecimal getResourceStandardCostRate(MPPCostCollector cc, int S_Resource_ID, CostDimension d, String trxName) {
        MProduct resourceProduct = MProduct.forS_Resource_ID((Properties)Env.getCtx(), (int)S_Resource_ID, null);
        return this.getProductStandardCostPrice(cc, resourceProduct, MAcctSchema.get((Properties)Env.getCtx(), (int)d.getC_AcctSchema_ID()), MCostElement.get((Properties)Env.getCtx(), (int)d.getM_CostElement_ID()));
    }

    public BigDecimal getResourceActualCostRate(MPPCostCollector cc, int S_Resource_ID, CostDimension d, String trxName) {
        if (S_Resource_ID <= 0) {
            return Env.ZERO;
        }
        MProduct resourceProduct = MProduct.forS_Resource_ID((Properties)Env.getCtx(), (int)S_Resource_ID, null);
        System.out.println("dadad" + resourceProduct.getM_Product_ID());
        return this.getProductActualCostPrice(cc, resourceProduct, MAcctSchema.get((Properties)Env.getCtx(), (int)d.getC_AcctSchema_ID()), MCostElement.get((Properties)Env.getCtx(), (int)d.getM_CostElement_ID()), trxName);
    }

    public BigDecimal getProductActualCostPrice(MPPCostCollector cc, MProduct product, MAcctSchema as, MCostElement element, String trxName) {
        int adorg = 0;
        if (cc == null) {
            System.out.println(product.getM_Product_ID());
            adorg = product.getAD_Org_ID();
            System.out.println("organisasi" + adorg + product.getAD_Org_ID());
        } else {
            adorg = cc.getAD_Org_ID();
        }
        this.d = element.getCostingMethod().equals("F") ? new CostDimension(product, as, as.getM_CostType_ID(), adorg, cc.getM_AttributeSetInstance_ID(), element.getM_CostElement_ID()) : new CostDimension(product, as, as.getM_CostType_ID(), adorg, product.getM_AttributeSetInstance_ID(), element.getM_CostElement_ID());
        System.out.println("afagag" + this.d + " " + element.getCostElementType());
        MCost cost = (MCost)this.d.toQuery(MCost.class, trxName).firstOnly();
        System.out.println("cost" + (Object)cost);
        if (cost == null) {
            return Env.ZERO;
        }
        BigDecimal price = cost.getCurrentCostPrice().add(cost.getCurrentCostPriceLL());
        System.out.println("dadadf" + price);
        return this.roundCost(price, as.getC_AcctSchema_ID());
    }

    public BigDecimal getProductStandardCostPrice(MPPCostCollector cc, MProduct product, MAcctSchema as, MCostElement element) {
        this.d = element.getCostingMethod().equals("F") ? new CostDimension(product, as, as.getM_CostType_ID(), cc.getAD_Org_ID(), DB.getSQLValue(null, (String)("select m_attributesetinstance_id from PP_Order_Cost where pp_order_id = " + cc.getPP_Order_ID() + " and m_product_id = " + product.getM_Product_ID())), 1000000) : new CostDimension(product, as, as.getM_CostType_ID(), cc.getAD_Org_ID(), product.getM_AttributeSetInstance_ID(), element.getM_CostElement_ID());
        MPPOrderCost oc = (MPPOrderCost)this.d.toQuery(MPPOrderCost.class, "PP_Order_ID=?", new Object[]{cc.getPP_Order_ID()}, cc.get_TrxName()).firstOnly();
        if (oc == null) {
            return Env.ZERO;
        }
        BigDecimal costs = oc.getCurrentCostPrice().add(oc.getCurrentCostPriceLL());
        return this.roundCost(costs, as.getC_AcctSchema_ID());
    }

    protected BigDecimal roundCost(BigDecimal price, int C_AcctSchema_ID) {
        int precision = MAcctSchema.get((Properties)Env.getCtx(), (int)C_AcctSchema_ID).getCostingPrecision();
        BigDecimal priceRounded = price;
        if (priceRounded.scale() > precision) {
            priceRounded = priceRounded.setScale(precision, RoundingMode.HALF_UP);
        }
        return priceRounded;
    }

    public Collection<MCost> getByElement(MProduct product, MAcctSchema as, int M_CostType_ID, int AD_Org_ID, int M_AttributeSetInstance_ID, int M_CostElement_ID) {
        CostDimension cd = new CostDimension(product, as, M_CostType_ID, AD_Org_ID, M_AttributeSetInstance_ID, M_CostElement_ID);
        return cd.toQuery(MCost.class, product.get_TrxName()).setOnlyActiveRecords(true).list();
    }

    private MCostDetail getCostDetail(IDocumentLine model, MTransaction mtrx, MAcctSchema as, int M_CostElement_ID) {
        String whereClause = "AD_Client_ID=? AND AD_Org_ID=? AND " + model.get_TableName() + "_ID=?" + " AND " + "M_Product_ID" + "=?" + " AND " + "M_AttributeSetInstance_ID" + "=?" + " AND " + "C_AcctSchema_ID" + "=?" + " AND " + "M_CostElement_ID" + "=?";
        Object[] params = new Object[]{mtrx.getAD_Client_ID(), mtrx.getAD_Org_ID(), model.get_ID(), mtrx.getM_Product_ID(), mtrx.getM_AttributeSetInstance_ID(), as.getC_AcctSchema_ID(), M_CostElement_ID};
        return (MCostDetail)new Query(mtrx.getCtx(), "M_CostDetail", whereClause, mtrx.get_TrxName()).setParameters(params).firstOnly();
    }

    public void createCostDetail(IDocumentLine model, MTransaction mtrx) {
        System.out.println("mtrx" + mtrx.getM_Product().getName() + " " + mtrx.getMovementQty());
        MPPCostCollector cc = model instanceof MPPCostCollector ? (MPPCostCollector)model : null;
        for (MAcctSchema as : this.getAcctSchema((PO)mtrx)) {
            MProduct product = MProduct.get((Properties)mtrx.getCtx(), (int)mtrx.getM_Product_ID());
            String costingMethod = product.getCostingMethod(as);
            for (MCostElement element : this.getCostElements(mtrx.getCtx(), costingMethod)) {
                this.deleteCostDetail(model, as, element.get_ID(), mtrx.getM_AttributeSetInstance_ID());
                BigDecimal qty = mtrx.getMovementQty();
                BigDecimal price = this.getProductActualCostPrice(cc, product, as, element, mtrx.get_TrxName());
                BigDecimal amt = this.roundCost(price.multiply(qty), as.getC_AcctSchema_ID());
                MCostDetail cd = this.getCostDetail(model, mtrx, as, element.get_ID());
                if (cd == null) {
                    cd = new MCostDetail(as, mtrx.getAD_Org_ID(), mtrx.getM_Product_ID(), mtrx.getM_AttributeSetInstance_ID(), element.get_ID(), amt, qty, model.getDescription(), mtrx.get_TrxName());
                    if (model instanceof MPPCostCollector) {
                        cd.setPP_Cost_Collector_ID(model.get_ID());
                    }
                } else {
                    cd.setDeltaAmt(amt.subtract(cd.getAmt()));
                    cd.setDeltaQty(mtrx.getMovementQty().subtract(cd.getQty()));
                    if (cd.isDelta()) {
                        cd.setProcessed(false);
                        cd.setAmt(amt);
                        cd.setQty(mtrx.getMovementQty());
                    }
                }
                cd.saveEx();
                this.processCostDetail(cd);
                this.log.config("" + (Object)cd);
            }
        }
    }

    private int deleteCostDetail(IDocumentLine model, MAcctSchema as, int M_CostElement_ID, int M_AttributeSetInstance_ID) {
        String sql = "DELETE FROM M_CostDetail WHERE Processed='N' AND COALESCE(DeltaAmt,0)=0 AND COALESCE(DeltaQty,0)=0 AND " + model.get_TableName() + "_ID=?" + " AND " + "C_AcctSchema_ID" + "=?" + " AND " + "M_AttributeSetInstance_ID" + "=?" + " AND " + "M_CostElement_ID" + "=?";
        System.out.println("sfsfs" + sql);
        Object[] parameters = new Object[]{model.get_ID(), as.getC_AcctSchema_ID(), M_AttributeSetInstance_ID, M_CostElement_ID};
        int no = DB.executeUpdateEx((String)sql, (Object[])parameters, (String)model.get_TrxName());
        if (no != 0) {
            this.log.config("Deleted #" + no);
        }
        return no;
    }

    private void processCostDetail(MCostDetail cd) {
        if (!cd.isProcessed()) {
            cd.process();
        }
    }

    public static boolean isActivityControlElement(I_M_CostElement element) {
        String costElementType = element.getCostElementType();
        return "R".equals(costElementType) || "O".equals(costElementType) || "B".equals(costElementType);
    }

    private Collection<MCostElement> getCostElements(Properties ctx, String costingelement) {
        if (costingelement.equals("F")) {
            return MCostElement.getByCostingMethod((Properties)ctx, (String)this.getCostingMethodFifo());
        }
        return MCostElement.getByCostingMethod((Properties)ctx, (String)this.getCostingMethod());
    }

    private Collection<MAcctSchema> getAcctSchema(PO po) {
        int AD_Org_ID = po.getAD_Org_ID();
        MAcctSchema[] ass = MAcctSchema.getClientAcctSchema((Properties)po.getCtx(), (int)po.getAD_Client_ID());
        ArrayList<MAcctSchema> list = new ArrayList<MAcctSchema>(ass.length);
        MAcctSchema[] arrmAcctSchema = ass;
        int n = ass.length;
        for (int i = 0; i < n; ++i) {
            MAcctSchema as = arrmAcctSchema[i];
            if (as.isSkipOrg(AD_Org_ID)) continue;
            list.add(as);
        }
        return list;
    }

    private MCostDetail getCostDetail(MPPCostCollector cc, int M_CostElement_ID) {
        MCostDetail cd = (MCostDetail)new Query(cc.getCtx(), "M_CostDetail", "PP_Cost_Collector_ID=? AND M_CostElement_ID=?", cc.get_TrxName()).setParameters(new Object[]{cc.getPP_Cost_Collector_ID(), M_CostElement_ID}).firstOnly();
        return cd;
    }

    private MPPCostCollector createVarianceCostCollector(MPPCostCollector cc, String CostCollectorType) {
        MPPCostCollector ccv = new MPPCostCollector(cc.getCtx(), 0, cc.get_TrxName());
        MPPCostCollector.copyValues((PO)cc, (PO)ccv);
        ccv.setProcessing(false);
        ccv.setProcessed(false);
        ccv.setDocStatus("DR");
        ccv.setDocAction("CO");
        ccv.setCostCollectorType(CostCollectorType);
        ccv.setDocumentNo(null);
        ccv.saveEx();
        return ccv;
    }

    private MCostDetail createVarianceCostDetail(MPPCostCollector ccv, BigDecimal amt, BigDecimal qty, MCostDetail cd, MProduct product, MAcctSchema as, MCostElement element) {
        MCostDetail cdv = new MCostDetail(ccv.getCtx(), 0, ccv.get_TrxName());
        if (cd != null) {
            MCostDetail.copyValues((PO)cd, (PO)cdv);
            cdv.setProcessed(false);
        }
        if (product != null) {
            cdv.setM_Product_ID(product.getM_Product_ID());
            cdv.setM_AttributeSetInstance_ID(0);
        }
        if (as != null) {
            cdv.setC_AcctSchema_ID(as.getC_AcctSchema_ID());
        }
        if (element != null) {
            cdv.setM_CostElement_ID(element.getM_CostElement_ID());
        }
        cdv.setPP_Cost_Collector_ID(ccv.getPP_Cost_Collector_ID());
        cdv.setAmt(amt);
        cdv.setQty(qty);
        cdv.saveEx();
        this.processCostDetail(cdv);
        return cdv;
    }

    public void createActivityControl(MPPCostCollector cc) {
        System.out.println("asfafagagag" + cc.isCostCollectorType("160"));
        System.out.println("gfsgs" + cc.getCostCollectorType());
        if (!cc.isCostCollectorType("160")) {
            return;
        }
        MProduct product = MProduct.forS_Resource_ID((Properties)cc.getCtx(), (int)cc.getS_Resource_ID(), null);
        RoutingService routingService = RoutingServiceFactory.get().getRoutingService(cc.getAD_Client_ID());
        BigDecimal qty = routingService.getResourceBaseValue(cc.getS_Resource_ID(), cc);
        String costingMethod = product.getCostingMethod(MAcctSchema.get((int)1000000));
        for (MAcctSchema as : this.getAcctSchema(cc)) {
            for (MCostElement element : this.getCostElements(cc.getCtx(), costingMethod)) {
                if (!CostEngine.isActivityControlElement((I_M_CostElement)element)) continue;
                CostDimension d = new CostDimension(product, as, as.getM_CostType_ID(), cc.getAD_Org_ID(), product.getM_AttributeSetInstance_ID(), element.getM_CostElement_ID());
                BigDecimal price = this.getResourceActualCostRate(cc, cc.getS_Resource_ID(), d, cc.get_TrxName());
                BigDecimal costs = price.multiply(qty);
                if (costs.scale() > as.getCostingPrecision()) {
                    costs = costs.setScale(as.getCostingPrecision(), RoundingMode.HALF_UP);
                }
                MCostDetail cd = new MCostDetail(as, cc.getAD_Org_ID(), d.getM_Product_ID(), product.getM_AttributeSetInstance_ID(), element.getM_CostElement_ID(), costs.negate().multiply(new BigDecimal(cc.getPP_Order_Node().get_Value("jmltenagakerja").toString())), qty.negate(), "", cc.get_TrxName());
                cd.setPP_Cost_Collector_ID(cc.getPP_Cost_Collector_ID());
                cd.saveEx();
                this.processCostDetail(cd);
            }
        }
    }

    public void createUsageVariances(MPPCostCollector ccuv, BigDecimal costvarian, BigDecimal qtyjmltenagakerja) {
        BigDecimal qty;
        MProduct product;
        if (!ccuv.isCostCollectorType("120")) {
            throw new IllegalArgumentException("Cost Collector is not Material Usage Variance");
        }
        if (ccuv.getPP_Order_BOMLine_ID() > 0) {
            product = MProduct.get((Properties)ccuv.getCtx(), (int)ccuv.getM_Product_ID());
            qty = ccuv.getMovementQty();
        } else {
            product = MProduct.forS_Resource_ID((Properties)ccuv.getCtx(), (int)ccuv.getS_Resource_ID(), null);
            RoutingServiceFactory.get().getRoutingService(ccuv.getAD_Client_ID());
            MWorkflow.get((Properties)ccuv.getCtx(), (int)ccuv.getPP_Order_Node().getAD_WF_Node().getAD_Workflow_ID());
            qty = new BigDecimal(ccuv.getPP_Order_Node().getAD_WF_Node().getDuration() - ccuv.getPP_Order_Node().getDuration()).multiply(ccuv.getMovementQty());
        }
        String costingMethod = product.getCostingMethod(MAcctSchema.get((int)1000000));
        for (MAcctSchema as : this.getAcctSchema(ccuv)) {
            for (MCostElement element : this.getCostElements(ccuv.getCtx(), costingMethod)) {
                BigDecimal price = this.getProductActualCostPrice(ccuv, product, as, element, ccuv.get_TrxName());
                BigDecimal amt = BigDecimal.ZERO;
                amt = costvarian.compareTo(BigDecimal.ZERO) > 0 ? this.roundCost(price.multiply(qtyjmltenagakerja).multiply(qty), as.getC_AcctSchema_ID()) : this.roundCost(price.multiply(qty), as.getC_AcctSchema_ID());
                System.out.println("price" + price + " " + ccuv.getPP_Order_BOMLine_ID() + " " + element.getM_CostElement_ID());
                if (costvarian.compareTo(BigDecimal.ZERO) > 0) {
                    if (price.compareTo(BigDecimal.ZERO) <= 0 || ccuv.getPP_Order_BOMLine_ID() != 0 || element.getM_CostElement_ID() == 1000000) continue;
                    this.createVarianceCostDetail(ccuv, amt, qty, null, product, as, element);
                    continue;
                }
                this.createVarianceCostDetail(ccuv, amt, qty, null, product, as, element);
            }
        }
    }

    public void createRateVariances(MPPCostCollector cc) {
        MProduct product;
        if (cc.isCostCollectorType("160")) {
            I_AD_WF_Node node = cc.getPP_Order_Node().getAD_WF_Node();
            product = MProduct.forS_Resource_ID((Properties)cc.getCtx(), (int)node.getS_Resource_ID(), null);
        } else if (cc.isCostCollectorType("110")) {
            MPPOrderBOMLine bomLine = cc.getPP_Order_BOMLine();
            product = MProduct.get((Properties)cc.getCtx(), (int)bomLine.getM_Product_ID());
        } else {
            return;
        }
        MPPCostCollector ccrv = null;
        for (MAcctSchema as : this.getAcctSchema(cc)) {
            String costingMethod = product.getCostingMethod(as);
            for (MCostElement element : this.getCostElements(cc.getCtx(), costingMethod)) {
                MCostDetail cd = this.getCostDetail(cc, element.getM_CostElement_ID());
                if (cd == null) continue;
                BigDecimal qty = cd.getQty();
                BigDecimal priceStd = this.getProductStandardCostPrice(cc, product, as, element);
                BigDecimal priceActual = this.getProductActualCostPrice(cc, product, as, element, cc.get_TrxName());
                BigDecimal amtStd = this.roundCost(priceStd.multiply(qty), as.getC_AcctSchema_ID());
                BigDecimal amtActual = this.roundCost(priceActual.multiply(qty), as.getC_AcctSchema_ID());
                System.out.println("ratevariance" + amtStd + " " + amtActual);
                if (amtStd.compareTo(amtActual) == 0) continue;
                if (ccrv == null) {
                    ccrv = this.createVarianceCostCollector(cc, "140");
                }
                this.createVarianceCostDetail(ccrv, amtStd.abs().subtract(amtActual.abs()), qty.negate(), cd, null, as, element);
            }
        }
        if (ccrv != null) {
            boolean ok = ccrv.processIt("CO");
            ccrv.saveEx();
            if (!ok) {
                throw new AdempiereException(ccrv.getProcessMsg());
            }
        }
    }

    public void createMethodVariances(MPPCostCollector cc) {
        int actual_resource_id;
        if (cc.isCostCollectorType("130")) {
            for (MAcctSchema as : this.getAcctSchema(cc)) {
                for (MCostElement element : this.getCostElements(cc.getCtx(), this.getCostingMethod())) {
                    MProduct product = cc.getM_Product();
                    BigDecimal qty = cc.getMovementQty();
                    BigDecimal priceStd = this.getProductActualCostPrice(cc, product, as, element, cc.get_TrxName());
                    BigDecimal amtStd = priceStd.multiply(qty);
                    this.createVarianceCostDetail(cc, amtStd, qty, null, product, as, element);
                }
            }
            return;
        }
        if (!cc.isCostCollectorType("160")) {
            return;
        }
        int std_resource_id = cc.getPP_Order_Node().getAD_WF_Node().getS_Resource_ID();
        if (std_resource_id == (actual_resource_id = cc.getS_Resource_ID())) {
            return;
        }
        MPPCostCollector ccmv = null;
        RoutingService routingService = RoutingServiceFactory.get().getRoutingService(cc.getAD_Client_ID());
        for (MAcctSchema as : this.getAcctSchema(cc)) {
            for (MCostElement element : this.getCostElements(cc.getCtx(), this.getCostingMethod())) {
                BigDecimal priceActual;
                MProduct resourcePStd = MProduct.forS_Resource_ID((Properties)cc.getCtx(), (int)std_resource_id, null);
                MProduct resourcePActual = MProduct.forS_Resource_ID((Properties)cc.getCtx(), (int)actual_resource_id, null);
                BigDecimal priceStd = this.getProductActualCostPrice(cc, resourcePStd, as, element, cc.get_TrxName());
                if (priceStd.compareTo(priceActual = this.getProductActualCostPrice(cc, resourcePActual, as, element, cc.get_TrxName())) == 0) continue;
                if (ccmv == null) {
                    ccmv = this.createVarianceCostCollector(cc, "130");
                }
                BigDecimal qty = routingService.getResourceBaseValue(cc.getS_Resource_ID(), cc);
                BigDecimal amtStd = priceStd.multiply(qty);
                BigDecimal amtActual = priceActual.multiply(qty);
                this.createVarianceCostDetail(ccmv, amtActual, qty, null, resourcePActual, as, element);
                this.createVarianceCostDetail(ccmv, amtStd.negate(), qty.negate(), null, resourcePStd, as, element);
            }
        }
        if (ccmv != null) {
            boolean ok = ccmv.processIt("CO");
            ccmv.saveEx();
            if (!ok) {
                throw new AdempiereException(ccmv.getProcessMsg());
            }
        }
    }
}

