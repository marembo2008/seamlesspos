/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales;

import com.anosym.utilities.IdGenerator;
import com.seamless.internal.Batch;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author marembo
 */
@Entity
public class ItemOrder implements Serializable {

  private static final long serialVersionUID = IdGenerator.serialVersionUID(ItemOrder.class);
  @Id
  private Long orderId = IdGenerator.generateId();
  @OneToOne(cascade = {CascadeType.MERGE})
  private Batch item;
  private int orderedQuantity;

  public ItemOrder(Batch item, int orderedQuantity) {
    this.item = item;
    this.orderedQuantity = orderedQuantity;
  }

  public ItemOrder() {
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Batch getItem() {
    return item;
  }

  public void setItem(Batch item) {
    this.item = item;
  }

  public int getOrderedQuantity() {
    return orderedQuantity;
  }

  public void setOrderedQuantity(int orderedQuantity) {
    this.orderedQuantity = orderedQuantity;
  }

  public void increaseOrderedQuantity(int ordered) {
    orderedQuantity += ordered;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 79 * hash + (this.orderId != null ? this.orderId.hashCode() : 0);
    hash = 79 * hash + (this.item != null ? this.item.hashCode() : 0);
    hash = 79 * hash + this.orderedQuantity;
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
    final ItemOrder other = (ItemOrder) obj;
    if (this.orderId != other.orderId && (this.orderId == null || !this.orderId.equals(other.orderId))) {
      return false;
    }
    if (this.item != other.item && (this.item == null || !this.item.equals(other.item))) {
      return false;
    }
    if (this.orderedQuantity != other.orderedQuantity) {
      return false;
    }
    return true;
  }
}
