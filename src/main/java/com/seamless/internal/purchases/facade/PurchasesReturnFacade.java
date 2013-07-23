/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.purchases.facade;

import com.seamless.internal.facade.*;
import com.seamless.internal.purchases.PurchasesReturn;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marembo
 */
@Stateless
public class PurchasesReturnFacade extends AbstractFacade<PurchasesReturn> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public PurchasesReturnFacade() {
    super(PurchasesReturn.class);
  }
}
