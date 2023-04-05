/*
 * Decompiled with CFR 0.150.
 */
package org.libero.bom.drop;

public interface ISupportRadioNode {
    public boolean isLeaf();

    public ISupportRadioNode getChild(int var1);

    public int getChildCount();

    public boolean isRadio();

    public String getGroupName();

    public String getLabel();

    public boolean isChecked();

    public boolean isDisable();

    public void setIsChecked(boolean var1);

    public void setIsDisable(boolean var1);
}

