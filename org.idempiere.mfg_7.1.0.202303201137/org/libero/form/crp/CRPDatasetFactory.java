/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.I_S_Resource
 *  org.compiere.model.MProduct
 *  org.compiere.model.MResource
 *  org.compiere.model.MResourceType
 *  org.compiere.model.MUOM
 *  org.compiere.model.MUOMConversion
 *  org.compiere.util.DisplayType
 *  org.compiere.util.Env
 *  org.compiere.util.Language
 *  org.compiere.util.Msg
 *  org.compiere.util.TimeUtil
 *  org.jfree.data.category.CategoryDataset
 *  org.jfree.data.category.DefaultCategoryDataset
 */
package org.libero.form.crp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Properties;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import org.compiere.model.I_S_Resource;
import org.compiere.model.MProduct;
import org.compiere.model.MResource;
import org.compiere.model.MResourceType;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.libero.form.crp.CRPModel;
import org.libero.form.crp.DiagramTreeCellRenderer;
import org.libero.model.MPPOrder;
import org.libero.model.MPPOrderNode;
import org.libero.model.MPPOrderWorkflow;
import org.libero.model.reasoner.CRPReasoner;

public abstract class CRPDatasetFactory
extends CRPReasoner
implements CRPModel {
    protected JTree tree;
    protected DefaultCategoryDataset dataset;

    protected abstract BigDecimal convert(BigDecimal var1);

    public static CRPModel get(Timestamp start, Timestamp end, MResource r) {
        MResourceType t = MResourceType.get((Properties)Env.getCtx(), (int)r.getS_ResourceType_ID());
        final MUOM uom1 = MUOM.get((Properties)Env.getCtx(), (int)MUOM.getMinute_UOM_ID((Properties)Env.getCtx()));
        final MUOM uom2 = MUOM.get((Properties)Env.getCtx(), (int)t.getC_UOM_ID());
        CRPDatasetFactory factory = new CRPDatasetFactory(){

            @Override
            protected BigDecimal convert(BigDecimal minutes) {
                return MUOMConversion.convert((Properties)Env.getCtx(), (int)uom1.get_ID(), (int)uom2.get_ID(), (BigDecimal)minutes);
            }
        };
        super.generate(start, end, r);
        return factory;
    }

    private void generate(Timestamp start, Timestamp end, MResource r) {
        if (start == null || end == null || r == null) {
            return;
        }
        String labelActCap = Msg.translate((Properties)Env.getCtx(), (String)"DailyCapacity");
        String labelLoadAct = Msg.translate((Properties)Env.getCtx(), (String)"ActualLoad");
        SimpleDateFormat formatter = DisplayType.getDateFormat((int)16, (Language)Env.getLanguage((Properties)Env.getCtx()));
        BigDecimal dailyCapacity = this.getMaxRange(r);
        this.dataset = new DefaultCategoryDataset();
        HashMap<DefaultMutableTreeNode, String> names = new HashMap<DefaultMutableTreeNode, String>();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode((Object)r);
        names.put(root, this.getTreeNodeRepresentation(null, root, r));
        Timestamp dateTime = start;
        while (end.after(dateTime)) {
            String label = formatter.format(dateTime);
            names.putAll(this.addTreeNodes(dateTime, root, r));
            boolean available = this.isAvailable((I_S_Resource)r, dateTime);
            this.dataset.addValue((Number)(available ? dailyCapacity : BigDecimal.ZERO), (Comparable)((Object)labelActCap), (Comparable)((Object)label));
            this.dataset.addValue((Number)(available ? this.calculateLoad(dateTime, r, null) : BigDecimal.ZERO), (Comparable)((Object)labelLoadAct), (Comparable)((Object)label));
            dateTime = TimeUtil.addDays((Timestamp)dateTime, (int)1);
        }
        this.tree = new JTree(root);
        this.tree.setCellRenderer(new DiagramTreeCellRenderer(names));
    }

    @Override
    public BigDecimal calculateLoad(Timestamp dateTime, MResource r, String docStatus) {
        MResourceType t = MResourceType.get((Properties)Env.getCtx(), (int)r.getS_ResourceType_ID());
        MUOM.get((Properties)Env.getCtx(), (int)t.getC_UOM_ID());
        long millis = 0L;
        for (MPPOrderNode node : this.getPPOrderNodes(dateTime, (I_S_Resource)r)) {
            MPPOrder o;
            if (docStatus != null && !(o = new MPPOrder(node.getCtx(), node.getPP_Order_ID(), node.get_TrxName())).getDocStatus().equals(docStatus)) continue;
            millis += this.calculateMillisForDay(dateTime, node, t);
        }
        BigDecimal scale = new BigDecimal(60000);
        BigDecimal minutes = new BigDecimal(millis).divide(scale, 2, 4);
        return this.convert(minutes);
    }

    private Timestamp[] getDayBorders(Timestamp dateTime, MPPOrderNode node, MResourceType t) {
        Timestamp endDayTime = t.getDayEnd(dateTime);
        endDayTime = endDayTime.before(node.getDateFinishSchedule()) ? endDayTime : node.getDateFinishSchedule();
        Timestamp startDayTime = t.getDayStart(dateTime);
        startDayTime = startDayTime.after(node.getDateStartSchedule()) ? startDayTime : node.getDateStartSchedule();
        return new Timestamp[]{startDayTime, endDayTime};
    }

    private long calculateMillisForDay(Timestamp dateTime, MPPOrderNode node, MResourceType t) {
        Timestamp[] borders = this.getDayBorders(dateTime, node, t);
        return borders[1].getTime() - borders[0].getTime();
    }

    private HashMap<DefaultMutableTreeNode, String> addTreeNodes(Timestamp dateTime, DefaultMutableTreeNode root, MResource r) {
        HashMap<DefaultMutableTreeNode, String> names = new HashMap<DefaultMutableTreeNode, String>();
        DefaultMutableTreeNode parent = new DefaultMutableTreeNode(dateTime);
        names.put(parent, this.getTreeNodeRepresentation(null, parent, r));
        root.add(parent);
        for (MPPOrder order : this.getPPOrders(dateTime, (I_S_Resource)r)) {
            DefaultMutableTreeNode childOrder = new DefaultMutableTreeNode((Object)order);
            parent.add(childOrder);
            names.put(childOrder, this.getTreeNodeRepresentation(dateTime, childOrder, r));
            for (MPPOrderNode node : this.getPPOrderNodes(dateTime, (I_S_Resource)r)) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(node);
                childOrder.add(childNode);
                names.put(childNode, this.getTreeNodeRepresentation(dateTime, childNode, r));
            }
        }
        return names;
    }

    private String getTreeNodeRepresentation(Timestamp dateTime, DefaultMutableTreeNode node, MResource r) {
        String name = null;
        if (node.getUserObject() instanceof MResource) {
            MResource res = (MResource)node.getUserObject();
            name = res.getName();
        } else if (node.getUserObject() instanceof Timestamp) {
            Timestamp d = (Timestamp)node.getUserObject();
            SimpleDateFormat df = Env.getLanguage((Properties)Env.getCtx()).getDateFormat();
            name = df.format(d);
            if (!this.isAvailable((I_S_Resource)r, d)) {
                name = "{" + name + "}";
            }
        } else if (node.getUserObject() instanceof MPPOrder) {
            MPPOrder o = (MPPOrder)((Object)node.getUserObject());
            MProduct p = MProduct.get((Properties)Env.getCtx(), (int)o.getM_Product_ID());
            name = String.valueOf(o.getDocumentNo()) + " (" + p.getName() + ")";
        } else if (node.getUserObject() instanceof MPPOrderNode) {
            MPPOrderNode on = (MPPOrderNode)node.getUserObject();
            MPPOrderWorkflow owf = on.getMPPOrderWorkflow();
            MResourceType rt = MResourceType.get((Properties)Env.getCtx(), (int)r.getS_ResourceType_ID());
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            Timestamp[] interval = this.getDayBorders(dateTime, on, rt);
            name = String.valueOf(df.format(interval[0])) + " - " + df.format(interval[1]) + " " + on.getName() + " (" + owf.getName() + ")";
        }
        return name;
    }

    private BigDecimal getMaxRange(MResource r) {
        BigDecimal utilizationDec = r.getPercentUtilization().divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);
        int precision = 2;
        return r.getDailyCapacity().multiply(utilizationDec).setScale(precision, RoundingMode.HALF_UP);
    }

    @Override
    public CategoryDataset getDataset() {
        return this.dataset;
    }

    @Override
    public JTree getTree() {
        return this.tree;
    }
}

