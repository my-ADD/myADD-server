package com.myadd.myadd.user.sigunup.google.controller;

import com.myadd.myadd.user.sigunup.google.service.GoogleLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class GoogleLoginController {

    private final GoogleLoginService googleLoginService;

    @GetMapping("/code/{registrationId}")
    public void googleLogin(@RequestParam String code, @PathVariable String registrationId) {
        googleLoginService.socialLogin(code, registrationId);
    }
}