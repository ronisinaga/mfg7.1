/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_M_Shipper
 *  org.compiere.model.I_M_Warehouse
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 *  org.compiere.util.Env
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.libero.tables.I_DD_NetworkDistribution;
import org.libero.tables.I_DD_NetworkDistributionLine;

public class X_DD_NetworkDistributionLine
extends PO
implements I_DD_NetworkDistributionLine,
I_Persistent {
    private static final long serialVersionUID = 20130626L;

    public X_DD_NetworkDistributionLine(Properties ctx, int DD_NetworkDistributionLine_ID, String trxName) {
        super(ctx, DD_NetworkDistributionLine_ID, trxName);
    }

    public X_DD_NetworkDistributionLine(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53061, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_DD_NetworkDistributionLine[").append(this.get_ID()).append("]");
        return sb.toString();
    }

    @Override
    public I_DD_NetworkDistribution getDD_NetworkDistribution() throws RuntimeException {
        return (I_DD_NetworkDistribution)MTable.get((Properties)this.getCtx(), (String)"DD_NetworkDistribution").getPO(this.getDD_NetworkDistribution_ID(), this.get_TrxName());
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
    public void setDD_NetworkDistributionLine_ID(int DD_NetworkDistributionLine_ID) {
        if (DD_NetworkDistributionLine_ID < 1) {
            this.set_ValueNoCheck("DD_NetworkDistributionLine_ID", null);
        } else {
            this.set_ValueNoCheck("DD_NetworkDistributionLine_ID", DD_NetworkDistributionLine_ID);
        }
    }

    @Override
    public int getDD_NetworkDistributionLine_ID() {
        Integer ii = (Integer)this.get_Value("DD_NetworkDistributionLine_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setDD_NetworkDistributionLine_UU(String DD_NetworkDistributionLine_UU) {
        this.set_Value("DD_NetworkDistributionLine_UU", DD_NetworkDistributionLine_UU);
    }

    @Override
    public String getDD_NetworkDistributionLine_UU() {
        return (String)this.get_Value("DD_NetworkDistributionLine_UU");
    }

    @Override
    public I_M_Shipper getM_Shipper() throws RuntimeException {
        return (I_M_Shipper)MTable.get((Properties)this.getCtx(), (String)"M_Shipper").getPO(this.getM_Shipper_ID(), this.get_TrxName());
    }

    @Override
    public void setM_Shipper_ID(int M_Shipper_ID) {
        if (M_Shipper_ID < 1) {
            this.set_Value("M_Shipper_ID", null);
        } else {
            this.set_Value("M_Shipper_ID", M_Shipper_ID);
        }
    }

    @Override
    public int getM_Shipper_ID() {
        Integer ii = (Integer)this.get_Value("M_Shipper_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
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
    public I_M_Warehouse getM_WarehouseSource() throws RuntimeException {
        return (I_M_Warehouse)MTable.get((Properties)this.getCtx(), (String)"M_Warehouse").getPO(this.getM_WarehouseSource_ID(), this.get_TrxName());
    }

    @Override
    public void setM_WarehouseSource_ID(int M_WarehouseSource_ID) {
        if (M_WarehouseSource_ID < 1) {
            this.set_Value("M_WarehouseSource_ID", null);
        } else {
            this.set_Value("M_WarehouseSource_ID", M_WarehouseSource_ID);
        }
    }

    @Override
    public int getM_WarehouseSource_ID() {
        Integer ii = (Integer)this.get_Value("M_WarehouseSource_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPercent(BigDecimal Percent) {
        this.set_Value("Percent", Percent);
    }

    @Override
    public BigDecimal getPercent() {
        BigDecimal bd = (BigDecimal)this.get_Value("Percent");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public void setPriorityNo(int PriorityNo) {
        this.set_Value("PriorityNo", PriorityNo);
    }

    @Override
    public int getPriorityNo() {
        Integer ii = (Integer)this.get_Value("PriorityNo");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setTransfertTime(BigDecimal TransfertTime) {
        this.set_Value("TransfertTime", TransfertTime);
    }

    @Override
    public BigDecimal getTransfertTime() {
        BigDecimal bd = (BigDecimal)this.get_Value("TransfertTime");
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

