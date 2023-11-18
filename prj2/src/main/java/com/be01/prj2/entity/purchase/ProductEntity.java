package com.be01.prj2.entity.purchase;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private int productPrice;

    @Column(name = "product_info")
    private String productInfo;

    @Column(name = "product_stock")
    private int productStock;

    @Column(name = "product_sell")
    private int productSell;

    @Column(name = "product_enroll")
    private Timestamp productEnroll;

    @Column(name = "product_img")
    private String productImg;

    @Column(name = "category_id")
    private Long categoryId;

    @Builder
    public ProductEntity(Long productId, String productName, int productPrice, String productInfo, int productStock, int productSell, Timestamp productEnroll, String productImg, Long categoryId) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInfo = productInfo;
        this.productStock = productStock;
        this.productSell = productSell;
        this.productEnroll = productEnroll;
        this.productImg = productImg;
        this.categoryId = categoryId;
    }
}
