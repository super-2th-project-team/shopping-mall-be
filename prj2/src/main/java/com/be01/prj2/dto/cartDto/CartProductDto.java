package com.be01.prj2.dto.cartDto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class CartProductDto {
    private Long cartProductIdx;
    private Long cartId;
    private Long productId;
    private int Quantity;
    private String color;
    private String size;
    private int price;




}
