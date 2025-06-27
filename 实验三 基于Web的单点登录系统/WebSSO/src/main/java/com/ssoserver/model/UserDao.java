package com.ssoserver.model;

import java.util.ArrayList;

//数据访问对象模式（Data Access Object Pattern）或 DAO 模式
public class UserDao implements Dao{
    //禁用账号
    @Override
    public void forbid(int forbidId, int isForbid) {
        DB.updateForbid(forbidId,1-isForbid);
    }

    //获取非管理员的用户列表
    @Override
    public ArrayList<User> getForbidUsers() {
        return DB.queryNoManager();
    }
}
