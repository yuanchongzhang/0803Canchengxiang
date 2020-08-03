package com.mingmen.canting.http.model;


import com.alibaba.fastjson.JSON;



public class Result {


    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;



    @Override
    public String toString() {
        return JSON.toJSON(this).toString();
    }
}
