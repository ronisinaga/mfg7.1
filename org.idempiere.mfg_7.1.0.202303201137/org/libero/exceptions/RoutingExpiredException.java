/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.compiere.model.I_AD_Workflow
 */
package org.libero.exceptions;

import java.sql.Timestamp;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Workflow;

public class RoutingExpiredException
extends AdempiereException {
    private static final long serialVersionUID = -7522979292063177848L;

    public RoutingExpiredException(I_AD_Workflow wf, Timestamp date) {
        super(RoutingExpiredException.buildMessage(wf, date));
    }

    private static final String buildMessage(I_AD_Workflow wf, Timestamp date) {
        return "@NotValid@ @AD_Workflow_ID@:" + wf.getValue() + " - @Date@:" + date;
    }
}

