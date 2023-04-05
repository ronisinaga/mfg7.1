/*
 * Decompiled with CFR 0.150.
 */
package org.libero.form.tree;

import java.awt.Component;
import java.util.HashMap;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public abstract class CachableTreeCellRenderer
extends DefaultTreeCellRenderer {
    private boolean virtual;
    private HashMap cache;
    private CachableTreeCellRenderer complement;

    protected abstract void init(Object var1);

    public CachableTreeCellRenderer() {
        this(false);
    }

    public CachableTreeCellRenderer(boolean virtual) {
        this.virtual = virtual;
        this.cache = new HashMap();
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        String name = (String)this.getFromCache(value);
        if (name == null) {
            this.init(value);
            name = (String)this.getFromCache(value);
        }
        this.setName(name);
        return this;
    }

    public boolean isInitialized() {
        return !this.cache.isEmpty();
    }

    public void addToCache(Object key, Object value) {
        this.cache.put(key, value);
    }

    public Object getFromCache(Object key) {
        return this.cache.get(key);
    }

    public boolean isVirtual() {
        return this.virtual;
    }

    public void setVirtual(boolean on) {
        this.virtual = on;
    }
}

