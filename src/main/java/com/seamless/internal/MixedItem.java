/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import com.anosym.utilities.IdGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author marembo
 */
@Entity
public class MixedItem extends Item implements Serializable {

  private static final long serialVersionUID = IdGenerator.serialVersionUID(MixedItem.class);
  @OneToMany(cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Item> items;

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  @Override
  public BigDecimal getCostAmount() {
    if (super.getCostAmount() != null && super.getCostAmount().compareTo(BigDecimal.ZERO) > 0) {
      return super.getCostAmount();
    }
    if (items == null) {
      return BigDecimal.ZERO;
    }
    BigDecimal cost = BigDecimal.ZERO;
    for (Item i : items) {
      cost = cost.add(i.getCostAmount());
    }
    super.setCostAmount(cost);
    return cost;
  }

  @Override
  public void setCostAmount(BigDecimal costAmount) {
    throw new IllegalArgumentException("Cannot set mixed item cost manually");
  }
}
