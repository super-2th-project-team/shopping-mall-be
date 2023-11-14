package com.be01.prj2.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "purview")
public class PurViewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private int productPrice;

    @Column(name = "product_img")
    private String productImg;

    @Column(name = "order_enroll")
    private LocalDateTime orderEnroll;

    @Builder
    public PurViewEntity(Long productId, Long userIdx, String productName, int productPrice, String productImg, LocalDateTime orderEnroll) {
        this.productId = productId;
        this.userIdx = userIdx;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImg = productImg;
        this.orderEnroll = orderEnroll;
    }
}
