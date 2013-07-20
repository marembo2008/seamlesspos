/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales.facade;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.Batch;
import com.seamless.internal.facade.*;
import com.seamless.internal.sales.ItemOrder;
import com.seamless.internal.sales.Sale;
import com.seamless.internal.sales.SaleItem;
import com.seamless.internal.sales.util.SaleStatus;
import com.seamless.internal.sales.util.SalesReceipt;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marembo
 */
@Stateless
public class SaleFacade extends AbstractFacade<Sale> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;
  @EJB
  private BatchFacade batchFacade;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public SaleFacade() {
    super(Sale.class);
  }

  public Sale lastSale() {
    String sql = "SELECT * FROM Sale ORDER BY SALEDATE DESC, RECEIPTNUMBER DESC";
    try {
      return (Sale) getEntityManager()
              .createNativeQuery(sql, Sale.class)
              .setMaxResults(1)
              .getSingleResult();
    } catch (Exception e) {
      JFlemaxController.logError(e);
      return null;
    }
  }

  public List<Sale> searchSales(String query) {
    return getEntityManager()
            .createNamedQuery("sale.search_sales_by_receipt_id")
            .setParameter("receiptId_", query + "%")
            .setParameter("status", SaleStatus.CANCELLED)
            .getResultList();
  }

  public List<Sale> searchSales(String query, SaleStatus saleStatus) {
    return getEntityManager()
            .createNamedQuery("sale.search_sales_by_receipt_id_and_status")
            .setParameter("receiptId_", query + "%")
            .setParameter("status", saleStatus)
            .getResultList();
  }

  public Sale findSale(String receiptId) {
    try {
      return getEntityManager()
              .createNamedQuery("sale.find_sales_by_receipt_id", Sale.class)
              .setParameter("receiptId_", receiptId)
              .getSingleResult();
    } catch (Exception e) {
      JFlemaxController.logError(e);
      return null;
    }
  }

  public Sale findSale(SalesReceipt salesReceipt) {
    try {
      return getEntityManager()
              .createNamedQuery("sale.find_sales_by_receipt_id", Sale.class)
              .setParameter("receiptId_", salesReceipt.toString())
              .getSingleResult();
    } catch (Exception e) {
      JFlemaxController.logError(e);
      return null;
    }
  }

  @Override
  public void create(Sale sale) {
    super.create(sale);
    //update the batches if possible
    updateBatch(sale);
  }

  @Override
  public void edit(Sale entity) {
    super.edit(entity);
    updateBatch(entity);
  }

  private void updateBatch(Sale sale) {
    for (SaleItem si : sale.getSaleItems()) {
      for (ItemOrder io : si.getItemOrders()) {
        Batch b = io.getItem();
        batchFacade.edit(b);
      }
    }
  }
}
