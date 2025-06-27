package command;

import service.Manage;

public class GenerateCommand implements Command{
    public int execute(Manage manager,int one,int two,int three,String word) {
        manager.dataGenerate();
        System.out.println("学生、老师、课程、教学班等基本信息的生成已完成！");
        manager.classSelect();
        System.out.println("学生选课已完成！");
        manager.gradeGet();
        System.out.println("学生成绩的生成已完成！");
        return 0;
    }
}
