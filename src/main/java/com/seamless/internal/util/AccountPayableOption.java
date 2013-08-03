/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.util;

/**
 *
 * @author marembo
 */
public enum AccountPayableOption {

  PURCHASE_ORDER("Purchase Order Account Payable"),
  EXPENSE("Expenses Account Payable");
  private String desc;

  private AccountPayableOption(String desc) {
    this.desc = desc;
  }

  @Override
  public String toString() {
    return desc;
  }
}
