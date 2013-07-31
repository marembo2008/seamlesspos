/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

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
public class DebtorAccount implements Serializable {

  private static final long serialVersionUID = 1264727489L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @OneToOne
  private Client debtor;
  private BigDecimal amount;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Client getDebtor() {
    return debtor;
  }

  public void setDebtor(Client debtor) {
    this.debtor = debtor;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
    hash = 71 * hash + (this.debtor != null ? this.debtor.hashCode() : 0);
    hash = 71 * hash + (this.amount != null ? this.amount.hashCode() : 0);
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
    final DebtorAccount other = (DebtorAccount) obj;
    if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
      return false;
    }
    if (this.debtor != other.debtor && (this.debtor == null || !this.debtor.equals(other.debtor))) {
      return false;
    }
    if (this.amount != other.amount && (this.amount == null || !this.amount.equals(other.amount))) {
      return false;
    }
    return true;
  }
}
