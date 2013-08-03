/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales.util;

/**
 *
 * @author marembo
 */
public enum SaleReportOption {

  ALL_SALES("Overall Sales"),
  DATE_RANGE("Date Range"),
  BY_USER("Per User"),
  BY_STORE("Per Store"),
  BY_ITEM("Per Item"),
  BY_PRODUCT("Per Product");
  private String desc;

  private SaleReportOption(String desc) {
    this.desc = desc;
  }

  @Override
  public String toString() {
    return desc;
  }
}
