package com.myadd.myadd.user.sigunup.email.auth.controller;

import com.myadd.myadd.user.sigunup.email.auth.domain.EmailAuthRequestDto;
import com.myadd.myadd.user.sigunup.email.auth.service.EmailAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

// 비밀번호 찾기를 위해 사용하는 이메일 인증
@RestController
@RequestMapping(value = "/users/join/email")
@RequiredArgsConstructor
public class EmailAuthController {
    private final EmailAuthService emailService;

    @PostMapping("/send-code") // /users/join/email/send-code
    public String sendEmail(@RequestBody EmailAuthRequestDto emailAuthRequestDto) throws MessagingException, UnsupportedEncodingException {
        String authCode = emailService.sendEmail(emailAuthRequestDto.getEmail());

        return authCode;
    }

    @PostMapping("/check-code")
    public String checkCode(@RequestParam String email, @RequestParam String code){

        return emailService.verifyCode(email, code);
    }
}
