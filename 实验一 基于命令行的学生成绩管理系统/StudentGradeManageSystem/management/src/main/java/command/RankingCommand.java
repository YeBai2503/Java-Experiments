package command;

import service.Manage;
import tool.IdSearchIndex;
import tool.PrintTable;
import tool.RankingStudent;
import tool.SearchCourseGrade;

import java.util.ArrayList;
import java.util.List;

//学生总排名（输出id，名字，性别，等级，科目成绩（没选的科目显示-1），总成绩）
public class RankingCommand implements Command{
    public int execute(Manage manager,int one,int courseId,int three,String word) {
        PrintTable table = PrintTable.create();
        table.setSbcMode(true);// 设置为全角模式
        // 添加表头
        List<String> headers = new ArrayList<String>();
        headers.add("排序编号");
        headers.add("学号");
        headers.add("姓名");
        for (int i = 0; i < manager.getCourses().size(); i++)
        {
            headers.add(manager.getCourses().get(i).getName());
        }
        headers.add("总成绩");
        // 使用 toArray() 方法将 List 转换为 String 数组
        String[] headerArray = new String[headers.size()];
        headers.toArray(headerArray);  // 将 List 转换为数组
        table.addHeader(headerArray);
        //获取排好序的学号数组
        ArrayList<Integer>rankingStudentIds;
        ArrayList<Integer>studentIds=new ArrayList<Integer>();
        RankingStudent rankingStudent=new RankingStudent();
        //原始按学号排好序的数组
        for (int i = 0; i < manager.getStudents().size(); i++) {
            studentIds.add(manager.getStudents().get(i).getId());
        }
        switch (one){
            case 1://学号
                rankingStudentIds=studentIds;
                break;
            case 2://各科成绩
                rankingStudentIds=rankingStudent.ranking(manager,studentIds,2,courseId);
                break;
            case 3://总成绩
                rankingStudentIds=rankingStudent.ranking(manager,studentIds,1,courseId);
                break;
            default:
                rankingStudentIds=studentIds;
                break;
        }
        //添加表体
        for (int i = 0; i < rankingStudentIds.size(); i++) {
            ArrayList<Integer>courseGrades=new ArrayList<Integer>();
            int id,gpa;
            int indexS= IdSearchIndex.searchIndex(manager.getStudents(),rankingStudentIds.get(i));
            List<String> bodys = new ArrayList<String>();
            id=manager.getStudents().get(indexS).getId();
            gpa=manager.getStudents().get(indexS).getGPA();
            //表体行元素
            bodys.add(Integer.toString(i+1));
            bodys.add(Integer.toString(id));
            bodys.add(manager.getStudents().get(indexS).getName());
            //各科成绩
            for (int j = 0; j < manager.getCourses().size(); j++) {
                int grade= SearchCourseGrade.search(manager,rankingStudentIds.get(i),manager.getCourses().get(j).getId());
                if(grade==-1)bodys.add("未修");
                else bodys.add(Integer.toString(grade));
            }
            bodys.add(Integer.toString(gpa));
            // 使用 toArray() 方法将 List 转换为 String 数组
            String[] bodyArray = new String[bodys.size()];
            bodys.toArray(bodyArray);  // 将 List 转换为数组
            table.addBody(bodyArray);
        }


        table.print(); // 打印表格
        return 0;
    }
}
