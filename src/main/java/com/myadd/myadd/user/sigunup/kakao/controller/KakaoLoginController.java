package com.myadd.myadd.user.sigunup.kakao.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myadd.myadd.user.sigunup.kakao.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/code/kakao/kakao")
    public @ResponseBody String kakaoCallback(@RequestParam String code) throws JsonProcessingException {

        String accessToken = kakaoLoginService.getUserInfoByAccessToken(code);
        return accessToken;
    }
}
