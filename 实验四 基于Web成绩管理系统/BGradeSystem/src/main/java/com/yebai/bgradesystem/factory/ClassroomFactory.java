package com.yebai.bgradesystem.factory;

import com.yebai.bgradesystem.pojo.Classroom;

public class ClassroomFactory implements Factory{
    @Override
    public Classroom produce(int id, Object... args) {
        Classroom classroom = new Classroom();
        classroom.setId(id);
        classroom.setName((String) args[0]);
        classroom.setTerm((int) args[1]);
        classroom.setCourseId((int) args[2]);
        classroom.setTeacherId((int) args[3]);
        if (args.length > 4) classroom.setNumStudent((int) args[4]);
        return classroom;
    }
}
