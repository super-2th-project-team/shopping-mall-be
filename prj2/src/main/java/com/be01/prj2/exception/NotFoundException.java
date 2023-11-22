package com.be01.prj2.exception_Customer;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}