/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.controller;

import com.anosym.jflemax.validation.annotation.LoginStatus;
import com.anosym.jflemax.validation.annotation.OnRequest;
import com.anosym.jflemax.validation.annotation.OnRequests;
import com.anosym.jflemax.validation.annotation.Principal;
import com.seamless.internal.Employee;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author marembo
 */
@Named(value = "seamlessController")
@ApplicationScoped
@OnRequests(onRequests = {
  @OnRequest(toPages = "*", excludedPages = {"/login"}, onRequestMethod = "isCurrentUserLoggedIn",
          redirect = true, redirectPage = "/login.xhtml", logInStatus = LoginStatus.EITHER)
})
public class SeamlessController {

  private static final Map<String, Employee> loggedInEmployees = new HashMap<String, Employee>();

  /**
   * Creates a new instance of SeamlessController
   */
  public SeamlessController() {
  }

  public void preRenderView() {
    FacesContext.getCurrentInstance().getExternalContext().getSession(true);
  }

  public String logout() {
    loggedInEmployees.remove(sessionId());
    ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
    preRenderView();
    return "#";
  }

  public void onLogin(Employee e) {
    loggedInEmployees.put(sessionId(), e);
  }

  private String sessionId() {
    return ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getId();
  }

  @Principal
  public Employee getCurrentEmployee() {
    return loggedInEmployees.get(sessionId());
  }

  public boolean isCurrentUserLoggedIn() {
    return getCurrentEmployee() != null;
  }
}
