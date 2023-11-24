package com.be01.prj2.dto.cartDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class CartProductUpdateDto {

    private Long cartProductIdx;
    private int cartQuantity;
    private String color;
    private String size;

}