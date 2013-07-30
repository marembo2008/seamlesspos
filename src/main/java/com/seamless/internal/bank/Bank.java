/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author marembo
 */
@Entity
@NamedQueries({
  @NamedQuery(name = "BANK.SEARCH_BANK_BY_NAME", query = "SELECT b FROM Bank b WHERE b.bankName LIKE :bankName")
})
public class Bank implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String bankName;
  @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<BankBranch> bankBranches;

  public void setBankBranches(List<BankBranch> bankBranches) {
    this.bankBranches = bankBranches;
  }

  public List<BankBranch> getBankBranches() {
    return bankBranches;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
    hash = 53 * hash + (this.bankName != null ? this.bankName.hashCode() : 0);
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
    final Bank other = (Bank) obj;
    if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
      return false;
    }
    if ((this.bankName == null) ? (other.bankName != null) : !this.bankName.equals(other.bankName)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return bankName;
  }

  public void addBranch(BankBranch bankBranch) {
    if (bankBranches == null) {
      bankBranches = new ArrayList<BankBranch>();
    }
    bankBranches.add(bankBranch);
  }
}
