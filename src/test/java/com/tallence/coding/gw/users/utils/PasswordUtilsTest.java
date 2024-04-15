package com.tallence.coding.gw.users.utils;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class PasswordUtilsTest {

    @Test
    void hashPassword() {
        String hashPassword = PasswordUtils.hashPassword("password");

        assertEquals("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", hashPassword);
    }
}