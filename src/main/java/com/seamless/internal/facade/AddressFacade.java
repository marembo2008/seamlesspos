/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.facade;

import com.seamless.internal.Address;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marembo
 */
@Stateless
public class AddressFacade extends AbstractFacade<Address> {
  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public AddressFacade() {
    super(Address.class);
  }

}
