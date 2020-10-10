package com.url.shortener.exception.handler;

import com.url.shortener.exception.UrlNotFoundException;
import com.url.shortener.exception.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UrlShortenerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UrlNotFoundException.class)
    public Error handleUrlNotFoundException(UrlNotFoundException e) {
        return new Error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new Error(e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
