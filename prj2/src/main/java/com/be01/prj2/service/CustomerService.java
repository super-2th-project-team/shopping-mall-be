package com.be01.prj2.service;

import com.be01.prj2.dto.LoginDto;
import com.be01.prj2.dto.SignOutDto;
import com.be01.prj2.dto.SignupDto;
import com.be01.prj2.entity.Customer;
import com.be01.prj2.entity.SignOut;
import com.be01.prj2.exception.NotFoundException;
import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.repository.CustomerRepository;
import com.be01.prj2.repository.SignOutRepository;
import com.be01.prj2.role.Role;
import lombok.AllArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final SignOutRepository signOutRepository;
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
        String mobile = signupDto.getMobile();
        //Role role = signupDto.getRole(); //일반 유저인지 판매자인지 구분

        if (customerRepository.existsByEmailAndRole(email, Role.USER) ) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("이미 사용중인 이메일입니다.");
        }
        if(!password.equals(pwdck)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("비밀번호 확인이 비밀번호와 다릅니다.");
        }

        Customer customer = Customer.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .mobile(mobile)
                .role(Role.USER)
                .build();
        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료입니다");
    }

    @Transactional
    public String login(LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            customerRepository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException("회원이 없습니다"));

            if (redisTemplate.opsForValue().get("logout : " + loginDto.getEmail()) != null) {
                redisTemplate.delete("logout: " + loginDto.getEmail());
            }
            return tokenProvider.createAccessToken(email);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("잘못된 자격 증명 입니다.");
        }
    }
    //리프레쉬로 access 토큰 재발급
    @Transactional
    public String createAccessTokenByRefresh(String email) {

        if (redisTemplate.opsForValue().get("RF :" + email) != null) {
            return tokenProvider.createAccessToken(email);

        }
        return null;
    }
    //로그아웃 기능
    @Transactional
    public void logout(@RequestHeader("AccessToken") String accessToken){
        redisTemplate.opsForValue().set("logout : "+ tokenProvider.getEmailBytoken(accessToken), "logout", Duration.ofSeconds(1800));
        redisTemplate.delete(tokenProvider.getEmailBytoken(accessToken));
        redisTemplate.delete("RF :" + tokenProvider.getEmailBytoken(accessToken));

    }

//    @Transactional
//    public void signOut(SignOutDto signOutDto){
//
//        String email = signOutDto.getEmail();
//        String password = signOutDto.getPassword();
//        String mobile = signOutDto.getMobile();
//
//        try {
//            Customer customer = customerRepository.findByEmail(email)
//                    .orElseThrow(() -> new NotFoundException("없는 회원 입니다"));
//            customerRepository.delete(customer);
//
//        }catch (Exception e){
//            throw new NotFoundException("없는 회원 입니다");
//        }
//
//        SignOut signOut = SignOut.builder()
//                .email(email)
//                .mobile(mobile)
//                .build();
//
//        signOutRepository.save(signOut);
//    }

    @Transactional
    public void signOut(Customer signOutDto) {
        String email = signOutDto.getEmail();
        String password = signOutDto.getPassword();
        String mobile = signOutDto.getMobile();

        Optional<Customer> signOutCustomer = customerRepository.findAllByEmailAndMobile(email, mobile);

        if (signOutCustomer.isPresent()) {
            Customer customer = signOutCustomer.get();

            if (passwordEncoder.matches(password, customer.getPassword())) {
                customer.setRole(Role.SIGNOUT);
                customerRepository.save(customer);
            } else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
            }
        } else {
            throw new IllegalArgumentException("회원이 없습니다");
        }
    }
}
