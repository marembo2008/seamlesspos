/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.util;

/**
 *
 * @author marembo
 */
public enum AccountPayableReportOption {

  All_ACCOUNTS("All Accounts Payable Report"),
  DATE("Date Range");
  private String desc;

  private AccountPayableReportOption(String desc) {
    this.desc = desc;
  }

  @Override
  public String toString() {
    return desc;
  }
}
