package com.be01.prj2.entity.product;

import com.be01.prj2.entity.cart.CartProduct;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.order.OrderProduct;
import lombok.*;

import javax.persistence.*;
import java.util.*;

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

    @ElementCollection
    @CollectionTable(name = "product_color", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "color")
    private List<String > color;


    @ElementCollection
    @CollectionTable(name = "product_size", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "size")
    private List<String> size;

    @ManyToOne
    @JoinColumn(name = "seller_idx") //자기 테이블의 실제 컬럼명
    private Customer sellerId;

    @OneToMany(mappedBy = "orderProductId") // mappedBy 속성은 Product 엔터티에 있는 필드명을 나타냅니다.
    private List<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "productId")
    private List<CartProduct> cartProducts; //장바구니에 담길 상품들


    @OneToMany(mappedBy = "orderProductSellerId")
    private List<OrderProduct> orderProductsId;

}
