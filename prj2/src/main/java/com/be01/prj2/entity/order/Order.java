package com.be01.prj2.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_idx")
    private Long orderId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Customer orderUserId;


    private Long productId;
    private Long totalPrice;
    private String address;
    private String mobile;
    private String Comment;
    private Date orderEnroll;

    @Column(name = "product_price")
    private int productPrice;


    @Column(name = "order_enroll")
    private Timestamp orderEnroll;




    @Column(name = "order_quantity")
    private int orderQuantity;

}



