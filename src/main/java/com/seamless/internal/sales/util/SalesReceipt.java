/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales.util;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;

/**
 *
 * @author marembo
 */
@Embeddable
public class SalesReceipt implements Serializable {

  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar saleDate;
  private int receiptnumber;

  public SalesReceipt() {
    receiptnumber = 1;
  }

  public SalesReceipt(Calendar saleDate, int receiptnumber) {
    this.saleDate = saleDate;
    this.receiptnumber = receiptnumber;
  }

  public Calendar getSaleDate() {
    return saleDate;
  }

  public void setSaleDate(Calendar saleDate) {
    this.saleDate = saleDate;
  }

  public int getReceiptnumber() {
    return receiptnumber;
  }

  public void setReceiptnumber(int receiptnumber) {
    this.receiptnumber = receiptnumber;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 79 * hash + (this.saleDate != null ? this.saleDate.hashCode() : 0);
    hash = 79 * hash + this.receiptnumber;
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final SalesReceipt other = (SalesReceipt) obj;
    if (this.saleDate != other.saleDate && (this.saleDate == null || !this.saleDate.equals(other.saleDate))) {
      return false;
    }
    if (this.receiptnumber != other.receiptnumber) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return saleDate.get(Calendar.YEAR) + ""
            + pad(2, saleDate.get(Calendar.MONTH)) + ""
            + pad(2, saleDate.get(Calendar.DATE))
            + "/" + receiptNumberPadded(6);
  }

  //pad with six integers
  private String receiptNumberPadded(int i) {
    String s = receiptnumber + "";
    while (s.length() < i) {
      s = "0" + s;
    }
    return s;
  }

  private String pad(int i, Object o) {
    String s = o + "";
    while (s.length() < i) {
      s = "0" + s;
    }
    return s;
  }
}
