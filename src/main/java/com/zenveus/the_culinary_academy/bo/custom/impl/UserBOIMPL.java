package com.zenveus.the_culinary_academy.bo.custom.impl;

import com.zenveus.the_culinary_academy.bo.custom.UserBO;
import com.zenveus.the_culinary_academy.dao.DAOFactory;
import com.zenveus.the_culinary_academy.dao.custom.UserDAO;
import com.zenveus.the_culinary_academy.dto.UserDto;
import com.zenveus.the_culinary_academy.entity.User;


public class UserBOIMPL implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.USER);


    @Override
    public boolean addUser(UserDto userDto) throws Exception {
        try {
            userDAO.add(new User(userDto.getUserId(), userDto.getFullName(), userDto.getEmail(), userDto.getPhoneNumber(), userDto.getAddress(), userDto.getUsername(), userDto.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
