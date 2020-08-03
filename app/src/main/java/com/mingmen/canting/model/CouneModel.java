package com.mingmen.canting.model;

import java.util.List;

public class CouneModel {


    /**
     * code : 0
     * message : 操作成功
     * time : 2020-08-03 11:40:29
     * data : [0]
     */

    private int code;
    private String message;
    private String time;
    private List<Integer> data;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
