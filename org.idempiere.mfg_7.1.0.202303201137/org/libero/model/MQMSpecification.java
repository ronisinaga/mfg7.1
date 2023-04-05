/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MAttribute
 *  org.compiere.model.MAttributeInstance
 *  org.compiere.model.MAttributeSet
 *  org.compiere.model.MAttributeSetInstance
 *  org.compiere.util.CPreparedStatement
 *  org.compiere.util.DB
 */
package org.libero.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MAttribute;
import org.compiere.model.MAttributeInstance;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.libero.model.MQMSpecificationLine;
import org.libero.tables.X_QM_Specification;

public class MQMSpecification
extends X_QM_Specification {
    private MQMSpecificationLine[] m_lines = null;

    public MQMSpecification(Properties ctx, int QM_Specification_ID, String trxName) {
        super(ctx, QM_Specification_ID, trxName);
    }

    public MQMSpecification(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    public MQMSpecificationLine[] getLines(String where) {
        if (this.m_lines != null) {
            return this.m_lines;
        }
        ArrayList<MQMSpecificationLine> list = new ArrayList<MQMSpecificationLine>();
        String sql = "SELECT * FROM QM_SpecificationLine WHERE QM_SpecificationLine_ID=? AND " + where + " ORDER BY Line";
        CPreparedStatement pstmt = null;
        try {
            pstmt = DB.prepareStatement((String)sql, (String)this.get_TrxName());
            pstmt.setInt(1, this.getQM_Specification_ID());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new MQMSpecificationLine(this.getCtx(), rs, this.get_TrxName()));
            }
            rs.close();
            pstmt.close();
            pstmt = null;
        }
        catch (Exception e) {
            this.log.log(Level.SEVERE, "getLines", (Throwable)e);
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
            pstmt = null;
        }
        catch (Exception exception) {
            pstmt = null;
        }
        this.m_lines = new MQMSpecificationLine[list.size()];
        list.toArray(this.m_lines);
        return this.m_lines;
    }

    public boolean isValid(int M_AttributeSetInstance_ID) {
        MAttributeSetInstance asi = new MAttributeSetInstance(this.getCtx(), M_AttributeSetInstance_ID, this.get_TrxName());
        MAttributeSet as = MAttributeSet.get((Properties)this.getCtx(), (int)asi.getM_AttributeSet_ID());
        MAttribute[] attributes = as.getMAttributes(false);
        for (int i = 0; i < attributes.length; ++i) {
            MAttributeInstance instance = attributes[i].getMAttributeInstance(M_AttributeSetInstance_ID);
            MQMSpecificationLine[] lines = this.getLines(" M_Attribute_ID=" + attributes[i].getM_Attribute_ID());
            int s = 0;
            while (s < lines.length) {
                Object objValue;
                MQMSpecificationLine line = lines[s];
                if ("N".equals(attributes[i].getAttributeValueType())) {
                    objValue = instance.getValueNumber();
                    if (!line.evaluate(objValue, instance.getValue())) {
                        // empty if block
                    }
                    return false;
                }
                objValue = instance.getValue();
                if (!line.evaluate(objValue, instance.getValue())) {
                    return false;
                }
                ++i;
            }
        }
        return true;
    }
}

