package com.be01.prj2.entity.order;

import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.cart.Cart;
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
public class Order {
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
    private String addressee; // 수령인

    @OneToMany(mappedBy = "orderCartId")
    private List<OrderProduct> orderProductList;

    @OneToMany(mappedBy = "orderUserId")
    private List<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "orderId")
    private List<OrderProduct> orderProductsOrderId;



}



