package be.katabooks.api.controller;

import be.katabooks.api.QuantityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class UnauthenticatedAdvice {

    @ExceptionHandler(QuantityException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String unauthenticatedHandler(QuantityException ex) {
        return ex.getMessage();
    }
}