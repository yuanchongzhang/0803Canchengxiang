package com.mingmen.canting.model;


import com.mingmen.canting.http.model.Result;

public class ObjectResult<T> extends Result {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
