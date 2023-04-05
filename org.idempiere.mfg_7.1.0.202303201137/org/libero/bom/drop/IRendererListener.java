/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.zkoss.zul.Treeitem
 *  org.zkoss.zul.Treerow
 */
package org.libero.bom.drop;

import org.libero.bom.drop.ISupportRadioNode;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

public interface IRendererListener {
    public void render(Treeitem var1, Treerow var2, ISupportRadioNode var3, int var4);

    public void onchecked(Treeitem var1, ISupportRadioNode var2, boolean var3);
}

