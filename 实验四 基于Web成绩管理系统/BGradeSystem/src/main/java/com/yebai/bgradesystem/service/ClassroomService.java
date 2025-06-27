package com.yebai.bgradesystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yebai.bgradesystem.dao.ClassroomDao;
import com.yebai.bgradesystem.dao.CourseDao;
import com.yebai.bgradesystem.factory.ClassroomFactory;
import com.yebai.bgradesystem.factory.CourseFactory;
import com.yebai.bgradesystem.pojo.Classroom;
import com.yebai.bgradesystem.pojo.Course;
import com.yebai.bgradesystem.pojo.Student;
import com.yebai.bgradesystem.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {
    @Autowired
    private ClassroomDao classroomDao;
    private ClassroomFactory classroomFactory=new ClassroomFactory();
    @Autowired
    private GradeService gradeService;
    @Autowired
    private CourseDao courseDao;

    //增
    public void addClassroom(int id, String name, int term,int courseId,int teacherId) {
        classroomDao.insert(classroomFactory.produce(id, name, term, courseId, teacherId));

    }
    //删
    public void delClassroomById(int id) {
        //删除相关的选课和成绩
        gradeService.delGradeByClassroom(id);
        //更新课程人数
        CourseFactory courseFactory=new CourseFactory();
        Course course=courseDao.selectById(selClassroomById(id).getCourseId());
        courseDao.updateById(courseFactory.produce(course.getId(),course.getName(),course.getDifficulty(),course.getCredit(),course.getNumStudents()-selClassroomById(id).getNumStudent()));
        classroomDao.deleteById(id);
    }
//    //删老师班级
//    public void delClassroomByTeacher(int teacherId) {
//        QueryWrapper<Classroom> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("teacherId", teacherId);
//        classroomDao.delete(queryWrapper);
//    }
    //删课程班级
    public void delClassroomByCourse(int courseId) {
        QueryWrapper<Classroom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("courseId", courseId);
        classroomDao.delete(queryWrapper);
    }
    //删全部
    public void delAllClassroom() {
        classroomDao.delete(null);  // 删除所有班级记录
    }
    //查
    public Classroom selClassroomById(int id) {
        return classroomDao.selectById(id);
    }
    //查课程班级
    public List<Classroom> selClassroomByCourse(int courseId) {
        QueryWrapper<Classroom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("courseId", courseId);
        return classroomDao.selectList(queryWrapper);
    }
    //改
    public void updClassroomById(int id, String name, int term,int courseId,int teacherId,int numStudent) {
        classroomDao.updateById(classroomFactory.produce(id, name, term,courseId,teacherId,numStudent));
    }

    //返回所有
    public List<Classroom> allClassroom() {
        return classroomDao.selectList(null);
    }


}
