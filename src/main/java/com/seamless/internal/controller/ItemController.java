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
import com.seamless.internal.MixedItemOption;
import com.seamless.internal.Product;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.facade.BatchFacade;
import com.seamless.internal.facade.ItemFacade;
import com.seamless.internal.facade.MixedItemOptionFacade;
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
      return t.getItemCode();
    }

    @Override
    public Item getRowData(String itemCode) {
      return itemFacade.find(itemCode);
    }
  }

  private class MixedItemOptionDataModel extends ListDataModel<MixedItemOption> implements SelectableDataModel<MixedItemOption> {

    public MixedItemOptionDataModel() {
    }

    public MixedItemOptionDataModel(List<MixedItemOption> list) {
      super(list);
    }

    @Override
    public Object getRowKey(MixedItemOption t) {
      return t.getId();
    }

    @Override
    public MixedItemOption getRowData(String id) {
      if (id == null) {
        return null;
      }
      return mixedItemOptionFacade.find(Long.parseLong(id));
    }
  }
  @EJB
  private ProductFacade productFacade;
  @EJB
  private BatchFacade batchFacade;
  @EJB
  private MixedItemOptionFacade mixedItemOptionFacade;
  @EJB
  private ItemFacade itemFacade;
  @Inject
  private BatchController batchController;
  @Inject
  private ProductController productController;
  private Item item;
  private DataModel<Item> items;
  private List<MixedItemOption> itemsForMixedItemOptions;
  private Batch batch;
  private Item itemStocking;
  private List<MixedItemOption> itemsForMixedItem;
  private boolean changeItemCost;
  private Item mixedItem;

  /**
   * Creates a new instance of ItemController
   */
  public ItemController() {
  }

  public MixedItemOption[] getSelectedItems() {
    if (itemsForMixedItem == null) {
      return new MixedItemOption[0];
    }
    return itemsForMixedItem.toArray(new MixedItemOption[0]);
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

  public void setSelectedItems(MixedItemOption[] items) {
    if (items != null && items.length > 0) {
      if (itemsForMixedItem == null) {
        itemsForMixedItem = new ArrayList<MixedItemOption>();
      }
      itemsForMixedItem.clear();
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
    MixedItemOption i = (MixedItemOption) se.getObject();
    System.out.println("Mixed: " + i);
    if (i != null) {
      if (itemsForMixedItem == null) {
        itemsForMixedItem = new ArrayList<MixedItemOption>();
      }
      itemsForMixedItem.add(i);
    }
  }

  public void onItemUnselected(UnselectEvent se) {
    MixedItemOption i = (MixedItemOption) se.getObject();
    if (i != null) {
      itemsForMixedItem.remove(i);
    }
  }

  public BigDecimal getTotalMixedItemCost() {
    if (itemsForMixedItem == null) {
      return BigDecimal.ZERO;
    }
    BigDecimal cost = BigDecimal.ZERO;
    for (MixedItemOption i : itemsForMixedItem) {
      cost = cost.add(i.getCostAmount());
    }
    return cost;
  }

  public BigDecimal getTotalMixedItemCostForView() {
    if (!isMixedItemOption()) {
      return BigDecimal.ZERO;
    }
    BigDecimal cost = BigDecimal.ZERO;
    for (MixedItemOption i : ((MixedItem) item).getItems()) {
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

  public boolean isMixedItemOption() {
    return item instanceof MixedItem;
  }

  public void stockinMixedItem() {
    try {
      Batch newBatch = batchController.getBatch();
      newBatch.setItem(item);
      batchFacade.createBatchForMixedItem(newBatch);
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

  public List<Item> searchMixedItem(String query) {
    return itemFacade.searchMixedItems(query);
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

  public List<MixedItemOption> getItemsForMixedItemOptions() {
    if (itemsForMixedItemOptions == null) {
      itemsForMixedItemOptions = new ArrayList<MixedItemOption>();
      for (Item i : itemFacade.findAll()) {
        itemsForMixedItemOptions.add(new MixedItemOption(i));
      }
    }
    return itemsForMixedItemOptions;
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
        BigDecimal proportion = BigDecimal.ZERO;
        for (MixedItemOption i : itemsForMixedItem) {
          ((MixedItem) mixedItem).addItem(i);
          proportion = proportion.add(i.getProportion());
        }
        if (proportion.compareTo(BigDecimal.ONE) != 0) {
          JsfUtil.addErrorMessage("Error adding mixed items. Mixed item proportion not valid. Please make sure total proportion equals 1.0. Current total proportion=" + proportion);
          return;
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
    this.item.setItemCode(IdGenerator.generateStringId());
  }

  public void generateMixedItemCode() {
    this.mixedItem.setItemCode(IdGenerator.generateStringId());
  }

  Item find(String id) {
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
      Object item = value != null ? JFlemaxController.findManagedBean(ItemController.class).find(value) : null;
      System.out.println(item);
      return item;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
      return (value instanceof Item) ? ((Item) value).getItemCode() : null;
    }
  }
}
