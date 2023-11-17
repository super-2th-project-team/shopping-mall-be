package com.be01.prj2.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cart_product")
public class CartProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column(name = "user_idx", nullable = false)
    private Long userIdx;

    @Column(name = "cart_quantity", nullable = false)
    private int cartQuantity;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "product_id", nullable = false)
    private Long productId;
}
