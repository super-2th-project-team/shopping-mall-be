package com.be01.prj2.entity.purchase;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "cart_id")
    private Long cartId;

    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "cart_quantity") //상품 수량
    private int cartQuantity;

    @Column(name = "cart_status")  //결제 여부
    private String cartStatus;

    @Column(name = "total_price")
    private int totalPrice;

    @Builder
    public CartEntity(Long cartId, Long userIdx, Long productId, int cartQuantity, String cartStatus, int totalPrice) {
        this.cartId = cartId;
        this.userIdx = userIdx;
        this.productId = productId;
        this.cartQuantity = cartQuantity;
        this.cartStatus = cartStatus;
        this.totalPrice = totalPrice;
    }
}
