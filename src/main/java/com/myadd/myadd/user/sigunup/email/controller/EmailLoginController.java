package com.myadd.myadd.user.sigunup.email.controller;

import com.myadd.myadd.user.AppConstants;
import com.myadd.myadd.user.domain.UserEntity;
import com.myadd.myadd.user.domain.UserTypeEnum;
import com.myadd.myadd.user.dto.UserDto;
import com.myadd.myadd.user.security.PrincipalDetails;
import com.myadd.myadd.user.sigunup.email.service.EmailLoginService;
import com.myadd.myadd.user.sigunup.email.domain.EmailLoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Enumeration;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@Controller
public class EmailLoginController {

    private final EmailLoginService emailLoginService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join") // 회원가입(아이디, 비밀번호 방식)
    public @ResponseBody String emailJoin(@ModelAttribute UserDto userDto){
        userDto.setUserType(UserTypeEnum.EMAIL);
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        emailLoginService.save(userDto);

        return "success";
    }

    @PostMapping("/join/email/check-duplicate") // 회원가입 중 이메일 중복 확인
    public String emailDuplicateCheck(@RequestParam String email){
        UserEntity userEntity = emailLoginService.findByEmail(email);
        if(userEntity != null)
            return "Duplicate Email!";
        else
            return "Non-Duplicate Email!";
    }

    @PostMapping("/my-info/delete/email-user")
    public @ResponseBody String emailWithdrawal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((PrincipalDetails)authentication.getPrincipal()).getEmail(); // 이메일 또는 사용자명
        Long id = ((PrincipalDetails) authentication.getPrincipal()).getId(); // UserDetailsImpl은 사용자의 상세 정보를 구현한 클래스
        return emailLoginService.emailWithdrawal(id, email);
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }
}
