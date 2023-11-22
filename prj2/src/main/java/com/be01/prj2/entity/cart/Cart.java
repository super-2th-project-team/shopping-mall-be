package com.be01.prj2.entity.cart;

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

public class Cart{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "buyer_idx")
    private Customer buyerId;

    private Long cartQuantity;
    private CartStatus status;
    private Long totalPrice;

    @OneToMany(mappedBy = "cartCustomerId")
    private List<CartProduct> cartProduct;

    @OneToOne(mappedBy = "cartId")
    private Order orderCartId;

}