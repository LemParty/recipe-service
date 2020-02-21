package com.lemparty.exception;

public class InvalidRecipeException extends Exception {

    public InvalidRecipeException(String email){
        super("No User Exists For Email: "+email);
    }
}
