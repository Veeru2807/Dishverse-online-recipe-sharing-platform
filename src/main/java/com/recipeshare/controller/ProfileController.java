package com.recipeshare.controller;

import com.recipeshare.dto.UserProfileDto;
import com.recipeshare.entity.User;
import com.recipeshare.service.UserService;
import com.recipeshare.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showProfile(Model model) {
        User currentUser = SecurityUtils.getCurrentUser();
        UserProfileDto profileDto = UserProfileDto.builder()
                .id(currentUser.getId())
                .name(currentUser.getName())
                .email(currentUser.getEmail())
                .build();

        model.addAttribute("profileDto", profileDto);
        model.addAttribute("currentUser", currentUser);
        return "user/profile";
    }

    @PostMapping("/update")
    public String updateProfile(@Valid @ModelAttribute("profileDto") UserProfileDto profileDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        User currentUser = SecurityUtils.getCurrentUser();
        if (bindingResult.hasErrors()) {
            model.addAttribute("currentUser", currentUser);
            return "user/profile";
        }

        try {
            User updatedUser = userService.updateProfile(currentUser.getId(), profileDto);
            SecurityUtils.updateCurrentUser(updatedUser);
            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
            return "redirect:/profile";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("currentUser", currentUser);
            return "user/profile";
        }
    }
}
