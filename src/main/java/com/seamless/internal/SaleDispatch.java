/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import com.anosym.utilities.IdGenerator;
import com.seamless.internal.sales.ItemDispatchOrder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
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
public class SaleDispatch implements Serializable {

  private static final long serialVersionUID = 12478274892L;
  @Id
  private Long id = IdGenerator.generateId();
  @OneToMany(cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<ItemDispatchOrder> dispatchedItems;
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar saleDispatchDate;
  @OneToOne
  private Vehicle dispatchVehicle;
  @OneToOne
  private Employee dispatchEmployee;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
}
