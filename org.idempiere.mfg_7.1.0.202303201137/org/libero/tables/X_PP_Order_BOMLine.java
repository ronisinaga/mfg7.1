/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_User
 *  org.compiere.model.I_C_UOM
 *  org.compiere.model.I_M_AttributeSetInstance
 *  org.compiere.model.I_M_ChangeNotice
 *  org.compiere.model.I_M_Locator
 *  org.compiere.model.I_M_Product
 *  org.compiere.model.I_M_Warehouse
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 *  org.compiere.util.Env
 *  org.compiere.util.KeyNamePair
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_ChangeNotice;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_Order_BOM;
import org.libero.tables.I_PP_Order_BOMLine;

public class X_PP_Order_BOMLine
extends PO
implements I_PP_Order_BOMLine,
I_Persistent {
    private static final long serialVersionUID = 20130626L;
    public static final int COMPONENTTYPE_AD_Reference_ID = 53225;
    public static final String COMPONENTTYPE_By_Product = "BY";
    public static final String COMPONENTTYPE_Component = "CO";
    public static final String COMPONENTTYPE_Phantom = "PH";
    public static final String COMPONENTTYPE_Packing = "PK";
    public static final String COMPONENTTYPE_Planning = "PL";
    public static final String COMPONENTTYPE_Tools = "TL";
    public static final String COMPONENTTYPE_Option = "OP";
    public static final String COMPONENTTYPE_Variant = "VA";
    public static final String COMPONENTTYPE_Co_Product = "CP";
    public static final int ISSUEMETHOD_AD_Reference_ID = 53226;
    public static final String ISSUEMETHOD_Issue = "0";
    public static final String ISSUEMETHOD_Backflush = "1";
    public static final String ISSUEMETHOD_FloorStock = "2";

    public X_PP_Order_BOMLine(Properties ctx, int PP_Order_BOMLine_ID, String trxName) {
        super(ctx, PP_Order_BOMLine_ID, trxName);
    }

    public X_PP_Order_BOMLine(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53025, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_PP_Order_BOMLine[").append(this.get_ID()).append("]");
        return sb.toString();
    }

    @Override
    public I_AD_User getAD_User() throws RuntimeException {
        return (I_AD_User)MTable.get((Properties)this.getCtx(), (String)"AD_User").getPO(this.getAD_User_ID(), this.get_TrxName());
    }

    @Override
    public void setAD_User_ID(int AD_User_ID) {
        if (AD_User_ID < 1) {
            this.set_Value("AD_User_ID", null);
        } else {
            this.set_Value("AD_User_ID", AD_User_ID);
        }
    }

    @Override
    public int getAD_User_ID() {
        Integer ii = (Integer)this.get_Value("AD_User_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setAssay(BigDecimal Assay) {
        this.set_ValueNoCheck("Assay", Assay);
    }

    @Override
    public BigDecimal getAssay() {
        BigDecimal bd = (BigDecimal)this.get_Value("Assay");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setBackflushGroup(String BackflushGroup) {
        this.set_ValueNoCheck("BackflushGroup", BackflushGroup);
    }

    @Override
    public String getBackflushGroup() {
        return (String)this.get_Value("BackflushGroup");
    }

    @Override
    public void setComponentType(String ComponentType) {
        this.set_Value("ComponentType", ComponentType);
    }

    @Override
    public String getComponentType() {
        return (String)this.get_Value("ComponentType");
    }

    @Override
    public void setCostAllocationPerc(BigDecimal CostAllocationPerc) {
        this.set_Value("CostAllocationPerc", CostAllocationPerc);
    }

    @Override
    public BigDecimal getCostAllocationPerc() {
        BigDecimal bd = (BigDecimal)this.get_Value("CostAllocationPerc");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public I_C_UOM getC_UOM() throws RuntimeException {
        return (I_C_UOM)MTable.get((Properties)this.getCtx(), (String)"C_UOM").getPO(this.getC_UOM_ID(), this.get_TrxName());
    }

    @Override
    public void setC_UOM_ID(int C_UOM_ID) {
        if (C_UOM_ID < 1) {
            this.set_ValueNoCheck("C_UOM_ID", null);
        } else {
            this.set_ValueNoCheck("C_UOM_ID", C_UOM_ID);
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
    public void setDateDelivered(Timestamp DateDelivered) {
        this.set_Value("DateDelivered", DateDelivered);
    }

    @Override
    public Timestamp getDateDelivered() {
        return (Timestamp)this.get_Value("DateDelivered");
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
    public void setFeature(String Feature) {
        this.set_Value("Feature", Feature);
    }

    @Override
    public String getFeature() {
        return (String)this.get_Value("Feature");
    }

    @Override
    public void setForecast(BigDecimal Forecast) {
        this.set_ValueNoCheck("Forecast", Forecast);
    }

    @Override
    public BigDecimal getForecast() {
        BigDecimal bd = (BigDecimal)this.get_Value("Forecast");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
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
    public void setIsCritical(boolean IsCritical) {
        this.set_Value("IsCritical", IsCritical);
    }

    @Override
    public boolean isCritical() {
        Object oo = this.get_Value("IsCritical");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIsQtyPercentage(boolean IsQtyPercentage) {
        this.set_ValueNoCheck("IsQtyPercentage", IsQtyPercentage);
    }

    @Override
    public boolean isQtyPercentage() {
        Object oo = this.get_Value("IsQtyPercentage");
        if (oo != null) {
            if (oo instanceof Boolean) {
                return (Boolean)oo;
            }
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public void setIssueMethod(String IssueMethod) {
        this.set_Value("IssueMethod", IssueMethod);
    }

    @Override
    public String getIssueMethod() {
        return (String)this.get_Value("IssueMethod");
    }

    @Override
    public void setLeadTimeOffset(int LeadTimeOffset) {
        this.set_Value("LeadTimeOffset", LeadTimeOffset);
    }

    @Override
    public int getLeadTimeOffset() {
        Integer ii = (Integer)this.get_Value("LeadTimeOffset");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setLine(int Line) {
        this.set_Value("Line", Line);
    }

    @Override
    public int getLine() {
        Integer ii = (Integer)this.get_Value("Line");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException {
        return (I_M_AttributeSetInstance)MTable.get((Properties)this.getCtx(), (String)"M_AttributeSetInstance").getPO(this.getM_AttributeSetInstance_ID(), this.get_TrxName());
    }

    @Override
    public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
        if (M_AttributeSetInstance_ID < 0) {
            this.set_ValueNoCheck("M_AttributeSetInstance_ID", null);
        } else {
            this.set_ValueNoCheck("M_AttributeSetInstance_ID", M_AttributeSetInstance_ID);
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
    public I_M_Locator getM_Locator() throws RuntimeException {
        return (I_M_Locator)MTable.get((Properties)this.getCtx(), (String)"M_Locator").getPO(this.getM_Locator_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Locator_ID(int M_Locator_ID) {
        if (M_Locator_ID < 1) {
            this.set_Value("M_Locator_ID", null);
        } else {
            this.set_Value("M_Locator_ID", M_Locator_ID);
        }
    }

    @Override
    public int getM_Locator_ID() {
        Integer ii = (Integer)this.get_Value("M_Locator_ID");
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
            this.set_ValueNoCheck("M_Product_ID", null);
        } else {
            this.set_ValueNoCheck("M_Product_ID", M_Product_ID);
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

    public KeyNamePair getKeyNamePair() {
        return new KeyNamePair(this.get_ID(), String.valueOf(this.getM_Product_ID()));
    }

    @Override
    public I_M_Warehouse getM_Warehouse() throws RuntimeException {
        return (I_M_Warehouse)MTable.get((Properties)this.getCtx(), (String)"M_Warehouse").getPO(this.getM_Warehouse_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Warehouse_ID(int M_Warehouse_ID) {
        if (M_Warehouse_ID < 1) {
            this.set_Value("M_Warehouse_ID", null);
        } else {
            this.set_Value("M_Warehouse_ID", M_Warehouse_ID);
        }
    }

    @Override
    public int getM_Warehouse_ID() {
        Integer ii = (Integer)this.get_Value("M_Warehouse_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public I_PP_Order_BOM getPP_Order_BOM() throws RuntimeException {
        return (I_PP_Order_BOM)MTable.get((Properties)this.getCtx(), (String)"PP_Order_BOM").getPO(this.getPP_Order_BOM_ID(), this.get_TrxName());
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
    public void setPP_Order_BOMLine_ID(int PP_Order_BOMLine_ID) {
        if (PP_Order_BOMLine_ID < 1) {
            this.set_ValueNoCheck("PP_Order_BOMLine_ID", null);
        } else {
            this.set_ValueNoCheck("PP_Order_BOMLine_ID", PP_Order_BOMLine_ID);
        }
    }

    @Override
    public int getPP_Order_BOMLine_ID() {
        Integer ii = (Integer)this.get_Value("PP_Order_BOMLine_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPP_Order_BOMLine_UU(String PP_Order_BOMLine_UU) {
        this.set_Value("PP_Order_BOMLine_UU", PP_Order_BOMLine_UU);
    }

    @Override
    public String getPP_Order_BOMLine_UU() {
        return (String)this.get_Value("PP_Order_BOMLine_UU");
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
    public void setQtyBatch(BigDecimal QtyBatch) {
        this.set_ValueNoCheck("QtyBatch", QtyBatch);
    }

    @Override
    public BigDecimal getQtyBatch() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyBatch");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyBOM(BigDecimal QtyBOM) {
        this.set_ValueNoCheck("QtyBOM", QtyBOM);
    }

    @Override
    public BigDecimal getQtyBOM() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyBOM");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyDelivered(BigDecimal QtyDelivered) {
        this.set_ValueNoCheck("QtyDelivered", QtyDelivered);
    }

    @Override
    public BigDecimal getQtyDelivered() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyDelivered");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyEntered(BigDecimal QtyEntered) {
        this.set_ValueNoCheck("QtyEntered", QtyEntered);
    }

    @Override
    public BigDecimal getQtyEntered() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyEntered");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyPost(BigDecimal QtyPost) {
        this.set_ValueNoCheck("QtyPost", QtyPost);
    }

    @Override
    public BigDecimal getQtyPost() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyPost");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyReject(BigDecimal QtyReject) {
        this.set_ValueNoCheck("QtyReject", QtyReject);
    }

    @Override
    public BigDecimal getQtyReject() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyReject");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyRequired(BigDecimal QtyRequired) {
        this.set_Value("QtyRequired", QtyRequired);
    }

    @Override
    public BigDecimal getQtyRequired() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyRequired");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyReserved(BigDecimal QtyReserved) {
        this.set_ValueNoCheck("QtyReserved", QtyReserved);
    }

    @Override
    public BigDecimal getQtyReserved() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyReserved");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setQtyScrap(BigDecimal QtyScrap) {
        this.set_ValueNoCheck("QtyScrap", QtyScrap);
    }

    @Override
    public BigDecimal getQtyScrap() {
        BigDecimal bd = (BigDecimal)this.get_Value("QtyScrap");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setScrap(BigDecimal Scrap) {
        this.set_ValueNoCheck("Scrap", Scrap);
    }

    @Override
    public BigDecimal getScrap() {
        BigDecimal bd = (BigDecimal)this.get_Value("Scrap");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
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
}

