/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.pettycash.facade;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.pettycash.PettyCash;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author marembo
 */
@Named(value = "pettyCashController")
@SessionScoped
public class PettyCashController implements Serializable {

  @EJB
  private PettyCashFacade pettyCashFacade;
  private PettyCash pettyCash;

  public void setPettyCash(PettyCash pettyCash) {
    this.pettyCash = pettyCash;
  }

  public PettyCash getPettyCash() {
    if (pettyCash == null) {
      pettyCash = new PettyCash();
    }
    return pettyCash;
  }

  public void createPettyCash() {
    try {
      pettyCashFacade.create(pettyCash);
      pettyCash = null;
      JsfUtil.addSuccessMessage("Created petty cash");
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Error Creating petty cash: " + e.getLocalizedMessage());
    }
  }

  public PettyCash.PettyCashMode[] getPettyCashModes() {
    return PettyCash.PettyCashMode.values();
  }
}
