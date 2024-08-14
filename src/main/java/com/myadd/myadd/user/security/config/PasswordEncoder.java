package com.myadd.myadd.user.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Spring Security: PasswordEncoder
@Configuration
public class PasswordEncoder {
    // BCrypt 알고리즘을 사용하는 PasswordEncoder 빈 생성
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
}