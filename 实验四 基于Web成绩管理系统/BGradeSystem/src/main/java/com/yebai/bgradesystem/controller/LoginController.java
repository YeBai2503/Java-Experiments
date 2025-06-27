package com.yebai.bgradesystem.controller;

import com.yebai.bgradesystem.service.ManagerService;
import com.yebai.bgradesystem.service.StudentService;
import com.yebai.bgradesystem.service.TeacherService;
import com.yebai.bgradesystem.tool.GenerateGaussian;
import com.yebai.bgradesystem.tool.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ManagerService managerService;

    // 学生登录
    @RequestMapping(value = "/loginStudent", method = RequestMethod.POST)
    public String loginStudent(@RequestParam("id") int id,
                             @RequestParam("password") String password) {
        if(studentService.selStudentById(id)==null)return Message.okMessage("false");
        if(studentService.selStudentById(id).getPassword().equals(password)) return Message.okMessage("true");
        else return Message.okMessage("false");
    }
    // 教师登录
    @RequestMapping(value = "/loginTeacher", method = RequestMethod.POST)
    public String loginTeacher(@RequestParam("id") int id,
                               @RequestParam("password") String password) {
        if(teacherService.selTeacherById(id)==null)return Message.okMessage("false");
        if(teacherService.selTeacherById(id).getPassword().equals(password)) return Message.okMessage("true");
        else return Message.okMessage("false");
    }
    // 管理员登录
    @RequestMapping(value = "/loginManager", method = RequestMethod.POST)
    public String loginManager(@RequestParam("id") int id,
                               @RequestParam("password") String password) {
        if(managerService.selManagerById(id)==null)return Message.okMessage("false");
        if(managerService.selManagerById(id).getPassword().equals(password)) return Message.okMessage("true");
        else return Message.okMessage("false");
    }

}
