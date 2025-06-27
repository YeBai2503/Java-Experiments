package command;

import service.Manage;
import tool.GenerateBarChart;
import tool.IdSearchIndex;
import tool.SearchCourseGrade;

import java.util.Arrays;
import java.util.List;

//统计分数段分布，并打印条形图
public class StatisticsCommand implements Command{
    public int execute(Manage manager,int one,int classroomId,int three,String word) {
        int score9=0,score8=0,score7=0,score6=0,score5=0,score4=0,score321=0;//分数段人数统计
        List<String> labels = Arrays.asList("100~90", "90~80", "80~70", "70~60", "60~50","50~40","40~0","44");
        switch (one){
            case 1://全部学生的总成绩和各科成绩统计
                for (int i = 0; i < manager.getStudents().size(); i++) {
                    int grade=manager.getStudents().get(i).getGPA();
                    if(grade>=90)score9++;
                    else if (grade>=80) {
                        score8++;
                    } else if (grade>=70) {
                        score7++;
                    } else if (grade>=60) {
                        score6++;
                    }else if (grade>=50) {
                        score5++;
                    }else if (grade>=40) {
                        score4++;
                    }else{
                        score321++;
                    }
                }
                //全体学生总成绩的分数段统计
                List<Integer> GPAs= Arrays.asList(score9,score8,score7,score6,score5,score4,score321);
                System.out.println("\n++++++++<全体学生总成绩的分数段统计>++++++++\n");
                GenerateBarChart.printBarChart(labels,GPAs);

                //全体学生各科成绩的分数段统计
                for(int i=0;i<manager.getCourses().size();i++)
                {
                    score9=0;score8=0;score7=0;score6=0;score5=0;score4=0;score321=0;//归零计数
                    for (int j = 0; j < manager.getStudents().size(); j++) {
                        int grade= SearchCourseGrade.search(manager,manager.getStudents().get(j).getId(),manager.getCourses().get(i).getId());
                        if(grade>=90)score9++;
                        else if (grade>=80) {
                            score8++;
                        } else if (grade>=70) {
                            score7++;
                        } else if (grade>=60) {
                            score6++;
                        }else if (grade>=50) {
                            score5++;
                        }else if (grade>=40) {
                            score4++;
                        }else{
                            score321++;
                        }
                    }
                    //全体学生各科成绩的分数段统计
                    List<Integer> Grades= Arrays.asList(score9,score8,score7,score6,score5,score4,score321);
                    System.out.println("\n++++++++<全体学生《"+manager.getCourses().get(i).getName()+"》的分数段统计>++++++++\n");
                    GenerateBarChart.printBarChart(labels,Grades);
                }
                break;
            case 2://某班级的统计
                int indexR= IdSearchIndex.searchIndex(manager.getClassrooms(),classroomId);
                score9=0;score8=0;score7=0;score6=0;score5=0;score4=0;score321=0;//归零计数
                for (int j = 0; j < manager.getStudents().size(); j++) {
                    int grade= SearchCourseGrade.search(manager,manager.getStudents().get(j).getId(),manager.getClassrooms().get(indexR).getCourseID());
                    if(grade>=90)score9++;
                    else if (grade>=80) {
                        score8++;
                    } else if (grade>=70) {
                        score7++;
                    } else if (grade>=60) {
                        score6++;
                    }else if (grade>=50) {
                        score5++;
                    }else if (grade>=40) {
                        score4++;
                    }else{
                        score321++;
                    }
                }
                //全体学生各科成绩的分数段统计
                List<Integer> Grades= Arrays.asList(score9,score8,score7,score6,score5,score4,score321);
                int indexC=IdSearchIndex.searchIndex(manager.getCourses(),manager.getClassrooms().get(indexR).getCourseID());
                System.out.println("\n++++++++<"+manager.getClassrooms().get(indexR).getId()+"班《"+manager.getCourses().get(indexC).getName()+"》的分数段统计>++++++++\n");
                GenerateBarChart.printBarChart(labels,Grades);
                break;
            default:
                break;
        }
        return 0;
    }
}
