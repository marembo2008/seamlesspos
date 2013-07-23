/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.Batch;
import com.seamless.internal.InternalTransfer;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.facade.BatchFacade;
import com.seamless.internal.facade.InternalTransferFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author marembo
 */
@Named(value = "internalAcquisitionController")
@SessionScoped
public class InternalAcquisitionController implements Serializable {

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

  public void onTransferBatchSelected() {
    Batch newBatch = internalTransfer.getNewStoreBatch();
    Batch currentBatch = internalTransfer.getTransferBatch();
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
      internalTransferFacade.create(internalTransfer);
      batchFacade.edit(internalTransfer.getTransferBatch());
      internalTransfer = null;
      JsfUtil.addSuccessMessage("Internal Transfer Successful");
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Error Performing Internal Transfers");
    }
  }

  public List<InternalTransfer> getInternalTransfers() {
    return internalTransferFacade.findAll();
  }
}
