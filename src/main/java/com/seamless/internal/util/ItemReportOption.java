/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.util;

/**
 *
 * @author marembo
 */
public enum ItemReportOption {

  STORE_OPTION("Item By Store"),
  RECEIVED_DATE("Item By Date"),
  EXPIRY_DATE("By Expiry Date");
  private String desc;

  private ItemReportOption(String desc) {
    this.desc = desc;
  }

  @Override
  public String toString() {
    return desc;
  }
}
