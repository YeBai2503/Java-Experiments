package com.ssoserver.model;

import java.util.ArrayList;

//数据访问对象模式（Data Access Object Pattern）或 DAO 模式
public interface Dao {
    public void forbid(int id,int two);
    public ArrayList<User> getForbidUsers();
}
