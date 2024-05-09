package com.example.tddecommerce.user.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class UserTest {

    @Test
    void testUserGettersAndSetters() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setEmail("user@example.com");
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        assertEquals(1L, user.getUserId());
        assertEquals("testUser", user.getUsername());
        assertEquals("testPassword", user.getPassword());
        assertEquals("user@example.com", user.getEmail());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void testUserEquality() {
        User user1 = new User();
        user1.setUserId(1L);
        user1.setUsername("testUser");
        user1.setPassword("testPassword");
        user1.setEmail("user@example.com");
        user1.setCreatedAt(LocalDateTime.now());
        user1.setUpdatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setUserId(1L);
        user2.setUsername("testUser");
        user2.setPassword("testPassword");
        user2.setEmail("user@example.com");
        user2.setCreatedAt(LocalDateTime.now());
        user2.setUpdatedAt(LocalDateTime.now());

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testUserNotEqual() {
        User user1 = new User();
        user1.setUserId(1L);
        user1.setUsername("testUser");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setUsername("anotherUser");

        assertNotEquals(user1, user2);
    }
}
