package com.zenveus.the_culinary_academy.bo.custom;

import com.zenveus.the_culinary_academy.bo.SuperBO;
import com.zenveus.the_culinary_academy.dto.UserDto;

public interface UserBO extends SuperBO {
    boolean addUser(UserDto userDto) throws Exception;
}
