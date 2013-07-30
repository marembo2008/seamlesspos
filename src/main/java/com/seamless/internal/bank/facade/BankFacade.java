/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.bank.facade;

import com.seamless.internal.bank.Bank;
import com.seamless.internal.facade.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marembo
 */
@Stateless
public class BankFacade extends AbstractFacade<Bank> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public BankFacade() {
    super(Bank.class);
  }

  public List<Bank> searchBanks(String query) {
    return getEntityManager()
            .createNamedQuery("BANK.SEARCH_BANK_BY_NAME")
            .setParameter("bankName", "%" + query + "%")
            .getResultList();
  }
}
