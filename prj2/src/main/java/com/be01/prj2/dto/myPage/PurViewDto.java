package com.be01.prj2.dto.myPage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurViewDto {

    private Long orderId;
    private Long productId;
    private Long totalPrice;
    private Date orderEnroll;
    private String addressee;
    private String address;
    private String mobile;
    private String comment;
}
