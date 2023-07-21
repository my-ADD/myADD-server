package com.myadd.myadd.user.sigunup.google;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class GoogleLoginController {

    private final GoogleLoginService googleLoginService;

    @Autowired
    public GoogleLoginController(GoogleLoginService googleLoginService){
        this.googleLoginService = googleLoginService;
    }

    @GetMapping("/code/{registrationId}")
    public void googleLogin(@RequestParam String code, @PathVariable String registrationId) {
        googleLoginService.socialLogin(code, registrationId);
    }
}