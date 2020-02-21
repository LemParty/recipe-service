package com.lemparty.service;

import com.lemparty.data.MongoRecipeDAO;
import com.lemparty.entity.Recipe;
import com.lemparty.exception.InvalidRecipeException;
import org.springframework.beans.factory.annotation.Autowired;

public class RecipeService {

    @Autowired
    private MongoRecipeDAO recipeDAO;

    public Recipe saveProfile(Recipe recipe) throws InvalidRecipeException {

        boolean recipeCreated = recipeDAO.create(recipe);

        if(recipeCreated)
            return recipe;
        else
            return null; //TODO do proper error handling
    }

}
