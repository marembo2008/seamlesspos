/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author marembo
 */
@Entity
@NamedQueries({
  @NamedQuery(name = "PRODUCT.SEARCH_PRODUCT", query = "SELECT p FROM Product p WHERE p.name LIKE :name")
})
public class Product implements Serializable {

  private static final long serialVersionUID = 1326423472086789L;
  @Id
  private Long productNumber;
  private String name;
  private int scale;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Item> items;

  public Long getProductNumber() {
    return productNumber;
  }

  public void setProductNumber(Long productNumber) {
    this.productNumber = productNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getScale() {
    return scale;
  }

  public void setScale(int scale) {
    this.scale = scale;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public void addItem(Item item) {
    if (this.items == null) {
      this.items = new ArrayList<Item>();
    }
    this.items.add(item);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 79 * hash + (this.productNumber != null ? this.productNumber.hashCode() : 0);
    hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0);
    hash = 79 * hash + this.scale;
    hash = 79 * hash + (this.items != null ? this.items.hashCode() : 0);
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
    final Product other = (Product) obj;
    if (this.productNumber != other.productNumber && (this.productNumber == null || !this.productNumber.equals(other.productNumber))) {
      return false;
    }
    if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
      return false;
    }
    if (this.scale != other.scale) {
      return false;
    }
    if (this.items != other.items && (this.items == null || !this.items.equals(other.items))) {
      return false;
    }
    return true;
  }
}
