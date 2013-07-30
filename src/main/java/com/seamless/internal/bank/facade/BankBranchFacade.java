/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.bank.facade;

import com.seamless.internal.bank.BankBranch;
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
public class BankBranchFacade extends AbstractFacade<BankBranch> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public BankBranchFacade() {
    super(BankBranch.class);
  }

  public List<BankBranch> searchBankBranches(String query) {
    return getEntityManager()
            .createNamedQuery("BANKBRANCH.SEARCH_BANK_BRANCH")
            .setParameter("name", "%" + query + "%")
            .getResultList();
  }
}
