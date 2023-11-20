package com.be01.prj2.entity.order;

import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.Product;
import lombok.*;

import javax.persistence.*;

@Entity(name = "OrderProduct")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_product")
public class OrderProduct {

    @Id
    @Column(name = "order_product_idx")
    private Long orderProductIdx;


    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order orderId;

    private Long orderQuantity;
    private Long price;

    @ManyToOne
    @JoinColumn(name = "product_id") //실제 컬럼명
    private Product orderProductId; //변수 명은 테이블 명으로


    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Order orderCartId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Order orderUserId; //구매자 ID

    @ManyToOne
    @JoinColumn(name = "seller_idx")
    private Product orderProductSellerId; //판매자 ID



}
