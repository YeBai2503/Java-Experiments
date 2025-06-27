package com.yebai.bgradesystem.controller;

import com.yebai.bgradesystem.pojo.Grade;
import com.yebai.bgradesystem.pojo.Student;
import com.yebai.bgradesystem.service.CourseService;
import com.yebai.bgradesystem.service.GradeService;
import com.yebai.bgradesystem.service.StudentService;
import com.yebai.bgradesystem.tool.GenerateGaussian;
import com.yebai.bgradesystem.tool.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StudentGradeController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private CourseService courseService;

    // 名字或id搜索学生成绩
    @RequestMapping(value = "/searchStudentGrade/{IdOrName}", method = RequestMethod.GET)
    public String courseStatistics(@PathVariable("IdOrName") String IdOrName) {
        Student student ;
        if (isNum(IdOrName)) {
            student = studentService.selStudentById(Integer.parseInt(IdOrName));
        } else {
            student = studentService.selStudentByName(IdOrName);
        }
        Map<String, Object> data= new HashMap<>();
        data.put("student", student);
        ArrayList<Grade> grades = new ArrayList<>(gradeService.selStudentGrade(student.getId()));
        for (Grade grade : grades) {
            grade.setCourseName(courseService.selCourseById(grade.getCourseId()).getName());
        }
        grades.sort(Comparator.comparingInt(Grade::getClassroomId));

        data.put("grade", grades);
        return Message.okMessageByData("查询学生成绩成功",data);
    }

    // 修改成绩
    @RequestMapping(value = "/modifyGrade", method = RequestMethod.PUT)
    public String addStudent(@RequestParam("id") int id,
                             @RequestParam("classId") int classId,
                             @RequestParam("usualScore") int usualScore,
                             @RequestParam("middleScore") int middleScore,
                             @RequestParam("experimentScore") int experimentScore,
                             @RequestParam("endScore") int endScore) {

        Grade grade = gradeService.selGrade(id, classId);
        gradeService.updGrade(grade.getStudentId(),grade.getClassroomId(),grade.getCourseId(),grade.getCredit(),grade.getTerm(),usualScore,middleScore,experimentScore,endScore);
        return Message.okMessage("更新成绩成功!");
    }



    public static boolean isNum(String str) {
        try {
            Integer.parseInt(str); // 尝试将字符串转换为整数
            return true; // 如果没有抛出异常，则返回 true
        } catch (NumberFormatException e) {
            return false; // 如果抛出异常，返回 false
        }
    }
}
