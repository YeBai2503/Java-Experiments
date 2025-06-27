package entity;

public class Teacher extends People {
    private int classID;//教课班的id

//    public Teacher(int id, String name, int sex)//构造函数
//    {
//        this.setId(id);
//        this.setName(name);
//        this.setSex(sex);
//    }

    @Override
    public void show()
    {
        System.out.println("\n老师信息： 老师编号:"+getId()+"  姓名:"+getName()+"  性别:"+getSex()+"  教学班号:"+getClassID());
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public int getClassID() {
        return classID;
    }
}
