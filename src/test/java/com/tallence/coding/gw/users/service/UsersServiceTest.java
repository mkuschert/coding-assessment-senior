package com.tallence.coding.gw.users.service;

import com.tallence.coding.gw.users.dto.UpdatePasswordDTO;
import com.tallence.coding.gw.users.dto.UpdateUserDTO;
import com.tallence.coding.gw.users.model.User;
import com.tallence.coding.gw.users.utils.PasswordUtils;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UsersServiceTest {

    @Inject
    UsersService usersService;

    @BeforeEach
    void setUp() {
        Map<String, User> testUsers = new HashMap<String, User>();
        IntStream.range(1, 3).forEach(i -> {
                User user = new User("user" + i, PasswordUtils.hashPassword("password" + i), "user", "Test User " + i);
                testUsers.put(user.getUsername(), user);
        });

            Field field = ReflectionUtils.findFields(UsersService.class, f -> f.getName().equals("inMemoryUsers"),
                            ReflectionUtils.HierarchyTraversalMode.TOP_DOWN).get(0);
        field.setAccessible(true);
        try {
            field.set(usersService, testUsers);
            usersService.setInMemoryUsers(testUsers);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getByUsername_ok() {
        String username = "user1";
        Uni<User> userUni = usersService.getByUsername(username);
        User user = userUni.await().indefinitely();

        assertNotNull(user);
        assertEquals("user1", user.getUsername());
        assertEquals("0b14d501a594442a01c6859541bcb3e8164d183d32937b851835442f69d5c94e", user.getPassword());
        assertEquals("Test User 1", user.getFullName());
    }

    @Test
    void getByUsername_not_found() {
        String username = "user0";
        Uni<User> userUni = usersService.getByUsername(username);
        User user = userUni.await().indefinitely();

        assertNull(user);
    }

    @Test
    void createUser_ok() {
        User user = new User("user0", "password0", "user", "Test User 0");
        Uni<String> usernameUni = usersService.createUser(user);
        String username = usernameUni.await().indefinitely();

        assertEquals("user0", username);
    }

    @Test
    void createUser_nok() {
        User user = new User("user1", "password1", "user", "Test User 1");
        Uni<String> usernameUni = usersService.createUser(user);
        String username = usernameUni.await().indefinitely();

        assertNull(username);
    }

    @Test
    void updateUser_ok() {
        String username = "user1";
        UpdateUserDTO updateUserDTO = new UpdateUserDTO("New Name");

        Uni<Boolean> updatedUni = usersService.updateUser(username, updateUserDTO);
        Boolean updated = updatedUni.await().indefinitely();

        assertTrue(updated);
    }

    @Test
    void updateUser_not_found() {
        String username = "user0";
        UpdateUserDTO updateUserDTO = new UpdateUserDTO("New Name");

        Uni<Boolean> updatedUni = usersService.updateUser(username, updateUserDTO);
        Boolean updated = updatedUni.await().indefinitely();

        assertFalse(updated);
    }

    @Test
    void updatePassword_ok() {
        String username = "user1";
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("newpassword");

        Uni<Boolean> updatedUni = usersService.updatePassword(username, updatePasswordDTO);
        Boolean updated = updatedUni.await().indefinitely();

        assertTrue(updated);
    }

    @Test
    void updatePassword_not_found() {
        String username = "user0";
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("newpassword");

        Uni<Boolean> updatedUni = usersService.updatePassword(username, updatePasswordDTO);
        Boolean updated = updatedUni.await().indefinitely();

        assertFalse(updated);
    }

    @Test
    void deleteUser_ok() {
        String username = "user1";
        Uni<Boolean> deletedUni = usersService.deleteUser(username);
        Boolean deleted = deletedUni.await().indefinitely();

        assertTrue(deleted);
    }

    @Test
    void deleteUser_not_found() {
        String username = "user0";
        Uni<Boolean> deletedUni = usersService.deleteUser(username);
        Boolean deleted = deletedUni.await().indefinitely();

        assertFalse(deleted);
    }
}