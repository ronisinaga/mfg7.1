/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.compiere.acct.Doc
 *  org.compiere.acct.DocLine
 *  org.compiere.model.MAccount
 *  org.compiere.model.MAcctSchema
 *  org.compiere.model.MCostElement
 *  org.compiere.model.PO
 *  org.compiere.util.DB
 */
package org.compiere.acct;

import java.util.HashMap;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCostElement;
import org.compiere.model.PO;
import org.compiere.util.DB;

public class DocLine_CostCollector
extends DocLine {
    private static final HashMap<Integer, String> s_acctName = new HashMap();

    static {
        s_acctName.put(11, "P_WIP_Acct");
        s_acctName.put(12, "P_MethodChangeVariance_Acct");
        s_acctName.put(13, "P_UsageVariance_Acct");
        s_acctName.put(14, "P_RateVariance_Acct");
        s_acctName.put(15, "P_MixVariance_Acct");
        s_acctName.put(16, "P_FloorStock_Acct");
        s_acctName.put(17, "P_CostOfProduction_Acct");
        s_acctName.put(18, "P_Labor_Acct");
        s_acctName.put(19, "P_Burden_Acct");
        s_acctName.put(20, "P_OutsideProcessing_Acct");
        s_acctName.put(21, "P_Overhead_Acct");
        s_acctName.put(22, "P_Scrap_Acct");
    }

    public DocLine_CostCollector(PO po, Doc doc) {
        super(po, doc);
    }

    public MAccount getAccount(MAcctSchema as, MCostElement element) {
        int acctType;
        String costElementType = element.getCostElementType();
        if ("M".equals(costElementType)) {
            acctType = 3;
        } else if ("R".equals(costElementType)) {
            acctType = 18;
        } else if ("B".equals(costElementType)) {
            acctType = 19;
        } else if ("O".equals(costElementType)) {
            acctType = 21;
        } else if ("X".equals(costElementType)) {
            acctType = 20;
        } else {
            throw new AdempiereException("@NotSupported@ " + (Object)element);
        }
        return this.getAccount(acctType, as);
    }

    public MAccount getAccount(int AcctType, MAcctSchema as) {
        String acctName = s_acctName.get(AcctType);
        if (this.getM_Product_ID() == 0 || acctName == null) {
            return super.getAccount(AcctType, as);
        }
        return this.getAccount(acctName, as);
    }

    public MAccount getAccount(String acctName, MAcctSchema as) {
        String sql = " SELECT  COALESCE(pa." + acctName + ",pca." + acctName + ",asd." + acctName + ")" + " FROM M_Product p" + " INNER JOIN M_Product_Acct pa ON (pa.M_Product_ID=p.M_Product_ID)" + " INNER JOIN M_Product_Category_Acct pca ON (pca.M_Product_Category_ID=p.M_Product_Category_ID AND pca.C_AcctSchema_ID=pa.C_AcctSchema_ID)" + " INNER JOIN C_AcctSchema_Default asd ON (asd.C_AcctSchema_ID=pa.C_AcctSchema_ID)" + " WHERE pa.M_Product_ID=? AND pa.C_AcctSchema_ID=?";
        int validCombination_ID = DB.getSQLValueEx(null, (String)sql, (Object[])new Object[]{this.getM_Product_ID(), as.get_ID()});
        if (validCombination_ID <= 0) {
            return null;
        }
        return MAccount.get((Properties)as.getCtx(), (int)validCombination_ID);
    }
}

