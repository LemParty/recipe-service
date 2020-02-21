package com.lemparty.exception;

public class RecipeDoesNotExistException extends Exception {

    public RecipeDoesNotExistException(String name, String userID){
        super("Recipe does not exist for: "+name+" "+userID);
    }
}
