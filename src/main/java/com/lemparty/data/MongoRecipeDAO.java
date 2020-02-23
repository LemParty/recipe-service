package com.lemparty.data;

import com.lemparty.entity.Recipe;
import com.lemparty.entity.Ingredient;
import com.lemparty.exception.DuplicateRecipeByNameAndUserException;
import com.lemparty.exception.RecipeDoesNotExistException;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;

import static com.mongodb.client.model.Filters.and;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Deprecated
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

    public boolean create(Recipe recipe) throws DuplicateRecipeByNameAndUserException {

        Recipe existingRecipe = getRecipeByNameAndUserID(recipe.getName(), recipe.getUserID());

        if(existingRecipe == null){
            collection.insertOne(recipe);
            return true;
        } else{
            throw new DuplicateRecipeByNameAndUserException(recipe.getName(), recipe.getUserID());
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

        Bson[] bsonArray = new Bson[]{eq("name", name), eq("userID", userID)};
        Bson criteria = and(bsonArray);

        Recipe obtainedRecipe = collection.find(criteria).first();

        return obtainedRecipe;
    }

    public Recipe getRecipeByRecipeID(String recipeID){

        Bson[] bsonArray = new Bson[]{eq("recipeID", recipeID)};
        Bson criteria = and(bsonArray);

        Recipe obtainedRecipe = collection.find(criteria).first();

        return obtainedRecipe;
    }

    public List<Recipe> getRecipesByUserID(String userID){
        List<Recipe> recipeList = new ArrayList<Recipe>();

        Bson[] bsonArray = bsonArray = new Bson[]{eq("userID", userID)};
        Bson criteria = and(bsonArray);

        FindIterable<Recipe> obtainedRecipe = collection.find(criteria);
        MongoCursor<Recipe> recipeIterator = obtainedRecipe.iterator();

        while(recipeIterator.hasNext()){
            recipeList.add(recipeIterator.next());
        }

        return recipeList;
    }

}
