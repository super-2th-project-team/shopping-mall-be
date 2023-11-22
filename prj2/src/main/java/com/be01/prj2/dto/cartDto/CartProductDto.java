package com.be01.prj2.Dto;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class CartProductDto {

    private Long cartId;
    private Long buyerId;
    private int Quantity;
    private int price;
    private Long productId;
    private Long sellerId;
}
