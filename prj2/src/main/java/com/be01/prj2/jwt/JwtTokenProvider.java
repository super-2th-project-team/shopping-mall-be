package com.be01.prj2.jwt;


import com.be01.prj2.role.Role;
import dto.TokenDto;
import entity.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultHeader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import repository.CustomerRepository;
import repository.customUserDetails.CustomUserDetails;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    @Value("${security.jwt.secret}")
    private String secretKey;


    private long accesstokenValidMillisecond = 500L * 60 * 60; //1시간
    private long refreshtokenValidMillisecond = 1000L * 60 * 60;

    private final CustomUserDetails customUserDetails;

    //secretkey base64 인코딩
    @PostConstruct
    protected void init(){
        secretKey  = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 생성
    public TokenDto createToken(Customer customer) {

        Date acceessTokenValidTime = getTokenExpiration(accesstokenValidMillisecond);
        Date refreshTokenValidTime = getTokenExpiration(refreshtokenValidMillisecond);
        

    }

    //jwt 토큰에서 인증 정보조회
    public Authentication getAuthentication(String jwtToken) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(this.getUserEmail(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰에서 회원 정보 추출
    private String getUserEmail(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    //토큰 값 가져오자
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰 유효성 + 만료일
    public boolean validateToken(String jwtToken) {
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken)
                    .getBody();
            Date now = new Date();
            return claims.getExpiration()
                    .after(now);
        }catch (Exception e){
            return false;
        }
    }

    public String findEmailBytoken(String token) {

        // JWT 토큰을 디코딩하여 페이로드를 얻기
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        // "userId" 클레임의 값을 얻기
        return claims.isEmpty() ? null : claims.get("sub", String.class);
    }

    //토큰 만료시간 체크
    private Date getTokenExpiration(long expirationMillisecond) {
        Date date = new Date();

        return new Date(date.getTime() + expirationMillisecond);
    }
}
