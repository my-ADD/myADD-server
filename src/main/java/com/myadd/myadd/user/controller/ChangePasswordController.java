package com.myadd.myadd.user.controller;

import com.myadd.myadd.response.BaseResponse;
import com.myadd.myadd.response.BaseResponseStatus;
import com.myadd.myadd.user.domain.dto.EmailAuthCodeCheckDto;
import com.myadd.myadd.user.domain.dto.EmailAuthCodeDto;
import com.myadd.myadd.user.domain.dto.EmailRequestDto;
import com.myadd.myadd.user.domain.dto.PasswordChangeRequestDto;
import com.myadd.myadd.user.service.ChangePasswordService;
import com.myadd.myadd.user.service.UserService;
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
    private final UserService userService;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @PostMapping("/send-code") // 이메일 회원 - 비밀번호 변경 중 인증번호 전송
    public BaseResponse<EmailAuthCodeDto> sendCode(@RequestBody EmailRequestDto emailRequestDto) throws MessagingException, UnsupportedEncodingException {

        EmailAuthCodeDto emailAuthCodeDto = new EmailAuthCodeDto();

        log.info("first = {}", emailRequestDto.getEmail());

        // 이메일을 입력하지 않은 경우
        if(emailRequestDto.getEmail() == null)
            return new BaseResponse<>(BaseResponseStatus.FAILED_INVALID_INPUT);

        // 회원으로 등록되지 않은 이메일을 입력한 경우
        if(userService.findByEmail(emailRequestDto.getEmail()) == null)
            return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_FOUND_USER);

        // 이메일로 회원가입한 유저가 아닌 경우 예외 처리
        if(!emailService.isUserTypeEmail(emailRequestDto.getEmail()))
            return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_EMAIL_USER);

        emailService.deleteExistCode(emailRequestDto.getEmail());
        emailAuthCodeDto.setAuthCode(emailService.sendEmail(emailRequestDto.getEmail()));

        executorService.schedule(emailService::deleteExpiredAuthNum, 5, TimeUnit.MINUTES);

        return new BaseResponse<>(emailAuthCodeDto, BaseResponseStatus.SUCCESS_SEND_AUTHCODE);

    }

    // 사용자가 입력한 인증 코드와 db의 인증 정보 비교
    @PostMapping("/check-code") // 이메일 회원 - 비밀번호 변경 중 인증번호 확인
    public BaseResponse<EmailAuthCodeCheckDto> checkCode(@RequestBody EmailAuthCodeCheckDto emailAuthCodeCheckDto){

        // 이메일을 입력하지 않은 경우
        if(emailAuthCodeCheckDto.getAuthCode() == null)
            return new BaseResponse<>(BaseResponseStatus.FAILED_INVALID_INPUT);
        
        // 이메일 회원이 아닌 경우
        if(!emailService.isUserTypeEmail(emailAuthCodeCheckDto.getEmail()))
            return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_EMAIL_USER);

        String response = emailService.verifyCode(emailAuthCodeCheckDto.getEmail(), emailAuthCodeCheckDto.getAuthCode());

        // 인증번호가 생성된지 5분이 되어 만료된 상황에서 인증 번호를 입력한 경우
        if(response.equals("failed: over 5 minute"))
            return new BaseResponse<>(BaseResponseStatus.FAILED_OVERTIME_AUTHCODE);

        // 인증 번호가 틀린 경우
        if(response.equals("failed: not correct auth code"))
            return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_CORRECT_AUTHCODE);

        return new BaseResponse<>(emailAuthCodeCheckDto, BaseResponseStatus.SUCCESS_CHECK_AUTHCODE);
    }

    @PutMapping("") // 이메일 회원 - 비번변경 비밀번호 변경
    public BaseResponse<PasswordChangeRequestDto> changePassword(@RequestBody PasswordChangeRequestDto passwordChangeRequestDto){

        // 회원으로 등록되지 않은 이메일인 경우
        if(userService.findByEmail(passwordChangeRequestDto.getEmail()) == null)
            return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_FOUND_USER);

        // 이메일 유저가 아닌 경우
        if(!emailService.isUserTypeEmail(passwordChangeRequestDto.getEmail()))
            return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_EMAIL_USER);

        // 새로운 비밀번호를 입력하지 않은 경우
        if(passwordChangeRequestDto.getPassword() == null || passwordChangeRequestDto.getPassword().equals(""))
            return new BaseResponse<>(BaseResponseStatus.FAILED_INVALID_INPUT);
        
        String response = emailService.changePassword(passwordChangeRequestDto.getEmail(), passwordChangeRequestDto.getPassword());

        // server나 db 상의 이유로 비밀번호 변경을 실패한 경우
        if(!response.equals("success: change password"))
            return new BaseResponse<>(BaseResponseStatus.FAILED_CHANGE_PASSWORD);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS_CHANGE_PASSWORD);
    }

    @PreDestroy // 스케줄러 소멸
    public void scheduledExecutorDestroy() {
        executorService.shutdown();
    }
}
