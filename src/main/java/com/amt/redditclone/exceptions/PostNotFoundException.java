package com.amt.redditclone.exceptions;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String message) {
        super((message));
    }
}