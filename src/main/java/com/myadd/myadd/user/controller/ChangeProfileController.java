package com.myadd.myadd.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.myadd.myadd.fileUpload.service.FileUploadService;
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
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/users/my-info")
@Controller
public class ChangeProfileController {

    @Autowired
    private final ChangeProfileService changeProfileService;
    @Autowired
    private final FileUploadService fileUploadService;

    @ResponseBody
    @PatchMapping(value = "/change/my-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<UserProfileDto> changeProfile(
            @RequestPart("nickname") String nickname,
            @RequestPart("profile") MultipartFile multipartFile) throws IOException {


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

        if(userProfileDto.getProfile() != null && !userProfileDto.getProfile().equals("")){
            log.info("delete file name = {}", userProfileDto.getProfile());
            fileUploadService.fileDelete(userProfileDto.getProfile().split("/")[3]);
        }

        String S3FileName = fileUploadService.upload(multipartFile);

        response = changeProfileService.changeProfile(userProfileDto, nickname, S3FileName);

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
