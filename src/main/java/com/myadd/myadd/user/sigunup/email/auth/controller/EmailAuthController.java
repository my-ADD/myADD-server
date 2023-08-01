package com.myadd.myadd.user.sigunup.email.auth.controller;

import com.myadd.myadd.user.sigunup.email.auth.domain.EmailAuthRequestDto;
import com.myadd.myadd.user.sigunup.email.auth.service.EmailAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// 비밀번호 찾기를 위해 사용하는 이메일 인증
@RestController
@RequestMapping(value = "/users/join/email")
@RequiredArgsConstructor
public class EmailAuthController {
    private final EmailAuthService emailService;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @PostMapping("/send-code") // /users/join/email/send-code
    public String sendEmail(@RequestBody EmailAuthRequestDto emailAuthRequestDto) throws MessagingException, UnsupportedEncodingException {
        String authCode = emailService.sendEmail(emailAuthRequestDto.getEmail());

        executorService.schedule(emailService::deleteExpiredAuthNum, 5, TimeUnit.MINUTES);

        return authCode;
    }

    @PostMapping("/check-code")
    public String checkCode(@RequestParam String email, @RequestParam String code){

        return emailService.verifyCode(email, code);
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }
}
