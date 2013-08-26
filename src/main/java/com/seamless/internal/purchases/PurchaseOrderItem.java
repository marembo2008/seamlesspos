
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.purchases;

import com.anosym.utilities.IdGenerator;
import com.seamless.internal.Item;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author marembo
 */
@Entity
public class PurchaseOrderItem implements Serializable {

  private static final long serialVersionUID = 8378229091L;
  @Id
  private Long id = IdGenerator.generateId();
  @OneToOne
  private Item orderItem;
  private int orderedQuantity;

  public PurchaseOrderItem(Item orderItem) {
    this.orderItem = orderItem;
  }

  public PurchaseOrderItem() {
  }

  public BigDecimal getOrderCost() {
    BigDecimal cost = orderItem.getCostAmount();
    if (cost != null) {
      return cost.multiply(BigDecimal.valueOf(orderedQuantity));
    }
    return BigDecimal.ZERO;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Item getOrderItem() {
    return orderItem;
  }

  public void setOrderItem(Item orderItem) {
    this.orderItem = orderItem;
  }

  public int getOrderedQuantity() {
    return orderedQuantity;
  }

  public void setOrderedQuantity(int orderedQuantity) {
    this.orderedQuantity = orderedQuantity;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
    hash = 71 * hash + (this.orderItem != null ? this.orderItem.hashCode() : 0);
    hash = 71 * hash + this.orderedQuantity;
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
    final PurchaseOrderItem other = (PurchaseOrderItem) obj;
    if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
      return false;
    }
    if (this.orderItem != other.orderItem && (this.orderItem == null || !this.orderItem.equals(other.orderItem))) {
      return false;
    }
    if (this.orderedQuantity != other.orderedQuantity) {
      return false;
    }
    return true;
  }
}
