/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales;

import com.anosym.utilities.IdGenerator;
import com.seamless.internal.Client;
import com.seamless.internal.Item;
import com.seamless.internal.SaleDispatch;
import com.seamless.internal.sales.util.SaleStatus;
import com.seamless.internal.sales.util.SalesReceipt;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author marembo
 */
@Entity
@NamedQueries({
  @NamedQuery(name = "sale.search_sales_by_receipt_id",
          query = "SELECT s FROM Sale s WHERE s.receiptId_ LIKE :receiptId_ AND s.status != :status"),
  @NamedQuery(name = "sale.search_sales_by_receipt_id_and_status",
          query = "SELECT s FROM Sale s WHERE s.receiptId_ LIKE :receiptId_ AND s.status = :status"),
  @NamedQuery(name = "sale.find_sales_by_receipt_id",
          query = "SELECT s FROM Sale s WHERE s.receiptId_ = :receiptId_"),
  @NamedQuery(name = "sale.find_sales_by_date_range",
          query = "SELECT s FROM Sale s WHERE s.receiptId.saleDate BETWEEN :startDate AND :endDate"),
  @NamedQuery(name = "sale.find_sales_by_employee",
          query = "SELECT s FROM Sale s WHERE s.saleDispatch.dispatchEmployee.employeeNumber = :employeeNumber")
})
public class Sale implements Serializable {

