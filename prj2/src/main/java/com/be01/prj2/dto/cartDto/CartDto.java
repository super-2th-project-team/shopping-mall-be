package com.be01.prj2.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor

public class CartDto {

    private Long cartId;
    private Long buyerId;
    private Long totalQuantity;
    private Long totalPrice;

}
