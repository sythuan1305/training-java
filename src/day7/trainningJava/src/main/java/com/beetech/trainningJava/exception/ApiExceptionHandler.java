package com.beetech.trainningJava.exception;

import com.beetech.trainningJava.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Class này dùng để xử lý các exception của api
 */
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
    }
}
