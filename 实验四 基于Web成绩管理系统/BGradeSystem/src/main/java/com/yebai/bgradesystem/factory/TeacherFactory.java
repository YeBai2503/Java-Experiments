package com.yebai.bgradesystem.factory;

import com.yebai.bgradesystem.pojo.Teacher;

public class TeacherFactory implements Factory {
    @Override
    public Teacher produce(int id, Object... args) {
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setName((String) args[0]);
        teacher.setPassword((String) args[1]);
        teacher.setSex((String) args[2]);
        if(args.length > 3)teacher.setClassId((int) args[3]);
        return teacher;
    }
}
