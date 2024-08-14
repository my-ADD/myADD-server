package com.myadd.myadd.user.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Spring Security: PasswordEncoder
@Configuration
public class PasswordEncoder {
    // BCrypt 알고리즘을 사용하는 PasswordEncoder 빈 생성
    // 단순히 Bean이 싱글톤으로 관리되기 때문이라고 할 수 있겠다.
    // BCryptPasswordEncoder의 용도 특성상 Bean으로 등록하지 않으면 객체가 무분별하게 생성될 수 있음.
    // 이런 부담을 줄이고자 Bean으로 등록해서 사용.
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
}