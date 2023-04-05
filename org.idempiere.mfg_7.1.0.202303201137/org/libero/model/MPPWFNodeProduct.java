/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.Query
 *  org.compiere.util.CCache
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 */
package org.libero.model;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Properties;
import org.compiere.model.Query;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.libero.tables.X_PP_WF_Node_Product;

public class MPPWFNodeProduct
extends X_PP_WF_Node_Product {
    private static final long serialVersionUID = 1L;
    private static CCache<Integer, Collection<MPPWFNodeProduct>> s_cache = new CCache("PP_WF_Node_Product", 20);

    public static Collection<MPPWFNodeProduct> forAD_WF_Node_ID(Properties ctx, int AD_WF_Node_ID) {
        Collection lines = (Collection)s_cache.get((Object)AD_WF_Node_ID);
        if (lines != null) {
            return lines;
        }
        lines = new Query(ctx, "PP_WF_Node_Product", "AD_WF_Node_ID=?", null).setParameters(new Object[]{AD_WF_Node_ID}).setOnlyActiveRecords(true).setOrderBy("SeqNo").list();
        s_cache.put((Object)AD_WF_Node_ID, (Object)lines);
        return lines;
    }

    public MPPWFNodeProduct(Properties ctx, int PP_WF_Node_Product_ID, String trxName) {
        super(ctx, PP_WF_Node_Product_ID, trxName);
    }

    public MPPWFNodeProduct(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    protected boolean beforeSave(boolean newRecord) {
        if (this.getSeqNo() == 0) {
            int seqNo = DB.getSQLValueEx((String)this.get_TrxName(), (String)"SELECT COALESCE(MAX(SeqNo),0)+10 FROM PP_WF_Node_Product WHERE  AD_WF_Node_ID=? AND PP_WF_Node_Product_ID<>?", (Object[])new Object[]{this.getAD_WF_Node_ID(), this.get_ID()});
            this.setSeqNo(seqNo);
        }
        if (this.getQty().compareTo(Env.ZERO) == 0 && this.isSubcontracting()) {
            this.setQty(Env.ONE);
        }
        return true;
    }
}

