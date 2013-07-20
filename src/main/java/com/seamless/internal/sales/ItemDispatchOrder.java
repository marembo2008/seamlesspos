/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales;

import com.seamless.internal.Item;
import com.seamless.settings.Setting;
import com.seamless.settings.SettingUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author marembo
 */
@Entity
public class ItemDispatchOrder implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @OneToOne
  private Item item;
  private int quantity;
  private BigDecimal sellingPrice;
  private BigDecimal tax;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setSellingPrice(BigDecimal sellingPrice) {
    this.sellingPrice = sellingPrice;
    if (sellingPrice != null) {
      Setting settings = SettingUtil.getSetting();
      tax = sellingPrice.multiply(settings.getVat());
    }
  }

  public BigDecimal getSellingPrice() {
    return sellingPrice;
  }

  public BigDecimal getNetPrice() {
    return sellingPrice != null && tax != null ? (sellingPrice.add(tax)) : sellingPrice;
  }

  public void setTax(BigDecimal tax) {
    this.tax = tax;
  }

  public BigDecimal getTax() {
    return tax;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
    if (item != null) {
      setSellingPrice(item.getPrice1());
    }
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
    hash = 59 * hash + (this.item != null ? this.item.hashCode() : 0);
    hash = 59 * hash + this.quantity;
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
    final ItemDispatchOrder other = (ItemDispatchOrder) obj;
    if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
      return false;
    }
    if (this.item != other.item && (this.item == null || !this.item.equals(other.item))) {
      return false;
    }
    if (this.quantity != other.quantity) {
      return false;
    }
    return true;
  }
}
