package com.be01.prj2.dto.productsDto;

import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.Product;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class SellDto {


    private Long sellerId;
    private Long productId;
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



//    public static SellDto fromEntity(Product product, List<String> color, List<String> size){
//        SellDto sellDto = new SellDto();
//        sellDto.setProductName(product.getProductName());
//        sellDto.setProductPrice(product.getProductPrice());
//        sellDto.setProductInfo(product.getProductInfo());
//        sellDto.setProductStock(product.getProductStock());
//        sellDto.setProductSell(product.getProductSell());
//        sellDto.setProductImg(product.getProductImg());
//        sellDto.setCategory(product.getCategory());
//        sellDto.setSubCategory(product.getSubCategory());
//
//
//    }


    public static SellDto fromEntity(Product product, List<String> color, List<String> size, Long userId){
        return SellDto.builder()
                .sellerId(userId)
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productInfo(product.getProductInfo())
                .productStock(product.getProductStock())
                .productSell(product.getProductSell())
                .productEnroll(product.getProductEnroll())
                .productImg(product.getProductImg())
                .category(product.getCategory())
                .subCategory(product.getSubCategory())
                .color(color)
                .size(size)
                .build();
    }
}
