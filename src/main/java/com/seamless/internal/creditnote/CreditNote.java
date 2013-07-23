/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.creditnote;

import com.anosym.utilities.IdGenerator;
import com.seamless.internal.converter.DateConverter;
import com.seamless.internal.sales.Sale;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author marembo
 */
@Entity
public class CreditNote implements Serializable {

  private static final long serialVersionUID = 124672482974L;
  @Id
  @Column(length = 50)
  private String creditNoteNumber = IdGenerator.generateId("CR//");
  @OneToOne
  private Sale sale;
  @OneToMany(cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<SalesReturn> salesReturns;
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar issuedDate;

  public CreditNote() {
    issuedDate = Calendar.getInstance();
  }

  public void setCreditNoteNumber(String creditNoteNumber) {
    this.creditNoteNumber = creditNoteNumber;
  }

  public String getCreditNoteNumber() {
    return creditNoteNumber;
  }

  public BigDecimal getTotalCreditNoteAmount() {
    BigDecimal total = BigDecimal.ZERO;
    for (SalesReturn salesReturn : getSalesReturns()) {
      total = total.add(salesReturn.getSaleReturnAmount());
    }
    return total;
  }

  public Sale getSale() {
    return sale;
  }

  public void setSale(Sale sale) {
    this.sale = sale;
  }

  public void setSalesReturns(List<SalesReturn> salesReturns) {
    this.salesReturns = salesReturns;
  }

  public List<SalesReturn> getSalesReturns() {
    if (salesReturns == null) {
      salesReturns = new ArrayList<SalesReturn>();
    }
    return salesReturns;
  }

  public String getIssuedDateString() {
    return new DateConverter().getAsString(null, null, issuedDate);
  }

  public Calendar getIssuedDate() {
    return issuedDate;
  }

  public void setIssuedDate(Calendar issuedDate) {
    this.issuedDate = issuedDate;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 19 * hash + (this.creditNoteNumber != null ? this.creditNoteNumber.hashCode() : 0);
    hash = 19 * hash + (this.sale != null ? this.sale.hashCode() : 0);
    hash = 19 * hash + (this.salesReturns != null ? this.salesReturns.hashCode() : 0);
    hash = 19 * hash + (this.issuedDate != null ? this.issuedDate.hashCode() : 0);
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
    final CreditNote other = (CreditNote) obj;
    if (this.creditNoteNumber != other.creditNoteNumber && (this.creditNoteNumber == null || !this.creditNoteNumber.equals(other.creditNoteNumber))) {
      return false;
    }
    if (this.sale != other.sale && (this.sale == null || !this.sale.equals(other.sale))) {
      return false;
    }
    if (this.salesReturns != other.salesReturns && (this.salesReturns == null || !this.salesReturns.equals(other.salesReturns))) {
      return false;
    }
    if (this.issuedDate != other.issuedDate && (this.issuedDate == null || !this.issuedDate.equals(other.issuedDate))) {
      return false;
    }
    return true;
  }

  public void addSaleReturn(SalesReturn sr) {
    getSalesReturns().add(sr);
  }
}
