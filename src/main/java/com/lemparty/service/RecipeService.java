package com.lemparty.service;

import com.lemparty.data.MongoRecipeDAO;
import com.lemparty.entity.Recipe;
import com.lemparty.exception.InvalidRecipeException;
import com.lemparty.exception.RecipeAlreadyExistsException;
import com.lemparty.exception.RecipeDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class RecipeService {

    @Autowired
    private MongoRecipeDAO recipeDAO;

    public Recipe create(Recipe recipe) throws RecipeAlreadyExistsException {

        recipe.setRecipeID(UUID.randomUUID().toString());

        boolean recipeCreated = recipeDAO.create(recipe);

        if(recipeCreated)
            return recipe;
        else
            return null; //TODO do proper error handling
    }

    public Recipe update(Recipe recipe) throws RecipeDoesNotExistException {

        boolean updated = recipeDAO.update(recipe);

        if(updated)
            return recipe;
        else
            return null;
    }

    public Recipe get(String name, String userID) {
        Recipe obtainedRecipe = recipeDAO.getRecipeByNameAndUserID(name, userID);

        return obtainedRecipe;
    }

    public Recipe getRecipeByRecipeID(String recipeID) {
        Recipe obtainedRecipe = recipeDAO.getRecipeByRecipeID(recipeID);

        return obtainedRecipe;
    }

    public List<Recipe> getRecipeByUserID(String userID) {
        List<Recipe> obtainedRecipe = recipeDAO.getRecipesByUserID(userID);

        return obtainedRecipe;
    }

}
