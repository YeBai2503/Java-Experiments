package com.yebai.bgradesystem.controller;

import com.yebai.bgradesystem.service.ManagerService;
import com.yebai.bgradesystem.singleton.AllData;
import com.yebai.bgradesystem.tool.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private AllData allData;

    // 注册管理员
    @RequestMapping(value = "/addManager", method = RequestMethod.POST)
    public String addManager(@RequestParam("id") int id,
                             @RequestParam("name") String name,
                             @RequestParam("password") String password) {
        managerService.addManager(id, name, password);
        return Message.okMessage("注册管理员成功!");
    }

    // 注销管理员
    @RequestMapping(value = "/delManagerById/{id}", method = RequestMethod.DELETE)
    public String deleteManager(@PathVariable("id") int id) {
        managerService.delManagerById(id);
        return Message.okMessage("注销管理员成功! id="+id);
    }

    // 按id查询管理员
    @RequestMapping(value = "/selManagerById/{id}", method = RequestMethod.GET)
    public String selectManagerById(@PathVariable("id") int id) {
        return Message.okMessageByData("查询管理员成功! id="+id,managerService.selManagerById(id));
    }

    // 查询所有管理员
    @RequestMapping(value = "/allManager", method = RequestMethod.GET)
    public String allManager() {
        return Message.okMessageByData("查询所有管理员成功!",managerService.allManager());
    }

    // 修改管理员信息
    @RequestMapping(value = "/updManager", method = RequestMethod.PUT)
    public String updateManager(@RequestParam("id") int id,
                                @RequestParam("name") String name,
                                @RequestParam("password") String password) {
        managerService.updManagerById(id, name, password);
        return Message.okMessage("修改管理员信息成功! id=" + id);
    }

    //重新生成数据库
    @RequestMapping(value = "/GenerateData", method = RequestMethod.POST)
    public String reGenerateDatabase() {
        allData.init();
        return Message.okMessage("重新生成数据库成功!");
    }
    //一键选课
    @RequestMapping(value = "/chooseClass", method = RequestMethod.POST)
    public String chooseClass() {
        allData.clearData();
        allData.dataGenerate();
        allData.classSelect();
        return Message.okMessage("一键选课成功!");
    }
    //一键生成成绩
    @RequestMapping(value = "/generateGrade", method = RequestMethod.POST)
    public String generateGrade() {
        allData.gradeGet();
        return Message.okMessage("一键生成成绩成功!");
    }

    //清除数据库
    @RequestMapping(value = "/clearData", method = RequestMethod.DELETE)
    public String clearDatabase() {
        allData.clearData();
        return Message.okMessage("清除数据库成功!");
    }


}
