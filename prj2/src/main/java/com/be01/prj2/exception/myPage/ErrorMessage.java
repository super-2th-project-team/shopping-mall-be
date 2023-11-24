package com.be01.prj2.exception.myPage;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    USER_NOT_FOUND("유저를 찾을 수 없습니다."),
    USER_UPDATE_FAILED("유저 정보 업데이트에 실패했습니다."),
    CART_NOT_FOUND("장바구니를 찾을 수 없습니다."),
    CART_NOT_AVAILABLE("장바구니가 사용 가능하지 않습니다."),
    CUSTOMER_NOT_FOUND("고객을 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND("상품을 찾을 수 없습니다."),
    PAY_NOT_FOUND("결제 정보를 찾을 수 없습니다."),
    ORDER_NOT_FOUND("구매 이력이 없습니다."),
    USER_SIGNOUT("탈퇴한 회원입니다");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

}
