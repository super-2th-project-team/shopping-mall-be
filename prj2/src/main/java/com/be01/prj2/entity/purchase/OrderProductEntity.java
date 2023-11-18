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
@Table(name = "order_product")
public class OrderProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_quantity")
    private int orderQuantity;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "product_id")
    private Long productId;

    @Builder
    public OrderProductEntity(Long orderId, int orderQuantity, int totalPrice, Long productId) {
        this.orderId = orderId;
        this.orderQuantity = orderQuantity;
        this.totalPrice = totalPrice;
        this.productId = productId;
    }
}