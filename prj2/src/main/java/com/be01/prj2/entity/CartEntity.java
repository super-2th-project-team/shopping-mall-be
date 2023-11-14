package com.be01.prj2.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column(name = "user_idx", nullable = false)
    private Long userIdx;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "cart_quantity", nullable = false)
    private int cartQuantity;

    @Column(name = "cart_status", nullable = false)
    private String cartStatus;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;
}
