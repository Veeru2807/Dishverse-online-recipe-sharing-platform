package com.recipeshare.service;

import com.recipeshare.enums.RecipeStatus;
import com.recipeshare.enums.Role;
import com.recipeshare.repository.RecipeRepository;
import com.recipeshare.repository.ReviewRepository;
import com.recipeshare.repository.UserRepository;
import com.recipeshare.util.JdbcDatabaseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final ReviewRepository reviewRepository;
    private final JdbcDatabaseHelper jdbcDatabaseHelper;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository,
                            RecipeRepository recipeRepository,
                            ReviewRepository reviewRepository,
                            JdbcDatabaseHelper jdbcDatabaseHelper) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.reviewRepository = reviewRepository;
        this.jdbcDatabaseHelper = jdbcDatabaseHelper;
    }

    @Override
    public Map<String, Object> getSystemStatistics() {
        Map<String, Object> stats = new HashMap<>();

        long totalUsers = userRepository.count();
        long totalAdminUsers = userRepository.countByRole(Role.ROLE_ADMIN);
        long totalRecipes = recipeRepository.count();
        long pendingRecipes = recipeRepository.countByStatus(RecipeStatus.PENDING);
        long approvedRecipes = recipeRepository.countByStatus(RecipeStatus.APPROVED);
        long rejectedRecipes = recipeRepository.countByStatus(RecipeStatus.REJECTED);
        long totalReviews = reviewRepository.count();

        // Native JDBC Verification Stat Execution
        long jdbcApprovedCount = jdbcDatabaseHelper.getApprovedRecipeCountViaJdbc();

        stats.put("totalUsers", totalUsers);
        stats.put("totalAdminUsers", totalAdminUsers);
        stats.put("totalRecipes", totalRecipes);
        stats.put("pendingRecipes", pendingRecipes);
        stats.put("approvedRecipes", approvedRecipes);
        stats.put("rejectedRecipes", rejectedRecipes);
        stats.put("totalReviews", totalReviews);
        stats.put("jdbcApprovedRecipes", jdbcApprovedCount);

        return stats;
    }
}
