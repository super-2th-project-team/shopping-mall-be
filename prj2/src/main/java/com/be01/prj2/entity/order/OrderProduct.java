package com.be01.prj2.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order_product")
public class OrderProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "order_quantity", nullable = false)
    private int orderQuantity;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "product_id", nullable = false)
    private Long productId;
}