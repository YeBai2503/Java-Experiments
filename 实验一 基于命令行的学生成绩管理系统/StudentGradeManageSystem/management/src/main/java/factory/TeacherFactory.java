package factory;

import entity.AbstractNameAndId;
import entity.Teacher;

public class TeacherFactory implements Factory{
    public AbstractNameAndId produce(int id, String name, int one, int two, int three) {
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setName(name);
        teacher.setSex(one);
        return teacher;
    }
}
