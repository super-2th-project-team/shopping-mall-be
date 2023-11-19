package com.be01.prj2.web.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long orderIdx;
    private Long userIdx;
    private Long productId;
    private int productPrice;
    private int totalPrice;
    private String address;
    private String mobile;
    private String orderComment;
    private String addressee;


    public OrderDto(Long orderIdx, Long userIdx, Long productId, int productPrice, int totalPrice, String address, String mobile, String orderComment, String addressee) {
        this.orderIdx = orderIdx;
        this.userIdx = userIdx;
        this.productId = productId;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
        this.address = address;
        this.mobile = mobile;
        this.orderComment = orderComment;
        this.addressee = addressee;
    }
}
