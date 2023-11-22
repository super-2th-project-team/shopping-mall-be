package com.be01.prj2.entity.customer;

import com.be01.prj2.entity.cart.Cart;
import com.be01.prj2.role.Role;
import lombok.*;
import org.hibernate.criterion.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long userId;

    private String name;
    private String email;
    private String password;
    private String mobile;
    private char gender;
    private String address;
    private String profileImg;
    private Role role;

    @OneToMany(mappedBy = "sellerId")
    private List<product> products;


    @OneToOne(mappedBy = "buyerId")
    private Cart cart;

    @OneToMany(mappedBy = "orderUserId")
    private List<Order> order;

    @OneToOne(mappedBy = "myPageUserId")
    private Mypage mypage;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.role.getName()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
