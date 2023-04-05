/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.adempiere.exceptions.AdempiereException
 *  org.compiere.model.MAttributeSetInstance
 *  org.compiere.model.MProduct
 *  org.compiere.model.MStorageOnHand
 *  org.compiere.model.MTable
 *  org.compiere.model.MTransaction
 *  org.compiere.model.MWarehouse
 *  org.compiere.model.PO
 *  org.compiere.model.Query
 *  org.compiere.util.CLogger
 *  org.compiere.util.DB
 *  org.compiere.util.Env
 */
package org.adempiere.model.engines;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.engines.CostEngineFactory;
import org.adempiere.model.engines.IDocumentLine;
import org.adempiere.model.engines.IInventoryAllocation;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MProduct;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MTable;
import org.compiere.model.MTransaction;
import org.compiere.model.MWarehouse;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class StorageEngine {
    protected static transient CLogger log = CLogger.getCLogger(StorageEngine.class);
    static BigDecimal qtyparsial = BigDecimal.ZERO;

    public static void createTransaction(IDocumentLine docLine, String MovementType, Timestamp MovementDate, BigDecimal Qty, boolean isReversal, int M_Warehouse_ID, int o_M_AttributeSetInstance_ID, int o_M_Warehouse_ID, boolean isSOTrx) {
        boolean incomingTrx;
        System.out.println("docline" + docLine);
        MProduct product = MProduct.get((Properties)docLine.getCtx(), (int)docLine.getM_Product_ID());
        boolean bl = incomingTrx = MovementType.charAt(1) == '+';
        if (product != null && product.isStocked()) {
            if (!isReversal) {
                StorageEngine.checkMaterialPolicy(docLine, MovementType, MovementDate, M_Warehouse_ID);
            }
            if (docLine.getM_AttributeSetInstance_ID() == 0) {
                System.out.println("product" + docLine.getM_Product_ID());
                IInventoryAllocation[] mas = StorageEngine.getMA(docLine);
                for (int j = 0; j < mas.length; ++j) {
                    IInventoryAllocation ma = mas[j];
                    BigDecimal QtyMA = ma.getMovementQty();
                    if (!incomingTrx) {
                        QtyMA = QtyMA.negate();
                    }
                    System.out.println("asfafagf" + QtyMA);
                    for (int i = 0; i < MStorageOnHand.getWarehouse((Properties)docLine.getCtx(), (int)o_M_Warehouse_ID, (int)product.getM_Product_ID(), (int)ma.getM_AttributeSetInstance_ID(), null, (boolean)true, (boolean)true, (int)docLine.getM_Locator_ID(), (String)docLine.get_TrxName(), (boolean)false).length; ++i) {
                        MStorageOnHand mStorageOnHand = MStorageOnHand.getWarehouse((Properties)docLine.getCtx(), (int)o_M_Warehouse_ID, (int)product.getM_Product_ID(), (int)ma.getM_AttributeSetInstance_ID(), null, (boolean)true, (boolean)true, (int)docLine.getM_Locator_ID(), (String)docLine.get_TrxName(), (boolean)false)[i];
                        qtyparsial = mStorageOnHand.getQtyOnHand();
                        if (!MStorageOnHand.add((Properties)docLine.getCtx(), (int)M_Warehouse_ID, (int)docLine.getM_Locator_ID(), (int)docLine.getM_Product_ID(), (int)ma.getM_AttributeSetInstance_ID(), (BigDecimal)Qty.negate(), (Timestamp)mStorageOnHand.getDateMaterialPolicy(), (String)docLine.get_TrxName())) {
                            throw new AdempiereException();
                        }
                        System.out.println("QtyMove" + Qty);
                        StorageEngine.create(docLine, MovementType, MovementDate, ma.getM_AttributeSetInstance_ID(), Qty.negate());
                    }
                }
            } else {
                if (!incomingTrx) {
                    Qty = Qty.negate();
                }
                Timestamp datematerialpolicy = DB.getSQLValueTS(null, (String)("select datematerialpolicy from m_storage where qtyonhand > 0 and m_product_id = " + docLine.getM_Product_ID() + " and m_locator_id = " + docLine.getM_Locator_ID() + " and M_AttributeSetInstance_ID = " + docLine.getM_AttributeSetInstance_ID()), (Object[])new Object[0]);
                if (!MStorageOnHand.add((Properties)docLine.getCtx(), (int)M_Warehouse_ID, (int)docLine.getM_Locator_ID(), (int)docLine.getM_Product_ID(), (int)docLine.getM_AttributeSetInstance_ID(), (BigDecimal)Qty, (Timestamp)datematerialpolicy, (String)docLine.get_TrxName())) {
                    throw new AdempiereException();
                }
                StorageEngine.create(docLine, MovementType, MovementDate, docLine.getM_AttributeSetInstance_ID(), Qty);
            }
        }
    }

    private static void checkMaterialPolicy(IDocumentLine line, String MovementType, Timestamp MovementDate, int M_Warehouse_ID) {
        StorageEngine.deleteMA(line);
        boolean incomingTrx = MovementType.charAt(1) == '+';
        MProduct product = MProduct.get((Properties)line.getCtx(), (int)line.getM_Product_ID());
        line.getM_Locator_ID();
        if (line.getM_AttributeSetInstance_ID() == 0) {
            if (incomingTrx) {
                MStorageOnHand[] storages;
                MAttributeSetInstance asi = null;
                MStorageOnHand[] arrmStorageOnHand = storages = MStorageOnHand.getWarehouse((Properties)line.getCtx(), (int)M_Warehouse_ID, (int)line.getM_Product_ID(), (int)0, null, (boolean)"F".equals(product.getMMPolicy()), (boolean)false, (int)line.getM_Locator_ID(), (String)line.get_TrxName());
                int n = storages.length;
                for (int i = 0; i < n; ++i) {
                    MStorageOnHand storage = arrmStorageOnHand[i];
                    if (storage.getQtyOnHand().signum() >= 0) continue;
                    asi = new MAttributeSetInstance(line.getCtx(), storage.getM_AttributeSetInstance_ID(), line.get_TrxName());
                    break;
                }
                if (asi == null) {
                    asi = MAttributeSetInstance.create((Properties)line.getCtx(), (MProduct)product, (String)line.get_TrxName());
                }
                line.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
                log.config("New ASI=" + line);
                StorageEngine.createMA(line, line.getM_AttributeSetInstance_ID(), line.getMovementQty());
            } else {
                String MMPolicy = product.getMMPolicy();
                Timestamp minGuaranteeDate = MovementDate;
                System.out.println("times" + minGuaranteeDate);
                MStorageOnHand[] storages = MStorageOnHand.getWarehouse((Properties)line.getCtx(), (int)M_Warehouse_ID, (int)line.getM_Product_ID(), (int)line.getM_AttributeSetInstance_ID(), (Timestamp)minGuaranteeDate, (boolean)"F".equals(MMPolicy), (boolean)true, (int)line.getM_Locator_ID(), (String)line.get_TrxName());
                BigDecimal qtyToDeliver = line.getMovementQty();
                MStorageOnHand[] arrmStorageOnHand = storages;
                int n = storages.length;
                for (int i = 0; i < n; ++i) {
                    MStorageOnHand storage = arrmStorageOnHand[i];
                    if (storage.getQtyOnHand().compareTo(qtyToDeliver) >= 0) {
                        StorageEngine.createMA(line, storage.getM_AttributeSetInstance_ID(), qtyToDeliver);
                        qtyToDeliver = Env.ZERO;
                    } else {
                        StorageEngine.createMA(line, storage.getM_AttributeSetInstance_ID(), storage.getQtyOnHand());
                        qtyToDeliver = qtyToDeliver.subtract(storage.getQtyOnHand());
                        log.fine("QtyToDeliver=" + qtyToDeliver);
                    }
                    if (qtyToDeliver.signum() == 0) break;
                }
                if (qtyToDeliver.signum() != 0) {
                    MAttributeSetInstance asi = MAttributeSetInstance.create((Properties)line.getCtx(), (MProduct)product, (String)line.get_TrxName());
                    StorageEngine.createMA(line, asi.getM_AttributeSetInstance_ID(), qtyToDeliver);
                }
            }
        } else if (!incomingTrx) {
            StorageEngine.createMA(line, line.getM_AttributeSetInstance_ID(), line.getMovementQty());
        }
        StorageEngine.save(line);
    }

    private static String getTableNameMA(IDocumentLine model) {
        return String.valueOf(model.get_TableName()) + "MA";
    }

    private static int deleteMA(IDocumentLine model) {
        String sql = "DELETE FROM " + StorageEngine.getTableNameMA(model) + " WHERE " + model.get_TableName() + "_ID=?";
        int no = DB.executeUpdateEx((String)sql, (Object[])new Object[]{model.get_ID()}, (String)model.get_TrxName());
        if (no > 0) {
            log.config("Delete old #" + no);
        }
        return no;
    }

    private static void saveMA(IInventoryAllocation ma) {
        ((PO)ma).saveEx();
    }

    private static void save(IDocumentLine line) {
        ((PO)line).saveEx(line.get_TrxName());
    }

    private static void create(IDocumentLine model, String MovementType, Timestamp MovementDate, int M_AttributeSetInstance_ID, BigDecimal Qty) {
        MTransaction mtrx = new MTransaction(model.getCtx(), model.getAD_Org_ID(), MovementType, model.getM_Locator_ID(), model.getM_Product_ID(), M_AttributeSetInstance_ID, Qty, MovementDate, model.get_TrxName());
        StorageEngine.setReferenceLine_ID((PO)mtrx, model);
        mtrx.saveEx(model.get_TrxName());
        CostEngineFactory.getCostEngine(model.getAD_Client_ID()).createCostDetail(model, mtrx);
    }

    private static IInventoryAllocation createMA(IDocumentLine model, int M_AttributeSetInstance_ID, BigDecimal MovementQty) {
        Properties ctx = model.getCtx();
        String tableName = StorageEngine.getTableNameMA(model);
        String trxName = model.get_TrxName();
        IInventoryAllocation ma = (IInventoryAllocation)MTable.get((Properties)ctx, (String)tableName).getPO(0, trxName);
        ma.setAD_Org_ID(model.getAD_Org_ID());
        StorageEngine.setReferenceLine_ID((PO)ma, model);
        ma.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
        ma.setMovementQty(MovementQty);
        StorageEngine.saveMA(ma);
        log.fine("##: " + ma);
        return ma;
    }

    private static IInventoryAllocation[] getMA(IDocumentLine model) {
        Properties ctx = model.getCtx();
        String IDColumnName = String.valueOf(model.get_TableName()) + "_ID";
        System.out.println("model" + model.get_TableName());
        String tableName = StorageEngine.getTableNameMA(model);
        String trxName = model.get_TrxName();
        String whereClause = String.valueOf(IDColumnName) + "=?";
        List list = new Query(ctx, tableName, whereClause, trxName).setParameters(new Object[]{model.get_ID()}).setOrderBy(IDColumnName).list();
        IInventoryAllocation[] arr = new IInventoryAllocation[list.size()];
        return list.toArray(arr);
    }

    private static void setReferenceLine_ID(PO model, IDocumentLine ref) {
        String refColumnName = String.valueOf(ref.get_TableName()) + "_ID";
        if (model.get_ColumnIndex(refColumnName) < 0) {
            throw new AdempiereException("Invalid inventory document line " + ref);
        }
        model.set_ValueOfColumn(refColumnName, (Object)ref.get_ID());
    }

    public static int getM_Locator_ID(Properties ctx, int M_Warehouse_ID, int M_Product_ID, int M_AttributeSetInstance_ID, BigDecimal Qty, String trxName) {
        int M_Locator_ID = MStorageOnHand.getM_Locator_ID((int)M_Warehouse_ID, (int)M_Product_ID, (int)M_AttributeSetInstance_ID, (BigDecimal)Qty, (String)trxName);
        if (M_Locator_ID == 0) {
            MWarehouse wh = MWarehouse.get((Properties)ctx, (int)M_Warehouse_ID);
            M_Locator_ID = wh.getDefaultLocator().getM_Locator_ID();
        }
        return M_Locator_ID;
    }
}

