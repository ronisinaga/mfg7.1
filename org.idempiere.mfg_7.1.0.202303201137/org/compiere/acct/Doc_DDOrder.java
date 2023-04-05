/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.acct.Doc
 *  org.compiere.acct.Fact
 *  org.compiere.model.MAcctSchema
 *  org.compiere.util.Env
 *  org.eevolution.model.MDDOrder
 */
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.compiere.acct.Doc;
import org.compiere.acct.Fact;
import org.compiere.model.MAcctSchema;
import org.compiere.util.Env;
import org.eevolution.model.MDDOrder;

public class Doc_DDOrder
extends Doc {
    public Doc_DDOrder(MAcctSchema ass, ResultSet rs, String trxName) {
        super(ass, MDDOrder.class, rs, "DOO", trxName);
    }

    protected String loadDocumentDetails() {
        MDDOrder order = (MDDOrder)this.getPO();
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

