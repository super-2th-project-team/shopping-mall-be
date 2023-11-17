package com.be01.prj2.entity.product;

import com.be01.prj2.entity.Customer;
import com.be01.prj2.entity.category.Category;
import lombok.*;

import javax.persistence.*;
import java.util.Currency;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name")
    private String productName;
    private Integer productPrice;
    private String productInfo;
    private Integer productStock;
    private Integer productSell;
    private Date productEnroll;
    private String productImg;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product") // mappedBy 속성은 Product 엔터티에 있는 필드명을 나타냅니다.
    private List<OrderProduct> orderProducts;

    @ManyToOne
    @JoinColumn(name = "seller_idx")
    private Customer sellerId;


}
