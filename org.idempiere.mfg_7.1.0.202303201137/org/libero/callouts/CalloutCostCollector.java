/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.model.GridTabWrapper
 *  org.compiere.model.CalloutEngine
 *  org.compiere.model.GridField
 *  org.compiere.model.GridTab
 */
package org.libero.callouts;

import java.math.BigDecimal;
import java.util.Properties;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.libero.model.MPPCostCollector;
import org.libero.model.MPPOrder;
import org.libero.model.MPPOrderNode;
import org.libero.model.RoutingService;
import org.libero.model.RoutingServiceFactory;
import org.libero.tables.I_PP_Cost_Collector;

public class CalloutCostCollector
extends CalloutEngine {
    private MPPOrderNode m_node = null;

    public String order(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
        Integer PP_Order_ID = (Integer)value;
        if (PP_Order_ID == null || PP_Order_ID <= 0) {
            return "";
        }
        I_PP_Cost_Collector cc = (I_PP_Cost_Collector)GridTabWrapper.create((GridTab)mTab, I_PP_Cost_Collector.class);
        MPPOrder pp_order = new MPPOrder(ctx, (int)PP_Order_ID, null);
        MPPCostCollector.setPP_Order(cc, pp_order);
        return "";
    }

    public String node(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
        Integer PP_Order_Node_ID = (Integer)value;
        if (PP_Order_Node_ID == null || PP_Order_Node_ID <= 0) {
            return "";
        }
        I_PP_Cost_Collector cc = (I_PP_Cost_Collector)GridTabWrapper.create((GridTab)mTab, I_PP_Cost_Collector.class);
        MPPOrderNode node = this.getPP_Order_Node(ctx, PP_Order_Node_ID);
        cc.setS_Resource_ID(node.getS_Resource_ID());
        cc.setIsSubcontracting(node.isSubcontracting());
        cc.setMovementQty(node.getQtyToDeliver());
        this.duration(ctx, WindowNo, mTab, mField, value);
        return "";
    }

    public String duration(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
        I_PP_Cost_Collector cc = (I_PP_Cost_Collector)GridTabWrapper.create((GridTab)mTab, I_PP_Cost_Collector.class);
        if (cc.getPP_Order_Node_ID() <= 0) {
            return "";
        }
        RoutingService routingService = RoutingServiceFactory.get().getRoutingService(ctx);
        BigDecimal durationReal = routingService.estimateWorkingTime(cc);
        cc.setDurationReal(durationReal);
        return "";
    }

    private MPPOrderNode getPP_Order_Node(Properties ctx, int PP_Order_Node_ID) {
        if (this.m_node != null && this.m_node.get_ID() == PP_Order_Node_ID) {
            return this.m_node;
        }
        this.m_node = new MPPOrderNode(ctx, PP_Order_Node_ID, null);
        return this.m_node;
    }
}

