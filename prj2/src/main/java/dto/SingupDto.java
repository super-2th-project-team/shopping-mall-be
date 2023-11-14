package dto;

import com.be01.prj2.role.Role;
import entity.Customer;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class SingupDto {

    private String name;
    private String email;
    private String password;
    private String pwdck;
    private Role role;

    public Customer toEntity(){
        return Customer.builder()
                .name(name)
                .email(email)
                .role(role)
                .build();
    }


}
