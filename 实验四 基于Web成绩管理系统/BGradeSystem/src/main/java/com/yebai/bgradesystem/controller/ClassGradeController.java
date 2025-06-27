package com.yebai.bgradesystem.controller;

import com.yebai.bgradesystem.command.GradeRankCommand;
import com.yebai.bgradesystem.command.IdRankCommand;
import com.yebai.bgradesystem.pojo.Grade;
import com.yebai.bgradesystem.service.GradeService;
import com.yebai.bgradesystem.tool.GenerateGaussian;
import com.yebai.bgradesystem.tool.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClassGradeController {
    @Autowired
    private GradeService gradeService;

    // 学号排序班级成绩
    @RequestMapping(value = "/idRankingClassGrade/{id}", method = RequestMethod.GET)
    public String idRankingClassGrade(@PathVariable("id") int id) {
        List<Grade> result = gradeService.selClassGrade(id);
        IdRankCommand idRankCommand = new IdRankCommand();
        result=idRankCommand.execute(result);
        return Message.okMessageByData("学号排序班成绩!", result);
    }

    // 成绩排序班级成绩
    @RequestMapping(value = "/gradeRankingClassGrade/{id}", method = RequestMethod.GET)
    public String gradeRankingClassGrade(@PathVariable("id") int id) {
        List<Grade> result = gradeService.selClassGrade(id);
        GradeRankCommand gradeRankCommand = new GradeRankCommand();
        result=gradeRankCommand.execute(result);
        return Message.okMessageByData("成绩排序班成绩!", result);
    }


}
