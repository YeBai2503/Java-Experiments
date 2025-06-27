package command;

import service.Manage;

//验证老师身份
public class CheckCommand implements Command{
    //从左到右传入管理器，教师ID（其他为无关参数）
    public int execute(Manage manager,int TID,int two,int three,String word) {
        for (int i = 0; i < manager.getTeachers().size(); i++) {
            if(TID==manager.getTeachers().get(i).getId())return 1;
        }
        return 0;
    }
}
