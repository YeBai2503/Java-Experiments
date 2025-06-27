package com.example.command;

import com.example.tool.DB;
import com.example.tool.DB2;
import com.example.tool.GetTime;

public class CreateOnlineCommand implements Command {
    @Override
    public void execute(int id) {
        String name= DB.queryUser(id).get(0).getName();
        String email=DB.queryUser(id).get(0).getEmail();
        String time= GetTime.get();
        String web="Web2";//记得修改

        DB2.setTableName("onlineUser");
        if(DB2.queryUser2(id,web).size() > 0)DB2.updateUser(id, name, email, time,web);
        else DB2.createUser(id, name, email, time,web);
        System.out.println("添加onlineUser: "+id);
    }

}
