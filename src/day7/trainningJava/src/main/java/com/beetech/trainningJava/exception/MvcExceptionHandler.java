package com.beetech.trainningJava.exception;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MvcExceptionHandler {

//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = RuntimeException.class)
    public ModelAndView handleException(RuntimeException e) {
        // return model and view
        return new ModelAndView("error/404");
    }
}
