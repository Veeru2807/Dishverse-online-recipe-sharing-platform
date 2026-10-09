package com.recipeshare.service;

import com.recipeshare.entity.Recipe;
import com.recipeshare.entity.RecipeCollection;
import com.recipeshare.entity.User;
import com.recipeshare.exception.ResourceNotFoundException;
import com.recipeshare.repository.RecipeCollectionRepository;
import com.recipeshare.repository.RecipeRepository;
import com.recipeshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    private final RecipeCollectionRepository collectionRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Autowired
    public CollectionServiceImpl(RecipeCollectionRepository collectionRepository,
                                  RecipeRepository recipeRepository,
                                  UserRepository userRepository) {
        this.collectionRepository = collectionRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public RecipeCollection addRecipeToCollection(User user, Long recipeId) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User must be logged in to bookmark recipes");
        }

        if (collectionRepository.existsByUserIdAndRecipeId(user.getId(), recipeId)) {
            throw new IllegalArgumentException("Recipe is already in your collection");
        }

        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + user.getId()));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with ID: " + recipeId));

        RecipeCollection collection = RecipeCollection.builder()
                .user(managedUser)
                .recipe(recipe)
                .build();

        return collectionRepository.save(collection);
    }

    @Override
    @Transactional
    public void removeRecipeFromCollection(User user, Long recipeId) {
        if (user != null && user.getId() != null) {
            collectionRepository.deleteByUserIdAndRecipeId(user.getId(), recipeId);
        }
    }

    @Override
    public List<RecipeCollection> getUserCollection(Long userId) {
        if (userId == null) return List.of();
        return collectionRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public boolean isRecipeSavedByUser(Long userId, Long recipeId) {
        if (userId == null || recipeId == null) return false;
        return collectionRepository.existsByUserIdAndRecipeId(userId, recipeId);
    }
}

