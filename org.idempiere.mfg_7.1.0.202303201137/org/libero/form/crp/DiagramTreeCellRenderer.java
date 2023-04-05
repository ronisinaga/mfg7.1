/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MResource
 *  org.compiere.util.Env
 */
package org.libero.form.crp;

import java.awt.Component;
import java.awt.Graphics;
import java.util.Date;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import org.compiere.model.MResource;
import org.compiere.util.Env;
import org.libero.form.tree.MapTreeCellRenderer;
import org.libero.model.MPPOrder;
import org.libero.model.MPPOrderNode;

public class DiagramTreeCellRenderer
extends MapTreeCellRenderer {
    private static final long serialVersionUID = 1L;

    public DiagramTreeCellRenderer(HashMap<?, ?> map) {
        super(map);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        String name = (String)this.getMapping(value);
        ImageIcon icon = this.getIcon(value);
        if (this.isNotAvailable(name)) {
            final int x1 = this.getFontMetrics(this.getFont()).stringWidth(name) + icon.getIconWidth();
            JLabel l = new JLabel(name.substring(1, name.length() - 1), icon, 2){
                private static final long serialVersionUID = 1L;

                @Override
                public void paint(Graphics g) {
                    super.paint(g);
                    int y = this.getFont().getSize() / 2;
                    g.drawLine(0, y, x1, y);
                }
            };
            l.setFont(this.getFont());
            return l;
        }
        return c;
    }

    private boolean isNotAvailable(String value) {
        return value.startsWith("{") && value.endsWith("}");
    }

    @Override
    protected ImageIcon getIcon(Object value) {
        ImageIcon icon = null;
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        if (!(node.getUserObject() instanceof MResource)) {
            if (node.getUserObject() instanceof Date) {
                icon = Env.getImageIcon((String)"Calendar10.gif");
            } else if (!(node.getUserObject() instanceof MPPOrder)) {
                boolean cfr_ignored_0 = node.getUserObject() instanceof MPPOrderNode;
            }
        }
        return icon;
    }
}

