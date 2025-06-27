package com.ssoserver.model;

public class User implements Model {
    public static final String SECRET_KEY = "irb45rjkwkw6";
    private int id;
    private String name;
    private String password;
    private String email;
    private int isManager;
    private int isForbid;
    private String time;
    private String web;

    public void setTime(String time) {
        this.time = time;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getTime() {
        return time;
    }

    public String getWeb() {
        return web;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIsManager(int isManager) {
        this.isManager = isManager;
    }

    public void setIsForbid(int isForbid) {
        this.isForbid = isForbid;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getIsManager() {
        return isManager;
    }

    public int getIsForbid() {
        return isForbid;
    }
}
