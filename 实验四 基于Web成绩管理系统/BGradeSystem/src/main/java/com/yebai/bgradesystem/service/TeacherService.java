package com.yebai.bgradesystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yebai.bgradesystem.dao.TeacherDao;
import com.yebai.bgradesystem.factory.TeacherFactory;
import com.yebai.bgradesystem.pojo.Student;
import com.yebai.bgradesystem.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherDao teacherDao;
    TeacherFactory teacherFactory = new TeacherFactory();
    @Autowired
    private ClassroomService classroomService;

    //增
    public void addTeacher(int id, String name, String password, String sex) {
        teacherDao.insert(teacherFactory.produce(id, name, password, sex));
    }
    //删
    public void delTeacherById(int id) {
        int classroomId=teacherDao.selectById(id).getClassId();
        teacherDao.deleteById(id);
        //删除相关的班级
        classroomService.delClassroomById(classroomId);
    }
//    //删班级老师
//    public void delTeacherByClass(int classId) {
//        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("classId", classId);
//        teacherDao.delete(queryWrapper);
//    }
    //删课程老师
    public void delTeacherByCourse(int courseId) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("courseId", courseId);
        teacherDao.delete(queryWrapper);
    }
    //删全部
    public void delAllTeacher() {
        teacherDao.delete(null);  // 删除所有教师记录
    }
    //查
    public Teacher selTeacherById(int id) {
        return teacherDao.selectById(id);
    }
    //改
    public void updTeacherById(int id, String name, String password, String sex,int classId) {
        teacherDao.updateById(teacherFactory.produce(id, name, password, sex, classId));
    }

    //返回所有
    public List<Teacher> allTeacher() {
        return teacherDao.selectList(null);
    }

}
