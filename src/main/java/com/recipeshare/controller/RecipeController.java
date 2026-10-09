package com.recipeshare.controller;

import com.recipeshare.dto.RecipeDto;
import com.recipeshare.dto.ReviewDto;
import com.recipeshare.entity.Recipe;
import com.recipeshare.entity.Review;
import com.recipeshare.entity.User;
import com.recipeshare.service.CollectionService;
import com.recipeshare.service.RecipeService;
import com.recipeshare.service.ReviewService;
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
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final ReviewService reviewService;
    private final CollectionService collectionService;

    @Autowired
    public RecipeController(RecipeService recipeService, ReviewService reviewService, CollectionService collectionService) {
        this.recipeService = recipeService;
        this.reviewService = reviewService;
        this.collectionService = collectionService;
    }

    @GetMapping
    public String listRecipes(@RequestParam(value = "query", required = false) String query, Model model) {
        List<Recipe> recipes = recipeService.searchApprovedRecipes(query);
        model.addAttribute("recipes", recipes);
        model.addAttribute("searchQuery", query);
        return "recipe/list";
    }

    @GetMapping("/details/{id}")
    public String viewRecipeDetails(@PathVariable("id") Long id, Model model) {
        Recipe recipe = recipeService.getApprovedRecipeById(id);
        List<Review> reviews = reviewService.getRecipeReviews(id);
        Double avgRating = reviewService.getAverageRating(id);

        User currentUser = SecurityUtils.getCurrentUser();
        boolean isSaved = false;
        Integer userRating = null;

        if (currentUser != null) {
            isSaved = collectionService.isRecipeSavedByUser(currentUser.getId(), id);
            userRating = reviewService.getUserRatingForRecipe(id, currentUser.getId());
        }

        model.addAttribute("recipe", recipe);
        model.addAttribute("reviews", reviews);
        model.addAttribute("avgRating", avgRating);
        model.addAttribute("isSaved", isSaved);
        model.addAttribute("userRating", userRating);
        model.addAttribute("reviewDto", new ReviewDto());
        return "recipe/details";
    }

    @GetMapping("/create")
    public String showCreateRecipeForm(Model model) {
        RecipeDto recipeDto = new RecipeDto();
        model.addAttribute("recipeDto", recipeDto);
        model.addAttribute("recipe", recipeDto);
        return "recipe/create";
    }

    @PostMapping("/create")
    public String createRecipe(@Valid @ModelAttribute("recipeDto") RecipeDto recipeDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("recipe", recipeDto);
            return "recipe/create";
        }

        User currentUser = SecurityUtils.getCurrentUser();
        recipeService.createRecipe(recipeDto, currentUser);
        redirectAttributes.addFlashAttribute("successMessage", "Recipe submitted successfully! It is now pending admin approval.");
        return "redirect:/recipes/my-recipes";
    }

    @GetMapping("/my-recipes")
    public String showMyRecipes(Model model) {
        User currentUser = SecurityUtils.getCurrentUser();
        List<Recipe> recipes = recipeService.getUserRecipes(currentUser.getId());
        model.addAttribute("recipes", recipes);
        return "recipe/my-recipes";
    }

    @GetMapping("/edit/{id}")
    public String showEditRecipeForm(@PathVariable("id") Long id, Model model) {
        User currentUser = SecurityUtils.getCurrentUser();
        Recipe recipe = recipeService.getRecipeById(id);

        RecipeDto recipeDto = RecipeDto.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .ingredients(recipe.getIngredients())
                .instructions(recipe.getInstructions())
                .existingImageUrl(recipe.getImageUrl())
                .status(recipe.getStatus())
                .build();

        model.addAttribute("recipeDto", recipeDto);
        model.addAttribute("recipe", recipeDto);
        return "recipe/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateRecipe(@PathVariable("id") Long id,
                               @Valid @ModelAttribute("recipeDto") RecipeDto recipeDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (bindingResult.hasErrors()) {
            recipeDto.setId(id);
            model.addAttribute("recipe", recipeDto);
            return "recipe/edit";
        }

        User currentUser = SecurityUtils.getCurrentUser();
        recipeService.updateRecipe(id, recipeDto, currentUser);
        redirectAttributes.addFlashAttribute("successMessage", "Recipe updated successfully!");
        return "redirect:/recipes/my-recipes";
    }

    @PostMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        User currentUser = SecurityUtils.getCurrentUser();
        recipeService.deleteRecipe(id, currentUser);
        redirectAttributes.addFlashAttribute("successMessage", "Recipe deleted successfully!");
        return "redirect:/recipes/my-recipes";
    }
}
