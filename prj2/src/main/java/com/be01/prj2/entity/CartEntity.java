package com.be01.prj2.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "cart_quantity")
    private int cartQuantity;

    @Column(name = "cart_status")
    private char cartStatus;

    @Column(name = "total_price")
    private int totalPrice;

    @Builder
    public CartEntity(Long cartId, Long userIdx, int productId, int cartQuantity, char cartStatus, int totalPrice) {
        this.cartId = cartId;
        this.userIdx = userIdx;
        this.productId = productId;
        this.cartQuantity = cartQuantity;
        this.cartStatus = cartStatus;
        this.totalPrice = totalPrice;
    }
}
