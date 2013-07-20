/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author marembo
 */
@Entity
@NamedQueries({
  @NamedQuery(name = "VEHICLE.SEARCH_VEHICLE", query = "SELECT v FROM Vehicle v WHERE v.registrationNumber LIKE :regNumber")
})
public class Vehicle implements Serializable {

  private static final long serialVersionUID = 2462879801L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(unique = true, nullable = false, length = 20)
  private String registrationNumber;

  public void setRegistrationNumber(String registrationNumber) {
    this.registrationNumber = registrationNumber;
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return registrationNumber;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
    hash = 47 * hash + (this.registrationNumber != null ? this.registrationNumber.hashCode() : 0);
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
    final Vehicle other = (Vehicle) obj;
    if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
      return false;
    }
    if ((this.registrationNumber == null) ? (other.registrationNumber != null) : !this.registrationNumber.equals(other.registrationNumber)) {
      return false;
    }
    return true;
  }
}
