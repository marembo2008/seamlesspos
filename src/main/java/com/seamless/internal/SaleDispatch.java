/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import com.anosym.utilities.IdGenerator;
import com.seamless.internal.sales.ItemDispatchOrder;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@NamedQueries({
  @NamedQuery(name = "SALEDISPATCH.SEARCH_SALE_DISPATCH_BY_ID",
          query = "SELECT sd FROM SaleDispatch sd WHERE sd.id LIKE :id")
})
public class SaleDispatch implements Serializable {

  private static final long serialVersionUID = 12478274892L;
  @Id
  @Column(length = 50)
  private String id = IdGenerator.generateStringId();
  @OneToMany(cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<ItemDispatchOrder> dispatchedItems;
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar saleDispatchDate;
  @OneToOne
  private Vehicle dispatchVehicle;
  @OneToOne
  private Employee dispatchEmployee;

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setDispatchedItems(List<ItemDispatchOrder> dispatchedItems) {
    this.dispatchedItems = dispatchedItems;
  }

  public List<ItemDispatchOrder> getDispatchedItems() {
    return dispatchedItems;
  }

  public Calendar getSaleDispatchDate() {
    return saleDispatchDate;
  }

  public void setSaleDispatchDate(Calendar saleDispatchDate) {
    this.saleDispatchDate = saleDispatchDate;
  }

  public Vehicle getDispatchVehicle() {
    return dispatchVehicle;
  }

  public void setDispatchVehicle(Vehicle dispatchVehicle) {
    this.dispatchVehicle = dispatchVehicle;
  }

  public Employee getDispatchEmployee() {
    return dispatchEmployee;
  }

  public void setDispatchEmployee(Employee dispatchEmployee) {
    this.dispatchEmployee = dispatchEmployee;
  }

  public void addItemDispatch(ItemDispatchOrder dispatchOrder) {
    if (dispatchedItems == null) {
      dispatchedItems = new ArrayList<ItemDispatchOrder>();
    }
    dispatchedItems.add(dispatchOrder);
  }

  public BigDecimal getTotalDispatchSaleValue() {
    BigDecimal total = BigDecimal.ZERO;
    if (this.dispatchedItems != null) {
      for (ItemDispatchOrder ido : dispatchedItems) {
        if (ido.getTotalSale() != null) {
          total = total.add(ido.getTotalSale());
        }
      }
    }
    return total;
  }

  public BigDecimal getTotalDispatchSaleDifference() {
    BigDecimal total = BigDecimal.ZERO;
    if (this.dispatchedItems != null) {
      for (ItemDispatchOrder ido : dispatchedItems) {
        if (ido.getSaleDifference() != null) {
          total = total.add(ido.getSaleDifference());
        }
      }
    }
    return total;
  }

  public BigDecimal getTotalDispatchValue() {
    BigDecimal total = BigDecimal.ZERO;
    if (this.dispatchedItems != null) {
      for (ItemDispatchOrder ido : dispatchedItems) {
        if (ido.getDispatchValue() != null) {
          total = total.add(ido.getDispatchValue());
        }
      }
    }
    return total;
  }

  @Override
  public String toString() {
    return "SaleDispatch{" + "id=" + id + ", dispatchedItems=" + dispatchedItems + ", saleDispatchDate=" + saleDispatchDate + ", dispatchVehicle=" + dispatchVehicle + ", dispatchEmployee=" + dispatchEmployee + '}';
  }
}
