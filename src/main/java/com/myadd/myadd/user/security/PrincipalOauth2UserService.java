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
        log.info("userRequest.getClientRegistration = {}", userRequest.getClientRegistration());
        log.info("userRequest.getAccessToken = {}", userRequest.getAccessToken());
        log.info("super.loadUser(userRequest).getAttributes() = {}", super.loadUser(userRequest).getAttributes());
        return super.loadUser(userRequest);
    }
}
