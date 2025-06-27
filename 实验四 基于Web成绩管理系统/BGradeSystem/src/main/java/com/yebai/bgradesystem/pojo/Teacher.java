package com.yebai.bgradesystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value="teacher")
public class Teacher {
    @TableId(value="id" ,type= IdType.NONE)
    private int id;
    @TableField(value="name")
    private String name;
    @TableField(value="password")
    private String password;
    @TableField(value="sex")
    private String sex;
    @TableField(value="classId")
    private int classId;
}
