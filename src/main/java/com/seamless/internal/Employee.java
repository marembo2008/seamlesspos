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
import javax.persistence.ManyToOne;

/**
 *
 * @author marembo
 */
@Entity
public class Employee implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long employeeNumber;
  private String name;
  @ManyToOne
  private Store outlet;

  public Long getEmployeeNumber() {
    return employeeNumber;
  }

  public void setEmployeeNumber(Long employeeNumber) {
    this.employeeNumber = employeeNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Store getOutlet() {
    return outlet;
  }

  public void setOutlet(Store outlet) {
    this.outlet = outlet;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 23 * hash + (this.employeeNumber != null ? this.employeeNumber.hashCode() : 0);
    hash = 23 * hash + (this.name != null ? this.name.hashCode() : 0);
    hash = 23 * hash + (this.outlet != null ? this.outlet.hashCode() : 0);
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
    final Employee other = (Employee) obj;
    if (this.employeeNumber != other.employeeNumber && (this.employeeNumber == null || !this.employeeNumber.equals(other.employeeNumber))) {
      return false;
    }
    if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
      return false;
    }
    if (this.outlet != other.outlet && (this.outlet == null || !this.outlet.equals(other.outlet))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return name + "(" + employeeNumber + ")";
  }
}
