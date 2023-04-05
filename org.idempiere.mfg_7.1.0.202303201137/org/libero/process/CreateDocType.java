/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MDocType
 *  org.compiere.model.MGLCategory
 *  org.compiere.model.MSequence
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.Env
 */
package org.libero.process;

import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MDocType;
import org.compiere.model.MGLCategory;
import org.compiere.model.MSequence;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

public class CreateDocType
extends SvrProcess {
    private int AD_Client_ID = 0;
    private String trxname = null;

    protected void prepare() {
        System.out.println("In AddLiberoRecords prepare");
        this.log.fine("In AddLiberoRecords prepare");
        this.AD_Client_ID = Integer.parseInt(Env.getContext((Properties)Env.getCtx(), (String)"#AD_Client_ID"));
        this.getParameter();
    }

    protected String doIt() throws Exception {
        System.out.println("In AddLiberoRecords doIt");
        this.log.fine("In AddLiberoRecords doIt");
        Env.getCtx();
        this.trxname = this.get_TrxName();
        int GL_Manufacturing = this.createGLCategory("Manufactuing", "D", false);
        int GL_Distribution = this.createGLCategory("Distribution", "D", false);
        this.createDocType("Manufacturing Order", "Manufacturing Order", "MOP", null, 0, 0, 80000, GL_Manufacturing);
        this.createDocType("Manufacturing Cost Collector", "Cost Collector", "MCC", null, 0, 0, 81000, GL_Manufacturing);
        this.createDocType("Maintenance Order", "Maintenance Order", "MOF", null, 0, 0, 86000, GL_Manufacturing);
        this.createDocType("Quality Order", "Quality Order", "MQO", null, 0, 0, 87000, GL_Manufacturing);
        this.createDocType("Distribution Order", "Distribution Orde", "DOO", null, 0, 0, 88000, GL_Distribution);
        return "ok";
    }

    private int createGLCategory(String Name, String CategoryType, boolean isDefault) {
        MGLCategory cat = new MGLCategory(Env.getCtx(), 0, this.trxname);
        cat.setName(Name);
        cat.setCategoryType(CategoryType);
        cat.setIsDefault(isDefault);
        if (!cat.save()) {
            this.log.log(Level.SEVERE, "GL Category NOT created - " + Name);
            return 0;
        }
        return cat.getGL_Category_ID();
    }

    private int createDocType(String Name, String PrintName, String DocBaseType, String DocSubTypeSO, int C_DocTypeShipment_ID, int C_DocTypeInvoice_ID, int StartNo, int GL_Category_ID) {
        this.log.fine("In createDocType");
        this.log.fine("docBaseType: " + DocBaseType);
        this.log.fine("GL_Category_ID: " + GL_Category_ID);
        MSequence sequence = null;
        if (StartNo != 0 && !(sequence = new MSequence(Env.getCtx(), this.getAD_Client_ID(), Name, StartNo, this.trxname)).save()) {
            this.log.log(Level.SEVERE, "Sequence NOT created - " + Name);
            return 0;
        }
        MDocType dt = new MDocType(Env.getCtx(), 0, this.trxname);
        dt.setAD_Org_ID(0);
        dt.set_CustomColumn("DocBaseType", (Object)DocBaseType);
        dt.setName(Name);
        dt.setPrintName(Name);
        if (DocSubTypeSO != null) {
            dt.setDocSubTypeSO(DocSubTypeSO);
        }
        if (C_DocTypeShipment_ID != 0) {
            dt.setC_DocTypeShipment_ID(C_DocTypeShipment_ID);
        }
        if (C_DocTypeInvoice_ID != 0) {
            dt.setC_DocTypeInvoice_ID(C_DocTypeInvoice_ID);
        }
        if (GL_Category_ID != 0) {
            dt.setGL_Category_ID(GL_Category_ID);
        }
        if (sequence == null) {
            dt.setIsDocNoControlled(false);
        } else {
            dt.setIsDocNoControlled(true);
            dt.setDocNoSequence_ID(sequence.getAD_Sequence_ID());
        }
        dt.setIsSOTrx(false);
        if (!dt.save()) {
            this.log.log(Level.SEVERE, "DocType NOT created - " + Name);
            return 0;
        }
        return dt.getC_DocType_ID();
    }
}

