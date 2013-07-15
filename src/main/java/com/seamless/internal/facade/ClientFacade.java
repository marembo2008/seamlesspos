/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.facade;

import com.seamless.internal.Client;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marembo
 */
@Stateless
public class ClientFacade extends AbstractFacade<Client> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public ClientFacade() {
    super(Client.class);
  }

  public List<Client> searchClients(String query) {
    return getEntityManager()
            .createNamedQuery("client.search_client")
            .setParameter("name", "%" + query + "%")
            .getResultList();
  }
}
