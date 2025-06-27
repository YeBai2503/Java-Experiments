package factory;

import entity.AbstractNameAndId;
import entity.Student;

public class StudentFactory implements Factory{
    public AbstractNameAndId produce(int id, String name, int one, int two, int three) {
        Student student=new Student();
        student.setId(id);
        student.setName(name);
        student.setSex(one);
        student.setLevel(two);
        return student;
    }
}
