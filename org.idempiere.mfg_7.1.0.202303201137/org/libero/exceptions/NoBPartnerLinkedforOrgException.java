/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.compiere.model.MOrg
 */
package org.libero.exceptions;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrg;

public class NoBPartnerLinkedforOrgException
extends AdempiereException {
    private static final long serialVersionUID = -8354155558569979580L;

    public NoBPartnerLinkedforOrgException(MOrg org) {
        super("@NotExistsBPLinkedforOrgError@ " + org.getName());
    }
}

