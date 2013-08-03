/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import com.anosym.utilities.IdGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

/**
 *
 * @author marembo
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedQueries({
  @NamedQuery(name = "ACCOUNTPAYABLE.FIND_ACCOUNT_PAYABLE_WITHIN_RANGE",
          query = "SELECT a FROM AccountPayable a WHERE a.paymentDate BETWEEN :start AND :end")
})
public abstract class AccountPayable implements Serializable {

  private static final long serialVersionUID = 12728472842L;
  @Id
  private Long id = IdGenerator.generateId();
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar paymentDate;
  private String chequeNumber;
  private BigDecimal amountPayable;

  public abstract String getDescription();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setAmountPayable(BigDecimal amountPayable) {
    this.amountPayable = amountPayable;
  }

  public BigDecimal getAmountPayable() {
    return amountPayable;
  }

  public Calendar getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(Calendar paymentDate) {
    this.paymentDate = paymentDate;
  }

  public String getChequeNumber() {
    return chequeNumber;
  }

  public void setChequeNumber(String chequeNumber) {
    this.chequeNumber = chequeNumber;
  }
}
