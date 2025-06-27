package com.yebai.bgradesystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yebai.bgradesystem.dao.GradeDao;
import com.yebai.bgradesystem.factory.GradeFactory;
import com.yebai.bgradesystem.pojo.Grade;
import com.yebai.bgradesystem.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {
    @Autowired
    private GradeDao gradeDao;
    private GradeFactory gradeFactory=new GradeFactory();

    //增
    public void addGrade(int studentId, int classroomId, int courseId, int credit,int term) {
        gradeDao.insert(gradeFactory.produce(studentId, classroomId, courseId, credit, term));
    }
    //删
    public void delGrade(int studentId,int classroomId) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentId", studentId);
        queryWrapper.eq("classroomId", classroomId);
        gradeDao.delete(queryWrapper);
    }
    //删学生成绩
    public void delGradeByStudent(int studentId) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentId", studentId);
        gradeDao.delete(queryWrapper);
    }
    //删班级成绩
    public void delGradeByClassroom(int classroomId) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("classroomId", classroomId);
        gradeDao.delete(queryWrapper);
    }
    //删全部
    public void delAllGrade() {
        gradeDao.delete(null);  // 删除所有成绩记录
    }
    //查
    public Grade selGrade(int studentId,int classroomId) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentId", studentId);
        queryWrapper.eq("classroomId", classroomId);
        return gradeDao.selectOne(queryWrapper);
    }
    //查某人课程成绩
    public Grade selGradeByCourse(int studentId,int courseId) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentId", studentId);
        queryWrapper.eq("courseId", courseId);
        return gradeDao.selectOne(queryWrapper);
    }
    //查某人成绩
    public List<Grade> selGradeByStudent(int studentId) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentId", studentId);
        return gradeDao.selectList(queryWrapper);
    }
    //查班成绩
    public List<Grade> selClassGrade(int classroomId) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("classroomId", classroomId);
        return gradeDao.selectList(queryWrapper);
    }
    //查单科成绩
    public List<Grade> selCourseGrade(int courseId) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("courseId", courseId);
        return gradeDao.selectList(queryWrapper);
    }
    //改
    public void updGrade(int studentId, int classroomId, int courseId, int credit,int term,int usualScore,int middleScore,int experimentScore,int endScore) {
        Grade grade = gradeFactory.produce2(studentId, classroomId, courseId, credit, term,usualScore,middleScore,experimentScore,endScore);
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentId", studentId);
        queryWrapper.eq("classroomId", classroomId);
        // 执行更新操作，使用新的 Grade 对象覆盖已有记录
        gradeDao.update(grade, queryWrapper);
    }

    //查某学生所有课程
    public List<Grade> selStudentGrade(int studentId) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentId", studentId);
        return gradeDao.selectList(queryWrapper);
    }

    //返回所有
    public List<Grade> allGrade() {
        return gradeDao.selectList(null);
    }



}
