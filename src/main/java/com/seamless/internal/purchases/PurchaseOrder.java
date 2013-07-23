/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.purchases;

import com.seamless.internal.Store;
import com.seamless.internal.Supplier;
import com.seamless.internal.purchases.PurchaseOrderItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author marembo
 */
@Entity
public class PurchaseOrder implements Serializable {

  private static final long serialVersionUID = 1264726428472482L;
  @Id
  @Column(length = 50)
  private String purchaseOrderNumber;
  @OneToMany(cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<PurchaseOrderItem> orderItems;
  @OneToOne
  private Supplier supplier;
  @OneToOne
  private Store store;

  public void setStore(Store store) {
    this.store = store;
  }

  public Store getStore() {
    return store;
  }

  public String getPurchaseOrderNumber() {
    return purchaseOrderNumber;
  }

  public void setPurchaseOrderNumber(String purchaseOrderNumber) {
    this.purchaseOrderNumber = purchaseOrderNumber;
  }

  public void setOrderItems(List<PurchaseOrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  public List<PurchaseOrderItem> getOrderItems() {
    if (orderItems == null) {
      orderItems = new ArrayList<PurchaseOrderItem>();
    }
    return orderItems;
  }

  public void addOrderItem(PurchaseOrderItem orderItem) {
    getOrderItems().add(orderItem);
  }

  public Supplier getSupplier() {
    return supplier;
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }
}
