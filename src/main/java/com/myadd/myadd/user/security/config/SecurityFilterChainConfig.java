package com.myadd.myadd.user.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myadd.myadd.response.BaseResponse;
import com.myadd.myadd.response.BaseResponseStatus;
import com.myadd.myadd.user.security.handler.CustomLogoutSuccessHandler;
import com.myadd.myadd.user.security.service.PrincipalOauth2UserService;
import com.myadd.myadd.user.security.handler.CustomAuthenticationFailureHandler;
import com.myadd.myadd.user.security.handler.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Spring Security: SecurityFilterChain
@Configuration
public class SecurityFilterChainConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic(); // HTTP Basic 인증 활성화
        httpSecurity.csrf().disable(); // CSRF 보호 비활성화 (주의: 프로덕션 환경에서는 권장되지 않음)
        httpSecurity.authorizeHttpRequests()
                .antMatchers("/posts/**").authenticated() // /posts/** 경로는 인증된 사용자만 접근 가능
                .antMatchers("/users/my-info/**").authenticated() // /users/my-info/** 경로는 인증된 사용자만 접근 가능
                .antMatchers("/users/change-password/**").authenticated() // /users/change-password/** 경로는 인증된 사용자만 접근 가능
                .anyRequest().permitAll() // 그 외 모든 요청은 누구나 접근 가능
                .and()
                .formLogin() // 폼 로그인 설정
                // 시큐리티가 /users/login/email 경로로 로직을 만들어서 시큐리티 로그인을 처리함(로그인 처리 URL 설정)
                // 결과적으로 로그인을 위해 UserController에 따로 "/users/login/email"을 구현하지 않아도 괜찮다.
                // 매우 편리하지만, 이 과정에서 UserDetails가 필요하기에 따로 이를 구현한 클래스를 만들어줘야한다.
                // 이 로그인 과정에서 필요한 것이 있기 때문에 auth 패키지를 파서 PrincipalDetails 을 만들어줘야한다.
                .loginProcessingUrl("/users/login/email")
                .successHandler(new CustomAuthenticationSuccessHandler()) // 로그인 성공 핸들러 설정
                .failureHandler(new CustomAuthenticationFailureHandler()) // 로그인 실패 핸들러 설정
                .usernameParameter("email") // 사용자 이름 파라미터를 'email'로 설정
                .and()
                .logout() // 로그아웃 설정
                .logoutUrl("/users/my-info/logout")
                .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                .and()
                .oauth2Login() // OAuth2 로그인 설정
                .userInfoEndpoint()
                // OAuth2 사용자 서비스 설정
                // 로그인한 사용자에게 받은 정보가 principalOauth2UserService의 매개변수인 userRequest로 return
                .userService(principalOauth2UserService);
        return httpSecurity.build();
    }
}
