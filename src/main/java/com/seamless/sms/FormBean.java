package com.seamless.sms;

import java.io.Serializable;

import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  

import javax.faces.bean.ManagedBean;

@ManagedBean

  
public class FormBean implements Serializable {  
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 397719659297116106L;

	private List<String> selectedGroups;  
	  
    private Map<String,String> groups;  
  
    public FormBean() {  
        groups = new HashMap<String, String>();  
        groups.put("Wheelbarrow", "Vijana");  
        groups.put("Plastic Buckets", "Wamama");  
        groups.put("Iron Sheets", "Wazee");  
        groups.put("Blue triangle", "Choir");  
    }  
  
    public List<String> getSelectedMovies() {  
        return selectedGroups;  
    }  
    public void setSelectedMovies(List<String> selectedGroups) {  
        this.selectedGroups = selectedGroups;  
    }  
  
    public Map<String, String> getMovies() {  
        return groups;  
    }  
}  
                          
