/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.zkoss.util.Utils
 *  org.zkoss.zk.ui.Component
 *  org.zkoss.zk.ui.event.Event
 *  org.zkoss.zk.ui.event.EventListener
 *  org.zkoss.zul.Checkbox
 *  org.zkoss.zul.Radio
 *  org.zkoss.zul.Radiogroup
 *  org.zkoss.zul.Space
 *  org.zkoss.zul.Treecell
 *  org.zkoss.zul.Treeitem
 *  org.zkoss.zul.TreeitemRenderer
 *  org.zkoss.zul.Treerow
 */
package org.libero.bom.drop;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.libero.bom.drop.IRendererListener;
import org.libero.bom.drop.ISupportRadioNode;
import org.zkoss.util.Utils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Space;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

public class SupportRadioTreeitemRenderer
implements TreeitemRenderer<ISupportRadioNode>,
EventListener<Event> {
    public static final String PROPERTIE_NAME_RADIO_GROUP = "gp_name";
    public static final String DATA_ITEM = "REF_DATA_MODEL";
    public static final String TREE_ITEM = "REF_TREE_ITEM";
    private Boolean needFixIndent = null;
    private Map<String, String> mGroupID = new HashMap<String, String>();
    private EventListener<Event> listenerSelection;
    private IRendererListener rendererListener;
    public boolean isOpen = false;

    public void setCheckedListener(EventListener<Event> listenerSelection) {
        this.listenerSelection = listenerSelection;
    }

    public void setRendererListener(IRendererListener rendererListener) {
        this.rendererListener = rendererListener;
    }

    protected void fixIndent(ISupportRadioNode data, Treecell cell, boolean firstLevel) {
        if (this.needFixIndent == null) {
            int[] correctVersion;
            int[] currentVersion = Utils.parseVersion((String)"9.6.3");
            this.needFixIndent = Utils.compareVersion((int[])currentVersion, (int[])(correctVersion = Utils.parseVersion((String)"8.0.0"))) < 0;
        }
        if (!firstLevel && data.isLeaf() && this.needFixIndent.booleanValue()) {
            cell.appendChild((Component)new Space());
            cell.appendChild((Component)new Space());
        }
    }

    public void render(Treeitem item, ISupportRadioNode data, int index) throws Exception {
        Treerow row = new Treerow();
        Treecell cell = new Treecell();
        cell.setSpan(2);
        item.appendChild((Component)row);
        row.appendChild((Component)cell);
        item.setAttribute(DATA_ITEM, (Object)data);
        Checkbox selectionCtr = null;
        if (data.isRadio()) {
            Component radioGroup;
            Radio radioCtr = new Radio();
            Component groupContainer = null;
            groupContainer = item.getParentItem() != null ? item.getParentItem().getTreerow().getFirstChild() : item.getTree().getParent();
            String uniqueGroupName = String.valueOf(groupContainer.hashCode()) + data.getGroupName();
            String groupId = this.mGroupID.get(uniqueGroupName);
            if (groupId == null) {
                UUID groupUUID = UUID.randomUUID();
                groupId = groupUUID.toString();
                this.mGroupID.put(uniqueGroupName, groupId);
                data.setIsChecked(true);
            }
            if ((radioGroup = groupContainer.getFellowIfAny(groupId)) == null) {
                radioGroup = new Radiogroup();
                radioGroup.setId(groupId);
                groupContainer.appendChild(radioGroup);
            }
            radioCtr.setRadiogroup((Radiogroup)radioGroup);
            selectionCtr = radioCtr;
        } else {
            selectionCtr = new Checkbox();
        }
        if (this.rendererListener != null) {
            this.rendererListener.render(item, row, data, index);
        }
        selectionCtr.setAttribute(DATA_ITEM, (Object)data);
        selectionCtr.setAttribute(TREE_ITEM, (Object)item);
        selectionCtr.setLabel(data.getLabel());
        this.fixIndent(data, cell, item.getParentItem() == null);
        cell.appendChild((Component)selectionCtr);
        selectionCtr.setDisabled(data.isDisable());
        selectionCtr.setChecked(data.isChecked());
        selectionCtr.addEventListener("onCheck", (EventListener)this);
        item.setOpen(this.isOpen);
    }

    public void onEvent(Event event) throws Exception {
        this.defaultHandleEvent(event);
        if (this.listenerSelection != null) {
            this.listenerSelection.onEvent(event);
        }
    }

    public void defaultHandleEvent(Event event) throws Exception {
        Component targetObj = event.getTarget();
        if (!(targetObj instanceof Checkbox)) {
            return;
        }
        Checkbox chkBox = (Checkbox)targetObj;
        ISupportRadioNode dataItem = (ISupportRadioNode)chkBox.getAttribute(DATA_ITEM);
        dataItem.setIsChecked(chkBox.isChecked());
        Treeitem curentTreeItem = (Treeitem)chkBox.getAttribute(TREE_ITEM);
        if (this.rendererListener != null) {
            this.rendererListener.onchecked(curentTreeItem, dataItem, true);
        }
        if (targetObj instanceof Radio) {
            for (Treeitem nextSiblingTreeItem = (Treeitem)curentTreeItem.getNextSibling(); nextSiblingTreeItem != null; nextSiblingTreeItem = (Treeitem)nextSiblingTreeItem.getNextSibling()) {
                ISupportRadioNode dataNodeNext = (ISupportRadioNode)nextSiblingTreeItem.getAttribute(DATA_ITEM);
                if (!dataNodeNext.getGroupName().equals(dataItem.getGroupName())) continue;
                dataNodeNext.setIsChecked(false);
                if (this.rendererListener == null) continue;
                this.rendererListener.onchecked(nextSiblingTreeItem, dataNodeNext, false);
            }
            for (Treeitem prevSiblingTreeItem = (Treeitem)curentTreeItem.getPreviousSibling(); prevSiblingTreeItem != null; prevSiblingTreeItem = (Treeitem)prevSiblingTreeItem.getPreviousSibling()) {
                ISupportRadioNode dataNodePrev = (ISupportRadioNode)prevSiblingTreeItem.getAttribute(DATA_ITEM);
                if (!dataNodePrev.getGroupName().equals(dataItem.getGroupName())) continue;
                dataNodePrev.setIsChecked(false);
                if (this.rendererListener == null) continue;
                this.rendererListener.onchecked(prevSiblingTreeItem, dataNodePrev, false);
            }
        }
    }
}

