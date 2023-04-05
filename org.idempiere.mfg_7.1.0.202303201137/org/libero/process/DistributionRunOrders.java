/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MDistributionRun
 *  org.compiere.model.MDistributionRunLine
 *  org.compiere.model.MDocType
 *  org.compiere.model.MPInstance
 *  org.compiere.model.MPInstancePara
 *  org.compiere.model.MPriceList
 *  org.compiere.model.MPriceListVersion
 *  org.compiere.model.MProcess
 *  org.compiere.model.MProduct
 *  org.compiere.model.MProductPrice
 *  org.compiere.model.MStorageOnHand
 *  org.compiere.model.MWarehouse
 *  org.compiere.process.ProcessInfo
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.CLogger
 *  org.compiere.util.CPreparedStatement
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.compiere.util.Msg
 *  org.compiere.util.Trx
 *  org.eevolution.model.MPPProductBOM
 *  org.eevolution.model.MPPProductBOMLine
 */
package org.libero.process;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MDistributionRun;
import org.compiere.model.MDistributionRunLine;
import org.compiere.model.MDocType;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProcess;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MWarehouse;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.libero.model.MPPMRP;

public class DistributionRunOrders
extends SvrProcess {
    private int p_M_DistributionList_ID = 0;
    private Timestamp p_DatePromised = null;
    private int p_AD_Org_ID = 0;
    private String p_IsTest = "N";
    private int p_M_Warehouse_ID = 0;
    private String p_ConsolidateDocument = "N";
    private String p_BasedInDamnd = "N";
    private MDistributionRun m_run = null;

    protected void prepare() {
        ProcessInfoParameter[] para = this.getParameter();
        for (int i = 0; i < para.length; ++i) {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null) continue;
            if (name.equals("AD_Org_ID")) {
                this.p_AD_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();
                continue;
            }
            if (name.equals("M_Warehouse_ID")) {
                this.p_M_Warehouse_ID = ((BigDecimal)para[i].getParameter()).intValue();
                continue;
            }
            if (name.equals("M_DistributionList_ID")) {
                this.p_M_DistributionList_ID = ((BigDecimal)para[i].getParameter()).intValue();
                continue;
            }
            if (name.equals("DatePromised")) {
                this.p_DatePromised = (Timestamp)para[i].getParameter();
                continue;
            }
            if (name.equals("ConsolidateDocument")) {
                this.p_ConsolidateDocument = (String)para[i].getParameter();
                continue;
            }
            if (name.equals("IsRequiredDRP")) {
                this.p_BasedInDamnd = (String)para[i].getParameter();
                continue;
            }
            if (name.equals("IsTest")) {
                this.p_IsTest = (String)para[i].getParameter();
                continue;
            }
            this.log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
    }

    protected String doIt() throws Exception {
        if (this.p_BasedInDamnd.equals("Y") ? !this.generateDistributionDemand() : !this.generateDistribution()) {
            throw new Exception(Msg.getMsg((Properties)this.getCtx(), (String)"ProcessFailed"), CLogger.retrieveException());
        }
        if (!this.executeDistribution()) {
            throw new Exception(Msg.getMsg((Properties)this.getCtx(), (String)"ProcessFailed"), CLogger.retrieveException());
        }
        return Msg.getMsg((Properties)this.getCtx(), (String)"ProcessOK");
    }

    public boolean generateDistribution() {
        this.m_run = new MDistributionRun(this.getCtx(), 0, this.get_TrxName());
        this.m_run.setName("Generate from DRP " + this.p_DatePromised);
        this.m_run.save();
        StringBuffer sql = new StringBuffer("SELECT M_Product_ID , SUM (QtyOrdered-QtyDelivered) AS TotalQty, l.M_Warehouse_ID FROM DD_OrderLine ol INNER JOIN M_Locator l ON (l.M_Locator_ID=ol.M_Locator_ID) INNER JOIN DD_Order o ON (o.DD_Order_ID=ol.DD_Order_ID) ");
        sql.append(" WHERE o.DocStatus IN ('DR','IN') AND ol.DatePromised <= ? AND l.M_Warehouse_ID=? GROUP BY M_Product_ID");
        CPreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            try {
                pstmt = DB.prepareStatement((String)sql.toString(), (String)this.get_TrxName());
                pstmt.setTimestamp(1, this.p_DatePromised);
                pstmt.setInt(2, this.p_M_Warehouse_ID);
                rs = pstmt.executeQuery();
                int line = 10;
                while (rs.next()) {
                    int M_Product_ID = rs.getInt("M_Product_ID");
                    BigDecimal QtyAvailable = MStorageOnHand.getQtyOnHand((int)M_Product_ID, (int)this.p_M_Warehouse_ID, (int)0, (String)this.get_TrxName());
                    BigDecimal QtyOrdered = rs.getBigDecimal("TotalQty");
                    MDistributionRunLine m_runLine = new MDistributionRunLine(this.getCtx(), 0, this.get_TrxName());
                    m_runLine.setM_DistributionRun_ID(this.m_run.getM_DistributionRun_ID());
                    m_runLine.setAD_Org_ID(this.p_AD_Org_ID);
                    m_runLine.setM_DistributionList_ID(this.p_M_DistributionList_ID);
                    m_runLine.setLine(line);
                    m_runLine.setM_Product_ID(M_Product_ID);
                    m_runLine.setDescription(String.valueOf(Msg.getMsg((Properties)this.getCtx(), (String)"QtyAvailable")) + " : " + QtyAvailable + " " + Msg.getMsg((Properties)this.getCtx(), (String)"QtyOrdered") + " : " + QtyOrdered);
                    if (QtyOrdered.compareTo(QtyAvailable) > 0) {
                        QtyOrdered = QtyAvailable;
                    }
                    m_runLine.setTotalQty(QtyOrdered);
                    m_runLine.save();
                    line += 10;
                }
            }
            catch (Exception e) {
                this.log.log(Level.SEVERE, "doIt - " + sql, (Throwable)e);
                DB.close(rs, (Statement)pstmt);
                rs = null;
                pstmt = null;
                return false;
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
        return true;
    }

    public boolean generateDistributionDemand() {
        this.m_run = new MDistributionRun(this.getCtx(), 0, null);
        this.m_run.setName("Generate from DRP " + this.p_DatePromised);
        this.m_run.save();
        StringBuffer sql = new StringBuffer("SELECT M_Product_ID , SUM (TargetQty) AS MinQty, SUM (QtyOrdered-QtyDelivered) AS TotalQty FROM DD_OrderLine ol INNER JOIN M_Locator l ON (l.M_Locator_ID=ol.M_Locator_ID) INNER JOIN DD_Order o ON (o.DD_Order_ID=ol.DD_Order_ID) ");
        sql.append(" WHERE o.DocStatus IN ('DR','IN') AND ol.DatePromised <= ? AND l.M_Warehouse_ID=? GROUP BY M_Product_ID");
        CPreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            try {
                pstmt = DB.prepareStatement((String)sql.toString(), (String)this.get_TrxName());
                pstmt.setTimestamp(1, this.p_DatePromised);
                pstmt.setInt(2, this.p_M_Warehouse_ID);
                rs = pstmt.executeQuery();
                int line = 10;
                while (rs.next()) {
                    int M_Product_ID = rs.getInt("M_Product_ID");
                    BigDecimal QtyAvailable = MStorageOnHand.getQtyOnHand((int)M_Product_ID, (int)this.p_M_Warehouse_ID, (int)0, (String)this.get_TrxName());
                    if (QtyAvailable.signum() <= 0) continue;
                    BigDecimal QtyToDistribute = rs.getBigDecimal("TotalQty");
                    if (QtyAvailable.compareTo(QtyToDistribute) >= 0) {
                        QtyAvailable = QtyToDistribute;
                    } else {
                        BigDecimal QtyReserved = this.getTargetQty(M_Product_ID);
                        QtyToDistribute = QtyAvailable.subtract(QtyReserved);
                    }
                    MDistributionRunLine m_runLine = new MDistributionRunLine(this.getCtx(), 0, this.get_TrxName());
                    m_runLine.setM_DistributionRun_ID(this.m_run.getM_DistributionRun_ID());
                    m_runLine.setAD_Org_ID(this.p_AD_Org_ID);
                    m_runLine.setM_DistributionList_ID(this.p_M_DistributionList_ID);
                    m_runLine.setLine(line);
                    m_runLine.setM_Product_ID(M_Product_ID);
                    m_runLine.setDescription(String.valueOf(Msg.translate((Properties)this.getCtx(), (String)"QtyAvailable")) + " : " + QtyAvailable + " " + Msg.translate((Properties)this.getCtx(), (String)"QtyOrdered") + " : " + QtyToDistribute);
                    m_runLine.setTotalQty(QtyToDistribute);
                    m_runLine.saveEx();
                    line += 10;
                }
            }
            catch (Exception e) {
                this.log.log(Level.SEVERE, "doIt - " + sql, (Throwable)e);
                DB.close(rs, (Statement)pstmt);
                rs = null;
                pstmt = null;
                return false;
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
        return true;
    }

    private BigDecimal getTargetQty(int M_Product_ID) {
        StringBuffer sql = new StringBuffer("SELECT SUM (TargetQty)  FROM DD_OrderLine ol INNER JOIN M_Locator l ON (l.M_Locator_ID=ol.M_Locator_ID) INNER JOIN DD_Order o ON (o.DD_Order_ID=ol.DD_Order_ID) ");
        sql.append(" WHERE o.DocStatus IN ('DR','IN') AND ol.DatePromised <= ? AND l.M_Warehouse_ID=? AND ol.M_Product_ID=? GROUP BY M_Product_ID");
        BigDecimal qty = DB.getSQLValueBD((String)this.get_TrxName(), (String)sql.toString(), (Object[])new Object[]{this.p_DatePromised, this.p_M_Warehouse_ID, M_Product_ID});
        if (qty == null) {
            return Env.ZERO;
        }
        return qty;
    }

    public boolean executeDistribution() throws Exception {
        int M_DocType_ID = 0;
        MDocType[] doc = MDocType.getOfDocBaseType((Properties)this.getCtx(), (String)"DOO");
        if (doc == null || doc.length == 0) {
            this.log.severe("Not found default document type for docbasetype DOO");
            throw new Exception(Msg.getMsg((Properties)this.getCtx(), (String)"SequenceDocNotFound"), CLogger.retrieveException());
        }
        M_DocType_ID = doc[0].getC_DocType_ID();
        String trxName = Trx.createTrxName((String)"Run Distribution to DRP");
        Trx.get((String)trxName, (boolean)true);
        int AD_Process_ID = 271;
        AD_Process_ID = MProcess.getProcess_ID((String)"M_DistributionRun Create", (String)this.get_TrxName());
        MPInstance instance = new MPInstance(Env.getCtx(), AD_Process_ID, 0);
        if (!instance.save()) {
            throw new Exception(Msg.getMsg((Properties)this.getCtx(), (String)"ProcessNoInstance"), CLogger.retrieveException());
        }
        ProcessInfo pi = new ProcessInfo("M_DistributionRun Orders", AD_Process_ID);
        pi.setAD_PInstance_ID(instance.getAD_PInstance_ID());
        pi.setRecord_ID(this.m_run.getM_DistributionRun_ID());
        MPInstancePara ip = new MPInstancePara(instance, 10);
        ip.setParameter("C_DocType_ID", M_DocType_ID);
        if (!ip.save()) {
            String msg = "No Parameter added";
            throw new Exception(msg, CLogger.retrieveException());
        }
        ip = new MPInstancePara(instance, 20);
        ip.setParameter("DatePromised", "");
        ip.setP_Date(this.p_DatePromised);
        if (!ip.save()) {
            String msg = "No Parameter added";
            throw new Exception(msg, CLogger.retrieveException());
        }
        ip = new MPInstancePara(instance, 30);
        ip.setParameter("M_Warehouse_ID", this.p_M_Warehouse_ID);
        if (!ip.save()) {
            String msg = "No Parameter added";
            throw new Exception(msg, CLogger.retrieveException());
        }
        ip = new MPInstancePara(instance, 40);
        ip.setParameter("ConsolidateDocument", this.p_ConsolidateDocument);
        if (!ip.save()) {
            String msg = "No Parameter added";
            throw new Exception(msg, CLogger.retrieveException());
        }
        ip = new MPInstancePara(instance, 50);
        ip.setParameter("IsTest", this.p_IsTest);
        if (!ip.save()) {
            String msg = "No Parameter added";
            throw new Exception(msg, CLogger.retrieveException());
        }
        ip = new MPInstancePara(instance, 60);
        ip.setParameter("M_DistributionList_ID", this.p_M_DistributionList_ID);
        if (!ip.save()) {
            String msg = "No Parameter added";
            throw new Exception(msg, CLogger.retrieveException());
        }
        ip = new MPInstancePara(instance, 70);
        ip.setParameter("IsRequiredDRP", this.p_BasedInDamnd);
        if (!ip.save()) {
            String msg = "No Parameter added";
            throw new Exception(msg, CLogger.retrieveException());
        }
        MProcess worker = new MProcess(this.getCtx(), AD_Process_ID, this.get_TrxName());
        worker.processIt(pi, Trx.get((String)this.get_TrxName(), (boolean)true));
        this.m_run.delete(true);
        return true;
    }

    public String groovy(String A_TrxName, Properties A_Ctx, int P_M_Warehouse_ID, int P_M_PriceList_Version_ID, int P_M_DistributionList_ID) {
        MPriceListVersion plv = new MPriceListVersion(A_Ctx, P_M_PriceList_Version_ID, A_TrxName);
        new MPriceList(A_Ctx, plv.getM_PriceList_ID(), A_TrxName);
        MWarehouse w = new MWarehouse(A_Ctx, P_M_Warehouse_ID, A_TrxName);
        MDistributionRun dr = new MDistributionRun(A_Ctx, 0, A_TrxName);
        dr.setName(plv.getName());
        dr.setIsActive(true);
        dr.setAD_Org_ID(w.getAD_Org_ID());
        dr.saveEx();
        MProductPrice[] products = plv.getProductPrice(true);
        int seq = 10;
        MProductPrice[] arrmProductPrice = products;
        int n = products.length;
        for (int i = 0; i < n; ++i) {
            MProductPrice pp = arrmProductPrice[i];
            int M_Product_ID = pp.getM_Product_ID();
            BigDecimal QtyAvailable = MStorageOnHand.getQtyOnHand((int)M_Product_ID, (int)this.p_M_Warehouse_ID, (int)0, (String)this.get_TrxName());
            BigDecimal QtyOnHand = MPPMRP.getQtyOnHand(A_Ctx, P_M_Warehouse_ID, M_Product_ID, A_TrxName);
            MDistributionRunLine drl = new MDistributionRunLine(A_Ctx, 0, A_TrxName);
            drl.setM_DistributionRun_ID(dr.get_ID());
            drl.setLine(seq);
            drl.setM_Product_ID(M_Product_ID);
            drl.setM_DistributionList_ID(P_M_DistributionList_ID);
            drl.setDescription(String.valueOf(Msg.translate((Properties)A_Ctx, (String)"QtyAvailable")) + " = " + QtyAvailable + " | " + Msg.translate((Properties)A_Ctx, (String)"QtyOnHand") + " = " + QtyOnHand);
            drl.setTotalQty(QtyAvailable);
            drl.saveEx();
        }
        return "";
    }

    public String groovy1(String A_TrxName, Properties A_Ctx, int P_M_Warehouse_ID, int P_M_PriceList_Version_ID, int P_M_DistributionList_ID) {
        MDistributionRunLine main = new MDistributionRunLine(A_Ctx, 0, A_TrxName);
        MProduct product = MProduct.get((Properties)A_Ctx, (int)main.getM_Product_ID());
        BigDecimal Qty = main.getTotalQty();
        int seq = main.getLine();
        int num = 1;
        if (product.isBOM() && Qty.signum() > 0) {
            ++seq;
            MPPProductBOM bom = MPPProductBOM.getDefault((MProduct)product, (String)A_TrxName);
            for (MPPProductBOMLine line : bom.getLines()) {
                ++num;
                int M_Product_ID = line.getM_Product_ID();
                BigDecimal QtyRequired = line.getQtyBOM().multiply(Qty);
                BigDecimal QtyAvailable = MStorageOnHand.getQtyOnHand((int)M_Product_ID, (int)this.p_M_Warehouse_ID, (int)0, (String)this.get_TrxName());
                BigDecimal QtyOnHand = MPPMRP.getQtyOnHand(A_Ctx, P_M_Warehouse_ID, M_Product_ID, A_TrxName);
                BigDecimal QtyToDeliver = QtyRequired;
                if (QtyRequired.compareTo(QtyAvailable) > 0) {
                    QtyToDeliver = QtyAvailable;
                }
                MDistributionRunLine drl = new MDistributionRunLine(A_Ctx, 0, A_TrxName);
                drl.setM_DistributionRun_ID(main.getM_DistributionRun_ID());
                drl.setLine(seq);
                drl.setM_Product_ID(M_Product_ID);
                drl.setM_DistributionList_ID(main.getM_DistributionList_ID());
                drl.setDescription(String.valueOf(Msg.translate((Properties)A_Ctx, (String)"QtyRequired")) + " = " + QtyRequired.intValue() + " | " + Msg.translate((Properties)A_Ctx, (String)"QtyAvailable") + " = " + QtyAvailable + " | " + Msg.translate((Properties)A_Ctx, (String)"QtyOnHand") + " = " + QtyOnHand);
                drl.setTotalQty(QtyToDeliver);
                drl.saveEx();
            }
        }
        main.setIsActive(false);
        return "Componentes del Juego:" + num;
    }
}

