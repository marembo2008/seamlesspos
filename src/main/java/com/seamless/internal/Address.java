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

/**
 *
 * @author marembo
 */
@Entity
public class Address implements Serializable {

  private static final long serialVersionUID = 14534553L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long addressId;
  private String postalAddress;
  private String city;
  private String telephoneNumber;
  private String email;

  public Long getAddressId() {
    return addressId;
  }

  public void setAddressId(Long addressId) {
    this.addressId = addressId;
  }

  public String getPostalAddress() {
    return postalAddress;
  }

  public void setPostalAddress(String postalAddress) {
    this.postalAddress = postalAddress;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getTelephoneNumber() {
    return telephoneNumber;
  }

  public void setTelephoneNumber(String telephoneNumber) {
    this.telephoneNumber = telephoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 47 * hash + (this.addressId != null ? this.addressId.hashCode() : 0);
    hash = 47 * hash + (this.postalAddress != null ? this.postalAddress.hashCode() : 0);
    hash = 47 * hash + (this.city != null ? this.city.hashCode() : 0);
    hash = 47 * hash + (this.telephoneNumber != null ? this.telephoneNumber.hashCode() : 0);
    hash = 47 * hash + (this.email != null ? this.email.hashCode() : 0);
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
    final Address other = (Address) obj;
    if (this.addressId != other.addressId && (this.addressId == null || !this.addressId.equals(other.addressId))) {
      return false;
    }
    if ((this.postalAddress == null) ? (other.postalAddress != null) : !this.postalAddress.equals(other.postalAddress)) {
      return false;
    }
    if ((this.city == null) ? (other.city != null) : !this.city.equals(other.city)) {
      return false;
    }
    if ((this.telephoneNumber == null) ? (other.telephoneNumber != null) : !this.telephoneNumber.equals(other.telephoneNumber)) {
      return false;
    }
    if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
      return false;
    }
    return true;
  }
}
