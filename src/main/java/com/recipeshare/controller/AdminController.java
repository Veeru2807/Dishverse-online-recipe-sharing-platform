package com.recipeshare.controller;

import com.recipeshare.entity.Recipe;
import com.recipeshare.entity.User;
import com.recipeshare.enums.RecipeStatus;
import com.recipeshare.enums.Role;
import com.recipeshare.service.AdminService;
import com.recipeshare.service.RecipeService;
import com.recipeshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final RecipeService recipeService;
    private final UserService userService;

    @Autowired
    public AdminController(AdminService adminService, RecipeService recipeService, UserService userService) {
        this.adminService = adminService;
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        Map<String, Object> stats = adminService.getSystemStatistics();
        List<Recipe> pendingRecipes = recipeService.getPendingRecipes();
        model.addAttribute("stats", stats);
        model.addAttribute("pendingRecipes", pendingRecipes);
        return "admin/dashboard";
    }

    @GetMapping("/recipes")
    public String listAllRecipes(Model model) {
        List<Recipe> recipes = recipeService.getAllRecipes();
        model.addAttribute("recipes", recipes);
        return "admin/recipes";
    }

    @PostMapping("/recipes/approve/{id}")
    public String approveRecipe(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        recipeService.updateRecipeStatus(id, RecipeStatus.APPROVED);
        redirectAttributes.addFlashAttribute("successMessage", "Recipe #" + id + " has been APPROVED.");
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/recipes/reject/{id}")
    public String rejectRecipe(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        recipeService.updateRecipeStatus(id, RecipeStatus.REJECTED);
        redirectAttributes.addFlashAttribute("successMessage", "Recipe #" + id + " has been REJECTED.");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PostMapping("/users/role/{id}")
    public String changeUserRole(@PathVariable("id") Long id,
                                 @RequestParam("role") Role role,
                                 RedirectAttributes redirectAttributes) {
        userService.updateUserRole(id, role);
        redirectAttributes.addFlashAttribute("successMessage", "User role updated to " + role.name());
        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
        return "redirect:/admin/users";
    }
}
