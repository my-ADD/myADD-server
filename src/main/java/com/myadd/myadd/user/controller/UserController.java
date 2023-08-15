package com.myadd.myadd.user.controller;

import com.myadd.myadd.response.BaseResponse;
import com.myadd.myadd.response.BaseResponseStatus;
import com.myadd.myadd.user.domain.dto.EmailRequestDto;
import com.myadd.myadd.user.domain.entity.UserEntity;
import com.myadd.myadd.user.domain.usertype.UserTypeEnum;
import com.myadd.myadd.user.domain.dto.UserDto;
import com.myadd.myadd.user.security.service.PrincipalDetails;
import com.myadd.myadd.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// 로그인은 spring security에서 처리해주므로 별도로 controller를 구현할 필요 없음
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@RestController
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join/email/check-duplicate") // 이메일 회원 - 회원가입 이메일 중복 확인
    public BaseResponse<UserDto> emailCheckDuplicate(@RequestBody EmailRequestDto emailRequestDto){
        if(emailRequestDto.getEmail() == null || emailRequestDto.getEmail().equals(""))
            return new BaseResponse<>(BaseResponseStatus.FAILED_INVALID_INPUT);

        UserEntity userEntity = userService.findByEmail(emailRequestDto.getEmail());

        if(userEntity == null)
            return new BaseResponse<>(BaseResponseStatus.SUCCESS_NOT_DUPLICATED_EMAIL);

        if(userEntity.getEmail().equals(emailRequestDto.getEmail()))
            return new BaseResponse<>(BaseResponseStatus.FAILED_DUPLICATED_EMAIL);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS_NOT_DUPLICATED_EMAIL);
    }

    @PostMapping("/join") // 이메일 회원 - 회원가입
    public BaseResponse<UserDto> joinUser(@RequestBody UserDto userDto){
        if(userDto.getEmail() == null || userDto.getPassword() == null || userDto.getNickname() == null
        || userDto.getEmail().equals("") || userDto.getPassword().equals("") || userDto.getNickname().equals(""))
            return new BaseResponse<>(BaseResponseStatus.FAILED_INVALID_INPUT);

        userDto.setUserType(UserTypeEnum.EMAIL);
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        UserEntity userEntity = userService.findByEmail(userDto.getEmail());

        if(userEntity != null){
            return new BaseResponse<>(BaseResponseStatus.FAILED_DUPLICATED_EMAIL);
        }

        userService.save(userDto);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS_EMAIL_SIGNUP);
    }

    @DeleteMapping("/my-info/delete/user") // 모든 방식 로그인 유저에 대해서 사용 가능
    public BaseResponse<UserDto> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null)
            return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);

        String email = ((PrincipalDetails)authentication.getPrincipal()).getEmail(); // 이메일 또는 사용자명
        Long id = ((PrincipalDetails) authentication.getPrincipal()).getId(); // UserDetailsImpl은 사용자의 상세 정보를 구현한 클래스

        if(userService.findByEmail(email) == null || !userService.deleteUser(id, email))
            return new BaseResponse<>(BaseResponseStatus.FAILED_ALREADY_DELETE_USER);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS_DELETE_USER);
    }

//    @GetMapping("/test") // 카카오에도 시큐리티 적용하고 이메일 잘 나오는지 확인해볼것!
//    public @ResponseBody String test(@AuthenticationPrincipal PrincipalDetails principalDetails){
//        log.info("principalDetails = {}", principalDetails.getUser().getEmail());
//        return "home";
//    }
}
