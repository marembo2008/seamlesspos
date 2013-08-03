/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.controller;

import com.seamless.internal.Batch;
import com.seamless.internal.Store;
import com.seamless.internal.facade.BatchFacade;
import com.seamless.internal.util.ItemReportOption;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author marembo
 */
@Named(value = "inventoryController")
@SessionScoped
public class InventoryController implements Serializable {

  @EJB
  private BatchFacade batchFacade;
  private List<Batch> batches;
  private ItemReportOption itemReportOption;
  private Calendar expiryDate;
  private Calendar receivedDate;
  private Store store;

  public ItemReportOption getItemReportOption() {
    return itemReportOption;
  }

  public List<Batch> getBatches() {
    return batches;
  }

  public void setItemReportOption(ItemReportOption itemReportOption) {
    this.itemReportOption = itemReportOption;
  }

  public void onItemReportOptionSelected() {
    switch (itemReportOption) {
      case EXPIRY_DATE:
        if (expiryDate != null) {
          batches = batchFacade.findBatchesByExpiryDate(expiryDate);
        }
        break;
      case RECEIVED_DATE:
        if (receivedDate != null) {
          batches = batchFacade.findBatchesByReceivedDate(receivedDate);
        }
        break;
      case STORE_OPTION:
        if (store != null) {
          batches = batchFacade.findBatchesInStore(store);
        }
        break;
    }
  }

  public BigDecimal getTotalBatchCost() {
    BigDecimal v = BigDecimal.ZERO;
    if (batches != null) {
      for (Batch b : batches) {
        v = v.add(b.getTotalCost());
      }
    }
    return v;
  }

  public Calendar getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Calendar expiryDate) {
    this.expiryDate = expiryDate;
  }

  public Calendar getReceivedDate() {
    return receivedDate;
  }

  public void setReceivedDate(Calendar receivedDate) {
    this.receivedDate = receivedDate;
  }

  public Store getStore() {
    return store;
  }

  public void setStore(Store store) {
    this.store = store;
  }

  public boolean isExpiryDateOption() {
    return itemReportOption == ItemReportOption.EXPIRY_DATE;
  }

  public boolean isReceivedDateOption() {
    return itemReportOption == ItemReportOption.RECEIVED_DATE;
  }

  public boolean isStoreOption() {
    return itemReportOption == ItemReportOption.STORE_OPTION;
  }

  public String prepareBatchInventoryReport() {
    batches = null;
    return "inventory-report.xhtml?faces-redirect=true";
  }

  public ItemReportOption[] getItemReportOptions() {
    return ItemReportOption.values();
  }
}
