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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.ValidatorException;

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
      List<Item> is = itemFacade.getStoreItemsForReorder(purchaseOrder.getStore());
      this.storeItems = new ArrayList<PurchaseOrderItem>();
      for (Item i : is) {
        this.storeItems.add(new PurchaseOrderItem(i));
      }
      System.out.println("onStoreSelected: " + storeItems.size());
    }
  }

  public void validateOrderedQuantity(FacesContext cxt, UIComponent cmp, Object value) {
    int qty = 0;
    try {
      if (value != null && (qty = Integer.parseInt(value.toString())) > 0) {
        return;
      }
    } catch (Exception e) {
    }
    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid ordered quantity: " + value, "Invalid ordered quantity: " + value);
    ((UIInput) cmp).setValid(false);
    throw new ValidatorException(msg);
  }

  public List<PurchaseOrderItem> getStoreItems() {
    return storeItems;
  }

  public List<PurchaseOrder> searchPurchaseOrders(String query) {
    return purchaseOrderFacade.searchPurchaseOrders(query);
  }

  @FacesConverter("purchaseOrderConverter")
  public static class PurchaseOrderConverter implements Converter {

    private static Map<String, PurchaseOrder> map = new HashMap<String, PurchaseOrder>();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      return map.get(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
      if (value instanceof PurchaseOrder) {
        PurchaseOrder po = (PurchaseOrder) value;
        map.put(po.getPurchaseOrderNumber(), po);
        return po.getPurchaseOrderNumber();
      }
      return null;
    }
  }
}
