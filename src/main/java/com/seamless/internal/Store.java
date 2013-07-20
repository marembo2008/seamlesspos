/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 *
 * @author marembo
 */
@Entity
@NamedQueries({
  @NamedQuery(name = "STORE.SEARCH_QUERY", query = "SELECT s FROM Store s WHERE s.name LIKE :name")
})
public class Store implements Serializable {

  private static final long serialVersionUID = 13423879820525L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long storeId;
  private String name;
  private String location;
  @OneToOne(cascade = CascadeType.ALL)
  private Address storeAddress;
  @Transient
  private int defaultSortKey = 0;

  public Store() {
    storeAddress = new Address();
  }

  public Long getStoreId() {
    return storeId;
  }

  public int getDefaultSortKey() {
    return defaultSortKey;
  }

  public void setStoreId(Long storeId) {
    this.storeId = storeId;
  }

  public void setStoreAddress(Address storeAddress) {
    this.storeAddress = storeAddress;
  }

  public Address getStoreAddress() {
    return storeAddress;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 71 * hash + (this.storeId != null ? this.storeId.hashCode() : 0);
    hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
    hash = 71 * hash + (this.location != null ? this.location.hashCode() : 0);
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
    final Store other = (Store) obj;
    if (this.storeId != other.storeId && (this.storeId == null || !this.storeId.equals(other.storeId))) {
      return false;
    }
    if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
      return false;
    }
    if ((this.location == null) ? (other.location != null) : !this.location.equals(other.location)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return name;
  }
}
