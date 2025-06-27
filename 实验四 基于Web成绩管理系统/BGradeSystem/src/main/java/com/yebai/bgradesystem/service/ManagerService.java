package com.yebai.bgradesystem.service;

import com.yebai.bgradesystem.dao.ManagerDao;
import com.yebai.bgradesystem.factory.ManagerFactory;
import com.yebai.bgradesystem.pojo.Manager;
import com.yebai.bgradesystem.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {
    @Autowired
    private ManagerDao managerDao;

    ManagerFactory managerFactory = new ManagerFactory();

    //增
    public void addManager(int id, String name, String password) {
        managerDao.insert(managerFactory.produce(id, name, password));
    }
    //删
    public void delManagerById(int id) {
        managerDao.deleteById(id);
    }
    //查
    public Manager selManagerById(int id) {
        return managerDao.selectById(id);
    }
    //改
    public void updManagerById(int id, String name, String password) {
        managerDao.updateById(managerFactory.produce(id, name, password));
    }

    //返回所有
    public List<Manager> allManager() {
        return managerDao.selectList(null);
    }

}
