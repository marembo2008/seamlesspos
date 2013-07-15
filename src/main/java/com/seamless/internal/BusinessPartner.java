/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import com.anosym.utilities.IdGenerator;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author marembo
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BusinessPartner implements Serializable {

  private static final long serialVersionUID = IdGenerator.serialVersionUID(BusinessPartner.class);
  @Id
  private Long id = IdGenerator.generateId();
  private String name;
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar registrationDate;
  @OneToOne(cascade = CascadeType.ALL)
  private Address address;

  public BusinessPartner() {
    registrationDate = Calendar.getInstance();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Calendar getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(Calendar registrationDate) {
    this.registrationDate = registrationDate;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return getName();
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
    hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
    hash = 53 * hash + (this.registrationDate != null ? this.registrationDate.hashCode() : 0);
    hash = 53 * hash + (this.address != null ? this.address.hashCode() : 0);
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
    final BusinessPartner other = (BusinessPartner) obj;
    if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
      return false;
    }
    if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
      return false;
    }
    if (this.registrationDate != other.registrationDate && (this.registrationDate == null || !this.registrationDate.equals(other.registrationDate))) {
      return false;
    }
    if (this.address != other.address && (this.address == null || !this.address.equals(other.address))) {
      return false;
    }
    return true;
  }
}
