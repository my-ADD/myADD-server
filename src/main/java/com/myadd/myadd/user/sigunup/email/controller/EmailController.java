package com.myadd.myadd.user.sigunup.email.controller;

import com.myadd.myadd.user.domain.UserEntity;
import com.myadd.myadd.user.domain.UserTypeEnum;
import com.myadd.myadd.user.dto.UserDto;
import com.myadd.myadd.user.service.UserService;
import com.myadd.myadd.user.sigunup.email.EmailLoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@RestController
public class EmailController {

    private final UserService userService;

    @PostMapping("/join") // 이메일 회원가입
    public String emailJoin(@ModelAttribute UserDto userDto){
        userDto.setUserType(UserTypeEnum.EMAIL);
        userService.save(userDto);

        return "success";
    }

    @PostMapping("/login") // 이메일 로그인
    public String emailLoginForm(@Valid @ModelAttribute EmailLoginForm emailLoginForm, BindingResult bindingResult, HttpServletResponse response){
        if (bindingResult.hasErrors()){
            return "has Error";
        }

        UserEntity loginUser = userService.emailLogin(emailLoginForm.getLoginEmail(), emailLoginForm.getLoginPassWord());

        if (loginUser == null){
            bindingResult.reject("loginFail", "이메일 또는 비밀번호가 맞지 않습니다.");
            return "login Error";
        }

        // 로그인 성공 처리
        // 쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료 시 모두 종료)
        Cookie idCookie = new Cookie("cookieUserId", String.valueOf(loginUser.getUserId()));
        response.addCookie(idCookie);

        return "success!";
    }

    @GetMapping("/home") // 로그인 화면에서 수행하여 로그인 수행 시
    public String homeLogin(@CookieValue(name = "cookieUserId", required = false) Long userId, Model model){
        // 쿠키가 없는 사용자
        if(userId == null){
            return "no cookie user";
        }

        // 쿠키가 있는 사용자
        UserEntity userEntity = userService.findById(userId);
        if (userEntity == null) { // 쿠키가 옛날에 만들어져서 db에 없을 수도 있음
            return "no cookie user";
        }
        else{ // 성공 로직
            model.addAttribute("user", userEntity);
            return "success! go to individual page";
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie expiredCookie = new Cookie("cookieUserId", null);
        expiredCookie.setMaxAge(0);
        response.addCookie(expiredCookie);
    }
}
