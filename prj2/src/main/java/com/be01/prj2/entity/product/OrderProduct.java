package com.be01.prj2.entity.product;

import com.be01.prj2.entity.Customer;
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
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Customer customer;
}
