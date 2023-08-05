package com.myadd.myadd.user.sigunup.email.change.profile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.myadd.myadd.user.domain.UserEntity;
import com.myadd.myadd.user.security.PrincipalDetails;
import com.myadd.myadd.user.sigunup.email.change.profile.service.ChangeProfileService;
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

    @PatchMapping("/change/my-profile") // 프로필 수정(닉네임, 프로필 사진)
    public @ResponseBody String changeProfile(@RequestParam String nickname, @RequestParam String profile) {

        String response = "";
        UserEntity user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((PrincipalDetails)authentication.getPrincipal()).getEmail(); // 이메일 또는 사용자명
        Long id = ((PrincipalDetails) authentication.getPrincipal()).getId(); // UserDetailsImpl은 사용자의 상세 정보를 구현한 클래스

        user = changeProfileService.findUser(id, email);

        response = changeProfileService.changeProfile(user, nickname, profile);

        return response;
    }

    @GetMapping("/get/my-profile")
    public @ResponseBody String getMyProfile(){

        UserEntity user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((PrincipalDetails)authentication.getPrincipal()).getEmail(); // 이메일 또는 사용자명
        Long id = ((PrincipalDetails) authentication.getPrincipal()).getId();

        user = changeProfileService.findUser(id, email);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseNode = objectMapper.createObjectNode();
        responseNode.put("email", user.getEmail());
        responseNode.put("nickname", user.getNickname());
        responseNode.put("profile", user.getProfile());

        return responseNode.toString();
    }
}
