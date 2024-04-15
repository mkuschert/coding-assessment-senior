package com.tallence.coding.gw.users.service;

import com.tallence.coding.gw.users.dto.UpdatePasswordDTO;
import com.tallence.coding.gw.users.dto.UpdateUserDTO;
import com.tallence.coding.gw.users.model.User;
import com.tallence.coding.gw.users.utils.PasswordUtils;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.HashMap;

@ApplicationScoped
public class UsersService {

    public Map<String, User> inMemoryUsers = new HashMap<String, User>();

    // workaround for setting test data
    public void setInMemoryUsers(Map<String, User> inMemoryUsers) {
        this.inMemoryUsers = inMemoryUsers;
    }

    public UsersService() {
        User adminUser = new User("admin", PasswordUtils.hashPassword("password"), "admin", "Super Admin");
        User demoUser = new User("demo", PasswordUtils.hashPassword("password123"), "user", "Demo User");

        inMemoryUsers.put(adminUser.getUsername(), adminUser);
        inMemoryUsers.put(demoUser.getUsername(), demoUser);
    }

    public Uni<User> getByUsername(String username) {
        User user = inMemoryUsers.get(username);

        return Uni.createFrom().item(user);
    }

    public User getUser(String username) {
        User user = inMemoryUsers.get(username);
inMemoryUsers.values().forEach(u -> System.out.println("U " + user.getUsername()));
        return user;
    }

    public Uni<String> createUser(User user) {
        String hashPassword = PasswordUtils.hashPassword(user.getPassword());
        user.setPassword(hashPassword);
        User existingUser = inMemoryUsers.putIfAbsent(user.getUsername(), user);

        return Uni.createFrom().item(existingUser == null ? user.getUsername() : null);
    }

    public Uni<Boolean> updateUser(String username, UpdateUserDTO updateUserDTO) {
        Boolean updated = Boolean.FALSE;
        User existingUser = inMemoryUsers.get(username);

        if(existingUser != null) {
            existingUser.setFullName(updateUserDTO.getFullName());
            inMemoryUsers.put(existingUser.getUsername(), existingUser);
            updated = Boolean.TRUE;
        }

        return Uni.createFrom().item(updated);
    }

    public Uni<Boolean> updatePassword(String username, UpdatePasswordDTO updatePasswordDTO) {
        Boolean updated = Boolean.FALSE;
        User existingUser = inMemoryUsers.get(username);

        if(existingUser != null) {
            String hashPassword = PasswordUtils.hashPassword(updatePasswordDTO.getPassword());
            existingUser.setPassword(hashPassword);
            inMemoryUsers.put(existingUser.getUsername(), existingUser);
            updated = Boolean.TRUE;
        }

        return Uni.createFrom().item(updated);
    }

    public Uni<Boolean> deleteUser(String username) {
        User existingUser = inMemoryUsers.remove(username);

        return Uni.createFrom().item(existingUser != null);
    }
}
