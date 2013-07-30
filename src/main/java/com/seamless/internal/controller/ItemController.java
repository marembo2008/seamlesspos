/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.anosym.utilities.IdGenerator;
import com.seamless.internal.Batch;
import com.seamless.internal.Item;
import com.seamless.internal.MixedItem;
import com.seamless.internal.Product;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.facade.BatchFacade;
import com.seamless.internal.facade.ItemFacade;
import com.seamless.internal.facade.ProductFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author marembo
 */
@Named(value = "itemController")
@SessionScoped
public class ItemController implements Serializable {

  private class ItemDataModel extends ListDataModel<Item> implements SelectableDataModel<Item> {

    public ItemDataModel() {
    }

    public ItemDataModel(List<Item> list) {
      super(list);
    }

    @Override
    public Object getRowKey(Item t) {
      return t.getItemId();
    }

    @Override
    public Item getRowData(String string) {
      return itemFacade.find(Long.parseLong(string));
    }
  }
  @EJB
  private ProductFacade productFacade;
  @EJB
  private BatchFacade batchFacade;
  @EJB
  private ItemFacade itemFacade;
  @Inject
  private BatchController batchController;
  @Inject
  private ProductController productController;
  private Item item;
  private DataModel<Item> items;
  private Batch batch;
  private Item itemStocking;
  private List<Item> itemsForMixedItem;
  private boolean changeItemCost;
  private Item mixedItem;

  /**
   * Creates a new instance of ItemController
   */
  public ItemController() {
  }

  public Item[] getSelectedItems() {
    if (itemsForMixedItem == null) {
      return new Item[0];
    }
    return itemsForMixedItem.toArray(new Item[0]);
  }

  public void setMixedItem(Item mixedItem) {
    this.mixedItem = mixedItem;
  }

  public Item getMixedItem() {
    if (mixedItem == null) {
      mixedItem = new MixedItem();
    }
    return mixedItem;
  }

  public void setSelectedItems(Item[] items) {
    if (items != null && items.length > 0) {
      if (itemsForMixedItem == null) {
        itemsForMixedItem = new ArrayList<Item>();
      }
      itemsForMixedItem.addAll(Arrays.asList(items));
    }
  }

  public void setChangeItemCost(boolean changeItemCost) {
    this.changeItemCost = changeItemCost;
  }

  public boolean isChangeItemCost() {
    return changeItemCost;
  }

  public void onItemSelected(SelectEvent se) {
    Item i = (Item) se.getObject();
    System.out.println("Mixed: " + i);
    if (i != null) {
      if (itemsForMixedItem == null) {
        itemsForMixedItem = new ArrayList<Item>();
      }
      itemsForMixedItem.add(i);
    }
  }

  public void onItemUnselected(UnselectEvent se) {
    Item i = (Item) se.getObject();
    if (i != null) {
      itemsForMixedItem.remove(i);
    }
  }

  public BigDecimal getTotalMixedItemCost() {
    if (itemsForMixedItem == null) {
      return BigDecimal.ZERO;
    }
    BigDecimal cost = BigDecimal.ZERO;
    for (Item i : itemsForMixedItem) {
      cost = cost.add(i.getCostAmount());
    }
    return cost;
  }

  public void stockin() {
    try {
      Batch newBatch = batchController.getBatch();
      newBatch.setItem(item);
      batchFacade.create(newBatch);
      batchController.prepareCreate();
      item = new Item();
      items = null; //reload
      changeItemCost = false;
      JsfUtil.addSuccessMessage("Item has been successfully stocked: Stocked quantity=" + newBatch.getQuantity());
    } catch (Exception e) {
      Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, e);
      JsfUtil.addErrorMessage("Error adding item. Please try again later");
    }
  }

  public void prepareMixedItem(TabChangeEvent event) {
    this.item = new MixedItem();
  }

  public List<Item> searchItem(String query) {
    return itemFacade.searchItems(query);
  }

  public List<Batch> searchItemBatch(String query) {
    return batchFacade.searchBatch(query);
  }

  public void onItemLoaded(SelectEvent event) {
    item = (Item) event.getObject();
  }

  public void setItemStocking(Item itemStocking) {
    this.itemStocking = itemStocking;
  }

  public Item getItemStocking() {
    if (itemStocking == null) {
      itemStocking = new MixedItem();
    }
    return itemStocking;
  }

  public void setItemBatch(Batch itemBatch) {
    this.batch = itemBatch;
  }

  public Batch getItemBatch() {
    return batch;
  }

  public DataModel<Item> getItems() {
    if (items == null) {
      items = new ItemDataModel(itemFacade.findAll());
    }
    return items;
  }

  public Item getItem() {
    if (item == null) {
      item = new Item();
    }
    return item;
  }

  public void create() {
    try {
      Product product = productController.getProduct();
      product.addItem(item);
      productFacade.edit(product);
      item = new Item();
      items = null; //reload
      JsfUtil.addSuccessMessage("Item has been successfully added");
    } catch (Exception e) {
      Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, e);
      JsfUtil.addErrorMessage("Error adding item. Please try again later");
    }
  }

  public void createMixedItem() {
    try {
      if (itemsForMixedItem != null && !itemsForMixedItem.isEmpty()) {
        for (Item i : itemsForMixedItem) {
          ((MixedItem) mixedItem).addItem(i);
        }
      } else {
        JsfUtil.addErrorMessage("Please select items for mixed items");
        return;
      }
      Product product = productController.getProduct();
      product.addItem(mixedItem);
      productFacade.edit(product);
      mixedItem = new Item();
      items = null; //reload
      itemsForMixedItem = null;
      JsfUtil.addSuccessMessage("Item has been successfully added");
    } catch (Exception e) {
      Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, e);
      JsfUtil.addErrorMessage("Error adding item. Please try again later");
    }
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public void generateItemCode() {
    this.item.setItemCode(IdGenerator.generateId());
  }

  public void generateMixedItemCode() {
    this.mixedItem.setItemCode(IdGenerator.generateId());
  }

  Item find(Long id) {
    return itemFacade.find(id);
  }

  public boolean isMixedItem(Item i) {
    return i != null && (i instanceof MixedItem);
  }

  @FacesConverter(value = "itemConverter", forClass = Item.class)
  public static class ItemConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      System.err.println("Finding item: " + value);
      Object item = value != null ? JFlemaxController.findManagedBean(ItemController.class).find(Long.parseLong(value)) : null;
      System.out.println(item);
      return item;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
      return (value instanceof Item) ? ((Item) value).getItemId() + "" : null;
    }
  }
}
