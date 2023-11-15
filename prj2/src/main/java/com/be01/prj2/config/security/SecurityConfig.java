package com.be01.prj2.config.security;

import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.web.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.headers().frameOptions().sameOrigin()
                .and()
                .formLogin().disable()
                .csrf().disable()
                .httpBasic().disable()
                .rememberMe().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/resources/static/**", "/api/register", "/api/login").permitAll()
                //나중에 글 작성 api에 Role_user
                .and()
                .exceptionHandling()
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                //             .accessDeniedHandler(new CustomerAccessDeniedHandler())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
