/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author marembo
 */
@Entity
@NamedQueries({
  @NamedQuery(name = "SUPPLIER.SEARCH_SUPPLIER", query = "SELECT s FROM Supplier s WHERE s.name LIKE :name")
})
public class Supplier extends BusinessPartner implements Serializable {

  private static final long serialVersionUID = 24289201L;
  private boolean quickSupplier;
  private BigDecimal openingBalance;
  private BigDecimal target;

  public boolean isQuickSupplier() {
    return quickSupplier;
  }

  public void setQuickSupplier(boolean quickSupplier) {
    this.quickSupplier = quickSupplier;
  }

  public BigDecimal getOpeningBalance() {
    return openingBalance;
  }

  public void setOpeningBalance(BigDecimal openingBalance) {
    this.openingBalance = openingBalance;
  }

  public BigDecimal getTarget() {
    return target;
  }

  public void setTarget(BigDecimal target) {
    this.target = target;
  }
}
