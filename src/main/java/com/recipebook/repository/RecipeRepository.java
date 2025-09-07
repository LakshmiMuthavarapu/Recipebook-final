package com.recipebook.repository;

import com.recipebook.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {

    // ✅ Find all recipes created by a specific user
    List<Recipe> findByAuthorEmail(String authorEmail);

    // ✅ Find recipes by category (Breakfast, Lunch, Dinner, etc.)
    List<Recipe> findByCategoryIgnoreCase(String category);

    // ✅ Search recipes by title (case-insensitive)
    List<Recipe> findByTitleContainingIgnoreCase(String title);

    // ✅ Search recipes by ingredient (case-insensitive, matches if any ingredient contains keyword)
    List<Recipe> findByIngredientsContainingIgnoreCase(String ingredient);

    // ✅ Combined search (title OR ingredient)
    List<Recipe> findByTitleContainingIgnoreCaseOrIngredientsContainingIgnoreCase(String title, String ingredient);
}