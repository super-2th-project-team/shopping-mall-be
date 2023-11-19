package com.be01.prj2.entity;

import com.be01.prj2.entity_Customer.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cart_product")
public class CartProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "user_idx", nullable = false)
    private Long userIdx;

    @Column(name = "cart_quantity", nullable = false)
    private int cartQuantity;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    @ManyToOne
    @JoinColumn(name = "user_idx", insertable = false, updatable = false)
    private Customer customer;

}
