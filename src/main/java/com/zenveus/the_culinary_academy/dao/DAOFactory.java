package com.zenveus.the_culinary_academy.dao;

import com.zenveus.the_culinary_academy.dao.custom.impl.ProgramDAOImpl;
import com.zenveus.the_culinary_academy.dao.custom.impl.StudentDAOImpl;
import com.zenveus.the_culinary_academy.dao.custom.impl.UserDAOImpl;

import static com.zenveus.the_culinary_academy.bo.BOFactory.BOTypes.STUDENT;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDAOFactory(){
        return(daoFactory==null)? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        PROGRAM, USER, STUDENT
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types){
            case USER:
                return new UserDAOImpl();
            case PROGRAM:
                return new ProgramDAOImpl();
            case STUDENT:
                return new StudentDAOImpl();
            default:
                return null;
        }
    }
}
