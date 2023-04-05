/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 */
package org.libero.exceptions;

import org.adempiere.exceptions.AdempiereException;
import org.libero.model.MPPOrderNode;

public class ActivityProcessedException
extends AdempiereException {
    private static final long serialVersionUID = 1L;

    public ActivityProcessedException(MPPOrderNode activity) {
        super("Order Activity Already Processed - " + activity);
    }
}

