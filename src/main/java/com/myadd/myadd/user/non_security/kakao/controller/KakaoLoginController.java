package com.myadd.myadd.user.non_security.kakao.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.myadd.myadd.user.domain.entity.UserEntity;
import com.myadd.myadd.user.security.service.PrincipalDetails;
import com.myadd.myadd.user.non_security.kakao.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KakaoLoginController {  // 스프링 시큐리티를 적용하지 않은 카카오 로그인 컨트롤러(현재 프로젝트에서 사용 x)

    private final KakaoLoginService kakaoLoginService;

    // 카카오 인증 서버는 서비스 서버의 Redirect URI로 인가 코드를 전달함.
    // 사용자가 모든 필수 동의항목에 동의하고 [동의하고 계속하기] 버튼을 누른 경우 -> redirect_uri로 인가 코드를 담은 쿼리 스트링 전달
    // 사용자가 동의 화면에서 [취소] 버튼을 눌러 로그인을 취소한 경우 -> redirect_uri로 에러 정보를 담은 쿼리 스트링 전달
    @GetMapping("/login/oauth2/code/kakao")
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

        return "kakao login success";
    }

    @PostMapping("/users/my-info/logout/kakao")
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

    @PostMapping("/users/my-info/delete/kakao-user")
    public @ResponseBody String kakaoWithdrawal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((PrincipalDetails)authentication.getPrincipal()).getEmail(); // 이메일 또는 사용자명
        Long id = ((PrincipalDetails) authentication.getPrincipal()).getId(); // UserDetailsImpl은 사용자의 상세 정보를 구현한 클래스
        log.info("email = {}", email);
        log.info("id = {}", id);
        return kakaoLoginService.kakaoWithdrawal(id, email);
    }
}
