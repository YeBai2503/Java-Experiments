package command;

import entity.Student;
import service.Manage;
import tool.IdSearchIndex;

//老师修改指定学生的成绩
public class ChangeGradeCommand implements Command{
    public int execute(Manage manager,int SID,int Score,int classroomId,String gradeSelect) {
        int select=Integer.parseInt(gradeSelect);//选择的成绩类型
        int indexS= IdSearchIndex.searchIndex(manager.getStudents(),SID);
        int indexG=searchGradeIndex(classroomId,manager.getStudents().get(indexS),manager);
        switch (select){
            case 1://平时成绩
                manager.getStudents().get(indexS).getGrades().get(indexG).setUsualScore(Score);
                break;
            case 2://期中成绩
                manager.getStudents().get(indexS).getGrades().get(indexG).setMiddleScore(Score);
                break;
            case 3://实验成绩
                manager.getStudents().get(indexS).getGrades().get(indexG).setExperimentScore(Score);
                break;
            case 4://期末成绩
                manager.getStudents().get(indexS).getGrades().get(indexG).setEndScore(Score);
                break;
            default:
                break;
        }
        //自动更新综合成绩
        manager.getStudents().get(indexS).getGrades().get(indexG).autoSumScore();
        //自动更新总成绩
        manager.getStudents().get(indexS).autoGPA();
        return 0;
    }
    //找出当前所选课程在该学生的成绩数组中的索引
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
