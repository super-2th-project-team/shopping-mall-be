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
    private String name;
    private Integer originPrice;
    private int price;
    private String description;
    private Integer stock;
    private Integer productSell;
    private Date enroll;
    private List<String> productImg;
    private Integer discount;
    private String category;
    private String subCategory;
    private List<String> color;
    private List<String> size;



    public static SellDto fromEntity(Product product, List<String> color, List<String> size, Long userId, List<String> img){
        return SellDto.builder()
                .sellerId(userId)
                .productId(product.getProductId())
                .name(product.getProductName())
                .price(product.getProductPrice())
                .originPrice(product.getOriginPrice() != null ? product.getOriginPrice():product.getProductPrice())
                .description(product.getProductInfo())
                .stock(product.getProductStock())
                .productSell(product.getProductSell())
                .enroll(product.getProductEnroll())
                .discount(product.getDiscount() != null ? product.getDiscount() : 0)
                .category(product.getCategory())
                .subCategory(product.getSubCategory())
                .color(color)
                .size(size)
                .productImg(img)
                .build();
    }
}
