package com.mindex.challenge.exceptions;

public class CompensationNotFoundException extends RuntimeException {

    public CompensationNotFoundException(String error){
        super(error);
    }
}
