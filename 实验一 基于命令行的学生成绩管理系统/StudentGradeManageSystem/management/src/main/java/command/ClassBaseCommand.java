package command;

import service.Manage;
import tool.PrintTable;

//用教师ID查某班级基本信息
public class ClassBaseCommand implements Command{
    public int execute(Manage manager,int TID,int two,int three,String word) {
        int classroomId=0;
        for (int i = 0; i < manager.getClassrooms().size(); i++) {
            if(manager.getClassrooms().get(i).getTID()==TID)
            {
                classroomId=manager.getClassrooms().get(i).getId();
                //打印教学班信息
                PrintTable table = PrintTable.create();
                table.setSbcMode(true);// 设置为全角模式
                table.addHeader("班级编号", "课程名称", "开课学期","班级总人数"); // 添加表头
                table.addBody(Integer.toString(classroomId), manager.getClassrooms().get(i).getName(), Integer.toString( manager.getClassrooms().get(i).getTerm()),Integer.toString( manager.getClassrooms().get(i).getNumStudent())); // 添加第一行
                table.print(); // 打印表格
                break;
            }
        }
        return classroomId;
    }
}
