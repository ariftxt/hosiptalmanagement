package com.hospitalManagement.mapper;

import com.hospitalManagement.dto.UserDTO;
import com.hospitalManagement.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user){
        return UserDTO.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .userRoles(user.getUserRoles()).build();
    }
}
