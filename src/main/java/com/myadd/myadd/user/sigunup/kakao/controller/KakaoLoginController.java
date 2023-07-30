package com.myadd.myadd.user.sigunup.kakao.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.myadd.myadd.user.domain.UserEntity;
import com.myadd.myadd.user.sigunup.kakao.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/login/oauth2/code/kakao/kakao")
    public void kakaoCallback(@RequestParam String code, HttpSession session) throws JsonProcessingException {
        String accessToken = kakaoLoginService.getAccessTokenResponse(code);
        session.setAttribute("accessToken", accessToken);
        JsonNode jsonNode = kakaoLoginService.getUserInfoByAccessTokenResponse(accessToken);
        UserEntity userEntity = kakaoLoginService.parshingUserInfo(jsonNode);
        kakaoLoginService.save(userEntity);
    }

    @PostMapping("/users/logout/kakao")
    public void kakaoLogout(HttpSession session) {
        String accessToken = (String) session.getAttribute("accessToken");
        if (accessToken != null) {
            kakaoLoginService.kakaoLogout(accessToken);
            session.removeAttribute("accessToken");
        }
    }
}
