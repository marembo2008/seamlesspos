/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import com.seamless.internal.util.Gender;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author marembo
 */
@Entity
@NamedQueries({
  @NamedQuery(name = "EMPLOYEE.SEARCH_EMPLOYEE",
          query = "SELECT DISTINCT e FROM Employee e WHERE e.surname LIKE :surname OR e.otherNames LIKE :otherNames OR e.employeeNumber LIKE :empNumber")
})
public class Employee implements Serializable {

  private static final long serialVersionUID = 18482474982L;
  @Id
  @Column(length = 30)
  private String employeeNumber;
  private String id;
  @ManyToOne
  private Store outlet;
  private String surname;
  private String otherNames;
  private String title;
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar hiredDate;
  private BigDecimal salesCommission;
  private BigDecimal basicSalary;
  private String username;
  private String password;
  private String department;
  private Gender gender;
  @OneToOne(cascade = CascadeType.ALL)
  private Address address;
  @OneToOne(cascade = CascadeType.ALL)
  private BankAccountInformation accountInformation;

  public void setAddress(Address address) {
    this.address = address;
  }

  public void setAccountInformation(BankAccountInformation accountInformation) {
    this.accountInformation = accountInformation;
  }

  public Address getAddress() {
    return address;
  }

  public BankAccountInformation getAccountInformation() {
    return accountInformation;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public Gender getGender() {
    return gender;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getOtherNames() {
    return otherNames;
  }

  public void setOtherNames(String otherNames) {
    this.otherNames = otherNames;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Calendar getHiredDate() {
    return hiredDate;
  }

  public void setHiredDate(Calendar hiredDate) {
    this.hiredDate = hiredDate;
  }

  public BigDecimal getSalesCommission() {
    return salesCommission;
  }

  public void setSalesCommission(BigDecimal salesCommission) {
    this.salesCommission = salesCommission;
  }

  public BigDecimal getBasicSalary() {
    return basicSalary;
  }

  public void setBasicSalary(BigDecimal basicSalary) {
    this.basicSalary = basicSalary;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public void setEmployeeNumber(String employeeNumber) {
    this.employeeNumber = employeeNumber;
  }

  public String getEmployeeNumber() {
    return employeeNumber;
  }

  public String getName() {
    return surname + " " + otherNames;
  }

  public Store getOutlet() {
    return outlet;
  }

  public void setOutlet(Store outlet) {
    this.outlet = outlet;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 17 * hash + (this.employeeNumber != null ? this.employeeNumber.hashCode() : 0);
    hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
    hash = 17 * hash + (this.outlet != null ? this.outlet.hashCode() : 0);
    hash = 17 * hash + (this.surname != null ? this.surname.hashCode() : 0);
    hash = 17 * hash + (this.otherNames != null ? this.otherNames.hashCode() : 0);
    hash = 17 * hash + (this.title != null ? this.title.hashCode() : 0);
    hash = 17 * hash + (this.hiredDate != null ? this.hiredDate.hashCode() : 0);
    hash = 17 * hash + (this.salesCommission != null ? this.salesCommission.hashCode() : 0);
    hash = 17 * hash + (this.basicSalary != null ? this.basicSalary.hashCode() : 0);
    hash = 17 * hash + (this.username != null ? this.username.hashCode() : 0);
    hash = 17 * hash + (this.password != null ? this.password.hashCode() : 0);
    hash = 17 * hash + (this.department != null ? this.department.hashCode() : 0);
    hash = 17 * hash + (this.gender != null ? this.gender.hashCode() : 0);
    hash = 17 * hash + (this.address != null ? this.address.hashCode() : 0);
    hash = 17 * hash + (this.accountInformation != null ? this.accountInformation.hashCode() : 0);
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
    if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
      return false;
    }
    if (this.outlet != other.outlet && (this.outlet == null || !this.outlet.equals(other.outlet))) {
      return false;
    }
    if ((this.surname == null) ? (other.surname != null) : !this.surname.equals(other.surname)) {
      return false;
    }
    if ((this.otherNames == null) ? (other.otherNames != null) : !this.otherNames.equals(other.otherNames)) {
      return false;
    }
    if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
      return false;
    }
    if (this.hiredDate != other.hiredDate && (this.hiredDate == null || !this.hiredDate.equals(other.hiredDate))) {
      return false;
    }
    if (this.salesCommission != other.salesCommission && (this.salesCommission == null || !this.salesCommission.equals(other.salesCommission))) {
      return false;
    }
    if (this.basicSalary != other.basicSalary && (this.basicSalary == null || !this.basicSalary.equals(other.basicSalary))) {
      return false;
    }
    if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
      return false;
    }
    if ((this.password == null) ? (other.password != null) : !this.password.equals(other.password)) {
      return false;
    }
    if ((this.department == null) ? (other.department != null) : !this.department.equals(other.department)) {
      return false;
    }
    if (this.gender != other.gender) {
      return false;
    }
    if (this.address != other.address && (this.address == null || !this.address.equals(other.address))) {
      return false;
    }
    if (this.accountInformation != other.accountInformation && (this.accountInformation == null || !this.accountInformation.equals(other.accountInformation))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return getName() + "(" + employeeNumber + ")";
  }
}
