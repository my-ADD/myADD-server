package com.myadd.myadd.user.sigunup.kakao.service;

import com.myadd.myadd.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;

    public String getAccessToken(String code){

        HttpHeaders headers = new HttpHeaders();;
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "f7dee74aded517904bcbea4c811ada0f");
        params.add("redirect_uri", "http://localhost:8080/login/oauth2/code/kakao/kakao");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                accessTokenRequest,
                String.class
        );

        return response.toString();
    }
}
