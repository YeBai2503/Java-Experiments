package com.yebai.bgradesystem.factory;

import com.yebai.bgradesystem.pojo.Manager;

public class ManagerFactory implements Factory{
    @Override
    public Manager produce(int id, Object... args) {
        Manager manager = new Manager();
        manager.setId(id);
        manager.setName((String) args[0]);
        manager.setPassword((String) args[1]);
        return manager;
    }

}
