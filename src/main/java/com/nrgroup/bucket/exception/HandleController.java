package com.nrgroup.bucket.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Order(1)
@ControllerAdvice
public class HandleController {

    @ExceptionHandler(ViewException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleBucketViewException() {
        return "error";
    }

}
