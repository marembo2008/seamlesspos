/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales.controller;

import com.seamless.internal.Employee;
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

  public boolean isDateRangeRequest() {
    return saleReportOption == SaleReportOption.DATE_RANGE;
  }

  public boolean isEmployeeRequest() {
    return saleReportOption == SaleReportOption.BY_USER;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public void onSaleReportOptionSelected() {
    if (saleReportOption == SaleReportOption.ALL_SALES) {
      sales = null;
    }
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
