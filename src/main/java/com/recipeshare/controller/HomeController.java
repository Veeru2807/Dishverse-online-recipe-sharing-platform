package com.recipeshare.controller;

import com.recipeshare.entity.Recipe;
import com.recipeshare.entity.User;
import com.recipeshare.service.RecipeService;
import com.recipeshare.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final RecipeService recipeService;

    @Autowired
    public HomeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Recipe> approvedRecipes = recipeService.getApprovedRecipes();
        model.addAttribute("recipes", approvedRecipes);
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User currentUser = SecurityUtils.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (SecurityUtils.isAdmin()) {
            return "redirect:/admin/dashboard";
        }

        List<Recipe> userRecipes = recipeService.getUserRecipes(currentUser.getId());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userRecipes", userRecipes);
        return "user/dashboard";
    }
}
