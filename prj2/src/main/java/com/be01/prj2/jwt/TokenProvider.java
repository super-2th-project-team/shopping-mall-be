package com.be01.prj2.jwt;

import com.be01.prj2.role.Role;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.be01.prj2.repository.customUserDetails.CustomUserDetails;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenProvider {

    @Value("${security.jwt.secret}")
    private String secretKey;

    private long accesstokenValidSecond = 500L * 60 * 60; //30분
    private long refreshtokenValidSecond = 1000L * 60 * 60; //1시간

    private final CustomUserDetails customUserDetails;

   //secretKey Base64 인코딩
    @PostConstruct
     protected void init(){
       secretKey  = Base64.getEncoder().encodeToString(secretKey.getBytes());
   }

    //토큰 생성
    public String createAccessToken(String email){

        return getString(email, accesstokenValidSecond, "ROLE_" + Role.USER.name());
    }
    private String getString(String email, long accesstokenValidSecond, String role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date now = new Date();
        Date acceessValidate = new Date(now.getTime() + accesstokenValidSecond);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setExpiration(acceessValidate)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(String email){
        return getString(email, refreshtokenValidSecond, "ROLE_" + Role.USER.name());
    }


    //토큰에서 인증 정보조회
    public Authentication getAuthentication(String token){
        UserDetails userDetails = customUserDetails.loadUserByUsername(this.getUserEmail(token));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
        log.info("Authentication authorities: " + authentication.getAuthorities());
        return authentication;
    }

    //토큰에서 회원정보 추출
    private String getUserEmail(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //토큰 값 가져오기
    public String resolveToken(HttpServletRequest request){
        String token = request.getHeader("access_token");
        log.info("Resolved token: " + token);
        return token;
    }
    public boolean validateToken(String jwtToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken)
                    .getBody();
            Date now = new Date();
            boolean isValid = claims.getExpiration().after(now);
            log.info("Token validation result: " + isValid);
            return isValid;
        } catch (Exception e) {
            return false;
        }
    }

    //토큰에서 이메일 값 추출
    public String getEmailBytoken(String token) {

        // JWT 토큰을 디코딩하여 페이로드를 얻기
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        // "userId" 클레임의 값을 얻기
        return claims.isEmpty() ? null : claims.get("sub", String.class);
    }

    public String findRoleByToken(String token){
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        return claims.isEmpty() ? null : claims.get("role", String.class);
    }
}


