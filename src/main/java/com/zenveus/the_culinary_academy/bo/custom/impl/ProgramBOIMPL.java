package com.zenveus.the_culinary_academy.bo.custom.impl;

import com.zenveus.the_culinary_academy.bo.custom.ProgramBO;
import com.zenveus.the_culinary_academy.dao.DAOFactory;
import com.zenveus.the_culinary_academy.dao.custom.ProgramDAO;
import com.zenveus.the_culinary_academy.dto.ProgramDto;
import com.zenveus.the_culinary_academy.entity.Program;
import com.zenveus.the_culinary_academy.tm.ProgramTm;

import java.util.ArrayList;
import java.util.List;

public class ProgramBOIMPL implements ProgramBO {
    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PROGRAM);

    @Override
    public List<ProgramDto> getAllPrograms() {
        ArrayList<ProgramDto> programList = new ArrayList<>();
        List<Program> allPrograms = null;
        try {
            allPrograms = programDAO.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (Program program : allPrograms) {
            programList.add(new ProgramDto());
        }

        return programList;
    }
}
