package com.yebai.bgradesystem.tool;

import com.alibaba.fastjson2.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Message {
    public static String okMessage(String a)
    {
        Map<String,Object> message = new HashMap<>();
        message.put("code", 200);
        message.put("message", a);
        String m=JSONObject.toJSONString(message);
        return m;
    }
    public static String okMessageByData(String a,Object data)
    {
        Map<String,Object> message = new HashMap<>();
        message.put("code", 200);
        message.put("message", a);
        message.put("data", data);
        String m=JSONObject.toJSONString(message);
        return m;
    }
}
