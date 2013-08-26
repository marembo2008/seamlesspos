/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.purchases;

import com.anosym.utilities.IdGenerator;
import com.seamless.internal.Batch;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author marembo
 */
@Entity
public class PurchasesReturn implements Serializable {

  private static final long serialVersionUID = 16764564564L;
  @Id
  private Long id = IdGenerator.generateId();
  @OneToOne
  private Batch batch;
  private int returnQuantity;
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar returnDate;

  public PurchasesReturn() {
    returnDate = Calendar.getInstance();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Batch getBatch() {
    return batch;
  }

  public void setBatch(Batch batch) {
    this.batch = batch;
  }

  public int getReturnQuantity() {
    return returnQuantity;
  }

  public BigDecimal getReturnCost() {
    return getBatch().getItem().getCostAmount().multiply(BigDecimal.valueOf((getReturnQuantity())));
  }

  public void setReturnQuantity(int returnQuantity) {
    this.returnQuantity = returnQuantity;
  }

  public Calendar getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(Calendar returnDate) {
    this.returnDate = returnDate;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
    hash = 97 * hash + (this.batch != null ? this.batch.hashCode() : 0);
    hash = 97 * hash + this.returnQuantity;
    hash = 97 * hash + (this.returnDate != null ? this.returnDate.hashCode() : 0);
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
    final PurchasesReturn other = (PurchasesReturn) obj;
    if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
      return false;
    }
    if (this.batch != other.batch && (this.batch == null || !this.batch.equals(other.batch))) {
      return false;
    }
    if (this.returnQuantity != other.returnQuantity) {
      return false;
    }
    if (this.returnDate != other.returnDate && (this.returnDate == null || !this.returnDate.equals(other.returnDate))) {
      return false;
    }
    return true;
  }
}
