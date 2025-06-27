package com.example.command;

import com.example.tool.DB2;

public class DeleteOnlineCommand implements Command {
    @Override
    public void execute(int id) {
        DB2.setTableName("onlineUser");
        DB2.deleteUser(id,"Web2");//记得修改
        System.out.println("删除onlineUser: "+id);
    }
}
