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
import javax.persistence.Entity;
import javax.persistence.Temporal;

/**
 *
 * @author marembo
 */
@Entity
public class CreditPayment extends Payment {

  private static final long serialVersionUID = IdGenerator.serialVersionUID(CreditPayment.class);
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar paymentDate;

  public CreditPayment() {
    super(PaymentOption.CREDIT);
  }

  public CreditPayment(BigDecimal paymentAmount, Client client) {
    super(paymentAmount, client, PaymentOption.CREDIT);
  }

  public Calendar getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(Calendar paymentDate) {
    this.paymentDate = paymentDate;
  }
}
