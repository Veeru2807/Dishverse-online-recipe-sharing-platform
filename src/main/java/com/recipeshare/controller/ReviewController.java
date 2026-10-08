package com.recipeshare.controller;

import com.recipeshare.dto.ReviewDto;
import com.recipeshare.entity.User;
import com.recipeshare.service.ReviewService;
import com.recipeshare.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/rate")
    public String rateRecipe(@RequestParam("recipeId") Long recipeId,
                             @RequestParam("rating") Integer rating,
                             RedirectAttributes redirectAttributes) {
        User currentUser = SecurityUtils.getCurrentUser();
        try {
            reviewService.addOrUpdateRating(recipeId, currentUser, rating);
            redirectAttributes.addFlashAttribute("successMessage", "Rating submitted successfully!");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/recipes/details/" + recipeId;
    }

    @PostMapping("/add")
    public String addReview(@Valid ReviewDto reviewDto, RedirectAttributes redirectAttributes) {
        User currentUser = SecurityUtils.getCurrentUser();
        try {
            if (reviewDto.getRating() != null) {
                reviewService.addOrUpdateRating(reviewDto.getRecipeId(), currentUser, reviewDto.getRating());
            }
            if (reviewDto.getComment() != null && !reviewDto.getComment().trim().isEmpty()) {
                reviewService.addReview(reviewDto.getRecipeId(), currentUser, reviewDto.getComment());
            }
            redirectAttributes.addFlashAttribute("successMessage", "Review submitted successfully!");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/recipes/details/" + reviewDto.getRecipeId();
    }

    @PostMapping("/delete/{id}")
    public String deleteReview(@PathVariable("id") Long id,
                               @RequestParam("recipeId") Long recipeId,
                               RedirectAttributes redirectAttributes) {
        User currentUser = SecurityUtils.getCurrentUser();
        reviewService.deleteReview(id, currentUser);
        redirectAttributes.addFlashAttribute("successMessage", "Review deleted successfully.");
        return "redirect:/recipes/details/" + recipeId;
    }
}
