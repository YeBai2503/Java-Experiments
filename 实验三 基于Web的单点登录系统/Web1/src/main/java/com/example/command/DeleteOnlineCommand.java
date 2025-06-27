package com.example.command;

import com.example.tool.DB2;

public class DeleteOnlineCommand implements Command {
    public void execute(int id) {
        DB2.setTableName("onlineUser");
        DB2.deleteUser(id,"Web1");//记得修改
        System.out.println("删除onlineUser: "+id);
    }

    @Override
    public void execute() {

    }
}
