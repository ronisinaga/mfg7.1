/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MAttribute
 *  org.compiere.model.MAttributeInstance
 *  org.compiere.model.MAttributeSet
 *  org.compiere.model.MAttributeSetInstance
 *  org.compiere.model.MAttributeValue
 *  org.compiere.model.MLocator
 *  org.compiere.model.MProduct
 *  org.compiere.model.MProject
 *  org.compiere.model.MStorageOnHand
 *  org.compiere.model.MStorageReservation
 *  org.compiere.model.MWarehouse
 *  org.compiere.util.Env
 *  org.compiere.util.Msg
 */
package org.libero.msg;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import org.compiere.model.MAttribute;
import org.compiere.model.MAttributeInstance;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MAttributeValue;
import org.compiere.model.MLocator;
import org.compiere.model.MProduct;
import org.compiere.model.MProject;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MStorageReservation;
import org.compiere.model.MWarehouse;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.libero.model.MPPOrder;
import org.libero.model.reasoner.StorageReasoner;
import org.libero.model.wrapper.BOMLineWrapper;
import org.libero.model.wrapper.BOMWrapper;

public class HTMLMessenger {
    protected final String PRODUCT_TOOLTIP = "<html><H1 align=\"CENTER\">" + Msg.translate((Properties)Env.getCtx(), (String)"M_Product_ID") + "</H1>" + "<table cellpadding=\"5\" cellspacing=\"5\">" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"Description") + ":</b></td><td>{0}</td></tr>" + "</table></html>";
    protected final String LENGTHTRANSFORM_INFO_PATTERN = "<html><table cellpadding=\"5\" cellspacing=\"5\"><tr><td><b>{0}</b></td></tr><tr><td>{1}</td></tr><tr><td>{2}</td></tr></table></html>";
    protected final String PP_ORDER_INFO_PATTERN = "<html><H1 align=\"CENTER\">" + Msg.translate((Properties)Env.getCtx(), (String)"PP_Order_ID") + "</H1>" + "<table cellpadding=\"5\" cellspacing=\"5\">" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"DocumentNo") + ":</b></td><td>{0}</td></tr>" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"DateStartSchedule") + ":</b></td><td>{1}</td></tr>" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"DateFinishSchedule") + ":</b></td><td>{2}</td></tr>" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"C_Project_ID") + ":</b></td><td>{3}</td></tr>" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"M_Product_ID") + ":</b></td><td>{4}</td></tr>" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"QtyOrdered") + ":</b></td><td>{5}</td></tr>" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"QtyDelivered") + ":</b></td><td>{6}</td></tr>" + "</table></html>";
    protected final String PP_ORDER_HEADER_INFO_PATTERN = "<html><H1 align=\"LEFT\">{0}</H1><table cellpadding=\"5\" cellspacing=\"5\"><tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"DocumentNo") + "</b></td>" + "<td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"DateStartSchedule") + "</b></td>" + "<td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"DateFinishSchedule") + "</b></td>" + "<td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"C_Project_ID") + "</b></td>" + "<td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"M_Product_ID") + "</b></td>" + "<td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"QtyOrdered") + "</b></td>" + "<td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"QtyDelivered") + "</b></td>" + "<tr>" + "</table></html>";
    protected final String PP_ORDER_LINE_INFO_PATTERN = "<html><table cellpadding=\"5\" cellspacing=\"5\"><tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><td>{6}</td></tr></table></html>";
    protected final String BOM_INFO_PATTERN = "<html><H1 align=\"CENTER\">" + Msg.translate((Properties)Env.getCtx(), (String)"PP_Product_BOM_ID") + "</H1>" + "<table cellpadding=\"5\" cellspacing=\"5\">" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"DocumentNo") + ":</b></td><td>{0}</td></tr>" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"PP_Product_BOM_ID") + ":</b></td><td>{1}</td></tr>" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"ValidFrom") + ":</b></td><td>{2} - {3}</td></tr>" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"Value") + ":</b></td><td>{4}</td></tr>" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"M_Product_ID") + ":</b></td><td>{5}</td></tr>" + "<tr><td></td><td>{6}</td></tr>" + "</table>" + "<p>{7}</p>" + "</html>";
    protected final String BOM_HEADER_INFO_PATTERN = "<table align=\"CENTER\" cellpadding=\"5\" cellspacing=\"5\"><tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"Line") + "</b></td>" + "<td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"Qty") + "</b></td>" + "<td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"M_Product_ID") + "</b></td>" + "<td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"M_AttributeSetInstance_ID") + "</b></td>" + "</tr>";
    protected final String BOM_LINE_INFO_PATTERN = "<tr><td align=RIGHT>{0}</td><td align=RIGHT>{1}</td><td>{2}</td><td>{3}</td></tr>";
    protected final String BOMLINE_INFO_PATTERN = "<html><H1 align=\"CENTER\">" + Msg.translate((Properties)Env.getCtx(), (String)"Line") + ":&nbsp;{0}</H1>" + "<table cellpadding=\"5\" cellspacing=\"5\">" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"ComponentType") + ":</b></td><td>{1}</td></tr>" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"ValidFrom") + ":</b></td><td>{2} - {3}</td></tr>" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"Qty") + ":</b></td><td>{4}</td></tr>" + "<tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"M_Product_ID") + ":</b></td><td>{5}</td></tr>" + "<tr><td></td><td>{6}</td></tr>" + "</table>" + "<p>{7}</p>" + "</html>";
    protected final String STORAGE_HEADER_INFO_PATTERN = "<table align=\"CENTER\" cellpadding=\"5\" cellspacing=\"5\"><tr><td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"M_Locator_ID") + "</b></td>" + "<td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"M_Warehouse_ID") + "</b></td>" + "<td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"QtyOnHand") + "</b></td>" + "<td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"QtyReserved") + "</b></td>" + "<td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"QtyOrdered") + "</b></td>" + "<td><b>" + Msg.translate((Properties)Env.getCtx(), (String)"QtyAvailable") + "</b></td>" + "</tr>";
    protected final String STORAGE_LINE_INFO_PATTERN = "<tr><td>{0}</td><td>{1}</td><td align=RIGHT>{2}</td><td align=RIGHT>{3}</td><td align=RIGHT>{4}</td><td align=RIGHT>{5}</td></tr>";
    protected final String STORAGE_SUM_LINE_INFO_PATTERN = "<tr><td></td><td></td><td align=RIGHT><hr size=\"1\" noshade=\"NOSHADE\">{0}</td><td align=RIGHT><hr size=\"1\" noshade=\"NOSHADE\">{1}</td><td align=RIGHT><hr size=\"1\" noshade=\"NOSHADE\">{2}</td><td align=RIGHT><hr size=\"1\" noshade=\"NOSHADE\">{3}</td></tr>";
    protected final String STORAGE_NOINVENTORY_INFO_PATTERN = "<tr><td align=\"CENTER\" colspan=\"6\">" + Msg.translate((Properties)Env.getCtx(), (String)Msg.getMsg((Properties)Env.getCtx(), (String)"NoQtyAvailable")) + "</td>" + "</tr>";
    protected final String STORAGE_FOOTER_INFO_PATTERN = "</table>";
    protected final String ATTRIBUTE_INFO_PATTERN = "{0}&nbsp;=&nbsp;<i>{1}</i>";

    public String getProductInfo(MProduct p) {
        Object[] obj = new Object[]{p.getDescription() == null ? "" : p.getDescription()};
        return MessageFormat.format(this.PRODUCT_TOOLTIP, obj);
    }

    public String getLengthTransformInfo(MProduct p, BigDecimal srcLength, BigDecimal tgtLength, BigDecimal pieces) {
        BigDecimal scrapLength = srcLength.subtract(tgtLength.multiply(pieces));
        Object[] obj = new Object[]{String.valueOf(p.getName()) + " (" + p.getValue() + ")", "1 x " + srcLength.setScale(2, 5) + " &#8594; " + pieces + " x " + tgtLength.setScale(2, 5), String.valueOf(Msg.translate((Properties)Env.getCtx(), (String)"Scrap")) + ": 1 x " + scrapLength.setScale(2, 5)};
        return MessageFormat.format("<html><table cellpadding=\"5\" cellspacing=\"5\"><tr><td><b>{0}</b></td></tr><tr><td>{1}</td></tr><tr><td>{2}</td></tr></table></html>", obj);
    }

    public String getMfcOrderInfo(MPPOrder o) {
        MProject pj = new MProject(Env.getCtx(), o.getC_Project_ID(), null);
        MProduct pd = new MProduct(Env.getCtx(), o.getM_Product_ID(), null);
        Object[] obj = new Object[]{o.getDocumentNo(), o.getDateStartSchedule(), o.getDateFinishSchedule(), String.valueOf(pj.getName() == null ? "-" : pj.getName()) + (pj.getValue() == null ? "" : " (" + pj.getValue() + ")"), String.valueOf(pd.getName()) + " (" + pd.getValue() + ")", o.getQtyOrdered(), o.getQtyDelivered()};
        return MessageFormat.format(this.PP_ORDER_INFO_PATTERN, obj);
    }

    public String getBOMLinesInfo(BOMLineWrapper[] lines) {
        MProduct p = null;
        MAttributeSetInstance asi = null;
        StringBuffer sb = new StringBuffer(this.BOM_HEADER_INFO_PATTERN);
        for (int i = 0; i < lines.length; ++i) {
            p = new MProduct(Env.getCtx(), lines[i].getM_Product_ID(), "M_Product");
            asi = new MAttributeSetInstance(Env.getCtx(), lines[i].getM_AttributeSetInstance_ID(), "M_AttributeSetInstance");
            Object[] obj = new Object[]{new Integer(lines[i].getPo()), lines[i].getQtyBOM(), p.getName(), this.getAttributeSetInstanceInfo(asi, true)};
            sb.append(MessageFormat.format("<tr><td align=RIGHT>{0}</td><td align=RIGHT>{1}</td><td>{2}</td><td>{3}</td></tr>", obj));
        }
        return sb.toString();
    }

    public String getBOMLineInfo(BOMLineWrapper mpbl) {
        SimpleDateFormat df = Env.getLanguage((Properties)Env.getCtx()).getDateFormat();
        MProduct p = new MProduct(Env.getCtx(), mpbl.getM_Product_ID(), "M_Product");
        MAttributeSetInstance asi = new MAttributeSetInstance(Env.getCtx(), mpbl.getM_AttributeSetInstance_ID(), "M_AttributeSetInstance");
        Object[] obj = new Object[]{new Integer(mpbl.getPo()), mpbl.getComponentType(), mpbl.getValidFrom() == null ? "" : df.format(mpbl.getValidFrom()), mpbl.getValidTo() == null ? "" : df.format(mpbl.getValidTo()), mpbl.getQtyBOM(), p.getName(), this.getAttributeSetInstanceInfo(asi, false), this.getStorageInfo(p, asi)};
        return MessageFormat.format(this.BOMLINE_INFO_PATTERN, obj);
    }

    public String getBOMInfo(BOMWrapper pb) {
        SimpleDateFormat df = Env.getLanguage((Properties)Env.getCtx()).getDateFormat();
        MProduct p = new MProduct(Env.getCtx(), pb.getM_Product_ID(), "M_Product");
        MAttributeSetInstance asi = new MAttributeSetInstance(Env.getCtx(), pb.getM_AttributeSetInstance_ID(), "M_AttributeSetInstance");
        Object[] obj = new Object[]{pb.getDocumentNo(), pb.getName(), pb.getValidFrom() == null ? "" : df.format(pb.getValidFrom()), pb.getValidTo() == null ? "" : df.format(pb.getValidTo()), pb.getValue(), p.getName(), this.getAttributeSetInstanceInfo(asi, false), this.getBOMLinesInfo(pb.getLines())};
        return MessageFormat.format(this.BOM_INFO_PATTERN, obj);
    }

    public String getAttributeSetInstanceInfo(MAttributeSetInstance asi, boolean singleRow) {
        new MAttributeSet(Env.getCtx(), asi.getM_AttributeSet_ID(), null);
        StorageReasoner mr = new StorageReasoner();
        int[] ids = mr.getAttributeIDs(asi);
        MAttributeInstance ai = null;
        MAttribute a = null;
        MAttributeValue av = null;
        StringBuffer sb = new StringBuffer();
        String value = null;
        Object[] obj = null;
        for (int i = 0; i < ids.length; ++i) {
            ai = new MAttributeInstance(Env.getCtx(), ids[i], asi.get_ID(), null, null);
            ai.load(null, new String[0]);
            a = new MAttribute(Env.getCtx(), ai.getM_Attribute_ID(), null);
            av = new MAttributeValue(Env.getCtx(), ai.getM_AttributeValue_ID(), null);
            if (ai.getValue() == null) {
                value = av.getValue();
            } else if ("N".equals(a.getAttributeValueType())) {
                BigDecimal number = ai.getValueNumber();
                value = number.setScale(2, 4).toString();
            } else {
                value = ai.getValue();
            }
            obj = new Object[]{a.getName(), value};
            sb.append(MessageFormat.format("{0}&nbsp;=&nbsp;<i>{1}</i>", obj));
            if (singleRow) {
                sb.append("&nbsp;");
                continue;
            }
            sb.append("<br>");
        }
        return sb.toString();
    }

    public String getStorageInfo(MProduct p, MAttributeSetInstance asi) {
        StorageReasoner mr = new StorageReasoner();
        int[] ids = mr.getPOIDs("M_Locator", null, null);
        MWarehouse warehouse = null;
        MStorageOnHand storage = null;
        MLocator locator = null;
        StringBuffer sb = new StringBuffer(this.STORAGE_HEADER_INFO_PATTERN);
        Object[] obj = null;
        BigDecimal sumQtyOnHand = BigDecimal.ZERO;
        BigDecimal sumQtyAvailable = BigDecimal.ZERO;
        int count = 0;
        for (int i = 0; i < ids.length; ++i) {
            storage = MStorageOnHand.get((Properties)Env.getCtx(), (int)ids[i], (int)p.get_ID(), (int)asi.get_ID(), null);
            if (storage == null) continue;
            ++count;
            warehouse = new MWarehouse(Env.getCtx(), storage.getM_Warehouse_ID(), null);
            locator = new MLocator(Env.getCtx(), storage.getM_Locator_ID(), null);
            BigDecimal available = MStorageReservation.getQtyAvailable((int)storage.getM_Warehouse_ID(), (int)p.get_ID(), (int)asi.get_ID(), null);
            sumQtyOnHand = sumQtyOnHand.add(storage.getQtyOnHand());
            sumQtyAvailable = sumQtyAvailable.add(available);
            obj = new Object[]{String.valueOf(locator.getX()) + " - " + locator.getY() + " - " + locator.getZ(), warehouse.getName(), storage.getQtyOnHand(), storage.getQtyOnHand().subtract(sumQtyAvailable), sumQtyAvailable};
            sb.append(MessageFormat.format("<tr><td>{0}</td><td>{1}</td><td align=RIGHT>{2}</td><td align=RIGHT>{3}</td><td align=RIGHT>{4}</td><td align=RIGHT>{5}</td></tr>", obj));
        }
        if (count > 1) {
            obj = new Object[]{sumQtyOnHand, storage.getQtyOnHand().subtract(sumQtyAvailable), sumQtyAvailable};
            sb.append(MessageFormat.format("<tr><td></td><td></td><td align=RIGHT><hr size=\"1\" noshade=\"NOSHADE\">{0}</td><td align=RIGHT><hr size=\"1\" noshade=\"NOSHADE\">{1}</td><td align=RIGHT><hr size=\"1\" noshade=\"NOSHADE\">{2}</td><td align=RIGHT><hr size=\"1\" noshade=\"NOSHADE\">{3}</td></tr>", obj));
        }
        double available = sumQtyAvailable.setScale(2, 4).doubleValue();
        if (count == 0 || available <= 0.0) {
            sb.append(MessageFormat.format(this.STORAGE_NOINVENTORY_INFO_PATTERN, obj));
        }
        sb.append("</table>");
        return sb.toString();
    }
}

