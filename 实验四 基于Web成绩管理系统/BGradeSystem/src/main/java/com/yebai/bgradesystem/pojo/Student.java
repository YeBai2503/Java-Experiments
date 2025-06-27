package com.yebai.bgradesystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yebai.bgradesystem.service.GradeService;
import com.yebai.bgradesystem.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value="student")
public class Student {
    @TableId(value="id" ,type= IdType.NONE)
    private int id;
    @TableField(value="name")
    private String name;
    @TableField(value="password")
    private String password;
    @TableField(value="sex")
    private String sex;
    @TableField(value="level")
    private int level;
    @TableField(value="gpa")
    private double gpa;

    @TableField(exist = false)
    private ArrayList<Grade> grades =new ArrayList<Grade>();//成绩(包含教学班)
    @TableField(exist = false)
    private ArrayList<Integer> courseIDs =new ArrayList<Integer>();//选的课程
    @TableField(exist = false)
    private Map<String,Integer> courseGradeMap=new HashMap<>();
    public void addCourseIDs(int courseID){
        this.courseIDs.add(courseID);
    }
    public void addCourseGradeMap(String courseName,int grade){
        courseGradeMap.put(courseName,grade);
    }
    public void autoGPA(GradeService gradeService, StudentService studentService)
    {
        int GPA=0;
        int sumCredit=0;
        this.grades=new ArrayList<>(gradeService.selStudentGrade(this.id));
        for (int i = 0; i < grades.size(); i++) {
            if(grades.get(i).getSumScore()!=-1){
                sumCredit+=grades.get(i).getCredit();
                GPA+=grades.get(i).getCredit()*grades.get(i).getSumScore();
            }
        }
        this.gpa = (int)GPA/sumCredit;
        studentService.updGpaById(this.id,GPA/sumCredit);
    }

}
