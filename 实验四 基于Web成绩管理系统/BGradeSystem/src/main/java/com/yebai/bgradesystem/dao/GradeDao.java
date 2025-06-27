package com.yebai.bgradesystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yebai.bgradesystem.pojo.Grade;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GradeDao extends BaseMapper<Grade> {
}
