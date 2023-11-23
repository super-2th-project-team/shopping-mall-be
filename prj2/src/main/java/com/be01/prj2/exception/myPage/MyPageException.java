package com.be01.prj2.exception.myPage;

import lombok.Getter;

@Getter
public class MyPageException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public MyPageException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return this.errorMessage.getMessage();
    }
}
