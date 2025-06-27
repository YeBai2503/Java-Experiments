package com.ssoserver.model;

import java.util.ArrayList;

public class HistoryBO implements Model{
    private ArrayList<User> history;
    public  ArrayList<User> setHistory(int id) {
        DB2.setTableName("historyLogin");
        history = DB2.queryUser(id,"");
        return history;
    }

    public ArrayList<User> getHistory() {
        return history;
    }
}
