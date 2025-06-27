package com.yebai.bgradesystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yebai.bgradesystem.pojo.Course;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseDao extends BaseMapper<Course> {
}
