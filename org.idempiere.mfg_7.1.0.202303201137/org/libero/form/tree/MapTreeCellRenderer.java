/*
 * Decompiled with CFR 0.150.
 */
package org.libero.form.tree;

import java.awt.Component;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public abstract class MapTreeCellRenderer
extends DefaultTreeCellRenderer {
    private HashMap<Object, Object> map = new HashMap();

    protected abstract ImageIcon getIcon(Object var1);

    public MapTreeCellRenderer(HashMap<?, ?> map) {
        this.map.putAll(map);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        String name = (String)this.getMapping(value);
        this.setText(name);
        ImageIcon icon = this.getIcon(value);
        this.setIcon(icon);
        return this;
    }

    protected Object getMapping(Object value) {
        return this.map.get(value);
    }
}

