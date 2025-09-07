package com.recipebook.controller;

import com.recipebook.model.Recipe;
import com.recipebook.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	// ✅ Get all recipes
	@GetMapping("/recipes")
	public List<Recipe> getAllRecipes() {
		return recipeService.getAllRecipes();
	}

	// ✅ Upload new recipe (JSON only)
	@PostMapping(value = "/recipes", consumes = "application/json")
	public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
		Recipe savedRecipe = recipeService.addRecipe(recipe);

		System.out.println("uploading recpie:::::::" + recipe.toString());

		return ResponseEntity.ok(savedRecipe);
	}

	// ✅ Get by ID
	@GetMapping("/recipes/{id}")
	public Recipe getRecipeById(@PathVariable String id) {

		System.out.println("getRecipeById:::::::" + id);

		return recipeService.getRecipeById(id);
	}
	
	// ✅ Get by email ID
		@GetMapping("/getrecipesByEmailId/{emailId}")
		public List<Recipe> getRecipeByUserEmailId(@PathVariable String emailId) {

			System.out.println("getrecipesByEmailId:::::::" + emailId);

			return recipeService.getRecipesByUserEmail(emailId);
		}

	// ✅ Delete recipe
	@DeleteMapping("/recipes/{id}")
	public ResponseEntity<String> deleteRecipe(@PathVariable String id) {
		recipeService.deleteRecipe(id);
		return ResponseEntity.ok("Recipe deleted successfully");
	}

	// ✅ Rate a recipe
	@PostMapping("/recipes/{id}/rate")
	public ResponseEntity<Recipe> rateRecipe(@PathVariable String id, @RequestParam Double rating,
			@RequestParam String userEmail) {
		System.out.println("id:::::::" + id);
		System.out.println("rating:::::::" + rating);
		System.out.println("userEmail:::::::" + userEmail);
		Recipe updatedRecipe = recipeService.rateRecipe(id, rating, userEmail);
		return ResponseEntity.ok(updatedRecipe);
	}
}