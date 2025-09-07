package com.recipebook.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "recipes")
public class Recipe {

    @Id
    private String id;

    private String title;
    private String description;
    private List<String> ingredients = new ArrayList<>();
    private List<String> steps = new ArrayList<>();
    private String category;
    private String imageUrl;

    // ✅ Store all ratings as doubles
    private List<Double> ratings = new ArrayList<>();

    // ✅ Store average rating
    private double averageRating = 0.0;

    // ✅ Link recipe to user who uploaded it
    private String authorEmail;

    // ---------- Constructors ----------
    public Recipe() {}

    public Recipe(String title, String description, List<String> ingredients, List<String> steps,
                  String category, String imageUrl, String authorEmail) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients != null ? ingredients : new ArrayList<>();
        this.steps = steps != null ? steps : new ArrayList<>();
        this.category = category;
        this.imageUrl = imageUrl;
        this.authorEmail = authorEmail;
        this.ratings = new ArrayList<>();
        this.averageRating = 0.0;
    }

    // ---------- Getters & Setters ----------
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getIngredients() { return ingredients; }
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients != null ? ingredients : new ArrayList<>();
    }

    public List<String> getSteps() { return steps; }
    public void setSteps(List<String> steps) {
        this.steps = steps != null ? steps : new ArrayList<>();
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<Double> getRatings() { return ratings; }
    public void setRatings(List<Double> ratings) {
        this.ratings = ratings != null ? ratings : new ArrayList<>();
        recalculateAverageRating();
    }

    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }

    public String getAuthorEmail() { return authorEmail; }
    public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }

    // ---------- Utility Methods ----------
    public void addRating(double newRating) {
        if (newRating >= 0 && newRating <= 5) { // ✅ validation
            this.ratings.add(newRating);
            recalculateAverageRating();
        }
    }

    private void recalculateAverageRating() {
        this.averageRating = this.ratings.stream()
                                         .mapToDouble(Double::doubleValue)
                                         .average()
                                         .orElse(0.0);
    }
}
