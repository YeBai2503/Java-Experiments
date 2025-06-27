package com.yebai.bgradesystem.controller;

import com.yebai.bgradesystem.pojo.Course;
import com.yebai.bgradesystem.pojo.Grade;
import com.yebai.bgradesystem.pojo.Student;
import com.yebai.bgradesystem.service.CourseService;
import com.yebai.bgradesystem.service.GradeService;
import com.yebai.bgradesystem.service.StudentService;
import com.yebai.bgradesystem.tool.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;

@RestController
public class AllRankController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private CourseService courseService;

    private ArrayList<Course> allCourses;


    // 全部学生按学号排序
    @RequestMapping(value = "/idRank", method = RequestMethod.GET)
    public String idRank() {
        ArrayList<Student> students = getAllRank();
        students.sort(Comparator.comparingInt(Student::getId));
        return Message.okMessageByData("学号排序总排名", students);
    }

    // 全部学生按总成绩排序
    @RequestMapping(value = "/gpaRank", method = RequestMethod.GET)
    public String gpaRank() {
        ArrayList<Student> students = getAllRank();
        students.sort(Comparator.comparingDouble(Student::getGpa).reversed());
        return Message.okMessageByData("总成绩排序总排名", students);
    }

    // 全部学生按指定课程成绩排序
    @RequestMapping(value = "/courseRank/{courseName}", method = RequestMethod.GET)
    public String courseRank(@PathVariable("courseName") String courseName) {
        ArrayList<Student> students = getAllRank();
        students.sort(Comparator.comparingInt((Student s) -> s.getCourseGradeMap().getOrDefault(courseName, 0)).reversed());
        return Message.okMessageByData("总成绩排序总排名", students);
    }


    //获取所有学生排名相关信息
    public  ArrayList<Student> getAllRank()
    {
        allCourses=new ArrayList<>(courseService.allCourse());
        ArrayList<Student> students = new ArrayList<>(studentService.allStudent());
        for (int i = 0; i < students.size(); i++)
        {
            for (int j = 0; j < allCourses.size(); j++)
            {
                String courseName = allCourses.get(j).getName();
                int courseId = allCourses.get(j).getId();
                int score;
                if(gradeService.selGradeByCourse(students.get(i).getId(), courseId)!=null)
                    score = (int)gradeService.selGradeByCourse(students.get(i).getId(), courseId).getSumScore();
                else score=-1;
                students.get(i).addCourseGradeMap(courseName, score);
            }
        }
        return students;
    }
}
