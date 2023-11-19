package com.be01.prj2.dto;

import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.customer.SignOut;
import com.be01.prj2.role.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class SignOutDto {

    private String email;
    private String password;
    private String mobile;
    private Role role;

    public Customer toEntity(){
        return Customer.builder()
                .email(email)
                .password(password)
                .mobile(mobile)
                .build();
    }

    public SignOut toOutEntity(){
        return SignOut.builder()
                .email(email)
                .mobile(mobile)
                .build();
    }
}
