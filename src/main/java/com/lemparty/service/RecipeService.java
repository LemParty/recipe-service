package com.lemparty.service;

import com.lemparty.data.MongoRecipeDAO;
import com.lemparty.entity.Recipe;
import com.lemparty.exception.InvalidRecipeException;
import com.lemparty.exception.RecipeAlreadyExistsException;
import com.lemparty.exception.RecipeDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;

public class RecipeService {

    @Autowired
    private MongoRecipeDAO recipeDAO;

    public Recipe create(Recipe recipe) throws RecipeAlreadyExistsException {

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

}
