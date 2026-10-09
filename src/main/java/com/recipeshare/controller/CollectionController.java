package com.recipeshare.controller;

import com.recipeshare.entity.RecipeCollection;
import com.recipeshare.entity.User;
import com.recipeshare.service.CollectionService;
import com.recipeshare.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/collections")
public class CollectionController {

    private final CollectionService collectionService;

    @Autowired
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    public String viewUserCollection(Model model) {
        User currentUser = SecurityUtils.getCurrentUser();
        List<RecipeCollection> collection = collectionService.getUserCollection(currentUser.getId());
        model.addAttribute("collections", collection);
        model.addAttribute("collection", collection);
        return "collection/list";
    }

    @PostMapping("/add/{recipeId}")
    public String addRecipeToCollection(@PathVariable("recipeId") Long recipeId, RedirectAttributes redirectAttributes) {
        User currentUser = SecurityUtils.getCurrentUser();
        try {
            collectionService.addRecipeToCollection(currentUser, recipeId);
            redirectAttributes.addFlashAttribute("successMessage", "Recipe added to your saved collection!");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/recipes/details/" + recipeId;
    }

    @PostMapping("/remove/{recipeId}")
    public String removeRecipeFromCollection(@PathVariable("recipeId") Long recipeId, RedirectAttributes redirectAttributes) {
        User currentUser = SecurityUtils.getCurrentUser();
        collectionService.removeRecipeFromCollection(currentUser, recipeId);
        redirectAttributes.addFlashAttribute("successMessage", "Recipe removed from your collection.");
        return "redirect:/collections";
    }
}
