package com.be01.prj2.exception.Purchase;

// 특정 상품 찾을 수 없을 때 발생하는 예외
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
