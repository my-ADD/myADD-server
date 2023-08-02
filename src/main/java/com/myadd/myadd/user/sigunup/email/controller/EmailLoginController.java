package com.myadd.myadd.user.sigunup.email.controller;

import com.myadd.myadd.user.AppConstants;
import com.myadd.myadd.user.domain.UserEntity;
import com.myadd.myadd.user.domain.UserTypeEnum;
import com.myadd.myadd.user.dto.UserDto;
import com.myadd.myadd.user.sigunup.email.service.EmailLoginService;
import com.myadd.myadd.user.sigunup.email.domain.EmailLoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/home")
    public String first(){
        return "home";
    }

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

    @PostMapping("/login/email") // 로그인(아이디, 비밀번호)  (로그인을 하기 위해 입력하는 창에서의 로직(실제 로그인))
    public @ResponseBody String login(@RequestParam String email, @RequestParam String password,  HttpServletRequest request){

        UserEntity loginUser = emailLoginService.emailLogin(email, password);

        // 로그인 성공 처리
        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보 보관
        session.setAttribute(AppConstants.LOGIN_MEMBER, loginUser);

        if (session != null) {
            Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                Object attributeValue = session.getAttribute(attributeName);
                log.info("Session attribute: {} = {}", attributeName, attributeValue);
            }
        } else {
            log.info("No active session found");
        }
        return "login success! go to individual page";
    }

    @PostMapping("/my-info/logout")
    public @ResponseBody String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // true면 없을 시 생성되므로 false로 함. true가 default임
        if (session != null){
            session.invalidate();
        }
        return "logout success!";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }
}
