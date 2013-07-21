/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.Batch;
import com.seamless.internal.Client;
import com.seamless.internal.Item;
import com.seamless.internal.controller.BatchController;
import com.seamless.internal.controller.ItemController;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.facade.BatchFacade;
import com.seamless.internal.management.SaleReceiptGenerator;
import com.seamless.internal.sales.CashPayment;
import com.seamless.internal.sales.ChequePayment;
import com.seamless.internal.sales.CreditPayment;
import com.seamless.internal.sales.ItemOrder;
import com.seamless.internal.sales.Sale;
import com.seamless.internal.sales.SaleItem;
import com.seamless.internal.sales.facade.SaleFacade;
import com.seamless.internal.sales.facade.SaleItemFacade;
import com.seamless.internal.sales.util.PaymentOption;
import com.seamless.internal.sales.util.SaleStatus;
import com.seamless.settings.Setting;
import com.seamless.settings.SettingUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author marembo
 */
@Named(value = "salesController")
@SessionScoped
public class SalesController implements Serializable {

  private class SaleItemDataModel extends ListDataModel<SaleItem> implements SelectableDataModel<SaleItem> {

    public SaleItemDataModel() {
      super(getSale().getSaleItems());
    }

    @Override
    public Object getRowKey(SaleItem t) {
      return t.getSaleItemId();
    }

    @Override
    public SaleItem getRowData(String key) {
      return getSale().getSaleItem(key);
    }
  }
  private SaleItem saleItem;
  @EJB
  private BatchFacade batchFacade;
  @EJB
  private SaleFacade saleFacade;
  @EJB
  private SaleItemFacade saleItemFacade;
  @EJB
  private SaleReceiptGenerator receiptGenerator;
  @Inject
  private BatchController batchController;
  @Inject
  private ItemController itemController;
  private Sale sale;
  private PaymentOption paymentOption;
  private DataModel<SaleItem> saleItems;

  /**
   * Creates a new instance of SalesController
   */
  public SalesController() {
  }

  public DataModel<SaleItem> getSaleItems() {
    if (saleItems == null) {
      saleItems = new SaleItemDataModel();
    }
    return saleItems;
  }

  public int getDefaultSortOrderForSummaryRow() {
    return 2424242;
  }

  public SelectItem[] getSalePriceOptions() {
    SelectItem[] options = new SelectItem[0];
    if (getSaleItem() != null && getSaleItem().getItem() != null) {
      options = new SelectItem[3];
      options[0] = new SelectItem(saleItem.getItem().getPrice1(), "KSh." + saleItem.getItem().getPrice1() + "");
      options[1] = new SelectItem(saleItem.getItem().getPrice2(), "KSh." + saleItem.getItem().getPrice2() + "");
      options[2] = new SelectItem(saleItem.getItem().getPrice3(), "KSh." + saleItem.getItem().getPrice3() + "");
    }
    return options;
  }

  public SaleItem getSaleItem() {
    if (saleItem == null) {
      saleItem = new SaleItem();
      saleItem.setSale(getSale());
    }
    return saleItem;
  }

  public void setSale(Sale sale) {
    this.sale = sale;
  }

  public int getUnsavedOrderedQuantity(Item i) {
    int orderQuantity = 0;
    for (SaleItem si : getSaleItems()) {
      if (saleItemFacade.find(si.getSaleItemId()) == null) {
        if (si.getItem() != null && si.getItem().equals(i)) {
          orderQuantity += si.getOrderedQuantity();
        }
      }
    }
    return orderQuantity;
  }

  public int getTotalItemQuantitiesAvailable() {
    return (getSaleItem().getItem() != null ? batchFacade.getTotalItemQuantitiesAvailable(getSaleItem().getItem()) : 0) - getUnsavedOrderedQuantity(getSaleItem().getItem());
  }

