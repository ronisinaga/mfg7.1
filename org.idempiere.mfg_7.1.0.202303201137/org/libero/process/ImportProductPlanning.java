/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MColumn
 *  org.compiere.model.MForecast
 *  org.compiere.model.MPeriod
 *  org.compiere.model.MProduct
 *  org.compiere.model.MProductPO
 *  org.compiere.model.MTable
 *  org.compiere.model.Query
 *  org.compiere.model.X_M_ForecastLine
 *  org.compiere.process.ProcessInfoParameter
 *  org.compiere.process.SvrProcess
 *  org.compiere.util.Msg
 *  org.eevolution.model.MPPProductPlanning
 *  org.eevolution.model.X_I_ProductPlanning
 */
package org.libero.process;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MColumn;
import org.compiere.model.MForecast;
import org.compiere.model.MPeriod;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPO;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.model.X_M_ForecastLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.eevolution.model.MPPProductPlanning;
import org.eevolution.model.X_I_ProductPlanning;

public class ImportProductPlanning
extends SvrProcess {
    private boolean isImported = false;
    private boolean p_DeleteOldImported = false;
    private boolean p_IsImportOnlyNoErrors = true;

    protected void prepare() {
        ProcessInfoParameter[] paramaters;
        ProcessInfoParameter[] arrprocessInfoParameter = paramaters = this.getParameter();
        int n = paramaters.length;
        for (int i = 0; i < n; ++i) {
            ProcessInfoParameter para = arrprocessInfoParameter[i];
            String name = para.getParameterName();
            if (para.getParameter() == null) continue;
            if (name.equals("IsImportOnlyNoErrors")) {
                this.p_IsImportOnlyNoErrors = "Y".equals(para.getParameter());
                continue;
            }
            if (name.equals("DeleteOldImported")) {
                this.p_DeleteOldImported = "Y".equals(para.getParameter());
                continue;
            }
            this.log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
    }

    protected String doIt() throws Exception {
        this.deleteImportedRecords();
        this.fillIDValues();
        this.importRecords();
        return "";
    }

    private void importRecords() {
        for (X_I_ProductPlanning ipp : this.getRecords(false, this.p_IsImportOnlyNoErrors)) {
            if (ipp.getM_Product_ID() > 0 && ipp.getS_Resource_ID() > 0 && ipp.getM_Warehouse_ID() > 0) {
                this.importProductPlanning(ipp);
            } else if (ipp.getForecastValue() == null || ipp.getM_Forecast_ID() == 0) {
                String error = "";
                if (ipp.getM_Product_ID() == 0) {
                    error = String.valueOf(error) + " @M_Product_ID@ @NotFound@ ,";
                }
                if (ipp.getS_Resource_ID() == 0) {
                    error = String.valueOf(error) + " @S_Resource_ID@ @NotFound@ ,";
                }
                if (ipp.getM_Warehouse_ID() == 0) {
                    error = String.valueOf(error) + " @M_Waehouse_ID@ @NotFound@";
                }
                ipp.setI_ErrorMsg(Msg.parseTranslation((Properties)this.getCtx(), (String)error));
                this.isImported = false;
                ipp.saveEx();
                return;
            }
            if (ipp.getForecastValue() == null) {
                this.isImported = true;
            } else if (ipp.getM_Forecast_ID() > 0 && ipp.getM_Warehouse_ID() > 0 && ipp.getM_Product_ID() > 0 && ipp.getQty().signum() > 0) {
                this.importForecast(ipp);
            } else {
                String error = "";
                if (ipp.getM_Forecast_ID() == 0) {
                    error = String.valueOf(error) + " @M_Forecast_ID@ @NotFound@ ,";
                }
                if (ipp.getM_Warehouse_ID() == 0) {
                    error = String.valueOf(error) + " @M_Warehouse_ID@ @NotFound@ ,";
                }
                if (ipp.getQty().signum() <= 0) {
                    error = String.valueOf(error) + " @Qty@ @Error@";
                }
                ipp.setI_ErrorMsg(Msg.parseTranslation((Properties)this.getCtx(), (String)error));
                this.isImported = false;
                ipp.saveEx();
                return;
            }
            if (!this.isImported) continue;
            ipp.setI_IsImported(true);
            ipp.setProcessed(true);
            ipp.saveEx();
        }
    }

    private void importProductPlanning(X_I_ProductPlanning ipp) {
        MPPProductPlanning pp = null;
        pp = ipp.getPP_Product_Planning_ID() > 0 ? new MPPProductPlanning(this.getCtx(), ipp.getPP_Product_Planning_ID(), this.get_TrxName()) : this.getProductPlanning(ipp);
        if (pp == null) {
            pp = new MPPProductPlanning(this.getCtx(), 0, this.get_TrxName());
        }
        this.fillValue(pp, ipp);
        if (ipp.getC_BPartner_ID() > 0 && ipp.getVendorProductNo() != null) {
            this.importPurchaseProductPlanning(ipp);
        }
    }

    private void importPurchaseProductPlanning(X_I_ProductPlanning ipp) {
        MProduct product = MProduct.get((Properties)this.getCtx(), (int)ipp.getM_Product_ID());
        if (product.isPurchased()) {
            StringBuffer whereClause = new StringBuffer();
            whereClause.append("M_Product_ID").append("=? AND ");
            whereClause.append("C_BPartner_ID").append("=?");
            MProductPO productPO = (MProductPO)new Query(this.getCtx(), "M_Product_PO", whereClause.toString(), this.get_TrxName()).setClient_ID().setParameters(new Object[]{ipp.getM_Product_ID(), ipp.getC_BPartner_ID()}).first();
            if (productPO == null) {
                productPO = new MProductPO(this.getCtx(), 0, this.get_TrxName());
            }
            productPO.setAD_Org_ID(ipp.getAD_Org_ID());
            productPO.setM_Product_ID(ipp.getM_Product_ID());
            productPO.setC_BPartner_ID(ipp.getC_BPartner_ID());
            productPO.setOrder_Min(ipp.getOrder_Min());
            productPO.setOrder_Pack(ipp.getOrder_Pack());
            productPO.setDeliveryTime_Promised(ipp.getDeliveryTime_Promised().intValue());
            productPO.setVendorProductNo(ipp.getVendorProductNo());
            productPO.saveEx();
        }
    }

    private void importForecast(X_I_ProductPlanning ipp) {
        if (ipp.getForecastValue() == null && ipp.getM_Forecast_ID() == 0) {
            ipp.setI_ErrorMsg(Msg.getMsg((Properties)this.getCtx(), (String)"@M_Forecast_ID@ @NotFound@"));
            ipp.saveEx();
            this.isImported = false;
            return;
        }
        new MForecast(this.getCtx(), ipp.getM_Forecast_ID(), this.get_TrxName());
        StringBuffer whereClause = new StringBuffer();
        whereClause.append("M_Forecast_ID").append("=? AND ").append("M_Product_ID").append("=? AND ").append("M_Warehouse_ID").append("=? AND ").append("DatePromised").append("=? AND ").append("SalesRep_ID").append("=?");
        X_M_ForecastLine forecastLine = null;
        forecastLine = ipp.getM_ForecastLine_ID() > 0 ? new X_M_ForecastLine(this.getCtx(), ipp.getM_ForecastLine_ID(), this.get_TrxName()) : (X_M_ForecastLine)new Query(this.getCtx(), "M_ForecastLine", whereClause.toString(), this.get_TrxName()).setClient_ID().setParameters(new Object[]{ipp.getM_Forecast_ID(), ipp.getM_Product_ID(), ipp.getM_Warehouse_ID(), ipp.getDatePromised(), ipp.getSalesRep_ID()}).first();
        if (forecastLine == null) {
            forecastLine = new X_M_ForecastLine(this.getCtx(), 0, this.get_TrxName());
        }
        forecastLine.setM_Forecast_ID(ipp.getM_Forecast_ID());
        forecastLine.setAD_Org_ID(ipp.getAD_Org_ID());
        forecastLine.setM_Product_ID(ipp.getM_Product_ID());
        forecastLine.setM_Warehouse_ID(ipp.getM_Warehouse_ID());
        forecastLine.setC_Period_ID(MPeriod.getC_Period_ID((Properties)this.getCtx(), (Timestamp)ipp.getDatePromised(), (int)ipp.getAD_Org_ID()));
        forecastLine.setDatePromised(ipp.getDatePromised());
        forecastLine.setSalesRep_ID(ipp.getSalesRep_ID());
        forecastLine.setQty(ipp.getQty());
        forecastLine.saveEx();
        ipp.setM_ForecastLine_ID(forecastLine.getM_ForecastLine_ID());
        ipp.saveEx();
        this.isImported = true;
    }

    private void fillValue(MPPProductPlanning pp, X_I_ProductPlanning ipp) {
        for (MColumn col : this.getProductPlanningColumns()) {
            if ("IsRequiredDRP".equals(col.getColumnName()) || "IsRequiredMRP".equals(col.getColumnName()) || "PP_Product_Planning_ID".equals(col.getColumnName()) || "Updated".equals(col.getColumnName()) || col.getAD_Reference_ID() == 13 || ipp.get_Value(col.getColumnName()) != null && pp.get_Value(col.getColumnName()).equals(ipp.get_Value(col.getColumnName()))) continue;
            pp.set_ValueOfColumn(col.getColumnName(), ipp.get_Value(col.getColumnName()));
        }
        pp.setIsRequiredDRP(false);
        pp.setIsRequiredMRP(false);
        String error = null;
        try {
            pp.saveEx();
            ipp.setPP_Product_Planning_ID(pp.getPP_Product_Planning_ID());
            ipp.saveEx();
        }
        catch (Exception e) {
            error = e.getMessage();
            ipp.setI_ErrorMsg(error);
            this.isImported = false;
            return;
        }
        this.isImported = true;
    }

    private MColumn[] getProductPlanningColumns() {
        return MTable.get((Properties)this.getCtx(), (String)"PP_Product_Planning").getColumns(false);
    }

    private MPPProductPlanning getProductPlanning(X_I_ProductPlanning ipp) {
        StringBuffer whereClause = new StringBuffer();
        ArrayList<Object> parameters = new ArrayList<Object>();
        MColumn[] cols = this.getProductPlanningColumns();
        int count = 0;
        MColumn[] arrmColumn = cols;
        int n = cols.length;
        for (int i = 0; i < n; ++i) {
            MColumn col = arrmColumn[i];
            if (!"AD_Org_ID".equals(col.getColumnName()) && !"S_Resource_ID".equals(col.getColumnName()) && !"M_Warehouse_ID".equals(col.getColumnName()) && !"M_Product_ID".equals(col.getColumnName())) continue;
            whereClause.append(col.getColumnName()).append("=?");
            parameters.add(ipp.get_Value(col.getColumnName()));
            if (count >= 3) continue;
            whereClause.append(" AND ");
            ++count;
        }
        return (MPPProductPlanning)new Query(this.getCtx(), "PP_Product_Planning", whereClause.toString(), this.get_TrxName()).setClient_ID().setParameters(parameters).firstOnly();
    }

    private void fillIDValues() {
        for (X_I_ProductPlanning ppi : this.getRecords(false, this.p_IsImportOnlyNoErrors)) {
            if (ppi.getC_BPartner_ID() == 0) {
                ppi.setC_BPartner_ID(this.getID("C_BPartner", "Value=?", new Object[]{ppi.getBPartner_Value()}));
            }
            if (ppi.getM_Product_ID() == 0) {
                ppi.setM_Product_ID(this.getID("M_Product", "Value=?", new Object[]{ppi.getProductValue()}));
            }
            if (ppi.getM_Warehouse_ID() == 0) {
                ppi.setM_Warehouse_ID(this.getID("M_Warehouse", "Value=?", new Object[]{ppi.getWarehouseValue()}));
            }
            if (ppi.getAD_Org_ID() == 0) {
                ppi.setAD_Org_ID(this.getID("AD_Org", "Value=?", new Object[]{ppi.getOrgValue()}));
            }
            if (ppi.getDD_NetworkDistribution_ID() == 0) {
                ppi.setDD_NetworkDistribution_ID(this.getID("DD_NetworkDistribution", "Value=?", new Object[]{ppi.getNetworkDistributionValue()}));
            }
            if (ppi.getPP_Product_BOM_ID() == 0) {
                ppi.setPP_Product_BOM_ID(this.getID("PP_Product_BOM", "Value=?", new Object[]{ppi.getProduct_BOM_Value()}));
            }
            if (ppi.getM_Forecast_ID() == 0) {
                ppi.setM_Forecast_ID(this.getID("M_Forecast", "Name=?", new Object[]{ppi.getForecastValue()}));
            }
            if (ppi.getS_Resource_ID() == 0) {
                ppi.setS_Resource_ID(this.getID("S_Resource", "Value=? AND ManufacturingResourceType=?", new Object[]{ppi.getResourceValue(), "PT"}));
            }
            ppi.saveEx();
        }
    }

    private int getID(String tableName, String whereClause, Object[] values) {
        return new Query(this.getCtx(), tableName, whereClause, this.get_TrxName()).setClient_ID().setParameters(values).firstId();
    }

    private Collection<X_I_ProductPlanning> getRecords(boolean imported, boolean isWithError) {
        StringBuffer whereClause = new StringBuffer("I_IsImported").append("=?");
        if (isWithError) {
            whereClause.append(" AND ").append("I_ErrorMsg").append(" IS NULL");
        }
        return new Query(this.getCtx(), "I_ProductPlanning", whereClause.toString(), this.get_TrxName()).setClient_ID().setParameters(new Object[]{imported}).list();
    }

    private void deleteImportedRecords() {
        if (this.p_DeleteOldImported) {
            for (X_I_ProductPlanning ipp : this.getRecords(false, this.p_IsImportOnlyNoErrors)) {
                ipp.deleteEx(true);
            }
        }
    }
}

