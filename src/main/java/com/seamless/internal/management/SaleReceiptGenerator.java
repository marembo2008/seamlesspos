/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.management;

import com.seamless.internal.sales.Sale;
import com.seamless.internal.sales.facade.SaleFacade;
import com.seamless.internal.sales.util.SalesReceipt;
import java.io.Serializable;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author marembo
 */
@Stateless
public class SaleReceiptGenerator implements Serializable {

  @EJB
  private SaleFacade saleFacade;

  public void setSaleReceipt(Sale currentSale) {
    Sale lastSale = saleFacade.lastSale();
    SalesReceipt receiptId = new SalesReceipt(Calendar.getInstance(), 1);
    if (lastSale != null
            && lastSale.getReceiptId().getSaleDate().get(Calendar.DAY_OF_YEAR) == receiptId.getSaleDate().get(Calendar.DAY_OF_YEAR)) {
      /*
       * We compare using the day of year since we only consider the date rather than hours.
       */
      receiptId.setReceiptnumber(lastSale.getReceiptId().getReceiptnumber() + 1);
    }
    currentSale.setReceiptId(receiptId);
  }
}
