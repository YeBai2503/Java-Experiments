package entity;

public class People extends AbstractNameAndId {
    private char sex;

    public char getSex() {
        return sex;
    }

    public void show()
    {
        System.out.println(getId()+" "+getName()+" "+getSex());
    }

    public void setSex(int sex) {
        if(sex==1)this.sex = '男';
        else if(sex==0)this.sex = '女';
        else this.sex = '男';
    }
}
