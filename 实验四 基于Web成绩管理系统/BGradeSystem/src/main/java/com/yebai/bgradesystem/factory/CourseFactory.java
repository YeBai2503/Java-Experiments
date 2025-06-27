package com.yebai.bgradesystem.factory;

import com.yebai.bgradesystem.pojo.Course;

public class CourseFactory implements Factory{
    @Override
    public Course produce(int id, Object... args) {
        Course course = new Course();
        course.setId(id);
        course.setName((String) args[0]);
        course.setDifficulty((int) args[1]);
        course.setCredit((int) args[2]);
        if (args.length > 3) course.setNumStudents((int) args[3]);
        return course;
    }
}
