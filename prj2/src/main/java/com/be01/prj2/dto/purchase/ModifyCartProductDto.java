package com.be01.prj2.dto.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyCartProductDto {

    private Long userId;
    private Long cartProductId;
    private int quantity;
}
