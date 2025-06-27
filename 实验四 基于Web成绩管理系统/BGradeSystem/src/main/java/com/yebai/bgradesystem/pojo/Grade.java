package com.yebai.bgradesystem.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value="grade")
public class Grade {
    @TableField(value="studentId")
    private int studentId;
    @TableField(value="classroomId")
    private int classroomId;
    @TableField(value="courseId")
    private int courseId;
    @TableField(value="credit")
    private int credit;
    @TableField(value="term")
    private int term;
    @TableField(value="usualScore")
    private int usualScore;
    @TableField(value="middleScore")
    private int middleScore;
    @TableField(value="experimentScore")
    private int experimentScore;
    @TableField(value="endScore")
    private int endScore;
    @TableField(value="sumScore")
    private double sumScore;

    @TableField(exist=false)
    private String courseName;

    public void autoSumScore() {//默认以平时成绩占10%，期中和实验分别占50%，期末占50%的比例来获取综合成绩
        this.sumScore = (int)Math.round(usualScore*0.1+middleScore*0.2+experimentScore*0.2+endScore*0.5);
    }
}
