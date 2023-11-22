package com.be01.prj2.entity.product;

import com.be01.prj2.entity.cart.CartProduct;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.order.OrderProduct;

import lombok.*;

import javax.persistence.*;



@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;


    private int productPrice;
    private String productInfo;

    private Integer productStock;
    private Integer productSell;

    private Date productEnroll;

    private String productImg;
    private String category;
    private String subCategory;


}

