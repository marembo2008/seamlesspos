/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales;

import com.anosym.utilities.IdGenerator;
import com.seamless.internal.Client;
import com.seamless.internal.sales.util.PaymentOption;
import java.math.BigDecimal;
import javax.persistence.Entity;

/**
 *
 * @author marembo
 */
@Entity
public class CashPayment extends Payment {

  private static final long serialVersionUID = IdGenerator.serialVersionUID(CashPayment.class);

  public CashPayment() {
    super(PaymentOption.CASH);
  }

  public CashPayment(BigDecimal paymentAmount) {
    super(paymentAmount, null, PaymentOption.CASH);
  }

  public CashPayment(BigDecimal paymentAmount, Client client) {
    super(paymentAmount, client, PaymentOption.CASH);
  }
}
