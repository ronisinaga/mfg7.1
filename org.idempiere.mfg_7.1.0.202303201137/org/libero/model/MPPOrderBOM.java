/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.Query
 *  org.eevolution.model.MPPProductBOM
 */
package org.libero.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import org.compiere.model.Query;
import org.eevolution.model.MPPProductBOM;
import org.libero.model.MPPOrderBOMLine;
import org.libero.tables.X_PP_Order_BOM;

public class MPPOrderBOM
extends X_PP_Order_BOM {
    private static final long serialVersionUID = 1L;

    public MPPOrderBOM(Properties ctx, int PP_Order_BOM_ID, String trxName) {
        super(ctx, PP_Order_BOM_ID, trxName);
        if (PP_Order_BOM_ID == 0) {
            this.setProcessing(false);
        }
    }

    public MPPOrderBOM(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    public MPPOrderBOM(MPPProductBOM bom, int PP_Order_ID, String trxName) {
        this(bom.getCtx(), 0, trxName);
        this.setBOMType(bom.getBOMType());
        this.setBOMUse(bom.getBOMUse());
        this.setM_ChangeNotice_ID(bom.getM_ChangeNotice_ID());
        this.setHelp(bom.getHelp());
        this.setProcessing(bom.isProcessing());
        this.setHelp(bom.getHelp());
        this.setDescription(bom.getDescription());
        this.setM_AttributeSetInstance_ID(bom.getM_AttributeSetInstance_ID());
        this.setM_Product_ID(bom.getM_Product_ID());
        this.setName(bom.getName());
        this.setRevision(bom.getRevision());
        this.setValidFrom(bom.getValidFrom());
        this.setValidTo(bom.getValidTo());
        this.setValue(bom.getValue());
        this.setDocumentNo(bom.get_Value("DocumentNo").toString());
        this.setC_UOM_ID(bom.getC_UOM_ID());
        this.setPP_Order_ID(PP_Order_ID);
    }

    public MPPOrderBOMLine[] getLines() {
        String whereClause = "PP_Order_BOM_ID=?";
        List list = new Query(this.getCtx(), "PP_Order_BOMLine", whereClause, this.get_TrxName()).setParameters(new Object[]{this.get_ID()}).list();
        return list.toArray(new MPPOrderBOMLine[list.size()]);
    }

    protected boolean beforeDelete() {
        for (MPPOrderBOMLine line : this.getLines()) {
            line.deleteEx(false);
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("MPPOrderBOM[").append(this.get_ID()).append("-").append(this.getDocumentNo()).append("]");
        return sb.toString();
    }
}

