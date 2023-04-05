/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.DBException
 *  org.compiere.apps.form.GenForm
 *  org.compiere.minigrid.IDColumn
 *  org.compiere.minigrid.IMiniTable
 *  org.compiere.model.MAttributeSetInstance
 *  org.compiere.model.MProduct
 *  org.compiere.model.MStorageOnHand
 *  org.compiere.util.CLogger
 *  org.compiere.util.CPreparedStatement
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 *  org.compiere.util.KeyNamePair
 *  org.compiere.util.Msg
 */
package org.libero.form;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import org.adempiere.exceptions.DBException;
import org.compiere.apps.form.GenForm;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MProduct;
import org.compiere.model.MStorageOnHand;
import org.compiere.util.CLogger;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.libero.model.MPPOrder;
import org.libero.model.MPPOrderBOMLine;

public class OrderReceiptIssue
extends GenForm {
    private static CLogger log = CLogger.getCLogger(OrderReceiptIssue.class);
    String m_sql = "";
    private boolean m_isOnlyReceipt = false;
    private boolean m_OnlyIssue = false;
    protected boolean m_IsBackflush = false;
    protected Timestamp m_movementDate = null;
    protected BigDecimal m_orderedQty = Env.ZERO;
    protected BigDecimal m_DeliveredQty = Env.ZERO;
    protected BigDecimal m_toDeliverQty = Env.ZERO;
    protected BigDecimal m_scrapQty = Env.ZERO;
    protected BigDecimal m_rejectQty = Env.ZERO;
    protected BigDecimal m_openQty = Env.ZERO;
    protected BigDecimal m_qtyBatchs = Env.ZERO;
    protected BigDecimal m_qtyBatchSize = Env.ZERO;
    protected int m_M_AttributeSetInstance_ID = 0;
    protected int m_M_Locator_ID = 0;
    private int m_PP_Order_ID = 0;
    private MPPOrder m_PP_order = null;

    public void configureMiniTable(IMiniTable issue) {
        issue.addColumn("PP_Order_BOMLine_ID");
        issue.addColumn("IsCritical");
        issue.addColumn("Value");
        issue.addColumn("M_Product_ID");
        issue.addColumn("C_UOM_ID");
        issue.addColumn("M_AttributeSetInstance_ID");
        issue.addColumn("QtyRequired");
        issue.addColumn("QtyDelivered");
        issue.addColumn("QtyToDeliver");
        issue.addColumn("QtyScrap");
        issue.addColumn("QtyOnHand");
        issue.addColumn("QtyReserved");
        issue.addColumn("QtyAvailable");
        issue.addColumn("M_Locator_ID");
        issue.addColumn("M_Warehouse_ID");
        issue.addColumn("QtyBOM");
        issue.addColumn("IsQtyPercentage");
        issue.addColumn("QtyBatch");
        issue.setMultiSelection(true);
        issue.setColumnClass(0, IDColumn.class, false, " ");
        issue.setColumnClass(1, Boolean.class, true, Msg.translate((Properties)Env.getCtx(), (String)"IsCritical"));
        issue.setColumnClass(2, String.class, true, Msg.translate((Properties)Env.getCtx(), (String)"Value"));
        issue.setColumnClass(3, KeyNamePair.class, true, Msg.translate((Properties)Env.getCtx(), (String)"M_Product_ID"));
        issue.setColumnClass(4, KeyNamePair.class, true, Msg.translate((Properties)Env.getCtx(), (String)"C_UOM_ID"));
        issue.setColumnClass(5, String.class, true, Msg.translate((Properties)Env.getCtx(), (String)"M_AttributeSetInstance_ID"));
        issue.setColumnClass(6, BigDecimal.class, true, Msg.translate((Properties)Env.getCtx(), (String)"QtyRequired"));
        issue.setColumnClass(7, BigDecimal.class, true, Msg.translate((Properties)Env.getCtx(), (String)"QtyDelivered"));
        issue.setColumnClass(8, BigDecimal.class, false, Msg.translate((Properties)Env.getCtx(), (String)"QtyToDeliver"));
        issue.setColumnClass(9, BigDecimal.class, false, Msg.translate((Properties)Env.getCtx(), (String)"QtyScrap"));
        issue.setColumnClass(10, BigDecimal.class, true, Msg.translate((Properties)Env.getCtx(), (String)"QtyOnHand"));
        issue.setColumnClass(11, BigDecimal.class, true, Msg.translate((Properties)Env.getCtx(), (String)"QtyReserved"));
        issue.setColumnClass(12, BigDecimal.class, true, Msg.translate((Properties)Env.getCtx(), (String)"QtyAvailable"));
        issue.setColumnClass(13, String.class, true, Msg.translate((Properties)Env.getCtx(), (String)"M_Locator_ID"));
        issue.setColumnClass(14, KeyNamePair.class, true, Msg.translate((Properties)Env.getCtx(), (String)"M_Warehouse_ID"));
        issue.setColumnClass(15, BigDecimal.class, true, Msg.translate((Properties)Env.getCtx(), (String)"QtyBom"));
        issue.setColumnClass(16, Boolean.class, true, Msg.translate((Properties)Env.getCtx(), (String)"IsQtyPercentage"));
        issue.setColumnClass(17, BigDecimal.class, true, Msg.translate((Properties)Env.getCtx(), (String)"QtyBatch"));
        issue.autoSize();
        issue.setRowCount(0);
        this.m_sql = "SELECT obl.PP_Order_BOMLine_ID,obl.IsCritical,p.Value,obl.M_Product_ID,p.Name,p.C_UOM_ID,u.Name,obl.QtyRequired,obl.QtyReserved,bomQtyAvailable(obl.M_Product_ID,obl.M_Warehouse_ID,0 ) AS QtyAvailable,bomQtyOnHand(obl.M_Product_ID,obl.M_Warehouse_ID,0) AS QtyOnHand,p.M_Locator_ID,obl.M_Warehouse_ID,w.Name,obl.QtyBom,obl.isQtyPercentage,obl.QtyBatch,obl.ComponentType,obl.QtyRequired - QtyDelivered AS QtyOpen,obl.QtyDelivered FROM PP_Order_BOMLine obl INNER JOIN M_Product p ON (obl.M_Product_ID = p.M_Product_ID)  INNER JOIN C_UOM u ON (p.C_UOM_ID = u.C_UOM_ID)  INNER JOIN M_Warehouse w ON (w.M_Warehouse_ID = obl.M_Warehouse_ID)  WHERE obl.PP_Order_ID = ? ORDER BY obl.Line";
    }

    private String createHTMLTable(String[][] table) {
        StringBuffer html = new StringBuffer("<table width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">");
        for (int i = 0; i < table.length; ++i) {
            if (table[i] == null) continue;
            html.append("<tr>");
            for (int j = 0; j < table[i].length; ++j) {
                html.append("<td>");
                if (table[i][j] != null) {
                    html.append(table[i][j]);
                }
                html.append("</td>");
            }
            html.append("</tr>");
        }
        html.append("</table>");
        return html.toString();
    }

    public void createIssue(MPPOrder order, IMiniTable issue) {
        int i;
        Timestamp movementDate;
        Timestamp minGuaranteeDate = movementDate = this.getMovementDate();
        ArrayList[][] m_issue = new ArrayList[issue.getRowCount()][1];
        int row = 0;
        for (i = 0; i < issue.getRowCount(); ++i) {
            ArrayList<Object> data = new ArrayList<Object>();
            IDColumn id = (IDColumn)issue.getValueAt(i, 0);
            KeyNamePair key = new KeyNamePair(id.getRecord_ID().intValue(), id.isSelected() ? "Y" : "N");
            data.add((Object)key);
            data.add(issue.getValueAt(i, 1));
            data.add(issue.getValueAt(i, 2));
            data.add(issue.getValueAt(i, 3));
            data.add(this.getValueBigDecimal(issue, i, 8));
            data.add(this.getValueBigDecimal(issue, i, 9));
            m_issue[row][0] = data;
            ++row;
        }
        MPPOrder.isQtyAvailable(order, m_issue, minGuaranteeDate);
        for (i = 0; i < m_issue.length; ++i) {
            MStorageOnHand[] storages;
            KeyNamePair key = (KeyNamePair)m_issue[i][0].get(0);
            boolean isSelected = key.getName().equals("Y");
            if (key == null || !isSelected) continue;
            Boolean cfr_ignored_0 = (Boolean)m_issue[i][0].get(1);
            String value = (String)m_issue[i][0].get(2);
            KeyNamePair productkey = (KeyNamePair)m_issue[i][0].get(3);
            int M_Product_ID = productkey.getKey();
            MPPOrderBOMLine orderbomLine = null;
            int PP_Order_BOMLine_ID = 0;
            int M_AttributeSetInstance_ID = 0;
            BigDecimal qtyToDeliver = (BigDecimal)m_issue[i][0].get(4);
            BigDecimal qtyScrapComponent = (BigDecimal)m_issue[i][0].get(5);
            System.out.println("fsafaf" + qtyToDeliver);
            MProduct product = MProduct.get((Properties)order.getCtx(), (int)M_Product_ID);
            System.out.println("prdbom" + product.getName());
            System.out.println("attribute" + Integer.valueOf(key.getKey()));
            if (product == null || product.get_ID() == 0 || !product.isStocked()) continue;
            if (DB.getSQLValueString((String)order.get_TrxName(), (String)("select coalesce(mpc.costingmethod,'') from M_Product_Category_Acct mpc where mpc.m_product_category_id = " + product.getM_Product_Category_ID()), (Object[])new Object[0]).equals("F") && value == null && isSelected) {
                M_AttributeSetInstance_ID = key.getKey();
                orderbomLine = MPPOrderBOMLine.forM_Product_ID(Env.getCtx(), order.get_ID(), M_Product_ID, order.get_TrxName());
                if (orderbomLine != null) {
                    PP_Order_BOMLine_ID = orderbomLine.get_ID();
                }
                System.out.println("sfafaf" + orderbomLine);
                storages = MPPOrder.getStorages(Env.getCtx(), M_Product_ID, order.getM_Warehouse_ID(), M_AttributeSetInstance_ID, minGuaranteeDate, order.get_TrxName());
                MPPOrder.createIssue(order, PP_Order_BOMLine_ID, movementDate, qtyToDeliver, qtyScrapComponent, Env.ZERO, storages, false);
            }
            if (DB.getSQLValueString((String)order.get_TrxName(), (String)("select coalesce(mpc.costingmethod,'') from M_Product_Category_Acct mpc where mpc.m_product_category_id = " + product.getM_Product_Category_ID()), (Object[])new Object[0]).equals("F") || value == null || !isSelected) continue;
            PP_Order_BOMLine_ID = key.getKey();
            if (PP_Order_BOMLine_ID > 0) {
                orderbomLine = new MPPOrderBOMLine(order.getCtx(), PP_Order_BOMLine_ID, order.get_TrxName());
                M_AttributeSetInstance_ID = orderbomLine.getM_AttributeSetInstance_ID();
            }
            System.out.println("sfafaf2" + orderbomLine);
            storages = MPPOrder.getStorages(Env.getCtx(), M_Product_ID, order.getM_Warehouse_ID(), M_AttributeSetInstance_ID, minGuaranteeDate, order.get_TrxName());
            MPPOrder.createIssue(order, PP_Order_BOMLine_ID, movementDate, qtyToDeliver, qtyScrapComponent, Env.ZERO, storages, false);
        }
    }

    public void executeQuery(IMiniTable issue) {
        int row = 0;
        issue.setRowCount(row);
        CPreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            try {
                pstmt = DB.prepareStatement((String)"SELECT obl.PP_Order_BOMLine_ID,obl.IsCritical,p.Value,obl.M_Product_ID,p.Name,p.C_UOM_ID,u.Name,obl.QtyRequired,obl.QtyReserved,bomQtyAvailable(obl.M_Product_ID,obl.M_Warehouse_ID,0 ) AS QtyAvailable,bomQtyOnHand(obl.M_Product_ID,obl.M_Warehouse_ID,0) AS QtyOnHand,p.M_Locator_ID,obl.M_Warehouse_ID,w.Name,obl.QtyBom,obl.isQtyPercentage,obl.QtyBatch,obl.ComponentType,obl.QtyRequired - QtyDelivered AS QtyOpen,obl.QtyDelivered FROM PP_Order_BOMLine obl INNER JOIN M_Product p ON (obl.M_Product_ID = p.M_Product_ID)  INNER JOIN C_UOM u ON (p.C_UOM_ID = u.C_UOM_ID)  INNER JOIN M_Warehouse w ON (w.M_Warehouse_ID = obl.M_Warehouse_ID)  INNER JOIN M_Product_Category_Acct mpc on (mpc.M_Product_Category_ID = p.M_Product_Category_ID) WHERE obl.PP_Order_ID = ?   ORDER BY obl.Line", null);
                pstmt.setInt(1, this.getPP_Order_ID());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    issue.setRowCount(row + 1);
                    IDColumn id = new IDColumn(rs.getInt(1));
                    BigDecimal qtyBom = rs.getBigDecimal(15);
                    Boolean isQtyPercentage = rs.getString(16).equals("Y");
                    Boolean isCritical = rs.getString(2).equals("Y");
                    BigDecimal qtyBatch = rs.getBigDecimal(17);
                    BigDecimal qtyRequired = rs.getBigDecimal(8);
                    BigDecimal qtyOnHand = rs.getBigDecimal(11);
                    BigDecimal qtyOpen = rs.getBigDecimal(19);
                    BigDecimal qtyDelivered = rs.getBigDecimal(20);
                    String componentType = rs.getString(18);
                    BigDecimal toDeliverQty = this.getToDeliverQty();
                    BigDecimal openQty = this.getOpenQty();
                    BigDecimal scrapQty = this.getScrapQty();
                    BigDecimal componentToDeliverQty = Env.ZERO;
                    BigDecimal componentScrapQty = Env.ZERO;
                    BigDecimal componentQtyReq = Env.ZERO;
                    BigDecimal componentQtyToDel = Env.ZERO;
                    id.setSelected(this.isOnlyReceipt());
                    issue.setValueAt((Object)id, row, 0);
                    issue.setValueAt((Object)isCritical, row, 1);
                    issue.setValueAt((Object)rs.getString(3), row, 2);
                    issue.setValueAt((Object)new KeyNamePair(rs.getInt(4), rs.getString(5)), row, 3);
                    issue.setValueAt((Object)new KeyNamePair(rs.getInt(6), rs.getString(7)), row, 4);
                    issue.setValueAt((Object)qtyRequired, row, 6);
                    issue.setValueAt((Object)qtyDelivered, row, 7);
                    issue.setValueAt((Object)qtyOnHand, row, 10);
                    issue.setValueAt((Object)rs.getBigDecimal(9), row, 11);
                    issue.setValueAt((Object)rs.getBigDecimal(10), row, 12);
                    issue.setValueAt((Object)new KeyNamePair(rs.getInt(13), rs.getString(14)), row, 14);
                    issue.setValueAt((Object)qtyBom, row, 15);
                    issue.setValueAt((Object)isQtyPercentage, row, 16);
                    issue.setValueAt((Object)qtyBatch, row, 17);
                    if (componentType.equals("CO") || componentType.equals("PK")) {
                        id.setSelected(qtyOnHand.signum() > 0 && qtyRequired.signum() > 0);
                        issue.setValueAt((Object)id, row, 0);
                        if (isQtyPercentage.booleanValue()) {
                            BigDecimal qtyBatchPerc = qtyBatch.divide(Env.ONEHUNDRED, 8, RoundingMode.HALF_UP);
                            if (this.isBackflush()) {
                                if (qtyRequired.signum() == 0 || qtyOpen.signum() == 0) {
                                    componentToDeliverQty = Env.ZERO;
                                } else {
                                    componentToDeliverQty = toDeliverQty.multiply(qtyBatchPerc);
                                    if (qtyRequired.subtract(qtyDelivered).signum() < 0 | componentToDeliverQty.signum() == 0) {
                                        componentToDeliverQty = qtyRequired.subtract(qtyDelivered);
                                    }
                                }
                                if (componentToDeliverQty.signum() != 0) {
                                    componentQtyToDel = componentToDeliverQty.setScale(4, 4);
                                    issue.setValueAt((Object)componentToDeliverQty, row, 8);
                                }
                            } else {
                                componentToDeliverQty = qtyOpen;
                                if (componentToDeliverQty.signum() != 0) {
                                    componentQtyReq = openQty.multiply(qtyBatchPerc);
                                    componentQtyToDel = componentToDeliverQty.setScale(4, 4);
                                    issue.setValueAt((Object)componentToDeliverQty.setScale(8, 4), row, 8);
                                    issue.setValueAt((Object)openQty.multiply(qtyBatchPerc), row, 6);
                                }
                            }
                            if (scrapQty.signum() != 0) {
                                componentScrapQty = scrapQty.multiply(qtyBatchPerc);
                                if (componentScrapQty.signum() != 0) {
                                    issue.setValueAt((Object)componentScrapQty, row, 9);
                                }
                            } else {
                                issue.setValueAt((Object)componentScrapQty, row, 9);
                            }
                        } else {
                            if (this.isBackflush()) {
                                componentToDeliverQty = toDeliverQty.multiply(qtyBom);
                                if (componentToDeliverQty.signum() != 0) {
                                    componentQtyReq = toDeliverQty.multiply(qtyBom);
                                    componentQtyToDel = componentToDeliverQty;
                                    issue.setValueAt((Object)componentQtyReq, row, 6);
                                    issue.setValueAt((Object)componentToDeliverQty, row, 8);
                                }
                            } else {
                                componentToDeliverQty = qtyOpen;
                                if (componentToDeliverQty.signum() != 0) {
                                    componentQtyReq = openQty.multiply(qtyBom);
                                    componentQtyToDel = componentToDeliverQty;
                                    issue.setValueAt((Object)componentQtyReq, row, 6);
                                    issue.setValueAt((Object)componentToDeliverQty, row, 8);
                                }
                            }
                            if (scrapQty.signum() != 0) {
                                componentScrapQty = scrapQty.multiply(qtyBom);
                                if (componentScrapQty.signum() != 0) {
                                    issue.setValueAt((Object)componentScrapQty, row, 9);
                                }
                            } else {
                                issue.setValueAt((Object)componentScrapQty, row, 9);
                            }
                        }
                    } else if (componentType.equals("TL")) {
                        componentToDeliverQty = qtyBom;
                        if (componentToDeliverQty.signum() != 0) {
                            componentQtyReq = qtyBom;
                            componentQtyToDel = componentToDeliverQty;
                            issue.setValueAt((Object)qtyBom, row, 6);
                            issue.setValueAt((Object)componentToDeliverQty, row, 8);
                        }
                    } else {
                        issue.setValueAt((Object)Env.ZERO, row, 6);
                    }
                    ++row;
                    if (!this.isOnlyIssue() && !this.isBackflush()) continue;
                    int warehouse_id = rs.getInt(13);
                    int product_id = rs.getInt(4);
                    row += this.lotes(row, id, warehouse_id, product_id, componentQtyReq, componentQtyToDel, issue);
                }
            }
            catch (SQLException e) {
                throw new DBException((Exception)e);
            }
        }
        catch (Throwable throwable) {
            DB.close(rs, (Statement)pstmt);
            rs = null;
            pstmt = null;
            throw throwable;
        }
        DB.close((ResultSet)rs, (Statement)pstmt);
        rs = null;
        pstmt = null;
        issue.autoSize();
    }

    public String generateSummaryTable(IMiniTable issue, String productField, String uomField, String attribute, String toDeliverQty, String deliveredQtyField, String scrapQtyField, boolean isBackflush, boolean isOnlyIssue, boolean isOnlyReceipt) {
        String[][] table;
        StringBuffer iText = new StringBuffer();
        iText.append("<b>");
        iText.append(Msg.translate((Properties)Env.getCtx(), (String)"IsShipConfirm"));
        iText.append("</b>");
        iText.append("<br />");
        if (isOnlyReceipt || isBackflush) {
            table = new String[][]{{Msg.translate((Properties)Env.getCtx(), (String)"Name"), Msg.translate((Properties)Env.getCtx(), (String)"C_UOM_ID"), Msg.translate((Properties)Env.getCtx(), (String)"M_AttributeSetInstance_ID"), Msg.translate((Properties)Env.getCtx(), (String)"QtyToDeliver"), Msg.translate((Properties)Env.getCtx(), (String)"QtyDelivered"), Msg.translate((Properties)Env.getCtx(), (String)"QtyScrap")}, {productField, uomField, attribute, toDeliverQty, deliveredQtyField, scrapQtyField}};
            iText.append(this.createHTMLTable(table));
        }
        if (isBackflush || isOnlyIssue) {
            iText.append("<br /><br />");
            table = new ArrayList();
            table.add(new String[]{Msg.translate((Properties)Env.getCtx(), (String)"Value"), Msg.translate((Properties)Env.getCtx(), (String)"Name"), Msg.translate((Properties)Env.getCtx(), (String)"C_UOM_ID"), Msg.translate((Properties)Env.getCtx(), (String)"M_AttributeSetInstance_ID"), Msg.translate((Properties)Env.getCtx(), (String)"QtyToDeliver"), Msg.translate((Properties)Env.getCtx(), (String)"QtyDelivered"), Msg.translate((Properties)Env.getCtx(), (String)"QtyScrap")});
            block0: for (int i = 0; i < issue.getRowCount(); ++i) {
                IDColumn id = (IDColumn)issue.getValueAt(i, 0);
                if (id == null || !id.isSelected()) continue;
                KeyNamePair m_productkey = (KeyNamePair)issue.getValueAt(i, 3);
                int m_M_Product_ID = m_productkey.getKey();
                KeyNamePair m_uomkey = (KeyNamePair)issue.getValueAt(i, 4);
                if (issue.getValueAt(i, 5) == null) {
                    Timestamp m_movementDate;
                    Timestamp minGuaranteeDate = m_movementDate = this.getMovementDate();
                    MStorageOnHand[] storages = MPPOrder.getStorages(Env.getCtx(), m_M_Product_ID, this.getPP_Order().getM_Warehouse_ID(), 0, minGuaranteeDate, null);
                    BigDecimal todelivery = this.getValueBigDecimal(issue, i, 8);
                    BigDecimal scrap = this.getValueBigDecimal(issue, i, 9);
                    BigDecimal toIssue = todelivery.add(scrap);
                    MStorageOnHand[] arrmStorageOnHand = storages;
                    int n = storages.length;
                    for (int j = 0; j < n; ++j) {
                        MStorageOnHand storage = arrmStorageOnHand[j];
                        if (storage.getQtyOnHand().signum() == 0) continue;
                        BigDecimal issueact = toIssue;
                        if (issueact.compareTo(storage.getQtyOnHand()) > 0) {
                            issueact = storage.getQtyOnHand();
                        }
                        toIssue = toIssue.subtract(issueact);
                        String desc = new MAttributeSetInstance(Env.getCtx(), storage.getM_AttributeSetInstance_ID(), null).getDescription();
                        String[] row = new String[]{"", "", "", "", "0.00", "0.00", "0.00"};
                        row[0] = issue.getValueAt(i, 2) != null ? issue.getValueAt(i, 2).toString() : "";
                        row[1] = m_productkey.toString();
                        row[2] = m_uomkey != null ? m_uomkey.toString() : "";
                        row[3] = desc != null ? desc : "";
                        row[4] = issueact.setScale(2, 4).toString();
                        row[5] = this.getValueBigDecimal(issue, i, 7).setScale(2, 4).toString();
                        row[6] = this.getValueBigDecimal(issue, i, 9).toString();
                        table.add(row);
                        if (toIssue.signum() <= 0) continue block0;
                    }
                    continue;
                }
                String[] row = new String[]{"", "", "", "", "0.00", "0.00", "0.00"};
                row[0] = issue.getValueAt(i, 2) != null ? issue.getValueAt(i, 2).toString() : "";
                row[1] = m_productkey.toString();
                row[2] = m_uomkey != null ? m_uomkey.toString() : "";
                row[3] = issue.getValueAt(i, 5) != null ? issue.getValueAt(i, 5).toString() : "";
                row[4] = this.getValueBigDecimal(issue, i, 8).toString();
                row[5] = this.getValueBigDecimal(issue, i, 7).toString();
                row[6] = this.getValueBigDecimal(issue, i, 9).toString();
                table.add(row);
            }
            String[][] tableArray = (String[][])table.toArray((T[])new String[table.size()][]);
            iText.append(this.createHTMLTable(tableArray));
        }
        return iText.toString();
    }

    protected BigDecimal getDeliveredQty() {
        return this.m_DeliveredQty;
    }

    protected int getM_AttributeSetInstance_ID() {
        return this.m_M_AttributeSetInstance_ID;
    }

    protected int getM_Locator_ID() {
        return this.m_M_Locator_ID;
    }

    protected Timestamp getMovementDate() {
        return this.m_movementDate;
    }

    protected BigDecimal getOpenQty() {
        return this.m_openQty;
    }

    protected BigDecimal getOrderedQty() {
        return this.m_orderedQty;
    }

    protected MPPOrder getPP_Order() {
        int id = this.getPP_Order_ID();
        if (id <= 0) {
            this.m_PP_order = null;
            return null;
        }
        if (this.m_PP_order == null || this.m_PP_order.get_ID() != id) {
            this.m_PP_order = new MPPOrder(Env.getCtx(), id, null);
        }
        return this.m_PP_order;
    }

    protected int getPP_Order_ID() {
        return this.m_PP_Order_ID;
    }

    protected BigDecimal getQtyBatchs() {
        return this.m_qtyBatchs;
    }

    protected BigDecimal getQtyBatchSize() {
        return this.m_qtyBatchSize;
    }

    protected BigDecimal getRejectQty() {
        return this.m_rejectQty;
    }

    protected BigDecimal getScrapQty() {
        return this.m_scrapQty;
    }

    protected BigDecimal getToDeliverQty() {
        return this.m_toDeliverQty;
    }

    private BigDecimal getValueBigDecimal(IMiniTable issue, int row, int col) {
        BigDecimal bd = (BigDecimal)issue.getValueAt(row, col);
        return bd == null ? Env.ZERO : bd;
    }

    protected boolean isBackflush() {
        return this.m_IsBackflush;
    }

    protected boolean isOnlyIssue() {
        return this.m_OnlyIssue;
    }

    protected boolean isOnlyReceipt() {
        return this.m_isOnlyReceipt;
    }

    private int lotes(int row, IDColumn id, int Warehouse_ID, int M_Product_ID, BigDecimal qtyRequired, BigDecimal qtyToDelivery, IMiniTable issue) {
        int linesNo = 0;
        BigDecimal qtyRequiredActual = qtyRequired;
        CPreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            try {
                pstmt = DB.prepareStatement((String)"SELECT s.M_Product_ID , s.QtyOnHand, s.M_AttributeSetInstance_ID, p.Name, masi.Description, l.Value, w.Value, w.M_warehouse_ID,p.Value  FROM M_Storage s  INNER JOIN M_Product p ON (s.M_Product_ID = p.M_Product_ID)  INNER JOIN C_UOM u ON (u.C_UOM_ID = p.C_UOM_ID)  INNER JOIN M_AttributeSetInstance masi ON (masi.M_AttributeSetInstance_ID = s.M_AttributeSetInstance_ID)  INNER JOIN M_Warehouse w ON (w.M_Warehouse_ID = ?)  INNER JOIN M_Locator l ON(l.M_Warehouse_ID=w.M_Warehouse_ID and s.M_Locator_ID=l.M_Locator_ID)  WHERE s.M_Product_ID = ? and s.QtyOnHand > 0  and s.M_AttributeSetInstance_ID <> 0  ORDER BY s.Created ", null);
                pstmt.setInt(1, Warehouse_ID);
                pstmt.setInt(2, M_Product_ID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    issue.setRowCount(row + 1);
                    BigDecimal qtyOnHand = rs.getBigDecimal(2);
                    IDColumn id1 = new IDColumn(rs.getInt(3));
                    id1.setSelected(false);
                    issue.setValueAt((Object)id1, row, 0);
                    KeyNamePair productkey = new KeyNamePair(rs.getInt(1), rs.getString(4));
                    issue.setValueAt((Object)productkey, row, 3);
                    issue.setValueAt((Object)qtyOnHand, row, 10);
                    issue.setValueAt((Object)rs.getString(5), row, 5);
                    issue.setValueAt((Object)rs.getString(6), row, 13);
                    KeyNamePair m_warehousekey = new KeyNamePair(rs.getInt(8), rs.getString(7));
                    issue.setValueAt((Object)m_warehousekey, row, 14);
                    issue.setValueAt((Object)qtyRequired, row, 6);
                    issue.setValueAt((Object)Env.ZERO, row, 9);
                    if (qtyRequiredActual.compareTo(qtyOnHand) < 0) {
                        issue.setValueAt((Object)(qtyRequiredActual.signum() > 0 ? qtyRequiredActual : Env.ZERO), row, 6);
                    } else {
                        issue.setValueAt((Object)qtyOnHand, row, 6);
                    }
                    if (qtyRequiredActual.compareTo(qtyOnHand) < 0) {
                        issue.setValueAt((Object)(qtyRequiredActual.signum() > 0 ? qtyRequiredActual : Env.ZERO), row, 8);
                    } else {
                        issue.setValueAt((Object)qtyOnHand, row, 8);
                    }
                    qtyRequiredActual = qtyRequiredActual.subtract(qtyOnHand);
                    ++linesNo;
                    ++row;
                }
            }
            catch (SQLException e) {
                throw new DBException((Exception)e);
            }
        }
        catch (Throwable throwable) {
            DB.close(rs, (Statement)pstmt);
            rs = null;
            pstmt = null;
            throw throwable;
        }
        DB.close((ResultSet)rs, (Statement)pstmt);
        rs = null;
        pstmt = null;
        return linesNo;
    }

    public void saveSelection(IMiniTable miniTable) {
        log.info("");
        ArrayList<Integer> results = new ArrayList<Integer>();
        this.setSelection(null);
        int rows = miniTable.getRowCount();
        for (int i = 0; i < rows; ++i) {
            IDColumn id = (IDColumn)miniTable.getValueAt(i, 0);
            if (id == null || !id.isSelected()) continue;
            results.add(id.getRecord_ID());
        }
        if (results.size() == 0) {
            return;
        }
        log.config("Selected #" + results.size());
        this.setSelection(results);
    }

    protected void setDeliveredQty(BigDecimal qty) {
        this.m_DeliveredQty = qty;
    }

    protected void setIsBackflush(boolean IsBackflush) {
        this.m_IsBackflush = IsBackflush;
    }

    protected void setIsOnlyIssue(boolean onlyIssue) {
        this.m_OnlyIssue = onlyIssue;
    }

    protected void setIsOnlyReceipt(boolean isOnlyReceipt) {
        this.m_isOnlyReceipt = isOnlyReceipt;
    }

    protected void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
        this.m_M_AttributeSetInstance_ID = M_AttributeSetInstance_ID;
    }

    protected void setM_Locator_ID(int M_Locator_ID) {
        this.m_M_Locator_ID = M_Locator_ID;
    }

    protected void setMovementDate(Timestamp date) {
        this.m_movementDate = date;
    }

    protected void setOpenQty(BigDecimal qty) {
        this.m_openQty = qty;
    }

    protected void setOrderedQty(BigDecimal qty) {
        this.m_orderedQty = qty;
    }

    protected void setPP_Order_ID(int PP_Order_ID) {
        this.m_PP_Order_ID = PP_Order_ID;
    }

    protected void setQtyBatchs(BigDecimal qty) {
        this.m_qtyBatchs = qty;
    }

    protected void setQtyBatchSize(BigDecimal qty) {
        this.m_qtyBatchSize = qty;
    }

    protected void setRejectQty(BigDecimal qty) {
        this.m_rejectQty = qty;
    }

    protected void setScrapQty(BigDecimal qty) {
        this.m_scrapQty = qty;
    }

    protected void setToDeliverQty(BigDecimal qty) {
        this.m_toDeliverQty = qty;
    }

    public void showMessage(String message, boolean error) {
    }
}

