package com.myadd.myadd.user.security.service;

import com.myadd.myadd.user.domain.entity.UserEntity;
import com.myadd.myadd.user.security.handler.CustomAuthenticationSuccessHandler;
import com.myadd.myadd.user.security.usertype.UserTypeEnum;
import com.myadd.myadd.user.repository.UserRepository;
import com.myadd.myadd.user.security.usertype.GoogleUserInfo;
import com.myadd.myadd.user.security.usertype.KakaoUserInfo;
import com.myadd.myadd.user.security.usertype.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// OAuth2 로그인 처리를 위한 서비스
// Spring Security: AuthenticationProvider(사용자 정보를 로드하는 핵심 인터페이스)
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationProvider extends DefaultOAuth2UserService { // Oauth2 로그인

    private BCryptPasswordEncoder bCryptPasswordEncoder; // 비밀번호 암호화를 위한 인코더
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private UserRepository userRepository;

    // OAuth2 제공자인 구글 혹은 카카오로부터 받은 사용자 정보인 userRequest에 대한 후처리를 하는 메서드
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest.getClientRegistration = {}", userRequest.getClientRegistration()); // 어떤 Oauth로 로그인 했는지 확인
        log.info("userRequest.getAccessToken = {}", userRequest.getAccessToken().getTokenValue()); // 실제 액세스 토큰 확인

        // userRequest 정보를 통해 OAuth2User 객체(회원프로필) 얻기
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // {sub=104717591461978030161, name=강병준, given_name=병준, family_name=강, picture=https://lh3.googleusercontent.com/a/AAcHTtczGvv086yOdzmf0UuQxF0cdYVIRVDooGQ3qWOIeLUv3Q=s96-c, email=bjkang402@gmail.com, email_verified=true, locale=ko}
        log.info("getAttributes = {}", oAuth2User.getAttributes());

        // OAuth2 제공자(Google, Kakao 등)에 따라 사용자 정보 처리 방식 분기
        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            log.info("구글 유저");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }
        else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            log.info("카카오 유저");
            oAuth2UserInfo = new KakaoUserInfo((Map)oAuth2User.getAttributes().get("kakao_account"));
        }
        
        // 주석에 적어둔 것은 구글로 로그인 한 경우
        String provider = userRequest.getClientRegistration().getRegistrationId(); // google
        String nickName = oAuth2UserInfo.getNickname(); // 강병준
        String profile = oAuth2UserInfo.getProfile(); // https://lh3.googleusercontent.com/a/AAcHTtczGvv086yOdzmf0UuQxF0cdYVIRVDooGQ3qWOIeLUv3Q=s96-c
        UserTypeEnum userTypeEnum = oAuth2UserInfo.getUsertype(); // 2
        String email = oAuth2UserInfo.getEmail(); // bjkang402@gamil.com
        String password = bCryptPasswordEncoder.encode(UUID.randomUUID().toString());

        // 소셜 로그인을 한 이메일에 User 객체 찾기
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        UserEntity userEntity = null;

        // 소셜 로그인을 한 이메일이 없다면 회원가입
        if(userEntityOptional.isEmpty()){
            userEntity = UserEntity.builder()
                    .nickname(nickName)
                    .profile(profile)
                    .userType(userTypeEnum)
                    .email(email)
                    .password(password)
                    .build();
            userRepository.save(userEntity);
        }
        else{ // 이미 존재하는 사용자라면 조회된 정보 사용
            userEntity = userEntityOptional.get();
            log.info("이미 존재하는 OAuth2 회원입니다.");
        }

        // 현재 요청의 HttpServletResponse 객체 얻기
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        // OAuth2 로그인 성공 처리
        String successMessage = null;
        try {
            successMessage = customAuthenticationSuccessHandler.oauth2LoginSuccess(response, provider);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 응답 설정 및 메시지 작성
        response.setContentType("application/json;charset=UTF-8");

        try {
            response.getWriter().write(successMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            response.getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Spring Security의 인증 객체에 들어갈 UserDetails 구현체 반환(Authentication 객체 안에 들어감)
        return new CustomUserDetails(userEntity, oAuth2User.getAttributes());
    }


}
