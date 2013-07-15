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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

/**
 *
 * @author marembo
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Payment implements Serializable {

  private static final long serialVersionUID = IdGenerator.serialVersionUID(Payment.class);
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long paymentId;
  @Column(nullable = false)
  private BigDecimal paymentAmount;
  @OneToOne
  private Client client;
  @Column(nullable = false)
  private PaymentOption paymentOption;

  public Payment() {
  }

  public Payment(PaymentOption paymentOption) {
    this.paymentOption = paymentOption;
  }

  public Payment(BigDecimal paymentAmount, Client client, PaymentOption paymentOption) {
    this.paymentAmount = paymentAmount;
    this.client = client;
    this.paymentOption = paymentOption;
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
