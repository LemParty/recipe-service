package com.lemparty.exception;

public class InvalidRecipeException extends Exception {

    public InvalidRecipeException(String name, String userID){
        super("Invalid Recipe for "+name+" "+userID);
    }

    public InvalidRecipeException(String recipeID){
        super("Invalid Recipe for "+recipeID);
    }
}
