package com.zenveus.the_culinary_academy.bo;

import com.zenveus.the_culinary_academy.bo.custom.impl.ProgramBOIMPL;
import com.zenveus.the_culinary_academy.bo.custom.impl.UserBOIMPL;

public class BOFactory {

    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        PROGRAM, USER
    }

    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case USER:
                return new UserBOIMPL();
            case PROGRAM:
                return new ProgramBOIMPL();
            default:
                return null;
        }
    }
}
