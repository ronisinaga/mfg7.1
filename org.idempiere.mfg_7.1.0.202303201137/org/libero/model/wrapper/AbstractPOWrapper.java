/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MAttachment
 *  org.compiere.model.PO
 */
package org.libero.model.wrapper;

import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.MAttachment;
import org.compiere.model.PO;

public abstract class AbstractPOWrapper {
    protected PO po;

    protected abstract PO receivePO(Properties var1, int var2, String var3, String var4);

    public AbstractPOWrapper(Properties ctx, int id, String trxName, String type) {
        this.po = this.receivePO(ctx, id, trxName, type);
    }

    public PO get() {
        return this.po;
    }

    public String toString() {
        return this.po.toString();
    }

    public boolean equals(Object cmp) {
        return this.po.equals(cmp);
    }

    public int compare(Object o1, Object o2) {
        return this.po.compare(o1, o2);
    }

    public String get_TableName() {
        return this.po.get_TableName();
    }

    public int getID() {
        return this.po.get_ID();
    }

    public int getIDOld() {
        return this.po.get_IDOld();
    }

    public Properties getCtx() {
        return this.po.getCtx();
    }

    public Object get_Value(int index) {
        return this.po.get_Value(index);
    }

    public Object get_Value(String columnName) {
        return this.po.get_Value(columnName);
    }

    public String get_ValueAsString(String variableName) {
        return this.po.get_ValueAsString(variableName);
    }

    public Object get_ValueOfColumn(int AD_Column_ID) {
        return this.po.get_ValueOfColumn(AD_Column_ID);
    }

    public Object get_ValueOld(int index) {
        return this.po.get_ValueOld(index);
    }

    public Object get_ValueOld(String columnName) {
        return this.po.get_ValueOld(columnName);
    }

    public boolean is_ValueChanged(int index) {
        return this.po.is_ValueChanged(index);
    }

    public boolean is_ValueChanged(String columnName) {
        return this.po.is_ValueChanged(columnName);
    }

    public Object get_ValueDifference(int index) {
        return this.po.get_ValueDifference(index);
    }

    public Object get_ValueDifference(String columnName) {
        return this.po.get_ValueDifference(columnName);
    }

    public void set_ValueOfColumn(int AD_Column_ID, Object value) {
        this.po.set_ValueOfColumn(AD_Column_ID, value);
    }

    public void set_CustomColumn(String columnName, Object value) {
        this.po.set_CustomColumn(columnName, value);
    }

    public int get_ColumnIndex(String columnName) {
        return this.po.get_ColumnIndex(columnName);
    }

    public boolean load(String trxName) {
        return this.po.load(trxName, new String[0]);
    }

    public int getAD_Client_ID() {
        return this.po.getAD_Client_ID();
    }

    public int getAD_Org_ID() {
        return this.po.getAD_Org_ID();
    }

    public void setIsActive(boolean active) {
        this.po.setIsActive(active);
    }

    public boolean isActive() {
        return this.po.isActive();
    }

    public Timestamp getCreated() {
        return this.po.getCreated();
    }

    public Timestamp getUpdated() {
        return this.po.getUpdated();
    }

    public int getCreatedBy() {
        return this.po.getCreatedBy();
    }

    public int getUpdatedBy() {
        return this.po.getUpdatedBy();
    }

    public boolean save() {
        return this.po.save();
    }

    public boolean save(String trxName) {
        return this.po.save(trxName);
    }

    public boolean is_Changed() {
        return this.po.is_Changed();
    }

    public String get_WhereClause(boolean withValues) {
        return this.po.get_WhereClause(withValues);
    }

    public boolean delete(boolean force) {
        return this.po.delete(force);
    }

    public boolean delete(boolean force, String trxName) {
        return this.po.delete(force, trxName);
    }

    public boolean lock() {
        return this.po.lock();
    }

    public boolean unlock(String trxName) {
        return this.po.unlock(trxName);
    }

    public void set_TrxName(String trxName) {
        this.po.set_TrxName(trxName);
    }

    public String get_TrxName() {
        return this.po.get_TrxName();
    }

    public MAttachment getAttachment() {
        return this.po.getAttachment();
    }

    public MAttachment getAttachment(boolean requery) {
        return this.po.getAttachment(requery);
    }

    public MAttachment createAttachment() {
        return this.po.createAttachment();
    }

    public boolean isAttachment(String extension) {
        return this.po.isAttachment(extension);
    }

    public byte[] getAttachmentData(String extension) {
        return this.po.getAttachmentData(extension);
    }

    public boolean isPdfAttachment() {
        return this.po.isPdfAttachment();
    }

    public byte[] getPdfAttachment() {
        return this.po.getPdfAttachment();
    }

    public void dump() {
        this.po.dump();
    }

    public void dump(int index) {
        this.po.dump(index);
    }
}

