package com.be01.prj2.dto.purchase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    private Long userIdx;
    private Long productId;
    private int productPrice;
    private int totalPrice;
    private String address;
    private String mobile;
    private String orderComment;
    private String addressee;
    private int orderQuantity;

    public OrderRequest() {
    }

    public OrderRequest(Long userIdx, Long productId, int productPrice, int totalPrice, String address,
                        String mobile, String orderComment, String addressee, int orderQuantity) {
        this.userIdx = userIdx;
        this.productId = productId;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
        this.address = address;
        this.mobile = mobile;
        this.orderComment = orderComment;
        this.addressee = addressee;
        this.orderQuantity = orderQuantity;
    }
}
