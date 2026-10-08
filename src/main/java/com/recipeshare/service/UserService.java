package com.recipeshare.service;

import com.recipeshare.dto.UserProfileDto;
import com.recipeshare.dto.UserRegistrationDto;
import com.recipeshare.entity.User;
import com.recipeshare.enums.Role;

import java.util.List;

public interface UserService {

    User registerUser(UserRegistrationDto registrationDto);

    User findByEmail(String email);

    User findById(Long id);

    User updateProfile(Long userId, UserProfileDto profileDto);

    List<User> getAllUsers();

    User updateUserRole(Long userId, Role role);

    void deleteUser(Long userId);

    void initAdminUser();
}
