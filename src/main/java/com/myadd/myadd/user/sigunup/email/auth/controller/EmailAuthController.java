package com.myadd.myadd.user.sigunup.email.auth.controller;

import com.myadd.myadd.user.sigunup.email.auth.domain.EmailAuthRequestDto;
import com.myadd.myadd.user.sigunup.email.auth.service.EmailAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

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
}
