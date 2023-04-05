/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MAcctSchema
 *  org.compiere.model.MClientInfo
 *  org.compiere.model.MCost
 *  org.compiere.model.MCostElement
 *  org.compiere.model.MCostQueue
 *  org.compiere.model.MProduct
 *  org.compiere.model.MProductionLine
 *  org.compiere.model.PO
 *  org.compiere.model.Query
 *  org.compiere.model.X_M_CostDetail
 *  org.compiere.model.X_M_CostHistory
 *  org.compiere.util.CLogger
 *  org.compiere.util.CPreparedStatement
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 */
package org.libero.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClientInfo;
import org.compiere.model.MCost;
import org.compiere.model.MCostElement;
import org.compiere.model.MCostQueue;
import org.compiere.model.MProduct;
import org.compiere.model.MProductionLine;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_M_CostDetail;
import org.compiere.model.X_M_CostHistory;
import org.compiere.util.CLogger;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MCostDetail
extends X_M_CostDetail {
    private static final long serialVersionUID = -3896161579785627935L;
    protected static final String INOUTLINE_DOCBASETYPE_SQL = "SELECT c.DocBaseType From M_InOut io INNER JOIN M_InOutLine iol ON io.M_InOut_ID=iol.M_InOut_ID INNER JOIN C_DocType c ON io.C_DocType_ID=c.C_DocType_ID WHERE iol.M_InOutLine_ID=?";
    private static CLogger s_log = CLogger.getCLogger(MCostDetail.class);

    public static boolean createOrder(MAcctSchema as, int AD_Org_ID, int M_Product_ID, int M_AttributeSetInstance_ID, int C_OrderLine_ID, int M_CostElement_ID, BigDecimal Amt, BigDecimal Qty, String Description, String trxName) {
        MCostDetail cd = MCostDetail.get(as.getCtx(), "C_OrderLine_ID=? AND Coalesce(M_CostElement_ID,0)=" + M_CostElement_ID, C_OrderLine_ID, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
        if (cd == null) {
            cd = new MCostDetail(as, AD_Org_ID, M_Product_ID, M_AttributeSetInstance_ID, M_CostElement_ID, Amt, Qty, Description, trxName);
            cd.setC_OrderLine_ID(C_OrderLine_ID);
        } else {
            if (cd.isProcessed()) {
                cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
                cd.setDeltaQty(Qty.subtract(cd.getQty()));
            } else {
                cd.setDeltaAmt(BigDecimal.ZERO);
                cd.setDeltaQty(BigDecimal.ZERO);
                cd.setAmt(Amt);
                cd.setQty(Qty);
            }
            if (cd.isDelta()) {
                cd.setProcessed(false);
                cd.setAmt(Amt);
                cd.setQty(Qty);
            } else if (cd.isProcessed()) {
                return true;
            }
        }
        boolean ok = cd.save();
        if (ok && !cd.isProcessed()) {
            ok = cd.process();
        }
        if (s_log.isLoggable(Level.CONFIG)) {
            s_log.config("(" + ok + ") " + (Object)((Object)cd));
        }
        return ok;
    }

    public static boolean createInvoice(MAcctSchema as, int AD_Org_ID, int M_Product_ID, int M_AttributeSetInstance_ID, int C_InvoiceLine_ID, int M_CostElement_ID, BigDecimal Amt, BigDecimal Qty, String Description, String trxName) {
        MCostDetail cd = MCostDetail.get(as.getCtx(), "C_InvoiceLine_ID=? AND Coalesce(M_CostElement_ID,0)=" + M_CostElement_ID + " AND M_Product_ID=" + M_Product_ID, C_InvoiceLine_ID, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
        if (cd == null) {
            cd = new MCostDetail(as, AD_Org_ID, M_Product_ID, M_AttributeSetInstance_ID, M_CostElement_ID, Amt, Qty, Description, trxName);
            cd.setC_InvoiceLine_ID(C_InvoiceLine_ID);
        } else {
            if (cd.isProcessed()) {
                cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
                cd.setDeltaQty(Qty.subtract(cd.getQty()));
            } else {
                cd.setDeltaAmt(BigDecimal.ZERO);
                cd.setDeltaQty(BigDecimal.ZERO);
                cd.setAmt(Amt);
                cd.setQty(Qty);
            }
            if (cd.isDelta()) {
                cd.setProcessed(false);
                cd.setAmt(Amt);
                cd.setQty(Qty);
            } else if (cd.isProcessed()) {
                return true;
            }
        }
        boolean ok = cd.save();
        if (ok && !cd.isProcessed()) {
            ok = cd.process();
        }
        if (s_log.isLoggable(Level.CONFIG)) {
            s_log.config("(" + ok + ") " + (Object)((Object)cd));
        }
        return ok;
    }

    public static boolean createShipment(MAcctSchema as, int AD_Org_ID, int M_Product_ID, int M_AttributeSetInstance_ID, int M_InOutLine_ID, int M_CostElement_ID, BigDecimal Amt, BigDecimal Qty, String Description, boolean IsSOTrx, String trxName) {
        MCostDetail cd = MCostDetail.get(as.getCtx(), "M_InOutLine_ID=? AND Coalesce(M_CostElement_ID,0)=" + M_CostElement_ID, M_InOutLine_ID, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
        if (cd == null) {
            cd = new MCostDetail(as, AD_Org_ID, M_Product_ID, M_AttributeSetInstance_ID, M_CostElement_ID, Amt, Qty, Description, trxName);
            cd.setM_InOutLine_ID(M_InOutLine_ID);
            cd.setIsSOTrx(IsSOTrx);
        } else {
            if (cd.isProcessed()) {
                cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
                cd.setDeltaQty(Qty.subtract(cd.getQty()));
            } else {
                cd.setDeltaAmt(BigDecimal.ZERO);
                cd.setDeltaQty(BigDecimal.ZERO);
                cd.setAmt(Amt);
                cd.setQty(Qty);
            }
            if (cd.isDelta()) {
                cd.setProcessed(false);
                cd.setAmt(Amt);
                cd.setQty(Qty);
            } else if (cd.isProcessed()) {
                return true;
            }
        }
        boolean ok = cd.save();
        if (ok && !cd.isProcessed()) {
            ok = cd.process();
        }
        if (s_log.isLoggable(Level.CONFIG)) {
            s_log.config("(" + ok + ") " + (Object)((Object)cd));
        }
        return ok;
    }

    public static boolean createInventory(MAcctSchema as, int AD_Org_ID, int M_Product_ID, int M_AttributeSetInstance_ID, int M_InventoryLine_ID, int M_CostElement_ID, BigDecimal Amt, BigDecimal Qty, String Description, String trxName) {
        MCostDetail cd = MCostDetail.get(as.getCtx(), "M_InventoryLine_ID=? AND Coalesce(M_CostElement_ID,0)=" + M_CostElement_ID, M_InventoryLine_ID, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
        if (cd == null) {
            cd = new MCostDetail(as, AD_Org_ID, M_Product_ID, M_AttributeSetInstance_ID, M_CostElement_ID, Amt, Qty, Description, trxName);
            cd.setM_InventoryLine_ID(M_InventoryLine_ID);
        } else {
            if (cd.isProcessed()) {
                cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
                cd.setDeltaQty(Qty.subtract(cd.getQty()));
            } else {
                cd.setDeltaAmt(BigDecimal.ZERO);
                cd.setDeltaQty(BigDecimal.ZERO);
                cd.setAmt(Amt);
                cd.setQty(Qty);
            }
            if (cd.isDelta()) {
                cd.setProcessed(false);
                cd.setAmt(Amt);
                cd.setQty(Qty);
            } else if (cd.isProcessed()) {
                return true;
            }
        }
        boolean ok = cd.save();
        if (ok && !cd.isProcessed()) {
            ok = cd.process();
        }
        if (s_log.isLoggable(Level.CONFIG)) {
            s_log.config("(" + ok + ") " + (Object)((Object)cd));
        }
        return ok;
    }

    public static boolean createMovement(MAcctSchema as, int AD_Org_ID, int M_Product_ID, int M_AttributeSetInstance_ID, int M_MovementLine_ID, int M_CostElement_ID, BigDecimal Amt, BigDecimal Qty, boolean from, String Description, String trxName) {
        StringBuilder msget = new StringBuilder("M_MovementLine_ID=? AND IsSOTrx=").append(from ? "'Y'" : "'N'").append(" AND Coalesce(M_CostElement_ID,0)=").append(M_CostElement_ID);
        MCostDetail cd = MCostDetail.get(as.getCtx(), msget.toString(), M_MovementLine_ID, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
        if (cd == null) {
            cd = new MCostDetail(as, AD_Org_ID, M_Product_ID, M_AttributeSetInstance_ID, M_CostElement_ID, Amt, Qty, Description, trxName);
            cd.setM_MovementLine_ID(M_MovementLine_ID);
            cd.setIsSOTrx(from);
        } else {
            if (cd.isProcessed()) {
                cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
                cd.setDeltaQty(Qty.subtract(cd.getQty()));
            } else {
                cd.setDeltaAmt(BigDecimal.ZERO);
                cd.setDeltaQty(BigDecimal.ZERO);
                cd.setAmt(Amt);
                cd.setQty(Qty);
            }
            if (cd.isDelta()) {
                cd.setProcessed(false);
                cd.setAmt(Amt);
                cd.setQty(Qty);
            } else if (cd.isProcessed()) {
                return true;
            }
        }
        boolean ok = cd.save();
        if (ok && !cd.isProcessed()) {
            ok = cd.process();
        }
        if (s_log.isLoggable(Level.CONFIG)) {
            s_log.config("(" + ok + ") " + (Object)((Object)cd));
        }
        return ok;
    }

    public static boolean createProduction(MAcctSchema as, int AD_Org_ID, int M_Product_ID, int M_AttributeSetInstance_ID, int M_ProductionLine_ID, int M_CostElement_ID, BigDecimal Amt, BigDecimal Qty, String Description, String trxName) {
        MCostDetail cd = MCostDetail.get(as.getCtx(), "M_ProductionLine_ID=? AND Coalesce(M_CostElement_ID,0)=" + M_CostElement_ID, M_ProductionLine_ID, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
        if (cd == null) {
            cd = new MCostDetail(as, AD_Org_ID, M_Product_ID, M_AttributeSetInstance_ID, M_CostElement_ID, Amt, Qty, Description, trxName);
            cd.setM_ProductionLine_ID(M_ProductionLine_ID);
        } else {
            if (cd.isProcessed()) {
                cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
                cd.setDeltaQty(Qty.subtract(cd.getQty()));
            } else {
                cd.setDeltaAmt(BigDecimal.ZERO);
                cd.setDeltaQty(BigDecimal.ZERO);
                cd.setAmt(Amt);
                cd.setQty(Qty);
            }
            if (cd.isDelta()) {
                cd.setProcessed(false);
                cd.setAmt(Amt);
                cd.setQty(Qty);
            } else if (cd.isProcessed()) {
                return true;
            }
        }
        boolean ok = cd.save();
        if (ok && !cd.isProcessed()) {
            ok = cd.process();
        }
        if (s_log.isLoggable(Level.CONFIG)) {
            s_log.config("(" + ok + ") " + (Object)((Object)cd));
        }
        return ok;
    }

    public static boolean createMatchInvoice(MAcctSchema as, int AD_Org_ID, int M_Product_ID, int M_AttributeSetInstance_ID, int M_MatchInv_ID, int M_CostElement_ID, BigDecimal Amt, BigDecimal Qty, String Description, String trxName) {
        MCostDetail cd = MCostDetail.get(as.getCtx(), "M_MatchInv_ID=? AND Coalesce(M_CostElement_ID,0)=" + M_CostElement_ID, M_MatchInv_ID, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
        if (cd == null) {
            cd = new MCostDetail(as, AD_Org_ID, M_Product_ID, M_AttributeSetInstance_ID, M_CostElement_ID, Amt, Qty, Description, trxName);
            cd.setM_MatchInv_ID(M_MatchInv_ID);
        } else {
            if (cd.isProcessed()) {
                cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
                cd.setDeltaQty(Qty.subtract(cd.getQty()));
            } else {
                cd.setDeltaAmt(BigDecimal.ZERO);
                cd.setDeltaQty(BigDecimal.ZERO);
                cd.setAmt(Amt);
                cd.setQty(Qty);
            }
            if (cd.isDelta()) {
                cd.setProcessed(false);
                cd.setAmt(Amt);
                cd.setQty(Qty);
            } else if (cd.isProcessed()) {
                return true;
            }
        }
        boolean ok = cd.save();
        if (ok && !cd.isProcessed()) {
            ok = cd.process();
        }
        if (s_log.isLoggable(Level.CONFIG)) {
            s_log.config("(" + ok + ") " + (Object)((Object)cd));
        }
        return ok;
    }

    public static boolean createProjectIssue(MAcctSchema as, int AD_Org_ID, int M_Product_ID, int M_AttributeSetInstance_ID, int C_ProjectIssue_ID, int M_CostElement_ID, BigDecimal Amt, BigDecimal Qty, String Description, String trxName) {
        MCostDetail cd = MCostDetail.get(as.getCtx(), "C_ProjectIssue_ID=? AND Coalesce(M_CostElement_ID,0)=" + M_CostElement_ID, C_ProjectIssue_ID, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
        if (cd == null) {
            cd = new MCostDetail(as, AD_Org_ID, M_Product_ID, M_AttributeSetInstance_ID, M_CostElement_ID, Amt, Qty, Description, trxName);
            cd.setC_ProjectIssue_ID(C_ProjectIssue_ID);
        } else {
            if (cd.isProcessed()) {
                cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
                cd.setDeltaQty(Qty.subtract(cd.getQty()));
            } else {
                cd.setDeltaAmt(BigDecimal.ZERO);
                cd.setDeltaQty(BigDecimal.ZERO);
                cd.setAmt(Amt);
                cd.setQty(Qty);
            }
            if (cd.isDelta()) {
                cd.setProcessed(false);
                cd.setAmt(Amt);
                cd.setQty(Qty);
            } else if (cd.isProcessed()) {
                return true;
            }
        }
        boolean ok = cd.save();
        if (ok && !cd.isProcessed()) {
            ok = cd.process();
        }
        if (s_log.isLoggable(Level.CONFIG)) {
            s_log.config("(" + ok + ") " + (Object)((Object)cd));
        }
        return ok;
    }

    public static MCostDetail get(Properties ctx, String whereClause, int ID, int M_AttributeSetInstance_ID, String trxName) {
        MCostDetail retValue;
        block8: {
            int C_AcctSchema_ID;
            StringBuilder sql = new StringBuilder("SELECT * FROM M_CostDetail WHERE ").append(whereClause);
            MClientInfo clientInfo = MClientInfo.get((Properties)ctx);
            MAcctSchema primary = clientInfo.getMAcctSchema1();
            int n = C_AcctSchema_ID = primary != null ? primary.getC_AcctSchema_ID() : 0;
            if (C_AcctSchema_ID > 0) {
                sql.append(" AND C_AcctSchema_ID=?");
            }
            retValue = null;
            CPreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                try {
                    pstmt = DB.prepareStatement((String)sql.toString(), null);
                    pstmt.setInt(1, ID);
                    pstmt.setInt(2, M_AttributeSetInstance_ID);
                    if (C_AcctSchema_ID > 0) {
                        pstmt.setInt(3, C_AcctSchema_ID);
                    }
                    if ((rs = pstmt.executeQuery()).next()) {
                        retValue = new MCostDetail(ctx, rs, trxName);
                    }
                }
                catch (Exception e) {
                    s_log.log(Level.SEVERE, sql + " - " + ID, (Throwable)e);
                    DB.close((ResultSet)rs, (Statement)pstmt);
                    break block8;
                }
            }
            catch (Throwable throwable) {
                DB.close(rs, pstmt);
                throw throwable;
            }
            DB.close((ResultSet)rs, (Statement)pstmt);
        }
        return retValue;
    }

    public static MCostDetail get(Properties ctx, String whereClause, int ID, int M_AttributeSetInstance_ID, int C_AcctSchema_ID, String trxName) {
        StringBuilder localWhereClause = new StringBuilder(whereClause).append(" AND M_AttributeSetInstance_ID=?").append(" AND C_AcctSchema_ID=?");
        MCostDetail retValue = (MCostDetail)new Query(ctx, "M_CostDetail", localWhereClause.toString(), trxName).setParameters(new Object[]{ID, M_AttributeSetInstance_ID, C_AcctSchema_ID}).first();
        return retValue;
    }

    public static boolean processProduct(MProduct product, String trxName) {
        int counterOK = 0;
        int counterError = 0;
        List list = new Query(product.getCtx(), "M_CostDetail", "M_Product_ID=? AND Processed=?", trxName).setParameters(new Object[]{product.getM_Product_ID(), false}).setOrderBy("C_AcctSchema_ID, M_CostElement_ID, AD_Org_ID, M_AttributeSetInstance_ID, Created").list();
        for (MCostDetail cd : list) {
            if (cd.process()) {
                ++counterOK;
                continue;
            }
            ++counterError;
        }
        if (s_log.isLoggable(Level.CONFIG)) {
            s_log.config("OK=" + counterOK + ", Errors=" + counterError);
        }
        return counterError == 0;
    }

    public MCostDetail(Properties ctx, int M_CostDetail_ID, String trxName) {
        super(ctx, M_CostDetail_ID, trxName);
        if (M_CostDetail_ID == 0) {
            this.setM_AttributeSetInstance_ID(0);
            this.setProcessed(false);
            this.setAmt(Env.ZERO);
            this.setQty(Env.ZERO);
            this.setIsSOTrx(false);
            this.setDeltaAmt(Env.ZERO);
            this.setDeltaQty(Env.ZERO);
        }
    }

    public MCostDetail(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    public MCostDetail(MAcctSchema as, int AD_Org_ID, int M_Product_ID, int M_AttributeSetInstance_ID, int M_CostElement_ID, BigDecimal Amt, BigDecimal Qty, String Description, String trxName) {
        this(as.getCtx(), 0, trxName);
        this.setClientOrg(as.getAD_Client_ID(), AD_Org_ID);
        this.setC_AcctSchema_ID(as.getC_AcctSchema_ID());
        this.setM_Product_ID(M_Product_ID);
        this.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
        this.setM_CostElement_ID(M_CostElement_ID);
        this.setAmt(Amt);
        this.setQty(Qty);
        this.setDescription(Description);
    }

    public void setAmt(BigDecimal Amt) {
        if (this.isProcessed()) {
            throw new IllegalStateException("Cannot change Amt - processed");
        }
        if (Amt == null) {
            super.setAmt(Env.ZERO);
        } else {
            super.setAmt(Amt);
        }
    }

    public void setQty(BigDecimal Qty) {
        if (this.isProcessed()) {
            throw new IllegalStateException("Cannot change Qty - processed");
        }
        if (Qty == null) {
            super.setQty(Env.ZERO);
        } else {
            super.setQty(Qty);
        }
    }

    public boolean isOrder() {
        return this.getC_OrderLine_ID() != 0;
    }

    public boolean isInvoice() {
        return this.getC_InvoiceLine_ID() != 0;
    }

    public boolean isShipment() {
        return this.isSOTrx() && this.getM_InOutLine_ID() != 0;
    }

    public boolean isVendorRMA() {
        if (!this.isSOTrx() && this.getM_InOutLine_ID() > 0) {
            String docBaseType = DB.getSQLValueString(null, (String)INOUTLINE_DOCBASETYPE_SQL, (int)this.getM_InOutLine_ID());
            return "MMS".equals(docBaseType);
        }
        return false;
    }

    public boolean isDelta() {
        return this.getDeltaAmt().signum() != 0 || this.getDeltaQty().signum() != 0;
    }

    protected boolean beforeDelete() {
        return !this.isProcessed();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("MCostDetail[");
        sb.append(this.get_ID());
        if (this.getC_OrderLine_ID() != 0) {
            sb.append(",C_OrderLine_ID=").append(this.getC_OrderLine_ID());
        }
        if (this.getM_InOutLine_ID() != 0) {
            sb.append(",M_InOutLine_ID=").append(this.getM_InOutLine_ID());
        }
        if (this.getC_InvoiceLine_ID() != 0) {
            sb.append(",C_InvoiceLine_ID=").append(this.getC_InvoiceLine_ID());
        }
        if (this.getC_ProjectIssue_ID() != 0) {
            sb.append(",C_ProjectIssue_ID=").append(this.getC_ProjectIssue_ID());
        }
        if (this.getM_MovementLine_ID() != 0) {
            sb.append(",M_MovementLine_ID=").append(this.getM_MovementLine_ID());
        }
        if (this.getM_InventoryLine_ID() != 0) {
            sb.append(",M_InventoryLine_ID=").append(this.getM_InventoryLine_ID());
        }
        if (this.getM_ProductionLine_ID() != 0) {
            sb.append(",M_ProductionLine_ID=").append(this.getM_ProductionLine_ID());
        }
        sb.append(",Amt=").append(this.getAmt()).append(",Qty=").append(this.getQty());
        if (this.isDelta()) {
            sb.append(",DeltaAmt=").append(this.getDeltaAmt()).append(",DeltaQty=").append(this.getDeltaQty());
        }
        sb.append("]");
        return sb.toString();
    }

    public synchronized boolean process() {
        if (this.isProcessed()) {
            this.log.info("Already processed");
            return true;
        }
        boolean ok = false;
        MAcctSchema as = MAcctSchema.get((Properties)this.getCtx(), (int)this.getC_AcctSchema_ID());
        MProduct product = new MProduct(this.getCtx(), this.getM_Product_ID(), this.get_TrxName());
        String CostingLevel = product.getCostingLevel(as);
        int Org_ID = this.getAD_Org_ID();
        int M_ASI_ID = this.getM_AttributeSetInstance_ID();
        if ("C".equals(CostingLevel)) {
            Org_ID = 0;
            M_ASI_ID = 0;
        } else if ("O".equals(CostingLevel)) {
            M_ASI_ID = 0;
        } else if ("B".equals(CostingLevel)) {
            Org_ID = 0;
        }
        if (this.getM_CostElement_ID() == 0) {
            MCostElement[] ces = MCostElement.getCostingMethods((PO)this);
            for (int i = 0; i < ces.length; ++i) {
                MCostElement ce = ces[i];
                if ((ce.isAverageInvoice() || ce.isAveragePO() || ce.isLifo() || ce.isFifo()) && !product.isStocked() || (ok = this.process(as, product, ce, Org_ID, M_ASI_ID))) {
                    continue;
                }
                break;
            }
        } else {
            MCostElement ce = MCostElement.get((Properties)this.getCtx(), (int)this.getM_CostElement_ID());
            if (ce.getCostingMethod() == null) {
                MCostElement[] ces;
                MCostElement[] arrmCostElement = ces = MCostElement.getCostingMethods((PO)this);
                int n = ces.length;
                for (int i = 0; i < n; ++i) {
                    MCostElement costingElement = arrmCostElement[i];
                    if ((costingElement.isAverageInvoice() || costingElement.isAveragePO() || costingElement.isLifo() || costingElement.isFifo()) && !product.isStocked() || (ok = this.process(as, product, costingElement, Org_ID, M_ASI_ID))) {
                        continue;
                    }
                    break;
                }
            } else if (ce.isAverageInvoice() || ce.isAveragePO() || ce.isLifo() || ce.isFifo()) {
                if (product.isStocked()) {
                    ok = this.process(as, product, ce, Org_ID, M_ASI_ID);
                }
            } else {
                ok = this.process(as, product, ce, Org_ID, M_ASI_ID);
            }
        }
        if (ok) {
            this.setDeltaAmt(null);
            this.setDeltaQty(null);
            this.setProcessed(true);
            ok = this.save();
        }
        if (this.log.isLoggable(Level.INFO)) {
            this.log.info(String.valueOf(ok) + " - " + this.toString());
        }
        return ok;
    }

    protected boolean process(MAcctSchema as, MProduct product, MCostElement ce, int Org_ID, int M_ASI_ID) {
        MCostElement thisCostElement;
        String costingMethod = product.getCostingMethod(as);
        if ("I".equals(costingMethod) ? ce.isAveragePO() : "A".equals(costingMethod) && ce.isAverageInvoice()) {
            return true;
        }
        MCost cost = MCost.get((MProduct)product, (int)M_ASI_ID, (MAcctSchema)as, (int)Org_ID, (int)ce.getM_CostElement_ID(), (String)this.get_TrxName());
        DB.getDatabase().forUpdate((PO)cost, 120);
        X_M_CostHistory history = new X_M_CostHistory(this.getCtx(), 0, this.get_TrxName());
        history.setM_AttributeSetInstance_ID(cost.getM_AttributeSetInstance_ID());
        history.setM_CostDetail_ID(this.getM_CostDetail_ID());
        history.setM_CostElement_ID(ce.getM_CostElement_ID());
        history.setM_CostType_ID(cost.getM_CostType_ID());
        history.setAD_Org_ID(cost.getAD_Org_ID());
        history.setOldQty(cost.getCurrentQty());
        history.setOldCostPrice(cost.getCurrentCostPrice());
        history.setOldCAmt(cost.getCumulatedAmt());
        history.setOldCQty(cost.getCumulatedQty());
        BigDecimal qty = Env.ZERO;
        BigDecimal amt = Env.ZERO;
        if (this.isDelta()) {
            qty = this.getDeltaQty();
            amt = this.getDeltaAmt();
        } else {
            qty = this.getQty();
            amt = this.getAmt();
        }
        boolean costAdjustment = false;
        if (this.getM_CostElement_ID() > 0 && this.getM_CostElement_ID() != ce.getM_CostElement_ID() && (thisCostElement = MCostElement.get((Properties)this.getCtx(), (int)this.getM_CostElement_ID())).getCostingMethod() == null && ce.getCostingMethod() != null) {
            qty = BigDecimal.ZERO;
            costAdjustment = true;
        }
        int precision = as.getCostingPrecision();
        BigDecimal price = amt;
        if (qty.signum() != 0) {
            price = amt.divide(qty, precision, RoundingMode.HALF_UP);
        }
        if (this.getC_OrderLine_ID() != 0) {
            boolean isReturnTrx;
            boolean bl = isReturnTrx = qty.signum() < 0;
            if (ce.isAveragePO()) {
                cost.setWeightedAverage(amt, qty);
                if (this.log.isLoggable(Level.FINER)) {
                    this.log.finer("PO - AveragePO - " + (Object)cost);
                }
            } else if (ce.isLastPOPrice() && !costAdjustment) {
                if (!isReturnTrx) {
                    if (qty.signum() != 0) {
                        cost.setCurrentCostPrice(price);
                    } else {
                        BigDecimal cCosts = cost.getCurrentCostPrice().add(amt);
                        cost.setCurrentCostPrice(cCosts);
                    }
                }
                cost.add(amt, qty);
                if (this.log.isLoggable(Level.FINER)) {
                    this.log.finer("PO - LastPO - " + (Object)cost);
                }
            } else if (ce.isStandardCosting() && !costAdjustment) {
                if (cost.getCurrentCostPrice().signum() == 0 && cost.getCurrentCostPriceLL().signum() == 0) {
                    cost.setCurrentCostPrice(price);
                    if (cost.getCurrentCostPrice().signum() == 0) {
                        cost.setCurrentCostPrice(MCost.getSeedCosts((MProduct)product, (int)M_ASI_ID, (MAcctSchema)as, (int)Org_ID, (String)ce.getCostingMethod(), (int)this.getC_OrderLine_ID()));
                    }
                    if (this.log.isLoggable(Level.FINEST)) {
                        this.log.finest("PO - Standard - CurrentCostPrice(seed)=" + cost.getCurrentCostPrice() + ", price=" + price);
                    }
                }
                cost.add(amt, qty);
                if (this.log.isLoggable(Level.FINER)) {
                    this.log.finer("PO - Standard - " + (Object)cost);
                }
            } else if (ce.isUserDefined()) {
                if (this.log.isLoggable(Level.FINER)) {
                    this.log.finer("PO - UserDef - " + (Object)cost);
                }
            } else if (!ce.isCostingMethod() && this.log.isLoggable(Level.FINER)) {
                this.log.finer("PO - " + (Object)ce + " - " + (Object)cost);
            }
        } else if (this.getC_InvoiceLine_ID() != 0) {
            boolean isReturnTrx;
            boolean bl = isReturnTrx = qty.signum() < 0;
            if (ce.isAverageInvoice()) {
                cost.setWeightedAverage(amt, qty);
                if (this.log.isLoggable(Level.FINER)) {
                    this.log.finer("Inv - AverageInv - " + (Object)cost);
                }
            } else if (ce.isAveragePO() && costAdjustment) {
                cost.setWeightedAverage(amt, qty);
            } else if (ce.isFifo() || ce.isLifo()) {
                MCostQueue cq = MCostQueue.get((MProduct)product, (int)this.getM_AttributeSetInstance_ID(), (MAcctSchema)as, (int)Org_ID, (int)ce.getM_CostElement_ID(), (String)this.get_TrxName());
                cq.setCosts(amt, qty, precision);
                cq.saveEx();
                MCostQueue[] cQueue = MCostQueue.getQueue((MProduct)product, (int)M_ASI_ID, (MAcctSchema)as, (int)Org_ID, (MCostElement)ce, (String)this.get_TrxName());
                if (cQueue != null && cQueue.length > 0) {
                    cost.setCurrentCostPrice(cQueue[0].getCurrentCostPrice());
                }
                cost.add(amt, qty);
                if (this.log.isLoggable(Level.FINER)) {
                    this.log.finer("Inv - FiFo/LiFo - " + (Object)cost);
                }
            } else if (ce.isLastInvoice() && !costAdjustment) {
                if (!isReturnTrx) {
                    if (qty.signum() != 0) {
                        cost.setCurrentCostPrice(price);
                    } else {
                        BigDecimal cCosts = cost.getCurrentCostPrice().add(amt);
                        cost.setCurrentCostPrice(cCosts);
                    }
                }
                cost.add(amt, qty);
                if (this.log.isLoggable(Level.FINER)) {
                    this.log.finer("Inv - LastInv - " + (Object)cost);
                }
            } else if (ce.isStandardCosting() && !costAdjustment) {
                if (cost.getCurrentCostPrice().signum() == 0 && cost.getCurrentCostPriceLL().signum() == 0) {
                    cost.setCurrentCostPrice(price);
                    if (cost.getCurrentCostPrice().signum() == 0) {
                        cost.setCurrentCostPrice(MCost.getSeedCosts((MProduct)product, (int)M_ASI_ID, (MAcctSchema)as, (int)Org_ID, (String)ce.getCostingMethod(), (int)this.getC_OrderLine_ID()));
                        if (this.log.isLoggable(Level.FINEST)) {
                            this.log.finest("Inv - Standard - CurrentCostPrice(seed)=" + cost.getCurrentCostPrice() + ", price=" + price);
                        }
                    }
                    cost.add(amt, qty);
                }
                if (this.log.isLoggable(Level.FINER)) {
                    this.log.finer("Inv - Standard - " + (Object)cost);
                }
            } else if (ce.isUserDefined()) {
                cost.add(amt, qty);
                if (this.log.isLoggable(Level.FINER)) {
                    this.log.finer("Inv - UserDef - " + (Object)cost);
                }
            }
        } else if (this.getM_InOutLine_ID() != 0 && costAdjustment) {
            if (ce.isAverageInvoice()) {
                cost.setWeightedAverage(amt, qty);
            }
        } else if (this.getM_InOutLine_ID() != 0 || this.getM_MovementLine_ID() != 0 || this.getM_InventoryLine_ID() != 0 || this.getM_ProductionLine_ID() != 0 || this.getC_ProjectIssue_ID() != 0 || this.getPP_Cost_Collector_ID() != 0) {
            boolean addition = qty.signum() > 0;
            boolean adjustment = this.getM_InventoryLine_ID() > 0 && qty.signum() == 0 && amt.signum() != 0;
            boolean isVendorRMA = this.isVendorRMA();
            if (ce.isAverageInvoice()) {
                if (!isVendorRMA) {
                    if (adjustment) {
                        costingMethod = this.getM_InventoryLine().getM_Inventory().getCostingMethod();
                        if ("I".equals(costingMethod)) {
                            if (cost.getCurrentQty().signum() == 0 && qty.signum() == 0) {
                                cost.setWeightedAverageInitial(amt);
                            } else {
                                cost.setWeightedAverage(amt.multiply(cost.getCurrentQty()), qty);
                            }
                        }
                    } else if (addition) {
                        cost.setWeightedAverage(amt, qty);
                        if (this.isShipment()) {
                            cost.setCumulatedQty(history.getOldCQty());
                            cost.setCumulatedAmt(history.getOldCAmt());
                        }
                    } else {
                        cost.setCurrentQty(cost.getCurrentQty().add(qty));
                    }
                    if (this.log.isLoggable(Level.FINER)) {
                        this.log.finer("QtyAdjust - AverageInv - " + (Object)cost);
                    }
                }
            } else if (ce.isAveragePO()) {
                if (adjustment) {
                    costingMethod = this.getM_InventoryLine().getM_Inventory().getCostingMethod();
                    if ("A".equals(costingMethod)) {
                        if (cost.getCurrentQty().signum() == 0 && qty.signum() == 0) {
                            cost.setWeightedAverageInitial(amt);
                        } else {
                            cost.setWeightedAverage(amt.multiply(cost.getCurrentQty()), qty);
                        }
                    }
                } else if (addition) {
                    cost.setWeightedAverage(amt, qty);
                    if (this.isShipment() && !this.isVendorRMA()) {
                        cost.setCumulatedQty(history.getOldCQty());
                        cost.setCumulatedAmt(history.getOldCAmt());
                    }
                } else if (isVendorRMA) {
                    cost.setWeightedAverage(amt, qty);
                } else {
                    cost.setCurrentQty(cost.getCurrentQty().add(qty));
                }
                if (this.log.isLoggable(Level.FINER)) {
                    this.log.finer("QtyAdjust - AveragePO - " + (Object)cost);
                }
            } else if (ce.isFifo() || ce.isLifo()) {
                if (!isVendorRMA && !adjustment) {
                    if (addition) {
                        MCostQueue cq = MCostQueue.get((MProduct)product, (int)this.getM_AttributeSetInstance_ID(), (MAcctSchema)as, (int)Org_ID, (int)ce.getM_CostElement_ID(), (String)this.get_TrxName());
                        cq.setCosts(amt, qty, precision);
                        cq.saveEx();
                    } else {
                        MCostQueue.adjustQty((MProduct)product, (int)M_ASI_ID, (MAcctSchema)as, (int)Org_ID, (MCostElement)ce, (BigDecimal)qty.negate(), (String)this.get_TrxName());
                    }
                    MCostQueue[] cQueue = MCostQueue.getQueue((MProduct)product, (int)M_ASI_ID, (MAcctSchema)as, (int)Org_ID, (MCostElement)ce, (String)this.get_TrxName());
                    if (cQueue != null && cQueue.length > 0) {
                        cost.setCurrentCostPrice(cQueue[0].getCurrentCostPrice());
                    }
                    cost.setCurrentQty(cost.getCurrentQty().add(qty));
                    if (this.log.isLoggable(Level.FINER)) {
                        this.log.finer("QtyAdjust - FiFo/Lifo - " + (Object)cost);
                    }
                }
            } else if (ce.isLastInvoice() && !isVendorRMA && !adjustment) {
                cost.setCurrentQty(cost.getCurrentQty().add(qty));
                if (this.log.isLoggable(Level.FINER)) {
                    this.log.finer("QtyAdjust - LastInv - " + (Object)cost);
                }
            } else if (ce.isLastPOPrice() && !isVendorRMA && !adjustment) {
                cost.setCurrentQty(cost.getCurrentQty().add(qty));
                if (this.log.isLoggable(Level.FINER)) {
                    this.log.finer("QtyAdjust - LastPO - " + (Object)cost);
                }
            } else if (ce.isStandardCosting() && !isVendorRMA) {
                if (adjustment) {
                    costingMethod = this.getM_InventoryLine().getM_Inventory().getCostingMethod();
                    if ("S".equals(costingMethod)) {
                        cost.add(amt.multiply(cost.getCurrentQty()), qty);
                        cost.setCurrentCostPrice(cost.getCurrentCostPrice().add(amt));
                    }
                } else if (addition) {
                    MProductionLine productionLine;
                    MProductionLine mProductionLine = productionLine = this.getM_ProductionLine_ID() > 0 ? new MProductionLine(this.getCtx(), this.getM_ProductionLine_ID(), this.get_TrxName()) : null;
                    if (productionLine != null && productionLine.getProductionReversalId() > 0) {
                        cost.setCurrentQty(cost.getCurrentQty().add(qty));
                    } else {
                        cost.add(amt, qty);
                    }
                    if (cost.getCurrentCostPrice().signum() == 0 && cost.getCurrentCostPriceLL().signum() == 0 && cost.is_new()) {
                        cost.setCurrentCostPrice(price);
                        if (this.log.isLoggable(Level.FINEST)) {
                            this.log.finest("QtyAdjust - Standard - CurrentCostPrice=" + price);
                        }
                    }
                } else {
                    cost.setCurrentQty(cost.getCurrentQty().add(qty));
                }
                if (this.log.isLoggable(Level.FINER)) {
                    this.log.finer("QtyAdjust - Standard - " + (Object)cost);
                }
            } else if (ce.isUserDefined() && !isVendorRMA && !adjustment) {
                if (addition) {
                    cost.add(amt, qty);
                } else {
                    cost.setCurrentQty(cost.getCurrentQty().add(qty));
                }
                if (this.log.isLoggable(Level.FINER)) {
                    this.log.finer("QtyAdjust - UserDef - " + (Object)cost);
                }
            } else if (!ce.isCostingMethod()) {
                if (this.log.isLoggable(Level.FINER)) {
                    this.log.finer("QtyAdjust - ?none? - " + (Object)cost);
                }
            } else if (ce.isStandardCosting() && isVendorRMA) {
                cost.add(amt, qty);
            } else {
                this.log.warning("QtyAdjust - " + (Object)ce + " - " + (Object)cost);
            }
        } else if (this.getM_MatchInv_ID() > 0) {
            if (ce.isAveragePO()) {
                cost.setWeightedAverage(amt, qty);
            }
        } else {
            this.log.warning("Unknown Type: " + this.toString());
            return false;
        }
        if (as.getCostingMethod().equals(ce.getCostingMethod())) {
            this.setCurrentCostPrice(cost.getCurrentCostPrice());
            this.setCurrentQty(cost.getCurrentQty());
            this.setCumulatedAmt(cost.getCumulatedAmt());
            this.setCumulatedQty(cost.getCumulatedQty());
        }
        history.setNewQty(cost.getCurrentQty());
        history.setNewCostPrice(cost.getCurrentCostPrice());
        history.setNewCAmt(cost.getCumulatedAmt());
        history.setNewCQty(cost.getCumulatedQty());
        if (!(history.getNewQty().compareTo(history.getOldQty()) == 0 && history.getNewCostPrice().compareTo(history.getOldCostPrice()) == 0 || history.save())) {
            return false;
        }
        return cost.save();
    }
}

