/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.compiere.model.MBPartner
 *  org.compiere.model.MOrder
 *  org.compiere.model.MOrderLine
 *  org.compiere.model.MProduct
 *  org.compiere.util.Env
 *  org.compiere.util.TimeUtil
 *  org.compiere.wf.MWorkflow
 *  org.eevolution.model.I_PP_Order
 *  org.eevolution.model.MPPProductBOM
 */
package test.functional.mfg;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.wf.MWorkflow;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.MPPProductBOM;
import org.libero.model.MPPOrder;
import test.functional.mfg.AdempiereTestCase;

public class AbstractMakeToOrder
extends AdempiereTestCase {
    String trxName = this.getTrxName();
    int M_Product_ID = 145;
    int C_BPartner_ID = 120;
    int AD_Org_ID = 50000;
    int AD_User_ID = 101;
    int M_Warehouse_ID = 50001;
    int PP_Product_BOM_ID = 145;
    int AD_Workflow_ID = 50018;
    int S_Resource_ID = 50005;
    int C_DocType_ID = 132;
    Timestamp today = TimeUtil.trunc((Timestamp)new Timestamp(System.currentTimeMillis()), (String)"DD/MM/YYYY");
    Timestamp promisedDeta = TimeUtil.addDays((Timestamp)this.today, (int)10);
    MPPProductBOM bom = null;
    MProduct product = null;
    MBPartner BPartner = null;
    MWorkflow workflow = null;
    BigDecimal Qty = Env.ZERO;
    MOrderLine oline = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void test01() throws Exception {
        this.Qty = new BigDecimal(10);
        this.product = MProduct.get((Properties)this.getCtx(), (int)this.M_Product_ID);
        this.BPartner = new MBPartner(this.getCtx(), this.C_BPartner_ID, this.trxName);
        int PP_Product_BOM_ID = MPPProductBOM.getBOMSearchKey((MProduct)this.product);
        if (PP_Product_BOM_ID <= 0) {
            throw new AdempiereException("@NotFound@ @PP_ProductBOM_ID@");
        }
        this.bom = new MPPProductBOM(this.getCtx(), PP_Product_BOM_ID, this.trxName);
        if (this.bom != null) {
            this.bom.setBOMType("O");
            this.bom.setBOMUse("M");
            this.bom.saveEx();
        }
        this.workflow = new MWorkflow(this.getCtx(), this.AD_Workflow_ID, this.trxName);
        this.workflow.setValue(this.product.getValue());
        this.workflow.saveEx();
        if (this.AD_Workflow_ID <= 0) {
            throw new AdempiereException("@NotFound@ @AD_Workflow_ID@");
        }
        this.workflow = MWorkflow.get((Properties)this.getCtx(), (int)this.AD_Workflow_ID);
        this.createOrder();
        MPPOrder expected = this.createPPOrder();
        MPPOrder actual = MPPOrder.forC_OrderLine_ID(this.getCtx(), this.oline.get_ID(), this.trxName);
        if (actual == null) {
            throw new AdempiereException("@NotFound@ @PP_Order_ID@ not was generate");
        }
        this.assertEquals("Confirming Manufacturing Order", (I_PP_Order)expected, (I_PP_Order)actual);
    }

    public MOrder createOrder() {
        MOrder order = new MOrder(this.getCtx(), 0, this.trxName);
        order.setAD_Org_ID(this.AD_Org_ID);
        order.setDateOrdered(this.today);
        order.setDatePromised(this.promisedDeta);
        order.setIsSOTrx(true);
        order.setC_DocTypeTarget_ID(this.C_DocType_ID);
        order.setC_BPartner_ID(this.C_BPartner_ID);
        order.setAD_User_ID(this.AD_User_ID);
        order.setM_Warehouse_ID(this.M_Warehouse_ID);
        order.setDocStatus("IP");
        order.setDocAction("CO");
        order.saveEx();
        this.oline = new MOrderLine(order);
        this.oline.setM_Product_ID(this.product.get_ID());
        this.oline.setQty(new BigDecimal(10));
        this.oline.saveEx();
        order.processIt("CO");
        return order;
    }

    public MPPOrder createPPOrder() {
        MPPOrder expected = new MPPOrder(this.getCtx(), 0, this.trxName);
        expected.setAD_Org_ID(this.AD_Org_ID);
        expected.setM_Product_ID(this.product.getM_Product_ID());
        expected.setDateOrdered(this.today);
        expected.setDatePromised(this.promisedDeta);
        expected.setDateFinish(this.promisedDeta);
        expected.setPP_Product_BOM_ID(this.PP_Product_BOM_ID);
        expected.setAD_Workflow_ID(this.AD_Workflow_ID);
        expected.setS_Resource_ID(this.S_Resource_ID);
        expected.setM_Warehouse_ID(this.M_Warehouse_ID);
        expected.setDocStatus("IP");
        expected.setQty(this.Qty);
        return expected;
    }

    public void assertEquals(String message, I_PP_Order expected, I_PP_Order actual) throws Exception {
        boolean equals = expected.getAD_Client_ID() == actual.getAD_Client_ID() && expected.getAD_Org_ID() == actual.getAD_Org_ID() && expected.getM_Warehouse_ID() == actual.getM_Warehouse_ID() && expected.getM_Product_ID() == actual.getM_Product_ID() && expected.getQtyOrdered().equals(actual.getQtyOrdered()) && expected.getDocStatus().equals(actual.getDocStatus()) && expected.getDatePromised().equals(actual.getDatePromised()) && expected.getDateOrdered().equals(actual.getDateOrdered());
        StringBuffer sb = new StringBuffer(message).append(": expected=" + (Object)expected).append(", actual=" + (Object)actual);
        AbstractMakeToOrder.assertTrue((String)sb.toString(), (boolean)equals);
    }
}

