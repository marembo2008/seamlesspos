package com.seamless.sms;

import java.io.Serializable;
import javax.faces.model.ListDataModel;

/**
 * An implementation of SelectableDataModel using a list as data
 */
public class PrimeDataModel<T> extends ListDataModel<T> implements SelectableDataModel<T>, Serializable {
            
    /**
	 * 
	 */
	private static final long serialVersionUID = -2564855851444563755L;

	public PrimeDataModel() {}
    
    public PrimeDataModel(Object data) {
        setWrappedData(data);
    }
    
    public Object getRowKey(T object) {
        throw new UnsupportedOperationException("Must be implemented");
    }
    
    public T getRowData(String rowKey) {
        throw new UnsupportedOperationException("Must be implemented");
    }
}
