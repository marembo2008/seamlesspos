/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import com.anosym.utilities.FormattedCalendar;
import com.anosym.utilities.IdGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;

/**
 *
 * @author marembo
 */
@Entity
@NamedQueries({
  @NamedQuery(name = "batchitem.find_batches_in_store",
          query = "SELECT i FROM Batch i WHERE i.store.storeId = :storeId"),
  @NamedQuery(name = "batchitem.find_batches_in_store_by_item",
          query = "SELECT i FROM Batch i WHERE i.store.storeId = :storeId AND i.item.itemCode = :itemCode"),
  @NamedQuery(name = "batchitem.find_batch_by_frozen_state",
          query = "SELECT bi FROM Batch bi WHERE bi.frozen = :frozen"),
  @NamedQuery(name = "batchitem.search_batch_by_item_name",
          query = "SELECT bi FROM Batch bi WHERE bi.item.name LIKE :name"),
  @NamedQuery(name = "batchitem.find_batch_by_item",
          query = "SELECT bi FROM Batch bi WHERE bi.item.itemCode = :itemCode"),
  @NamedQuery(name = "batchitem.find_batch_by_item_and_state_and_availability",
          query = "SELECT bi FROM Batch bi WHERE bi.item.itemCode = :itemCode AND bi.currentQuantity > 0 AND bi.frozen = :frozen"),
  @NamedQuery(name = "batchitem.search_batch_by_id",
          query = "SELECT bi FROM Batch bi WHERE bi.frozen = :frozen AND bi.batchNumber_ LIKE :batchNumber_"),
  @NamedQuery(name = "batchitem.find_total_item_cost_from_store",
          query = "SELECT SUM(i.costAmount * b.quantity) FROM Batch b JOIN b.item i WHERE b.store.storeId = :storeId"),
  @NamedQuery(name = "batchitem.find_total_items_quantity",
          query = "SELECT SUM(b.currentQuantity) FROM Batch b JOIN b.item i WHERE i.itemCode = :itemCode AND b.frozen = :frozen"),
  @NamedQuery(name = "batchitem.find_total_item_cost_from_all_store",
          query = "SELECT SUM(i.costAmount * b.quantity) FROM Batch b JOIN b.item i"),
  @NamedQuery(name = "batchitem.find_batch_by_expiry_date",
          query = "SELECT b FROM Batch b WHERE b.bestBefore = :expiryDate"),
  @NamedQuery(name = "batchitem.find_batch_by_received_date",
          query = "SELECT b FROM Batch b WHERE b.receivedDate = :receivedDate")
})
public class Batch implements Serializable {

  private static final long serialVersionUID = 4648664846841L;
  @Id
  private Long batchNumber = IdGenerator.generateId();
  private String batchNumber_; //used for searching
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar receivedDate;
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar manufacturedDate;
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar bestBefore;
  /**
   * Only unfrozen items can be available for sale.
   */
  private boolean frozen;
  @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  private Item item;
  /**
   * The number of items in this batch.
   */
  private int quantity;
  /**
   * The current quantity of the item.
   */
  private int currentQuantity;
  @OneToOne
  private Supplier supplier;
  /**
   * Rather than making an item as a member of a store, since a store can contain so many items, and
   * loading them at once would not be good.
   */
  @OneToOne
  @JoinColumn(nullable = false)
  private Store store;

  public Batch() {
    receivedDate = Calendar.getInstance();
    frozen = true;
  }

  @PrePersist
  void onSave() {
    this.batchNumber_ = this.batchNumber + "";
    this.currentQuantity = this.quantity;
  }

  public void setStore(Store store) {
    this.store = store;
  }

  public String getStoreDescription() {
    return getBatchNumber() + "(" + getItem().getName() + ")(Store: " + getStore().getName() + ")";
  }

  public Store getStore() {
    return store;
  }

  public BigDecimal getCostAmount() {
    return item != null ? item.getCostAmount() : BigDecimal.ZERO;
  }

  public void setCurrentQuantity(int currentQuantity) {
    this.currentQuantity = currentQuantity;
  }

  public int getCurrentQuantity() {
    return currentQuantity;
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }

  public Supplier getSupplier() {
    return supplier;
  }

  public Long getBatchNumber() {
    return batchNumber;
  }

  public void setBatchNumber(Long batchNumber) {
    this.batchNumber = batchNumber;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getQuantity() {
    return quantity;
  }

  public BigDecimal getTotalCost() {
    BigDecimal total = BigDecimal.ZERO;
    if (item != null) {
      total = getCostAmount().multiply(BigDecimal.valueOf(quantity));
    }
    return total;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public Item getItem() {
    return item;
  }

  public Calendar getReceivedDate() {
    return receivedDate;
  }

  public void setReceivedDate(Calendar receivedDate) {
    this.receivedDate = receivedDate;
  }

  public Calendar getManufacturedDate() {
    return manufacturedDate;
  }

  public void setManufacturedDate(Calendar manufacturedDate) {
    this.manufacturedDate = manufacturedDate;
  }

  public Calendar getBestBefore() {
    return bestBefore;
  }

  public void setBestBefore(Calendar bestBefore) {
    this.bestBefore = bestBefore;
  }

  public boolean isFrozen() {
    return frozen;
  }

  public void setFrozen(boolean frozen) {
    this.frozen = frozen;
  }

  public void decreaseCurrentQuantity(int ordered) {
    currentQuantity -= ordered;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 71 * hash + (this.batchNumber != null ? this.batchNumber.hashCode() : 0);
    hash = 71 * hash + (this.receivedDate != null ? this.receivedDate.hashCode() : 0);
    hash = 71 * hash + (this.manufacturedDate != null ? this.manufacturedDate.hashCode() : 0);
    hash = 71 * hash + (this.bestBefore != null ? this.bestBefore.hashCode() : 0);
    hash = 71 * hash + (this.frozen ? 1 : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Batch other = (Batch) obj;
    if (this.batchNumber != other.batchNumber && (this.batchNumber == null || !this.batchNumber.equals(other.batchNumber))) {
      return false;
    }
    if (this.receivedDate != other.receivedDate && (this.receivedDate == null || !this.receivedDate.equals(other.receivedDate))) {
      return false;
    }
    if (this.manufacturedDate != other.manufacturedDate && (this.manufacturedDate == null || !this.manufacturedDate.equals(other.manufacturedDate))) {
      return false;
    }
    if (this.bestBefore != other.bestBefore && (this.bestBefore == null || !this.bestBefore.equals(other.bestBefore))) {
      return false;
    }
    if (this.frozen != other.frozen) {
      return false;
    }
    if (this.item != other.item && (this.item == null || !this.item.equals(other.item))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "BatchItem{" + "batchNumber=" + batchNumber
            + ", receivedDate=" + FormattedCalendar.toString(receivedDate)
            + ", manufacturedDate=" + FormattedCalendar.toString(manufacturedDate)
            + ", bestBefore=" + FormattedCalendar.toString(bestBefore)
            + ", frozen=" + frozen + ", item=" + item + '}';
  }
}
