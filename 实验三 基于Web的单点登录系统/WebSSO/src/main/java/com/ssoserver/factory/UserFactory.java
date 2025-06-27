package com.ssoserver.factory;

import com.ssoserver.model.Model;
import com.ssoserver.model.User;

public class UserFactory implements Factory {
    @Override
    public User produce(int id, String name, String password, String email, int isManager, int isForbid) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        user.setIsManager(isManager);
        user.setIsForbid(isForbid);
        return user;
    }
    public User produce2(int id, String name,  String email, String time,String web) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setTime(time);
        user.setWeb(web);
        return user;
    }
}
