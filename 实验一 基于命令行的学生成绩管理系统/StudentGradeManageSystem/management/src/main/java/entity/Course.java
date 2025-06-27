package entity;

import java.util.ArrayList;

//课程
public class Course extends AbstractNameAndId {
    private ArrayList<Integer> classroomsIDs =new ArrayList<Integer>();
    private int numStudents;
    private int difficulty;//课程难度(五分制)
    private int credit;

//    public Course(int id,String name,int difficulty,int credit)
//    {
//        this.setId(id);
//        this.setName(name);
//        this.setDifficulty(difficulty);
//        this.credit=credit;
//    }

    public void show()
    {
        System.out.print(this.getName()+" 包含的教学班号有:");
        for (Integer classroomsID : classroomsIDs) {
            System.out.print(" " + classroomsID);
        }
        System.out.print("\n");
    }

    public int getCredit() {
        return credit;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void addClassroomsIDs(int id) {
        this.classroomsIDs.add(id);
    }

    public void setNumStudents(int numStudents) {
        this.numStudents = numStudents;
    }

    public int getNumStudents() {
        return numStudents;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public ArrayList<Integer> getClassroomsIDs() {
        return classroomsIDs;
    }
}
