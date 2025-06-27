package com.yebai.bgradesystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yebai.bgradesystem.dao.StudentDao;
import com.yebai.bgradesystem.factory.StudentFactory;
import com.yebai.bgradesystem.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentDao studentDao;
    StudentFactory studentFactory = new StudentFactory();
    @Autowired
    GradeService gradeService;
    @Autowired
    CourseService courseService;
    @Autowired
    ClassroomService classroomService;

    //增
    public void addStudent(int studentId, String name, String password, String sex,int level) {
        studentDao.insert(studentFactory.produce(studentId, name, password, sex, level));
    }
    //删
    public void delStudentById(int id) {
        Student student = selStudentById(id);
        //更新班级和课程总人数
        List<Grade> grades = gradeService.selGradeByStudent(id);
        for (Grade grade : grades) {
            Classroom classroom = classroomService.selClassroomById(grade.getClassroomId());
            Course course = courseService.selCourseById(grade.getCourseId());
            courseService.updCourseById(course.getId(),course.getName(),course.getDifficulty(),course.getCredit(),course.getNumStudents()-1);
            classroomService.updClassroomById(classroom.getId(),classroom.getName(),classroom.getTerm(),classroom.getCourseId(),classroom.getTeacherId(),classroom.getNumStudent()-1);
        }
        studentDao.deleteById(id);
        gradeService.delGradeByStudent(id);

    }
    //删全部
    public void delAllStudent() {
        studentDao.delete(null);  // 删除所有学生记录
    }

    //查
    public Student selStudentById(int id) {
        return studentDao.selectById(id);
    }
    //按名字查
    public Student selStudentByName(String name) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return studentDao.selectOne(queryWrapper);
    }
    //改
    public void updStudentById(int id, String name, String password, String sex,int level) {
        studentDao.updateById(studentFactory.produce(id, name, password, sex, level));
    }
    //更新绩点
    public void updGpaById(int id,int gpa) {
        // 查找现有学生记录
        Student student = selStudentById(id);
        student.setGpa(gpa); // 更新 GPA
        studentDao.updateById(student); // 保存更新后的学生信息
    }

    //返回所有
    public List<Student> allStudent() {
        return studentDao.selectList(null);
    }

}
