package com.be01.prj2.dto.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductDto {

    private Long userId;
    private Long productId;
    private int quantity;
}
