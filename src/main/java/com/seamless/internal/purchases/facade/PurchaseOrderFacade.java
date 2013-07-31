/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.purchases.facade;

import com.seamless.internal.facade.*;
import com.seamless.internal.purchases.PurchaseOrder;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marembo
 */
@Stateless
public class PurchaseOrderFacade extends AbstractFacade<PurchaseOrder> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public PurchaseOrderFacade() {
    super(PurchaseOrder.class);
  }

  public List<PurchaseOrder> searchPurchaseOrders(String orderNumberQuery) {
    return getEntityManager()
            .createNamedQuery("PURCHASEORDER.FIND_PURCHASE_ORDER_BY_ORDER_NUMBER")
            .setParameter("orderNumber", "%" + orderNumberQuery + "%")
            .getResultList();
  }
}
