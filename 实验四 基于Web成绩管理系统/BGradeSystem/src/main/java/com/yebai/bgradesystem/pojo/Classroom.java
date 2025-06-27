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
@TableName(value="classroom")
public class Classroom {
    @TableId(value="id" ,type= IdType.NONE)
    private int id;
    @TableField(value="name")
    private String name;
    @TableField(value="term")
    private int term;
    @TableField(value="courseId")
    private int courseId;
    @TableField(value="teacherId")
    private int teacherId;
    @TableField(value="numStudent")
    private int numStudent;


    @TableField(exist = false)
    private String teacherName;
    @TableField(exist=false)
    private ArrayList<Integer> studentIDs=new ArrayList<Integer>();

    public void addStudentIDs(int studentID) {
        this.studentIDs.add(studentID);
    }
}
