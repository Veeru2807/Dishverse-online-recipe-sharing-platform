package com.recipeshare.repository;

import com.recipeshare.entity.Recipe;
import com.recipeshare.enums.RecipeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByStatus(RecipeStatus status);

    List<Recipe> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<Recipe> findByIdAndStatus(Long id, RecipeStatus status);

    long countByStatus(RecipeStatus status);

    boolean existsByTitle(String title);

    Optional<Recipe> findByTitle(String title);

    @Query("SELECT r FROM Recipe r WHERE r.status = :status AND " +
           "(LOWER(r.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.ingredients) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Recipe> searchApprovedRecipes(@Param("status") RecipeStatus status, @Param("query") String query);
}
