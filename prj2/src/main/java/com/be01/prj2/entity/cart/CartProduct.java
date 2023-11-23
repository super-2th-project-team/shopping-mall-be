package com.be01.prj2.entity.cart;


import com.be01.prj2.entity.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_idx")
    private Long cartProductIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cartId;

    private String productName;
    private int cartQuantity;
    private int price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product productId;

    private String color;
    private String size;

    public static CartProduct createCartProduct(Cart cart, Product product, int cartQuantity ,String color ,
                                                String size){
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCartId(cart);
        cartProduct.setProductId(product);
        cartProduct.setProductName(product.getProductName());
        cartProduct.setPrice(product.getProductPrice());
        cartProduct.setCartQuantity(cartQuantity);
        cartProduct.setColor(color);
        cartProduct.setSize(size);
        return cartProduct;
    }
    public void addCount(int cartQuantity){
        this.cartQuantity += cartQuantity;
    }

}
