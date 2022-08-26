package com.redataha.redditclone.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
