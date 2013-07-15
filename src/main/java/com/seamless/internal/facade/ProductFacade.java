/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.facade;

import com.seamless.internal.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marembo
 */
@Stateless
public class ProductFacade extends AbstractFacade<Product> {

  @PersistenceContext(unitName = "com.seamless_seamlesspos_war_1.0-SNAPSHOTPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public ProductFacade() {
    super(Product.class);
  }

  public List<Product> searchProduct(String query) {
    return getEntityManager()
            .createNamedQuery("PRODUCT.SEARCH_PRODUCT")
            .setParameter("name", "%" + query + "%")
            .getResultList();
  }
}
