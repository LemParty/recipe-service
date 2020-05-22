package com.lemparty.service;

import com.lemparty.data.IngredientRepository;
import com.lemparty.data.RecipeRepository;
import com.lemparty.entity.Ingredient;
import com.lemparty.entity.IngredientID;
import com.lemparty.entity.Recipe;
import com.lemparty.exception.DuplicateRecipeByNameAndUserException;
import com.lemparty.exception.InvalidRecipeException;
import com.lemparty.exception.RecipeDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    public Recipe createRecipe(Recipe recipe) throws DuplicateRecipeByNameAndUserException {
        String recipeID = UUID.randomUUID().toString();
        recipe.setRecipeID(recipeID);

        Optional<Recipe> existingRecipe = recipeRepository.findByNameAndUserID(recipe.getName(), recipe.getUserID());

        if(!existingRecipe.isPresent()){
            Recipe createdRecipe = recipeRepository.save(recipe);

            List<Ingredient> iterIngredient = createdRecipe.getIngredientsList();
            List<Ingredient> saveIngredients = new ArrayList<Ingredient>();

            for(Ingredient i : iterIngredient){
                i.setRecipeID(recipeID);
//                i.setName(recipe.getName());
//                i.setIngredientID(UUID.randomUUID().toString());
                saveIngredients.add(i);
            }
            ingredientRepository.saveAll(saveIngredients);

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

        List<Ingredient> saveIngredients = updated.getIngredientsList();
        for(Ingredient i : saveIngredients){
            i.setRecipeID(recipeID);
        }
        ingredientRepository.saveAll(saveIngredients);
        return updated;

    }

    public Recipe findById(String id) throws InvalidRecipeException {

        Optional<Recipe> recipeGotten = recipeRepository.findById(id);

        if(!recipeGotten.isPresent())
            throw new InvalidRecipeException(id);

        List<Ingredient> ingredientsGotten = ingredientRepository.findByRecipeID(recipeGotten.get().getRecipeID());
        Recipe toReturn = recipeGotten.get();
        toReturn.setIngredientsList(ingredientsGotten);

        return toReturn;
    }

    public List<Recipe> findByUserID(String userID) throws InvalidRecipeException {
        Map<String, List<Ingredient>> recipeToIngredientsMap = new HashMap<String, List<Ingredient>>();

        List<Recipe> recipeGotten = recipeRepository.findByUserID(userID);

        if(recipeGotten.isEmpty()){
            throw new InvalidRecipeException(userID);
        }

        List<String> idsToSearch = new ArrayList<String>();
//        List<IngredientID> idsToSearch = new ArrayList<IngredientID>();
        for(Recipe r : recipeGotten){
            IngredientID newID = new IngredientID();
            newID.setRecipeID(r.getRecipeID());
            newID.setName("*");
            idsToSearch.add(r.getRecipeID());
        }

//
        Iterable<Ingredient> ingredientsGotten = ingredientRepository.findAllByRecipeIDIn(idsToSearch);
                //findAllById(idsToSearch);//findByRecipeIDIn(idsToSearch);
        for(Ingredient i : ingredientsGotten){
            List<Ingredient> mapIngredients = recipeToIngredientsMap.get(i.getRecipeID());
            if(mapIngredients == null){
                mapIngredients = new ArrayList<Ingredient>();
            }

            mapIngredients.add(i);
            recipeToIngredientsMap.put(i.getRecipeID(), mapIngredients);
        }

        List<Recipe> updatedRecipeList = new ArrayList<Recipe>();

        for(int i = 0; i < recipeGotten.size(); i++){
            Recipe inplaceRecipe = recipeGotten.get(i);
            inplaceRecipe.setIngredientsList(recipeToIngredientsMap.get(inplaceRecipe.getRecipeID()));

            updatedRecipeList.add(inplaceRecipe);
        }
        return updatedRecipeList;
    }

    public Recipe findByNameAndUserID(String name, String userID) throws InvalidRecipeException {

        Optional<Recipe> recipeGotten = recipeRepository.findByNameAndUserID(name, userID);

        if(!recipeGotten.isPresent()){
            throw new InvalidRecipeException(userID);
        }

        List<Ingredient> ingredientsGotten = ingredientRepository.findByRecipeID(recipeGotten.get().getRecipeID());
        Recipe toReturn = recipeGotten.get();
        toReturn.setIngredientsList(ingredientsGotten);

        return recipeGotten.get();
    }
    

}
