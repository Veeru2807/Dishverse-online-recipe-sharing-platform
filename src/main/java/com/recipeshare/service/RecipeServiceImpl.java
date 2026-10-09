package com.recipeshare.service;

import com.recipeshare.dto.RecipeDto;
import com.recipeshare.entity.Recipe;
import com.recipeshare.entity.User;
import com.recipeshare.enums.RecipeStatus;
import com.recipeshare.enums.Role;
import com.recipeshare.exception.ResourceNotFoundException;
import com.recipeshare.exception.UnauthorizedAccessException;
import com.recipeshare.repository.RecipeRepository;
import com.recipeshare.repository.UserRepository;
import com.recipeshare.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Value("${app.upload.dir:uploads/recipes/}")
    private String uploadDir;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Recipe createRecipe(RecipeDto recipeDto, User user) {
        if (user == null || user.getId() == null) {
            throw new UnauthorizedAccessException("You must be logged in to create a recipe");
        }

        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + user.getId()));

        String fileName = null;
        if (recipeDto.getImageFile() != null && !recipeDto.getImageFile().isEmpty()) {
            try {
                fileName = FileUploadUtil.saveFile(uploadDir, recipeDto.getImageFile());
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image file: " + e.getMessage(), e);
            }
        }

        Recipe recipe = Recipe.builder()
                .title(recipeDto.getTitle())
                .description(recipeDto.getDescription())
                .ingredients(recipeDto.getIngredients())
                .instructions(recipeDto.getInstructions())
                .imageUrl(fileName)
                .status(RecipeStatus.PENDING)
                .user(managedUser)
                .build();

        return recipeRepository.save(recipe);
    }


    @Override
    @Transactional
    public Recipe updateRecipe(Long recipeId, RecipeDto recipeDto, User user) {
        Recipe recipe = getRecipeById(recipeId);

        if (!recipe.getUser().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedAccessException("You are not authorized to edit this recipe");
        }

        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setIngredients(recipeDto.getIngredients());
        recipe.setInstructions(recipeDto.getInstructions());

        if (recipeDto.getImageFile() != null && !recipeDto.getImageFile().isEmpty()) {
            try {
                String fileName = FileUploadUtil.saveFile(uploadDir, recipeDto.getImageFile());
                recipe.setImageUrl(fileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to update image file: " + e.getMessage(), e);
            }
        }

        // Re-submit for pending status if edited by non-admin
        if (user.getRole() != Role.ROLE_ADMIN) {
            recipe.setStatus(RecipeStatus.PENDING);
        }

        return recipeRepository.save(recipe);
    }

    @Override
    @Transactional
    public void deleteRecipe(Long recipeId, User user) {
        Recipe recipe = getRecipeById(recipeId);

        if (!recipe.getUser().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedAccessException("You are not authorized to delete this recipe");
        }

        recipeRepository.delete(recipe);
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with ID: " + id));
    }

    @Override
    public Recipe getApprovedRecipeById(Long id) {
        return recipeRepository.findByIdAndStatus(id, RecipeStatus.APPROVED)
                .orElseThrow(() -> new ResourceNotFoundException("Approved recipe not found with ID: " + id));
    }

    @Override
    public List<Recipe> getApprovedRecipes() {
        return recipeRepository.findByStatus(RecipeStatus.APPROVED);
    }

    @Override
    public List<Recipe> getPendingRecipes() {
        return recipeRepository.findByStatus(RecipeStatus.PENDING);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> getUserRecipes(Long userId) {
        return recipeRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<Recipe> searchApprovedRecipes(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getApprovedRecipes();
        }
        return recipeRepository.searchApprovedRecipes(RecipeStatus.APPROVED, query.trim());
    }

    @Override
    @Transactional
    public Recipe updateRecipeStatus(Long recipeId, RecipeStatus status) {
        Recipe recipe = getRecipeById(recipeId);
        recipe.setStatus(status);
        return recipeRepository.save(recipe);
    }
}
