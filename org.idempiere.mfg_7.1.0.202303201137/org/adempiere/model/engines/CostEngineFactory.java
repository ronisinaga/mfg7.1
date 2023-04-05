/*
 * Decompiled with CFR 0.150.
 */
package org.adempiere.model.engines;

import java.util.HashMap;
import org.adempiere.model.engines.CostEngine;

public class CostEngineFactory {
    private static final HashMap<Integer, CostEngine> s_engines = new HashMap();

    public static CostEngine getCostEngine(int AD_Client_ID) {
        CostEngine engine = s_engines.get(AD_Client_ID);
        if (engine == null && AD_Client_ID > 0) {
            engine = s_engines.get(0);
        }
        if (engine == null) {
            engine = new CostEngine();
            s_engines.put(AD_Client_ID, engine);
        }
        return engine;
    }

    public static void registerCostEngine(int AD_Client_ID, CostEngine engine) {
        s_engines.put(AD_Client_ID, engine);
    }
}

