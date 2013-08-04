/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales.controller;

import com.seamless.internal.Employee;
import com.seamless.internal.Store;
import com.seamless.internal.sales.Sale;
import com.seamless.internal.sales.facade.SaleFacade;
import com.seamless.internal.sales.util.SaleReportOption;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author marembo
 */
@Named(value = "salesReportController")
@SessionScoped
public class SalesReportController implements Serializable {

  @EJB
  private SaleFacade saleFacade;
  private SaleReportOption saleReportOption;
  private List<Sale> sales;
  private Calendar startDate;
  private Calendar endDate;
  private Employee employee;
  private Store store;

  /**
   * Creates a new instance of SalesReportController
   */
  public SalesReportController() {
  }

  public void setSaleReportOption(SaleReportOption saleReportOption) {
    this.saleReportOption = saleReportOption;
  }

  public SaleReportOption getSaleReportOption() {
    return saleReportOption;
  }

  public SaleReportOption[] getSaleReportOptions() {
    return SaleReportOption.values();
  }

  public void setStore(Store store) {
    this.store = store;
  }

  public Store getStore() {
    return store;
  }

  public boolean isDateRangeRequest() {
    return saleReportOption == SaleReportOption.DATE_RANGE;
  }

  public boolean isEmployeeRequest() {
    return saleReportOption == SaleReportOption.BY_USER;
  }

  public boolean isStoreRequest() {
    return saleReportOption == SaleReportOption.BY_STORE;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public void onSaleReportOptionSelected() {
    generateReport();
  }

  public void generateReport() {
    if (saleReportOption != null) {
      switch (saleReportOption) {
        case ALL_SALES:
          sales = null;
          break;
        case BY_USER:
          if (employee != null) {
            generateEmployeeSalesReport();
          }
          break;
        case DATE_RANGE:
          if (startDate != null && endDate != null) {
            generateDateRangeReport();
          }
          break;
        case BY_STORE:
          if (store != null) {
            generateStoreReport();
          }
          break;
      }
    }
  }

  public void generateStoreReport() {
    sales = saleFacade.findSalesByStore(store);
  }

  public void generateDateRangeReport() {
    sales = saleFacade.findSalesByDate(startDate, endDate);
  }

  public void generateEmployeeSalesReport() {
    sales = saleFacade.findSalesByEmployee(employee);
  }

  public Calendar getEndDate() {
    return endDate;
  }

  public void setEndDate(Calendar endDate) {
    this.endDate = endDate;
  }

  public void setStartDate(Calendar startDate) {
    this.startDate = startDate;
  }

  public Calendar getStartDate() {
    return startDate;
  }

  public List<Sale> getSales() {
    if (sales == null) {
      return sales = saleFacade.findAll();
    }
    return sales;
  }

  public String prepareSalesReport() {
    saleReportOption = SaleReportOption.ALL_SALES;
    sales = null;
    return "sales-reports.xhtml?faces-redirect=true";
  }

  public BigDecimal getTotalSaleAmount() {
    BigDecimal total = BigDecimal.ZERO;
    for (Sale s : getSales()) {
      total = total.add(s.getTotalSale());
    }
    return total;
  }
}
