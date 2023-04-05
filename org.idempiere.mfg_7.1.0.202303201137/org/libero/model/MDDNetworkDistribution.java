/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.Query
 *  org.compiere.util.CCache
 */
package org.libero.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.compiere.model.Query;
import org.compiere.util.CCache;
import org.libero.model.MDDNetworkDistributionLine;
import org.libero.tables.X_DD_NetworkDistribution;

public class MDDNetworkDistribution
extends X_DD_NetworkDistribution {
    private static final long serialVersionUID = 1L;
    private static CCache<Integer, MDDNetworkDistribution> s_cache = new CCache("DD_NetworkDistribution", 50);
    private MDDNetworkDistributionLine[] m_lines = null;

    public MDDNetworkDistribution(Properties ctx, int DD_NetworkDistribution_ID, String trxName) {
        super(ctx, DD_NetworkDistribution_ID, trxName);
    }

    public MDDNetworkDistribution(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    public static MDDNetworkDistribution get(Properties ctx, int DD_NetworkDistribution_ID) {
        MDDNetworkDistribution retValue = (MDDNetworkDistribution)s_cache.get((Object)DD_NetworkDistribution_ID);
        if (retValue != null) {
            return retValue;
        }
        retValue = new MDDNetworkDistribution(ctx, DD_NetworkDistribution_ID, null);
        s_cache.put((Object)DD_NetworkDistribution_ID, (Object)retValue);
        return retValue;
    }

    public MDDNetworkDistributionLine[] getLines() {
        if (this.m_lines != null) {
            return this.m_lines;
        }
        List list = new Query(this.getCtx(), "DD_NetworkDistributionLine", "DD_NetworkDistribution_ID=?", this.get_TrxName()).setParameters(new Object[]{this.get_ID()}).setOrderBy("PriorityNo, M_Shipper_ID").list();
        this.m_lines = list.toArray(new MDDNetworkDistributionLine[list.size()]);
        return this.m_lines;
    }

    public MDDNetworkDistributionLine[] getLines(int M_Warehouse_ID) {
        ArrayList<MDDNetworkDistributionLine> list = new ArrayList<MDDNetworkDistributionLine>();
        for (MDDNetworkDistributionLine line : this.getLines()) {
            if (line.getM_Warehouse_ID() != M_Warehouse_ID) continue;
            list.add(line);
        }
        return list.toArray(new MDDNetworkDistributionLine[list.size()]);
    }
}

