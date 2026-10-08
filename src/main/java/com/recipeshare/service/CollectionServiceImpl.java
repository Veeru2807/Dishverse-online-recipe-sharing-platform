package com.recipeshare.service;

import com.recipeshare.entity.Recipe;
import com.recipeshare.entity.RecipeCollection;
import com.recipeshare.entity.User;
import com.recipeshare.exception.ResourceNotFoundException;
import com.recipeshare.repository.RecipeCollectionRepository;
import com.recipeshare.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    private final RecipeCollectionRepository collectionRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public CollectionServiceImpl(RecipeCollectionRepository collectionRepository, RecipeRepository recipeRepository) {
        this.collectionRepository = collectionRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public RecipeCollection addRecipeToCollection(User user, Long recipeId) {
        if (collectionRepository.existsByUserIdAndRecipeId(user.getId(), recipeId)) {
            throw new IllegalArgumentException("Recipe is already in your collection");
        }

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with ID: " + recipeId));

        RecipeCollection collection = RecipeCollection.builder()
                .user(user)
                .recipe(recipe)
                .build();

        return collectionRepository.save(collection);
    }

    @Override
    @Transactional
    public void removeRecipeFromCollection(User user, Long recipeId) {
        collectionRepository.deleteByUserIdAndRecipeId(user.getId(), recipeId);
    }

    @Override
    public List<RecipeCollection> getUserCollection(Long userId) {
        return collectionRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public boolean isRecipeSavedByUser(Long userId, Long recipeId) {
        if (userId == null) return false;
        return collectionRepository.existsByUserIdAndRecipeId(userId, recipeId);
    }
}
