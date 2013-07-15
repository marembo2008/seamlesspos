/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.settings;

import com.anosym.vjax.converter.v3.impl.DefaultBigDecimalConverter;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author marembo
 */
public class Setting implements Serializable {

  private static final long serialVersionUID = 1246247280L;
  /**
   * The vat percentage charged on products.
   */
  private BigDecimal vat;

  public Setting() {
  }

  @com.anosym.vjax.annotations.v3.Converter(DefaultBigDecimalConverter.class)
  public BigDecimal getVat() {
    return vat;
  }

  public void setVat(BigDecimal vat) {
    this.vat = vat;
  }
}
