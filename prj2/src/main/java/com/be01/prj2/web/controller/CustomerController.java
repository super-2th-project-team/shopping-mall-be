package com.be01.prj2.web.controller;

import com.be01.prj2.dto.AddExtraInfoDto;
import com.be01.prj2.dto.LoginDto;

import com.be01.prj2.dto.SignupDto;
import com.be01.prj2.entity.Customer;
import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.repository.CustomerRepository;
import com.be01.prj2.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String , String > redisTemplate;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupDto signupDto){
        return customerService.register(signupDto);
    }

    @PostMapping("/login")
    public Map<String ,String > login(@RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse) throws JsonProcessingException {
        String accessToken = customerService.login(loginDto);
        String refreshToken = tokenProvider.createRefreshToken(loginDto.getEmail());

        redisTemplate.opsForValue().set(loginDto.getEmail(), accessToken, Duration.ofSeconds(1800));
        redisTemplate.opsForValue().set("RF :" + loginDto.getEmail(), refreshToken, Duration.ofHours(1L));
        httpServletResponse.addCookie(new Cookie("access_token", accessToken));
        httpServletResponse.addCookie(new Cookie("refresh_token", refreshToken));

        // JSON 형식으로 응답을 구성
        Map<String, String> response = new HashMap<>();
        response.put("message", "로그인 되었습니다");
        response.put("access_token", accessToken);
        response.put("refresh_token", refreshToken);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(response);

        log.info("JSON Response: {}", jsonResponse);
        return response;
    }

    @PostMapping("/refresh")
    private ResponseEntity<?> refresh(@RequestHeader("refresh_token") String refreshToken, HttpServletResponse httpServletResponse) throws IllegalAccessException {
        String key = tokenProvider.getEmailBytoken(refreshToken);
//        Role role = tokenProvider.getRoleByToken(refreshToken);

        String accessTokenByRefresh = customerService.createAccessTokenByRefresh(key);
        httpServletResponse.setHeader("access_token", accessTokenByRefresh);
        redisTemplate.opsForValue().set(key, accessTokenByRefresh, Duration.ofSeconds(1800));
        return ResponseEntity.status(HttpStatus.OK.value()).body("토큰이 재발급 되었습니다");
    }
    @PostMapping("/logout")
    public ResponseEntity<?>  logout(@RequestHeader("AccessToken")String accessToken){
        customerService.logout(accessToken);
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 완료");
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signout(@RequestBody Customer signOutDto){

        customerService.signOut(signOutDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("회원 탈퇴가 완료되었습니다. 이용해주셔서 감사합니다");
    }

    @PostMapping("/addInfo")
    private ResponseEntity<?> addInfo(@RequestHeader("AccessToken")String token ,@RequestBody AddExtraInfoDto addExtraInfoDto){
        customerService.addExtraInfo(token, addExtraInfoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("저장 되었습니다!");
    }

}
