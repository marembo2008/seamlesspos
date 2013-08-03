/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.facade;

import com.seamless.internal.AccountPayable;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marembo
 */
@Stateless
public class AccountPayableFacade extends AbstractFacade<AccountPayable> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public AccountPayableFacade() {
    super(AccountPayable.class);
  }

  public List<AccountPayable> findAccountPayableWithinRange(Calendar start, Calendar end) {
    return getEntityManager()
            .createNamedQuery("ACCOUNTPAYABLE.FIND_ACCOUNT_PAYABLE_WITHIN_RANGE")
            .setParameter("start", start)
            .setParameter("end", end)
            .getResultList();
  }
}
