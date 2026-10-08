package com.recipeshare.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "recipe_collections",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_collection_user_recipe", columnNames = {"user_id", "recipe_id"})
    }
)
public class RecipeCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public RecipeCollection() {
    }

    public RecipeCollection(Long id, User user, Recipe recipe, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.recipe = recipe;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static RecipeCollectionBuilder builder() {
        return new RecipeCollectionBuilder();
    }

    public static class RecipeCollectionBuilder {
        private Long id;
        private User user;
        private Recipe recipe;

        public RecipeCollectionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RecipeCollectionBuilder user(User user) {
            this.user = user;
            return this;
        }

        public RecipeCollectionBuilder recipe(Recipe recipe) {
            this.recipe = recipe;
            return this;
        }

        public RecipeCollection build() {
            RecipeCollection rc = new RecipeCollection();
            rc.setId(this.id);
            rc.setUser(this.user);
            rc.setRecipe(this.recipe);
            return rc;
        }
    }
}
