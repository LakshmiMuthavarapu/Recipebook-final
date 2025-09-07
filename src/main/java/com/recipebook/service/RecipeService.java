package com.recipebook.service;

import com.recipebook.model.Recipe;
import com.recipebook.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    // ✅ Get all recipes
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    // ✅ Save recipe (direct JSON object)
    public Recipe addRecipe(Recipe recipe) {
        // Ensure safe defaults
        if (recipe.getIngredients() == null) recipe.setIngredients(new ArrayList<>());
        if (recipe.getSteps() == null) recipe.setSteps(new ArrayList<>());
        if (recipe.getRatings() == null) recipe.setRatings(new ArrayList<>());

        // Always start with 0 rating if not set
        recipe.setAverageRating(0.0);

        return recipeRepository.save(recipe);
    }

    // ✅ Get recipe by ID
    public Recipe getRecipeById(String id) {
        return recipeRepository.findById(id).orElse(null);
    }

    // ✅ Delete recipe
    public void deleteRecipe(String id) {
        recipeRepository.deleteById(id);
    }

    // ✅ Allow others to rate a recipe
    public Recipe rateRecipe(String id, double newRating, String userEmail) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        if (!Objects.isNull(recipe.getAuthorEmail()) && recipe.getAuthorEmail().equalsIgnoreCase(userEmail)) {
            throw new RuntimeException("Uploader cannot rate their own recipe");
        }

        recipe.addRating(newRating); // ✅ model handles recalculating average
        return recipeRepository.save(recipe);
    }

    // ✅ Calculate average rating (reusable)
    public double calculateAverageRating(Recipe recipe) {
        if (recipe.getRatings() == null || recipe.getRatings().isEmpty()) {
            return 0.0;
        }
        return recipe.getRatings().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    // ✅ Get recipes with minimum average rating
    public List<Recipe> getRecipesWithMinRating(double minRating) {
        return recipeRepository.findAll().stream()
                .filter(recipe -> calculateAverageRating(recipe) >= minRating)
                .toList();
    }
    
    // ✅ Get recipes by email id of user
    public List<Recipe> getRecipesByUserEmail(String userEmail) {
        return recipeRepository.findByAuthorEmail(userEmail);
    }
}