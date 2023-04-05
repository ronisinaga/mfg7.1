/*
 * Decompiled with CFR 0.150.
 */
package org.libero.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.libero.model.MPPOrderNode;
import org.libero.model.MPPWFNodeAsset;
import org.libero.tables.X_PP_Order_Node_Asset;

public class MPPOrderNodeAsset
extends X_PP_Order_Node_Asset {
    private static final long serialVersionUID = 1L;

    public MPPOrderNodeAsset(Properties ctx, int PP_Order_Node_Asset_ID, String trxName) {
        super(ctx, PP_Order_Node_Asset_ID, trxName);
    }

    public MPPOrderNodeAsset(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    public MPPOrderNodeAsset(MPPWFNodeAsset na, MPPOrderNode PP_Order_Node) {
        this(PP_Order_Node.getCtx(), 0, PP_Order_Node.get_TrxName());
        this.setClientOrg(PP_Order_Node);
        this.setA_Asset_ID(na.getA_Asset_ID());
        this.setPP_Order_ID(PP_Order_Node.getPP_Order_ID());
        this.setPP_Order_Workflow_ID(PP_Order_Node.getPP_Order_Workflow_ID());
        this.setPP_Order_Node_ID(PP_Order_Node.get_ID());
    }
}

