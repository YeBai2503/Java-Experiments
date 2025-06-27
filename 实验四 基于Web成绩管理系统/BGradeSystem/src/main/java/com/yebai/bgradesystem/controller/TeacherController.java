package com.yebai.bgradesystem.controller;

import com.yebai.bgradesystem.pojo.Teacher;
import com.yebai.bgradesystem.service.ClassroomService;
import com.yebai.bgradesystem.service.TeacherService;
import com.yebai.bgradesystem.singleton.AllData;
import com.yebai.bgradesystem.tool.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private AllData allData;

    // 添加教师
    @RequestMapping(value = "/addTeacher", method = RequestMethod.POST)
    public String addTeacher(@RequestParam("id") int id,
                             @RequestParam("name") String name,
                             @RequestParam("password") String password,
                             @RequestParam("sex") String sex) {
        teacherService.addTeacher(id, name, password, sex);
        return Message.okMessage("添加教师成功!");
    }

    // 删除教师
    @RequestMapping(value = "/delTeacherById/{id}", method = RequestMethod.DELETE)
    public String deleteTeacher(@PathVariable("id") int id) {
        teacherService.delTeacherById(id);
        return Message.okMessage("删除教师成功! id="+id);
    }

    // 按id查询教师
    @RequestMapping(value = "/selTeacherById/{id}", method = RequestMethod.GET)
    public String selectTeacherById(@PathVariable("id") int id) {
        Map<String, Object> map = new HashMap<>();
        Teacher teacher = teacherService.selTeacherById(id);
        map.put("teacher", teacher);
        map.put("classroom", classroomService.selClassroomById(teacher.getClassId()));
        return Message.okMessageByData("查询教师成功! id="+id, map);
    }

    // 查询所有教师
    @RequestMapping(value = "/allTeacher", method = RequestMethod.GET)
    public String allTeacher() {
        return Message.okMessageByData("查询所有教师成功!",teacherService.allTeacher());
    }
    // 修改教师信息
    @RequestMapping(value = "/updTeacher", method = RequestMethod.PUT)
    public String updateStudent(@RequestParam("id") int id,
                                @RequestParam("name") String name,
                                @RequestParam("password") String password,
                                @RequestParam("sex") String sex,
                                @RequestParam("classId") int classId) {
        teacherService.updTeacherById(id, name, password, sex, classId);
        return Message.okMessage("修改教师信息成功! id=" + id);
    }

    // 批量添加成绩
    @RequestMapping(value = "/manyGradeAuto", method = RequestMethod.POST)
    public String addTeacher(@RequestParam("classId") int classId,
                             @RequestParam("studentIds")List<Integer> studentIds) {
        allData.manyGradeGet(studentIds, classId);
        return Message.okMessage("批量生成成绩成功!");
    }


}
