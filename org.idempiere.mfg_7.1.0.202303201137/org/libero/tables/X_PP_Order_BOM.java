/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_C_UOM
 *  org.compiere.model.I_M_AttributeSetInstance
 *  org.compiere.model.I_M_ChangeNotice
 *  org.compiere.model.I_M_Product
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.tables;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_ChangeNotice;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_Order_BOM;

public class X_PP_Order_BOM
extends PO
implements I_PP_Order_BOM,
I_Persistent {
    private static final long serialVersionUID = 20130626L;
    public static final int BOMTYPE_AD_Reference_ID = 347;
    public static final String BOMTYPE_CurrentActive = "A";
    public static final String BOMTYPE_Make_To_Order = "O";
    public static final String BOMTYPE_Previous = "P";
    public static final String BOMTYPE_PreviousSpare = "S";
    public static final String BOMTYPE_Future = "F";
    public static final String BOMTYPE_Maintenance = "M";
    public static final String BOMTYPE_Repair = "R";
    public static final String BOMTYPE_ProductConfigure = "C";
    public static final String BOMTYPE_Make_To_Kit = "K";
    public static final int BOMUSE_AD_Reference_ID = 348;
    public static final String BOMUSE_Master = "A";
    public static final String BOMUSE_Engineering = "E";
    public static final String BOMUSE_Manufacturing = "M";
    public static final String BOMUSE_Planning = "P";
    public static final String BOMUSE_Quality = "Q";

    public X_PP_Order_BOM(Properties ctx, int PP_Order_BOM_ID, String trxName) {
        super(ctx, PP_Order_BOM_ID, trxName);
    }

    public X_PP_Order_BOM(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53026, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_PP_Order_BOM[").append(this.get_ID()).append("]");
        return sb.toString();
    }

    @Override
    public void setBOMType(String BOMType) {
        this.set_Value("BOMType", BOMType);
    }

    @Override
    public String getBOMType() {
        return (String)this.get_Value("BOMType");
    }

    @Override
    public void setBOMUse(String BOMUse) {
        this.set_Value("BOMUse", BOMUse);
    }

    @Override
    public String getBOMUse() {
        return (String)this.get_Value("BOMUse");
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
    public I_C_UOM getC_UOM() throws RuntimeException {
        return (I_C_UOM)MTable.get((Properties)this.getCtx(), (String)"C_UOM").getPO(this.getC_UOM_ID(), this.get_TrxName());
    }

    @Override
    public void setC_UOM_ID(int C_UOM_ID) {
        if (C_UOM_ID < 1) {
            this.set_Value("C_UOM_ID", null);
        } else {
            this.set_Value("C_UOM_ID", C_UOM_ID);
        }
    }

    @Override
    public int getC_UOM_ID() {
        Integer ii = (Integer)this.get_Value("C_UOM_ID");
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
    public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException {
        return (I_M_AttributeSetInstance)MTable.get((Properties)this.getCtx(), (String)"M_AttributeSetInstance").getPO(this.getM_AttributeSetInstance_ID(), this.get_TrxName());
    }

    @Override
    public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
        if (M_AttributeSetInstance_ID < 0) {
            this.set_Value("M_AttributeSetInstance_ID", null);
        } else {
            this.set_Value("M_AttributeSetInstance_ID", M_AttributeSetInstance_ID);
        }
    }

    @Override
    public int getM_AttributeSetInstance_ID() {
        Integer ii = (Integer)this.get_Value("M_AttributeSetInstance_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
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

    public KeyNamePair getKeyNamePair() {
        return new KeyNamePair(this.get_ID(), this.getName());
    }

    @Override
    public void setPP_Order_BOM_ID(int PP_Order_BOM_ID) {
        if (PP_Order_BOM_ID < 1) {
            this.set_ValueNoCheck("PP_Order_BOM_ID", null);
        } else {
            this.set_ValueNoCheck("PP_Order_BOM_ID", PP_Order_BOM_ID);
        }
    }

    @Override
    public int getPP_Order_BOM_ID() {
        Integer ii = (Integer)this.get_Value("PP_Order_BOM_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPP_Order_BOM_UU(String PP_Order_BOM_UU) {
        this.set_Value("PP_Order_BOM_UU", PP_Order_BOM_UU);
    }

    @Override
    public String getPP_Order_BOM_UU() {
        return (String)this.get_Value("PP_Order_BOM_UU");
    }

    @Override
    public I_PP_Order getPP_Order() throws RuntimeException {
        return (I_PP_Order)MTable.get((Properties)this.getCtx(), (String)"PP_Order").getPO(this.getPP_Order_ID(), this.get_TrxName());
    }

    @Override
    public void setPP_Order_ID(int PP_Order_ID) {
        if (PP_Order_ID < 1) {
            this.set_ValueNoCheck("PP_Order_ID", null);
        } else {
            this.set_ValueNoCheck("PP_Order_ID", PP_Order_ID);
        }
    }

    @Override
    public int getPP_Order_ID() {
        Integer ii = (Integer)this.get_Value("PP_Order_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
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
}

