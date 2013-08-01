/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales;

import com.anosym.utilities.IdGenerator;
import com.seamless.internal.Item;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author marembo
 */
@Entity
public class SaleItem implements Serializable {

  private static final long serialVersionUID = IdGenerator.serialVersionUID(SaleItem.class);
  @Id
  private Long saleItemId = IdGenerator.generateId();
  /**
   * The items that have been sold. The reason why we have a list is because if one batch is
   * finished, the rest of the number of items can be retrieved from other batches.
   */
  @OneToMany(cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<ItemOrder> itemOrders;
  private BigDecimal salePrice;
  private BigDecimal discount;
  private BigDecimal tax;
  @Transient
  private Sale sale;
  private int orderedQuantity;
  @OneToOne
  private Item item;

  public SaleItem() {
    salePrice = discount = tax = BigDecimal.ZERO;
    orderedQuantity = 1;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public Item getItem() {
    return item;
  }

  private int totalSetQuantity() {
    int total = 0;
    for (ItemOrder io : getItemOrders()) {
      total += io.getOrderedQuantity();
    }
    return total;
  }

  public int getUnassignedOrderedQuantity() {
    return orderedQuantity - totalSetQuantity();
  }

  public boolean isOrderSet() {
    return getUnassignedOrderedQuantity() == 0;
  }

  public void setOrderedQuantity(int orderedQuantity) {
    this.orderedQuantity = orderedQuantity;
  }

  public int getOrderedQuantity() {
    return orderedQuantity;
  }

  public void setSale(Sale sale) {
    this.sale = sale;
  }

  public BigDecimal getCostAmount() {
    if (itemOrders == null || itemOrders.isEmpty()) {
      return BigDecimal.ZERO;
    }
    return itemOrders.get(0).getItem().getItem().getCostAmount();
  }

  public Sale getSale() {
    return sale;
  }

  public Long getSaleItemId() {
    return saleItemId;
  }

  public void setSaleItemId(Long saleItemId) {
    this.saleItemId = saleItemId;
  }

  public void setItemOrders(List<ItemOrder> itemOrders) {
    this.itemOrders = itemOrders;
  }

  public List<ItemOrder> getItemOrders() {
    if (itemOrders == null) {
      itemOrders = new ArrayList<ItemOrder>();
    }
    return itemOrders;
  }

  public void addItem(ItemOrder itemOrder) {
    getItemOrders().add(itemOrder);
  }

  public BigDecimal getTotalTax() {
    return tax.multiply(BigDecimal.valueOf(getOrderedQuantity()));
  }

  public BigDecimal getNetPrice() {
    return tax != null ? getSalePrice().subtract(tax) : getSalePrice();
  }

  public BigDecimal getTotalSale() {
    BigDecimal total = BigDecimal.ZERO;
    if (getSalePrice() != null) {
      total = getSalePrice();
      total = total.multiply(BigDecimal.valueOf(getOrderedQuantity()));
    }
    return total.subtract(getTotalTax()).subtract(discount);
  }

  public BigDecimal getSalePrice() {
    return salePrice;
  }

  public void setSalePrice(BigDecimal salePrice) {
    this.salePrice = salePrice;
  }

  public BigDecimal getDiscount() {
    return discount;
  }

  public void setDiscount(BigDecimal discount) {
    this.discount = discount;
  }

  public BigDecimal getTax() {
    return tax;
  }

  public void setTax(BigDecimal tax) {
    this.tax = tax;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 23 * hash + (this.saleItemId != null ? this.saleItemId.hashCode() : 0);
    hash = 23 * hash + (this.itemOrders != null ? this.itemOrders.hashCode() : 0);
    hash = 23 * hash + (this.salePrice != null ? this.salePrice.hashCode() : 0);
    hash = 23 * hash + (this.discount != null ? this.discount.hashCode() : 0);
    hash = 23 * hash + (this.tax != null ? this.tax.hashCode() : 0);
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
    final SaleItem other = (SaleItem) obj;
    if (this.saleItemId != other.saleItemId && (this.saleItemId == null || !this.saleItemId.equals(other.saleItemId))) {
      return false;
    }
    if (this.itemOrders != other.itemOrders && (this.itemOrders == null || !this.itemOrders.equals(other.itemOrders))) {
      return false;
    }
    if (this.salePrice != other.salePrice && (this.salePrice == null || !this.salePrice.equals(other.salePrice))) {
      return false;
    }
    if (this.discount != other.discount && (this.discount == null || !this.discount.equals(other.discount))) {
      return false;
    }
    if (this.tax != other.tax && (this.tax == null || !this.tax.equals(other.tax))) {
      return false;
    }
    return true;
  }
}
