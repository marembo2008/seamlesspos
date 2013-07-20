/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author marembo
 */
@Entity
public class BankAccountInformation implements Serializable {

  private static final long serialVersionUID = 3389478924492871L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long accountInfoId;
  private String bankName;
  private String branchCode;
  private String accountName;
  private String accountNumber;

  public BankAccountInformation() {
  }

  public BankAccountInformation(String bankName, String branchCode, String accountName, String accountNumber) {
    this.bankName = bankName;
    this.branchCode = branchCode;
    this.accountName = accountName;
    this.accountNumber = accountNumber;
  }

  public void setAccountInfoId(Long accountInfoId) {
    this.accountInfoId = accountInfoId;
  }

  public Long getAccountInfoId() {
    return accountInfoId;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getBranchCode() {
    return branchCode;
  }

  public void setBranchCode(String branchCode) {
    this.branchCode = branchCode;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }
}
