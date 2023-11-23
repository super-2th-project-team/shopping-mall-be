package com.be01.prj2.dto.orderDto;

import com.be01.prj2.role.CartStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class OrderDto {

    private Long cartId;
    private String address;
    private String comment;
    private int totalPrice;
}
