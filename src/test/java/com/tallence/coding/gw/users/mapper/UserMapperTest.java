package com.tallence.coding.gw.users.mapper;

import com.tallence.coding.gw.users.dto.UserDTO;
import com.tallence.coding.gw.users.model.User;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserMapperTest {

    @Test
    void modelToUserDTO() {
        User user = new User("test", "password", "user", "Test User");

        UserDTO userDTO = UserMapper.modelToUserDTO(user);

        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getFullName(), userDTO.getFullName());
    }
}