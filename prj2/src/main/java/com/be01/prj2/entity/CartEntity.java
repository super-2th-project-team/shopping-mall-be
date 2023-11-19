package com.be01.prj2.entity;

import com.be01.prj2.entity_Customer.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "cart_id")
    private Long cartId;

    @Column(name = "user_idx", nullable = false)
    private Long userIdx;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "cart_quantity", nullable = false) //상품 수량
    private int cartQuantity;

    @Column(name = "cart_status", nullable = false)  //결제 여부
    private String cartStatus;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    public CartEntity(Long cartId, Long userIdx, Long productId, int cartQuantity, String cartStatus, int totalPrice) {
        this.cartId = cartId;
        this.userIdx = userIdx;
        this.productId = productId;
        this.cartQuantity = cartQuantity;
        this.cartStatus = cartStatus;
        this.totalPrice = totalPrice;
    }
    @ManyToOne
    @JoinColumn(name = "user_idx", insertable = false, updatable = false)
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartProductEntity> cartProducts;
}
