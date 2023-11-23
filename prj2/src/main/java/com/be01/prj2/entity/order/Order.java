package com.be01.prj2.entity.order;

import com.be01.prj2.entity.cart.Cart;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.Product;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
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
    @JoinColumn(name = "user_id")
    private Customer userId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;

    private Long totalPrice;

    private String address;

    @Column(name = "comment")
    private String comment;

    @Column(name = "order_enroll")
    private Date orderEnroll;

    @Column(name = "addressee") //수령인
    private String addressee;

    @Column(name = "mobile")
    private String mobile;

    @Builder
    public static Order createOrder(Cart cartId, Long totalPrice, String comment, Date orderEnroll, String address, String addressee, String mobile) {
        Order order = new Order();
        order.cartId = cartId;
        order.totalPrice = totalPrice;
        order.comment = comment;
        order.orderEnroll = orderEnroll;
        order.address = address;
        order.addressee = addressee;
        order.mobile = mobile;
        return order;
    }
}
