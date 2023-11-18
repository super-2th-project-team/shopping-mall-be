package com.be01.prj2.entity.purchase;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class OrdersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_idx")
    private Long orderIdx;

    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_price")
    private int productPrice;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "address", nullable = true)
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

    @Builder
    public OrdersEntity(Long orderIdx, Long userIdx, Long productId, int productPrice, int totalPrice, String address, String mobile, String orderComment, Timestamp orderEnroll, String addressee, int orderQuantity) {
        this.orderIdx = orderIdx;
        this.userIdx = userIdx;
        this.productId = productId;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
        this.address = address;
        this.mobile = mobile;
        this.orderComment = orderComment;
        this.orderEnroll = orderEnroll;
        this.addressee = addressee;
        this.orderQuantity = orderQuantity;
    }
}
