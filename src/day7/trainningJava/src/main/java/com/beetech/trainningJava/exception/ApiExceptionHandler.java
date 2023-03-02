package com.beetech.trainningJava.exception;

import com.beetech.trainningJava.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<?> handleException(HttpMediaTypeException e) {
        return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
    }
}
