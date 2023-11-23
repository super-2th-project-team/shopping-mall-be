package com.be01.prj2.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private String comment;
    private String addressee;
    private String address;
    private Long productId;
    private String mobile;

}
