/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.anosym.utilities.Utility;
import com.seamless.internal.Batch;
import com.seamless.internal.Item;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.facade.BatchFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author marembo
 */
@Named(value = "batchController")
@SessionScoped
public class BatchController implements Serializable {

  @EJB
  private BatchFacade batchFacade;
  private Batch batch;

  /**
   * Creates a new instance of BatchController
   */
  public BatchController() {
  }

  public Batch getBatch() {
    if (batch == null) {
      batch = new Batch();
    }
    return batch;
  }

  public void validateQuantity(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    if (value == null || !Utility.isNumber(value.toString()) || Integer.valueOf(value.toString()) < 1) {
      String msg = "You must specify item quantity greater than zero";
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
      throw new ValidatorException(facesMsg);
    }
  }

  public void setBatch(Batch batch) {
    this.batch = batch;
  }

  public void prepareCreate() {
    batch = new Batch();
  }

  public void create() {
    try {
      System.err.println("Creating batch: " + batch);
      batchFacade.create(batch);
      JsfUtil.addSuccessMessage("Successfully added a new batch");
    } catch (Exception e) {
      JsfUtil.addErrorMessage("Failed to add batch. Please try again later");
      Logger.getLogger(BatchController.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  public List<Batch> getBatches() {
    return batchFacade.findAll();
  }

  public List<Batch> itemBatches(Item item) {
    return batchFacade.itemBatches(item);
  }

  public List<Batch> findBatches(String itemName) {
    return batchFacade.searchBatch(itemName);
  }

  void addAndUpdate(Item item) {
    this.batch.setItem(item);
    this.batchFacade.edit(batch);
  }

  Batch find(Long id) {
    return batchFacade.find(id);
  }

  public String prepareList() {
    return "stocking.xhtml?faces-redirect=true";
  }

  public void update() {
    batchFacade.edit(batch);
    JsfUtil.addSuccessMessage("Batch successfully unfrozen. It will now be available for sales");
  }

  public void updateFrozenState(Batch batch, boolean currentState) {
    try {
      if (batch.isFrozen()) {
        batch.setFrozen(!currentState);
      } else {
        JsfUtil.addErrorMessage("Batch is already unfrozen. It cannot be frozen again!");
        return;
      }
      System.err.println("Frozen State: " + batch.isFrozen());
      batchFacade.edit(batch);
      JsfUtil.addSuccessMessage("Batch successfully unfrozen. It will now be available for sales");
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Error: " + e.getMessage());
    }
  }

  @FacesConverter("batchConverter")
  public static class BatchConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      if (value != null) {
        return JFlemaxController.findManagedBean(BatchController.class).find(Long.parseLong(value));
      }
      return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
      if (value instanceof Batch) {
        return ((Batch) value).getBatchNumber() + "";
      }
      return null;
    }
  }
}
