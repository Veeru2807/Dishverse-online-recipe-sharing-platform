package com.recipeshare.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipeshare.util.JdbcDatabaseHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Native Jakarta HttpServlet implementation.
 * Exposes approved public DishVerse recipes at /api/recipes/export using direct JDBC query data.
 */
@WebServlet(name = "RecipeExportServlet", urlPatterns = "/api/recipes/export")
public class RecipeExportServlet extends HttpServlet {

    private final JdbcDatabaseHelper jdbcDatabaseHelper;
    private final ObjectMapper objectMapper;

    @Autowired
    public RecipeExportServlet(JdbcDatabaseHelper jdbcDatabaseHelper) {
        this.jdbcDatabaseHelper = jdbcDatabaseHelper;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<Map<String, Object>> approvedRecipes = jdbcDatabaseHelper.fetchApprovedRecipesForExport();
            
            PrintWriter out = resp.getWriter();
            String jsonOutput = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(approvedRecipes);
            out.print(jsonOutput);
            out.flush();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print("{\"error\":\"Failed to export recipe data via Servlet: " + e.getMessage() + "\"}");
        }
    }
}
