package com.yebai.bgradesystem.service;

import com.yebai.bgradesystem.dao.CourseDao;
import com.yebai.bgradesystem.factory.CourseFactory;
import com.yebai.bgradesystem.pojo.Classroom;
import com.yebai.bgradesystem.pojo.Course;
import com.yebai.bgradesystem.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseDao courseDao;
    private CourseFactory courseFactory=new CourseFactory();
    @Autowired
    private ClassroomService classroomService;

    //增
    public void addCourse(int id, String name, int difficulty,int credit) {
        courseDao.insert(courseFactory.produce(id, name, difficulty,credit));
    }
    //删
    public void delCourseById(int id) {
        List<Classroom>classrooms=classroomService.selClassroomByCourse(id);
        for (Classroom classroom:classrooms)
        {
            classroomService.delClassroomById(classroom.getId());
        }
        courseDao.deleteById(id);
    }
    //查
    public Course selCourseById(int id) {
        return courseDao.selectById(id);
    }
    //改
    public void updCourseById(int id, String name, int difficulty,int credit,int numStudents) {
        courseDao.updateById(courseFactory.produce(id, name,difficulty,credit,numStudents));
    }

    //返回所有
    public List<Course> allCourse() {
        return courseDao.selectList(null);
    }



}
