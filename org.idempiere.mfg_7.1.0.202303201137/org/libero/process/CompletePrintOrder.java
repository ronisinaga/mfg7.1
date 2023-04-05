/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.adempiere.exceptions.FillMandatoryException
 *  org.compiere.model.MQuery
 *  org.compiere.model.MTable
 *  org.compiere.model.PrintInfo
 *  org.compiere.print.MPrintFormat
 *  org.compiere.print.ReportCtl
 *  org.compiere.print.ReportEngine
 *  org.compiere.process.ClientProcess
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 */
package org.libero.process;

import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.model.PrintInfo;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.process.ClientProcess;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.libero.model.MPPOrder;

public class CompletePrintOrder
extends SvrProcess
implements ClientProcess {
    private int p_PP_Order_ID = 0;
    private boolean p_IsPrintPickList = false;
    private boolean p_IsPrintWorkflow = false;
    private boolean p_IsPrintPackList = false;
    private boolean p_IsComplete = false;

    protected void prepare() {
        for (ProcessInfoParameter para : this.getParameter()) {
            String name = para.getParameterName();
            if (para.getParameter() == null) continue;
            if (name.equals("PP_Order_ID")) {
                this.p_PP_Order_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("IsPrintPickList")) {
                this.p_IsPrintPickList = para.getParameterAsBoolean();
                continue;
            }
            if (name.equals("IsPrintWorkflow")) {
                this.p_IsPrintWorkflow = para.getParameterAsBoolean();
                continue;
            }
            if (name.equals("IsPrintPackingList")) {
                this.p_IsPrintPackList = para.getParameterAsBoolean();
                continue;
            }
            if (name.equals("IsComplete")) {
                this.p_IsComplete = para.getParameterAsBoolean();
                continue;
            }
            this.log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
    }

    protected String doIt() throws Exception {
        ReportEngine re;
        if (this.p_PP_Order_ID == 0) {
            throw new FillMandatoryException(new String[]{"PP_Order_ID"});
        }
        if (this.p_IsComplete) {
            MPPOrder order = new MPPOrder(this.getCtx(), this.p_PP_Order_ID, this.get_TrxName());
            if (!order.isAvailable()) {
                throw new AdempiereException("@NoQtyAvailable@");
            }
            boolean ok = order.processIt("CO");
            order.saveEx();
            if (!ok) {
                throw new AdempiereException(order.getProcessMsg());
            }
            if (!"CO".equals(order.getDocStatus())) {
                throw new AdempiereException(order.getProcessMsg());
            }
        }
        if (this.p_IsPrintPickList) {
            re = this.getReportEngine("Manufacturing_Order_BOM_Header ** TEMPLATE **", "PP_Order_BOM_Header_v");
            if (re == null) {
                return "";
            }
            ReportCtl.preview((ReportEngine)re);
            re.print();
        }
        if (this.p_IsPrintPackList) {
            re = this.getReportEngine("Manufacturing_Order_BOM_Header_Packing ** TEMPLATE **", "PP_Order_BOM_Header_v");
            if (re == null) {
                return "";
            }
            ReportCtl.preview((ReportEngine)re);
            re.print();
        }
        if (this.p_IsPrintWorkflow) {
            re = this.getReportEngine("Manufacturing_Order_Workflow_Header ** TEMPLATE **", "PP_Order_Workflow_Header_v");
            if (re == null) {
                return "";
            }
            ReportCtl.preview((ReportEngine)re);
            re.print();
        }
        return "@OK@";
    }

    private ReportEngine getReportEngine(String formatName, String tableName) {
        int format_id = MPrintFormat.getPrintFormat_ID((String)formatName, (int)MTable.getTable_ID((String)tableName), (int)this.getAD_Client_ID());
        MPrintFormat format = MPrintFormat.get((Properties)this.getCtx(), (int)format_id, (boolean)true);
        if (format == null) {
            this.addLog("@NotFound@ @AD_PrintFormat_ID@");
            return null;
        }
        MQuery query = new MQuery(tableName);
        query.addRestriction("PP_Order_ID", "=", this.p_PP_Order_ID);
        PrintInfo info = new PrintInfo(tableName, MTable.getTable_ID((String)tableName), this.p_PP_Order_ID);
        ReportEngine re = new ReportEngine(this.getCtx(), format, query, info);
        return re;
    }
}

