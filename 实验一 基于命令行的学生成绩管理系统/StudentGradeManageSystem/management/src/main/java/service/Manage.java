package service;
import entity.*;
import factory.ClassroomFactory;
import factory.CourseFactory;
import factory.StudentFactory;
import factory.TeacherFactory;
import tool.GenerateGaussian;
import tool.IdSearchIndex;
import java.util.*;
import java.util.Random;

//数据管理器（管理系统类）
//单例模式（饿汉式）
//用于生成、保存及清除所有系统数据（实体类对象）
public class Manage implements Management {
    private static Manage manager=new Manage();
    private ArrayList<Student>students = new ArrayList<Student>();//学生数据集
    private ArrayList<Teacher>teachers = new ArrayList<Teacher>();//老师数据集
    private ArrayList<Classroom>classrooms = new ArrayList<Classroom>();//教学班数据集
    private ArrayList<Course>courses = new ArrayList<Course>();//课程数据集
    private int sumS,sumT,sumR,sumC;//学生、老师、教学班、课程的总数

    private Manage(){}
    public static Manage getInstance(){return manager;}


    //生成随机数据
    public void dataGenerate()
    {
        //生成各对象的数量和课程对象
        this.sumS = 120;
        this.sumT = 12;
        this.sumR = this.sumT;//教学班数量和老师数量相同
        this.sumC = 5;
        CourseFactory cF=new CourseFactory();
//        this.courses.add(new Course(1,"高等数学",5,3));
//        this.courses.add(new Course(2,"线性代数",4,2));
//        this.courses.add(new Course(3,"离散数学",3,2));
//        this.courses.add(new Course(4,"程序设计",4,2));
//        this.courses.add(new Course(5,"大学英语",2,1));
        this.courses.add((Course)cF.produce(1,"高等数学",5,3,0));
        this.courses.add((Course)cF.produce(2,"线性代数",4,2,0));
        this.courses.add((Course)cF.produce(3,"离散数学",3,2,0));
        this.courses.add((Course)cF.produce(4,"程序设计",4,2,0));
        this.courses.add((Course)cF.produce(5,"大学英语",2,1,0));

        //生成随机姓名用的姓和名(中文字符java不好对齐，所以统一三个字)
        String[] lastname={
                "王", "李", "张", "刘", "陈",
                "杨", "黄", "赵", "吴", "周",
                "徐", "孙", "马", "朱", "胡",
                "魏", "关", "江", "陆", "龙"
        };
        String[] firstname={//前十三个为男名，后12个为女名，设置性别时会用到
                "志伟", "磊峰", "文强", "洋扬", "翔越",
                "建军", "天辰", "超人", "平良", "健豪",
                "杰雄", "辉文", "俊豪",
                "小芳", "娜美", "静珠", "晓敏", "丽莎",
                "霞心", "梦婷", "莉莉", "梦泽", "程雯",
                "天舞", "嘉怡",
        };

        //生成随机学生
        Random ranName = new Random();
        HashSet<Integer> nameSet = new HashSet<Integer>();
        HashSet<Integer>idSet=new HashSet<Integer>();
        for(int i=0;i<this.sumS;i++)
        {
            String name;
            int id,sex,name1,name2,level;
            //生成名字
            do {
                name1=ranName.nextInt(lastname.length);//获取姓的数组的随机下标
                name2=ranName.nextInt(firstname.length);//获取名的数组的随机下标
            }while(nameSet.contains(name1*100+name2));//确保唯一的姓名组合
            nameSet.add(name1*100+name2);
            name=lastname[name1]+firstname[name2];
            //根据名来确定性别
            if(name2<(firstname.length/2+1))sex=1;
            else sex=0;
            //生成四位的随机学生id
//            Random ranId= new Random();
//            do {
//                id=1000+ranId.nextInt(9000);
//            }while(idSet.contains(id));//确保唯一的id
//            idSet.add(id);
            id=24001+i;
            //生成正态分布的优秀等级（五分制）
            level=(int)Math.round(GenerateGaussian.Gauss(3,2,1,5));
//            this.students.add(new Student(id,name,sex,level));
            StudentFactory sF=new StudentFactory();
            this.students.add((Student) sF.produce(id,name,sex,level,0));

//            students.get(i).show();
        }

        //生成老师和对应的教学班数据
        int sumClass=0;//记录当前已生成的教学班个数
        for(int i=0;i<this.sumT;i++)
        {
            //生成老师基本信息
            String name;
            int id,sex,name1,name2;
            do {
                name1=ranName.nextInt(lastname.length);//获取姓的数组的随机下标
                name2=ranName.nextInt(firstname.length);//获取名的数组的随机下标
            }while(nameSet.contains(name1*100+name2));//确保唯一的姓名组合
            nameSet.add(name1*100+name2);
            name=lastname[name1]+firstname[name2];
            if(name2<(firstname.length/2+1))sex=1;//根据名来确定性别
            else sex=0;
            //生成三位的随机老师id
//            Random ranId= new Random();
//            do {
//                id=100+ranId.nextInt(900);
//            }while(idSet.contains(id));//确保唯一的id
//            idSet.add(id);
            id=101+i;
//            this.teachers.add(new Teacher(id,name,sex));
            TeacherFactory tF=new TeacherFactory();
            this.teachers.add((Teacher) tF.produce(id,name,sex,0,0));


            //生成对应教学班
            int cSelect;//选择的课程在课程数组里的索引
            int RID,term;//教学班号，开课学期
            Random ranC=new Random();
            if(sumClass<2*sumC)cSelect=sumClass%sumC;//先满足至少的每个课程两个班
            else {//满足后随机选择课程建班
                cSelect=ranC.nextInt(sumC);
            }
            term=1+ranC.nextInt(4);//默认只有四个学期
            //生成2位的随机班号id
//            do {
//                RID=10+ranC.nextInt(90);
//            }while(idSet.contains(RID));//确保唯一的id
//            idSet.add(RID);
            RID=2401+i;
            teachers.get(i).setClassID(RID);
            courses.get(cSelect).addClassroomsIDs(RID);
//            classrooms.add(new Classroom(RID,courses.get(cSelect).getName(),id,term,cSelect));
            ClassroomFactory rF=new ClassroomFactory();
            classrooms.add((Classroom)rF.produce(RID,courses.get(cSelect).getName(),id,term,courses.get(cSelect).getId()));

            sumClass++;
//            System.out.println(classrooms.get(i).getId()+" "+classrooms.get(i).getName()+' '+classrooms.get(i).getTID()+' '+classrooms.get(i).getTerm()+' '+classrooms.get(i).getCourseID());
//            teachers.get(i).show();
        }

        //展示课程信息
//        for(int i=0;i<courses.size();i++)
//        {
//            courses.get(i).show();
//        }
//        System.out.println("学生、老师、课程，教学班等初始化数据的基本信息生成完成！");
    }

