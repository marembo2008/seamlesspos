/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.purchases.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.anosym.utilities.IdGenerator;
import com.seamless.internal.Item;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.facade.ItemFacade;
import com.seamless.internal.purchases.PurchaseOrder;
import com.seamless.internal.purchases.PurchaseOrderItem;
import com.seamless.internal.purchases.facade.PurchaseOrderFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author marembo
 */
@Named(value = "purchaseOrderController")
@SessionScoped
public class PurchaseOrderController implements Serializable {

  @EJB
  private PurchaseOrderFacade purchaseOrderFacade;
  @EJB
  private ItemFacade itemFacade;
  private PurchaseOrder purchaseOrder;
  private List<PurchaseOrderItem> storeItems;
  private PurchaseOrderItem[] selectedStoreItems;
  private List<PurchaseOrder> purchaseOrders;

  public void createOrder() {
    try {
      if (selectedStoreItems == null || selectedStoreItems.length <= 0) {
        JsfUtil.addErrorMessage("You must select itmes for order!");
        return;
      }
      for (PurchaseOrderItem item : selectedStoreItems) {
        purchaseOrder.addOrderItem(item);
      }
      purchaseOrderFacade.create(purchaseOrder);
      JsfUtil.addSuccessMessage("Successfully added order");
      purchaseOrder = null;
      purchaseOrders = null;
      selectedStoreItems = null;
      storeItems = null;
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Error Creating Purchasing Order: " + e.getLocalizedMessage());
    }
  }

  public List<PurchaseOrder> getPurchaseOrders() {
    if (purchaseOrders == null) {
      purchaseOrders = purchaseOrderFacade.findAll();
    }
    return purchaseOrders;
  }

  public void setSelectedStoreItems(PurchaseOrderItem[] selectedStoreItems) {
    this.selectedStoreItems = selectedStoreItems;
  }

  public PurchaseOrderItem[] getSelectedStoreItems() {
    return selectedStoreItems;
  }

  public PurchaseOrder getPurchaseOrder() {
    if (purchaseOrder == null) {
      purchaseOrder = new PurchaseOrder();
    }
    return purchaseOrder;
  }

  public void generatePurchaseOrderNumber() {
    getPurchaseOrder().setPurchaseOrderNumber(IdGenerator.generateId("PO/"));
  }

  public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
    this.purchaseOrder = purchaseOrder;
  }

  public void onStoreSelected() {
    if (purchaseOrder.getStore() != null) {
      List<Item> is = itemFacade.findItemsForStore(purchaseOrder.getStore());
      this.storeItems = new ArrayList<PurchaseOrderItem>();
      for (Item i : is) {
        this.storeItems.add(new PurchaseOrderItem(i));
      }
    }
  }

  public List<PurchaseOrderItem> getStoreItems() {
    return storeItems;
  }
}
