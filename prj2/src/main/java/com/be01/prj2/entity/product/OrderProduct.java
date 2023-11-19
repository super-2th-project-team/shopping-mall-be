package com.be01.prj2.entity.product;

import com.be01.prj2.entity.customer.Customer;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_product")
public class OrderProduct {

    @Id
    @Column(name = "order_id")
    private Long orderId;

    private Long orderQuantity;
    private Long totalPrice;


    @ManyToOne
    @JoinColumn(name = "product_id") //실제 컬럼명
    private Product productId; //변수 명은 테이블 명으로

    @ManyToOne
    @JoinColumn(name = "user_idx") //실제 컬럼명
    private Customer customerId; //변수 명은 테이블 명으로
}
