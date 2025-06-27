package com.example.command;
import com.example.tool.DB;
import com.example.tool.DB2;
import com.example.tool.GetTime;

public class CreateHistoryCommand implements Command {
    public static void create(int id, String web)
    {
        String name= DB.queryUser(id).get(0).getName();
        String email=DB.queryUser(id).get(0).getEmail();
        String time= GetTime.get();
        if(web==null)web="SSO";
        switch (web)
        {
            case "SSO":
                web="SSO";
                break;
            case "http://localhost:8081/Web1_war_exploded/index":
                web="Web1";
                break;
            case "http://localhost:8082/Web2_war_exploded/index":
                web="Web2";
                break;
            default:
                web="errorWeb";
                break;
        }
        DB2.setTableName("historyLogin");
        DB2.createUser(id,name,email,time,web);
    }
    public void execute() {}
}
