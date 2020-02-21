package com.lemparty.data;

import com.lemparty.entity.Recipe;
import com.lemparty.entity.Ingredient;
import com.lemparty.exception.InvalidRecipeException;
import com.lemparty.exception.RecipeAlreadyExistsException;
import com.lemparty.exception.RecipeDoesNotExistException;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.and;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class MongoRecipeDAO {
    MongoCollection<Recipe> collection;

    public MongoRecipeDAO(String url){
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().register(Ingredient.class, Recipe.class).automatic(true).build()));

        //"mongodb://localhost:27017"
        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase database = mongoClient.getDatabase("lemparty-cook");
        collection = database.getCollection("recipeservice", Recipe.class).withCodecRegistry(pojoCodecRegistry);
    }

    public boolean create(Recipe recipe) throws RecipeAlreadyExistsException {

        Recipe existingRecipe = getRecipeByNameAndUserID(recipe.getName(), recipe.getUserID());

        if(existingRecipe == null){
            collection.insertOne(recipe);
            return true;
        } else{
            throw new RecipeAlreadyExistsException(recipe.getName(), recipe.getUserID());
        }

    }

    public boolean update(Recipe recipe) throws RecipeDoesNotExistException {

        Recipe existingRecipe = getRecipeByNameAndUserID(recipe.getName(), recipe.getUserID());

        if(existingRecipe != null){
            collection.findOneAndReplace(and(eq("name", recipe.getName()),  eq("userID", recipe.getUserID())), recipe);
            return true;
        } else{
            throw new RecipeDoesNotExistException(recipe.getName(), recipe.getUserID());
        }

    }

    public Recipe getRecipeByNameAndUserID(String name, String userID){
        Bson criteria = and(eq("name", name),  eq("userID", userID));

        Recipe obtainedRecipe = collection.find(criteria).first();
        System.out.println("Obtained Recipe is not Null: "+String.valueOf(obtainedRecipe != null));

        return obtainedRecipe;
    }

}
