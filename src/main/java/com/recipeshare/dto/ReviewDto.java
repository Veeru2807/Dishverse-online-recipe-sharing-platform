package com.recipeshare.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewDto {

    @NotNull(message = "Recipe ID is required")
    private Long recipeId;

    @Min(value = 1, message = "Rating must be at least 1 star")
    @Max(value = 5, message = "Rating cannot exceed 5 stars")
    private Integer rating;

    @NotBlank(message = "Review comment cannot be empty")
    private String comment;

    public ReviewDto() {
    }

    public ReviewDto(Long recipeId, Integer rating, String comment) {
        this.recipeId = recipeId;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static ReviewDtoBuilder builder() {
        return new ReviewDtoBuilder();
    }

    public static class ReviewDtoBuilder {
        private Long recipeId;
        private Integer rating;
        private String comment;

        public ReviewDtoBuilder recipeId(Long recipeId) {
            this.recipeId = recipeId;
            return this;
        }

        public ReviewDtoBuilder rating(Integer rating) {
            this.rating = rating;
            return this;
        }

        public ReviewDtoBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public ReviewDto build() {
            return new ReviewDto(recipeId, rating, comment);
        }
    }
}
