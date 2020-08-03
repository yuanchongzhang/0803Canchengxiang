package com.mingmen.canting.base;

import java.util.List;

public class BaseBean {

    /**
     * code : 0
     * message : 操作成功
     * time : 2020-07-25 10:52:36
     * data : ["发送成功！"]
     */

    private int code;
    private String message;
    private String time;
    private List<Object> data;

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

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