    //选课
    public void classSelect()
    {
        //先选课程，保证各个课程的人数没有太大差距。
        // 策略为先在未满最小人数的课程中随机选取，若数量不够，则在已满足的课程中再随机选
        int[] minNumC=new int[sumC];//课程最少人数
        int[] nowNumC=new int[sumC];//课程当前已有人数
        for(int k=0;k<sumC;k++)
        {
            minNumC[k]=courses.get(k).getClassroomsIDs().size()*20;
//            System.out.println(courses.get(k).getName()+" 课程最少人数为 "+minNumC[k]);
        }
        for(int i=0;i<sumS;i++)
        {
            Random ranNC=new Random();
            int numC=3+ranNC.nextInt(sumC-2);//要学习的课程数量
            ArrayList<Integer>ableSC=new ArrayList<Integer>();//可选的课程集合(保存的是courses数组的下标)
            for(int k=0;k<sumC;k++)
            {
              if(nowNumC[k]<minNumC[k])ableSC.add(k);
            }
            HashSet<Integer> haveCSet=new HashSet<Integer>();//已经选中的课，防止重复
            if(ableSC.size()>numC)//只需从可选课程里随机选择
            {
                Random ranCourse=new Random();
                for(int k=0;k<numC;k++)
                {
                    int sC=ranCourse.nextInt(ableSC.size());//随机的ableSC的索引
                    students.get(i).addCourseIDs(courses.get(ableSC.get(sC)).getId());
                    nowNumC[ableSC.get(sC)]++;
                    ableSC.remove(sC);
                }

            }
            else if(ableSC.size()==numC)//可选课刚好满足要学习的课程数量
            {
                for(int k=0;k<numC;k++)
                {
                    students.get(i).addCourseIDs(courses.get(ableSC.get(k)).getId());
                    nowNumC[ableSC.get(k)]++;
                }
            }
            else if (ableSC.size()<numC)//需从已满足最少人数的课程里再随机选择
            {
                ArrayList<Integer>ableSC2=new ArrayList<Integer>();//已满足的课程集合
                for(int k=0;k<sumC;k++)
                {
                    if(nowNumC[k]>=minNumC[k])ableSC2.add(k);
                }
                //先将未满足的都加入
                for(int k=0;k<ableSC.size();k++)
                {
                    students.get(i).addCourseIDs(courses.get(ableSC.get(k)).getId());
                    nowNumC[ableSC.get(k)]++;
                }
                //加入随机的已经满足的
                Random ranCourse2=new Random();
                for(int k=0;k<numC-ableSC.size();k++)
                {
                    int sC=ranCourse2.nextInt(ableSC2.size());//随机的ableSC的索引
                    students.get(i).addCourseIDs(courses.get(ableSC2.get(sC)).getId());
                    nowNumC[ableSC2.get(sC)]++;
                    ableSC2.remove(sC);
                }
            }
            else System.out.println("Error: 选课");

//            System.out.println(students.get(i).getCourseIDs());
        }
        for(int i=0;i<sumC;i++)//课程人数保存到课程类中
        {
            courses.get(i).setNumStudents(nowNumC[i]);
        }
//        for(int i=0;i<sumC;i++)
//        {
//            System.out.println(courses.get(i).getNumStudents()+" "+nowNumC[i]);
//        }

        //选完课程后，选具体的教学班
        // 策略与选课程类似，为先在未满最小人数的课程班级中随机选取，若数量不够，则在已满足的课程班级中再随机选
        int minNumR=20;//班级最少人数
        int[] nowNumR=new int[sumR];//班级当前已有人数
        for(int i=0;i<sumS;i++)
        {
            for(int nowC=0;nowC<students.get(i).getCourseIDs().size();nowC++)
            {
                int indexC=IdSearchIndex.searchIndex(courses,students.get(i).getCourseIDs().get(nowC));//当前选班的课程在courses中的索引
                ArrayList<Integer> classroomIDs=courses.get(indexC).getClassroomsIDs();
                ArrayList<Integer> ableR=new ArrayList<Integer>();//存储推荐班级的id
                Random ranR=new Random();
                for(int j=0;j<classroomIDs.size();j++)//找出未满最低人数的班级
                {
                    int indexR=IdSearchIndex.searchIndex(classrooms,classroomIDs.get(j));
                    if(nowNumR[indexR]<minNumR)ableR.add(classroomIDs.get(j));
                }
                if(ableR.size()>0)//有班级未达最少人数
                {
                    int sR= ranR.nextInt(ableR.size());//选择的班级在ableR数组中的索引
                    int indexR=IdSearchIndex.searchIndex(classrooms,ableR.get(sR));
                    nowNumR[indexR]++;
                    students.get(i).addGrades(ableR.get(sR),courses.get(indexC).getCredit(),classrooms.get(indexR).getTerm());
                    classrooms.get(indexR).addStudentIDs(students.get(i).getId());
                }
                else //班级都已经满足最少人数
                {
                    int sR= ranR.nextInt(classroomIDs.size());//选择的班级在ableR数组中的索引
                    int indexR=IdSearchIndex.searchIndex(classrooms,classroomIDs.get(sR));
                    nowNumR[indexR]++;
                    students.get(i).addGrades(classroomIDs.get(sR),courses.get(indexC).getCredit(),classrooms.get(indexR).getTerm());
                    classrooms.get(indexR).addStudentIDs(students.get(i).getId());
                }
            }

            //打印选课结果
//            System.out.print(students.get(i).getId()+" "+students.get(i).getName()+" ");
//            for (int j = 0; j < students.get(i).getGrades().size(); j++) {
//                System.out.print(students.get(i).getGrades().get(j).getClassroomID()+" ");
//            }
//            System.out.println();

        }

        //更新教学班人数
        for (int i = 0; i < sumR; i++) {
            classrooms.get(i).setNumStudent(nowNumR[i]);
            //打印
//            System.out.println("ID:"+classrooms.get(i).getId()+"人数："+classrooms.get(i).getNumStudent());
//            System.out.println("学生id: "+classrooms.get(i).getStudentIDs());
        }

//        System.out.println("选课已完成！");
    }

