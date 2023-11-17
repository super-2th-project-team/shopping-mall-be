package com.be01.prj2.dto.productsDto;

import com.be01.prj2.entity.Customer;
import com.be01.prj2.entity.category.Category;
import com.be01.prj2.entity.product.Product;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class SellDto {

    private Customer sellerID;
    private String productName;
    private Integer productPrice;
    private String productInfo;
    private Integer productStock;
    private Integer productSell;
    private Date productEnroll;
    private String productImg;
    private Category category;

    public Product toEntity(){
        return Product.builder()
                .sellerId(sellerID)
                .productName(productName)
                .productPrice(productPrice)
                .productInfo(productInfo)
                .productStock(productStock)
                .productSell(productSell)
                .productEnroll(productEnroll)
                .productImg(productImg)
                .category(category)
                .build();
    }
}
