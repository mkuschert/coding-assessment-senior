package com.tallence.coding.gw.users.mapper;

import com.tallence.coding.gw.users.dto.UserDTO;
import com.tallence.coding.gw.users.model.User;
import com.tallence.coding.gw.users.utils.PasswordUtils;

public class UserMapper {

    private UserMapper() {};

    public static UserDTO modelToUserDTO(User user) {
        return new UserDTO(user.getUsername(), user.getRole(), user.getFullName());
    }
}
