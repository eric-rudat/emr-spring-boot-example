package com.emr.example.exceptions;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String id) {
        super(id + " was not found.");
    }

    public ObjectNotFoundException(Integer id) {
        super(id + " was not found.");
    }
}
