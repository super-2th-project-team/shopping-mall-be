package com.be01.prj2.repository.customerRepository.customUserDetails;

import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.repository.customerRepository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
