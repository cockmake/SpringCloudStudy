package com.example.controller;

import jakarta.validation.ValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
// 这边是检验后报错后的统一处理
@ControllerAdvice
public class ControllerValidation {
    
    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    public String error(ValidationException e){
        return e.getMessage();
    }
}
