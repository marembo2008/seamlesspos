/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales;

import com.anosym.utilities.IdGenerator;
import com.seamless.internal.Client;
import com.seamless.internal.sales.util.PaymentOption;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

/**
 *
 * @author marembo
 */
@Entity
@NamedQueries({
  @NamedQuery(name = "CHEQUEPAYMENT.FIND_CHEQUE_PAYMENT",
          query = "SELECT c FROM ChequePayment c WHERE c.chequeNumber = :chequeNumber"),
  @NamedQuery(name = "CHEQUEPAYMENT.FIND_CHEQUE_PAYMENT_BY_STATE",
          query = "SELECT c FROM ChequePayment c WHERE c.chequeNumber = :chequeNumber AND c.paymentState = :state")
})
public class ChequePayment extends Payment {

  private static final long serialVersionUID = IdGenerator.serialVersionUID(ChequePayment.class);
  private String chequeNumber;
  private String drawer;
  private String bank;
  private String branch;
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar maturityDate;

  public ChequePayment() {
    super(PaymentOption.CHEQUE);
  }

  public ChequePayment(BigDecimal paymentAmount) {
    super(paymentAmount, null, PaymentOption.CHEQUE);
  }

  public ChequePayment(BigDecimal paymentAmount, Client client) {
    super(paymentAmount, client, PaymentOption.CHEQUE);
  }

  public String getChequeNumber() {
    return chequeNumber;
  }

  public void setChequeNumber(String chequeNumber) {
    this.chequeNumber = chequeNumber;
  }

  public String getDrawer() {
    return drawer;
  }

  public void setDrawer(String drawer) {
    this.drawer = drawer;
  }

  public String getBank() {
    return bank;
  }

  public void setBank(String bank) {
    this.bank = bank;
  }

  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  public Calendar getMaturityDate() {
    return maturityDate;
  }

  public void setMaturityDate(Calendar maturityDate) {
    this.maturityDate = maturityDate;
  }
}
