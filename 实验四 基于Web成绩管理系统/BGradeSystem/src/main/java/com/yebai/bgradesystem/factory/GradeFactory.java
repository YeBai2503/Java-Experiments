package com.yebai.bgradesystem.factory;

import com.yebai.bgradesystem.pojo.Grade;

public class GradeFactory implements Factory{
    @Override
    public Grade produce(int studentId, Object... args) {
        Grade grade = new Grade();
        grade.setStudentId(studentId);
        grade.setClassroomId((int) args[0]);
        grade.setCourseId((int) args[1]);
        grade.setCredit((int) args[2]);
        grade.setTerm((int) args[3]);
        return grade;
    }
    public Grade produce2(int studentId, Object... args) {
        Grade grade = new Grade();
        grade.setStudentId(studentId);
        grade.setClassroomId((int) args[0]);
        grade.setCourseId((int) args[1]);
        grade.setCredit((int) args[2]);
        grade.setTerm((int) args[3]);
        grade.setUsualScore((int) args[4]);
        grade.setMiddleScore((int) args[5]);
        grade.setExperimentScore((int) args[6]);
        grade.setEndScore((int) args[7]);
        grade.autoSumScore();
        return grade;
    }
}
