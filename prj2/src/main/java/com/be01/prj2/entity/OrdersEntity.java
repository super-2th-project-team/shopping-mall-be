package com.be01.prj2.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderIdx;

    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_price")
    private int productPrice;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "address")
    private String address;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "order_comment")
    private String orderComment;

    @Column(name = "order_enroll")
    private Timestamp orderEnroll;

    @Column(name = "addressee")
    private String addressee;

    @Column(name = "order_quantity")
    private int orderQuantity;

}
