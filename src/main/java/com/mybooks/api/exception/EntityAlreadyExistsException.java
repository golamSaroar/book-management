package com.mybooks.api.exception;

public class EntityAlreadyExistsException extends Exception {
    public EntityAlreadyExistsException(String s){
        super(s);
    }
}
