package com.example.recify;

public class Recipe {
    String recipeName;
    int recipeImage;
    String recipeDescription;
    String nutrition;
    String[] ingredients;
    String[] quantities;
    int ingredientCount;
    String procedure;
    int matchCount;
    double matchPercentage;

    public Recipe(String recipeName, int recipeImage, String[] ingredients, String[] quantities, String recipeDescription, String nutrition, String procedure) {
        this.recipeName = recipeName;
        this.recipeImage = recipeImage;
        this.ingredients = ingredients;
        this.quantities = quantities;
        ingredientCount = ingredients.length;
        this.recipeDescription = recipeDescription;
        this.nutrition = nutrition;
        this.procedure = procedure;

        matchCount = 0;
        matchPercentage = 0;
    }
}
