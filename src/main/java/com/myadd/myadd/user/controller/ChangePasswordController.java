package com.myadd.myadd.user.controller;

import com.myadd.myadd.user.domain.dto.EmailAuthRequestDto;
import com.myadd.myadd.user.service.ChangePasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// 비밀번호 찾기 및 비밀번호 변경
@Slf4j
@RestController
@RequestMapping(value = "/change-password")
@RequiredArgsConstructor
public class ChangePasswordController {
    private final ChangePasswordService emailService;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @PostMapping("/send-code") // 이메일 회원 - 회원가입 이메일 중복 확인
    public String sendCode(@RequestBody EmailAuthRequestDto emailAuthRequestDto) throws MessagingException, UnsupportedEncodingException {
        String authCode="";

        log.info("first = {}", emailAuthRequestDto.getEmail());
            // 이메일로 회원가입한 유저가 아닌 경우 예외 처리
            if(emailService.isUserTypeEmail(emailAuthRequestDto.getEmail())){
                emailService.deleteExistCode(emailAuthRequestDto.getEmail());
                authCode = emailService.sendEmail(emailAuthRequestDto.getEmail());

                executorService.schedule(emailService::deleteExpiredAuthNum, 5, TimeUnit.MINUTES);

            return authCode;
        }
        else{
            return "not email user";
        }
    }

    // 사용자가 입력한 인증 코드와 db의 인증 정보 비교
    @PostMapping("/check-code") // 이메일 인증번호 확인
    public String verifyCode(@RequestParam String email, @RequestParam String code){
        if(emailService.isUserTypeEmail(email)) {
            return emailService.verifyCode(email, code);
        }
        else{
            return "not email user";
        }
    }

    @PutMapping("") // 비밀번호 변경
    public String changePassword(@RequestParam String email, @RequestParam String password){
        if(emailService.isUserTypeEmail(email)) {
            return emailService.changePassword(email, password);
        }
        else{
            return "not email user";
        }

    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }
}
