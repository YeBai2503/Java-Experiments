package com.ssoserver.factory;

import com.ssoserver.model.HistoryBO;
import com.ssoserver.model.Model;

public class HistoryBOFactory implements Factory{
    @Override
    public Model produce(int id, String username, String password, String email, int isManager, int isForbid) {
        return null;
    }
    public HistoryBO produce2()
    {
        return new HistoryBO();
    }
}
