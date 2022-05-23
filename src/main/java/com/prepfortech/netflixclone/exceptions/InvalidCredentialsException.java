package com.prepfortech.netflixclone.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.lang.reflect.InvocationHandler;

public class InvalidCredentialsException extends RuntimeException{
    private String message;

    public InvalidCredentialsException(final String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
