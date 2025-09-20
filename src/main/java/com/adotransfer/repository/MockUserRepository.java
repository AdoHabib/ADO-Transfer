package com.adotransfer.repository;

import com.adotransfer.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("no-db")
public class MockUserRepository {

    public Optional<User> findByPhoneNumber(String phoneNumber) {
        // Mock user
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setPhoneNumber(phoneNumber);
        mockUser.setFirstName("Test");
        mockUser.setLastName("User");
        return Optional.of(mockUser);
    }

    public Optional<User> findById(Long id) {
        // Mock user
        User mockUser = new User();
        mockUser.setId(id);
        mockUser.setPhoneNumber("1234567890");
        mockUser.setFirstName("Test");
        mockUser.setLastName("User");
        return Optional.of(mockUser);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return false; // Mock - no existing users
    }

    public boolean existsByEmail(String email) {
        return false; // Mock - no existing users
    }

    public User save(User user) {
        user.setId(1L); // Mock ID
        return user;
    }
}

