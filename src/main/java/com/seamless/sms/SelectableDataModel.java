package com.seamless.sms;

public interface SelectableDataModel<T> {
    
    public Object getRowKey(T object);
    
    public T getRowData(String rowKey);
}