  public void validateQuantity(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    if (value == null || !Integer.class.isAssignableFrom(value.getClass())) {
      FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
              "Invalid value. Required integral values only",
              "Invalid value. Required integral values only");
      throw new ValidatorException(fm);
    } else {
      Integer quantity = (Integer) value;
      if (getTotalItemQuantitiesAvailable() < quantity) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Specified quantity exceeds available item quantities",
                "Specified quantity exceeds available item quantities");
        throw new ValidatorException(fm);
      }
    }
  }

  public void setSelectedCustomer(SelectEvent se) {
    if (se.getObject() != null) {
      this.sale.setCustomer((Client) se.getObject());
    }
  }

  public Sale getSale() {
    if (sale == null) {
      prepareSale();
    }
    return sale;
  }

  private void prepareSale() {
    sale = new Sale();
    receiptGenerator.setSaleReceipt(sale);
    saleItem = null;
    saleItems = null;
  }

  public void setSaleItem(SaleItem saleItem) {
    this.saleItem = saleItem;
  }

  public void onItemLoaded(SelectEvent event) {
    Item i = (Item) event.getObject();
    if (i != null) {
      this.saleItem.setSalePrice(i.getPrice1());
      calculateTax();
    }
  }

  public void calculateTax() {
    if (getSaleItem().getItem().isTaxable()) {
      BigDecimal tax = getSaleItem().getSalePrice();
      Setting settings = SettingUtil.getSetting();
      tax = tax.multiply(settings.getVat());
      getSaleItem().setTax(tax);
    }
  }

  public PaymentOption getPaymentOption() {
    return paymentOption;
  }

  public void setPaymentOption(PaymentOption paymentOption) {
    this.paymentOption = paymentOption;
  }

  public boolean isCreditPayment() {
    return paymentOption == PaymentOption.CREDIT;
  }

  public boolean isChequePayment() {
    return paymentOption == PaymentOption.CHEQUE;
  }

  public void calculatePaymentOption() {
    switch (paymentOption) {
      case CASH:
        getSale().setPayment(new CashPayment(getSale().getTotalSale(), getSale().getCustomer()));
        break;
      case CREDIT:
        getSale().setPayment(new CreditPayment(getSale().getTotalSale(), getSale().getCustomer()));
        break;
      case CHEQUE:
        getSale().setPayment(new ChequePayment(getSale().getTotalSale(), getSale().getCustomer()));
        break;
    }
  }

  public PaymentOption[] getPaymentOptions() {
    return PaymentOption.values();
  }

  public void editItem() {
    try {
      validateQuantity(null, null, saleItem.getOrderedQuantity());
      //TODO(marembo). load the relevant batch to add the sale item order.
      SaleItem si = saleItem;
      if (si != null) {
        if (!si.isOrderSet()) {
          //set the batch orders appropriately
          int unassignedOrder = si.getUnassignedOrderedQuantity();
          for (ItemOrder io : si.getItemOrders()) {
            Batch ioBatch = io.getItem();
            if (ioBatch.getCurrentQuantity() > 0) {
              int ordered = Math.min(unassignedOrder, ioBatch.getCurrentQuantity());
              unassignedOrder -= ordered;
              ioBatch.decreaseCurrentQuantity(ordered);
              io.increaseOrderedQuantity(ordered);
            }
            if (si.isOrderSet()) {
              break;
            }
          }
        }
      }
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Sorry, unable to edit item: " + e.getMessage());
    }
  }

  public void addItem() {
    try {
      if (sale.getSaleItems().contains(saleItem)) {
        editItem();
        return;
      }
      validateQuantity(null, null, saleItem.getOrderedQuantity());
      //TODO(marembo). load the relevant batch to add the sale item order.
      SaleItem si = saleItem;
      if (!si.isOrderSet()) {
        Item i = si.getItem();
        //get the batches.
        /*
         * TODO(marembo) this will fail if the item has been referenced in more than one sale item!
         *
         */
        List<Batch> availableBatches = batchFacade.findAvailableBatches(i);
        ListIterator<Batch> it_b = availableBatches.listIterator();
        //for us to reach here, there must have been available items
        int orderedQuantity = si.getOrderedQuantity();
        while (orderedQuantity > 0 && it_b.hasNext()) {
          Batch b = it_b.next();
          int b_quantity = b.getCurrentQuantity();
          //get the order quantity for item order
          int io_quantity = Math.min(orderedQuantity, b_quantity);
          //get the current order quantity for this batch if any
          int b_currentQuantity = Math.max(b_quantity - io_quantity, 0);
          b.setCurrentQuantity(b_currentQuantity);
          ItemOrder io = new ItemOrder(b, io_quantity);
          orderedQuantity -= io_quantity;
          si.addItem(io);
        }
      }
      getSale().addSaleItem(saleItem);
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Sorry, unable to add item: " + e.getMessage());
    } finally {
      saleItem = null;
    }
  }

  public void doSale() {
    try {
      sale.setStatus(SaleStatus.SOLD);
      doSave();
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Failed: " + e.getLocalizedMessage());
    } finally {
      prepareSale();
    }
  }

  private void doSave() {
    try {
      if (sale.getStatus() != SaleStatus.CANCELLED) {
        if (sale.getCustomer() == null) {
          throw new IllegalArgumentException("Please specify the customer to sell to");
        }
        if (sale.getSaleItems().isEmpty()) {
          throw new IllegalArgumentException("Please add items for this sale");
        }
      }
      if (saleFacade.findSale(sale.getReceiptId()) != null) {
        saleFacade.edit(sale);
        JsfUtil.addSuccessMessage("Sale Successfully Updated");
      } else {
        saleFacade.create(sale);
        JsfUtil.addSuccessMessage("Sale Successfull");
      }
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Failed: " + e.getLocalizedMessage());
    }
  }

  public void removeItem(SaleItem item) {
    sale.removeSaleItem(item);
    //return all the batch items
    for (ListIterator<ItemOrder> it_io = item.getItemOrders().listIterator(); it_io.hasNext();) {
      ItemOrder io = it_io.next();
      io.getItem().setCurrentQuantity(io.getItem().getCurrentQuantity() + io.getOrderedQuantity());
      //if the sale exist, then we must have recalled a sale
      batchFacade.edit(io.getItem());
      saleFacade.edit(sale);
    }
  }

  public void prepareToTender() {
    //by default we set cash payment.
    if (sale.getPayment() == null) {
      paymentOption = PaymentOption.CASH;
      sale.setPayment(new CashPayment(sale.getTotalSale(), getSale().getCustomer()));
    }
  }

  public void holdSale() {
    try {
      sale.setPayment(new CashPayment(BigDecimal.ZERO));
      sale.setStatus(SaleStatus.HOLD);
      sale.setPayment(null); //we do this so that we do not save payment information for held sales.
      doSave();
    } finally {
      prepareSale();
    }
  }

  public int getSorter() {
    return 0;
  }

  public void saleItemSelected(SelectEvent ree) {
    saleItem = (SaleItem) ree.getObject();
  }

  public void cancelSale() {
    try {
      sale.setStatus(SaleStatus.CANCELLED);
      for (SaleItem item : sale.getSaleItems()) {
        for (ListIterator<ItemOrder> it_io = item.getItemOrders().listIterator(); it_io.hasNext();) {
          ItemOrder io = it_io.next();
          io.getItem().setCurrentQuantity(io.getItem().getCurrentQuantity() + io.getOrderedQuantity());
        }
      }
      doSave();
    } finally {
      prepareSale();
    }
  }

  public List<Sale> searchSales(String query) {
    return saleFacade.searchSales(query);
  }

  public List<Sale> searchHeldSales(String query) {
    return saleFacade.searchSales(query, SaleStatus.HOLD);
  }

  public void recallHeldSales(SelectEvent se) {
    this.sale = (Sale) se.getObject();
    this.saleItems = new SaleItemDataModel();
  }

  public List<Sale> getSales() {
    return saleFacade.findAll();
  }

  Sale find(String id) {
    System.err.println("receipt id: " + id);
    return saleFacade.findSale(id);
  }

  @FacesConverter(value = "saleConverter", forClass = Sale.class)
  public static class SaleConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      return value != null ? JFlemaxController.findManagedBean(SalesController.class).find(value) : null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
      return (value != null && value instanceof Sale) ? ((Sale) value).getReceiptId().toString() : null;
    }
  }
}
