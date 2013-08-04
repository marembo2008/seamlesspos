/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.facade;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.Employee;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marembo
 */
@Stateless
public class EmployeeFacade extends AbstractFacade<Employee> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public EmployeeFacade() {
    super(Employee.class);
  }

  public List<Employee> searchEmployees(String query) {
    return getEntityManager()
            .createNamedQuery("EMPLOYEE.SEARCH_EMPLOYEE")
            .setParameter("surname", "%" + query + "%")
            .setParameter("otherNames", "%" + query + "%")
            .setParameter("empNumber", "%" + query + "%")
            .getResultList();
  }

  public Employee findEmployee(String username, String password) {
    try {
      return getEntityManager()
              .createNamedQuery("EMPLOYEE.FIND_EMPLOYEE_BY_CREDENTIAL", Employee.class)
              .setParameter("username", username)
              .setParameter("password", password)
              .getSingleResult();
    } catch (NoResultException ex) {
    } catch (Exception e) {
      JFlemaxController.logError(e);
    }
    return null;
  }
}