    //获得成绩
    public void gradeGet()
    {
        //按课程难度来获取成绩
        for (int i = 0; i < courses.size(); i++)//每个课程
        {
            int difficulty=courses.get(i).getDifficulty();//课程难度
            for (int j = 0; j < courses.get(i).getClassroomsIDs().size(); j++)//每个课程的每个班级
            {
                int indexR=IdSearchIndex.searchIndex(classrooms,courses.get(i).getClassroomsIDs().get(j));
                for (int k = 0; k < classrooms.get(indexR).getNumStudent(); k++)//每个班级的每个学生
                {
                    int indexS=IdSearchIndex.searchIndex(students,classrooms.get(indexR).getStudentIDs().get(k));
                    int indexG=0;//成绩数组的索引项
                    //获取当前课程班级在成绩数组中的索引
                    for (int l = 0; l < students.get(indexS).getGrades().size(); l++)
                    {
                        if(students.get(indexS).getGrades().get(l).getClassroomID()==classrooms.get(indexR).getId())
                        {
                            indexG=l;
                            break;
                        }
                    }
                    //按成绩生成策略用随机数来生成成绩
                    int level=students.get(indexS).getLevel();
                    double mean = 5.0; // 均值
                    double standardDeviation = 10; // 标准差
                    double lowerBound = 0; // 下界
                    double upperBound = 100; // 上界
                    // 用正态分布生成器生成成绩(均值由1.学生优秀等级、2.课程难度等决定)
                    int usualScore=(int)Math.round(GenerateGaussian.Gauss(75+level*5-2*difficulty,3,lowerBound,upperBound));
                    int middleScore=(int)Math.round(GenerateGaussian.Gauss(50+level*10-2*difficulty,7,lowerBound,upperBound));
                    int experimentScore=(int)Math.round(GenerateGaussian.Gauss(60+level*8-2*difficulty,6,lowerBound,upperBound));
                    int endScore=(int)Math.round(GenerateGaussian.Gauss(40+level*12-3*difficulty,8,lowerBound,upperBound));
                    students.get(indexS).getGrades().get(indexG).setUsualScore(usualScore);
                    students.get(indexS).getGrades().get(indexG).setMiddleScore(middleScore);
                    students.get(indexS).getGrades().get(indexG).setExperimentScore(experimentScore);
                    students.get(indexS).getGrades().get(indexG).setEndScore(endScore);
                    students.get(indexS).getGrades().get(indexG).autoSumScore();//默认以平时成绩占10%，期中和实验分别占50%，期末占50%的比例来获取综合成绩
                    students.get(indexS).getGrades().get(indexG).setTerm(classrooms.get(indexR).getTerm());//成绩获得的时间（学期）
                }
            }
        }
        //自动生成绩点
        for (int i = 0; i < sumS; i++) {
            students.get(i).autoGPA();
//            System.out.println(students.get(i).getId()+" "+students.get(i).getName()+" "+students.get(i).getLevel()+" "+students.get(i).getGPA());
        }
    }

    //清空数据
    public void clearData()
    {
        students.clear();
        teachers.clear();
        classrooms.clear();
        courses.clear();
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public ArrayList<Classroom> getClassrooms() {
        return classrooms;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}
