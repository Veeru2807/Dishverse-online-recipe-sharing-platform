package com.recipeshare.service;

import com.recipeshare.entity.RecipeCollection;
import com.recipeshare.entity.User;

import java.util.List;

public interface CollectionService {

    RecipeCollection addRecipeToCollection(User user, Long recipeId);

    void removeRecipeFromCollection(User user, Long recipeId);

    List<RecipeCollection> getUserCollection(Long userId);

    boolean isRecipeSavedByUser(Long userId, Long recipeId);
}
