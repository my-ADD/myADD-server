package com.myadd.myadd.user.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myadd.myadd.response.BaseResponse;
import com.myadd.myadd.response.BaseResponseStatus;
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

@Configuration
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

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
                .loginProcessingUrl("/users/login/email") // 시큐리티가 /login 경로로 로직을 만들어서 시큐리티 로그인을 처리함
                .successHandler(new CustomAuthenticationSuccessHandler())
                .failureHandler(new CustomAuthenticationFailureHandler())
                .usernameParameter("email")
                .and()
                .logout()
                .logoutUrl("/users/my-info/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        logoutResponse(response, authentication);
                    }
                })
                .and()
                .oauth2Login()
                //.loginPage("/afterLogin") // 구글 로그인 완료 후 후처리가 필요함. 후처리는 PrincipalOauth2UserService에서 진행
                .userInfoEndpoint()
                .userService(principalOauth2UserService); // 로그인한 사용자에게 받은 정보가 principalOauth2UserService의 매개변수인 userRequest로 return
        return httpSecurity.build();
    }

    public void logoutResponse(HttpServletResponse response, Authentication authentication) throws IOException {

        BaseResponse<BaseResponseStatus> logoutResponseValue;
        String jsonResponse;

        if (authentication == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logoutResponseValue = new BaseResponse<>(BaseResponseStatus.FAILED_LOGOUT);

        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            logoutResponseValue = new BaseResponse<>(BaseResponseStatus.SUCCESS_LOGOUT);
        }
        jsonResponse = new ObjectMapper().writeValueAsString(logoutResponseValue);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
}
