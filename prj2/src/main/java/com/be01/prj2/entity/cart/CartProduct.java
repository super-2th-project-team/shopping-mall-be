package com.be01.prj2.entity.cart;

import com.be01.prj2.entity.product.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_product")
public class CartProduct {

    @Id
    @Column(name = "cart_id")
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "buyer_idx")
    private Cart cartCustomerId;

    private int cartQuantity;
    private int price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;

    @ManyToOne
    @JoinColumn(name="seller_idx")
    private Product sellerId;



}
