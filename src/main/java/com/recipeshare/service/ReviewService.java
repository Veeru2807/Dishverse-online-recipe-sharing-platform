package com.recipeshare.service;

import com.recipeshare.entity.Rating;
import com.recipeshare.entity.Review;
import com.recipeshare.entity.User;

import java.util.List;

public interface ReviewService {

    Rating addOrUpdateRating(Long recipeId, User user, Integer score);

    Review addReview(Long recipeId, User user, String comment);

    List<Review> getRecipeReviews(Long recipeId);

    Double getAverageRating(Long recipeId);

    Integer getUserRatingForRecipe(Long recipeId, Long userId);

    void deleteReview(Long reviewId, User user);
}
