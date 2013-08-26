/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author marembo
 */
@Entity
@NamedQueries({
  @NamedQuery(name = "item.find_available_items",
          query = "select i from Item i where (select count(b) from Batch b JOIN b.item i_ where i_.itemCode = i.itemCode AND b.frozen = :state) > 0"),
  @NamedQuery(name = "item.search_items_by_name", query = "SELECT i FROM Item i WHERE i.name LIKE :name"),
  @NamedQuery(name = "Item.find_store_item_for_reorder",
          query = "SELECT i FROM Item i WHERE i.reorderLevel >= (SELECT SUM(b.currentQuantity) FROM Batch b WHERE b.item.itemCode = i.itemCode AND b.store.storeId = :storeId)")
})
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Item implements Serializable {

  private static final long serialVersionUID = 12345246284278L;
  @Id
  @Column(length = 50)
  private String itemCode;
  private String name;
  private BigDecimal costAmount;
  private BigDecimal price1;
  private BigDecimal price2;
  private BigDecimal price3;
  private int reorderLevel;
  @OneToMany(cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Item> stockins;
  private boolean taxable;

  public void setTaxable(boolean taxable) {
    this.taxable = taxable;
  }

  public boolean isTaxable() {
    return taxable;
  }

  public void setStockins(List<Item> stockins) {
    this.stockins = stockins;
  }

  public List<Item> getStockins() {
    return stockins;
  }

  public void addStockin(Item stockin) {
    if (stockins == null) {
      stockins = new ArrayList<Item>();
    }
    stockins.add(stockin);
  }

  public String getItemCode() {
    return itemCode;
  }

  public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getCostAmount() {
    return costAmount;
  }

  public void setCostAmount(BigDecimal costAmount) {
    this.costAmount = costAmount;
  }

  public BigDecimal getPrice1() {
    return price1;
  }

  public void setPrice1(BigDecimal price1) {
    this.price1 = price1;
  }

  public BigDecimal getPrice2() {
    return price2;
  }

  public void setPrice2(BigDecimal price2) {
    this.price2 = price2;
  }

  public BigDecimal getPrice3() {
    return price3;
  }

  public void setPrice3(BigDecimal price3) {
    this.price3 = price3;
  }

  public int getReorderLevel() {
    return reorderLevel;
  }

  public void setReorderLevel(int reorderLevel) {
    this.reorderLevel = reorderLevel;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 29 * hash + (this.itemCode != null ? this.itemCode.hashCode() : 0);
    hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
    hash = 29 * hash + (this.costAmount != null ? this.costAmount.hashCode() : 0);
    hash = 29 * hash + (this.price1 != null ? this.price1.hashCode() : 0);
    hash = 29 * hash + (this.price2 != null ? this.price2.hashCode() : 0);
    hash = 29 * hash + (this.price3 != null ? this.price3.hashCode() : 0);
    hash = 29 * hash + this.reorderLevel;
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
    final Item other = (Item) obj;
    if (this.itemCode != other.itemCode && (this.itemCode == null || !this.itemCode.equals(other.itemCode))) {
      return false;
    }
    if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
      return false;
    }
    if (this.costAmount != other.costAmount && (this.costAmount == null || !this.costAmount.equals(other.costAmount))) {
      return false;
    }
    if (this.price1 != other.price1 && (this.price1 == null || !this.price1.equals(other.price1))) {
      return false;
    }
    if (this.price2 != other.price2 && (this.price2 == null || !this.price2.equals(other.price2))) {
      return false;
    }
    if (this.price3 != other.price3 && (this.price3 == null || !this.price3.equals(other.price3))) {
      return false;
    }
    if (this.reorderLevel != other.reorderLevel) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Item{" + "itemCode=" + itemCode + ", name=" + name + ", costAmount=" + costAmount + ", price1=" + price1 + ", price2=" + price2 + ", price3=" + price3 + ", reorderLevel=" + reorderLevel + ", stockins=" + stockins + ", taxable=" + taxable + '}';
  }
}
