/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.Batch;
import com.seamless.internal.Item;
import com.seamless.internal.SaleDispatch;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.facade.BatchFacade;
import com.seamless.internal.facade.ItemFacade;
import com.seamless.internal.sales.ItemDispatchOrder;
import com.seamless.internal.sales.facade.SaleDispatchFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author marembo
 */
@Named(value = "saleDispatchController")
@SessionScoped
public class SaleDispatchController implements Serializable {

  @EJB
  private ItemFacade itemFacade;
  @EJB
  private BatchFacade batchFacade;
  @EJB
  private SaleDispatchFacade saleDispatchFacade;
  private SaleDispatch dispatch;
  private ItemDispatchOrder currentSelectDispatchOrder;
  private Map<Item, List<Batch>> dispatchedItems;
  private boolean editBasicInfo = true;

  public void setEditBasicInfo(boolean editBasicInfo) {
    this.editBasicInfo = editBasicInfo;
  }

  public void acceptBasicInfo() {
    setEditBasicInfo(false);
    System.err.println("acceptBasicInfo: " + isEditBasicInfo());
  }

  public void doEditBasicInfo() {
    setEditBasicInfo(true);
  }

  public boolean isEditBasicInfo() {
    return editBasicInfo;
  }

  public SaleDispatch getDispatch() {
    if (dispatch == null) {
      dispatch = new SaleDispatch();
      dispatchedItems = new HashMap<Item, List<Batch>>();
    }
    return dispatch;
  }

  public void setDispatch(SaleDispatch dispatch) {
    this.dispatch = dispatch;
  }

  public ItemDispatchOrder getCurrentSelectDispatchOrder() {
    if (currentSelectDispatchOrder == null) {
      currentSelectDispatchOrder = new ItemDispatchOrder();
    }
    return currentSelectDispatchOrder;
  }

  public void setCurrentSelectDispatchOrder(ItemDispatchOrder currentSelectDispatchOrder) {
    this.currentSelectDispatchOrder = currentSelectDispatchOrder;
  }

  public void addItemDispatch() {
    if (currentSelectDispatchOrder.getItem() != null) {
      dispatch.addItemDispatch(currentSelectDispatchOrder);
      List<Batch> batchs = batchFacade.findAvailableBatches(currentSelectDispatchOrder.getItem());
      int orderedQ = currentSelectDispatchOrder.getQuantity();
      for (Batch b : batchs) {
        if (orderedQ <= 0) {
          break;
        }
        int avQuantity = b.getCurrentQuantity();
        b.setCurrentQuantity(Math.max(0, avQuantity - orderedQ));
        orderedQ -= avQuantity;
      }
      dispatchedItems.put(currentSelectDispatchOrder.getItem(), batchs);
      currentSelectDispatchOrder = null;
    } else {
      JsfUtil.addErrorMessage("Please select item to add to this dispatch");
    }
  }

  public List<Item> searchItems(String query) {
    return itemFacade.searchItems(query, dispatchedItems.keySet());
  }

  public List<ItemDispatchOrder> getDispatchOrders() {
    return getDispatch().getDispatchedItems();
  }

  public void cancel() {
    this.dispatch = null;
    this.currentSelectDispatchOrder = null;
    this.dispatchedItems = null;
    this.editBasicInfo = true;
  }

  public void dispatchSales() {
    try {
      saleDispatchFacade.create(dispatch);
      cancel();
      JsfUtil.addSuccessMessage("Successfully dispatched sales. This dispatch will not be recorded as sales until recorded by the employee at end of day.");
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Error dispatching sales, please make sure that the dispatch has correct values set");
    }
  }

  public void checkDispatchedQuantity(FacesContext context, UIComponent component, Object value) {
    int itemQuantity = batchFacade.getTotalItemQuantitiesAvailable(currentSelectDispatchOrder.getItem());
    if (value == null || Integer.parseInt(value.toString()) > itemQuantity) {
      throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Dispatch Quantity: " + value, "Available Quantity: " + itemQuantity));
    }
  }

  public int getItemsAvailable() {
    if (getCurrentSelectDispatchOrder().getItem() != null) {
      return batchFacade.getTotalItemQuantitiesAvailable(currentSelectDispatchOrder.getItem());
    }
    return 0;
  }

  public SelectItem[] getSalePriceOptions() {
    SelectItem[] options = new SelectItem[0];
    if (getCurrentSelectDispatchOrder() != null && getCurrentSelectDispatchOrder().getItem() != null) {
      //set price1 as the default selling price.
      options = new SelectItem[3];
      options[0] = new SelectItem(getCurrentSelectDispatchOrder().getItem().getPrice1(), "KSh." + getCurrentSelectDispatchOrder().getItem().getPrice1() + "");
      options[1] = new SelectItem(getCurrentSelectDispatchOrder().getItem().getPrice2(), "KSh." + getCurrentSelectDispatchOrder().getItem().getPrice2() + "");
      options[2] = new SelectItem(getCurrentSelectDispatchOrder().getItem().getPrice3(), "KSh." + getCurrentSelectDispatchOrder().getItem().getPrice3() + "");
    }
    return options;
  }

  public BigDecimal getTotalOrderPrice() {
    BigDecimal total = BigDecimal.ZERO;
    if (getDispatchOrders() != null) {
      for (ItemDispatchOrder ido : getDispatchOrders()) {
        if (ido.getNetPrice() != null) {
          total = total.add(ido.getNetPrice());
        }
      }
    }
    return total;
  }

  public List<SaleDispatch> searchSaleDispatches(String idQuery) {
    return saleDispatchFacade.searchSaleDispatches(idQuery);
  }

  @FacesConverter("saleDispatchConverter")
  public static class SaleDispatchConverter implements Converter {

    private static Map<String, SaleDispatch> map = new HashMap<String, SaleDispatch>();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      return map.get(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
      if (value instanceof SaleDispatch) {
        SaleDispatch sd = (SaleDispatch) value;
        map.put(sd.getId(), sd);
        return sd.getId();
      }
      return null;
    }
  }
}
