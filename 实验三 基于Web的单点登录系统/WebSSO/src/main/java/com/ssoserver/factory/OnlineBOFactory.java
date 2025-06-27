package com.ssoserver.factory;

import com.ssoserver.model.Model;
import com.ssoserver.model.OnlineBO;

public class OnlineBOFactory implements Factory{
    @Override
    public Model produce(int id, String username, String password, String email, int isManager, int isForbid) {
        return null;
    }
    public OnlineBO produce2()
    {
        return new OnlineBO();
    }
}
