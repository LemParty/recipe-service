package com.lemparty.exception;

public class DuplicateRecipeByNameAndUserException extends Exception {

    public DuplicateRecipeByNameAndUserException(String name, String userID){
        super("Recipe with Name: '"+name+"' already exists for User: "+userID);
    }
}
