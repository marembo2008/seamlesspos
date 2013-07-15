package com.seamless.sms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class SalesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1373047317532994298L;

	private final static String[] colors;

	private final static String[] manufacturers;
	private SelectItem[] manufacturerOptions;

	private List<Sales> filteredSaless;

	static {
		colors = new String[10];
		colors[0] = "Makongeni";
		colors[1] = "Juja";
		colors[2] = "Thika Town";
		colors[3] = "Kenol";
		colors[4] = "Ruiru";
		colors[5] = "Makongeni";
		colors[6] = "Juja";
		colors[7] = "Thika Town";
		colors[8] = "Kenol";
		colors[9] = "Ruiru";
		
		

		manufacturers = new String[10];
		manufacturers[0] = "Makongeni";
		manufacturers[1] = "Behind JKUAT";
		manufacturers[2] = "Next to Stadium";
		manufacturers[3] = "Next To DC's Office";
		manufacturers[4] = "Kihunguro";
                manufacturers[5] = "Makongeni";
		manufacturers[6] = "Behind JKUAT";
		manufacturers[7] = "Next to Stadium";
		manufacturers[8] = "Next To DC's Office";
		manufacturers[9] = "Kihunguro";
		
		


	}

	private List<Sales> carsSmall;

	private List<Sales> cars;

	private Sales selectedSales;

	private Sales[] selectedSaless;
	 private CarDataModel mediumSalessModel;

	public SalesBean() {
		carsSmall = new ArrayList<Sales>();

		populateRandomSaless(carsSmall, 9);
	}
	
	public Sales[] getSelectedSaless() {  
        return selectedSaless;  
    }  
    public void setSelectedSaless(Sales[] selectedSaless) {  
        this.selectedSaless = selectedSaless;  
    }  
  
    public Sales getSelectedSales() {  
        return selectedSales;  
    }  
  
    public void setSelectedSales(Sales selectedSales) {  
        this.selectedSales = selectedSales;  
    }  
	

	private void populateRandomSaless(List<Sales> list, int size) {
		for (int i = 0; i < size; i++)
			list.add(new Sales(getRandomModel(), getRandomYear(),
					getRandomManufacturer(), getRandomColor()));
	}

	public List<Sales> getFilteredSaless() {
		return filteredSaless;
	}

	public void setFilteredSaless(List<Sales> filteredSaless) {
		this.filteredSaless = filteredSaless;
	}

	public List<Sales> getSalessSmall() {
		return carsSmall;
	}

	private int getRandomYear() {
		return (int) (Math.random() * 500000);
	}
        
        private int getRandomNet() {
		return (int) (Math.random() * 500000);
	}

	private String getRandomColor() {
		return colors[(int) (Math.random() * 10)];
	}

	private String getRandomManufacturer() {
		return manufacturers[(int) (Math.random() * 10)];
	}

	private String getRandomModel() {
		return UUID.randomUUID().toString().substring(0, 8);
	}

	private SelectItem[] createFilterOptions(String[] data) {
		SelectItem[] options = new SelectItem[data.length + 1];

		options[0] = new SelectItem("", "Select");
		for (int i = 0; i < data.length; i++) {
			options[i + 1] = new SelectItem(data[i], data[i]);
		}

		return options;
	}

	public SelectItem[] getManufacturerOptions() {
		return manufacturerOptions;
	}
	
	public PrimeDataModel getMediumSalessModel() {  
        return mediumSalessModel;  
    }  
}
