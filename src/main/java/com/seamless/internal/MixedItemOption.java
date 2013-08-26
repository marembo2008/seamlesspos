/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import com.anosym.utilities.IdGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author marembo
 */
@Entity
public class MixedItemOption implements Serializable {

  private static final long serialVersionUID = 14834793L;
  @Id
  private Long id = IdGenerator.generateId();
  @ManyToOne
  private Item item;
  //decimal proportion, should be more than 1.0
  private BigDecimal proportion;

  public MixedItemOption() {
    this(null);
  }

  public MixedItemOption(Item item) {
    this(item, BigDecimal.valueOf(0.0));
  }

  public MixedItemOption(Item item, BigDecimal proportion) {
    this.item = item;
    this.proportion = proportion;
  }

  public void setTaxable(boolean taxable) {
    item.setTaxable(taxable);
  }

  public boolean isTaxable() {
    return item.isTaxable();
  }

  public String getItemCode() {
    return item.getItemCode();
  }

  public void setItemCode(String itemCode) {
    item.setItemCode(itemCode);
  }

  public String getName() {
    return item.getName();
  }

  public void setName(String name) {
    item.setName(name);
  }

  public BigDecimal getCostAmount() {
    return item.getCostAmount().multiply(proportion);
  }

  public BigDecimal getPrice1() {
    return item.getPrice1().multiply(proportion);
  }

  public BigDecimal getPrice2() {
    return item.getPrice2().multiply(proportion);
  }

  public BigDecimal getPrice3() {
    return item.getPrice3().multiply(proportion);
  }

  public int getReorderLevel() {
    return item.getReorderLevel();
  }

  public void setReorderLevel(int reorderLevel) {
    item.setReorderLevel(reorderLevel);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public BigDecimal getProportion() {
    return proportion;
  }

  public void setProportion(BigDecimal proportion) {
    this.proportion = proportion;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 79 * hash + (this.item != null ? this.item.hashCode() : 0);
    hash = 79 * hash + (this.proportion != null ? this.proportion.hashCode() : 0);
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
    final MixedItemOption other = (MixedItemOption) obj;
    if (this.item != other.item && (this.item == null || !this.item.equals(other.item))) {
      return false;
    }
    if (this.proportion != other.proportion && (this.proportion == null || !this.proportion.equals(other.proportion))) {
      return false;
    }
    return true;
  }
}
