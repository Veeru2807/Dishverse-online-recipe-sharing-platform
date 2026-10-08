package com.recipeshare.util;

import com.recipeshare.entity.User;
import com.recipeshare.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static CustomUserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return (CustomUserDetails) authentication.getPrincipal();
        }
        return null;
    }

    public static User getCurrentUser() {
        CustomUserDetails userDetails = getCurrentUserDetails();
        return userDetails != null ? userDetails.getUser() : null;
    }

    public static boolean isAuthenticated() {
        return getCurrentUserDetails() != null;
    }

    public static boolean isAdmin() {
        User user = getCurrentUser();
        return user != null && user.getRole().name().equals("ROLE_ADMIN");
    }

    public static void updateCurrentUser(User updatedUser) {
        CustomUserDetails userDetails = getCurrentUserDetails();
        if (userDetails != null && updatedUser != null) {
            userDetails.setUser(updatedUser);
            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
            org.springframework.security.authentication.UsernamePasswordAuthenticationToken newAuth =
                    new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                            userDetails,
                            currentAuth != null ? currentAuth.getCredentials() : null,
                            userDetails.getAuthorities()
                    );
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
    }
}
