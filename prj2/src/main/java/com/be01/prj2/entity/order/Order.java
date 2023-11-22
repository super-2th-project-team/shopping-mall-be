package com.be01.prj2.entity.order;

import com.be01.prj2.entity.cart.Cart;
import com.be01.prj2.entity.customer.Customer;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_idx")
    private Long orderId;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cartId;

    @ManyToOne
    @JoinColumn(name = "order_user_id")
    private Customer orderUserId;

    private Long totalPrice;

    private String address;

    private String comment;

    private Date orderEnroll;

    private String addressee; //수령인, 주소 ?












}
