package com.recipeshare.service;

import com.recipeshare.dto.RecipeDto;
import com.recipeshare.entity.Recipe;
import com.recipeshare.entity.User;
import com.recipeshare.enums.RecipeStatus;

import java.util.List;

public interface RecipeService {

    Recipe createRecipe(RecipeDto recipeDto, User user);

    Recipe updateRecipe(Long recipeId, RecipeDto recipeDto, User user);

    void deleteRecipe(Long recipeId, User user);

    Recipe getRecipeById(Long id);

    Recipe getApprovedRecipeById(Long id);

    List<Recipe> getApprovedRecipes();

    List<Recipe> getPendingRecipes();

    List<Recipe> getAllRecipes();

    List<Recipe> getUserRecipes(Long userId);

    List<Recipe> searchApprovedRecipes(String query);

    Recipe updateRecipeStatus(Long recipeId, RecipeStatus status);
}
