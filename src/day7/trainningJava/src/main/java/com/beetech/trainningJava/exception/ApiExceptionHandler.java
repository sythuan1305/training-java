package com.beetech.trainningJava.exception;

import com.beetech.trainningJava.model.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class này dùng để xử lý các exception của api
 */
@RestControllerAdvice
public class ApiExceptionHandler {
    //    private final ObjectMapper jacksonMapper;
//
//    public ApiExceptionHandler(ObjectMapper jacksonMapper) {
//        this.jacksonMapper = jacksonMapper;
//    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.badRequest().body(
                new ApiResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        false,
                        e.getMessage(),
                        null));
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // Nếu validate fail thì trả về 400
    public ResponseEntity<Object> handleBindException(BindException e) {
        // Trả về message của lỗi đầu tiên
        String errorMessage = "";
        if (e.getBindingResult().hasErrors())
            errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        return ResponseEntity.badRequest().body(
                new ApiResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        false,
                        errorMessage,
                        null));
    }

    @ExceptionHandler(HttpError.class)
    public ResponseEntity<Object> handleCustomException(HttpError ex, HttpServletRequest req) {
        ex.setPath(req.getRequestURI());
        return new ResponseEntity<>(
                new ApiResponse(ex.getHttpStatus().value(), false, ex.getMessage(), null),
                ex.getHttpStatus());
    }

//    @ExceptionHandler(BindException.class)
//    public ResponseEntity<?> handleBindException(BindException ex, HttpServletRequest req) throws JsonProcessingException {
//        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
//        Map<String, Set<String>> map = fieldErrors.stream().collect(Collectors.groupingBy(FieldError::getField,
//                Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())));
//
//        String message = jacksonMapper.writeValueAsString(map);
//        HttpError error = new HttpError(message, HttpStatus.BAD_REQUEST);
//        error.setPath(req.getRequestURI());
//
//        return ResponseEntity.badRequest().body(error);
//    }
}
