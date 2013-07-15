/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.controller;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.seamless.internal.Address;
import com.seamless.internal.Client;
import com.seamless.internal.controller.util.JsfUtil;
import com.seamless.internal.facade.ClientFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author marembo
 */
@Named
@SessionScoped
public class ClientController implements Serializable {

  private class ClientDataModel extends ListDataModel<Client> implements SelectableDataModel<Client> {

    public ClientDataModel(List<Client> list) {
      super(list);
    }

    public ClientDataModel() {
    }

    @Override
    public Object getRowKey(Client t) {
      return t.getId();
    }

    @Override
    public Client getRowData(String string) {
      return clientFacade.find(Long.valueOf(string));
    }
  }
  @EJB
  private ClientFacade clientFacade;
  private Client client;
  private DataModel<Client> clients;

  /**
   * Creates a new instance of ClientController
   */
  public ClientController() {
  }

  public Client getClient() {
    if (client == null) {
      prepareCreate();
    }
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void clientSelected(SelectEvent event) {
    if (event.getObject() != null) {
      this.client = (Client) event.getObject();
    }
  }

  public void prepareCreate() {
    this.client = new Client();
    this.client.setAddress(new Address());
  }

  public void create() {
    try {
      System.err.println("Client id: " + client.getId());
      if (clientFacade.find(client.getId()) != null) {
        clientFacade.edit(client);
        JsfUtil.addSuccessMessage("Successfully updated client: " + client.getName());
      } else {
        clientFacade.create(client);
        JsfUtil.addSuccessMessage("Successfully added a client");
      }
      client = null;
      loadClients();
    } catch (Exception e) {
      JsfUtil.addErrorMessage("Error adding new client, please try again later");
      Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  public void update() {
    try {
      clientFacade.edit(client);
      JsfUtil.addSuccessMessage("Successfully updated client: " + client.getName());
      client = null;
      loadClients();
    } catch (Exception e) {
      JsfUtil.addErrorMessage("Error adding new client, please try again later");
      Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  public List<Client> searchClients(String query) {
    return clientFacade.searchClients(query);
  }

  public int getActiveIndex() {
    return isNewClient() ? -1 : 0;
  }

  public void setActiveIndex(int ix) {
  }

  public boolean isNewClient() {
    return clientFacade.find(getClient().getId()) == null;
  }

  private void loadClients() {
    clients = new ClientController.ClientDataModel(clientFacade.findAll());
  }

  public DataModel<Client> getClients() {
    if (clients == null) {
      loadClients();
    }
    return clients;
  }

  public void setClients(DataModel<Client> clients) {
    this.clients = clients;
  }

  Client find(Long id) {
    return clientFacade.find(id);
  }

  @FacesConverter(value = "clientConverter", forClass = Client.class)
  public static class ClientConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      return (value != null) ? JFlemaxController.findManagedBean(ClientController.class).find(Long.parseLong(value)) : null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
      return (value instanceof Client) ? (((Client) value).getId() + "") : null;
    }
  }
}
