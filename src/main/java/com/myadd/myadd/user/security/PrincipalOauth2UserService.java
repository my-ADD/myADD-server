package com.myadd.myadd.user.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Override // 구글로부터 받은 userRequest 데이터에 대한 후처리를 하는 메서드
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest = {}", userRequest);
        log.info("userRequest.getClientRegistration = {}", userRequest.getClientRegistration()); // registrationId = google을 통해 어떤 Oauth로 로그인 했는지 확인가능
        log.info("userRequest.getAccessToken = {}", userRequest.getAccessToken().getTokenValue()); // getToenValue()를 통해 실제 액세스 토큰 확인 가능

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // userRequest 정보를 통해서 loadUser() 함수를 호출하여 회원프로필을 구글로부터 받음
        // {sub=104717591461978030161, name=강병준, given_name=병준, family_name=강, picture=https://lh3.googleusercontent.com/a/AAcHTtczGvv086yOdzmf0UuQxF0cdYVIRVDooGQ3qWOIeLUv3Q=s96-c, email=bjkang402@gmail.com, email_verified=true, locale=ko}
        log.info("getAttributes = {}", oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getClientId(); // google
        String providerId = oAuth2User.getAttribute("sub"); // 104717591461978030161

        return super.loadUser(userRequest);
    }
}
