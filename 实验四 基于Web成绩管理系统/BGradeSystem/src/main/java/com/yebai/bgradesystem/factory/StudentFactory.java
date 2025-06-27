package com.yebai.bgradesystem.factory;

import com.yebai.bgradesystem.pojo.Student;

public class StudentFactory implements Factory{
    @Override
    public Student produce(int id, Object... args) {
        Student student = new Student();
        student.setId(id);
        student.setName((String) args[0]);
        student.setPassword((String) args[1]);
        student.setSex((String) args[2]);
        if(args.length > 3)student.setLevel((int) args[3]);
        return student;
    }
}
