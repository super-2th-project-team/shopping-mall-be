package com.be01.prj2.entity.cart;

import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.order.Order;
import com.be01.prj2.role.CartStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "user_idx")
    @JsonManagedReference
    private Customer userIdx;

    @Column(name = "cart_quantity")
    private int cartQuantity;

    @Column(name = "status")
    private String  status;

    @Column(name = "total_price")
    private int totalPrice;

    @OneToMany(mappedBy = "cartId",cascade = CascadeType.ALL ,orphanRemoval = true)
    @JsonIgnore
    private List<CartProduct> cartProducts;

    @OneToOne(mappedBy = "cartId")
    private Order order;


    public static Cart createCart(Customer customer){
        Cart cart = new Cart();
        cart.setUserIdx(customer);
        cart.setStatus(CartStatus.NOTPAY.getName());
        return cart;
    }


}