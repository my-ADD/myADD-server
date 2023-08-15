package com.myadd.myadd.user.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myadd.myadd.response.BaseResponse;
import com.myadd.myadd.response.BaseResponseStatus;
import com.myadd.myadd.user.domain.entity.UserEntity;
import com.myadd.myadd.user.domain.usertype.UserTypeEnum;
import com.myadd.myadd.user.repository.UserRepository;
import com.myadd.myadd.user.domain.usertype.GoogleUserInfo;
import com.myadd.myadd.user.domain.usertype.KakaoUserInfo;
import com.myadd.myadd.user.domain.usertype.OAuth2UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService { // Oauth2 로그인

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override // 구글로부터 받은 userRequest 데이터에 대한 후처리를 하는 메서드
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest = {}", userRequest);
        log.info("userRequest.getClientRegistration = {}", userRequest.getClientRegistration()); // registrationId = google을 통해 어떤 Oauth로 로그인 했는지 확인가능
        log.info("userRequest.getAccessToken = {}", userRequest.getAccessToken().getTokenValue()); // getToenValue()를 통해 실제 액세스 토큰 확인 가능

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // userRequest 정보를 통해서 loadUser() 함수를 호출하여 회원프로필을 구글로부터 받음
        // {sub=104717591461978030161, name=강병준, given_name=병준, family_name=강, picture=https://lh3.googleusercontent.com/a/AAcHTtczGvv086yOdzmf0UuQxF0cdYVIRVDooGQ3qWOIeLUv3Q=s96-c, email=bjkang402@gmail.com, email_verified=true, locale=ko}
        log.info("getAttributes = {}", oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            log.info("구글 유저");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }
        else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            log.info("카카오 유저");
            oAuth2UserInfo = new KakaoUserInfo((Map)oAuth2User.getAttributes().get("kakao_account"));
        }

//        String provider = userRequest.getClientRegistration().getRegistrationId(); // google
//        String nickName = oAuth2User.getAttribute("name"); // 강병준
//        String profile = oAuth2User.getAttribute("picture"); // https://lh3.googleusercontent.com/a/AAcHTtczGvv086yOdzmf0UuQxF0cdYVIRVDooGQ3qWOIeLUv3Q=s96-c
//        UserTypeEnum userTypeEnum = UserTypeEnum.GOOGLE; // 2
//        String email = oAuth2User.getAttribute("email"); // bjkang402@gamil.com
//        String password = bCryptPasswordEncoder.encode(UUID.randomUUID().toString());

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String nickName = oAuth2UserInfo.getNickname();
        String profile = oAuth2UserInfo.getProfile();
        UserTypeEnum userTypeEnum = oAuth2UserInfo.getUsertype();
        String email = provider.toLowerCase() + "_" + oAuth2UserInfo.getEmail();
        String password = bCryptPasswordEncoder.encode(UUID.randomUUID().toString());

        log.info("userInfo = {}", provider + " " + nickName + " " + profile + " " +userTypeEnum + " " + email + " "+password);

        Optional<UserEntity> userEntityOptional= userRepository.findByEmail(email);
        UserEntity userEntity;

        if(!userEntityOptional.isPresent()){
            userEntity = UserEntity.builder()
                    .nickname(nickName)
                    .profile(profile)
                    .userType(userTypeEnum)
                    .email(email)
                    .password(password)
                    .build();
            userRepository.save(userEntity);
        }
        else{
            userEntity = userEntityOptional.get();
            log.info("이미 존재하는 OAuth2 회원입니다.");
        }

        // response 출력을 위한 응답 스트림 처리
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        // 로그인 성공 메시지 생성
        String successMessage = null;
        try {
            successMessage = oauth2LoginSuccess(response, provider);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("application/json;charset=UTF-8");

        try {
            response.getWriter().write(successMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            response.getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes()); // Authentication 객체 안에 들어감
    }

    public String oauth2LoginSuccess(HttpServletResponse response, String provider) throws IOException {
        BaseResponse<BaseResponseStatus> successResponse = null;

        if(provider.equals("google"))
            successResponse = new BaseResponse<>(BaseResponseStatus.SUCCESS_GOOGLE_LOGIN);
        else if(provider.equals("kakao"))
            successResponse = new BaseResponse<>(BaseResponseStatus.SUCCESS_KAKAO_LOGIN);

        String jsonResponse = new ObjectMapper().writeValueAsString(successResponse);

        // response.setStatus(HttpServletResponse.SC_OK);
        // response.setContentType("application/json");
        // response.setCharacterEncoding("UTF-8");
        // response.getWriter().write(jsonResponse);

        return jsonResponse;
    }
}
