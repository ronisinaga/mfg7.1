/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MResource
 *  org.jfree.data.category.CategoryDataset
 */
package org.libero.form.crp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.swing.JTree;
import org.compiere.model.MResource;
import org.jfree.data.category.CategoryDataset;

public interface CRPModel {
    public JTree getTree();

    public CategoryDataset getDataset();

    public BigDecimal calculateLoad(Timestamp var1, MResource var2, String var3);
}

