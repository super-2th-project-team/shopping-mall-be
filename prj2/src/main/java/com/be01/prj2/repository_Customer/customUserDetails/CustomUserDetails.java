package com.be01.prj2.repository_Customer.customUserDetails;

import com.be01.prj2.entity_Customer.Customer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.be01.prj2.repository_Customer.CustomerRepository;

@Service
public class CustomUserDetails implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomUserDetails(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("로그인 시도한 아이디가 없는 아이디 입니다"));

        return customer;
    }
}
