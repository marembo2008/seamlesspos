/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import com.seamless.internal.purchases.PurchaseOrder;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
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
public class AccountPayable implements Serializable {

  private static final long serialVersionUID = 12728472842L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar paymentDate;
  @OneToOne
  private PurchaseOrder purchaseOrder;
  private String chequeNumber;
  private BigDecimal amountPayable;
  private boolean amountInclusiveOfVat;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setAmountInclusiveOfVat(boolean amountInclusiveOfVat) {
    this.amountInclusiveOfVat = amountInclusiveOfVat;
  }

  public boolean isAmountInclusiveOfVat() {
    return amountInclusiveOfVat;
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

  public PurchaseOrder getPurchaseOrder() {
    return purchaseOrder;
  }

  public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
    this.purchaseOrder = purchaseOrder;
  }

  public String getChequeNumber() {
    return chequeNumber;
  }

  public void setChequeNumber(String chequeNumber) {
    this.chequeNumber = chequeNumber;
  }
}
