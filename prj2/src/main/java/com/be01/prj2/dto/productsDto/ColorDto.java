package com.be01.prj2.dto.productsDto;

import com.be01.prj2.entity.product.ProductColor;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class ColorDto {

    private Long productId;
    private List<ProductColor> color;


}
