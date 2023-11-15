package com.be01.prj2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurViewDto {

    private String productName;
    private int productPrice;
    private String productImg;
    private Date orderEnroll;
}
