package com.myadd.myadd.user.sigunup.google.controller;

import com.myadd.myadd.user.sigunup.google.service.GoogleLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/login/oauth2", produces = "application/json") // 스프링 시큐리티를 적용하지 않은 구글 로그인 컨트롤러(현재 프로젝트에서 사용 x)
public class GoogleLoginController {

    private final GoogleLoginService googleLoginService;
//
//    @GetMapping("/code/google/{registrationId}")
//    public void googleLogin(@RequestParam String code, @PathVariable String registrationId) {
//        googleLoginService.socialLogin(code, registrationId);
//    }
}