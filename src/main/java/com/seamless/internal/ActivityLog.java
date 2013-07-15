/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author marembo
 */
@Entity
public class ActivityLog implements Serializable {

  private static final long serialVersionUID = 134852395823L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long logId;
  @Temporal(javax.persistence.TemporalType.DATE)
  private Calendar logTime;
  @Column(length = 1024)
  private String description;

  public Long getLogId() {
    return logId;
  }

  public void setLogId(Long logId) {
    this.logId = logId;
  }

  public Calendar getLogTime() {
    return logTime;
  }

  public void setLogTime(Calendar logTime) {
    this.logTime = logTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 97 * hash + (this.logId != null ? this.logId.hashCode() : 0);
    hash = 97 * hash + (this.logTime != null ? this.logTime.hashCode() : 0);
    hash = 97 * hash + (this.description != null ? this.description.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ActivityLog other = (ActivityLog) obj;
    if (this.logId != other.logId && (this.logId == null || !this.logId.equals(other.logId))) {
      return false;
    }
    if (this.logTime != other.logTime && (this.logTime == null || !this.logTime.equals(other.logTime))) {
      return false;
    }
    if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
      return false;
    }
    return true;
  }
}
