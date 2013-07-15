/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.facade;

import com.seamless.internal.Supplier;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marembo
 */
@Stateless
public class SupplierFacade extends AbstractFacade<Supplier> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public SupplierFacade() {
    super(Supplier.class);
  }

  public List<Supplier> searchSupplier(String query) {
    return getEntityManager()
            .createNamedQuery("SUPPLIER.SEARCH_SUPPLIER")
            .setParameter("name", "%" + query + "%")
            .getResultList();
  }
}