  private static final long serialVersionUID = IdGenerator.serialVersionUID(Sale.class);
  @EmbeddedId
  private SalesReceipt receiptId;
  private String receiptId_; //purely for search
  @OneToOne
  private Client customer;
  @OneToMany(cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<SaleItem> saleItems;
  private BigDecimal totalSale; //for convinience
  private BigDecimal tenderedAmount; //the amount the customer gave
  private BigDecimal tenderedChange; //the change given to client
  @OneToMany(cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Payment> payments;
  private SaleStatus status;
  @Transient
  private Map<String, SaleItem> modelSales;
  /**
   * Not null if this was done through sale dispatch collection
   */
  @OneToOne
  private SaleDispatch saleDispatch;

  public Sale() {
    status = SaleStatus.PENDING;
    modelSales = new HashMap<String, SaleItem>();
  }

  /**
   * If the status of the sale is pending, we change it to sold on save, otherwise it may have been
   * held.
   */
  @PrePersist
  @PreUpdate
  void onSave() {
    if (status == SaleStatus.PENDING) {
      status = SaleStatus.SOLD;
    }
    receiptId_ = receiptId.toString();
  }

  public String getReceiptId_() {
    return receiptId_;
  }

  public void setSaleDispatch(SaleDispatch saleDispatch) {
    this.saleDispatch = saleDispatch;
  }

  public SaleDispatch getSaleDispatch() {
    return saleDispatch;
  }

  public int getOrderedQuantity(Item i) {
    int orderQuantity = 0;
    for (SaleItem si : getSaleItems()) {
      if (si.getItem() != null && si.getItem().equals(i)) {
        orderQuantity += si.getOrderedQuantity();
      }
    }
    return orderQuantity;
  }

  public void addSaleItem(SaleItem saleItem) {
    if (!getSaleItems().contains(saleItem)) {
      getSaleItems().add(saleItem);
    }
    if (modelSales == null) {
      modelSales = new HashMap<String, SaleItem>();
    }
    if (saleItem.getItem() != null) {
      modelSales.put(saleItem.getItem().getItemCode(), saleItem);
    }
  }

  public void removeSaleItem(SaleItem item) {
    getSaleItems().remove(item);
    modelSales.remove(item.getItem().getItemCode());
  }

  public SaleItem getSaleItem(String itemCode) {
    return itemCode != null ? modelSales.get(itemCode) : null;
  }

  public void setReceiptId(SalesReceipt receiptId) {
    this.receiptId = receiptId;
  }

  public SaleStatus getStatus() {
    return status;
  }

  public void setStatus(SaleStatus status) {
    this.status = status;
  }

  public SalesReceipt getReceiptId() {
    return receiptId;
  }

  public Client getCustomer() {
    return customer;
  }

  public void setCustomer(Client customer) {
    this.customer = customer;
  }

  public List<SaleItem> getSaleItems() {
    if (saleItems == null) {
      saleItems = new ArrayList<SaleItem>();
      saleItems.add(new SaleItem());
    }
    return saleItems;
  }

  public void setSaleItems(List<SaleItem> saleItems) {
    this.saleItems = saleItems;
  }

  public int getTotalOrderedQuantity() {
    int total = 0;
    for (SaleItem i : saleItems) {
      total += i.getOrderedQuantity();
    }
    return total;
  }

  public BigDecimal getTotalSale() {
    BigDecimal total = BigDecimal.ZERO;
    for (SaleItem i : getSaleItems()) {
      total = total.add(i.getTotalSale());
    }
    return totalSale = total;
  }

  public void setTotalSale(BigDecimal totalSale) {
    this.totalSale = totalSale;
  }

  public BigDecimal getTenderedAmount() {
    return tenderedAmount == null ? (tenderedAmount = BigDecimal.ZERO) : tenderedAmount;
  }

  public void setTenderedAmount(BigDecimal tenderedAmount) {
    this.tenderedAmount = tenderedAmount;
  }

  public BigDecimal getChange() {
    return tenderedChange = (getTotalSale().subtract(getTenderedAmount()));
  }

  public void setChange(BigDecimal change) {
    this.tenderedChange = change;
  }

  public void setPayments(List<Payment> payments) {
    this.payments = payments;
  }

  public List<Payment> getPayments() {
    return payments;
  }

  public void addPayment(Payment payment) {
    if (payments == null) {
      payments = new ArrayList<Payment>();
    }
    payments.add(payment);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 73 * hash + (this.receiptId != null ? this.receiptId.hashCode() : 0);
    hash = 73 * hash + (this.receiptId_ != null ? this.receiptId_.hashCode() : 0);
    hash = 73 * hash + (this.customer != null ? this.customer.hashCode() : 0);
    hash = 73 * hash + (this.saleItems != null ? this.saleItems.hashCode() : 0);
    hash = 73 * hash + (this.totalSale != null ? this.totalSale.hashCode() : 0);
    hash = 73 * hash + (this.tenderedAmount != null ? this.tenderedAmount.hashCode() : 0);
    hash = 73 * hash + (this.tenderedChange != null ? this.tenderedChange.hashCode() : 0);
    hash = 73 * hash + (this.status != null ? this.status.hashCode() : 0);
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
    final Sale other = (Sale) obj;
    if (this.receiptId != other.receiptId && (this.receiptId == null || !this.receiptId.equals(other.receiptId))) {
      return false;
    }
    if ((this.receiptId_ == null) ? (other.receiptId_ != null) : !this.receiptId_.equals(other.receiptId_)) {
      return false;
    }
    if (this.customer != other.customer && (this.customer == null || !this.customer.equals(other.customer))) {
      return false;
    }
    if (this.saleItems != other.saleItems && (this.saleItems == null || !this.saleItems.equals(other.saleItems))) {
      return false;
    }
    if (this.totalSale != other.totalSale && (this.totalSale == null || !this.totalSale.equals(other.totalSale))) {
      return false;
    }
    if (this.tenderedAmount != other.tenderedAmount && (this.tenderedAmount == null || !this.tenderedAmount.equals(other.tenderedAmount))) {
      return false;
    }
    if (this.tenderedChange != other.tenderedChange && (this.tenderedChange == null || !this.tenderedChange.equals(other.tenderedChange))) {
      return false;
    }
    if (this.status != other.status) {
      return false;
    }
    return true;
  }

  public void clearPayments() {
    payments = null;
  }

  public Payment getPayment() {
    return payments == null || payments.isEmpty() ? null : payments.get(0);
  }

  public void setPayment(Payment cashPayment) {
    clearPayments();
    addPayment(cashPayment);
  }
}
