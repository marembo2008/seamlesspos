/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.AccountPayable;
import com.seamless.internal.ExpenseAccountPayable;
import com.seamless.internal.PurchaseOrderAccountPayable;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.facade.AccountPayableFacade;
import com.seamless.internal.util.AccountPayableOption;
import com.seamless.internal.util.AccountPayableReportOption;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
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
  private AccountPayableOption accountPayableOption;
  private List<AccountPayable> accountPayables;
  private Calendar start;
  private Calendar end;
  private AccountPayableReportOption accountPayableReportOption;

  public void generateAccountPayableReports() {
    accountPayables = accountPayableFacade.findAccountPayableWithinRange(start, end);
  }

  public AccountPayableReportOption[] getAccountPayableReportOptions() {
    return AccountPayableReportOption.values();
  }

  public void setAccountPayableReportOption(AccountPayableReportOption accountPayableReportOption) {
    this.accountPayableReportOption = accountPayableReportOption;
  }

  public void onAccountPayableReportOptionSelected() {
    if (accountPayableReportOption == AccountPayableReportOption.All_ACCOUNTS) {
      accountPayables = null;
    } else if (start != null && end != null) {
      generateAccountPayableReports();
    }
  }

  public void setEnd(Calendar end) {
    this.end = end;
  }

  public Calendar getEnd() {
    return end;
  }

  public void setStart(Calendar start) {
    this.start = start;
  }

  public Calendar getStart() {
    return start;
  }

  public BigDecimal getTotalAccountPayable() {
    BigDecimal v = BigDecimal.ZERO;
    for (AccountPayable ap : getAccountPayables()) {
      v = v.add(ap.getAmountPayable());
    }
    return v;
  }

  public boolean isDateRangeSelected() {
    return accountPayableReportOption == AccountPayableReportOption.DATE;
  }

  public String prepareAccountPayableReport() {
    accountPayableReportOption = AccountPayableReportOption.All_ACCOUNTS;
    accountPayables = null;
    return "account-payable-report.xhtml?faces-redirect=true";
  }

  public AccountPayableReportOption getAccountPayableReportOption() {
    return accountPayableReportOption;
  }

  public void setAccountPayableOption(AccountPayableOption accountPayableOption) {
    this.accountPayableOption = accountPayableOption;
  }

  public AccountPayableOption getAccountPayableOption() {
    return accountPayableOption;
  }

  public AccountPayableOption[] getAccountPayableOptions() {
    return AccountPayableOption.values();
  }

  public void setAccountPayables(List<AccountPayable> accountPayables) {
    this.accountPayables = accountPayables;
  }

  public List<AccountPayable> getAccountPayables() {
    if (accountPayables == null) {
      accountPayables = accountPayableFacade.findAll();
    }
    return accountPayables;
  }

  public boolean isExpenseAccountPayable() {
    return accountPayableOption == AccountPayableOption.EXPENSE;
  }

  public void onAccountPayableOptionSelected() {
    if (isExpenseAccountPayable()) {
      accountPayable = new ExpenseAccountPayable();
    } else {
      accountPayable = new PurchaseOrderAccountPayable();
    }
  }

  public boolean isAccountPayableOptionSelected() {
    return accountPayableOption != null;
  }

  public void setAccountPayable(AccountPayable accountPayable) {
    this.accountPayable = accountPayable;
  }

  public AccountPayable getAccountPayable() {
    return accountPayable;
  }

  public void onPurchaseOrderSelected() {
    if (!isExpenseAccountPayable() && accountPayable != null) {
      this.accountPayable.setAmountPayable(((PurchaseOrderAccountPayable) this.accountPayable).getPurchaseOrder().getPurchaseOrderCost());
    }
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
