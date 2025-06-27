package entity;

import java.util.ArrayList;

//教学班
public class Classroom extends AbstractNameAndId {
    private int TID;//教师id
    private int numStudent=0;//总学生人数
    private int term;//开课学期
    private int courseID;//课程编号
    private ArrayList<Integer>studentIDs=new ArrayList<Integer>();

    public ArrayList<Integer> getStudentIDs() {
        return studentIDs;
    }

    public void addStudentIDs(int SID) {
        this.studentIDs.add(SID);
    }

    public void setTID(int TID) {
        this.TID = TID;
    }

    public void setNumStudent(int numStudent) {
        this.numStudent = numStudent;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getTID() {
        return TID;
    }

    public int getNumStudent() {
        return numStudent;
    }

    public int getTerm() {
        return term;
    }

    public int getCourseID() {
        return courseID;
    }
}
