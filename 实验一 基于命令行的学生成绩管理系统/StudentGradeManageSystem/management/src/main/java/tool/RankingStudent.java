package tool;

import entity.Student;
import service.Manage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//按各科成绩、总成绩来排序
public class RankingStudent {
    public ArrayList<Integer> ranking(final Manage manager, ArrayList<Integer>studentIds, int strategy, final int courseId) {
        final ArrayList<Student> students = manager.getStudents();
        ArrayList<Integer> rank = new ArrayList<Integer>(studentIds);
        switch (strategy){
            case 1://总成绩排序
                //用lambda表达式进行排序（当前java.8.102版本不能使用lambda表达式，因此注释并使用其他方法）
                //studentIds.sort(Comparator.comparingDouble(id -> students.get(IdSearchIndex.searchIndex(students,id)).getGPA()));

                // 使用Collections.sort()和自定义Comparator
                Collections.sort(rank, new Comparator<Integer>() {
                    public int compare(Integer id1, Integer id2) {
                        double gpa1 = students.get(IdSearchIndex.searchIndex(students,id1)).getGPA();
                        double gpa2 = students.get(IdSearchIndex.searchIndex(students,id2)).getGPA();
                        return Double.compare(gpa2, gpa1);
                    }
                });
                break;
            case 2://各科绩排序
                // 使用Collections.sort()和自定义Comparator
                Collections.sort(rank, new Comparator<Integer>() {
                    public int compare(Integer id1, Integer id2) {
                        // 比较根据指定课程的成绩
                        double gpa1 = SearchCourseGrade.search(manager,id1,courseId);
                        double gpa2 = SearchCourseGrade.search(manager,id2,courseId);
                        return Double.compare(gpa2, gpa1);
                    }
                });
                break;

            default:
                break;
        }
        return rank;
    }

    //找出当前所选课程在该学生的成绩数组中的索引
    public static int searchGradeIndex(int courseId,Student student,Manage manager)
    {
        for (int i = 0; i < student.getGrades().size(); i++) {
            //成绩所属班级的索引
            int indexR=IdSearchIndex.searchIndex(manager.getClassrooms(),student.getGrades().get(i).getClassroomID());
            if(manager.getClassrooms().get(indexR).getCourseID()==courseId)return i;
        }
        return 0;
    }
}
