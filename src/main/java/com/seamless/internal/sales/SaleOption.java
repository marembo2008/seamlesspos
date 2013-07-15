/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales;

/**
 *
 * @author marembo
 */
public class SaleOption {

  public static enum OptionType {

    CASH,
    CREDIT,
    CHEQUE;
  }
  public static final SaleOption CASH_SALE = new SaleOption(OptionType.CASH);
  private OptionType option;
  private Payment payment;

  public SaleOption() {
  }

  public SaleOption(OptionType option) {
    this.option = option;
  }

  public SaleOption(OptionType option, Payment payment) {
    this.option = option;
    this.payment = payment;
  }

  public OptionType getOption() {
    return option;
  }

  public void setOption(OptionType option) {
    this.option = option;
  }

  public Payment getPayment() {
    return payment;
  }

  public void setPayment(Payment payment) {
    this.payment = payment;
  }
}
