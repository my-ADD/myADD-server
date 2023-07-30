package com.myadd.myadd.user.sigunup.kakao.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.myadd.myadd.user.AppConstants;
import com.myadd.myadd.user.domain.UserEntity;
import com.myadd.myadd.user.sigunup.kakao.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/login/oauth2/code/kakao/kakao")
    public String kakaoCallback(@RequestParam String code, HttpServletRequest request) throws JsonProcessingException {
        JsonNode accessTokenResponse = kakaoLoginService.getAccessTokenResponse(code); // code를 통해 얻은 response(access token과 여러 key들 존재)
        String accessToken = kakaoLoginService.parshingAccessToken(accessTokenResponse);
        JsonNode userInfoResponse = kakaoLoginService.getUserInfoByAccessTokenResponse(accessTokenResponse); // access token을 통해 얻은 response(유저 정보 존재)

        HttpSession session = request.getSession();

        UserEntity userEntity = kakaoLoginService.parshingUserInfo(userInfoResponse);
        session.setAttribute("accessToken", accessToken);
        session.setAttribute("id", userEntity.getUserId());
        session.setAttribute("email", userEntity.getEmail());

        if (userEntity!=null){
            kakaoLoginService.save(userEntity);
        }

        return "index";
    }

    @PostMapping("/users/logout/kakao")
    public @ResponseBody String kakaoLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String responseId = "";
        if (session != null) {
            String accessToken = (String) session.getAttribute("accessToken");
            if (accessToken != null) {
                responseId = kakaoLoginService.kakaoLogout(accessToken);
                session.invalidate();
            }
        }
        return responseId;
    }

    @PostMapping("/users/withdrawal/kakao")
    public @ResponseBody String kakaoWithdrawal(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String response = "null";

        if (session != null) {
            log.info("session = {}", session);
            Long deleteUserId = (Long) session.getAttribute("id");
            String deleteUserEmail = (String) session.getAttribute("email");
            response = kakaoLoginService.kakaoWithdrawal(deleteUserId, deleteUserEmail);
            if (response.equals("Withdrawal Success")) {
                session.invalidate();
            }
        }
        else
            response = "Session is Null";


        log.info("respone = {}", response);

        return response;
    }
}
