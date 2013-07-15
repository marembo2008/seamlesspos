/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.facade;

import com.seamless.internal.Item;
import com.seamless.internal.Store;
import java.math.BigDecimal;
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
public class ItemFacade extends AbstractFacade<Item> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;
  @EJB
  private BatchFacade batchItemFacade;
  @EJB
  private ProductFacade productFacade;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public ItemFacade() {
    super(Item.class);
  }

  public List<Item> searchItems(String query) {
    return getEntityManager()
            .createNamedQuery("item.search_items_by_name")
            .setParameter("name", "%" + query + "%")
            .getResultList();
  }
}
