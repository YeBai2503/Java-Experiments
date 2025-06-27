package com.yebai.bgradesystem;

import com.yebai.bgradesystem.pojo.Classroom;
import com.yebai.bgradesystem.service.ClassroomService;
import com.yebai.bgradesystem.service.CourseService;
import com.yebai.bgradesystem.service.GradeService;
import com.yebai.bgradesystem.service.StudentService;
import com.yebai.bgradesystem.singleton.AllData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BGradeSystemApplicationTests {
    @Autowired
    private StudentService studentService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private AllData allData;

    @Test
    void contextLoads() {
        System.out.println("Hello World!");
        studentService.addStudent(20221375, "急急急", "567", "男",3);
        //studentService.delStudentById(20221370);
    }
    @Test
    void testGradeService() {
        //gradeService.addGrade(44,2,2,3,2);
        //gradeService.delGrade(44,2);
        //gradeService.updGrade(44,2,2,3,2,50,60,70,80);
        System.out.println(gradeService.allGrade());
    }
    @Test
    void testClassroomService() {
        classroomService.addClassroom(7,"高数",3,4,5);
        System.out.println(classroomService.selClassroomById(5));
        classroomService.updClassroomById(1,"高数",3,4,5,50);
        classroomService.delClassroomById(5);
        System.out.println(classroomService.allClassroom());
    }
    @Test
    void testCourseService() {
        courseService.addCourse(10,"fff",3,4);
        courseService.addCourse(15,"ggg",3,4);
        courseService.addCourse(20,"ttt",3,4);
        System.out.println(courseService.selCourseById(3));
        System.out.println(courseService.allCourse());
        courseService.delCourseById(15);
        courseService.updCourseById(20,"kkkkkk",3,4,50);

    }
    @Test
    void dataInit()
    {
//        AllData allData = AllData.getInstance();
        allData.init();
    }

}
