package com.mingmen.canting.model;

public class UserInfo {

    public UserInfo(String ame, long id) {
        this.name = name;
        this.age = age;
    }

    public String name;

    public String age;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
