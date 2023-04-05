/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.adempiere.exceptions.FillMandatoryException
 *  org.compiere.model.MRefList
 *  org.compiere.model.PO
 *  org.compiere.model.Query
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.eevolution.model.MPPProductBOMLine
 */
package org.libero.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.MRefList;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.eevolution.model.MPPProductBOMLine;

public class ComponentChange
extends SvrProcess {
    private static final int ACTION_AD_Reference_ID = 53227;
    private static final String ACTION_Add = "A";
    private static final String ACTION_Deactivate = "D";
    private static final String ACTION_Expire = "E";
    private static final String ACTION_Replace = "R";
    private static final String ACTION_ReplaceAndExpire = "RE";
    private int p_M_Product_ID = 0;
    private Timestamp p_ValidTo = null;
    private Timestamp p_ValidFrom = null;
    private String p_Action;
    private int p_New_M_Product_ID = 0;
    private BigDecimal p_Qty = null;
    private int p_M_ChangeNotice_ID = 0;

    protected void prepare() {
        boolean morepara = false;
        for (ProcessInfoParameter para : this.getParameter()) {
            String name = para.getParameterName();
            if (para.getParameter() == null) continue;
            if (name.equals("M_Product_ID") && !morepara) {
                this.p_M_Product_ID = para.getParameterAsInt();
                morepara = true;
                continue;
            }
            if (name.equals("ValidTo")) {
                this.p_ValidTo = (Timestamp)para.getParameter();
                continue;
            }
            if (name.equals("ValidFrom")) {
                this.p_ValidFrom = (Timestamp)para.getParameter();
                continue;
            }
            if (name.equals("Action")) {
                this.p_Action = (String)para.getParameter();
                continue;
            }
            if (name.equals("M_Product_ID")) {
                this.p_New_M_Product_ID = para.getParameterAsInt();
                continue;
            }
            if (name.equals("Qty")) {
                this.p_Qty = (BigDecimal)para.getParameter();
                continue;
            }
            if (name.equals("M_ChangeNotice_ID")) {
                this.p_M_ChangeNotice_ID = para.getParameterAsInt();
                continue;
            }
            this.log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
    }

    protected String doIt() throws Exception {
        if (this.p_Action == null) {
            throw new FillMandatoryException(new String[]{"Action"});
        }
        ArrayList<Comparable<Integer>> params = new ArrayList<Comparable<Integer>>();
        StringBuffer whereClause = new StringBuffer();
        whereClause.append("M_Product_ID=?");
        params.add(Integer.valueOf(this.p_M_Product_ID));
        if (this.p_ValidTo != null) {
            whereClause.append(" AND TRUNC(ValidTo) <= ?");
            params.add(this.p_ValidTo);
        }
        if (this.p_ValidFrom != null) {
            whereClause.append(" AND TRUNC(ValidFrom) >= ?");
            params.add(this.p_ValidFrom);
        }
        List components = new Query(this.getCtx(), "PP_Product_BOMLine", whereClause.toString(), this.get_TrxName()).setParameters(params).list();
        for (MPPProductBOMLine bomline : components) {
            if (this.p_Action.equals(ACTION_Add)) {
                this.actionAdd(bomline, 0);
            } else if (this.p_Action.equals(ACTION_Deactivate)) {
                this.actionDeactivate(bomline);
            } else if (this.p_Action.equals(ACTION_Expire)) {
                this.actionExpire(bomline);
            } else if (this.p_Action.equals(ACTION_Replace)) {
                this.actionAdd(bomline, bomline.getLine() + 1);
                this.actionDeactivate(bomline);
            } else if (this.p_Action.equals(ACTION_ReplaceAndExpire)) {
                this.actionAdd(bomline, bomline.getLine() + 1);
                this.actionExpire(bomline);
            } else {
                throw new AdempiereException("Action not supported - " + this.p_Action);
            }
            this.addLog(MRefList.getListName((Properties)this.getCtx(), (int)53227, (String)this.p_Action));
        }
        return "@OK@";
    }

    protected void actionAdd(MPPProductBOMLine bomline, int line) {
        MPPProductBOMLine newbomline = new MPPProductBOMLine(this.getCtx(), 0, this.get_TrxName());
        MPPProductBOMLine.copyValues((PO)bomline, (PO)newbomline);
        newbomline.setIsActive(true);
        newbomline.setLine(line);
        newbomline.setM_ChangeNotice_ID(this.p_M_ChangeNotice_ID);
        newbomline.setM_Product_ID(this.p_New_M_Product_ID);
        if (this.p_Qty.signum() != 0) {
            newbomline.setQtyBOM(this.p_Qty);
        }
        newbomline.setValidFrom(newbomline.getUpdated());
        newbomline.saveEx();
    }

    protected void actionDeactivate(MPPProductBOMLine bomline) {
        bomline.setIsActive(false);
        bomline.setM_ChangeNotice_ID(this.p_M_ChangeNotice_ID);
        bomline.saveEx();
    }

    protected void actionExpire(MPPProductBOMLine bomline) {
        bomline.setIsActive(true);
        bomline.setValidTo(bomline.getUpdated());
        bomline.setM_ChangeNotice_ID(this.p_M_ChangeNotice_ID);
        bomline.saveEx();
    }
}

