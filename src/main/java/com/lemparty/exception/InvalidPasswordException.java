package com.lemparty.exception;

public class InvalidPasswordException extends Exception {

    public InvalidPasswordException(String email){
        super("Passwords Do Not Match For Email: "+email);
    }
}
