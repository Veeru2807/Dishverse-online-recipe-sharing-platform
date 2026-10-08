package com.recipeshare.repository;

import com.recipeshare.entity.RecipeCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeCollectionRepository extends JpaRepository<RecipeCollection, Long> {

    List<RecipeCollection> findByUserIdOrderByCreatedAtDesc(Long userId);

    boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);

    Optional<RecipeCollection> findByUserIdAndRecipeId(Long userId, Long recipeId);

    void deleteByUserIdAndRecipeId(Long userId, Long recipeId);
}
