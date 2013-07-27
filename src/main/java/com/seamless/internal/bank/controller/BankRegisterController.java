/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.bank.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.bank.BankRegister;
import com.seamless.internal.bank.facade.BankRegisterFacade;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.util.BankTransactionType;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author marembo
 */
@Named(value = "bankRegisterController")
@SessionScoped
public class BankRegisterController implements Serializable {

  @EJB
  private BankRegisterFacade bankRegisterFacade;
  private BankRegister bankRegister;

  public BankRegister getBankRegister() {
    if (bankRegister == null) {
      bankRegister = new BankRegister();
    }
    return bankRegister;
  }

  public void setBankRegister(BankRegister bankRegister) {
    this.bankRegister = bankRegister;
  }

  public void addBankRegister() {
    try {
      bankRegisterFacade.create(bankRegister);
      JsfUtil.addSuccessMessage("Successfully added bank register");
      bankRegister = null;
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessages(Arrays.asList("Error adding bank register: " + e.getLocalizedMessage(), bankRegister + ""));
    }
  }

  public BankTransactionType[] getBankTransactionTypes() {
    return BankTransactionType.values();
  }

  public List<BankRegister> getBankRegisters() {
    return bankRegisterFacade.findAll();
  }
}
