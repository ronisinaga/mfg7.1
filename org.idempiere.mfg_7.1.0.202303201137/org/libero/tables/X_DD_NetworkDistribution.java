/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_M_ChangeNotice
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 *  org.compiere.util.KeyNamePair
 */
package org.libero.tables;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.I_M_ChangeNotice;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.KeyNamePair;
import org.libero.tables.I_DD_NetworkDistribution;

public class X_DD_NetworkDistribution
extends PO
implements I_DD_NetworkDistribution,
I_Persistent {
    private static final long serialVersionUID = 20130626L;

    public X_DD_NetworkDistribution(Properties ctx, int DD_NetworkDistribution_ID, String trxName) {
        super(ctx, DD_NetworkDistribution_ID, trxName);
    }

    public X_DD_NetworkDistribution(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53060, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_DD_NetworkDistribution[").append(this.get_ID()).append("]");
        return sb.toString();
    }

    @Override
    public void setCopyFrom(String CopyFrom) {
        this.set_Value("CopyFrom", CopyFrom);
    }

    @Override
    public String getCopyFrom() {
        return (String)this.get_Value("CopyFrom");
    }

    @Override
    public void setDD_NetworkDistribution_ID(int DD_NetworkDistribution_ID) {
        if (DD_NetworkDistribution_ID < 1) {
            this.set_ValueNoCheck("DD_NetworkDistribution_ID", null);
        } else {
            this.set_ValueNoCheck("DD_NetworkDistribution_ID", DD_NetworkDistribution_ID);
        }
    }

    @Override
    public int getDD_NetworkDistribution_ID() {
        Integer ii = (Integer)this.get_Value("DD_NetworkDistribution_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setDD_NetworkDistribution_UU(String DD_NetworkDistribution_UU) {
        this.set_Value("DD_NetworkDistribution_UU", DD_NetworkDistribution_UU);
    }

    @Override
    public String getDD_NetworkDistribution_UU() {
        return (String)this.get_Value("DD_NetworkDistribution_UU");
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
    public void setDocumentNo(String DocumentNo) {
        this.set_Value("DocumentNo", DocumentNo);
    }

    @Override
    public String getDocumentNo() {
        return (String)this.get_Value("DocumentNo");
    }

    @Override
    public void setHelp(String Help) {
        this.set_Value("Help", Help);
    }

    @Override
    public String getHelp() {
        return (String)this.get_Value("Help");
    }

    @Override
    public I_M_ChangeNotice getM_ChangeNotice() throws RuntimeException {
        return (I_M_ChangeNotice)MTable.get((Properties)this.getCtx(), (String)"M_ChangeNotice").getPO(this.getM_ChangeNotice_ID(), this.get_TrxName());
    }

    @Override
    public void setM_ChangeNotice_ID(int M_ChangeNotice_ID) {
        if (M_ChangeNotice_ID < 1) {
            this.set_Value("M_ChangeNotice_ID", null);
        } else {
            this.set_Value("M_ChangeNotice_ID", M_ChangeNotice_ID);
        }
    }

    @Override
    public int getM_ChangeNotice_ID() {
        Integer ii = (Integer)this.get_Value("M_ChangeNotice_ID");
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
    public void setProcessing(boolean Processing) {
        this.set_Value("Processing", Processing);
    }

    @Override
    public boolean isProcessing() {
        Object oo = this.get_Value("Processing");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setRevision(String Revision) {
        this.set_Value("Revision", Revision);
    }

    @Override
    public String getRevision() {
        return (String)this.get_Value("Revision");
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

    public KeyNamePair getKeyNamePair() {
        return new KeyNamePair(this.get_ID(), this.getValue());
    }
}

