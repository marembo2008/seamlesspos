/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.facade;

import com.seamless.internal.Vehicle;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marembo
 */
@Stateless
public class VehicleFacade extends AbstractFacade<Vehicle> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public VehicleFacade() {
    super(Vehicle.class);
  }

  public List<Vehicle> searchVehicles(String query) {
    return getEntityManager()
            .createNamedQuery("VEHICLE.SEARCH_VEHICLE")
            .setParameter("regNumber", "%" + query + "%")
            .getResultList();
  }
}
