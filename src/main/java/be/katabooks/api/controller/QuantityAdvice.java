package be.katabooks.api.controller;

import be.katabooks.api.BookNotFoundException;
import be.katabooks.api.QuantityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class QuantityAdvice {

    @ExceptionHandler(QuantityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String quantityHandler(QuantityException ex) {
        return ex.getMessage();
    }
}