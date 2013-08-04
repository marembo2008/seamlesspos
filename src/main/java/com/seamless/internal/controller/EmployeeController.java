/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.anosym.utilities.IdGenerator;
import com.seamless.internal.Address;
import com.seamless.internal.BankAccountInformation;
import com.seamless.internal.Employee;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.facade.EmployeeFacade;
import com.seamless.internal.util.Gender;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author marembo
 */
@Named(value = "employeeController")
@SessionScoped
public class EmployeeController implements Serializable {

  private class EmployeeDataModel extends ListDataModel<Employee> implements SelectableDataModel<Employee>, Serializable {

    public EmployeeDataModel() {
      super(employeeFacade.findAll());
    }

    @Override
    public Object getRowKey(Employee t) {
      return t.getEmployeeNumber();
    }

    @Override
    public Employee getRowData(String string) {
      return employeeFacade.find(Long.parseLong(string));
    }
  }
  private static final long serialVersionUID = 1782378272888L;
  @EJB
  private EmployeeFacade employeeFacade;
  @Inject
  private SeamlessController seamlessController;
  private Employee currentEmployee;
  private DataModel<Employee> employees;
  private String username;
  private String password;

  //get default employee
  @PostConstruct
  void createDefaultEmployee() {
    String empNumber = IdGenerator.serialVersionUID(Employee.class) + "";
    if (employeeFacade.find(empNumber) == null) {
      Employee e = new Employee();
      e.setEmployeeNumber(empNumber);
      e.setUsername("admin");
      e.setPassword("adminadmin");
      e.setSurname("Admin");
      e.setOtherNames("Admin");
      employeeFacade.create(e);
    }
  }

  void onLoad() {
    employees = new EmployeeDataModel();
  }

  public List<Employee> searchEmployees(String query) {
    return employeeFacade.searchEmployees(query);
  }

  public String login() {
    Employee e = employeeFacade.findEmployee(username, password);
    if (e != null) {
      seamlessController.onLogin(e);
      return "store.xhtml?faces-redirect=true";
    }
    JsfUtil.addErrorMessage("Login failed. Username or password incorrect");
    return null;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Employee getCurrentEmployee() {
    if (currentEmployee == null) {
      prepareCreate();
    }
    return currentEmployee;
  }

  private void prepareCreate() {
    currentEmployee = new Employee();
    currentEmployee.setAccountInformation(new BankAccountInformation());
    currentEmployee.setAddress(new Address());
  }

  public void setCurrentEmployee(Employee currentEmployee) {
    this.currentEmployee = currentEmployee;
  }

  public String toEmployeeView() {
    onLoad();
    return "/employee.xhtml?faces-redirect=true";
  }

  public void addEmployee() {
    try {
      if (currentEmployee.getEmployeeNumber() != null
              && employeeFacade.find(currentEmployee.getEmployeeNumber()) != null) {
        employeeFacade.edit(currentEmployee);
      } else {
        employeeFacade.create(currentEmployee);
      }
      JsfUtil.addSuccessMessage("Successfully saved employee");
      prepareCreate();
      onLoad();
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Failed to save employee");
    }
  }

  public SelectItem[] getGenderOption() {
    return JsfUtil.getSelectItems(Arrays.asList(Gender.values()), false);
  }

  public DataModel<Employee> getEmployees() {
    if (employees == null) {
      onLoad();
    }
    return employees;
  }

  private Employee find(String empNumber) {
    return employeeFacade.find(empNumber);
  }

  /**
   * TODO(marembo) Very pathetic implementation!!!!!!!!!!!!!!!!!!!!!!!!!!!!
   */
  @FacesConverter(value = "employeeConverter", forClass = Employee.class)
  public static class EmployeeConverter implements Converter {

    private static Map<String, Employee> map = new HashMap<String, Employee>();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      return map.get(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
      if (value != null && value instanceof Employee) {
        map.put(((Employee) value).getEmployeeNumber(), (Employee) value);
        return ((Employee) value).getEmployeeNumber();
      }
      return null;
    }
  }
}
