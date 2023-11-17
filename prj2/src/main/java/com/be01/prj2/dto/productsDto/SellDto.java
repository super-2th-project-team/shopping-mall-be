package com.be01.prj2.dto.productsDto;

import com.be01.prj2.entity.Customer;
import com.be01.prj2.entity.product.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class SellDto {

    private String productName;
    private Integer productPrice;
    private String productInfo;
    private Integer productStock;
    private Integer productSell;
    private Date productEnroll;
    private String productImg;
    private String category;
    private String subCategory;
    private List<String> color;
    private List<String> size;



    public Product toEntity(){
       return Product.builder()
               .productName(productName)
               .productPrice(productPrice)
               .productInfo(productInfo)
               .productStock(productStock)
               .productSell(productSell)
               .productEnroll(productEnroll)
               .productImg(productImg)
               .category(category)
               .subCategory(subCategory)
               .color(color)
               .size(size)
               .build();
   }
}
