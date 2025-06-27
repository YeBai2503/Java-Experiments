package com.yebai.bgradesystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yebai.bgradesystem.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentDao extends BaseMapper<Student> {
    @Select("SELECT * FROM student ")
    public List<Student> selectAllStudents();
}
