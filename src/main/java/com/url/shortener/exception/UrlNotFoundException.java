package com.url.shortener.exception;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
