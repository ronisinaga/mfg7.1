/*
 * Decompiled with CFR 0.150.
 */
package org.libero.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.libero.tables.X_QM_SpecificationLine;

public class MQMSpecificationLine
extends X_QM_SpecificationLine {
    private static final long serialVersionUID = 1L;

    public MQMSpecificationLine(Properties ctx, int QM_SpecificationLine_ID, String trxName) {
        super(ctx, QM_SpecificationLine_ID, trxName);
    }

    public MQMSpecificationLine(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    public boolean evaluate(Object valueObj, String value1) {
        boolean result = false;
        result = valueObj instanceof Number ? this.compareNumber((Number)valueObj, value1, this.getValue()) : this.compareString(valueObj, value1, this.getValue());
        return result;
    }

    private boolean compareNumber(Number valueObj, String value1, String value2) {
        BigDecimal valueObjB = null;
        BigDecimal value1B = null;
        BigDecimal value2B = null;
        try {
            valueObjB = valueObj instanceof BigDecimal ? (BigDecimal)valueObj : (valueObj instanceof Integer ? new BigDecimal((Integer)valueObj) : new BigDecimal(String.valueOf(valueObj)));
        }
        catch (Exception e) {
            this.log.fine("compareNumber - valueObj=" + valueObj + " - " + e.toString());
            return this.compareString(valueObj, value1, value2);
        }
        try {
            value1B = new BigDecimal(value1);
        }
        catch (Exception e) {
            this.log.fine("compareNumber - value1=" + value1 + " - " + e.toString());
            return this.compareString(valueObj, value1, value2);
        }
        String op = this.getOperation();
        if ("==".equals(op)) {
            return valueObjB.compareTo(value1B) == 0;
        }
        if (">>".equals(op)) {
            return valueObjB.compareTo(value1B) > 0;
        }
        if (">=".equals(op)) {
            return valueObjB.compareTo(value1B) >= 0;
        }
        if ("<<".equals(op)) {
            return valueObjB.compareTo(value1B) < 0;
        }
        if ("<=".equals(op)) {
            return valueObjB.compareTo(value1B) <= 0;
        }
        if ("~~".equals(op)) {
            return valueObjB.compareTo(value1B) == 0;
        }
        if ("!=".equals(op)) {
            return valueObjB.compareTo(value1B) != 0;
        }
        if ("SQ".equals(op)) {
            throw new IllegalArgumentException("SQL not Implemented");
        }
        if ("AB".equals(op)) {
            if (valueObjB.compareTo(value1B) < 0) {
                return false;
            }
            try {
                value2B = new BigDecimal(String.valueOf(value2));
                return valueObjB.compareTo(value2B) <= 0;
            }
            catch (Exception e) {
                this.log.fine("compareNumber - value2=" + value2 + " - " + e.toString());
                return false;
            }
        }
        throw new IllegalArgumentException("Unknown Operation=" + op);
    }

    private boolean compareString(Object valueObj, String value1S, String value2S) {
        String valueObjS = String.valueOf(valueObj);
        String op = this.getOperation();
        if ("==".equals(op)) {
            return valueObjS.compareTo(value1S) == 0;
        }
        if (">>".equals(op)) {
            return valueObjS.compareTo(value1S) > 0;
        }
        if (">=".equals(op)) {
            return valueObjS.compareTo(value1S) >= 0;
        }
        if ("<<".equals(op)) {
            return valueObjS.compareTo(value1S) < 0;
        }
        if ("<=".equals(op)) {
            return valueObjS.compareTo(value1S) <= 0;
        }
        if ("~~".equals(op)) {
            return valueObjS.compareTo(value1S) == 0;
        }
        if ("!=".equals(op)) {
            return valueObjS.compareTo(value1S) != 0;
        }
        if ("SQ".equals(op)) {
            throw new IllegalArgumentException("SQL not Implemented");
        }
        if ("AB".equals(op)) {
            if (valueObjS.compareTo(value1S) < 0) {
                return false;
            }
            return valueObjS.compareTo(value2S) <= 0;
        }
        throw new IllegalArgumentException("Unknown Operation=" + op);
    }
}

