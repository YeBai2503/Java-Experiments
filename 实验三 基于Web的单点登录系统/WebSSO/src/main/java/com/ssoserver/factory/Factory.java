package com.ssoserver.factory;
import com.ssoserver.model.Model;

public interface Factory {
    public Model produce(int id, String username, String password, String email, int isManager,int isForbid);
}
