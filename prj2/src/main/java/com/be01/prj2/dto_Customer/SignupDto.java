package com.be01.prj2.dto_Customer;

import com.be01.prj2.role_Customer.Role;
import com.be01.prj2.entity_Customer.Customer;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class SignupDto {

    private String name;
    private String email;
    private String password;
    private String pwdck;
    private String mobile;
    private Role role;

    public Customer toEntity(){
        return Customer.builder()
                .name(name)
                .email(email)
                .mobile(mobile)
                .role(role)
                .build();
    }


}
