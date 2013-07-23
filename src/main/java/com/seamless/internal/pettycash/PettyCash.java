/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.pettycash;

import com.seamless.internal.Employee;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author marembo
 */
@Entity
public class PettyCash implements Serializable {

  public static enum PettyCashMode {

    CASH,
    VOUCHER;
  }
  private static final long serialVersionUID = 174873439898L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private BigDecimal amount;
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar pettyCashDate;
  @OneToOne
  private Employee payee;
  private PettyCashMode pettyCashMode;
  @Column(length = 1024)
  private String comment;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Calendar getPettyCashDate() {
    return pettyCashDate;
  }

  public void setPettyCashDate(Calendar pettyCashDate) {
    this.pettyCashDate = pettyCashDate;
  }

  public Employee getPayee() {
    return payee;
  }

  public void setPayee(Employee payee) {
    this.payee = payee;
  }

  public PettyCashMode getPettyCashMode() {
    return pettyCashMode;
  }

  public void setPettyCashMode(PettyCashMode pettyCashMode) {
    this.pettyCashMode = pettyCashMode;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
    hash = 97 * hash + (this.amount != null ? this.amount.hashCode() : 0);
    hash = 97 * hash + (this.pettyCashDate != null ? this.pettyCashDate.hashCode() : 0);
    hash = 97 * hash + (this.payee != null ? this.payee.hashCode() : 0);
    hash = 97 * hash + (this.pettyCashMode != null ? this.pettyCashMode.hashCode() : 0);
    hash = 97 * hash + (this.comment != null ? this.comment.hashCode() : 0);
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
    final PettyCash other = (PettyCash) obj;
    if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
      return false;
    }
    if (this.amount != other.amount && (this.amount == null || !this.amount.equals(other.amount))) {
      return false;
    }
    if (this.pettyCashDate != other.pettyCashDate && (this.pettyCashDate == null || !this.pettyCashDate.equals(other.pettyCashDate))) {
      return false;
    }
    if (this.payee != other.payee && (this.payee == null || !this.payee.equals(other.payee))) {
      return false;
    }
    if (this.pettyCashMode != other.pettyCashMode) {
      return false;
    }
    if ((this.comment == null) ? (other.comment != null) : !this.comment.equals(other.comment)) {
      return false;
    }
    return true;
  }
}
