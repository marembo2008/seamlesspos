package com.seamless.sms;

import java.util.List;
import javax.faces.model.ListDataModel; 


public class CarDataModel extends PrimeDataModel<Car> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6297432028549288971L;

	public CarDataModel() {
    }

    public CarDataModel(Object data) {
        super(data);
    }
    
    @Override
    public Car getRowData(String rowKey) {
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data
        
        List<?> cars = (List<?>) getWrappedData();
        
        for(Object car : cars) {
            if(((Car)car).getModel().equals(rowKey))
                return (Car)car;
        }
        
        return null;
    }

    @Override
    public Object getRowKey(Car car) {
        return car.getModel();
    }
}
