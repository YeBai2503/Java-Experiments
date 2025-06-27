package com.ssoserver.model;

import com.ssoserver.factory.UserFactory;
import com.ssoserver.tool.GetTime;

import java.util.ArrayList;

public class OnlineBO implements Model{
    private ArrayList<User> online;
    public  void setOnline(int id,String web) {
        DB2.setTableName("onlineUser");
        online = DB2.queryUser(-1,web);
        UserFactory userFactory = new UserFactory();
        //记得改网站
        int isHaven=0;//判断当前数据库是否有当前用户，如果没有，则自动添加自己在线
        for (User user : online) {
            if (user.getId() == id && user.getWeb().equals(web)) {
                isHaven=1;
                break;
            }
        }
        if (isHaven==0)
        {
            online.add(userFactory.produce2(id, DB.queryUser(id,"").get(0).getName(),DB.queryUser(id,"").get(0).getEmail(), GetTime.get(),web));
            System.out.println("1111111自动添加自己在线");
        }
    }

    public ArrayList<User> getOnline() {
        return online;
    }
}
