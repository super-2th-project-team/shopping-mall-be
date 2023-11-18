package com.be01.prj2.exception.Purchase;

// 상품 재고 부족 예외
public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message) {
        super(message);
    }
}