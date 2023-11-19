package com.be01.prj2.entity;

import com.be01.prj2.entity_Customer.Customer;
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
    @Column(name = "order_idx")
    private Long orderIdx;

    @Column(name = "user_idx", nullable = false)
    private Long userIdx;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_price", nullable = false)
    private int productPrice;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Column(name = "order_comment")
    private String orderComment;

    @Column(name = "order_enroll", nullable = false)
    private Timestamp orderEnroll;

    @Column(name = "addressee", nullable = false)
    private String addressee;

    @ManyToOne
    @JoinColumn(name = "user_idx", insertable = false, updatable = false)
    private Customer customer;

}
