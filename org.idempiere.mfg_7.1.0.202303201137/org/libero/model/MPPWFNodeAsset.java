/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.Query
 *  org.compiere.util.CCache
 */
package org.libero.model;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Properties;
import org.compiere.model.Query;
import org.compiere.util.CCache;
import org.libero.tables.X_PP_WF_Node_Asset;

public class MPPWFNodeAsset
extends X_PP_WF_Node_Asset {
    private static final long serialVersionUID = 1L;
    private static CCache<Integer, Collection<MPPWFNodeAsset>> s_cache = new CCache("PP_WF_Node_Asset", 20);

    public static Collection<MPPWFNodeAsset> forAD_WF_Node_ID(Properties ctx, int AD_WF_Node_ID) {
        Collection lines = (Collection)s_cache.get((Object)AD_WF_Node_ID);
        if (lines != null) {
            return lines;
        }
        lines = new Query(ctx, "PP_WF_Node_Asset", "AD_WF_Node_ID=?", null).setParameters(new Object[]{AD_WF_Node_ID}).setOnlyActiveRecords(true).setOrderBy("SeqNo").list();
        s_cache.put((Object)AD_WF_Node_ID, (Object)lines);
        return lines;
    }

    public MPPWFNodeAsset(Properties ctx, int PP_WF_Node_Asset_ID, String trxName) {
        super(ctx, PP_WF_Node_Asset_ID, trxName);
    }

    public MPPWFNodeAsset(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}

