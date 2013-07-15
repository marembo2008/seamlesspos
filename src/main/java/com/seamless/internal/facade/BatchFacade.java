/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.facade;

import com.seamless.internal.Batch;
import com.seamless.internal.Item;
import com.seamless.internal.Store;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marembo
 */
@Stateless
public class BatchFacade extends AbstractFacade<Batch> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public BatchFacade() {
    super(Batch.class);
  }

  public List<Batch> findFrozenBatchItems() {
    return getEntityManager().createNamedQuery("batchitem.find_batch_by_frozen_state")
            .setParameter("frozen", true).getResultList();
  }

  public List<Batch> searchFrozenbatchItems(String batchId) {
    return getEntityManager().createNamedQuery("batchitem.search_batch_by_id")
            .setParameter("frozen", true)
            .setParameter("batchNumber_", "%" + batchId + "%")
            .getResultList();
  }

  public List<Batch> searchBatch(String itemName) {
    return getEntityManager()
            .createNamedQuery("batchitem.search_batch_by_item_name")
            .setParameter("name", "%" + itemName + "%")
            .getResultList();
  }

  public List<Batch> itemBatches(Item item) {
    return getEntityManager()
            .createNamedQuery("batchitem.find_batch_by_item")
            .setParameter("itemCode", item.getItemCode())
            .getResultList();
  }

  public BigDecimal findTotalStoreItemCost(Store store) {
    BigDecimal val = getEntityManager()
            .createNamedQuery("batchitem.find_total_item_cost_from_store", BigDecimal.class)
            .setParameter("storeId", store.getStoreId())
            .getSingleResult();
    return val == null ? BigDecimal.ZERO : val;
  }

  public List<Batch> findAvailableBatches(Item i) {
    return getEntityManager()
            .createNamedQuery("batchitem.find_batch_by_item_and_state_and_availability")
            .setParameter("itemCode", i.getItemCode())
            .setParameter("frozen", false)
            .getResultList();
  }

  public BigDecimal findTotalStoreItemCost() {
    BigDecimal val = getEntityManager()
            .createNamedQuery("batchitem.find_total_item_cost_from_all_store", BigDecimal.class)
            .getSingleResult();
    return val == null ? BigDecimal.ZERO : val;
  }

  public int getTotalItemQuantitiesAvailable(Item item) {
    Long i = getEntityManager()
            .createNamedQuery("batchitem.find_total_items_quantity", Long.class)
            .setParameter("itemId", item.getItemId())
            .setParameter("frozen", false)
            .getSingleResult();
    return i != null ? i.intValue() : 0;
  }
}
