package com.lemparty.service;

import com.lemparty.data.MongoRecipeDAO;
import com.lemparty.data.MongoRecipeRepository;
import com.lemparty.entity.Recipe;
import com.lemparty.exception.DuplicateRecipeByNameAndUserException;
import com.lemparty.exception.InvalidRecipeException;
import com.lemparty.exception.RecipeDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RecipeService {

    @Autowired
    private MongoRecipeDAO recipeDAO;

    @Autowired
    private MongoRecipeRepository recipeRepository;

    public Recipe createRecipe(Recipe recipe) throws DuplicateRecipeByNameAndUserException {

        recipe.setRecipeID(UUID.randomUUID().toString());

        Optional<Recipe> existingRecipe = recipeRepository.findByNameAndUserID(recipe.getName(), recipe.getUserID());

        if(!existingRecipe.isPresent()){
            Recipe createdRecipe = recipeRepository.insert(recipe);
            return createdRecipe;
        } else{
            throw new DuplicateRecipeByNameAndUserException(recipe.getName(), recipe.getUserID());
        }
    }

    public Recipe updateRecipe(String recipeID, Recipe recipe) throws RecipeDoesNotExistException {

        Optional<Recipe> existingRecipe = recipeRepository.findById(recipeID);

        if(!existingRecipe.isPresent()){
            throw new RecipeDoesNotExistException(recipe.getName(), recipe.getUserID());
        }

        recipe.setRecipeID(existingRecipe.get().getRecipeID());
        Recipe updated = recipeRepository.save(recipe);

        return updated;

    }

    public Recipe findById(String id) throws InvalidRecipeException {

        Optional<Recipe> recipeGotten = recipeRepository.findById(id);

        if(!recipeGotten.isPresent())
            throw new InvalidRecipeException(id);

        return recipeGotten.get();
    }

    public List<Recipe> findByUserID(String userID) throws InvalidRecipeException {

        List<Recipe> recipeGotten = recipeRepository.findByUserID(userID);

        if(recipeGotten.isEmpty()){
            throw new InvalidRecipeException(userID);
        }

        return recipeGotten;
    }

    public Recipe findByNameAndUserID(String name, String userID) throws InvalidRecipeException {

        Optional<Recipe> recipeGotten = recipeRepository.findByNameAndUserID(name, userID);

        if(!recipeGotten.isPresent()){
            throw new InvalidRecipeException(userID);
        }

        return recipeGotten.get();
    }
    

}
