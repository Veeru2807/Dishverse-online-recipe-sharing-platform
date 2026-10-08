package com.recipeshare.dto;

import com.recipeshare.enums.RecipeStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class RecipeDto {

    private Long id;

    @NotBlank(message = "Recipe title is required")
    @Size(min = 3, max = 150, message = "Title must be between 3 and 150 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Ingredients are required")
    private String ingredients;

    @NotBlank(message = "Preparation instructions are required")
    private String instructions;

    private MultipartFile imageFile;

    private String existingImageUrl;

    private RecipeStatus status;

    public RecipeDto() {
    }

    public RecipeDto(Long id, String title, String description, String ingredients, String instructions, MultipartFile imageFile, String existingImageUrl, RecipeStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.imageFile = imageFile;
        this.existingImageUrl = existingImageUrl;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getExistingImageUrl() {
        return existingImageUrl;
    }

    public void setExistingImageUrl(String existingImageUrl) {
        this.existingImageUrl = existingImageUrl;
    }

    public RecipeStatus getStatus() {
        return status;
    }

    public void setStatus(RecipeStatus status) {
        this.status = status;
    }

    public static RecipeDtoBuilder builder() {
        return new RecipeDtoBuilder();
    }

    public static class RecipeDtoBuilder {
        private Long id;
        private String title;
        private String description;
        private String ingredients;
        private String instructions;
        private MultipartFile imageFile;
        private String existingImageUrl;
        private RecipeStatus status;

        public RecipeDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RecipeDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public RecipeDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public RecipeDtoBuilder ingredients(String ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public RecipeDtoBuilder instructions(String instructions) {
            this.instructions = instructions;
            return this;
        }

        public RecipeDtoBuilder imageFile(MultipartFile imageFile) {
            this.imageFile = imageFile;
            return this;
        }

        public RecipeDtoBuilder existingImageUrl(String existingImageUrl) {
            this.existingImageUrl = existingImageUrl;
            return this;
        }

        public RecipeDtoBuilder status(RecipeStatus status) {
            this.status = status;
            return this;
        }

        public RecipeDto build() {
            return new RecipeDto(id, title, description, ingredients, instructions, imageFile, existingImageUrl, status);
        }
    }
}
