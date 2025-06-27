package com.yebai.bgradesystem.controller;

import com.yebai.bgradesystem.pojo.Course;
import com.yebai.bgradesystem.pojo.Grade;
import com.yebai.bgradesystem.pojo.Student;
import com.yebai.bgradesystem.service.CourseService;
import com.yebai.bgradesystem.service.GradeService;
import com.yebai.bgradesystem.service.StudentService;
import com.yebai.bgradesystem.tool.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StatisticsController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private CourseService courseService;

    // 单科成绩统计分布
    @RequestMapping(value = "/courseStatistics", method = RequestMethod.GET)
    public String courseStatistics() {
        Map<String,ArrayList<Integer>> data = new HashMap<>();
        ArrayList<Course> courseList = new ArrayList<>(courseService.allCourse());
        for(int i=0;i<courseList.size();i++)
        {
            String courseName = courseList.get(i).getName();
            ArrayList<Grade> gradeList = new ArrayList<>(gradeService.selCourseGrade(courseList.get(i).getId()));
            ArrayList<Integer> gradeStatistics = new ArrayList<>();
            for (int k = 0; k < 8; k++) {
                gradeStatistics.add(0);
            }
            for(int j=0;j<gradeList.size();j++)
            {
                if(gradeList.get(j).getSumScore()>=90)gradeStatistics.set(0,gradeStatistics.get(0)+1);
                else if(gradeList.get(j).getSumScore()>=80)gradeStatistics.set(1,gradeStatistics.get(1)+1);
                else if(gradeList.get(j).getSumScore()>=70)gradeStatistics.set(2,gradeStatistics.get(2)+1);
                else if(gradeList.get(j).getSumScore()>=60)gradeStatistics.set(3,gradeStatistics.get(3)+1);
                else if(gradeList.get(j).getSumScore()>=50)gradeStatistics.set(4,gradeStatistics.get(4)+1);
                else if(gradeList.get(j).getSumScore()>=40)gradeStatistics.set(5,gradeStatistics.get(5)+1);
                else if(gradeList.get(j).getSumScore()>=30)gradeStatistics.set(6,gradeStatistics.get(6)+1);
                else gradeStatistics.set(7,gradeStatistics.get(7)+1);
            }
            data.put(courseName,gradeStatistics);
        }
        return Message.okMessageByData("单科成绩统计分布",data);
    }

    // 总成绩统计分布
    @RequestMapping(value = "/gpaStatistics", method = RequestMethod.GET)
    public String gpaStatistics() {
        ArrayList<Student> gradeList = new ArrayList<>(studentService.allStudent());
        ArrayList<Integer> gradeStatistics = new ArrayList<>();
        for (int k = 0; k < 8; k++) {
            gradeStatistics.add(0);
        }
        for(int j=0;j<gradeList.size();j++)
        {
            if(gradeList.get(j).getGpa()>=90)gradeStatistics.set(0,gradeStatistics.get(0)+1);
            else if(gradeList.get(j).getGpa()>=80)gradeStatistics.set(1,gradeStatistics.get(1)+1);
            else if(gradeList.get(j).getGpa()>=70)gradeStatistics.set(2,gradeStatistics.get(2)+1);
            else if(gradeList.get(j).getGpa()>=60)gradeStatistics.set(3,gradeStatistics.get(3)+1);
            else if(gradeList.get(j).getGpa()>=50)gradeStatistics.set(4,gradeStatistics.get(4)+1);
            else if(gradeList.get(j).getGpa()>=40)gradeStatistics.set(5,gradeStatistics.get(5)+1);
            else if(gradeList.get(j).getGpa()>=30)gradeStatistics.set(6,gradeStatistics.get(6)+1);
            else gradeStatistics.set(7,gradeStatistics.get(7)+1);
        }
        return Message.okMessageByData("总成绩统计分布",gradeStatistics);
    }
}
