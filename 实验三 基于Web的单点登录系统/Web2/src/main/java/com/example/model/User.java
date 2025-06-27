package com.example.model;

public class User {
    public static final String SECRET_KEY = "irb45rjkwkw6";
    private int id;
    private String name;
    private String password;
    private String email;
    private int isManager;
    private int isForbid;
    private String time;
    private String web;

    public String getTime() {
        return time;
    }

    public String getWeb() {
        return web;
    }

    public User(int id, String name, String password, String email, int isManager, int isForbid) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.isManager = isManager;
        this.isForbid = isForbid;
    }
    public User(int id, String name, String email, String time, String web) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.time = time;
        this.web = web;
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
