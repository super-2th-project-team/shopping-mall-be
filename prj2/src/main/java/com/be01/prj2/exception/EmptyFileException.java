package com.be01.prj2.exception;

public class EmptyFileException extends RuntimeException{
    public EmptyFileException(String message) {
        super(message);
    }
}
