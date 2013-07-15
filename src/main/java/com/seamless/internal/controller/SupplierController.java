/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.Address;
import com.seamless.internal.Supplier;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.facade.SupplierFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author marembo
 */
@Named("supplierController")
@SessionScoped
public class SupplierController implements Serializable {

  private class SupplierDataModel extends ListDataModel<Supplier> implements SelectableDataModel<Supplier> {

    public SupplierDataModel(List<Supplier> list) {
      super(list);
    }

    public SupplierDataModel() {
    }

    @Override
    public Object getRowKey(Supplier t) {
      return t.getId();
    }

    @Override
    public Supplier getRowData(String string) {
      return supplierFacade.find(Long.valueOf(string));
    }
  }
  @EJB
  private SupplierFacade supplierFacade;
  private Supplier supplier;
  private DataModel<Supplier> suppliers;

  /**
   * Creates a new instance of SupplierController
   */
  public SupplierController() {
  }

  public SupplierFacade getSupplierFacade() {
    return supplierFacade;
  }

  public void setSupplierFacade(SupplierFacade supplierFacade) {
    this.supplierFacade = supplierFacade;
  }

  public Supplier getSupplier() {
    if (supplier == null) {
      prepareCreate();
    }
    return supplier;
  }

  public List<Supplier> searchSupplier(String query) {
    return supplierFacade.searchSupplier(query);
  }

  public void supplierSelected(SelectEvent event) {
    if (event.getObject() != null) {
      this.supplier = (Supplier) event.getObject();
    }
  }

  public void prepareCreate() {
    supplier = new Supplier();
    supplier.setAddress(new Address());
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }

  public void create() {
    try {
      System.err.println("Supplier id: " + supplier.getId());
      if (supplierFacade.find(supplier.getId()) != null) {
        supplierFacade.edit(supplier);
        JsfUtil.addSuccessMessage("Successfully updated supplier: " + supplier.getName());
      } else {
        supplierFacade.create(supplier);
        JsfUtil.addSuccessMessage("Successfully added a supplier");
      }
      supplier = null;
      loadSuppliers();
    } catch (Exception e) {
      JsfUtil.addErrorMessage("Error adding new supplier, please try again later");
      Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  public void update() {
    try {
      supplierFacade.edit(supplier);
      JsfUtil.addSuccessMessage("Successfully updated supplier: " + supplier.getName());
      supplier = null;
      loadSuppliers();
    } catch (Exception e) {
      JsfUtil.addErrorMessage("Error adding new supplier, please try again later");
      Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  public int getActiveIndex() {
    return isNewSupplier() ? -1 : 0;
  }

  public void setActiveIndex(int ix) {
  }

  public boolean isNewSupplier() {
    return supplierFacade.find(getSupplier().getId()) == null;
  }

  private void loadSuppliers() {
    suppliers = new SupplierDataModel(supplierFacade.findAll());
  }

  public DataModel<Supplier> getSuppliers() {
    if (suppliers == null) {
      loadSuppliers();
    }
    return suppliers;
  }

  public void setSuppliers(DataModel<Supplier> suppliers) {
    this.suppliers = suppliers;
  }

  Supplier find(Long id) {
    return supplierFacade.find(id);
  }

  @FacesConverter(value = "supplierConverter", forClass = Supplier.class)
  public static class SupplierConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      return (value != null) ? JFlemaxController.findManagedBean(SupplierController.class).find(Long.parseLong(value)) : null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
      return (value instanceof Supplier) ? (((Supplier) value).getId() + "") : null;
    }
  }
}
