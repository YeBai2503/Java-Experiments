package com.yebai.bgradesystem.controller;

import com.yebai.bgradesystem.factory.CourseFactory;
import com.yebai.bgradesystem.service.CourseService;
import com.yebai.bgradesystem.tool.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    // 添加课程
    @RequestMapping(value = "/addCourse", method = RequestMethod.POST)
    public String addCourse(@RequestParam("id") int id,
                             @RequestParam("name") String name,
                             @RequestParam("difficulty") int difficulty,
                             @RequestParam("credit") int credit) {
        courseService.addCourse(id, name, difficulty, credit);
        return Message.okMessage("添加课程成功!");
    }
    // 查询所有课程
    @RequestMapping(value = "/allCourse", method = RequestMethod.GET)
    public String allCourse() {
        return Message.okMessageByData("查询所有课程成功!",courseService.allCourse());
    }
    // 删除课程
    @RequestMapping(value = "/delCourseById/{id}", method = RequestMethod.DELETE)
    public String deleteCourseById(@PathVariable("id") int id) {
        courseService.delCourseById(id);
        return Message.okMessage("删除课程成功! id="+id);
    }

}
