package com.yebai.bgradesystem.controller;

import com.yebai.bgradesystem.service.GradeService;
import com.yebai.bgradesystem.service.StudentService;
import com.yebai.bgradesystem.singleton.AllData;
import com.yebai.bgradesystem.tool.GenerateGaussian;
import com.yebai.bgradesystem.tool.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AllData allData;
    @Autowired
    private GradeService gradeService;

    // 添加学生
    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public String addStudent(@RequestParam("id") int id,
                             @RequestParam("name") String name,
                             @RequestParam("password") String password,
                             @RequestParam("sex") String sex) {
        int level = (int)Math.round(GenerateGaussian.Gauss(3,2,1,5));
        studentService.addStudent(id, name, password, sex,level);
        return Message.okMessage("添加学生成功!");
    }

    // 删除学生
    @RequestMapping(value = "/delStudentById/{id}", method = RequestMethod.DELETE)
    public String deleteStudent(@PathVariable("id") int id) {
        studentService.delStudentById(id);
        return Message.okMessage("删除学生成功! id="+id);
    }

    // 按id查询学生
    @RequestMapping(value = "/selStudentById/{id}", method = RequestMethod.GET)
    public String selectStudent(@PathVariable("id") int id) {
        return Message.okMessageByData("查询学生成功! id="+id,studentService.selStudentById(id));
    }

    // 查询所有学生
    @RequestMapping(value = "/allStudent", method = RequestMethod.GET)
    public String allStudent() {
        return Message.okMessageByData("查询所有学生成功!",studentService.allStudent());
    }
    // 修改学生信息
    @RequestMapping(value = "/updStudent", method = RequestMethod.PUT)
    public String updateStudent(@RequestParam("id") int id,
                                @RequestParam("name") String name,
                                @RequestParam("password") String password,
                                @RequestParam("sex") String sex,
                                @RequestParam("level") int level) {
        studentService.updStudentById(id, name, password, sex, level);
        return Message.okMessage("修改学生信息成功! id=" + id);
    }

    // 自动选课
    @RequestMapping(value = "/autoSelectCourse", method = RequestMethod.POST)
    public String addStudent(@RequestParam("id") int id) {
        if(gradeService.selGradeByStudent(id).size() == 0)
        {
            allData.selectCourseOneStudent(id);
            return Message.okMessage("自动选课成功!");
        }
        else return Message.okMessage("该学生已经有选课记录!");
    }

    // 测试连通性
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String deleteStudent() {
        return Message.okMessage("测试成功!");
    }
}
