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
  @NamedQuery(name = "client.search_client", query = "SELECT s FROM Client s WHERE s.name LIKE :name")
})
public class Client extends BusinessPartner implements Serializable {

  private static final long serialVersionUID = 32252920781L;
  private BigDecimal creditLimit;
  private BigDecimal openingBalance;

  public void setOpeningBalance(BigDecimal openingBalance) {
    this.openingBalance = openingBalance;
  }

  public BigDecimal getOpeningBalance() {
    return openingBalance;
  }

  public BigDecimal getCreditLimit() {
    return creditLimit;
  }

  public void setCreditLimit(BigDecimal creditLimit) {
    this.creditLimit = creditLimit;
  }
}
