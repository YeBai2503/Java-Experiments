package tool;

import service.Manage;

//返回搜索的学生对应课程的综合成绩
public class SearchCourseGrade {
    public static int search(Manage manager,int studentId,int courseId)
    {
        int grade=-1;//没找到返回-1
        int indexS=IdSearchIndex.searchIndex(manager.getStudents(),studentId);
        for(int i=0;i<manager.getStudents().get(indexS).getGrades().size();i++)
        {
            int classroomId=manager.getStudents().get(indexS).getGrades().get(i).getClassroomID();
            int indexR=IdSearchIndex.searchIndex(manager.getClassrooms(),classroomId);
            if(courseId==manager.getClassrooms().get(indexR).getCourseID())
            {
                grade=manager.getStudents().get(indexS).getGrades().get(i).getSumScore();
                break;
            }
        }
        return grade;
    }
}
