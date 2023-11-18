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
@Table(name = "cart_product")
public class CartProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "cart_quantity")
    private int cartQuantity;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "product_id")
    private Long productId;

    @Builder
    public CartProductEntity(Long cartId, Long userIdx, int cartQuantity, int totalPrice, Long productId) {
        this.cartId = cartId;
        this.userIdx = userIdx;
        this.cartQuantity = cartQuantity;
        this.totalPrice = totalPrice;
        this.productId = productId;
    }
}
