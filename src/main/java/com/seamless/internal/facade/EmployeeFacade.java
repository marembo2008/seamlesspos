/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.facade;

import com.seamless.internal.Employee;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
}
