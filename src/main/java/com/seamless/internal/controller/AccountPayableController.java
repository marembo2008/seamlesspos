/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.AccountPayable;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.facade.AccountPayableFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author marembo
 */
@Named(value = "accountPayableController")
@SessionScoped
public class AccountPayableController implements Serializable {

  @EJB
  private AccountPayableFacade accountPayableFacade;
  private AccountPayable accountPayable;

  public void setAccountPayable(AccountPayable accountPayable) {
    this.accountPayable = accountPayable;
  }

  public AccountPayable getAccountPayable() {
    if (accountPayable == null) {
      accountPayable = new AccountPayable();
    }
    return accountPayable;
  }

  public void onPurchaseOrderSelected() {
    this.accountPayable.setAmountPayable(this.accountPayable.getPurchaseOrder().getPurchaseOrderCost());
  }

  public void cancel() {
    this.accountPayable = null;
  }

  public void saveAccountPayable() {
    try {
      accountPayableFacade.create(accountPayable);
      JsfUtil.addSuccessMessage("Successfully Saved Account Payable Information");
      accountPayable = null;
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Error Saving Account Payable");
    }
  }
}
