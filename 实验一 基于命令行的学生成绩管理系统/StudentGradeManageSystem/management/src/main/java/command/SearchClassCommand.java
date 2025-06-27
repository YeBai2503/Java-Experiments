package command;

import entity.Student;
import service.Manage;
import tool.IdSearchIndex;
import tool.PrintTable;
import tool.RankingStudent;

import java.util.ArrayList;

//以班级为单位打印信息(可选以学号排序还是成绩排序)
public class SearchClassCommand implements Command{

    public int execute(Manage manager,int one,int classroomId,int three,String word) {
        PrintTable table = PrintTable.create();
        table.setSbcMode(true);// 设置为全角模式
        table.addHeader("排序编号","学号", "姓名","优秀等级" ,"平时成绩","期中成绩","实验成绩","期末成绩","综合成绩","总成绩"); // 添加表头
        int id,level,usualScore,middleScore,experimentScore,endScore,sumScore,gpa,indexG;
        int indexR= IdSearchIndex.searchIndex(manager.getClassrooms(),classroomId);//索引班级
        int indexT=IdSearchIndex.searchIndex(manager.getTeachers(),manager.getClassrooms().get(indexR).getTID());
        System.out.println("\n++++《"+manager.getClassrooms().get(indexR).getName()+"》课程班++++");
        manager.getTeachers().get(indexT).show();//打印老师基本信息
        //int indexC=IdSearchIndex.searchIndex(manager.getCourses(),manager.getClassrooms().get(indexR).getCourseID());
        switch (one){
            case 1://学号排序
                for (int i = 0; i < manager.getClassrooms().get(indexR).getNumStudent(); i++)
                {
                    //索引学生并记录信息
                    int indexS=IdSearchIndex.searchIndex(manager.getStudents(),manager.getClassrooms().get(indexR).getStudentIDs().get(i));
                    id=manager.getStudents().get(indexS).getId();
                    level=manager.getStudents().get(indexS).getLevel();
                    indexG=searchGradeIndex(classroomId,manager.getStudents().get(indexS),manager);
                    usualScore=manager.getStudents().get(indexS).getGrades().get(indexG).getUsualScore();
                    middleScore=manager.getStudents().get(indexS).getGrades().get(indexG).getMiddleScore();
                    experimentScore=manager.getStudents().get(indexS).getGrades().get(indexG).getExperimentScore();
                    endScore=manager.getStudents().get(indexS).getGrades().get(indexG).getEndScore();
                    sumScore=manager.getStudents().get(indexS).getGrades().get(indexG).getSumScore();
                    gpa=manager.getStudents().get(indexS).getGPA();
                    // 添加表格行
                    table.addBody(Integer.toString(i+1),Integer.toString(id),manager.getStudents().get(indexS).getName(),Integer.toString(level),Integer.toString(usualScore),Integer.toString(middleScore),Integer.toString(experimentScore),Integer.toString(endScore),Integer.toString(sumScore),Integer.toString(gpa));
                }
                table.print(); // 打印表格
                break;
            case 2://成绩排序
                RankingStudent rankingStudent=new RankingStudent();
                ArrayList<Integer> rank=rankingStudent.ranking(manager,manager.getClassrooms().get(indexR).getStudentIDs(),2,manager.getClassrooms().get(indexR).getCourseID());
                for (int i = 0; i < rank.size(); i++)
                {
                    //索引学生并记录信息
                    int indexS=IdSearchIndex.searchIndex(manager.getStudents(),rank.get(i));
                    id=manager.getStudents().get(indexS).getId();
                    level=manager.getStudents().get(indexS).getLevel();
                    indexG=searchGradeIndex(classroomId,manager.getStudents().get(indexS),manager);
                    usualScore=manager.getStudents().get(indexS).getGrades().get(indexG).getUsualScore();
                    middleScore=manager.getStudents().get(indexS).getGrades().get(indexG).getMiddleScore();
                    experimentScore=manager.getStudents().get(indexS).getGrades().get(indexG).getExperimentScore();
                    endScore=manager.getStudents().get(indexS).getGrades().get(indexG).getEndScore();
                    sumScore=manager.getStudents().get(indexS).getGrades().get(indexG).getSumScore();
                    gpa=manager.getStudents().get(indexS).getGPA();
                    // 添加表格行
                    table.addBody(Integer.toString(i+1),Integer.toString(id),manager.getStudents().get(indexS).getName(),Integer.toString(level),Integer.toString(usualScore),Integer.toString(middleScore),Integer.toString(experimentScore),Integer.toString(endScore),Integer.toString(sumScore),Integer.toString(gpa));
                }
                table.print(); // 打印表格
                break;
            default:
                break;
        }
        return 0;
    }
    //找出学生当前所选课程的成绩索引
    public static int searchGradeIndex(int classroomId, Student student, Manage manager)
    {
        int index=0;
        for (int i = 0; i < student.getGrades().size(); i++) {
            if(student.getGrades().get(i).getClassroomID()==classroomId)
            {
                return i;
            }
        }
        return index;
    }
}
