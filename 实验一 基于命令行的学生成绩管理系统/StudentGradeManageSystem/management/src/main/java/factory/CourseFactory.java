package factory;

import entity.AbstractNameAndId;
import entity.Course;

public class CourseFactory implements Factory{
    public AbstractNameAndId produce(int id, String name, int one, int two, int three) {
        Course course = new Course();
        course.setId(id);
        course.setName(name);
        course.setDifficulty(one);
        course.setCredit(two);
        return course;
    }
}
