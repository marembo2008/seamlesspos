/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.creditnote.facade;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.creditnote.CreditNote;
import com.seamless.internal.creditnote.SalesReturn;
import com.seamless.internal.sales.SaleItem;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author marembo
 */
@Named(value = "creditNoteController")
@SessionScoped
public class CreditNoteController implements Serializable {

  @EJB
  private CreditNoteFacade creditNoteFacade;
  private CreditNote creditNote;
  private SalesReturn[] selectedSalesReturns;
  private List<SalesReturn> saleReturns;

  public void setCreditNote(CreditNote creditNote) {
    this.creditNote = creditNote;
  }

  public CreditNote getCreditNote() {
    if (creditNote == null) {
      creditNote = new CreditNote();
    }
    return creditNote;
  }

  public List<SalesReturn> getSaleReturns() {
    return saleReturns;
  }

  public SalesReturn[] getSelectedCreditNoteSaleItems() {
    return selectedSalesReturns;
  }

  public void setSelectedCreditNoteSaleItems(SalesReturn[] selectedCreditNoteSaleItems) {
    this.selectedSalesReturns = selectedCreditNoteSaleItems;
  }

  public void onSaleReceiptSelected(SelectEvent se) {
    //get the sale items
    if (saleReturns == null) {
      saleReturns = new ArrayList<SalesReturn>();
    }
    for (SaleItem i : getCreditNote().getSale().getSaleItems()) {
      saleReturns.add(new SalesReturn(i));
    }
  }

  public List<CreditNote> getCreditNotes() {
    return creditNoteFacade.findAll();
  }

  public void createCreditNote() {
    //make sure that the selected sale returns is not empty
    if (selectedSalesReturns == null || selectedSalesReturns.length < 0) {
      JsfUtil.addErrorMessage("Please create the sales return for this credit note");
    }
    try {
      for (SalesReturn sr : selectedSalesReturns) {
        creditNote.addSaleReturn(sr);
      }
      creditNoteFacade.create(creditNote);
      JsfUtil.addSuccessMessage("Credit not has been successfully created");
      creditNote = null;
      saleReturns = null;
      selectedSalesReturns = null;
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Sorry! Error creating credit note: " + e.getLocalizedMessage());
    }
  }
}
