package com.myadd.myadd.user.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myadd.myadd.response.BaseResponse;
import com.myadd.myadd.response.BaseResponseStatus;
import com.myadd.myadd.user.security.handler.CustomLogoutSuccessHandler;
import com.myadd.myadd.user.security.service.AuthenticationProvider;
import com.myadd.myadd.user.security.handler.CustomAuthenticationFailureHandler;
import com.myadd.myadd.user.security.handler.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 어떤 요청에 대한 엔드 포인트에 도달하기 전에 요청을 가로채서 어떤 작업을 수행하는 컴포넌트를 “서블릿 필터(Servlet Filter)”라고 함. (보호나 인증 등에 대한 처리를 전처리하기 위해 사용)
// 이러한 필터들이 Chain, 즉 사슬처럼 엮인 것을 Filter Chain이라고 부름.
// Spring Security: SecurityFilterChain
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨
public class SecurityFilterChainConfig {

    private AuthenticationProvider authenticationProvider;

    // 관련 있는 설정들은 람다를 사용해서 그룹화
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic() // HTTP Basic 인증 활성화
                .and()
                .csrf().disable() // CSRF 보호 비활성화 (주의: 프로덕션 환경에서는 권장되지 않음)
                .authorizeHttpRequests(auth -> auth // 인증 요청 설정
                        // /posts/**, /users/my-info/**, /users/change-password/** 경로는 인증된 사용자만 접근 가능
                        .antMatchers("/posts/**", "/users/my-info/**", "/users/change-password/**").authenticated()
                        // 그 외 모든 요청은 누구나 접근 가능
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form // 폼 로그인 설정
                        // 시큐리티가 /users/login/email 경로로 로직을 만들어서 시큐리티 로그인을 처리함(이메일 방식 로그인 처리 URL 설정)
                        // 로그인을 위해 UserController에 따로 "/users/login/email"을 구현하지 않아도 됨.
                        // 이 과정에서 UserDetails가 필요하기에 따로 이를 구현한 클래스를 만들어줘야한다.
                        // 이 로그인 과정에서 필요한 것이 있기 때문에 auth 패키지를 파서 PrincipalDetails 을 만들어줘야한다.
                        .loginProcessingUrl("/users/login/email")
                        .successHandler(new CustomAuthenticationSuccessHandler()) // 로그인 성공 핸들러 설정
                        .failureHandler(new CustomAuthenticationFailureHandler()) // 로그인 실패 핸들러 설정
                        .usernameParameter("email") // 사용자 이름 파라미터를 'email'로 설정
                )
                .logout(logout -> logout // 로그아웃 설정
                        .logoutUrl("/users/my-info/logout")
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                )
                .oauth2Login(oauth2 -> oauth2 // OAuth2 로그인 설정
                        .userInfoEndpoint()
                        // OAuth2로 로그인한 사용자에 대해서는 authenticationProvider에 구현한대로 처리함.
                        // 로그인한 사용자에게 받은 정보가 AuthenticationProvider의 매개변수인 userRequest로 return
                        .userService(authenticationProvider)
                ) ;

        return httpSecurity.build();
    }
}
