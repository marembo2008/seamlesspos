/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.anosym.utilities.Utility;
import com.seamless.internal.Item;
import com.seamless.internal.Product;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.facade.ProductFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author marembo
 */
@Named(value = "productController")
@SessionScoped
public class ProductController implements Serializable {

  private class ProductDataModel extends ListDataModel<Product> implements SelectableDataModel<Product> {

    public ProductDataModel(List<Product> list) {
      super(list);
    }

    public ProductDataModel() {
    }

    @Override
    public Object getRowKey(Product t) {
      return t.getProductNumber();
    }

    @Override
    public Product getRowData(String string) {
      return productFacade.find(Long.valueOf(string));
    }
  }
  @EJB
  private ProductFacade productFacade;
  private Product product;
  private DataModel<Product> products;

  /**
   * Creates a new instance of ProductController
   */
  public ProductController() {
  }

  public ProductFacade getProductFacade() {
    return productFacade;
  }

  public Product getProduct() {
    if (product == null) {
      product = new Product();
    }
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public List<Product> searchProduct(String query) {
    return productFacade.searchProduct(query);
  }

  public void create() {
    try {
      productFacade.create(product);
      JsfUtil.addSuccessMessage("Successfully added a new product");
      product = new Product();
      loadProducts();
    } catch (Exception e) {
      Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, e);
      JsfUtil.addErrorMessage("Error adding a new product, please try again later");
    }
  }

  public void loadProducts() {
    System.err.println("Loading products...............");
    products = new ProductDataModel(productFacade.findAll());
  }

  public String prepareList() {
    loadProducts();
    return "new_product.xhtml?faces-redirect=true";
  }

  public DataModel<Product> getProducts() {
    if (products == null) {
      loadProducts();
    }
    return products;
  }

  void addAndUpdate(Item item) {
    this.product.addItem(item);
    this.productFacade.edit(product);
  }

  Product find(Long id) {
    return productFacade.find(id);
  }

  @FacesConverter(value = "productConverter", forClass = Product.class)
  public static class ProductConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      return (!Utility.isNullOrEmpty(value)) ? JFlemaxController.findManagedBean(ProductController.class).find(Long.parseLong(value)) : null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
      return (value instanceof Product) ? (((Product) value).getProductNumber() + "") : null;
    }
  }
}
