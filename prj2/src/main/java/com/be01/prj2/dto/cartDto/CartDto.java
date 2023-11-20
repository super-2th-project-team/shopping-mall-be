package com.be01.prj2.dto.cartDto;

import com.be01.prj2.role.CartStatus;
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
    private CartStatus status;
    private Long totalPrice;

}
