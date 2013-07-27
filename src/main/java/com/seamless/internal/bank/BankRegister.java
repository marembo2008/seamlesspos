/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.bank;

import com.seamless.internal.Employee;
import com.seamless.internal.util.BankTransactionType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author marembo
 */
@Entity
public class BankRegister implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(unique = true, length = 40)
  private String slipNumber;
  private String bankName;
  private String bankBranch;
  private String accountNumber;
  private String drawer;
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar depositedDate;
  private BigDecimal amount;
  @OneToOne
  private Employee depositedBy;
  private BankTransactionType transactionType;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSlipNumber() {
    return slipNumber;
  }

  public void setSlipNumber(String slipNumber) {
    this.slipNumber = slipNumber;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getBankBranch() {
    return bankBranch;
  }

  public void setBankBranch(String bankBranch) {
    this.bankBranch = bankBranch;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getDrawer() {
    return drawer;
  }

  public void setDrawer(String drawer) {
    this.drawer = drawer;
  }

  public Calendar getDepositedDate() {
    return depositedDate;
  }

  public void setDepositedDate(Calendar depositedDate) {
    this.depositedDate = depositedDate;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Employee getDepositedBy() {
    return depositedBy;
  }

  public void setDepositedBy(Employee depositedBy) {
    this.depositedBy = depositedBy;
  }

  public BankTransactionType getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(BankTransactionType transactionType) {
    this.transactionType = transactionType;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
    hash = 47 * hash + (this.slipNumber != null ? this.slipNumber.hashCode() : 0);
    hash = 47 * hash + (this.bankName != null ? this.bankName.hashCode() : 0);
    hash = 47 * hash + (this.bankBranch != null ? this.bankBranch.hashCode() : 0);
    hash = 47 * hash + (this.accountNumber != null ? this.accountNumber.hashCode() : 0);
    hash = 47 * hash + (this.drawer != null ? this.drawer.hashCode() : 0);
    hash = 47 * hash + (this.depositedDate != null ? this.depositedDate.hashCode() : 0);
    hash = 47 * hash + (this.amount != null ? this.amount.hashCode() : 0);
    hash = 47 * hash + (this.depositedBy != null ? this.depositedBy.hashCode() : 0);
    hash = 47 * hash + (this.transactionType != null ? this.transactionType.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final BankRegister other = (BankRegister) obj;
    if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
      return false;
    }
    if ((this.slipNumber == null) ? (other.slipNumber != null) : !this.slipNumber.equals(other.slipNumber)) {
      return false;
    }
    if ((this.bankName == null) ? (other.bankName != null) : !this.bankName.equals(other.bankName)) {
      return false;
    }
    if ((this.bankBranch == null) ? (other.bankBranch != null) : !this.bankBranch.equals(other.bankBranch)) {
      return false;
    }
    if ((this.accountNumber == null) ? (other.accountNumber != null) : !this.accountNumber.equals(other.accountNumber)) {
      return false;
    }
    if ((this.drawer == null) ? (other.drawer != null) : !this.drawer.equals(other.drawer)) {
      return false;
    }
    if (this.depositedDate != other.depositedDate && (this.depositedDate == null || !this.depositedDate.equals(other.depositedDate))) {
      return false;
    }
    if (this.amount != other.amount && (this.amount == null || !this.amount.equals(other.amount))) {
      return false;
    }
    if (this.depositedBy != other.depositedBy && (this.depositedBy == null || !this.depositedBy.equals(other.depositedBy))) {
      return false;
    }
    if (this.transactionType != other.transactionType) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "BankRegister{" + "id=" + id + ", slipNumber=" + slipNumber + ", bankName=" + bankName + ", bankBranch=" + bankBranch + ", accountNumber=" + accountNumber + ", drawer=" + drawer + ", depositedDate=" + depositedDate + ", amount=" + amount + ", depositedBy=" + depositedBy + ", transactionType=" + transactionType + '}';
  }
}
