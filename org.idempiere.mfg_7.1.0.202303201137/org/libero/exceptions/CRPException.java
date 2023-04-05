/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.compiere.model.I_S_Resource
 *  org.compiere.process.DocAction
 *  org.eevolution.model.I_PP_Order
 */
package org.libero.exceptions;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_S_Resource;
import org.compiere.process.DocAction;
import org.eevolution.model.I_PP_Order;
import org.libero.tables.I_PP_Order_Node;

public class CRPException
extends AdempiereException {
    private I_PP_Order order = null;
    private I_PP_Order_Node node = null;
    private I_S_Resource resource = null;

    public CRPException(String message) {
        super(message);
    }

    public CRPException(Exception e) {
        super((Throwable)e);
    }

    public CRPException setPP_Order(I_PP_Order order) {
        this.order = order;
        return this;
    }

    public CRPException setPP_Order_Node(I_PP_Order_Node node) {
        this.node = node;
        return this;
    }

    public CRPException setS_Resource(I_S_Resource resource) {
        this.resource = resource;
        return this;
    }

    public String getMessage() {
        String msg = super.getMessage();
        StringBuffer sb = new StringBuffer(msg);
        if (this.order != null) {
            String info = this.order instanceof DocAction ? ((DocAction)this.order).getSummary() : this.order.getDocumentNo() + "/" + this.order.getDatePromised();
            sb.append(" @PP_Order_ID@:").append(info);
        }
        if (this.node != null) {
            sb.append(" @PP_Order_Node_ID@:").append(this.node.getValue()).append("_").append(this.node.getName());
        }
        if (this.resource != null) {
            sb.append(" @S_Resource_ID@:").append(this.resource.getValue()).append("_").append(this.resource.getName());
        }
        return sb.toString();
    }
}

