package com.recipeshare.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Direct JDBC Database Helper Component.
 * Demonstrates native java.sql (Connection, DriverManager, PreparedStatement, ResultSet)
 * integration alongside Spring Data JPA in the DishVerse application.
 */
@Component
public class JdbcDatabaseHelper {

    @Value("${spring.datasource.url:jdbc:mysql://localhost:3306/recipe_sharing_db}")
    private String dbUrl;

    @Value("${spring.datasource.username:root}")
    private String dbUsername;

    @Value("${spring.datasource.password:}")
    private String dbPassword;

    /**
     * Executes a raw JDBC query to count approved recipes.
     */
    public long getApprovedRecipeCountViaJdbc() {
        String sql = "SELECT COUNT(*) FROM recipes WHERE status = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "APPROVED");
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("JDBC Execution Error [getApprovedRecipeCountViaJdbc]: " + e.getMessage());
        }
        return 0L;
    }

    /**
     * Executes a raw JDBC query to fetch approved public recipes for JSON export.
     */
    public List<Map<String, Object>> fetchApprovedRecipesForExport() {
        List<Map<String, Object>> recipes = new ArrayList<>();
        String sql = "SELECT id, title, description, ingredients, instructions, image_url, created_at FROM recipes WHERE status = ? ORDER BY id ASC";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "APPROVED");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> recipe = new HashMap<>();
                    recipe.put("id", rs.getLong("id"));
                    recipe.put("title", rs.getString("title"));
                    recipe.put("description", rs.getString("description"));
                    recipe.put("ingredients", rs.getString("ingredients"));
                    recipe.put("instructions", rs.getString("instructions"));
                    recipe.put("imageUrl", rs.getString("image_url"));
                    recipe.put("createdAt", rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toString() : null);
                    recipes.add(recipe);
                }
            }
        } catch (SQLException e) {
            System.err.println("JDBC Execution Error [fetchApprovedRecipesForExport]: " + e.getMessage());
        }
        return recipes;
    }
}
