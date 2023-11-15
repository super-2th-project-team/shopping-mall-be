package com.be01.prj2.exception.myPage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyPageExceptionHandler {

    @ExceptionHandler(MyPageException.class)
    public ResponseEntity<String> handleMyPageException(MyPageException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
