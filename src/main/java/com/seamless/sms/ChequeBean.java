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
public class ChequeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1373047317532994298L;

	private final static String[] colors;

	private final static String[] manufacturers;
	private SelectItem[] manufacturerOptions;

	private List<Cheque> filteredCars;

	static {
		colors = new String[10];
		colors[0] = "334454332";
		colors[1] = "878654343";
		colors[2] = "232445624";
		colors[3] = "234345555";
		colors[4] = "546456356";
		colors[5] = "456646564";
		colors[6] = "456465652";
		colors[7] = "555778881";
		colors[8] = "565655544";
		colors[9] = "856878774";
		
		

		manufacturers = new String[10];
		manufacturers[0] = "Eco Bank";
		manufacturers[1] = "Standard Chartered Bank";
		manufacturers[2] = "Citi Bank";
		manufacturers[3] = "Commercial Bank of Africa";
		manufacturers[4] = "Barclays Bank";
		manufacturers[5] = "Stanbik Bank";
		manufacturers[6] = "Co-operative Bank";
		manufacturers[7] = "Chase Bank";
		manufacturers[8] = "I&M Bank";
		manufacturers[9] = "NIC Bank";
		
		


	}

	private List<Cheque> carsSmall;

	private List<Cheque> cars;

	private Cheque selectedCar;

	private Cheque[] selectedCars;
	 private CarDataModel mediumCarsModel;

	public ChequeBean() {
		carsSmall = new ArrayList<Cheque>();

		populateRandomCars(carsSmall, 9);
	}
	
	public Cheque[] getSelectedCars() {  
        return selectedCars;  
    }  
    public void setSelectedCars(Cheque[] selectedCars) {  
        this.selectedCars = selectedCars;  
    }  
  
    public Cheque getSelectedCar() {  
        return selectedCar;  
    }  
  
    public void setSelectedCar(Cheque selectedCar) {  
        this.selectedCar = selectedCar;  
    }  
	

	private void populateRandomCars(List<Cheque> list, int size) {
		for (int i = 0; i < size; i++)
			list.add(new Cheque(getRandomModel(), getRandomYear(),
					getRandomManufacturer(), getRandomColor()));
	}

	public List<Cheque> getFilteredCars() {
		return filteredCars;
	}

	public void setFilteredCars(List<Cheque> filteredCars) {
		this.filteredCars = filteredCars;
	}

	public List<Cheque> getCarsSmall() {
		return carsSmall;
	}

	private int getRandomYear() {
		return (int) (Math.random() * 50 + 876960);
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
	
	public PrimeDataModel getMediumCarsModel() {  
        return mediumCarsModel;  
    }  
}
