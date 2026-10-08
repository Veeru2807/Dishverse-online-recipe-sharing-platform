package com.recipeshare.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "ratings",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_rating_user_recipe", columnNames = {"recipe_id", "user_id"})
    }
)
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer rating;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Rating() {
    }

    public Rating(Long id, Recipe recipe, User user, Integer rating, LocalDateTime createdAt) {
        this.id = id;
        this.recipe = recipe;
        this.user = user;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static RatingBuilder builder() {
        return new RatingBuilder();
    }

    public static class RatingBuilder {
        private Long id;
        private Recipe recipe;
        private User user;
        private Integer rating;

        public RatingBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RatingBuilder recipe(Recipe recipe) {
            this.recipe = recipe;
            return this;
        }

        public RatingBuilder user(User user) {
            this.user = user;
            return this;
        }

        public RatingBuilder rating(Integer rating) {
            this.rating = rating;
            return this;
        }

        public Rating build() {
            Rating r = new Rating();
            r.setId(this.id);
            r.setRecipe(this.recipe);
            r.setUser(this.user);
            r.setRating(this.rating);
            return r;
        }
    }
}
