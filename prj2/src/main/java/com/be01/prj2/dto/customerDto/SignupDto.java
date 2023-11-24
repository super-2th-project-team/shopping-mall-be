package com.be01.prj2.dto.customerDto;

import com.be01.prj2.entity.myPage.Mypage;
import com.be01.prj2.role.Role;
import com.be01.prj2.entity.customer.Customer;
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

    // Mypage에 필요한 필드들 추가
    private String profile;
    private String myinfo;
    private String address;
    private char gender;
    private String my_info;
    private String profile_img;
    public Customer toEntity(){
        return Customer.builder()
                .name(name)
                .email(email)
                .mobile(mobile)
                .role(role)
                .build();
    }


    public Mypage myPageEntity(Customer customer) {
        return Mypage.builder()
                .myPageUserId(customer)
                .email(email)
                .mobile(mobile)
                .name(name)
                .build();
    }
}
