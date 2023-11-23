package com.be01.prj2.entity.order;

import com.be01.prj2.entity.cart.Cart;
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

    private Long totalPrice;

    private String address;

    private String comment;

    private Date orderEnroll;

    private String addressee; //수령인, 주소 ?

    @Builder
    public static Order createOrder(Cart cartId, Long totalPrice, String comment, Date orderEnroll, String address, String addressee, String mobile) {
        Order order = new Order();
        order.cartId = cartId;
        order.totalPrice = totalPrice;
        order.comment = comment;
        order.orderEnroll = orderEnroll;
        order.address = address;
        order.addressee = addressee;
        return order;
    }
}