/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales.facade;

import com.seamless.internal.Batch;
import com.seamless.internal.SaleDispatch;
import com.seamless.internal.facade.AbstractFacade;
import com.seamless.internal.facade.BatchFacade;
import com.seamless.internal.sales.ItemDispatchOrder;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marembo
 */
@Stateless
public class SaleDispatchFacade extends AbstractFacade<SaleDispatch> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;
  @EJB
  private BatchFacade batchFacade;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public SaleDispatchFacade() {
    super(SaleDispatch.class);
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public void create(SaleDispatch dispatch) {
    super.create(dispatch);
    for (ItemDispatchOrder dispatchOrder : dispatch.getDispatchedItems()) {
      List<Batch> batchs = batchFacade.findAvailableBatches(dispatchOrder.getItem());
      int orderedQ = dispatchOrder.getQuantity();
      for (Batch b : batchs) {
        if (orderedQ <= 0) {
          break;
        }
        int avQuantity = b.getCurrentQuantity();
        b.setCurrentQuantity(Math.max(0, avQuantity - orderedQ));
        orderedQ -= avQuantity;
        batchFacade.edit(b);
      }
    }
  }

  public List<SaleDispatch> searchSaleDispatches(String idQuery) {
    return getEntityManager()
            .createNamedQuery("SALEDISPATCH.SEARCH_SALE_DISPATCH_BY_ID")
            .setParameter("id", "%" + idQuery + "%")
            .getResultList();
  }
}
