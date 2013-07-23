/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.purchases.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.purchases.PurchasesReturn;
import com.seamless.internal.purchases.facade.PurchasesReturnFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
@Named(value = "purchasesReturnController")
@SessionScoped
public class PurchasesReturnController implements Serializable {

  @EJB
  private PurchasesReturnFacade purchasesReturnFacade;
  private PurchasesReturn purchasesReturn;

  public void setPurchasesReturn(PurchasesReturn purchasesReturn) {
    this.purchasesReturn = purchasesReturn;
  }

  public PurchasesReturn getPurchasesReturn() {
    if (purchasesReturn == null) {
      purchasesReturn = new PurchasesReturn();
    }
    return purchasesReturn;
  }

  public void validateReturnAmount(FacesContext cxt, UIComponent component, Object value) {
    try {
      if (value != null) {
        int returnQuantity = Integer.parseInt(value.toString());
        if (returnQuantity < purchasesReturn.getBatch().getQuantity()) {
          return;
        }
      }
    } catch (Exception ex) {
      JFlemaxController.logError(ex);
    }
    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
            "You have specified no return amount or the return amount specified is greater than the batch ordered quantity", null));
  }

  public void returnGoods() {
    try {
      purchasesReturnFacade.create(purchasesReturn);
      /**
       * TODO(marembo) When return is made, be sure to update logic for finding the number of
       * available items during sale and sale dispatch.
       */
      purchasesReturn = null;
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Error returning goods: " + e.getLocalizedMessage());
    }
  }

  public List<PurchasesReturn> getPurchasesReturns() {
    return purchasesReturnFacade.findAll();
  }
}
