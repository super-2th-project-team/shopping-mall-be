package com.be01.prj2.dto.productsDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class SizeDto {

    private Long productId;
    private List<String > size;
}
