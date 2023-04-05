/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_M_Attribute
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 */
package org.libero.tables;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.libero.tables.I_QM_Specification;
import org.libero.tables.I_QM_SpecificationLine;

public class X_QM_SpecificationLine
extends PO
implements I_QM_SpecificationLine,
I_Persistent {
    private static final long serialVersionUID = 20130626L;
    public static final int ANDOR_AD_Reference_ID = 204;
    public static final String ANDOR_And = "A";
    public static final String ANDOR_Or = "O";
    public static final int OPERATION_AD_Reference_ID = 205;
    public static final String OPERATION_Eq = "==";
    public static final String OPERATION_GtEq = ">=";
    public static final String OPERATION_Gt = ">>";
    public static final String OPERATION_Le = "<<";
    public static final String OPERATION_Like = "~~";
    public static final String OPERATION_LeEq = "<=";
    public static final String OPERATION_X = "AB";
    public static final String OPERATION_Sql = "SQ";
    public static final String OPERATION_NotEq = "!=";

    public X_QM_SpecificationLine(Properties ctx, int QM_SpecificationLine_ID, String trxName) {
        super(ctx, QM_SpecificationLine_ID, trxName);
    }

    public X_QM_SpecificationLine(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53041, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_QM_SpecificationLine[").append(this.get_ID()).append("]");
        return sb.toString();
    }

    @Override
    public void setAndOr(String AndOr) {
        this.set_Value("AndOr", AndOr);
    }

    @Override
    public String getAndOr() {
        return (String)this.get_Value("AndOr");
    }

    @Override
    public I_M_Attribute getM_Attribute() throws RuntimeException {
        return (I_M_Attribute)MTable.get((Properties)this.getCtx(), (String)"M_Attribute").getPO(this.getM_Attribute_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Attribute_ID(int M_Attribute_ID) {
        if (M_Attribute_ID < 1) {
            this.set_Value("M_Attribute_ID", null);
        } else {
            this.set_Value("M_Attribute_ID", M_Attribute_ID);
        }
    }

    @Override
    public int getM_Attribute_ID() {
        Integer ii = (Integer)this.get_Value("M_Attribute_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setOperation(String Operation) {
        this.set_Value("Operation", Operation);
    }

    @Override
    public String getOperation() {
        return (String)this.get_Value("Operation");
    }

    @Override
    public I_QM_Specification getQM_Specification() throws RuntimeException {
        return (I_QM_Specification)MTable.get((Properties)this.getCtx(), (String)"QM_Specification").getPO(this.getQM_Specification_ID(), this.get_TrxName());
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
    public void setQM_SpecificationLine_ID(int QM_SpecificationLine_ID) {
        if (QM_SpecificationLine_ID < 1) {
            this.set_ValueNoCheck("QM_SpecificationLine_ID", null);
        } else {
            this.set_ValueNoCheck("QM_SpecificationLine_ID", QM_SpecificationLine_ID);
        }
    }

    @Override
    public int getQM_SpecificationLine_ID() {
        Integer ii = (Integer)this.get_Value("QM_SpecificationLine_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setQM_SpecificationLine_UU(String QM_SpecificationLine_UU) {
        this.set_Value("QM_SpecificationLine_UU", QM_SpecificationLine_UU);
    }

    @Override
    public String getQM_SpecificationLine_UU() {
        return (String)this.get_Value("QM_SpecificationLine_UU");
    }

    @Override
    public void setSeqNo(int SeqNo) {
        this.set_Value("SeqNo", SeqNo);
    }

    @Override
    public int getSeqNo() {
        Integer ii = (Integer)this.get_Value("SeqNo");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setValidFrom(String ValidFrom) {
        this.set_Value("ValidFrom", ValidFrom);
    }

    @Override
    public String getValidFrom() {
        return (String)this.get_Value("ValidFrom");
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

