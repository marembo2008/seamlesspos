package com.seamless.sms;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped

public class OrderListBean {  
	
      
    private List<String> cities; 
    private List<String> closed;
    private List<String> shared;
    private List<String> notifications;
  
    public OrderListBean() {  
          
      
        //Cities  
        cities = new ArrayList<String>();
        closed = new ArrayList<String>();
        shared = new ArrayList<String>();
        notifications = new ArrayList<String>();
          
        cities.add("Business Law");  
        cities.add("Cost Accounting");  
        cities.add("Financial Management");
        cities.add("Business Law");  
        cities.add("Christian beliefs");  
        cities.add("Financial Management");
        cities.add("Business Law");  
        cities.add("Cost Accounting");  
        cities.add("Financial Management");
         cities.add("Christian beliefs");  
        
        
        closed.add("Andrew Don vs State");  
        closed.add("Ndathi Joy vs Ngeru Isaac");  
        closed.add("Kamoni wanjiku vs Daniel Maigua");
        closed.add("Wangeci Kanyoro vs Phoebe Nyawira");  
        closed.add("Dave Korir vs Loise Taipei");  
        closed.add("Brian Keith vs Nyali Beach Hotel");
        
        shared.add("Kimani Land sale Agreement");  
        shared.add("Isaac scene evidence");  
        shared.add("Rose Mwende Divorce Evidence2");
        shared.add("Jane Equity Bank Statement");  
        
        notifications.add("You have a new message");  
        notifications.add("Don shared a file with you");  
        notifications.add("Meeting today");
        notifications.add("Wedding on tomorrow");  
        
    }  
  
    public List<String> getCities() {  
        return cities;  
    }  
    public void setCities(List<String> cities) {  
        this.cities = cities;  
    }  
    
    public List<String> getClosed() {  
        return closed;  
    }  
    public void setClosed(List<String> closed) {  
        this.closed = closed;  
    }  
  
    public List<String> getShared() {  
        return shared;  
    }  
    public void setShared(List<String> shared) {  
        this.shared = shared;  
    }  
    
    public List<String> getNotifications() {  
        return notifications;  
    }  
    public void seNotifications(List<String> notifications) {  
        this.notifications = notifications;  
    }  
   
}  
