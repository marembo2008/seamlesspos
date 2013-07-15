/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.settings;

import com.anosym.utilities.IdGenerator;
import com.anosym.vjax.VMarshaller;
import com.anosym.vjax.VXMLBindingException;
import com.anosym.vjax.xml.VDocument;
import com.seamless.internal.controller.util.JsfUtil;
import java.io.File;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author marembo
 */
@Named
@SessionScoped
public class SettingUtil implements Serializable {

  private static final long serialVersionUID = IdGenerator.serialVersionUID(SettingUtil.class);
  private static final String SETTING_PATH = "seamless-setting.xml";
  private static Setting setting;

  public static Setting getSetting() {
    if (setting == null) {
      File path = new File(SETTING_PATH);
      if (path.exists()) {
        try {
          setting = new VMarshaller<Setting>().unmarshall(VDocument.parseDocument(path));
        } catch (VXMLBindingException ex) {
          Logger.getLogger(SettingUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
      } else {
        try {
          setting = new Setting();
          VDocument doc = new VDocument(path);
          doc.setRootElement(new VMarshaller<Setting>().marshall(setting));
          doc.writeDocument();
        } catch (VXMLBindingException ex) {
          Logger.getLogger(SettingUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    return setting;
  }

  public Setting getSettings() {
    return getSetting();
  }

  public void saveSettings() {
    try {
      File path = new File(SETTING_PATH);
      System.err.println(path.getAbsolutePath());
      VDocument doc = new VDocument(path);
      doc.setRootElement(new VMarshaller<Setting>().marshall(setting));
      doc.writeDocument();
      JsfUtil.addSuccessMessage("Settings successfully saved!");
    } catch (VXMLBindingException ex) {
      Logger.getLogger(SettingUtil.class.getName()).log(Level.SEVERE, null, ex);
      JsfUtil.addErrorMessage("Failed to save settings: " + ex.getLocalizedMessage());
    }
  }
}
