package com.be01.prj2.dto.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCartDto {

    private Long userId;
    private List<Long> cartProductIds;
    private String address;
    private String addressee;
    private String mobile;
    private Date order_enroll;
}
