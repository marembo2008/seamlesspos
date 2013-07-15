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
public class TableBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1373047317532994298L;

	private final static String[] colors;

	private final static String[] manufacturers;
	private SelectItem[] manufacturerOptions;

	private List<Car> filteredCars;

	static {
		colors = new String[10];
		colors[0] = "Ann Nkirote";
		colors[1] = "Joseph Kinanu";
		colors[2] = "Andrew Mwongela";
		colors[3] = "Rose Kanini";
		colors[4] = "James Ndirangu";
		colors[5] = "Lawrence Boit";
		colors[6] = "Brian Wanyonyi";
		colors[7] = "Angela Njoki";
		colors[8] = "Staicy Wambui";
		colors[9] = "Milka Lesonet";
		
		

		manufacturers = new String[10];
		manufacturers[0] = "Makongeni";
		manufacturers[1] = "Juja";
		manufacturers[2] = "Thika Town";
		manufacturers[3] = "Kenol";
		manufacturers[4] = "Ruiru";
		manufacturers[5] = "Makongeni";
		manufacturers[6] = "Juja";
		manufacturers[7] = "Thika Town";
		manufacturers[8] = "Kenol";
		manufacturers[9] = "Ruiru";
		
		


	}

	private List<Car> carsSmall;

	private List<Car> cars;

	private Car selectedCar;

	private Car[] selectedCars;
	 private CarDataModel mediumCarsModel;

	public TableBean() {
		carsSmall = new ArrayList<Car>();

		populateRandomCars(carsSmall, 9);
	}
	
	public Car[] getSelectedCars() {  
        return selectedCars;  
    }  
    public void setSelectedCars(Car[] selectedCars) {  
        this.selectedCars = selectedCars;  
    }  
  
    public Car getSelectedCar() {  
        return selectedCar;  
    }  
  
    public void setSelectedCar(Car selectedCar) {  
        this.selectedCar = selectedCar;  
    }  
	

	private void populateRandomCars(List<Car> list, int size) {
		for (int i = 0; i < size; i++)
			list.add(new Car(getRandomModel(), getRandomYear(),
					getRandomManufacturer(), getRandomColor()));
	}

	public List<Car> getFilteredCars() {
		return filteredCars;
	}

	public void setFilteredCars(List<Car> filteredCars) {
		this.filteredCars = filteredCars;
	}

	public List<Car> getCarsSmall() {
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
