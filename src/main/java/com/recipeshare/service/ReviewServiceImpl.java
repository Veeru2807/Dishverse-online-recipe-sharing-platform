package com.recipeshare.service;

import com.recipeshare.entity.Rating;
import com.recipeshare.entity.Recipe;
import com.recipeshare.entity.Review;
import com.recipeshare.entity.User;
import com.recipeshare.enums.Role;
import com.recipeshare.exception.ResourceNotFoundException;
import com.recipeshare.exception.UnauthorizedAccessException;
import com.recipeshare.repository.RatingRepository;
import com.recipeshare.repository.RecipeRepository;
import com.recipeshare.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final RatingRepository ratingRepository;
    private final ReviewRepository reviewRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public ReviewServiceImpl(RatingRepository ratingRepository, ReviewRepository reviewRepository, RecipeRepository recipeRepository) {
        this.ratingRepository = ratingRepository;
        this.reviewRepository = reviewRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public Rating addOrUpdateRating(Long recipeId, User user, Integer score) {
        if (score == null || score < 1 || score > 5) {
            throw new IllegalArgumentException("Rating score must be between 1 and 5 stars");
        }

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with ID: " + recipeId));

        Rating rating = ratingRepository.findByRecipeIdAndUserId(recipeId, user.getId())
                .orElse(Rating.builder().recipe(recipe).user(user).build());

        rating.setRating(score);
        return ratingRepository.save(rating);
    }

    @Override
    @Transactional
    public Review addReview(Long recipeId, User user, String comment) {
        if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("Review comment cannot be empty");
        }

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with ID: " + recipeId));

        Review review = Review.builder()
                .recipe(recipe)
                .user(user)
                .comment(comment.trim())
                .build();

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getRecipeReviews(Long recipeId) {
        return reviewRepository.findByRecipeIdOrderByCreatedAtDesc(recipeId);
    }

    @Override
    public Double getAverageRating(Long recipeId) {
        Double avg = ratingRepository.getAverageRatingByRecipeId(recipeId);
        return avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0;
    }

    @Override
    public Integer getUserRatingForRecipe(Long recipeId, Long userId) {
        if (userId == null) return null;
        return ratingRepository.findByRecipeIdAndUserId(recipeId, userId)
                .map(Rating::getRating)
                .orElse(null);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId, User user) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + reviewId));

        if (!review.getUser().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedAccessException("You are not authorized to delete this review");
        }

        reviewRepository.delete(review);
    }
}
