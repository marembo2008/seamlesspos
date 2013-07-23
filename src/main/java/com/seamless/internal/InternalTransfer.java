/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import com.anosym.utilities.IdGenerator;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author marembo
 */
@Entity
public class InternalTransfer implements Serializable {

  private static final long serialVersionUID = 264282291L;
  @Id
  private Long id = IdGenerator.generateId();
  @OneToOne
  private Store fromStore;
  @OneToOne
  private Store toStore;
  @OneToOne(cascade = CascadeType.MERGE)
  private Batch transferBatch;
  @OneToOne(cascade = CascadeType.ALL)
  private Batch newStoreBatch;
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar transferDate;

  public InternalTransfer() {
    transferDate = Calendar.getInstance();
  }

  public void setTransferDate(Calendar transferDate) {
    this.transferDate = transferDate;
  }

  public Calendar getTransferDate() {
    return transferDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Store getFromStore() {
    return fromStore;
  }

  public void setFromStore(Store fromStore) {
    this.fromStore = fromStore;
  }

  public Store getToStore() {
    return toStore;
  }

  public void setToStore(Store toStore) {
    this.toStore = toStore;
  }

  public Batch getTransferBatch() {
    return transferBatch;
  }

  public void setTransferBatch(Batch transferBatch) {
    this.transferBatch = transferBatch;
  }

  public Batch getNewStoreBatch() {
    return newStoreBatch;
  }

  public void setNewStoreBatch(Batch newStoreBatch) {
    this.newStoreBatch = newStoreBatch;
  }
}
