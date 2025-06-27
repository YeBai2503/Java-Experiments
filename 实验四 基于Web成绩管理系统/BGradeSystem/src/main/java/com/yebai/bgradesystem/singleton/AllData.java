package com.yebai.bgradesystem.singleton;

import com.yebai.bgradesystem.factory.ClassroomFactory;
import com.yebai.bgradesystem.factory.GradeFactory;
import com.yebai.bgradesystem.factory.StudentFactory;
import com.yebai.bgradesystem.factory.TeacherFactory;
import com.yebai.bgradesystem.pojo.*;
import com.yebai.bgradesystem.service.*;
import com.yebai.bgradesystem.tool.GenerateGaussian;
import com.yebai.bgradesystem.tool.IdSearchIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Component
public class AllData {
    //private static AllData allData=new AllData();
    private ArrayList<Student> students = new ArrayList<Student>();//学生数据集
    private ArrayList<Teacher>teachers = new ArrayList<Teacher>();//老师数据集
    private ArrayList<Classroom>classrooms = new ArrayList<Classroom>();//教学班数据集
    private ArrayList<Course>courses = new ArrayList<Course>();//课程数据集
    private ArrayList<Grade>grades = new ArrayList<Grade>();//成绩数据集
    private int sumS,sumT,sumR,sumC;//学生、老师、教学班、课程的总数

//    private AllData(){}
//    public static AllData getInstance(){return allData;}
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private CourseService courseService;


    //生成随机基础数据
    public void dataGenerate()
    {
        //生成各对象的数量和课程对象
        this.sumS = 120;
        this.sumT = 12;
        this.sumR = this.sumT;//教学班数量和老师数量相同
        courses = new ArrayList<>(courseService.allCourse());
        this.sumC = courses.size();

        //生成随机姓名用的姓和名(中文字符java不好对齐，所以统一三个字)
        String[] lastname={
                "王", "李", "张", "刘", "陈",
                "杨", "黄", "赵", "吴", "周",
                "徐", "孙", "马", "朱", "胡",
                "魏", "关", "江", "陆", "龙"
        };
        String[] firstname={//前十三个为男名，后12个为女名，设置性别时会用到
                "志伟", "磊峰", "文强", "洋扬", "翔",
                "建军", "天辰", "超", "平良", "健豪",
                "杰雄", "辉文", "俊",
                "小芳", "娜美", "静珠", "晓敏", "丽莎",
                "霞", "婷", "莉莉", "梦泽", "程雯",
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
            String sex2;
            //生成名字
            do {
                name1=ranName.nextInt(lastname.length);//获取姓的数组的随机下标
                name2=ranName.nextInt(firstname.length);//获取名的数组的随机下标
            }while(nameSet.contains(name1*100+name2));//确保唯一的姓名组合
            nameSet.add(name1*100+name2);
            name=lastname[name1]+firstname[name2];
            //根据名来确定性别
            if(name2<(firstname.length/2+1))sex2="男";
            else sex2="女";
            //生成5位的学生id
            id=24001+i;
            //生成正态分布的优秀等级（五分制）
            level=(int)Math.round(GenerateGaussian.Gauss(3,2,1,5));
            //生成学生对象
            StudentFactory sF=new StudentFactory();
            this.students.add(sF.produce(id,name,"123456",sex2,level));
            studentService.addStudent(id,name,"123456",sex2,level);
        }

        //生成老师和对应的教学班数据
        int sumClass=0;//记录当前已生成的教学班个数
        for(int i=0;i<this.sumT;i++)
        {
            //生成老师基本信息
            String name;
            int id,sex,name1,name2;
            String sex2;
            do {
                name1=ranName.nextInt(lastname.length);//获取姓的数组的随机下标
                name2=ranName.nextInt(firstname.length);//获取名的数组的随机下标
            }while(nameSet.contains(name1*100+name2));//确保唯一的姓名组合
            nameSet.add(name1*100+name2);
            name=lastname[name1]+firstname[name2];
            if(name2<(firstname.length/2+1))sex2="男";//根据名来确定性别
            else sex2="女";
            //生成三位的老师id
            id=101+i;
            TeacherFactory tF=new TeacherFactory();
            this.teachers.add( tF.produce(id,name,"654321",sex2));
            teacherService.addTeacher(id,name,"654321",sex2);


            //生成对应教学班
            int cSelect;//选择的课程在课程数组里的索引
            int RID,term;//教学班号，开课学期
            Random ranC=new Random();
            if(sumClass<2*sumC)cSelect=sumClass%sumC;//先满足至少的每个课程两个班
            else {//满足后随机选择课程建班
                cSelect=ranC.nextInt(sumC);
            }
            term=1+ranC.nextInt(4);//默认只有四个学期
            //生成4位的班号id
            RID=2401+i;
            teachers.get(i).setClassId(RID);
            courses.get(cSelect).addClassroomsIDs(RID);
            ClassroomFactory rF=new ClassroomFactory();
            classrooms.add(rF.produce(RID,courses.get(cSelect).getName(),term,courses.get(cSelect).getId(),id));
            classroomService.addClassroom(RID,courses.get(cSelect).getName(),term,courses.get(cSelect).getId(),id);
            sumClass++;
            //更新老师教学班号
            teacherService.updTeacherById(id,name,"654321",sex2,RID);
        }

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
        }
        for(int i=0;i<sumC;i++)//课程人数保存到课程类中
        {
            courses.get(i).setNumStudents(nowNumC[i]);
            courseService.updCourseById(courses.get(i).getId(),courses.get(i).getName(),courses.get(i).getDifficulty(),courses.get(i).getCredit(),nowNumC[i]);
        }

