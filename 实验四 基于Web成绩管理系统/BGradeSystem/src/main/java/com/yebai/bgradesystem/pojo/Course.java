package com.yebai.bgradesystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value="course")
public class Course {
    @TableId(value="id" ,type= IdType.NONE)
    private int id;
    @TableField(value="name")
    private String name;
    @TableField(value="numStudents")
    private int numStudents;
    @TableField(value="difficulty")
    private int difficulty;
    @TableField(value="credit")
    private int credit;

    @TableField(exist = false)
    private ArrayList<Integer> classroomsIDs =new ArrayList<Integer>();

    public void addClassroomsIDs(int classroomID) {
        classroomsIDs.add(classroomID);
    }
}
