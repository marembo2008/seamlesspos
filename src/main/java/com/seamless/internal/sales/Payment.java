/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales;

import com.anosym.utilities.IdGenerator;
import com.seamless.internal.Client;
import com.seamless.internal.sales.util.PaymentOption;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author marembo
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Payment implements Serializable {

  public static enum PaymentState {

    PAID,
    PENDING,
    REJECTED;
  }
  private static final long serialVersionUID = IdGenerator.serialVersionUID(Payment.class);
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long paymentId;
  @Column(nullable = false)
  private BigDecimal paymentAmount;
  @OneToOne
  private Client client;
  private PaymentOption paymentOption;
  private PaymentState paymentState;
  /**
   * The day the actual payment is actually done or recorded.
   */
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar paymentDate;

  public Payment() {
    this(PaymentOption.CASH);
  }

  public Payment(PaymentOption paymentOption) {
    this(null, null, paymentOption);
  }

  public Payment(BigDecimal paymentAmount, Client client, PaymentOption paymentOption) {
    this.paymentAmount = paymentAmount;
    this.client = client;
    this.paymentOption = paymentOption;
    this.paymentState = PaymentState.PENDING;
  }

  public void setPaymentDate(Calendar paymentDate) {
    this.paymentDate = paymentDate;
  }

  public Calendar getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentState(PaymentState paymentState) {
    this.paymentState = paymentState;
  }

  public PaymentState getPaymentState() {
    return paymentState;
  }

  public void setPaymentOption(PaymentOption paymentOption) {
    this.paymentOption = paymentOption;
  }

  public PaymentOption getPaymentOption() {
    return paymentOption;
  }

  public void setPaymentId(Long paymentId) {
    this.paymentId = paymentId;
  }

  public Long getPaymentId() {
    return paymentId;
  }

  public BigDecimal getPaymentAmount() {
    return paymentAmount;
  }

  public void setPaymentAmount(BigDecimal paymentAmount) {
    this.paymentAmount = paymentAmount;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
