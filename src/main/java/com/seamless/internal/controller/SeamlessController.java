/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.controller;

import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author marembo
 */
@Named(value = "seamlessController")
@ApplicationScoped
public class SeamlessController {

  /**
   * Creates a new instance of SeamlessController
   */
  public SeamlessController() {
  }

  public void preRenderView() {
    FacesContext.getCurrentInstance().getExternalContext().getSession(true);
  }
}
