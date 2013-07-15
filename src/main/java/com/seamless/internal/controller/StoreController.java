/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.Store;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.facade.BatchFacade;
import com.seamless.internal.facade.ItemFacade;
import com.seamless.internal.facade.StoreFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author marembo
 */
@Named(value = "storeController")
@SessionScoped
public class StoreController implements Serializable {

  private class StoreModel extends ListDataModel<Store> implements SelectableDataModel<Store> {

    public StoreModel(List<Store> list) {
      super(list);
    }

    public StoreModel() {
    }

    @Override
    public Object getRowKey(Store t) {
      return t.getStoreId();
    }

    @Override
    public Store getRowData(String string) {
      return storeFacade.find(Long.valueOf(string));
    }
  }
  @EJB
  private BatchFacade batchFacade;
  @EJB
  private ItemFacade itemFacade;
  @EJB
  private StoreFacade storeFacade;
  private Store store;
  private DataModel<Store> stores;

  /**
   * Creates a new instance of StoreController
   */
  public StoreController() {
  }

  public Store getStore() {
    if (store == null) {
      store = new Store();
    }
    return store;
  }

  public List<Store> searchStore(String query) {
    return storeFacade.searchStore(query);
  }

  public void setStore(Store store) {
    this.store = store;
  }

  public void create() {
    try {
      storeFacade.create(store);
      //reload stores
      loadStores();
      store = null;
      JsfUtil.addSuccessMessage("Successfully Created a store");
    } catch (Exception e) {
      JsfUtil.addErrorMessage("Error while creating store. Please try again lateer");
      Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  public String loadStores() {
    stores = new StoreModel(storeFacade.findAll());
    return "/?faces-redirect=true";
  }

  public DataModel<Store> getStores() {
    if (stores == null) {
      loadStores();
    }
    return stores;
  }

  public SelectItem[] getSingleSelectStores() {
    return JsfUtil.getSelectItems(storeFacade.findAll(), true);
  }

  public BigDecimal getStoreSales(Store store) {
    return BigDecimal.ZERO;
  }

  public BigDecimal getStorePurchases(Store store) {
    return batchFacade.findTotalStoreItemCost(store);
  }

  public BigDecimal getStoreNetworth(Store store) {
    return BigDecimal.ZERO;
  }

  public BigDecimal getTotalStoreSales() {
    return BigDecimal.ZERO;
  }

  public BigDecimal getTotalStorePurchases() {
    return batchFacade.findTotalStoreItemCost();
  }

  public BigDecimal getTotalStoreNetworth() {
    return BigDecimal.ZERO;
  }

  Store findStore(Long id) {
    return storeFacade.find(id);
  }

  @FacesConverter(value = "storeConverter", forClass = Store.class)
  public static class StoreConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      System.out.println("Store Id: " + value);
      if (value == null) {
        return null;
      }
      StoreController controller = JFlemaxController.findManagedBean(StoreController.class);
      return controller.findStore(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
      if (value instanceof Store) {
        return ((Store) value).getStoreId() + "";
      }
      return null;
    }
  }
}
