package com.redataha.redditclone.exceptions;

public class SubredditNoFoundException extends RuntimeException {
    public SubredditNoFoundException(String subredditName) {
        super("There is no subreddit with this name: " + subredditName);
    }
}
