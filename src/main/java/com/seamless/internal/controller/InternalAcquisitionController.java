/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.Batch;
import com.seamless.internal.InternalTransfer;
import com.seamless.internal.Store;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.facade.BatchFacade;
import com.seamless.internal.facade.InternalTransferFacade;
import com.seamless.internal.facade.StoreFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author marembo
 */
@Named(value = "internalAcquisitionController")
@SessionScoped
public class InternalAcquisitionController implements Serializable {

  @EJB
  private StoreFacade storeFacade;
  @EJB
  private BatchFacade batchFacade;
  @EJB
  private InternalTransferFacade internalTransferFacade;
  private InternalTransfer internalTransfer;

  public InternalTransfer getInternalTransfer() {
    if (internalTransfer == null) {
      internalTransfer = new InternalTransfer();
      internalTransfer.setNewStoreBatch(new Batch());
    }
    return internalTransfer;
  }

  public List<Store> searchStoreForTransfer(String query) {
    List<Store> l = storeFacade.searchStore(query);
    l.remove(getInternalTransfer().getFromStore());
    return l;
  }

  public void onTransferBatchSelected() {
    Batch newBatch = internalTransfer.getNewStoreBatch();
    Batch currentBatch = internalTransfer.getTransferBatch();
    internalTransfer.setFromStore(currentBatch.getStore());
    newBatch.setBestBefore(currentBatch.getBestBefore());
    newBatch.setFrozen(currentBatch.isFrozen());
    newBatch.setItem(currentBatch.getItem());
    newBatch.setManufacturedDate(currentBatch.getManufacturedDate());
    newBatch.setReceivedDate(Calendar.getInstance());
    newBatch.setSupplier(currentBatch.getSupplier());
  }

  public void transferItemBatch() {
    try {
      //reduce current quantity of the other batch
      int currentBatchQuantity = internalTransfer.getTransferBatch().getCurrentQuantity() - internalTransfer.getNewStoreBatch().getQuantity();
      internalTransfer.getTransferBatch().setCurrentQuantity(currentBatchQuantity);
      internalTransfer.getNewStoreBatch().setStore(internalTransfer.getToStore());
      internalTransferFacade.create(internalTransfer);
      batchFacade.edit(internalTransfer.getTransferBatch());
      internalTransfer = null;
      JsfUtil.addSuccessMessage("Internal Transfer Successful");
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Error Performing Internal Transfers");
    }
  }

  public void validateTransferAmount(FacesContext cxt, UIComponent cmp, Object value) {
    try {
      if (value != null && (Integer.parseInt(value.toString())) < internalTransfer.getTransferBatch().getCurrentQuantity()) {
        return;
      }
    } catch (Exception e) {
    }
    String msg = "Please transfer amount equal to or less that the current available items";
    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
  }

  public List<InternalTransfer> getInternalTransfers() {
    return internalTransferFacade.findAll();
  }
}
