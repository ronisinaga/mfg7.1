/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.acct.Doc
 *  org.compiere.acct.Fact
 *  org.compiere.model.MAcctSchema
 *  org.compiere.util.Env
 */
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.compiere.acct.Doc;
import org.compiere.acct.Fact;
import org.compiere.model.MAcctSchema;
import org.compiere.util.Env;
import org.libero.model.MPPOrder;

public class Doc_PPOrder
extends Doc {
    public Doc_PPOrder(MAcctSchema ass, ResultSet rs, String trxName) {
        super(ass, MPPOrder.class, rs, "MOP", trxName);
    }

    protected String loadDocumentDetails() {
        MPPOrder order = (MPPOrder)this.getPO();
        this.setDateDoc(order.getDateOrdered());
        return "Y";
    }

    public BigDecimal getBalance() {
        BigDecimal retValue = Env.ZERO;
        return retValue;
    }

    public ArrayList<Fact> createFacts(MAcctSchema as) {
        return null;
    }
}

