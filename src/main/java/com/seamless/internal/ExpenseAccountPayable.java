/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author marembo
 */
@Entity
public class ExpenseAccountPayable extends AccountPayable implements Serializable {

  private String expense;

  public String getExpense() {
    return expense;
  }

  public void setExpense(String expense) {
    this.expense = expense;
  }

  @Override
  public String toString() {
    return expense;
  }

  @Override
  public String getDescription() {
    return toString();
  }
}
