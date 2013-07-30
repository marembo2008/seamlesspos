/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.bank.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.bank.Bank;
import com.seamless.internal.bank.BankBranch;
import com.seamless.internal.bank.BankRegister;
import com.seamless.internal.bank.facade.BankBranchFacade;
import com.seamless.internal.bank.facade.BankFacade;
import com.seamless.internal.bank.facade.BankRegisterFacade;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.util.BankTransactionType;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author marembo
 */
@Named(value = "bankRegisterController")
@SessionScoped
public class BankRegisterController implements Serializable {

  @EJB
  private BankFacade bankFacade;
  @EJB
  private BankBranchFacade bankBranchFacade;
  @EJB
  private BankRegisterFacade bankRegisterFacade;
  private BankRegister bankRegister;
  private String bankingSlipQuery;
  private List<BankRegister> bankRegisters;
  private Bank bank;
  private BankBranch bankBranch;
  private List<Bank> banks;

  public void setBank(Bank bank) {
    this.bank = bank;
  }

  public Bank getBank() {
    if (bank == null) {
      bank = new Bank();
    }
    return bank;
  }

  public void setBankBranch(BankBranch bankBranch) {
    this.bankBranch = bankBranch;
  }

  public BankBranch getBankBranch() {
    if (bankBranch == null) {
      bankBranch = new BankBranch();
    }
    return bankBranch;
  }

  public void updateBank() {
    try {
      bankBranch.getBank().addBranch(bankBranch);
      bankFacade.edit(bankBranch.getBank());
      JsfUtil.addSuccessMessage("bank updated");
      banks = null;
      bankBranch = null;
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Error updating bank");
    }
  }

  public List<Bank> searchBanks(String query) {
    return bankFacade.searchBanks(query);
  }

  public List<BankBranch> searchBankBranches(String query) {
    return bankBranchFacade.searchBankBranches(query);
  }

  public List<Bank> getBanks() {
    if (banks == null) {
      return banks = bankFacade.findAll();
    }
    return banks;
  }

  public void addBank() {
    try {
      bankFacade.create(bank);
      bank = null;
      banks = null;
      JsfUtil.addSuccessMessage("Bank added");
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Error adding bank");
    }
  }

  public BankRegister getBankRegister() {
    if (bankRegister == null) {
      bankRegister = new BankRegister();
    }
    return bankRegister;
  }

  public void setBankingSlipQuery(String bankingSlipQuery) {
    this.bankingSlipQuery = bankingSlipQuery;
  }

  public String getBankingSlipQuery() {
    return bankingSlipQuery;
  }

  public void searchBankRegisters() {
    bankRegisters = bankRegisterFacade.searchBankRegisters(bankingSlipQuery);
  }

  public void setBankRegister(BankRegister bankRegister) {
    this.bankRegister = bankRegister;
  }

  public void addBankRegister() {
    try {
      bankRegisterFacade.create(bankRegister);
      JsfUtil.addSuccessMessage("Successfully added bank register");
      bankRegister = null;
      bankRegisters = null;
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessages(Arrays.asList("Error adding bank register: " + e.getLocalizedMessage(), bankRegister + ""));
    }
  }

  public BankTransactionType[] getBankTransactionTypes() {
    return BankTransactionType.values();
  }

  public List<BankRegister> getBankRegisters() {
    if (bankRegisters == null) {
      return bankRegisters = bankRegisterFacade.findAll();
    }
    return bankRegisters;
  }

  @FacesConverter("bankConverter")
  public static class BankConverter implements Converter {

    private static Map<String, Bank> map = new HashMap<String, Bank>();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      return map.get(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
      if (value instanceof Bank) {
        Bank b = (Bank) value;
        map.put(b.getId() + "", b);
        return b.getId() + "";
      }
      return null;
    }
  }

  @FacesConverter("bankBranchConverter")
  public static class BankBranchConverter implements Converter {

    private static Map<String, BankBranch> map = new HashMap<String, BankBranch>();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      return map.get(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
      if (value instanceof BankBranch) {
        BankBranch b = (BankBranch) value;
        map.put(b.getId() + "", b);
        return b.getId() + "";
      }
      return null;
    }
  }
}
