/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 * An everyday activity logs for the employees
 *
 * @author marembo
 */
@Entity
public class EmployeeActivity implements Serializable {

  private static final long serialVersionUID = 3423842848901L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long activityId;
  @OneToOne
  private Employee employee;
  @Temporal(javax.persistence.TemporalType.TIMESTAMP)
  private Calendar timeIn;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<ActivityLog> activityLogs;

  public Long getActivityId() {
    return activityId;
  }

  public void setActivityId(Long activityId) {
    this.activityId = activityId;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public Calendar getTimeIn() {
    return timeIn;
  }

  public void setTimeIn(Calendar timeIn) {
    this.timeIn = timeIn;
  }

  public List<ActivityLog> getActivityLogs() {
    return activityLogs;
  }

  public void setActivityLogs(List<ActivityLog> activityLogs) {
    this.activityLogs = activityLogs;
  }

  public void logActivity(ActivityLog activityLog) {
    if (this.activityLogs == null) {
      this.activityLogs = new ArrayList<ActivityLog>();
    }
    this.activityLogs.add(activityLog);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 67 * hash + (this.activityId != null ? this.activityId.hashCode() : 0);
    hash = 67 * hash + (this.employee != null ? this.employee.hashCode() : 0);
    hash = 67 * hash + (this.timeIn != null ? this.timeIn.hashCode() : 0);
    hash = 67 * hash + (this.activityLogs != null ? this.activityLogs.hashCode() : 0);
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
    final EmployeeActivity other = (EmployeeActivity) obj;
    if (this.activityId != other.activityId && (this.activityId == null || !this.activityId.equals(other.activityId))) {
      return false;
    }
    if (this.employee != other.employee && (this.employee == null || !this.employee.equals(other.employee))) {
      return false;
    }
    if (this.timeIn != other.timeIn && (this.timeIn == null || !this.timeIn.equals(other.timeIn))) {
      return false;
    }
    if (this.activityLogs != other.activityLogs && (this.activityLogs == null || !this.activityLogs.equals(other.activityLogs))) {
      return false;
    }
    return true;
  }
}
