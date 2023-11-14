package com.be01.prj2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private int productId;
    private int cartQuantity;
    private char cartStatus;
    private int totalPrice;
}
