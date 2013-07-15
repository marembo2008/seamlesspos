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
public class ItemBean implements Serializable {

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
		colors[0] = "Padloc";
		colors[1] = "Mabati";
		colors[2] = "Hammer";
		colors[3] = "Watering Can";
		colors[4] = "UPS";
		colors[5] = "Bicycle";
		colors[6] = "Switch";
		colors[7] = "Swewing Machines";
		colors[8] = "Printer";
		colors[9] = "Computer";
		
		

		manufacturers = new String[10];
		manufacturers[0] = "Singer Sewing Machine";
		manufacturers[1] = "Plastic Hammer";
		manufacturers[2] = "CLop Watering Can";
		manufacturers[3] = "Lid Power Switch";
		manufacturers[4] = "Sams Wheelbarrow";
		manufacturers[5] = "Solex Padlock";
		manufacturers[6] = "Tri-Circle Padlock";
		manufacturers[7] = "HP UPS";
		manufacturers[8] = "Dumu Zas Mabati";
		manufacturers[9] = "Black Mamba Bicycle";
		
		


	}

	private List<Cheque> carsSmall;

	private List<Cheque> cars;

	private Cheque selectedCar;

	private Cheque[] selectedCars;
	 private CarDataModel mediumCarsModel;

	public ItemBean() {
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
		return (int) (Math.random() * 43);
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
