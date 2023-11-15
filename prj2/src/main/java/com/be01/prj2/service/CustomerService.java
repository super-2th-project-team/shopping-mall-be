package com.be01.prj2.service;

import com.be01.prj2.dto.LoginDto;
import com.be01.prj2.dto.SignupDto;
import com.be01.prj2.entity.Customer;
import com.be01.prj2.exception.NotFoundException;
import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.repository.CustomerRepository;
import com.be01.prj2.role.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RedisTemplate<String ,String> redisTemplate;

    @Transactional
    public ResponseEntity<?> register(SignupDto signupDto){
        String name = signupDto.getName();
        String email = signupDto.getEmail();
        String password = signupDto.getPassword();
        String pwdck = signupDto.getPwdck();
        Role role = signupDto.getRole(); //일반 유저인지 판매자인지 구분

        if (customerRepository.existsByEmail(email)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("이미 사용중인 이메일입니다.");
        }
        if(!password.equals(pwdck)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("비밀번호 확인이 비밀번호와 다릅니다.");
        }
        Customer customer = Customer.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료입니다");
    }

    @Transactional
    public String login(LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        Role role = loginDto.getRole();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            customerRepository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException("회원이 없습니다"));

            if (redisTemplate.opsForValue().get("logout: " + loginDto.getEmail()) != null) {
                redisTemplate.delete("logout: " + loginDto.getEmail());
            }
            return tokenProvider.createAccessToken(email, role);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("잘못된 자격 증명 입니다.");
        }
    }
    @Transactional
    public void createAccessTokenByRefresh(String email, Role role){

        if(redisTemplate.opsForValue().get("RF :" + email) != null){
            tokenProvider.createAccessToken(email, role);
        }
        else {
            return;
        }
    }




}
