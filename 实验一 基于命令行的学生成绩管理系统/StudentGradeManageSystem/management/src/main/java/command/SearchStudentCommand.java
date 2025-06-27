package command;

import service.Manage;
import tool.IdSearchIndex;
import tool.PrintTable;

//查询指定学生所有成绩
public class SearchStudentCommand implements Command{
    public int execute(Manage manager,int one,int id,int three,String name) {
        int live=0;//是否存在该学生
        PrintTable table = PrintTable.create();
        table.setSbcMode(true);// 设置为全角模式
        table.addHeader("排序编号", "课程名","平时成绩","期中成绩","实验成绩","期末成绩","综合成绩","学分","获取时间（学期）"); // 添加表头
        int usual,middle,experiment,end,sum,credit,term;
        String nameC;
        switch (one){
            case 1://按学号
                for(int i=0;i<manager.getStudents().size();i++)
                {
                    if(manager.getStudents().get(i).getId()==id)//找到学生
                    {
                        live=1;
                        manager.getStudents().get(i).show();//打印学生基本信息
                        //添加成绩到表格
                        for (int j = 0; j < manager.getStudents().get(i).getGrades().size(); j++)
                        {
                            int indexR= IdSearchIndex.searchIndex(manager.getClassrooms(),manager.getStudents().get(i).getGrades().get(j).getClassroomID());
                            int indexC=IdSearchIndex.searchIndex(manager.getCourses(),manager.getClassrooms().get(indexR).getCourseID());
                            nameC=manager.getCourses().get(indexC).getName();
                            usual=manager.getStudents().get(i).getGrades().get(j).getUsualScore();
                            middle=manager.getStudents().get(i).getGrades().get(j).getMiddleScore();
                            experiment=manager.getStudents().get(i).getGrades().get(j).getExperimentScore();
                            end=manager.getStudents().get(i).getGrades().get(j).getEndScore();
                            sum=manager.getStudents().get(i).getGrades().get(j).getSumScore();
                            credit=manager.getCourses().get(indexC).getCredit();
                            term=manager.getStudents().get(i).getGrades().get(j).getTerm();
                            //添加行
                            table.addBody(Integer.toString(j+1),nameC,Integer.toString(usual),Integer.toString(middle),Integer.toString(experiment),Integer.toString(end),Integer.toString(sum),Integer.toString(credit),Integer.toString(term)); // 添加行
                        }
                    }
                }
                break;
            case 2://按名字
                for(int i=0;i<manager.getStudents().size();i++)
                {
                    if(manager.getStudents().get(i).getName().equals(name))//找到学生
                    {
                        live=1;
                        manager.getStudents().get(i).show();//打印学生基本信息
                        //添加成绩到表格
                        for (int j = 0; j < manager.getStudents().get(i).getGrades().size(); j++)
                        {
                            int indexR= IdSearchIndex.searchIndex(manager.getClassrooms(),manager.getStudents().get(i).getGrades().get(j).getClassroomID());
                            int indexC=IdSearchIndex.searchIndex(manager.getCourses(),manager.getClassrooms().get(indexR).getCourseID());
                            nameC=manager.getCourses().get(indexC).getName();
                            usual=manager.getStudents().get(i).getGrades().get(j).getUsualScore();
                            middle=manager.getStudents().get(i).getGrades().get(j).getMiddleScore();
                            experiment=manager.getStudents().get(i).getGrades().get(j).getExperimentScore();
                            end=manager.getStudents().get(i).getGrades().get(j).getEndScore();
                            sum=manager.getStudents().get(i).getGrades().get(j).getSumScore();
                            credit=manager.getCourses().get(indexC).getCredit();
                            term=manager.getStudents().get(i).getGrades().get(j).getTerm();
                            //添加行
                            table.addBody(Integer.toString(j+1),nameC,Integer.toString(usual),Integer.toString(middle),Integer.toString(experiment),Integer.toString(end),Integer.toString(sum),Integer.toString(credit),Integer.toString(term)); // 添加行
                        }
                    }
                }
                break;

            default:
                break;
        }
        if(live==1)table.print(); // 打印表格
        return live;
    }
}
