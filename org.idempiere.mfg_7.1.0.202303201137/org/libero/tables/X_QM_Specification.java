/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_Workflow
 *  org.compiere.model.I_M_AttributeSet
 *  org.compiere.model.I_M_Product
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 *  org.eevolution.model.I_PP_Product_BOM
 */
package org.libero.tables;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.eevolution.model.I_PP_Product_BOM;
import org.libero.tables.I_QM_Specification;

public class X_QM_Specification
extends PO
implements I_QM_Specification,
I_Persistent {
    private static final long serialVersionUID = 20130626L;

    public X_QM_Specification(Properties ctx, int QM_Specification_ID, String trxName) {
        super(ctx, QM_Specification_ID, trxName);
    }

    public X_QM_Specification(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53040, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_QM_Specification[").append(this.get_ID()).append("]");
        return sb.toString();
    }

    @Override
    public I_AD_Workflow getAD_Workflow() throws RuntimeException {
        return (I_AD_Workflow)MTable.get((Properties)this.getCtx(), (String)"AD_Workflow").getPO(this.getAD_Workflow_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_Workflow_ID(int AD_Workflow_ID) {
        if (AD_Workflow_ID < 1) {
            this.set_Value("AD_Workflow_ID", null);
        } else {
            this.set_Value("AD_Workflow_ID", AD_Workflow_ID);
        }
    }

    @Override
    public int getAD_Workflow_ID() {
        Integer ii = (Integer)this.get_Value("AD_Workflow_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setDescription(String Description) {
        this.set_Value("Description", Description);
    }

    @Override
    public String getDescription() {
        return (String)this.get_Value("Description");
    }

    @Override
    public I_M_AttributeSet getM_AttributeSet() throws RuntimeException {
        return (I_M_AttributeSet)MTable.get((Properties)this.getCtx(), (String)"M_AttributeSet").getPO(this.getM_AttributeSet_ID(), this.get_TrxName());
    }

    @Override
    public void setM_AttributeSet_ID(int M_AttributeSet_ID) {
        if (M_AttributeSet_ID < 0) {
            this.set_Value("M_AttributeSet_ID", null);
        } else {
            this.set_Value("M_AttributeSet_ID", M_AttributeSet_ID);
        }
    }

    @Override
    public int getM_AttributeSet_ID() {
        Integer ii = (Integer)this.get_Value("M_AttributeSet_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_M_Product getM_Product() throws RuntimeException {
        return (I_M_Product)MTable.get((Properties)this.getCtx(), (String)"M_Product").getPO(this.getM_Product_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Product_ID(int M_Product_ID) {
        if (M_Product_ID < 1) {
            this.set_Value("M_Product_ID", null);
        } else {
            this.set_Value("M_Product_ID", M_Product_ID);
        }
    }

    @Override
    public int getM_Product_ID() {
        Integer ii = (Integer)this.get_Value("M_Product_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setName(String Name) {
        this.set_Value("Name", Name);
    }

    @Override
    public String getName() {
        return (String)this.get_Value("Name");
    }

    @Override
    public I_PP_Product_BOM getPP_Product_BOM() throws RuntimeException {
        return (I_PP_Product_BOM)MTable.get((Properties)this.getCtx(), (String)"PP_Product_BOM").getPO(this.getPP_Product_BOM_ID(), this.get_TrxName());
    }

    @Override
    public void setPP_Product_BOM_ID(int PP_Product_BOM_ID) {
        if (PP_Product_BOM_ID < 1) {
            this.set_Value("PP_Product_BOM_ID", null);
        } else {
            this.set_Value("PP_Product_BOM_ID", PP_Product_BOM_ID);
        }
    }

    @Override
    public int getPP_Product_BOM_ID() {
        Integer ii = (Integer)this.get_Value("PP_Product_BOM_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setQM_Specification_ID(int QM_Specification_ID) {
        if (QM_Specification_ID < 1) {
            this.set_ValueNoCheck("QM_Specification_ID", null);
        } else {
            this.set_ValueNoCheck("QM_Specification_ID", QM_Specification_ID);
        }
    }

    @Override
    public int getQM_Specification_ID() {
        Integer ii = (Integer)this.get_Value("QM_Specification_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setQM_Specification_UU(String QM_Specification_UU) {
        this.set_Value("QM_Specification_UU", QM_Specification_UU);
    }

    @Override
    public String getQM_Specification_UU() {
        return (String)this.get_Value("QM_Specification_UU");
    }

    @Override
    public void setValidFrom(Timestamp ValidFrom) {
        this.set_Value("ValidFrom", ValidFrom);
    }

    @Override
    public Timestamp getValidFrom() {
        return (Timestamp)this.get_Value("ValidFrom");
    }

    @Override
    public void setValidTo(Timestamp ValidTo) {
        this.set_Value("ValidTo", ValidTo);
    }

    @Override
    public Timestamp getValidTo() {
        return (Timestamp)this.get_Value("ValidTo");
    }

    @Override
    public void setValue(String Value) {
        this.set_Value("Value", Value);
    }

    @Override
    public String getValue() {
        return (String)this.get_Value("Value");
    }
}

