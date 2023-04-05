/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.zkoss.zul.AbstractTreeModel
 */
package org.libero.bom.drop;

import org.libero.bom.drop.ISupportRadioNode;
import org.zkoss.zul.AbstractTreeModel;

public class SupportRadioTreeModel
extends AbstractTreeModel<ISupportRadioNode> {
    private static final long serialVersionUID = -4260907076488563930L;

    public SupportRadioTreeModel(ISupportRadioNode root) {
        super((Object)root);
    }

    public boolean isLeaf(ISupportRadioNode node) {
        return node.isLeaf();
    }

    public ISupportRadioNode getChild(ISupportRadioNode parent, int index) {
        return parent.getChild(index);
    }

    public int getChildCount(ISupportRadioNode parent) {
        return parent.getChildCount();
    }
}

