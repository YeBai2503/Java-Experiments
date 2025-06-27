package command;

import entity.Classroom;
import entity.Course;
import entity.Student;
import entity.Teacher;
import service.Manage;
import tool.IdSearchIndex;
import tool.PrintTable;

//以类为单位打印基本信息
public class SearchBaseCommand implements Command{
    public int execute(Manage manager,int one,int two,int three,String word) {
        PrintTable table = PrintTable.create();
        table.setSbcMode(true);// 设置为全角模式
        int id;
        char sex;
        switch (one){
            case 1://学生信息
                table.addHeader("排序编号","学号", "姓名","性别","优秀等级" ,"总成绩"); // 添加表头
                int level,gpa;
                for (int i = 0; i < manager.getStudents().size(); i++)
                {
                    Student student=manager.getStudents().get(i);
                    id=student.getId();
                    sex=student.getSex();
                    level=student.getLevel();
                    gpa=student.getGPA();
                    // 添加表格行
                    table.addBody(Integer.toString(i+1),Integer.toString(id),student.getName(),Character.toString(sex),Integer.toString(level),Integer.toString(gpa));
                }
                table.print(); // 打印表格
                break;
            case 2://老师信息
                table.addHeader("排序编号","教师编号", "姓名","性别","授课班级编号"); // 添加表头
                int classId;
                for (int i = 0; i < manager.getTeachers().size(); i++)
                {
                    Teacher t=manager.getTeachers().get(i);
                    id=t.getId();
                    sex=t.getSex();
                    classId=t.getClassID();
                    // 添加表格行
                    table.addBody(Integer.toString(i+1),Integer.toString(id),t.getName(),Character.toString(sex),Integer.toString(classId));
                }
                table.print(); // 打印表格
                break;
            case 3://课程信息
                table.addHeader("排序编号","课程编号", "课程名","难度" ,"学分","选课人数"); // 添加表头
                int difficulty,credit,numSC;
                for (int i = 0; i < manager.getCourses().size(); i++)
                {
                    Course c=manager.getCourses().get(i);
                    id=c.getId();
                    difficulty=c.getDifficulty();
                    credit=c.getCredit();
                    numSC=c.getNumStudents();
                    // 添加表格行
                    table.addBody(Integer.toString(i+1),Integer.toString(id),c.getName(),Integer.toString(difficulty),Integer.toString(credit),Integer.toString(numSC));
                }
                table.print(); // 打印表格
                break;
            case 4://教学班信息
                table.addHeader("排序编号","班级编号", "课程名","授课老师" ,"授课老师编号","开课学期","人数"); // 添加表头
                int TID,term,numSR;
                String tName;
                for (int i = 0; i < manager.getClassrooms().size(); i++)
                {
                    Classroom room=manager.getClassrooms().get(i);
                    id=room.getId();
                    TID=room.getTID();
                    tName=manager.getTeachers().get(IdSearchIndex.searchIndex(manager.getTeachers(),TID)).getName();
                    term=room.getTerm();
                    numSR=room.getNumStudent();
                    // 添加表格行
                    table.addBody(Integer.toString(i+1),Integer.toString(id),room.getName(),tName,Integer.toString(TID),Integer.toString(term),Integer.toString(numSR));
                }
                table.print(); // 打印表格
                break;
            default:
                break;
        }
        return 0;
    }
}
