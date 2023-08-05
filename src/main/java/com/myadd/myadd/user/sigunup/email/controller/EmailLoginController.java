package com.myadd.myadd.user.sigunup.email.controller;

import com.myadd.myadd.user.domain.UserEntity;
import com.myadd.myadd.user.domain.UserTypeEnum;
import com.myadd.myadd.user.dto.UserDto;
import com.myadd.myadd.user.security.PrincipalDetails;
import com.myadd.myadd.user.sigunup.email.service.EmailLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public @ResponseBody String emailDuplicateCheck(@RequestParam String email){
        UserEntity userEntity = emailLoginService.findByEmail(email);
        if(userEntity != null)
            return "Duplicate Email!";
        else
            return "Not Duplicate Email!";
    }

    @DeleteMapping("/my-info/delete/user") // 모든 방식 로그인 유저에 대해서 사용 가능
    public @ResponseBody String deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((PrincipalDetails)authentication.getPrincipal()).getEmail(); // 이메일 또는 사용자명
        Long id = ((PrincipalDetails) authentication.getPrincipal()).getId(); // UserDetailsImpl은 사용자의 상세 정보를 구현한 클래스
        return emailLoginService.deleteUser(id, email);
    }

    // 테스트 용
//    @GetMapping("/home")
//    public String home(){
//        return "home";
//    }

//    @GetMapping("/test") // 카카오에도 시큐리티 적용하고 이메일 잘 나오는지 확인해볼것!
//    public @ResponseBody String test(@AuthenticationPrincipal PrincipalDetails principalDetails){
//        log.info("principalDetails = {}", principalDetails.getUser().getEmail());
//        return "home";
//    }
}
