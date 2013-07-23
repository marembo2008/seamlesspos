/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.creditnote;

import com.anosym.utilities.IdGenerator;
import com.seamless.internal.sales.SaleItem;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author marembo
 */
@Entity
public class SalesReturn implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(length = 30)
  private Long id = IdGenerator.generateId();
  @OneToOne
  private SaleItem saleItem;
  private int returnedQuantity;

  public SalesReturn(SaleItem saleItem) {
    this.saleItem = saleItem;
  }

  public SalesReturn() {
  }

  public BigDecimal getSaleReturnAmount() {
    return saleItem != null ? (saleItem.getSalePrice().multiply(BigDecimal.valueOf(returnedQuantity))) : BigDecimal.ZERO;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SaleItem getSaleItem() {
    return saleItem;
  }

  public void setSaleItem(SaleItem saleItem) {
    this.saleItem = saleItem;
  }

  public int getReturnedQuantity() {
    return returnedQuantity;
  }

  public void setReturnedQuantity(int returnedQuantity) {
    this.returnedQuantity = returnedQuantity;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
    hash = 29 * hash + (this.saleItem != null ? this.saleItem.hashCode() : 0);
    hash = 29 * hash + this.returnedQuantity;
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
    final SalesReturn other = (SalesReturn) obj;
    if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
      return false;
    }
    if (this.saleItem != other.saleItem && (this.saleItem == null || !this.saleItem.equals(other.saleItem))) {
      return false;
    }
    if (this.returnedQuantity != other.returnedQuantity) {
      return false;
    }
    return true;
  }
}
