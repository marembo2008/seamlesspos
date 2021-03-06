/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.bank.facade;

import com.seamless.internal.bank.BankRegister;
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
public class BankRegisterFacade extends AbstractFacade<BankRegister> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public BankRegisterFacade() {
    super(BankRegister.class);
  }

  public List<BankRegister> searchBankRegisters(String query) {
    return getEntityManager()
            .createNamedQuery("BANKREGISTER.SEARCH_BANK_REGISTERS_BY_SLIP_NUMBER")
            .setParameter("slipNumber", "%" + query + "%")
            .getResultList();
  }
}
