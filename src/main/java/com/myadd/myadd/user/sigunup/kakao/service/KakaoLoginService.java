package com.myadd.myadd.user.sigunup.kakao.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myadd.myadd.user.repository.UserRepository;
import com.myadd.myadd.user.sigunup.kakao.OAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginService {
    private final UserRepository userRepository;
    private final Environment env;

    public String getAccessTokenResponse(String code) throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader object 생성
        HttpHeaders headers = new HttpHeaders();;
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody object 생성
        String clientId = env.getProperty("oauth2.kakao.client-id");
        String redirectUri = env.getProperty("oauth2.kakao.redirect-uri");
        String resourceUri = env.getProperty("oauth2.kakao.resource-uri");

        // 확인을 위한 log
        log.info("clientId = {}", clientId);
        log.info("redirectUri = {}", redirectUri);
        log.info("resourceUri = {}", resourceUri);

        // HttpHeader와 HttpBody를 하나의 obejct에 담기
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity response = restTemplate.exchange(
                resourceUri,
                HttpMethod.POST,
                accessTokenRequest,
                String.class
        );

        return response.getBody().toString();
    }

    public void getUserInfoByAccessTokenResponse(String code) throws JsonProcessingException {
        String accessTokenResponse = getAccessTokenResponse(code);

        // json object를 자바에서 처리하기 위한 변환 과정
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = objectMapper.readValue(accessTokenResponse, OAuthToken.class);
        log.info("accesstoken = {}", oAuthToken.getAccess_token());

        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader object 생성
        HttpHeaders headers = new HttpHeaders();;
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody object 생성
        String clientId = env.getProperty("oauth2.kakao.client-id");
        String redirectUri = env.getProperty("oauth2.kakao.redirect-uri");
        String resourceUri = env.getProperty("oauth2.kakao.resource-uri");

        // 확인을 위한 log
        log.info("clientId = {}", clientId);
        log.info("redirectUri = {}", redirectUri);
        log.info("resourceUri = {}", resourceUri);

        // HttpHeader와 HttpBody를 하나의 obejct에 담기
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>> userInfoRequest = new HttpEntity<>(params, headers);

        ResponseEntity response = restTemplate.exchange(
                resourceUri,
                HttpMethod.POST,
                userInfoRequest,
                String.class
        );
    }
}
