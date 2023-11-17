package com.be01.prj2.dto_Customer;

import com.be01.prj2.entity_Customer.Customer;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class AddExtraInfoDto {
    private char gender;
    private String address;

    public Customer toEntity(){
        return Customer.builder()
                .gender(gender)
                .address(address)
                .build();
    }
}
