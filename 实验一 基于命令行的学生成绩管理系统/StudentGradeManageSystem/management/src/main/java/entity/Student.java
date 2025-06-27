package entity;

import java.util.ArrayList;

public class Student extends People {
    private ArrayList<Grade> grades =new ArrayList<Grade>();//成绩(包含教学班)
    private ArrayList<Integer> courseIDs =new ArrayList<Integer>();//选的课程
    private int level;//优秀等级（五分制）
    private int GPA;//百分制绩点

//    public Student(int id,String name,int sex,int level)//构造函数
//    {
//        this.setId(id);
//        this.setName(name);
//        this.setSex(sex);
//        this.setLevel(level);
//    }

    //自动计算绩点
    public void autoGPA() {
        int GPA=0;
        int sumCredit=0;
        for (int i = 0; i < grades.size(); i++) {
            sumCredit+=grades.get(i).getCredit();
            GPA+=grades.get(i).getCredit()*grades.get(i).getSumScore();
        }
        this.GPA = GPA/sumCredit;
    }

    public int getLevel() {
        return level;
    }

    public void addGrades(int classroomID,int credit,int term) {
        Grade grade = new Grade(classroomID,credit,term);
        this.grades.add(grade);
    }

    public void addCourseIDs(int courseID) {
        this.courseIDs.add(courseID);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public ArrayList<Integer> getCourseIDs() {
        return courseIDs;
    }

    public int getGPA() {
        return GPA;
    }

    @Override
    public void show()
    {
        System.out.println("\n学生信息： 学号:"+getId()+"  姓名:"+getName()+"  性别:"+getSex()+"  优秀等级:"+getLevel()+"  总成绩:"+getGPA());
    }


    //成绩内部类
    public class Grade {
        private int classroomID;
        private int credit;//学分
        private int term;//获得成绩的时间（学期）
        private int usualScore,middleScore,experimentScore,endScore,sumScore;//平时成绩，期中成绩，实验成绩，期末成绩，综合成绩

        public Grade(int classroomID,int credit,int term)
        {
            this.classroomID=classroomID;
            this.credit=credit;
            this.term=term;
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }

        public void setTerm(int term) {
            this.term = term;
        }

        public void setClassroomID(int classroomID) {
            this.classroomID = classroomID;
        }

        public void setUsualScore(int usualScore) {
            this.usualScore = usualScore;
        }

        public void setMiddleScore(int middleScore) {
            this.middleScore = middleScore;
        }

        public void setExperimentScore(int experimentScore) {
            this.experimentScore = experimentScore;
        }

        public void setEndScore(int endScore) {
            this.endScore = endScore;
        }

        public void setSumScore(int sumScore) {
            this.sumScore = sumScore;
        }

        public void autoSumScore() {//默认以平时成绩占10%，期中和实验分别占50%，期末占50%的比例来获取综合成绩
            this.sumScore = (int)Math.round(usualScore*0.1+middleScore*0.2+experimentScore*0.2+endScore*0.5);
        }

        public int getTerm() {
            return term;
        }

        public int getClassroomID() {
            return classroomID;
        }

        public int getUsualScore() {
            return usualScore;
        }

        public int getMiddleScore() {
            return middleScore;
        }

        public int getExperimentScore() {
            return experimentScore;
        }

        public int getEndScore() {
            return endScore;
        }

        public int getSumScore() {
            return sumScore;
        }

        public int getCredit() {
            return credit;
        }
    }


}
