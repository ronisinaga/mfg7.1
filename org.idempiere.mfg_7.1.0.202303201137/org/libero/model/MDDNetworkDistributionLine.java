/*
 * Decompiled with CFR 0.150.
 */
package org.libero.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.libero.tables.X_DD_NetworkDistributionLine;

public class MDDNetworkDistributionLine
extends X_DD_NetworkDistributionLine {
    private static final long serialVersionUID = 1L;

    public MDDNetworkDistributionLine(Properties ctx, int DD_NetworkDistributionLine_ID, String trxName) {
        super(ctx, DD_NetworkDistributionLine_ID, trxName);
    }

    public MDDNetworkDistributionLine(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}

