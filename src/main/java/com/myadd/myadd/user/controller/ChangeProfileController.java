package com.myadd.myadd.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.myadd.myadd.response.BaseResponse;
import com.myadd.myadd.response.BaseResponseStatus;
import com.myadd.myadd.user.domain.dto.ProfileChangeRequestDto;
import com.myadd.myadd.user.domain.dto.UserProfileDto;
import com.myadd.myadd.user.domain.entity.UserEntity;
import com.myadd.myadd.user.security.service.PrincipalDetails;
import com.myadd.myadd.user.service.ChangeProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/users/my-info")
@Controller
public class ChangeProfileController {

    @Autowired
    private final ChangeProfileService changeProfileService;

    @ResponseBody
    @PatchMapping("/change/my-profile") // 모든 방식 - 프로필 수정
    public BaseResponse<UserProfileDto> changeProfile(@RequestBody ProfileChangeRequestDto profileChangeRequestDto) {

        String response = "";
        UserProfileDto userProfileDto = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null)
            return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);

        String email = ((PrincipalDetails)authentication.getPrincipal()).getEmail(); // 이메일 또는 사용자명
        Long id = ((PrincipalDetails) authentication.getPrincipal()).getId(); // UserDetailsImpl은 사용자의 상세 정보를 구현한 클래스

        userProfileDto = changeProfileService.findUser(id, email);

        if(userProfileDto == null)
            return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_FOUND_USER);

        response = changeProfileService.changeProfile(userProfileDto, profileChangeRequestDto.getNickname(), profileChangeRequestDto.getProfile());

        if(response.equals("Failed: Change Profile"))
            return new BaseResponse<>(BaseResponseStatus.FAILED_CHANGE_PROFILE);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS_CHANGE_PROFILE);
    }

    @ResponseBody
    @GetMapping("/get/my-profile")
    public BaseResponse<UserProfileDto> getMyProfile(){

        UserProfileDto userProfileDto = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null)
            return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);

        String email = ((PrincipalDetails)authentication.getPrincipal()).getEmail(); // 이메일 또는 사용자명
        Long id = ((PrincipalDetails) authentication.getPrincipal()).getId();

        userProfileDto = changeProfileService.findUser(id, email);

        if(userProfileDto == null)
            return new BaseResponse<>(BaseResponseStatus.FAILED_GET_PROFILE);

        return new BaseResponse<>(userProfileDto, BaseResponseStatus.SUCCESS_GET_PROFILE);

        //ObjectMapper objectMapper = new ObjectMapper();
        //ObjectNode responseNode = objectMapper.createObjectNode();
        //responseNode.put("email", user.getEmail());
        //responseNode.put("nickname", user.getNickname());
        //responseNode.put("profile", user.getProfile());

        //return responseNode.toString();
    }
}
