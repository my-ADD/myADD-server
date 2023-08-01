package com.myadd.myadd.user.sigunup.email.auth.controller;

import com.myadd.myadd.user.sigunup.email.auth.domain.EmailAuthRequestDto;
import com.myadd.myadd.user.sigunup.email.auth.service.EmailAuthService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// 비밀번호 찾기를 위해 사용하는 이메일 인증
@Slf4j
@RestController
@RequestMapping(value = "/users/change-password")
@RequiredArgsConstructor
public class EmailAuthController {
    private final EmailAuthService emailService;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    // 이메일 인증 코드 전송
    @PostMapping("/email/send-code") // /users/join/email/send-code
    public String sendEmail(@RequestBody EmailAuthRequestDto emailAuthRequestDto) throws MessagingException, UnsupportedEncodingException {
        String authCode = emailService.sendEmail(emailAuthRequestDto.getEmail());

        executorService.schedule(emailService::deleteExpiredAuthNum, 5, TimeUnit.MINUTES);

        return authCode;
    }

    // 사용자가 입력한 인증 코드와 db의 인증 정보 비교
    @PostMapping("/email/check-code")
    public String verifyCode(@RequestParam String email, @RequestParam String code){

        return emailService.verifyCode(email, code);
    }

    @PutMapping("/")
    public String changePassword(@RequestParam String email, @RequestParam String password){
        return emailService.changePassword(email, password);
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }
}
