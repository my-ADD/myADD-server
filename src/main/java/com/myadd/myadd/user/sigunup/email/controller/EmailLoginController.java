package com.myadd.myadd.user.sigunup.email.controller;

import com.myadd.myadd.user.AppConstants;
import com.myadd.myadd.user.domain.UserEntity;
import com.myadd.myadd.user.domain.UserTypeEnum;
import com.myadd.myadd.user.dto.UserDto;
import com.myadd.myadd.user.sigunup.email.service.EmailLoginService;
import com.myadd.myadd.user.sigunup.email.auth.domain.EmailLoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@RestController
public class EmailLoginController {

    private final EmailLoginService emailLoginService;

    @PostMapping("/join") // 이메일 회원가입
    public String emailJoin(@ModelAttribute UserDto userDto){
        userDto.setUserType(UserTypeEnum.EMAIL);
        emailLoginService.save(userDto);

        return "success";
    }

    @PostMapping("/login") // 로그인을 하기 위해 입력하는 창에서의 로직(실제 로그인)
    public String login(@Valid @ModelAttribute EmailLoginForm emailLoginForm, BindingResult bindingResult, HttpServletRequest request){
        if (bindingResult.hasErrors()){
            return "login hasErrors";
        }

        UserEntity loginUser = emailLoginService.emailLogin(emailLoginForm.getLoginEmail(), emailLoginForm.getLoginPassWord());
        log.info("login? {}", loginUser);

        if (loginUser == null){
            bindingResult.reject("loginFail", "이메일 또는 비밀번호가 맞지 않습니다.");
            return "login Error";
        }

        // 로그인 성공 처리
        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보 보관
        session.setAttribute(AppConstants.LOGIN_MEMBER, loginUser);

        return "login success! go to individual page";
    }

    @GetMapping("/") // 로그인되어 있는지 확인하고, 그에 따라 적절한 페이지로 이동
    public String homeLogin(
            @SessionAttribute(name = AppConstants.LOGIN_MEMBER, required = false) UserEntity userEntity, Model model){

        // 세션에 회원 데이터가 없으면 home
        if (userEntity == null){
            return "home";
        }

        // 세션이 유지되면 개인 화면 유지
        model.addAttribute("user", userEntity);
        return "login status continue!! go to individual page";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // true면 없을 시 생성되므로 false로 함. true가 default임
        if (session != null){
            session.invalidate();
        }
        return "logout success!";
    }
}
