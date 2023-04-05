/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_M_AttributeSetInstance
 *  org.compiere.model.I_Persistent
 *  org.compiere.model.MTable
 *  org.compiere.model.PO
 *  org.compiere.model.POInfo
 *  org.compiere.util.Env
 */
package org.libero.tables;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.libero.tables.I_PP_Cost_Collector;
import org.libero.tables.I_PP_Cost_CollectorMA;

public class X_PP_Cost_CollectorMA
extends PO
implements I_PP_Cost_CollectorMA,
I_Persistent {
    private static final long serialVersionUID = 20130626L;

    public X_PP_Cost_CollectorMA(Properties ctx, int PP_Cost_CollectorMA_ID, String trxName) {
        super(ctx, PP_Cost_CollectorMA_ID, trxName);
    }

    public X_PP_Cost_CollectorMA(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected int get_AccessLevel() {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties ctx) {
        POInfo poi = POInfo.getPOInfo((Properties)ctx, (int)53062, (String)this.get_TrxName());
        return poi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_PP_Cost_CollectorMA[").append(this.get_ID()).append("]");
        return sb.toString();
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
    public void setMovementQty(BigDecimal MovementQty) {
        this.set_Value("MovementQty", MovementQty);
    }

    @Override
    public BigDecimal getMovementQty() {
        BigDecimal bd = (BigDecimal)this.get_Value("MovementQty");
        if (bd == null) {
            return Env.ZERO;
        }
        return bd;
    }

    @Override
    public I_PP_Cost_Collector getPP_Cost_Collector() throws RuntimeException {
        return (I_PP_Cost_Collector)MTable.get((Properties)this.getCtx(), (String)"PP_Cost_Collector").getPO(this.getPP_Cost_Collector_ID(), this.get_TrxName());
    }

    @Override
    public void setPP_Cost_Collector_ID(int PP_Cost_Collector_ID) {
        if (PP_Cost_Collector_ID < 1) {
            this.set_Value("PP_Cost_Collector_ID", null);
        } else {
            this.set_Value("PP_Cost_Collector_ID", PP_Cost_Collector_ID);
        }
    }

    @Override
    public int getPP_Cost_Collector_ID() {
        Integer ii = (Integer)this.get_Value("PP_Cost_Collector_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPP_Cost_CollectorMA_ID(int PP_Cost_CollectorMA_ID) {
        if (PP_Cost_CollectorMA_ID < 1) {
            this.set_ValueNoCheck("PP_Cost_CollectorMA_ID", null);
        } else {
            this.set_ValueNoCheck("PP_Cost_CollectorMA_ID", PP_Cost_CollectorMA_ID);
        }
    }

    @Override
    public int getPP_Cost_CollectorMA_ID() {
        Integer ii = (Integer)this.get_Value("PP_Cost_CollectorMA_ID");
        if (ii == null) {
            return 0;
        }
        return ii;
    }

    @Override
    public void setPP_Cost_CollectorMA_UU(String PP_Cost_CollectorMA_UU) {
        this.set_Value("PP_Cost_CollectorMA_UU", PP_Cost_CollectorMA_UU);
    }

    @Override
    public String getPP_Cost_CollectorMA_UU() {
        return (String)this.get_Value("PP_Cost_CollectorMA_UU");
    }
}