        //选完课程后，选具体的教学班
        // 策略与选课程类似，为先在未满最小人数的课程班级中随机选取，若数量不够，则在已满足的课程班级中再随机选
        int minNumR=20;//班级最少人数
        int[] nowNumR=new int[sumR];//班级当前已有人数
        for(int i=0;i<sumS;i++)
        {
            for(int nowC=0;nowC<students.get(i).getCourseIDs().size();nowC++)
            {
                int indexC= IdSearchIndex.searchIndex(courses,students.get(i).getCourseIDs().get(nowC));//当前选班的课程在courses中的索引
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
                    //给学生选班
                    GradeFactory gF=new GradeFactory();
                    grades.add(gF.produce(students.get(i).getId(),ableR.get(sR),courses.get(indexC).getId(),courses.get(indexC).getCredit(),classrooms.get(indexR).getTerm()));
                    gradeService.addGrade(students.get(i).getId(),ableR.get(sR),courses.get(indexC).getId(),courses.get(indexC).getCredit(),classrooms.get(indexR).getTerm());
                    classrooms.get(indexR).addStudentIDs(students.get(i).getId());
                }
                else //班级都已经满足最少人数
                {
                    int sR= ranR.nextInt(classroomIDs.size());//选择的班级在ableR数组中的索引
                    int indexR=IdSearchIndex.searchIndex(classrooms,classroomIDs.get(sR));
                    nowNumR[indexR]++;
                    //给学生选班
                    GradeFactory gF=new GradeFactory();
                    grades.add(gF.produce(students.get(i).getId(),classroomIDs.get(sR),courses.get(indexC).getId(),courses.get(indexC).getCredit(),classrooms.get(indexR).getTerm()));
                    gradeService.addGrade(students.get(i).getId(),classroomIDs.get(sR),courses.get(indexC).getId(),courses.get(indexC).getCredit(),classrooms.get(indexR).getTerm());
                    classrooms.get(indexR).addStudentIDs(students.get(i).getId());
                }
            }
        }
        //更新教学班人数
        for (int i = 0; i < sumR; i++) {
            classrooms.get(i).setNumStudent(nowNumR[i]);
            classroomService.updClassroomById(classrooms.get(i).getId(),classrooms.get(i).getName(),classrooms.get(i).getTerm(),classrooms.get(i).getCourseId(),classrooms.get(i).getTeacherId(),nowNumR[i]);
        }
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
                    for (int l = 0; l < grades.size(); l++)
                    {
                        if(grades.get(l).getStudentId()==students.get(indexS).getId()&&grades.get(l).getClassroomId()==classrooms.get(indexR).getId())
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
                    int term=classrooms.get(indexR).getTerm();
                    //更新成绩
                    gradeService.updGrade(students.get(indexS).getId(),classrooms.get(indexR).getId(),courses.get(i).getId(),courses.get(i).getCredit(),term,usualScore,middleScore,experimentScore,endScore);

                    grades.get(indexG).setUsualScore(usualScore);
                    grades.get(indexG).setMiddleScore(middleScore);
                    grades.get(indexG).setExperimentScore(experimentScore);
                    grades.get(indexG).setEndScore(endScore);
                    grades.get(indexG).autoSumScore();//默认以平时成绩占10%，期中和实验分别占50%，期末占50%的比例来获取综合成绩
                    grades.get(indexG).setTerm(term);//成绩获得的时间（学期）
                }
            }
        }
        //自动生成绩点
        for (int i = 0; i < sumS; i++) {
            students.get(i).autoGPA(gradeService,studentService);
        }
    }

    //清空数据
    public void clearData()
    {
        students.clear();
        teachers.clear();
        classrooms.clear();
        grades.clear();
        //courses.clear();
        studentService.delAllStudent();
        teacherService.delAllTeacher();
        classroomService.delAllClassroom();
        gradeService.delAllGrade();
    }

    //生成全部数据
    public void init()
    {
        clearData();
        dataGenerate();
        classSelect();
        gradeGet();
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

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void selectCourseOneStudent(int studentId)
    {
        Student student=studentService.selStudentById(studentId);
        ArrayList<Integer> courseId=new ArrayList<Integer>(),classroomIds=new ArrayList<Integer>();
        Random ranNC=new Random();
        courses = new ArrayList<>(courseService.allCourse());
        int numC=3+ranNC.nextInt(courses.size()-2);//要学习的课程数量
        //ArrayList<Course>courses1=new ArrayList<Course>(courses);
        //先选课程
        Random ranCourse=new Random();
        for(int k=0;k<numC;k++)
        {
            int sC=ranCourse.nextInt(courses.size());
            courseId.add(courses.get(sC).getId());
            courses.remove(sC);
        }
        for(int i=0;i<courseId.size();i++)//课程人数保存到课程类中
        {
            Course course=courseService.selCourseById(courseId.get(i));
            courseService.updCourseById(course.getId(),course.getName(),course.getDifficulty(),course.getCredit(),course.getNumStudents()+1);
        }
        //选完课程后，选具体的教学班
        for(int nowC=0;nowC<courseId.size();nowC++)
        {
            List<Classroom> classroomList=classroomService.selClassroomByCourse(courseId.get(nowC));
            Random ranR=new Random();
            int sR= ranR.nextInt(classroomList.size());
            classroomIds.add(classroomList.get(sR).getId());
            int classroomId=classroomList.get(sR).getId();
            //给学生选班
            GradeFactory gF=new GradeFactory();
            Course course=courseService.selCourseById(courseId.get(nowC));
            Classroom classroom=classroomService.selClassroomById(classroomId);
            gradeService.addGrade(student.getId(),classroomId,course.getId(),course.getCredit(),classroom.getTerm());
        }
        //更新教学班人数
        for (int i = 0; i < classroomIds.size(); i++) {
            Classroom classroom=classroomService.selClassroomById(classroomIds.get(i));
            classroomService.updClassroomById(classroom.getId(),classroom.getName(),classroom.getTerm(),classroom.getCourseId(),classroom.getTeacherId(),classroom.getNumStudent()+1);
        }
    }

    public void manyGradeGet(List<Integer>studentIds,int classroomId)
    {
        Classroom classroom=classroomService.selClassroomById(classroomId);
        Course course=courseService.selCourseById(classroom.getCourseId());
        for(int studentId:studentIds)
        {
            Student student=studentService.selStudentById(studentId);
            int level=student.getLevel();
            int difficulty=course.getDifficulty();
            double lowerBound = 0; // 下界
            double upperBound = 100; // 上界
            // 用正态分布生成器生成成绩(均值由1.学生优秀等级、2.课程难度等决定)
            int usualScore=(int)Math.round(GenerateGaussian.Gauss(75+level*5-2*difficulty,3,lowerBound,upperBound));
            int middleScore=(int)Math.round(GenerateGaussian.Gauss(50+level*10-2*difficulty,7,lowerBound,upperBound));
            int experimentScore=(int)Math.round(GenerateGaussian.Gauss(60+level*8-2*difficulty,6,lowerBound,upperBound));
            int endScore=(int)Math.round(GenerateGaussian.Gauss(40+level*12-3*difficulty,8,lowerBound,upperBound));
            int term=classroom.getTerm();
            //更新成绩
            gradeService.updGrade(studentId,classroom.getId(),course.getId(),course.getCredit(),term,usualScore,middleScore,experimentScore,endScore);
        }


    }

}
