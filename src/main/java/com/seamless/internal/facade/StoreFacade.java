/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.facade;

import com.seamless.internal.Store;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marembo
 */
@Stateless
public class StoreFacade extends AbstractFacade<Store> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public StoreFacade() {
    super(Store.class);
  }

  public List<Store> searchStore(String query) {
    return getEntityManager()
            .createNamedQuery("STORE.SEARCH_QUERY")
            .setParameter("name", "%" + query + "%")
            .getResultList();
  }
}
