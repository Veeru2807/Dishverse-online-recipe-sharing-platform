package com.recipeshare.repository;

import com.recipeshare.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByRecipeIdOrderByCreatedAtDesc(Long recipeId);

    long countByRecipeId(Long recipeId);
}
