package com.lemparty.exception;

public class RecipeAlreadyExistsException extends Exception {

    public RecipeAlreadyExistsException(String name, String userID){
        super("Recipe Already Exists for: "+name+" "+userID);
    }
}
