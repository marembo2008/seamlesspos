/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.Vehicle;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.facade.VehicleFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author marembo
 */
@Named(value = "vehicleController")
@SessionScoped
public class VehicleController implements Serializable {

  @EJB
  private VehicleFacade vehicleFacade;
  private Vehicle vehicle;
  private List<Vehicle> vehicles;

  public Vehicle getVehicle() {
    if (vehicle == null) {
      vehicle = new Vehicle();
    }
    return vehicle;
  }

  public List<Vehicle> getVehicles() {
    if (vehicles == null) {
      vehicles = vehicleFacade.findAll();
    }
    return vehicles;
  }

  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  public void setVehicles(List<Vehicle> vehicles) {
    this.vehicles = vehicles;
  }

  public void saveVehicle() {
    try {
      if (vehicle.getId() != null && vehicleFacade.find(vehicle.getId()) != null) {
        vehicleFacade.edit(vehicle);
      } else {
        vehicleFacade.create(vehicle);
      }
      JsfUtil.addSuccessMessage("Successfully saved vehicle: " + vehicle);
      prepare();
    } catch (Exception e) {
      JFlemaxController.logError(e);
      JsfUtil.addErrorMessage("Failed to add vehicle: " + e.getLocalizedMessage());
    }
  }

  public List<Vehicle> searchVehilces(String query) {
    return vehicleFacade.searchVehicles(query);
  }

  void prepare() {
    vehicle = null;
    vehicles = null;
  }

  private Vehicle find(String id) {
    return vehicleFacade.find(Long.parseLong(id));
  }

  /**
   * TODO(marembo) Very pathetic implementation!!!!!!!!!!!!!!!!!!!!!!!!!!!!
   */
  @FacesConverter(value = "vehicleConverter", forClass = Vehicle.class)
  public static class VehicleConverter implements Converter {

    private static Map<String, Vehicle> map = new HashMap<String, Vehicle>();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      return map.get(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
      if (value != null && value instanceof Vehicle) {
        Vehicle v = ((Vehicle) value);
        map.put(v.getId() + "", v);
        return v.getId() + "";
      }
      return null;
    }
  }
}
