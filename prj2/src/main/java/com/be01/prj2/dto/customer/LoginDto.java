package com.be01.prj2.dto.customer;

import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.role.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class LoginDto {

    private String email;
    private String password;
    private Role role;

    public Customer toEntity(){
     return Customer.builder()
             .email(email)
             .password(password)
             .build();
    }

}
