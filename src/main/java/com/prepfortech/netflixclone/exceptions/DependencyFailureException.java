package com.prepfortech.netflixclone.exceptions;

public class DependencyFailureException extends RuntimeException{
    public DependencyFailureException(Throwable cause){
        super(cause);
    }
}
