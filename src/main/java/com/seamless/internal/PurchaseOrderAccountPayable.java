/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import com.seamless.internal.purchases.PurchaseOrder;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 *
 * @author marembo
 */
@Entity
public class PurchaseOrderAccountPayable extends AccountPayable implements Serializable {

  @OneToOne
  private PurchaseOrder purchaseOrder;
  private boolean amountInclusiveOfVat;

  public PurchaseOrder getPurchaseOrder() {
    return purchaseOrder;
  }

  public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
    this.purchaseOrder = purchaseOrder;
  }

  public boolean isAmountInclusiveOfVat() {
    return amountInclusiveOfVat;
  }

  public void setAmountInclusiveOfVat(boolean amountInclusiveOfVat) {
    this.amountInclusiveOfVat = amountInclusiveOfVat;
  }

  @Override
  public String getDescription() {
    return purchaseOrder != null ? "Purchase Order: " + purchaseOrder.getPurchaseOrderNumber() : null;
  }
}
