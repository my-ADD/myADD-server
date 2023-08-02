package com.myadd.myadd.user.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();
        httpSecurity.csrf().disable();
        httpSecurity.authorizeHttpRequests()
                .antMatchers("/posts/**").authenticated()
                .antMatchers("/users/my-info/**").authenticated()
                .antMatchers("/users/change-password/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/login") // 시큐리티가 /login 경로로 로직을 만들어서 시큐리티 로그인을 처리함
                .usernameParameter("email");


        return httpSecurity.build();
    }
}
