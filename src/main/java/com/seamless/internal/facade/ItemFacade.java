/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.facade;

import com.seamless.internal.Item;
import java.util.Collection;
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

  public List<Item> searchItems(String query, Collection<Item> itemsToIgnore) {
    String sql = "SELECT i FROM Item i WHERE i.name LIKE :name ";
    if (itemsToIgnore != null && !itemsToIgnore.isEmpty()) {
      for (Item i : itemsToIgnore) {
        sql += " AND i.itemCode != " + i.getItemCode();
      }
    }
    System.err.println(sql);
    return getEntityManager()
            .createQuery(sql)
            .setParameter("name", "%" + query + "%")
            .getResultList();
  }
}
