package com.yebai.bgradesystem.controller;

import com.yebai.bgradesystem.pojo.Classroom;
import com.yebai.bgradesystem.pojo.Course;
import com.yebai.bgradesystem.pojo.Teacher;
import com.yebai.bgradesystem.service.ClassroomService;
import com.yebai.bgradesystem.service.TeacherService;
import com.yebai.bgradesystem.tool.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class ClassroomController {
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private TeacherService teacherService;
    // 查询班级信息
    // 添加班级
    @RequestMapping(value = "/addClassroom", method = RequestMethod.POST)
    public String addClassroom(@RequestParam("id") int id,
                            @RequestParam("name") String name,
                            @RequestParam("term") int term,
                            @RequestParam("courseId") int courseId,
                            @RequestParam("teacherId") int teacherId) {
        classroomService.addClassroom(id, name, term, courseId, teacherId);
        Teacher t=teacherService.selTeacherById(teacherId);
        teacherService.updTeacherById(t.getId(),t.getName(),t.getPassword(),t.getSex(),id);
        return Message.okMessage("添加班级成功!");
    }
    // 查询所有班级
    @RequestMapping(value = "/allClassroom", method = RequestMethod.GET)
    public String allClassroom() {
        ArrayList<Classroom>classrooms=new ArrayList<>(classroomService.allClassroom());
        for(Classroom classroom:classrooms){
            int teacherId=classroom.getTeacherId();
            String teacherName=teacherService.selTeacherById(teacherId).getName();
            classroom.setTeacherName(teacherName);
        }
        return Message.okMessageByData("查询所有班级成功!",classrooms);
    }
    // 删除班级
    @RequestMapping(value = "/delClassroomById/{id}", method = RequestMethod.DELETE)
    public String deleteClassroomById(@PathVariable("id") int id) {
        classroomService.delClassroomById(id);
        return Message.okMessage("删除班级成功! id="+id);
    }
}
