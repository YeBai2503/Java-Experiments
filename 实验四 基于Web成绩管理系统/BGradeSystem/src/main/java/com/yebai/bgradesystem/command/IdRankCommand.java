package com.yebai.bgradesystem.command;

import com.yebai.bgradesystem.pojo.Grade;

import java.util.ArrayList;
import java.util.Comparator;

public class IdRankCommand implements Command {
    @Override
    public ArrayList<Grade> execute(Object... args) {
        ArrayList<Grade> grades = (ArrayList<Grade>) args[0];
        grades.sort(Comparator.comparingInt(Grade::getStudentId));
        return grades;
    }
}

