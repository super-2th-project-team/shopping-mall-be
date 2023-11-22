//package com.be01.prj2.entity;
//
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Getter
//@Setter
//@Data
//@NoArgsConstructor
//@Entity
//@Table(name = "order_product")
//public class OrderProductEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long orderId;
//
//    @Column(name = "order_quantity", nullable = false)
//    private int orderQuantity;
//
//    @Column(name = "total_price", nullable = false)
//    private int totalPrice;
//
//    @Column(name = "product_id", insertable = false, updatable = false)
//    private Long productId;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id", nullable = false)
//    private ProductEntity product;
//
//    public Long getProductId() {
//        return productId;
//    }
//
//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }
//}