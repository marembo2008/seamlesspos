/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.converter;

import com.anosym.utilities.Utility;
import java.math.BigDecimal;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author marembo
 */
@FacesConverter(value = "bigDecimalConverter", forClass = BigDecimal.class)
public class BigDecimalConverter implements Converter {

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value) {
    return value != null ? Utility.formatCurrencyValue(value) : BigDecimal.ZERO;
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) {
    BigDecimal currencyValue = (BigDecimal) (value instanceof BigDecimal ? value : BigDecimal.ZERO);
    return Utility.formatCurrencyValue(currencyValue);
  }
}
