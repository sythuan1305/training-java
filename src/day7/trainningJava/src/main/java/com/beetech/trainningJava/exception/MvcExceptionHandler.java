package com.beetech.trainningJava.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class này dùng để xử lý các exception của mvc
 */
@ControllerAdvice
public class MvcExceptionHandler {

//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    @ExceptionHandler(value = RuntimeException.class)
//    public ModelAndView handleException(RuntimeException e) {
//        return new ModelAndView("error/404");
//    }
}
