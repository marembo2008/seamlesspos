/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author marembo
 */
@Entity
public class BusinessPartnerInfo implements Serializable {

  private static final long serialVersionUID = 134234082L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long infoId;
  @OneToOne
  private BusinessPartner businessPartner;
  private BigDecimal discount;
  private BigDecimal openingBalance;
  @OneToOne
  private Employee clientManager; //sales man in charge of this client.
  private BigDecimal creditLimit;

  public Long getInfoId() {
    return infoId;
  }

  public void setInfoId(Long infoId) {
    this.infoId = infoId;
  }

  public BusinessPartner getBusinessPartner() {
    return businessPartner;
  }

  public void setBusinessPartner(BusinessPartner businessPartner) {
    this.businessPartner = businessPartner;
  }

  public BigDecimal getDiscount() {
    return discount;
  }

  public void setDiscount(BigDecimal discount) {
    this.discount = discount;
  }

  public BigDecimal getOpeningBalance() {
    return openingBalance;
  }

  public void setOpeningBalance(BigDecimal openingBalance) {
    this.openingBalance = openingBalance;
  }

  public Employee getClientManager() {
    return clientManager;
  }

  public void setClientManager(Employee clientManager) {
    this.clientManager = clientManager;
  }

  public BigDecimal getCreditLimit() {
    return creditLimit;
  }

  public void setCreditLimit(BigDecimal creditLimit) {
    this.creditLimit = creditLimit;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 29 * hash + (this.infoId != null ? this.infoId.hashCode() : 0);
    hash = 29 * hash + (this.businessPartner != null ? this.businessPartner.hashCode() : 0);
    hash = 29 * hash + (this.discount != null ? this.discount.hashCode() : 0);
    hash = 29 * hash + (this.openingBalance != null ? this.openingBalance.hashCode() : 0);
    hash = 29 * hash + (this.clientManager != null ? this.clientManager.hashCode() : 0);
    hash = 29 * hash + (this.creditLimit != null ? this.creditLimit.hashCode() : 0);
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
    final BusinessPartnerInfo other = (BusinessPartnerInfo) obj;
    if (this.infoId != other.infoId && (this.infoId == null || !this.infoId.equals(other.infoId))) {
      return false;
    }
    if (this.businessPartner != other.businessPartner && (this.businessPartner == null || !this.businessPartner.equals(other.businessPartner))) {
      return false;
    }
    if (this.discount != other.discount && (this.discount == null || !this.discount.equals(other.discount))) {
      return false;
    }
    if (this.openingBalance != other.openingBalance && (this.openingBalance == null || !this.openingBalance.equals(other.openingBalance))) {
      return false;
    }
    if (this.clientManager != other.clientManager && (this.clientManager == null || !this.clientManager.equals(other.clientManager))) {
      return false;
    }
    if (this.creditLimit != other.creditLimit && (this.creditLimit == null || !this.creditLimit.equals(other.creditLimit))) {
      return false;
    }
    return true;
  }
}
