package com.tallence.coding.gw.users.resource;

import com.tallence.coding.gw.users.dto.UpdatePasswordDTO;
import com.tallence.coding.gw.users.dto.UpdateUserDTO;
import com.tallence.coding.gw.users.model.User;
import com.tallence.coding.gw.users.service.UsersService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.smallrye.mutiny.Uni;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;

@QuarkusTest
class UsersResourceTest {

    @InjectMock
    UsersService usersService;

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    void getByUsername_ok() {
        String username = "user1";
        User user = new User("user1", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8",
                "user", "User 1");
        Mockito.when(usersService.getByUsername(username)).thenReturn(Uni.createFrom().item(user));

        given()
                .when().get("/users/" + username)
                .then()
                .statusCode(200)
                .body("username", is("user1"), "fullName", is("User 1"));
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    void getByUsername_not_found() {
        String username = "user0";
        Mockito.when(usersService.getByUsername(username)).thenReturn(Uni.createFrom().item((User)null));

        given()
                .when().get("/users/" + username)
                .then()
                .statusCode(404);
    }

    @Test
    @TestSecurity(user = "demo", roles = {"user"})
    void getByUsername_forbidden() {
        String username = "user1";

        given()
                .when().get("/users/" + username)
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    void createUser_created() {
        String username = "user0";
        User user = new User(username, "password0", "user", "Test User 0");
        Mockito.when(usersService.createUser(user)).thenReturn(Uni.createFrom().item(username));

        given()
                .header("Content-Type", "application/json")
                .body(user)
                .when().post("/users")
                .then()
                .statusCode(201);
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    void createUser_bad_request() {
        String username = "user1";
        User user = new User(username, "password1", "user", "Test User 1");
        Mockito.when(usersService.createUser(user)).thenReturn(Uni.createFrom().item((String)null));

        given()
                .header("Content-Type", "application/json")
                .body(user)
                .when().post("/users")
                .then()
                .statusCode(400);
    }

    @Test
    @TestSecurity(user = "demo", roles = {"user"})
    void createUser_forbidden() {
        String username = "user0";
        User user = new User(username, "password0", "user", "Test User 0");

        given()
                .header("Content-Type", "application/json")
                .body(user)
                .when().post("/users")
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    void updateUser_ok() {
        String username = "user1";
        UpdateUserDTO updateUserDTO = new UpdateUserDTO("New Name");
        Mockito.when(usersService.updateUser(username, updateUserDTO)).thenReturn(Uni.createFrom().item(Boolean.TRUE));

        given()
                .header("Content-Type", "application/json")
                .body(updateUserDTO)
                .when().put("/users/" + username)
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    void updateUser_not_found() {
        String username = "user0";
        UpdateUserDTO updateUserDTO = new UpdateUserDTO("New Name");
        Mockito.when(usersService.updateUser(username, updateUserDTO)).thenReturn(Uni.createFrom().item(Boolean.FALSE));

        given()
                .header("Content-Type", "application/json")
                .body(updateUserDTO)
                .when().put("/users/" + username)
                .then()
                .statusCode(404);
    }

    @Test
    @TestSecurity(user = "demo", roles = {"user"})
    void updateUser_forbidden() {
        String username = "user1";
        UpdateUserDTO updateUserDTO = new UpdateUserDTO("New Name");

        given()
                .header("Content-Type", "application/json")
                .body(updateUserDTO)
                .when().put("/users/" + username)
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    void updatePassword_ok() {
        String username = "user1";
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("newpassword");
        Mockito.when(usersService.updatePassword(username, updatePasswordDTO)).thenReturn(Uni.createFrom().item(Boolean.TRUE));

        given()
                .header("Content-Type", "application/json")
                .body(updatePasswordDTO)
                .when().put("/users/" + username + "/password")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    void updatePassword_not_found() {
        String username = "user0";
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("newpassword");
        Mockito.when(usersService.updatePassword(username, updatePasswordDTO)).thenReturn(Uni.createFrom().item(Boolean.FALSE));

        given()
                .header("Content-Type", "application/json")
                .body(updatePasswordDTO)
                .when().put("/users/" + username + "/password")
                .then()
                .statusCode(404);
    }

    @Test
    @TestSecurity(user = "demo", roles = {"user"})
    void updatePassword_forbidden() {
        String username = "user1";
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("newpassword");

        given()
                .header("Content-Type", "application/json")
                .body(updatePasswordDTO)
                .when().put("/users/" + username + "/password")
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    void deleteUser_ok() {
        String username = "user1";
        Mockito.when(usersService.deleteUser(username)).thenReturn(Uni.createFrom().item(Boolean.TRUE));

        given()
                .when().delete("/users/" + username)
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    void deleteUser_net_found() {
        String username = "user0";
        Mockito.when(usersService.deleteUser(username)).thenReturn(Uni.createFrom().item(Boolean.FALSE));

        given()
                .when().delete("/users/" + username)
                .then()
                .statusCode(404);
    }

    @Test
    @TestSecurity(user = "demo", roles = {"user"})
    void deleteUser_forbidden() {
        String username = "user1";

        given()
                .when().delete("/users/" + username)
                .then()
                .statusCode(403);
    }
}