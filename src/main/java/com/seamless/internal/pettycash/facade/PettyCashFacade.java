/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.pettycash.facade;

import com.seamless.internal.facade.AbstractFacade;
import com.seamless.internal.pettycash.PettyCash;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marembo
 */
@Stateless
public class PettyCashFacade extends AbstractFacade<PettyCash> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public PettyCashFacade() {
    super(PettyCash.class);
  }
}
