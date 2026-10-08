package com.recipeshare.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserProfileDto {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    private String currentPassword;

    private String newPassword;

    public UserProfileDto() {
    }

    public UserProfileDto(Long id, String name, String email, String currentPassword, String newPassword) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public static UserProfileDtoBuilder builder() {
        return new UserProfileDtoBuilder();
    }

    public static class UserProfileDtoBuilder {
        private Long id;
        private String name;
        private String email;
        private String currentPassword;
        private String newPassword;

        public UserProfileDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserProfileDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserProfileDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserProfileDtoBuilder currentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
            return this;
        }

        public UserProfileDtoBuilder newPassword(String newPassword) {
            this.newPassword = newPassword;
            return this;
        }

        public UserProfileDto build() {
            return new UserProfileDto(id, name, email, currentPassword, newPassword);
        }
    }
}
