/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_AD_WF_Node
 *  org.compiere.model.I_AD_Workflow
 *  org.compiere.model.I_S_Resource
 *  org.compiere.model.MResourceAssignment
 */
package org.libero.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_S_Resource;
import org.compiere.model.MResourceAssignment;
import org.libero.model.MPPMRP;
import org.libero.model.MPPOrderNode;
import org.libero.tables.I_PP_Cost_Collector;
import org.libero.tables.I_PP_Order_Node;

public interface RoutingService {
    public BigDecimal estimateWorkingTime(I_AD_WF_Node var1);

    public BigDecimal estimateWorkingTime(I_PP_Order_Node var1, BigDecimal var2);

    public BigDecimal estimateWorkingTime(I_PP_Cost_Collector var1);

    public BigDecimal calculateDuration(MPPMRP var1, I_AD_Workflow var2, I_S_Resource var3, BigDecimal var4, Timestamp var5);

    public MResourceAssignment createResourceAssign(MPPMRP var1, Properties var2, BigDecimal var3, I_AD_WF_Node var4, Timestamp var5, Timestamp var6);

    public long calculateMillisFor(MPPOrderNode var1, long var2);

    public long calculateMillisFor(I_AD_WF_Node var1, long var2, BigDecimal var4);

    public BigDecimal getResourceBaseValue(int var1, I_PP_Cost_Collector var2);

    public BigDecimal getResourceBaseValue(int var1, I_AD_WF_Node var2);

    public Timestamp getStartAssignTime();
}

