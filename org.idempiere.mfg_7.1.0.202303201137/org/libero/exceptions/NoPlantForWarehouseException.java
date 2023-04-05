/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.compiere.model.MWarehouse
 *  org.compiere.util.Env
 */
package org.libero.exceptions;

import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MWarehouse;
import org.compiere.util.Env;

public class NoPlantForWarehouseException
extends AdempiereException {
    private static final long serialVersionUID = 4986043215550031772L;

    public NoPlantForWarehouseException(int M_Warehouse_ID) {
        super("@NoPlantForWarehouseException@ @M_Warehouse_ID@ : " + MWarehouse.get((Properties)Env.getCtx(), (int)M_Warehouse_ID).getName());
    }
}

