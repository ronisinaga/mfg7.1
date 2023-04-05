/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.eevolution.model.I_PP_Product_BOM
 */
package org.libero.exceptions;

import java.sql.Timestamp;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.model.I_PP_Product_BOM;

public class BOMExpiredException
extends AdempiereException {
    private static final long serialVersionUID = -3084324343550833077L;

    public BOMExpiredException(I_PP_Product_BOM bom, Timestamp date) {
        super(BOMExpiredException.buildMessage(bom, date));
    }

    private static final String buildMessage(I_PP_Product_BOM bom, Timestamp date) {
        return "@NotValid@ @PP_Product_BOM_ID@:" + bom.getValue() + " - @Date@:" + date;
    }
}

