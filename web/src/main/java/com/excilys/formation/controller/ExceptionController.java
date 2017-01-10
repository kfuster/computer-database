package com.excilys.formation.controller;

import com.excilys.formation.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({NoHandlerFoundException.class, NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView notFoundException(Exception e) {
        ModelAndView modelView = new ModelAndView("/404");
        modelView.addObject("name", e.getClass().getSimpleName());
        modelView.addObject("message", e.getMessage());
        return modelView;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView exception(Exception e) {
        ModelAndView modelView = new ModelAndView("/500");
        modelView.addObject("name", e.getClass().getSimpleName());
        modelView.addObject("message", e.getMessage());
        return modelView;
    }
}