package com.lemparty.data;

import com.lemparty.entity.Recipe;
import com.lemparty.exception.InvalidRecipeException;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.and;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class MongoRecipeDAO {
    MongoCollection<Recipe> collection;

    public MongoRecipeDAO(String url){
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().register("com.lemparty.entity.Recipe").automatic(true).build()));

        //"mongodb://localhost:27017"
        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase database = mongoClient.getDatabase("lemparty");
        collection = database.getCollection("recipe", Recipe.class).withCodecRegistry(pojoCodecRegistry);
    }

    public boolean create(Recipe recipe) throws InvalidRecipeException {

        Recipe existingRecipe = getRecipeByName(recipe.getEmail());

        if(existingRecipe == null){
            collection.insertOne(recipe);
            return true;
        } else{
            System.out.println("Recipe already exists for "+recipe.getEmail());
            throw new InvalidRecipeException(recipe.getEmail());
        }

    }

    public Recipe getRecipeByName(String email){
        Recipe obtainedRecipe = collection.find(eq("email", email)).first();
        System.out.println("Obtained Recipe is not Null: "+String.valueOf(obtainedRecipe != null));

        return obtainedRecipe;
    }

}
