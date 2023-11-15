package com.be01.prj2.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Data
@NoArgsConstructor
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_price", nullable = false)
    private int productPrice;

    @Column(name = "product_info", nullable = false)
    private String productInfo;

    @Column(name = "product_stock", nullable = false)
    private int productStock;

    @Column(name = "product_sell", nullable = false)
    private int productSell;

    @Column(name = "product_enroll", nullable = false)
    private Timestamp productEnroll;

    @Column(name = "product_img", nullable = false)
    private String productImg;

}
