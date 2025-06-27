package factory;

import entity.AbstractNameAndId;
import entity.Classroom;

public class ClassroomFactory implements Factory{
    public AbstractNameAndId produce(int id, String name, int one, int two, int three) {
        Classroom classroom =new Classroom();
        classroom.setId(id);
        classroom.setName(name);
        classroom.setTID(one);
        classroom.setTerm(two);
        classroom.setCourseID(three);
        return classroom;
    }
}
