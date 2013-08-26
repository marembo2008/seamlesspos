/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.facade;

import com.seamless.internal.MixedItemOption;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marembo
 */
@Stateless
public class MixedItemOptionFacade extends AbstractFacade<MixedItemOption> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public MixedItemOptionFacade() {
    super(MixedItemOption.class);
  }
}
