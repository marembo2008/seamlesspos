/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.facade;

import com.seamless.internal.Batch;
import com.seamless.internal.Item;
import com.seamless.internal.MixedItem;
import com.seamless.internal.MixedItemOption;
import com.seamless.internal.Store;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
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

  public List<Batch> findBatchesInStore(Store store) {
    return getEntityManager()
            .createNamedQuery("batchitem.find_batches_in_store")
            .setParameter("storeId", store.getStoreId())
            .getResultList();
  }

  public List<Batch> findBatchesInStore(Store store, Item i) {
    return getEntityManager()
            .createNamedQuery("batchitem.find_batches_in_store_by_item")
            .setParameter("storeId", store.getStoreId())
            .setParameter("itemCode", i.getItemCode())
            .getResultList();
  }

  public List<Batch> findBatchesByExpiryDate(Calendar expiryDate) {
    return getEntityManager()
            .createNamedQuery("batchitem.find_batch_by_expiry_date")
            .setParameter("expiryDate", expiryDate)
            .getResultList();
  }

  public List<Batch> findBatchesByReceivedDate(Calendar receivedDate) {
    return getEntityManager()
            .createNamedQuery("batchitem.find_batch_by_received_date")
            .setParameter("receivedDate", receivedDate)
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
            .setParameter("itemCode", item.getItemCode())
            .setParameter("frozen", false)
            .getSingleResult();
    return i != null ? i.intValue() : 0;
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public void createBatchForMixedItem(Batch batch) {
    //we need to reduce batches appropriately here for the mixed item.
    //get the bacthes with the specified mixed options
    for (MixedItemOption io : ((MixedItem) batch.getItem()).getItems()) {
      Item i = io.getItem();
      List<Batch> itemBatches = findBatchesInStore(batch.getStore(), i);
      int itemQuantity = io.getProportion().multiply(BigDecimal.valueOf(batch.getQuantity())).intValue();
      for (Batch b : itemBatches) {
        int currentQty = b.getCurrentQuantity();
        itemQuantity -= currentQty;
        //if the itemQty is greater than zero, set the batch current value to 0
        //if the itemQty is equal to zero, set the batch size to currentQty-itemQty and break
        if (itemQuantity >= 0) {
          b.setCurrentQuantity(0);
        } else {
          b.setCurrentQuantity(currentQty - itemQuantity);
        }
        edit(b);
      }
    }
    create(batch);
  }
}
