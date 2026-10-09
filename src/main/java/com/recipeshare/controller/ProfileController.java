package com.recipeshare.controller;

import com.recipeshare.dto.UserProfileDto;
import com.recipeshare.entity.Recipe;
import com.recipeshare.entity.RecipeCollection;
import com.recipeshare.entity.Review;
import com.recipeshare.entity.User;
import com.recipeshare.service.CollectionService;
import com.recipeshare.service.RecipeService;
import com.recipeshare.service.ReviewService;
import com.recipeshare.service.UserService;
import com.recipeshare.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final RecipeService recipeService;
    private final CollectionService collectionService;
    private final ReviewService reviewService;

    @Autowired
    public ProfileController(UserService userService,
                             RecipeService recipeService,
                             CollectionService collectionService,
                             ReviewService reviewService) {
        this.userService = userService;
        this.recipeService = recipeService;
        this.collectionService = collectionService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public String showProfile(@RequestParam(value = "tab", required = false, defaultValue = "overview") String activeTab, Model model) {
        User currentUser = SecurityUtils.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        UserProfileDto profileDto = UserProfileDto.builder()
                .id(currentUser.getId())
                .name(currentUser.getName())
                .email(currentUser.getEmail())
                .build();

        List<Recipe> userRecipes = recipeService.getUserRecipes(currentUser.getId());
        List<RecipeCollection> savedCollections = collectionService.getUserCollection(currentUser.getId());
        List<Review> userReviews = reviewService.getUserReviews(currentUser.getId());

        model.addAttribute("profileDto", profileDto);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userRecipes", userRecipes);
        model.addAttribute("savedCollections", savedCollections);
        model.addAttribute("userReviews", userReviews);
        model.addAttribute("myRecipesCount", userRecipes.size());
        model.addAttribute("savedCount", savedCollections.size());
        model.addAttribute("reviewsCount", userReviews.size());
        model.addAttribute("activeTab", activeTab);

        return "user/profile";
    }

    @PostMapping("/update")
    public String updateProfile(@Valid @ModelAttribute("profileDto") UserProfileDto profileDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        User currentUser = SecurityUtils.getCurrentUser();
        if (bindingResult.hasErrors()) {
            List<Recipe> userRecipes = recipeService.getUserRecipes(currentUser.getId());
            List<RecipeCollection> savedCollections = collectionService.getUserCollection(currentUser.getId());
            List<Review> userReviews = reviewService.getUserReviews(currentUser.getId());

            model.addAttribute("currentUser", currentUser);
            model.addAttribute("userRecipes", userRecipes);
            model.addAttribute("savedCollections", savedCollections);
            model.addAttribute("userReviews", userReviews);
            model.addAttribute("myRecipesCount", userRecipes.size());
            model.addAttribute("savedCount", savedCollections.size());
            model.addAttribute("reviewsCount", userReviews.size());
            model.addAttribute("activeTab", "settings");
            return "user/profile";
        }

        try {
            User updatedUser = userService.updateProfile(currentUser.getId(), profileDto);
            SecurityUtils.updateCurrentUser(updatedUser);
            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
            return "redirect:/profile?tab=settings";
        } catch (IllegalArgumentException ex) {
            List<Recipe> userRecipes = recipeService.getUserRecipes(currentUser.getId());
            List<RecipeCollection> savedCollections = collectionService.getUserCollection(currentUser.getId());
            List<Review> userReviews = reviewService.getUserReviews(currentUser.getId());

            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("userRecipes", userRecipes);
            model.addAttribute("savedCollections", savedCollections);
            model.addAttribute("userReviews", userReviews);
            model.addAttribute("myRecipesCount", userRecipes.size());
            model.addAttribute("savedCount", savedCollections.size());
            model.addAttribute("reviewsCount", userReviews.size());
            model.addAttribute("activeTab", "settings");
            return "user/profile";
        }
    }
}

