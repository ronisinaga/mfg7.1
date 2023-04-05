/*
 * Decompiled with CFR 0.150.
 */
package org.libero.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.adempiere.model.engines.IInventoryAllocation;
import org.libero.tables.X_PP_Cost_CollectorMA;

public class MPPCostCollectorMA
extends X_PP_Cost_CollectorMA
implements IInventoryAllocation {
    private static final long serialVersionUID = 1L;

    public MPPCostCollectorMA(Properties ctx, int PP_Cost_CollectorMA_ID, String trxName) {
        super(ctx, PP_Cost_CollectorMA_ID, trxName);
        if (PP_Cost_CollectorMA_ID != 0) {
            throw new IllegalArgumentException("Multi-Key");
        }
    }

    public MPPCostCollectorMA(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}

